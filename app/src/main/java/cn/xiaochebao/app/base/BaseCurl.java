package cn.xiaochebao.app.base;

import cn.xiaochebao.app.core.FrameApp;
import cn.xiaochebao.app.libs.HttpRequestHandler;
import cn.xiaochebao.app.libs.Logger;
import cn.xiaochebao.app.utils.JsonUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alan on 2017/03/31 0031.
 */
public abstract class BaseCurl {

    public static final String GET = "GET";   //读
    public static final String POST = "POST"; //创建
    public static final String PUT = "PUT";  //更新
    public static final String DELETE = "DELETE"; //删除

    /**
     * 返回的数据类型
     */
    public static final String response_err = "response_err";

    /**
     * 用户判断数据返回有效性,如果是1.则有效,可缓存,如果是0.无效,不缓存
     */
    public static final String response_code = "response_code";
    public static final String response_data = "response_data";
    public static final String response_proto = "response_proto";

    public static final int response_Success = 1;
    public static final int response_Failed = 0;

    private static final int TIMEOUT = 8000;

    public BaseCurl(){}

    /**
     * 网络请求
     * @param _uri 通常为请求网址
     * @param param 请求的参数,通常是:a=1&b=2这种格式
     * @param method 请求的方法
     * @return
     */
    protected static final  Map<String,Object> curl(String _uri, String param, String method, HttpRequestHandler handler){

        Map<String,Object> response = new HashMap<>();
        InputStream in = null;
        ByteArrayOutputStream out = null;
        HttpURLConnection conn = null;

        response.put(response_err,"网络繁忙!");
        response.put(response_code,0);
        response.put(response_data,null);

        try {
            if(!checkParam(_uri,method)){
                throw new Exception("Request data isn't standard.");
            }
            Logger.info("uri:"+_uri+" params:"+param);

            URL url = new URL(_uri);
            conn = (HttpURLConnection) url.openConnection();

            if (!FrameApp.getSessionId().equals("") && FrameApp.getSessionId().indexOf("PHPSESSID") != -1){
                conn.setRequestProperty("cookie",FrameApp.getSessionId());//设置sessionID
            }

            if (!FrameApp.getCookies().equals("") && FrameApp.getCookies().indexOf("PHPSESSID") != -1){
                conn.setRequestProperty("cookie",FrameApp.getCookies());//设置Cookies,这个必须包含SessionID
            }

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setReadTimeout(TIMEOUT);
            conn.setConnectTimeout(TIMEOUT);
            conn.setRequestMethod(method);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection","Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Cache-Control","no-cache");
            conn.setRequestProperty("X-Powered-By","LanxinAndroid v1.1");

            if(method == GET){
                param = null;
            }
            if(param != null && param.trim().indexOf("=") != -1){
                PrintWriter pw = new PrintWriter(conn.getOutputStream());
                pw.print(param);
                pw.flush();
            }

            //conn.connect();
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                in = conn.getInputStream();
                out = new ByteArrayOutputStream();

                int len = -1;
                byte[] bytes = new byte[128];

                while ((len = in.read(bytes)) != -1){
                    if (handler != null){
                        handler.sendProgress(conn.getContentLength(),out.size());
                    }
                    out.write(bytes,0,len);
                }
                out.flush();
               // Logger.warn(out.toString());
                response.put(response_err,"success");
                response.put(response_code,1);
                response.put(response_data, JsonUtils.jsonToMap(out.toString()));
                response.put(response_proto, out.toString());

            }else {
                throw new RuntimeException("HTTP ERR_CODE: "+conn.getResponseCode());
            }


        } catch (Exception e) {
            e.printStackTrace();
            response.put(response_err, e.getMessage());
            response.put(response_proto, null);
        }finally {
            try {
                if(in != null){
                    in.close();
                }
                if(out != null){
                    out.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            if(conn != null){
                conn.disconnect();
            }
        }

        return response;

    }

    /**
     * 检查参数是否正确
     * @param _uri
     * @param method
     * @return
     */
    protected static final boolean checkParam(String _uri,String method){
        if (_uri.length() < 10){
            return false;
        }

        return  method == POST || method == GET || method == PUT || method == DELETE;
    }

}
