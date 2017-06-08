package cn.xiaochebao.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import cn.xiaochebao.app.core.FrameApp;

import java.util.Map;
import java.util.Set;

/**
 * 共享配置操作类
 * v1.2
 * Created by Alan on 2016/05/27 0027.
 */
public class SharedConfig {
    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    public SharedConfig(){

        //MODE_PRIVATE,MODE_WORLD_WRITEABLE
        sp = FrameApp.getInstance().getSharedPreferences("sysset", Context.MODE_PRIVATE);

    }

    /**
     * 取操作类实例
     * @return
     */
    public static SharedConfig getInstance(){
        return new SharedConfig();
    }

    public String getKeyStr(String key){
        return sp.getString(key,"");
    }

    public int getKeyInt(String key){
        return sp.getInt(key, 0);
    }

    public long getKeyLong(String key){
        return sp.getLong(key, 0);
    }

    public boolean getKeyBoolean(String key){
        return sp.getBoolean(key, false);
    }

    public float getKeyFloat(String key){
        return sp.getFloat(key, 0);
    }

    public Set<String> getKeySet(String key){
        return sp.getStringSet(key, null);
    }

    public Map<String,?> getKeyMap(){
        return sp.getAll();
    }

    public boolean putKey(String key,String val){
        ed = sp.edit();
        ed.putString(key,val);
        return ed.commit();
    }

    public boolean putKey(String key,int val){
        ed = sp.edit();
        ed.putInt(key, val);
        return ed.commit();
    }

    public boolean putKey(String key,boolean val){
        ed = sp.edit();
        ed.putBoolean(key, val);
        return ed.commit();
    }

    public boolean putKey(String key,float val){
        ed = sp.edit();
        ed.putFloat(key, val);
        return ed.commit();
    }

    public boolean putKey(String key,long val){
        ed = sp.edit();
        ed.putLong(key, val);
        return ed.commit();
    }

    public boolean putKey(String key,Set<String> val){
        ed = sp.edit();
        ed.putStringSet(key,val);
        return ed.commit();
    }

    public boolean remove(String key){
        ed = sp.edit();
        ed.remove(key);
        return ed.commit();
    }

    /**
     * 是否包含这个值
     * @param key
     * @return
     */
    public boolean contains(String key){
        return sp.contains(key);
    }

}
