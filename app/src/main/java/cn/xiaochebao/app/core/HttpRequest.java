package cn.xiaochebao.app.core;

import android.app.Dialog;
import android.util.Base64;

import cn.xiaochebao.app.base.BaseCurl;
import cn.xiaochebao.app.config.Config;
import cn.xiaochebao.app.config.Constant;
import cn.xiaochebao.app.libs.FileCache;
import cn.xiaochebao.app.libs.HttpRequestHandler;
import cn.xiaochebao.app.utils.DialogUtil;
import cn.xiaochebao.app.utils.NetWorkUtil;
import cn.xiaochebao.app.utils.UtilsEx;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alan on 2017/03/31 0031.
 */
public class HttpRequest{

    /**
     * 需要提交的请求参数
     */
    private String params = null;

    /**
     * 需要请求的网址
     */
    private String uri = null;

    /**
     * 初始化控制器名称
     */
    private String ctl = "init";

    /**
     * 初始化行为的方法
     */
    private String act = "index";

    /**
     * 请求类型;0.base64编码,1.正常提交
     * i_type = 0 需要把所有的请求编码为BASE64并传输给requestData
     * Constant.RequestDataType
     */
    private int i_type = Constant.RequestDataType.REQUEST;
    private final String requestData = "requestData";


    /**
     * 返回的请求数据类型
     * 请查阅Constant.ResponseDataType类
     */
    private int r_type = 1;

    private ThreadPoolExecutor mThreadPool;



    /**
     * http请求类
     * httpRequest = HttpRequest.getInstance();
     * httpRequest.setUri("http://demo.cn/api/");
     * httpRequest.setCtl("init");
     * httpRequest.get("init","remove","bnt=but4",new HttpRequestCallBack);
     */
    public HttpRequest(){
        setRtype(Constant.ResponseDataType.JSON);
        setItype(Constant.RequestDataType.REQUEST);
        setCtl(Config.API_CTL.INDEX);
        setAct(Config.API_ACT.INDEX);
        setUri(Config.CFG_SERVER_URI);
        mThreadPool =  new ThreadPoolExecutor(5,10,0L,TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static HttpRequest getInstance(){
        return new HttpRequest();
    }

    /**
     * 发起数据读取请求
     * @param ctl 控制器
     * @param act 方法
     * @param param 请求参数(a=1&b=2)
     * @param handler
     */
    public void get(String ctl, String act, String param, HttpRequestHandler handler){
        setCtl(ctl);
        setAct(act);
        setParams(param);

        asyn(getUri() + "?" + getParams(), BaseCurl.GET,handler);
    }

    /**
     * 发起数据保存请求
     * @param ctl
     * @param act
     * @param param
     * @param handler
     */
    public void post(String ctl, String act, String param, HttpRequestHandler handler){
        setCtl(ctl);
        setAct(act);
        setParams(param);
        asyn(getUri(), BaseCurl.POST,handler);
    }

    /**
     * 发起数据更新请求
     * @param ctl
     * @param act
     * @param param
     * @param handler
     */
    public void put(String ctl, String act, String param, HttpRequestHandler handler){
        setCtl(ctl);
        setAct(act);
        setParams(param);

        asyn(getUri() + "?" + getParams(), BaseCurl.PUT,handler);
    }

    /**
     * 发起删除请求
     * @param ctl
     * @param act
     * @param param
     * @param handler
     */
    public void delete(String ctl, String act, String param, HttpRequestHandler handler){
        setCtl(ctl);
        setAct(act);
        setParams(param);

        asyn(getUri() + "?" + getParams(), BaseCurl.PUT,handler);
    }

    protected final void asyn(String uri, String method, HttpRequestHandler handler){
        mThreadPool.submit(new AsyncHttpRequest(uri,getParams(),method,handler));
    }

    protected final void sync(final String uri, final String method, final HttpRequestHandler handler){
        new Thread(new AsyncHttpRequest(uri,getParams(),method,handler)).run();
    }

    /**
     * 强制打断http线程
     * @param isTrue
     */
    public void doInterrupt(boolean isTrue){
        if (isTrue == true && mThreadPool != null){
            if(mThreadPool.isShutdown() == false){
                mThreadPool.shutdownNow();
            }
        }
    }

    public ThreadPoolExecutor getThreadPool() {
        return mThreadPool;
    }

    /**
     * 取数据请求加密类型
     * @return
     */
    public int getItype() {
        return i_type;
    }

    /**
     * 取数据回应加密类型
     * @return
     */
    public int getRtype() {
        return r_type;
    }


    /**
     * 取控制器行为方法
     * @return
     */
    public String getAct() {
        return act;
    }

    /**
     * 取控制器
     * @return
     */
    public String getCtl() {
        return ctl;
    }

    /**
     * 取请求参数
     * @return
     */
    public String getParams() {
        return params;
    }

    /**
     * 取请求API地址
     * @return
     */
    public String getUri() {
        return uri;
    }

    public void setCtl(String c) {
        this.ctl = c;
    }

    public void setAct(String a) {
        this.act = a;
    }

    public void setItype(int i) {
        this.i_type = i;
    }

    public void setRtype(int r) {
        this.r_type = r;
    }

    /**
     * 设置请求参数
     * @param p 格式必须是:a=x&b=x
     */
    public void setParams(String p) {
        //ctl=deal&id=1437&is_app=1&client_ip=127.0.0.1&act=deal&r_type=0&i_type=1&from=wap
        String param = "ctl=%s&act=%s&r_type=%s&i_type=%s&dev_type=%s";
        param = String.format(param,getCtl(),getAct(),getRtype(),getItype(),Constant.DeviceType.DEVICE_ANDROID);
        if(p != null && p.indexOf("=") != -1){
            if(getItype() == Constant.RequestDataType.BASE64){
                param = param + "&" + requestData + "=" + new String(Base64.encode(p.getBytes(),Base64.DEFAULT));
            }else {
                param = param + "&" + p;
            }

        }

        this.params = param;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
