package cn.xiaochebao.app.service;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.util.Map;
import cn.xiaochebao.app.R;
import cn.xiaochebao.app.base.BaseCurl;
import cn.xiaochebao.app.core.FrameApp;
import cn.xiaochebao.app.libs.HttpInterfaceServer;
import cn.xiaochebao.app.libs.HttpRequestHandler;
import cn.xiaochebao.app.libs.Logger;
import cn.xiaochebao.app.model.VersionModel;
import cn.xiaochebao.app.utils.DialogUtil;
import cn.xiaochebao.app.utils.DownloadUtil;
import cn.xiaochebao.app.utils.JsonUtils;
import cn.xiaochebao.app.utils.UtilsEx;


public class UpgradeService extends Service {

    public static final String SERVICE_START_TYPE = "SERVICE_START_TYPE";
    private int mStartType = 0;//0.程序自动,1.手动检测

    private VersionModel model;
    private Dialog mDialog = null;
    private DownloadUtil mDownLoad = null;

    public UpgradeService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {

        // mDownloadUrl =
        // "http://gdown.baidu.com/data/wisegame/484af350ea7ba5bc/baidushoujizhushou_16783625.apk";

        mStartType = intent.getIntExtra(SERVICE_START_TYPE,0);

        testUpgrade();
        return super.onStartCommand(intent, flags, startId);
    }

    private void testUpgrade() {
        if(mStartType == 1){
            mDialog = DialogUtil.getInstance(this).showLoading(getString(R.string.checkUpgrade),false);
        }

        model = new VersionModel();
        HttpInterfaceServer.getInstance(model, new HttpRequestHandler() {
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(long totalSize, long currentSize) {

            }

            @Override
            public void onSuccess(Map<String, Object> data, int code) {
                Object object = JsonUtils.parseObject((Map<String, Object>) data.get(BaseCurl.response_data), VersionModel.class);
                if (object != null){
                    model = (VersionModel) object;
                    FrameApp.setPrevModel(model,VersionModel.class.getName());

                    if(model.getHasUpgrade() == 1 && UtilsEx.getVersionCode() < model.getServerVersion()){
                        initDownload();
                    }
                }


            }

            @Override
            public void onError(Map<String, Object> data, int code) {
                Logger.warn(data.get(BaseCurl.response_err));
                DialogUtil.getInstance(getApplicationContext()).toast(getString(R.string.checkUpgradeFailed));
            }

            @Override
            public void onFinish() {
                UtilsEx.dialogDismiss(mDialog);
                stopSelf();
            }
        }).get();
//        HttpInterfaceServer.getInstance(model, new HttpRequestCallBack() {
//            @Override
//            public void onSuccess(Map<String, Object> data, int code) {
//
//                Logger.info(data);
//                Object object = JsonUtils.parseObject((Map<String, Object>) data.get(BaseCurl.response_data), VersionModel.class);
//                if (object != null){
//                    model = (VersionModel) object;
//                    FrameApp.setPrevModel(model,VersionModel.class.getName());
//
//                    if(model.getHasUpgrade() == 1 && UtilsEx.getVersionCode() < model.getServerVersion()){
//                        initDownload();
//                    }
//                }
//
//                UtilsEx.dialogDismiss(mDialog);
//            }
//
//            @Override
//            public void onError(Map<String, Object> data, int code) {
//                Logger.warn(data.get(BaseCurl.response_err));
//                DialogUtil.getInstance(getApplicationContext()).toast(getString(R.string.checkUpgradeFailed));
//                UtilsEx.dialogDismiss(mDialog);
//                //stopSelf();
//            }
//            @Override
//            public void onFinish() {
//                UtilsEx.dialogDismiss(mDialog);
//                stopSelf();
//            }
//        }).get();
    }

    /**
     * 初始化下载
     */
    private void initDownload() {
        if(model.getHasUpgrade() == 1 && UtilsEx.getVersionCode() < model.getServerVersion() && !model.getFilename().equals("")){
            Logger.info("has new version "+model.getServerVersion()+" url:"+model.getFilename());
            DialogUtil.getInstance(getApplicationContext()).toast(getString(R.string.checkUpgradeSuccessNew));

            String url = model.getFilename();
            //serverVersion
            mDownLoad = DownloadUtil.getInstance();
            mDownLoad.setAutoOpen(true);
            mDownLoad.doDownload(url,getString(R.string.app_name)+url.substring(url.lastIndexOf("/")).toLowerCase(),String.format(getString(R.string.vercodeUpgrade),model.getServerVersion()),-1);

        }else{
            //DialogUtil.getInstance(getApplicationContext()).toast(getString(R.string.checkUpgradeSuccessNot));
            stopSelf();
        }
    }

    public void onDestroy() {
        if (mDownLoad != null){
            mDownLoad.onPause();
        }
    }

}
