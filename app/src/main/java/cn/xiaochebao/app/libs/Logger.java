package cn.xiaochebao.app.libs;

import android.util.Log;

/**
 * Created by Alan on 2017/03/31 0031.
 */
public class Logger {

    private static final String TAG = "FRAMEAPPLOG>>>";
    private static final boolean IS_DEBUG = true;
    private static Object obj;

    public void Looper(){

    }

    private static void setObj(Object o) {
        obj = o;
    }

    private static String getObj() {
        if(obj == null){
            return "null";
        }
        return obj.toString();
    }

    public static void info(Object obj){
        setObj(obj);
        if(IS_DEBUG){
            Log.i(TAG,getObj());
        }
    }

    public static void debug(Object obj){
        setObj(obj);
        if(IS_DEBUG){
            Log.d(TAG,getObj());
        }
    }
    public static void err(Object obj){
        setObj(obj);
        if(IS_DEBUG){
            Log.e(TAG,getObj());
        }
    }
    public static void verbose(Object obj){
        setObj(obj);
        if(IS_DEBUG){
            Log.v(TAG,getObj());
        }
    }

    public static void warn(Object obj){
        setObj(obj);
        if(IS_DEBUG){
            Log.w(TAG,getObj());
        }
    }

}
