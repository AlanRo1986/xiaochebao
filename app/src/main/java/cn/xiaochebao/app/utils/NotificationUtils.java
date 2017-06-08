package cn.xiaochebao.app.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import cn.xiaochebao.app.R;
import cn.xiaochebao.app.core.FrameApp;
import cn.xiaochebao.app.recevier.NotifyRecevier;

/**
 * 消息管理器
 * Created by Alan on 2016/05/27 0027.
 */
public class NotificationUtils {

    private static final String ACTION_ONCLICK = "com.lanxinbase.app.NOTIFY_CLICK";
    private static final String ACTION_ONCLEAR = "com.lanxinbase.app.NOTIFY_CLEAR";

    private NotificationManager mNotifyManager;
    private Context mContext;
    private int count = 0;

    private static final int NOTIFITY_ID = 0x1124;

    public NotificationUtils(){
        mContext = FrameApp.getInstance();
        mNotifyManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static NotificationUtils getInstance(){
        return new NotificationUtils();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean send (String ticker, String title, String msg, Bundle bundle){

        Intent intent = new Intent(mContext, NotifyRecevier.class);
        intent.setAction(ACTION_ONCLICK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        PendingIntent piClick = PendingIntent.getBroadcast(mContext,0,intent,PendingIntent.FLAG_ONE_SHOT);

        intent = new Intent(mContext, NotifyRecevier.class);
        intent.setAction(ACTION_ONCLEAR);
        PendingIntent piClear = PendingIntent.getBroadcast(mContext,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder build = new Notification.Builder(mContext)
                .setAutoCancel(true)
                .setTicker(ticker)//通知标题
                .setSmallIcon(R.mipmap.ic_launcher)//图标
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)//提示标题
                .setContentText(msg)//提示内容
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)//系统默认的声音
                /**
                 * <uses-permission android:name="android.permission.VIBRATE"></uses-permission>
                 * 设置震动需要添加权限
                 *
                 */
                //.setVibrate(new long[]{0, 100, 200, 300})

                // .setSound(Uri.parse("android.resource://com.lanxin.ui"+R.raw.msg)).setWhen(System.currentTimeMillis())自定义声音
                .setContentIntent(piClick)
                .setDeleteIntent(piClear);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            build.setVisibility(Notification.VISIBILITY_PRIVATE);//任何情况都会显示通知
        }

        mNotifyManager.notify(NOTIFITY_ID,build.build());

        set();

        return true;
    }

    /**
     * 发送第二条消息(消息通知更新)
     * @param ticker 通知标题
     * @param title 消息标题
     * @param msg 消息内容
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean update(String ticker, String title, String msg, Bundle bundle){
        set();
        Intent intent = new Intent(mContext, NotifyRecevier.class);
        intent.setAction(ACTION_ONCLICK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        PendingIntent piClick = PendingIntent.getBroadcast(mContext,0,intent,PendingIntent.FLAG_ONE_SHOT);

        intent = new Intent(mContext, NotifyRecevier.class);
        intent.setAction(ACTION_ONCLEAR);
        PendingIntent piClear = PendingIntent.getBroadcast(mContext,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder build = new Notification.Builder(mContext)
                .setAutoCancel(true)
                .setTicker(ticker)//通知标题
                .setSmallIcon(R.mipmap.ic_launcher)//图标
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)//提示标题
                .setContentText(msg)//提示内容
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)//系统默认的声音
                /**
                 * <uses-permission android:name="android.permission.VIBRATE"></uses-permission>
                 * 设置震动需要添加权限
                 *
                 */
                //.setVibrate(new long[]{0, 100, 200, 300})

                // .setSound(Uri.parse("android.resource://com.lanxin.ui"+R.raw.msg)).setWhen(System.currentTimeMillis())自定义声音
                .setContentIntent(piClick)
                .setDeleteIntent(piClear);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            build.setVisibility(Notification.VISIBILITY_PRIVATE);//任何情况都会显示通知
        }

        mNotifyManager.notify(NOTIFITY_ID,build.build());

        return true;
    }

    /**
     * 返回当前消息条数
     * @return
     */
    public int get(){
        return count;
    }

    /**
     * 设置消息数
     */
    public void set() {
        count++;
    }

    /**
     * 清除消息数
     */
    public boolean clear(){
        mNotifyManager.cancel(NOTIFITY_ID);
        count = 0;
        return true;
    }

}

