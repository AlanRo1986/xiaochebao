package cn.xiaochebao.app.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import cn.xiaochebao.app.core.FrameApp;


/**
 * 获取网络状态
 * 需要开启:<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>权限
 * Created by Alan on 2017/04/10 0010.
 */
public class NetWorkUtil {
    private static final String permission = "android.permission.ACCESS_NETWORK_STATE";

    public NetWorkUtil(){

    }

    /**
     * 判断是否手机是否可以上网
     * @return
     */
    public static boolean isAvailableConnected(){
        ConnectivityManager cm = (ConnectivityManager) FrameApp.getInstance().getSystemService("connectivity");
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null){
            return info.isAvailable();
        }
        return false;
    }

    /**
     * 判断是否wifi连接
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static boolean isWifiConnected(){
        return getConnectedType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断是否手机网络连接
     * @return
     */
    public static boolean isMobileConnected(){
        return getConnectedType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 取网络连接类型
     * @return
     */
    public static int getConnectedType(){
        ConnectivityManager cm = (ConnectivityManager)FrameApp.getInstance().getSystemService("connectivity");
        NetworkInfo info = cm.getActiveNetworkInfo();

        if(info != null){
            return info.getType();
        }
        return -1;

    }

    /**
     * 取APN连接类型
     * @return
     */
   public static APNType getAPNType(){
        ConnectivityManager cm = (ConnectivityManager)FrameApp.getInstance().getSystemService("connectivity");
        NetworkInfo info = cm.getActiveNetworkInfo();

       if(info != null){
           if(info.getType() == ConnectivityManager.TYPE_MOBILE){
               if(info.getExtraInfo().toLowerCase().equals("cmnet")){
                   return APNType.CMNET;
               }else{
                   return APNType.CMWAP;
               }
           }else if (info.getType() == ConnectivityManager.TYPE_WIFI){
               return APNType.WiFI;
           }

       }
        return APNType.NONE;
   }

    public static enum APNType {
        WiFI,
        CMNET,
        CMWAP,
        NONE;
        private APNType() {

        }
    }
}
