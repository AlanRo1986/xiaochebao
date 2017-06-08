package cn.xiaochebao.app.libs;

import android.os.Handler;
import android.os.Looper;

import java.util.Map;

import cn.xiaochebao.app.interfaces.HttpRequestCallBack;

/**
 * Created by Alan on 2017/04/21 0021.
 */
public abstract class HttpRequestHandler implements HttpRequestCallBack {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public HttpRequestHandler(){}

    public void sendStart() {
        mHandler.post(new Runnable() {
            public void run() {
                HttpRequestHandler.this.onStart();
            }
        });
    }

    public void sendProgress(final long totalSize, final long currentSize) {
        mHandler.post(new Runnable() {
            public void run() {
                HttpRequestHandler.this.onProgress(totalSize,currentSize);
            }
        });
    }

    public void sendSuccess(final Map<String,Object> data, final int code) {
        mHandler.post(new Runnable() {
            public void run() {
                if(data != null){
                    HttpRequestHandler.this.onSuccess(data,code);
                }
            }
        });
    }

    public void sendError(final Map<String,Object> data, final int code) {
        mHandler.post(new Runnable() {
            public void run() {
                HttpRequestHandler.this.onError(data,code);
            }
        });
    }

    public void sendFinish() {
        mHandler.post(new Runnable() {
            public void run() {
                HttpRequestHandler.this.onFinish();
            }
        });
    }

}
