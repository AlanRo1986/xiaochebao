package cn.xiaochebao.app.model;

import android.database.sqlite.SQLiteDatabase;

import cn.xiaochebao.app.base.BaseDBModel;

/**
 * The DB Model
 * Example:
 * TestModel dbModel = TestModel.getInstance();
 *
 * insert data:
 * ContentValues v = new ContentValues();
 * v.put("name","a123456"+DateTimeUtil.getSecond(null));
 * v.put("create_time", DateTimeUtil.getTime());
 * res = dbModel.add(v);
 *
 * get data for one fiele:
 * dbModel.getOne(new String[]{"name"},"id=?",new String[]{"1"}); //returen the ListArray Object
 * dbModel.getOneByJson(new String[]{"name"},"id=?",new String[]{"1"});//return the Json Object
 *
 * get data for All:
 * dbModel.getAll(TestModel.cls,"id > ?",new String[]{"2"},"id desc",null);
 * dbModel.getAllByJson(TestModel.cls,"id > ?",new String[]{"2"},"id desc",null);
 *
 *
 * Created by Alan on 2017/04/06 0006.
 *
 */
public class TestModel extends BaseDBModel {

    private static final int Version = 1;
    private static final String table = "test";
    public static String[] cls = {"id","name","create_time"};

    public TestModel() {
        super(table,Version);
    }

    public static TestModel getInstance(){
        return new TestModel();
    }

    @Override
    public void createDB(SQLiteDatabase db) {

    }

    @Override
    public void updateDB(SQLiteDatabase db) {

    }
}
