package cn.xiaochebao.app.core;

import java.util.HashMap;
import java.util.Map;

import cn.xiaochebao.app.R;
import cn.xiaochebao.app.base.BaseCurl;
import cn.xiaochebao.app.libs.HttpRequestHandler;
import cn.xiaochebao.app.utils.NetWorkUtil;

/**
 * 异步HTTP请求
 * Created by Alan on 2017/04/11 0011.
 */
public class AsyncHttpRequest extends BaseCurl implements Runnable {

    private String mUrl = null;
    private String mMethod = null;
    private String mParams = null;
    private HttpRequestHandler mHandler = null;
    private Map<String,Object> response = null;

    public AsyncHttpRequest(String uri, String params, String method, HttpRequestHandler handler){
        mUrl = uri;
        mMethod = method;
        mHandler = handler;
        mParams = params;

    }

    @Override
    public void run() {
        try {
            mHandler.sendStart();
            if(!NetWorkUtil.isAvailableConnected()){
                throw new Exception(FrameApp.getInstance().getString(R.string.inValidNetwork));
            }

            response = curl(mUrl,mParams,mMethod,mHandler);
            mHandler.sendSuccess(response, (Integer) response.get(BaseCurl.response_code));

        }catch (Exception e){
            e.printStackTrace();
            response = new HashMap<>();
            response.put(BaseCurl.response_err,e.getMessage());
            response.put(BaseCurl.response_code,0);
            response.put(BaseCurl.response_data,null);
            mHandler.sendError(response, 0);
        }finally {
            mHandler.sendFinish();
        }

    }
}
