package cn.xiaochebao.app.interfaces;

import java.util.Map;

/**
 * Created by Alan on 2017/04/01 0001.
 */
public interface HttpRequestCallBack {
    void onStart();
    void onProgress(long totalSize,long currentSize);
    void onSuccess(Map<String,Object> data,int code);
    void onError(Map<String,Object> data,int code);
    void onFinish();
}
