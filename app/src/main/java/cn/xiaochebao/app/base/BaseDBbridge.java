package cn.xiaochebao.app.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.xiaochebao.app.core.FrameApp;

/**
 * Created by Alan on 2016/05/13 0013.
 */
public abstract class BaseDBbridge {

    private Context mContext;
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    private static final String DB_NAME = "data.db";


    private String CREATE_DB_SQL = "CREATE TABLE test (id INTEGER PRIMARY KEY autoincrement,name TEXT NOT NULL,create_time int);";
    private String UPDATE_DB_SQL = "";

    public BaseDBbridge(int version){

        mContext = FrameApp.getInstance();
        dbHelper = new DBHelper(mContext,DB_NAME,null,version);
        db = dbHelper.getReadableDatabase();
    }

    /**
     * 插入数据
     * @param table 表名
     * @param values 要插入的数据对象
     * @return 返回-1则失败
     */
    public long insert(String table , ContentValues values){
        checkOpen();
        return db.insert(table,null,values);
    }

    /**
     * 修改数据
     * @param table 表名
     * @param values 要更新的数据对象
     * @param where 条件,如: id=? 或者 id=? and name like ?
     * @param whereArgs 条件对应数组值 new String[] {"1"} 或者 new String[] {"1","admin"}
     * @return
     */
    public int update(String table ,ContentValues values ,String where , String[] whereArgs){
        checkOpen();
        return db.update(table, values, where, whereArgs);
    }

    /**
     *删除数据
     * @param table 表名
     * @param where 条件,如: id=? 或者 id=? and name like ?
     * @param whereArgs 条件对应数组值 new String[] {"1"} 或者 new String[] {"1","admin"}
     * @return
     */
    public int delete(String table , String where , String[] whereArgs){
        checkOpen();
        return db.delete(table, where, whereArgs);
    }

    /**
     * 查询数据
     * @param table 表名
     * @param cls 列字段名
     * @param select 条件,如: id=? 或者 id=? and name like ?
     * @param selectionArgs 条件对应数组值 new String[] {"1"} 或者 new String[] {"1","admin"}
     * @param groupBy 分组
     * @param orderBy 排序
     * @return
     */
    public Cursor query(String table, String[] cls, String select, String[] selectionArgs, String groupBy, String orderBy, String limit){
        if(orderBy == null || orderBy.isEmpty()){
            orderBy = "id desc";
        }
        if(limit == null || limit.isEmpty()){
            limit = "0,65535";
        }
        checkOpen();

        return db.query(table, cls, select, selectionArgs, groupBy, null, orderBy, limit);
    }

    public void execSQL(String sql){
        checkOpen();

        db.execSQL(sql);
    }

    public void close(){
        if(db.isOpen()){
            db.close();
        }
    }

    public int getVersion(){
        checkOpen();

        return db.getVersion();
    }
    public String getDbPath(){
        checkOpen();

        return db.getPath();
    }

    private void checkOpen(){
        if (!db.isOpen()){
            db = dbHelper.getReadableDatabase();
        }
    }


    /**
     * 创建数据库表
     * @param db
     */
    public abstract void createDB(SQLiteDatabase db);

    /**
     * 更新数据库表
     * @param db
     */
    public abstract void updateDB(SQLiteDatabase db);

    public class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_SQL);
            createDB(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(UPDATE_DB_SQL);
            updateDB(db);
        }
    }



}


