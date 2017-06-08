package cn.xiaochebao.app.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import java.io.File;
import cn.xiaochebao.app.R;
import cn.xiaochebao.app.config.Constant;
import cn.xiaochebao.app.core.FrameApp;
import cn.xiaochebao.app.libs.Logger;

/**
 * 文件下载类,通常用于更新
 * mDownLoad = DownloadUtil.getInstance();
 * mDownLoad.setAutoOpen(true);
 * mDownLoad.doDownload(url,getString(R.string.app_name)+url.substring(url.lastIndexOf("/")).toLowerCase(),String.format(getString(R.string.vercodeUpgrade),model.getServerVersion()),-1);
 *
 * Created by Alan on 2017/04/12 0012.
 */
public class DownloadUtil {

    private static final String DownLoadId = Constant.DIR_NAME+"DownLoadId";
    private DownloadManager mDrmg = null;
    private DownloadManager.Request mRequest = null;
    private IntentFilter mFilter;
    private long __id = 0x020170412;
    private SharedConfig mSdCfg = null;
    private Context mContext = null;

    //下载完毕后是否自动安装程序
    private boolean isAutoOpen = true;

    public static int NETWORK_MOBILE = DownloadManager.Request.NETWORK_MOBILE;
    public static int NETWORK_WIFI = DownloadManager.Request.NETWORK_WIFI;

    DownloadUtil() {
        this.onResume();
    }

    public static DownloadUtil getInstance(){
        return new DownloadUtil();
    }

    /**
     * 下载执行
     * @param url
     * @param tilte
     * @param description
     * @param networkType
     * @return
     */
    public Long doDownload(String url,String tilte,String description,int networkType){
        if(!mSdCfg.contains(DownLoadId)){
            String endName = url.substring(url.lastIndexOf("/")).toLowerCase();
            mRequest = new DownloadManager.Request(Uri.parse(url));
            mRequest.setTitle(tilte);
            mRequest.setDescription(description);

            if(networkType > 0){
                mRequest.setAllowedNetworkTypes(networkType);
            }
            mRequest.setAllowedOverRoaming(false);
            mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            mRequest.setMimeType(getMimeType(endName));
            mRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,endName);
            mRequest.allowScanningByMediaScanner();
            mRequest.setVisibleInDownloadsUi(true);//设置为可见可管理

            mFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            mContext.registerReceiver(receiver,mFilter);

            __id = mDrmg.enqueue(mRequest);
            mSdCfg.putKey(DownLoadId,__id);

            Logger.info("downID:"+__id);

        }else{
            queryData();
        }
        return __id;

    }


    /**
     * 下载接收器
     */
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            long doId = bundle.getLong(DownloadManager.EXTRA_DOWNLOAD_ID);
            if(__id == doId){
                try {
                    //获取URI
                    Uri uri = mDrmg.getUriForDownloadedFile(__id);
                    Logger.info("downReceiver:"+uri.getEncodedPath());
                    _clear();
                    //打开文件
                    openFile(new File(uri.getEncodedPath()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 下载恢复
     */
    public void onResume(){
        mContext = FrameApp.getInstance();
        mDrmg = (DownloadManager)mContext.getSystemService(mContext.DOWNLOAD_SERVICE);
        mSdCfg = SharedConfig.getInstance();
    }

    /**
     * 下载暂停
     */
    public void onPause(){
        if(mRequest != null){
            mContext.unregisterReceiver(receiver);
            mDrmg = null;
        }

    }

    /**
     * 查询下载数据
     */
    private void queryData(){
        __id = mSdCfg.getKeyLong(DownLoadId);
        Logger.info("query:"+__id);
        if (__id > 0){
            DownloadManager.Query dmQ = new DownloadManager.Query();
            dmQ.setFilterById(__id);

            Cursor c = mDrmg.query(dmQ);
            if(c.moveToNext()){
                int s = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                switch (s){
                    case DownloadManager.STATUS_PAUSED:
                    case DownloadManager.STATUS_RUNNING:
                    case DownloadManager.STATUS_PENDING:
                        DialogUtil.getInstance(mContext).toast(mContext.getString(R.string.downloading));
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        //下载完成
                        Uri uri = mDrmg.getUriForDownloadedFile(__id);
                        _clear();
                        if(isAutoOpen == true){
                            openFile(new File(uri.getEncodedPath()));
                        }
                        break;
                    case DownloadManager.STATUS_FAILED:
                        //死掉
                        mDrmg.remove(__id);
                        _clear();
                        break;

                }
                c.close();
            }else {
                _clear();
            }
        }
    }

    private void _clear(){
        mSdCfg.remove(DownLoadId);
    }

    /**
     * 设置是否自动打开安装文件
     * @param autoOpen
     */
    public void setAutoOpen(boolean autoOpen) {
        isAutoOpen = autoOpen;
    }

    private void openFile(File file){
        UtilsEx.openFile(file);
    }

    private String getMimeType(String filePath){
        if(filePath.endsWith(".apk")){
            return "application/vnd.android.package-archive";
        }else if(filePath.endsWith(".png") || filePath.endsWith(".jpg") || filePath.endsWith(".jpeg") || filePath.endsWith(".gif")){
            return "image/*";
        }else if(filePath.endsWith(".mp4")
                || filePath.endsWith(".3gp")
                || filePath.endsWith(".avi")
                || filePath.endsWith(".flv")
                || filePath.endsWith(".wmv")
                || filePath.endsWith(".rmvb")
                || filePath.endsWith(".asf")
                || filePath.endsWith(".mkv")
                || filePath.endsWith(".mpg")){
            return "video/*";
        }else if(filePath.endsWith(".mp3")
                || filePath.endsWith(".ogg")
                || filePath.endsWith(".ape")){
            return "audio/*";
        }

        return null;

    }

}
