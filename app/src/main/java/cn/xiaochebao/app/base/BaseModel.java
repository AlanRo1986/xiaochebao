package cn.xiaochebao.app.base;

import android.os.Bundle;
import android.text.format.DateUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import cn.xiaochebao.app.utils.DateTimeUtil;

/**
 * Created by Alan on 2017/04/07 0007.
 */
public abstract class BaseModel {

    private int page = 1;
    private String params = "";

    private String controller = null;
    private String action = null;

    //是否开启缓存
    private int cacheTime = 0;
    private long expiredTime = 0L;

    public BaseModel(String _ctl,String _act){
        setController(_ctl);
        setAction(_act);
    }

    public String getParams() {
        return params;
    }


    public int getPage() {
        return page;
    }

    public String getController() {
        return controller;
    }

    public String getAction() {
        return action;
    }

    public int getCacheTime(){
        return this.cacheTime;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setParams(String p){
        this.params = p;
    }

    public void setController(String c) {
        this.controller = c;
    }

    public void setAction(String a) {
        this.action = a;
    }

    /**
     * 设置缓存时间,单位为秒
     * @param cache
     */
    public void setCacheTime(int cache) {
        cacheTime = cache * 1000;
    }

    /**
     * 给对象设置一个过期时间
     * @param expiredTime
     */
    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime + getCacheTime();
    }

    /**
     * 使用此方法判断模型数据有效性,是否需要更新
     * @return true 则是数据无效,需要重新读取
     */
    public boolean getExpiredTime() {
        return expiredTime < DateTimeUtil.getTime();
    }
}
