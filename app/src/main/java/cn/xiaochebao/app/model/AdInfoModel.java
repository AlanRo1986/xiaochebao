package cn.xiaochebao.app.model;

import java.util.Map;

import cn.xiaochebao.app.base.BaseModel;
import cn.xiaochebao.app.config.Config;

/**
 * Ad广告模型
 * 传入参数:page=android_init
 * Created by Alan on 2017/04/07 0007.
 */

public class AdInfoModel extends BaseModel {
    private int id;
    private String name;
    private String url;
    private String img;
    private int type;
    private String data;
    private int status;

    /**
     * 初始化模型类
     */
    public AdInfoModel(){
        super(Config.API_CTL.ADV, Config.API_ACT.ADV);
    }

    public static AdInfoModel getInstance(){
        return new AdInfoModel();
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return img;
    }

    public int getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image) {
        this.img = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void bindModel(Map<String,Object> map){
        setId((Integer) map.get("id"));
        setName((String) map.get("name"));
        setUrl((String) map.get("url"));
        setImage((String) map.get("img"));
    }

}
