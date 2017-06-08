package cn.xiaochebao.app.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;

import cn.xiaochebao.app.R;
import cn.xiaochebao.app.core.FrameApp;
import cn.xiaochebao.app.libs.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * android 工具类 v1.3
 * Created by Alan on 2016/06/02 0002.
 */
public class UtilsEx {

   private static final double EARTH_RADIUS = 6378137;//地球的半径

    /**
     * Md5换算
     *
     * @param string
     * @return
     */
    public static String md5(String string) {

        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 should be supported?", e);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);

        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 设置剪辑版内容
     * @param str
     */
    public static void setClipBoard(String str) {
        ClipboardManager cm = (ClipboardManager) FrameApp.getInstance().getSystemService(FrameApp.getInstance().CLIPBOARD_SERVICE);

        ClipData clipData = ClipData.newPlainText(FrameApp.TAG,str);
        cm.setPrimaryClip(clipData);
    }

    /**
     * 读取剪辑版内容
     * @return
     */
    public static String getClipBoard() {
        ClipboardManager cm = (ClipboardManager) FrameApp.getInstance().getSystemService(FrameApp.getInstance().CLIPBOARD_SERVICE);
        ClipData clipData = cm.getPrimaryClip();

        if(clipData == null){
            return "";
        }
        return clipData.getItemAt(0).getText().toString();
    }

    /**
     * 取app版本信息
     * @return
     */
    public static String getVersionName() {
        try {
            PackageInfo manager = FrameApp.getInstance().getPackageManager().getPackageInfo(
                    FrameApp.getInstance().getPackageName(), 0);
            return manager.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }

    /**
     * 取APP版本号码
     * @return
     */
    public static int getVersionCode() {
        try {
            PackageInfo manager = FrameApp.getInstance().getPackageManager().getPackageInfo(
                    FrameApp.getInstance().getPackageName(), 0);
            return manager.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 1;
        }
    }

    /**
     * 检查是否有权限
     * @param ctx
     * @param permission
     * @return
     */
    public static boolean checkPermission(Context ctx,String permission){
        int i = ctx.checkCallingOrSelfPermission(permission);
        return i == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 取内存卡路径
     * 如果目录不存在,则创建新目录
     * android 6.0需要申请权限
     * @return
     */
    public static String getPath(String path) {
        if(path == null){
            path = "/mnt/sdcard/lanxin/";
        }

        String[] paths = path.split("/");
        String tmp = "/";
        if (paths == null || paths.length <= 0) {
            return null;
        }
        for (int i = 0; i < paths.length; i++) {
            if (!paths[i].equals("")) {
                tmp += paths[i] + "/";
            }
            File file = new File(tmp);
            if (!file.exists()){
                file.mkdir();
            }

        }
        return tmp;
    }

    /**
     * 复制文件
     * @param filePath 源文件
     * @param target 目标文件
     * @return
     */
    public static boolean cpFile(String filePath, String target){
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1 );

        File file1 = new File(filePath);
        File file2 = new File(target);

        if (!file1.exists())
            return false;

        if (!file2.exists())
            file2.mkdirs();

        try {
            InputStream in = new FileInputStream(file1);
            FileOutputStream fos = new FileOutputStream(target + "/" + fileName);

            byte[] b = new byte[1024];
            int lenght = -1;
            int byteed = 0;

            while ( (lenght = in.read(b)) != -1){
                byteed += lenght;

                fos.write(b,0,byteed);
            }

            fos.close();

        }catch (Exception e){
            return false;
        }

        return true;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，
     *
     * @param lat1 本次的lat
     * @param lng1 本次的lng
     * @param lat2 上次的lat
     * @param lng2 上次的lng
     * @return 距离：单位为米
     */
    public static double operationTwoPoints(double lat1,double lng1, double lat2,double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);

        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));

        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }


    /**
     * 格式化数值
     * @param num
     * @param fromat
     * @return
     */
    public static float fromatNumber(Double num, String fromat){
        if (fromat == null){
            fromat = "##.##";
        }
        DecimalFormat df = new DecimalFormat(fromat);
        return Float.parseFloat(df.format(num));
    }

    /**
     * 取文件的后缀名
     * @param filename
     * @return 返回类似: .jpg 小写字符串
     */
    public static String getFileExtend(String filename) {
        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
    }

    /**
     * 判断数组是否存在某个值
     * @param arr 数组
     * @param name 要判断的字符串
     * @return
     */
    public static boolean inArray(String[] arr, String name) {
        return Arrays.asList(arr).contains(name);
    }

    /**
     * 检查一个文件是否存在,如果醋在,曾返回一个新的文件名
     * @param path 路径
     * @param filename 文件名
     * @return
     */
    public static String checkFileExists(String path,String filename){
        if(!path.substring(path.length()-1).equals("/")){
            path += "/";
        }

        File file = new File(path + filename);

        if(!file.exists()){
            return filename;
        }else{
            String extend = getFileExtend(filename);
            String newName = filename.replace(extend, "");

            int f = 1;
            File newFile = new File(path + newName + "(" + f + ")"+extend);
            while (newFile.exists()){
                f++;
                newFile = new File(path + newName + "(" + f + ")"+extend);
            }
            return newName + "(" + f + ")"+extend;
        }
    }

    /**
     * 打开文件
     * @param file
     */
    public static void openFile(File file){
        if (!file.exists()){
            return;
        }
        Intent intent = new Intent();
        String fileName = file.getAbsolutePath();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String[] imgs = {"image/*",".png",".jpg",".jpeg",".gif",".bmp"};
        String[] videos = {"video/*",".mp4",".3gp",".avi",".flv",".wmv",".rmvb",".asf",".mkv",".mpg"};
        String[] audios = {"audio/*",".mp3",".ogg",".ape",".wav",".wma"};

        if(inArray(imgs,getFileExtend(fileName))){
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "image/*");
            FrameApp.getInstance().startActivity(intent);

        }else if(inArray(videos,getFileExtend(fileName))){
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "video/*");
            FrameApp.getInstance().startActivity(intent);

        }else if(inArray(audios,getFileExtend(fileName))){
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "audio/*");
            FrameApp.getInstance().startActivity(intent);

        }else if(fileName.endsWith(".apk")){
            intent.setAction("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            FrameApp.getInstance().startActivity(intent);
        }
    }

    public static void dialogDismiss(Dialog d){
        if(d != null){
            d.dismiss();
        }
    }
}
