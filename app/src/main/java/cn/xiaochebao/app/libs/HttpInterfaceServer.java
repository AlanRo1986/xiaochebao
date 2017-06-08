package cn.xiaochebao.app.libs;

import java.util.HashMap;
import java.util.Map;

import cn.xiaochebao.app.base.BaseCurl;
import cn.xiaochebao.app.base.BaseModel;
import cn.xiaochebao.app.core.HttpRequest;
import cn.xiaochebao.app.utils.JsonUtils;

/**
 * HTTP请求接口服务
 * 所有的http请求均使用此接口调取
 * Created by Alan on 2017/04/10 0010.
 */
public class HttpInterfaceServer extends HttpRequestHandler{

    private HttpRequestHandler httpHandler;
    private BaseModel model = null;
    private HttpRequest mHttp = null;
    private FileCache mCache = null;
    private String cacheHash = "";

    public HttpInterfaceServer(BaseModel _model, HttpRequestHandler handler) {
        model = _model;
        httpHandler = handler;
        mHttp = HttpRequest.getInstance();
        mCache = FileCache.getInstance();
        mCache.setValidTime(_model.getCacheTime());
        setCacheHash();
    }

    public static HttpInterfaceServer getInstance(BaseModel _model, HttpRequestHandler handler){
        return new HttpInterfaceServer(_model,handler);
    }

    /**
     * http获取数据的接口,通常会使用缓存
     */
    public void get(){
        String str = getCache(cacheHash);
        if(str != null && !str.equals("")){
            onStart();
            Map<String,Object> map = new HashMap<>();
            map.put(BaseCurl.response_data,JsonUtils.jsonToMap(str));
            onSuccess(map,1);
            onFinish();

            return;
        }else {
            mHttp.get(model.getController(),model.getAction(),model.getParams(),this);
        }

    }

    /**
     * http请求的方法,通常会删除缓存
     * @param method post/put/delete
     */
    public void put(String method){
        rmCache(cacheHash);
        switch (method){
            case BaseCurl.POST:
                mHttp.post(model.getController(),model.getAction(),model.getParams(),this);
                break;
            case BaseCurl.PUT:
                mHttp.put(model.getController(),model.getAction(),model.getParams(),this);
                break;
            case BaseCurl.DELETE:
                mHttp.delete(model.getController(),model.getAction(),model.getParams(),this);
                break;
        }
    }

    /**
     * 取缓存
     * @param hash
     * @return
     */
    private String getCache(String hash){
        if (model.getCacheTime() <= 0){
            return null;
        }
        //Logger.info(getClass().getName());
        return mCache.get(hash);
    }

    /**
     * 删除缓存
     * @param hash
     * @return
     */
    private boolean rmCache(String hash){
        if (model.getCacheTime() <= 0){
            return false;
        }

        return mCache.rm(hash);
    }

    /**
     * 保存缓存,会先判断模型是否允许缓存,如果允许,则缓存数据
     * @param jsonProto 原型数据
     * @return
     */
    private boolean saveCache(String jsonProto){
        if (model.getCacheTime() <= 0){
            return false;
        }
        return mCache.set(cacheHash,jsonProto);
    }

    /**
     * 取模型
     * @return
     */
    public BaseModel getModel() {
        return model;
    }

    /**
     * 设置缓存HASH值,计算方式是:ctl+act+params+id+page(请求方式独一无二)
     */
    public void setCacheHash() {
        cacheHash = model.getController() + model.getAction() + model.getParams() + model.getPage();
    }

    /**
     * 强制打断http线程
     * @param isTrue
     */
    public void doInterrupt(boolean isTrue){
        if (isTrue == true){
            mHttp.doInterrupt(isTrue);
        }
    }

    @Override
    public void onStart() {
        httpHandler.sendStart();
    }

    @Override
    public void onProgress(long totalSize, long currentSize) {
        httpHandler.sendProgress(totalSize,currentSize);
    }


    @Override
    public void onSuccess(Map<String, Object> data, int code) {
        Map<String, Object> map = (Map<String, Object>) data.get(BaseCurl.response_data);
        if(map != null){
            if ((int)map.get(BaseCurl.response_code) == BaseCurl.response_Success){
                saveCache((String) data.get(BaseCurl.response_proto));
            }

            //把原型数据删除,免得占用内存
            data.remove(BaseCurl.response_proto);
        }
        httpHandler.sendSuccess(data,code);
    }

    @Override
    public void onError(Map<String, Object> data, int code) {
        httpHandler.sendError(data,code);
    }

    @Override
    public void onFinish() {
        httpHandler.sendFinish();
    }

}
