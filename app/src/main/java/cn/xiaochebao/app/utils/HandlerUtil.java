package cn.xiaochebao.app.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 消息提醒
 * handler = new BaseHandler(Looper.getMainLooper()) {
 *     @Override
 *     public void processMessage(Message msg) {
 *
 *     }
 * };
 *
 * Created by Alan on 2016/06/12 0012.
 *
 */
public class HandlerUtil {

    private static Handler handler = new Handler(Looper.getMainLooper());
    public HandlerUtil(){

    }

    public synchronized static void run(Runnable r)
    {
        if (Looper.myLooper() != Looper.getMainLooper())
        {
            handler.post(r);
        } else
        {
            r.run();
        }
    }

    public synchronized static void runAtFrontOfQueue(Runnable r)
    {
        handler.postAtFrontOfQueue(r);
    }

    public synchronized static void runAtTime(Runnable r, long uptimeMillis)
    {
        handler.postAtTime(r, uptimeMillis);
    }

    public synchronized static void runAtTime(Runnable r, Object msgObj, long uptimeMillis)
    {
        handler.postAtTime(r, msgObj, uptimeMillis);
    }

    public synchronized static void runDelayed(Runnable r, long delayMillis)
    {
        handler.postDelayed(r, delayMillis);
    }
}
