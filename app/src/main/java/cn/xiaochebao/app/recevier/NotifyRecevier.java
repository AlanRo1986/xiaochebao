package cn.xiaochebao.app.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.xiaochebao.app.core.FrameApp;
import cn.xiaochebao.app.libs.Logger;

/**
 * Created by Alan on 2017/04/06 0006.
 */
public class NotifyRecevier extends BroadcastReceiver {

    private static final String ACTION_ONCLICK = "com.lanxinbase.app.NOTIFY_CLICK";
    private static final String ACTION_ONCLEAR = "com.lanxinbase.app.NOTIFY_CLEAR";
    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    private static final String ACTION_LOW = Intent.ACTION_BATTERY_LOW;
    private static final String ACTION_OKAY = Intent.ACTION_BATTERY_OKAY;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();



        switch (action){
            case ACTION_ONCLICK:
                Bundle bundle = intent.getExtras();
                /**
                 * do something to work....
                 *
                 */
                if(FrameApp.getNotify() != null){
                    FrameApp.getNotify().clear();
                }

                Logger.info("NotifyRecevier:点击了消息");
                break;
            case ACTION_ONCLEAR:
                if(FrameApp.getNotify() != null){
                    FrameApp.getNotify().clear();
                }

                Logger.info("NotifyRecevier:滑动或点击删除消息");
                break;
            case ACTION_LOW:
                //<uses-permission android:name="android.permission.BATTERY_STATS"></uses-permission>

                Logger.info("NotifyRecevier:低电量模式....");
                break;
            case ACTION_OKAY:
                Logger.info("NotifyRecevier:已经充满了");
                break;
            case ACTION_BOOT:
                //<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"></uses-permission>
                Logger.info("NotifyRecevier:开机启动");
                break;
        }
    }
}
