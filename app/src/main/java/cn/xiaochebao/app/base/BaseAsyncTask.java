package cn.xiaochebao.app.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import cn.xiaochebao.app.libs.Logger;


/**
 * 异步后台处理类
 *
 * 请在执行的Acitivity的Manifest添加:android:configChanges="screenSize|orientation",否则当屏幕旋转时ProgressDialog会自动消失
 * 权限申请:
 * <uses-permission android:name="android.permission.INTERNET"></uses-permission>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
 * <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"></uses-permission>
 *
 * Created by Alan on 2017/04/05 0005.
 */

public abstract class BaseAsyncTask extends AsyncTask<String,Integer,Object> implements DialogInterface.OnCancelListener {

    private boolean isCanCancel = true;
    private Context mContext;
    private ProgressDialog pd;
    private boolean isShowProcess;//是否显示进度条

    private String ProgressTitle = "AsyncTask";
    private String ProgressMsg = "wait...";

    public void __init(Context ctx,String title,String msg){
        if(title != null){
            setProgressTitle(title);
        }
        if(msg != null){
            setProgressMsg(msg);
        }
        mContext = ctx;
        isShowProcess = true;
    }

    /**
     * 任务开始执行,开启ProgressDialog
     */
    protected void onPreExecute() {
        pd = new ProgressDialog(mContext);
        pd.setIndeterminate(isShowProcess);
        pd.setCanceledOnTouchOutside(false);
        if(!isShowProcess){
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        }
        pd.setCancelable(true);
        pd.setOnCancelListener(this);
        pd.setTitle(getProgressTitle());
        pd.setMessage(getProgressMsg());
        pd.setMax(100);
        pd.setProgress(0);
        pd.show();
        Logger.info("asyncDownLoadTask Start...");

    }

    /**
     * 任务进行中
     * @param values
     */
    protected void onProgressUpdate(Integer... values) {
        pd.setProgress(values[0]);
        if(isShowProcess){
            pd.setMessage(getProgressMsg()+"..."+ values[0] + "%");
        }
        Logger.info("asyncDownLoadTask do Working..."+values[0]);
    }

    /**
     * 进入后台任务
     * @param params
     * @return
     */
    @Override
    protected Object doInBackground(String... params) {
        return doWorking(params[0]);
    }

    /**
     * 数据处理函数,需要继承
     * 需要实行以下代码
     * publishProgress((target * 100) / length);
     *
     * @param url
     * @return
     */
    public abstract Object doWorking(String url);

    /**
     * 任务取消后执行的函数,需要继承
     * @return
     */
    public abstract void doCancel();

    /**
     * 任务执行成功后需要执行的函数,需要继承
     * @param result
     */
    public abstract void doSuccess(Object result);

    /**
     * 任务执行完毕,需要继承
     * @param result
     */
    @Override
    protected void onPostExecute(Object result) {
        pd.dismiss();
        doSuccess(result);
    }

    /**
     * 任务取消
     * @param dialogInterface
     */
    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if (isCanCancel == true){
            this.cancel(true);
            doCancel();
        }
    }

    /**
     * 设置任务信息提示标题
     * @param progressTitle
     */
    protected void setProgressTitle(String progressTitle) {
        ProgressTitle = progressTitle;
    }

    /**
     * 设置任务信息提示内容
     * @param progressMsg
     */
    protected void setProgressMsg(String progressMsg) {
        ProgressMsg = progressMsg;
    }

    /**
     * 取任务信息提示标题
     * @return
     */
    protected String getProgressTitle() {
        return ProgressTitle;
    }

    /**
     * 取任务信息提示内容
     * @return
     */
    protected String getProgressMsg() {
        return ProgressMsg;
    }

    /**
     * 是否可以取消
     * @param canCancel
     */
    public void setCanCancel(boolean canCancel) {
        isCanCancel = canCancel;
    }
}
