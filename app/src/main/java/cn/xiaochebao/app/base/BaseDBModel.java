package cn.xiaochebao.app.base;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/04/06 0006.
 */
public abstract class BaseDBModel extends BaseDBbridge {

    private String table = "test";

    public BaseDBModel(String table,int version) {
        super(version);

        setTable(table);
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    /**
     * 取一个,返回 Map<String,Object> 对象
     * @param field 数据库对应的字段
     * @param where 条件,通常为:"id = ? " or "id = ? and name = ?"
     * @param whereArgs 根据where条件进行设定的值, example: new String[] {"1"}
     */
    public Map<String,Object> getOne(String[] field, String where, String[] whereArgs){

        List<Map<String, Object>> list = precessCursorToList(_getOne(field,where,whereArgs),field);

        if (list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 取一个,返回 Map<String,Object> 对象
     * @param field 数据库对应的字段
     * @param where 条件,通常为:"id = ? " or "id = ? and name = ?"
     * @param whereArgs 根据where条件进行设定的值, example: new String[] {"1"}
     */
    public JSONObject getOneByJson(String[] field, String where, String[] whereArgs){
        JSONArray jsonArray = precessCursorToJson(_getOne(field,where,whereArgs),field);

        if(jsonArray.isNull(0)){
            return null;
        }else {
            try {
                return jsonArray.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 取一行,返回 Map<String,Object> 对象
     * @param field 数据库对应的字段
     * @param where 条件,通常为:"id = ? " or "id = ? and name = ?"
     * @param whereArgs 根据where条件进行设定的值, example: new String[] {"1"}
     */
    public Map<String,Object> getRow(String[] field,String where, String[] whereArgs){
        List<Map<String, Object>> list = precessCursorToList(_getRow(field,where,whereArgs),field);
        if (list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 取一行,返回 JSONObject 对象
     * @param field 数据库对应的字段
     * @param where 条件,通常为:"id = ? " or "id = ? and name = ?"
     * @param whereArgs 根据where条件进行设定的值, example: new String[] {"1"}
     * @return
     */
    public JSONObject getRowByJson(String[] field,String where, String[] whereArgs){
        JSONArray jsonArray = precessCursorToJson(_getRow(field,where,whereArgs),field);
        if(jsonArray.isNull(0)){
            return null;
        }else {
            try {
                return jsonArray.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 取全部
     * @param field
     * @param where
     * @param whereArgs
     * @param orderBy
     * @param limit
     */
    public List<Map<String, Object>> getAll(String[] field, String where, String[] whereArgs, String orderBy, String limit){
        return precessCursorToList(_getAll(field,where,whereArgs,orderBy,limit),field);
    }

    public JSONArray getAllByJson(String[] field, String where, String[] whereArgs, String orderBy, String limit){
        return precessCursorToJson(_getAll(field,where,whereArgs,orderBy,limit),field);
    }

    /**
     * 新数据插入
     * @param keyVal
     * @return
     */
    public long add(ContentValues keyVal){
        return insert(table,keyVal);
    }

    /**
     * 数据更新
     * @param keyVal
     * @param where
     * @param whereArgs
     * @return
     */
    public long change(ContentValues keyVal,String where , String[] whereArgs){
        return update(table,keyVal,where , whereArgs);
    }

    /**
     * 数据删除
     * @param where
     * @param whereArgs
     * @return
     */
    public long remove(String where , String[] whereArgs){
        return delete(table,where , whereArgs);
    }

    /**
     * 解析Cursor对象
     * @param cursor
     * @param field
     * @return
     */
    private  List<Map<String,Object>> precessCursorToList(Cursor cursor,String[] field){

        List<Map<String,Object>> list = new ArrayList<>();
        if(cursor.getCount() > 0){
            Map<String,Object> map;
            while(cursor.moveToNext()){
                map = new HashMap<>();
                for (String i:field){
                    if(cursor.isNull(cursor.getColumnIndex(i))){
                        map.put(i,"");
                    }else{
                        map.put(i,cursor.getString(cursor.getColumnIndex(i)));
                    }
                }
                list.add(map);
            }
        }
        //Logger.info(list.toString());
        cursor.close();
        return list;
    }

    /**
     * 解析Cursor数据到JSON
     * @param cursor
     * @param field
     * @return
     */
    private JSONArray precessCursorToJson(Cursor cursor, String[] field){

        JSONArray jsonArray = new JSONArray();

        if(cursor.getCount() > 0){
            JSONObject object;
            while(cursor.moveToNext()){
                object = new JSONObject();
                try {
                    for (String i:field){
                        if(cursor.isNull(cursor.getColumnIndex(i))){
                            object.put(i,"");
                        }else{
                            object.put(i,cursor.getString(cursor.getColumnIndex(i)));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(object);
            }
        }
        //Logger.info(jsonArray.toString());
        cursor.close();
        return jsonArray;
    }


    /**
     * 私有类,不公开
     * @param field
     * @param where
     * @param whereArgs
     * @return
     */
    private Cursor _getOne(String[] field, String where, String[] whereArgs){
        return query(table,field,where,whereArgs,null,null,"1");
    }

    /**
     * 私有类,不公开
     * @param field 数据库对应的字段
     * @param where 条件,通常为:"id = ? " or "id = ? and name = ?"
     * @param whereArgs 根据where条件进行设定的值, example: new String[] {"1"}
     * @return
     */
    private Cursor _getRow(String[] field,String where, String[] whereArgs){
        return query(table,field,where,whereArgs,null,null,"1");
    }

    /**
     * 私有类,不公开
     * @param field
     * @param where
     * @param whereArgs
     * @param orderBy
     * @param limit
     * @return
     */
    private Cursor _getAll(String[] field, String where, String[] whereArgs, String orderBy, String limit){
        return query(table,field,where,whereArgs,null,orderBy,limit);
    }


    @Override
    public abstract void createDB(SQLiteDatabase db);

    @Override
    public abstract void updateDB(SQLiteDatabase db);
}
