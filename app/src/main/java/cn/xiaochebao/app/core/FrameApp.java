package cn.xiaochebao.app.core;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.xiaochebao.app.base.BaseModel;
import cn.xiaochebao.app.utils.DateTimeUtil;
import cn.xiaochebao.app.utils.ImageLoaderUtils;
import cn.xiaochebao.app.utils.NotificationUtils;

/**
 * Created by Alan on 2017/03/31 0031.
 */
public class FrameApp extends Application {

    public static final String TAG = "FrameApp";
    private static FrameApp instance;
    private static NotificationUtils mNotify;

    private static String SESSION_ID = "";
    private static String COOKIES = "";
    private static Map<String,BaseModel> prevModel = new HashMap<>();
    private static ImageLoaderUtils mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        instance = this;
        mNotify = NotificationUtils.getInstance();
        mImageLoader = ImageLoaderUtils.getInstance(this);

//        handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message message) {
//                Logger.info("message.what" + message.what);
//                return false;
//            }
//        });

    }

    public static FrameApp getInstance(){
        return instance;
    }


    public static BaseModel getPrevModel(String key) {
        return prevModel.get(key);
    }

    public static void setPrevModel(BaseModel p,String key) {
        if(p != null){
            p.setExpiredTime(DateTimeUtil.getTime());
       }
        prevModel.put(key,p);
    }

    public static NotificationUtils getNotify() {
        return mNotify;
    }

    public static void setSessionId(String sessionId) {
        SESSION_ID = sessionId;
    }

    public static String getSessionId() {
        return SESSION_ID;
    }

    public static void setCookies(String a) {
        FrameApp.COOKIES = a;
    }

    public static String getCookies() {
        return COOKIES;
    }

    public static ImageLoaderUtils getImageLoader() {
        return mImageLoader;
    }

    public void exitApp(boolean isBackground) {
        releaseResource();
        if (isBackground)
        {

        } else
        {
            System.exit(0);
        }
    }

    private void releaseResource() {

    }
}
