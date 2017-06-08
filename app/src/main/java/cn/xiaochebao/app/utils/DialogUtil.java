package cn.xiaochebao.app.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cn.xiaochebao.app.R;

/**
 * 信息框实用类
 * 如弹出alert or toast
 */
public class DialogUtil {

    private Context mContext = null;
    private Toast toast;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public DialogUtil(Context ctx) {
        mContext = ctx;
    }

    public static DialogUtil getInstance(Context ctx){
        return new DialogUtil(ctx);
    }

    /**
     * mDialog.Alert("Alert标题NoID","Alert信息内容");
     * @param title
     * @param message
     */
    public void alert(CharSequence title, CharSequence message) {
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    /**
     * mDialog.Alert(R.string.msg_title,R.string.msg_context);
     * @param title
     * @param message
     */
    public void alert(int title, int message) {
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }


    /**
     * 确认框
     * example:
     * mDialog.confirm("Confirm标题noID", "Confirm信息内容", new DialogInterface.OnClickListener(), new DialogInterface.OnClickListener());
     *
     * @param title
     * @param message
     * @param confirmListener
     * @param cancelListener
     */
    public void confirm(CharSequence title, CharSequence message, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, confirmListener)
                .setNegativeButton(R.string.no, cancelListener)
                .show();
    }

    /**
     * mDialog.confirm(R.string.msg_title,R.string.msg_context, new DialogInterface.OnClickListener(), new DialogInterface.OnClickListener());
     * @param title
     * @param message
     * @param confirmListener
     * @param cancelListener
     */
    public void confirm(int title, int message, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, confirmListener)
                .setNegativeButton(R.string.no, cancelListener)
                .show();
    }


    /**
     * 弹出自定义的窗体
     *
     * example:
     * View v2 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_view,null);
     * dialogShowView("dialogShowView标题Noid", v2, new DialogInterface.OnClickListener() , new DialogInterface.OnClickListener());
     *
     * @param title
     * @param view
     * @param confirmListener
     * @param cancelListener
     */
    public void dialogShowView(CharSequence title, View view, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setView(view)
                .setPositiveButton(R.string.ok, confirmListener)
                .setNegativeButton(R.string.no, cancelListener)
                .show();
    }


    /**
     *弹出自定义的窗体
     * example:
     * View v2 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_view,null);
     * mDialog.dialogShowView(R.string.msg_title, v2, new DialogInterface.OnClickListener() , new DialogInterface.OnClickListener() );
     *
     * @param title
     * @param view
     * @param confirmListener
     * @param cancelListener
     */
    public void dialogShowView(int title, View view, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setView(view)
                .setPositiveButton(R.string.ok, confirmListener)
                .setNegativeButton(R.string.no, cancelListener)
                .show();
    }


    /**
     * 加载中....
     * @param message
     * @param isCancel
     * @return
     */
    public Dialog showLoading(String message,boolean isCancel) {
        Dialog dialog = new Dialog(mContext,R.style.dialogBase);
        View view = View.inflate(mContext, R.layout.z_dialog_custom_loading, null);
        TextView text = (TextView) view.findViewById(R.id.dialog_custom_loading_txt_progress_msg);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.dialog_custom_loading_pb_progress);
        progressBar.setIndeterminateDrawable(mContext.getResources().getDrawable(R.drawable.utlis_dialog_rec_rotate_progress_white));
        text.setText(message);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setContentView(view);
        dialog.show();
        dialog.setCancelable(isCancel);

//        /**
//         * 设置窗口参数
//         */
//        Point point = new Point();
//        WindowManager _wm = dialog.getWindow().getWindowManager();
//        _wm.getDefaultDisplay().getSize(point);
//
//        int barHeight = 230;
//        Window dialogWindow = dialog.getWindow();
//        WindowManager.LayoutParams _lp = dialogWindow.getAttributes();
//
//        _lp.gravity = Gravity.TOP;
//        _lp.y = barHeight;//top:220
//        _lp.height = point.y - _lp.y;//如果有footbar 那么应该在这里减去对应的高度
//        _lp.width = point.x;
//        _lp.alpha = 0.95f;
//
//        dialogWindow.setAttributes(_lp);

        return dialog;
    }

    /**
     * 加载中....
     * DialogUtil dialog = DialogUtil.getInstance(mContext);
     * dialog.showLoading2("请稍后....",true);
     *
     * @param message
     * @param isCancel
     * @return
     */
    public Dialog showLoading2(String message,boolean isCancel) {

        Dialog dialog = new Dialog(mContext, R.layout.z_dialog_metaball_loading);
        View view = View.inflate(mContext, R.layout.z_dialog_metaball_loading, null);
        TextView text = (TextView) view.findViewById(R.id.dialog_custom_loading_txt_progress_msg);
        text.setText(message);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setContentView(view);
        dialog.show();
        dialog.setCancelable(isCancel);

        /**
         * 设置窗口参数
         */
        Point point = new Point();
        WindowManager _wm = dialog.getWindow().getWindowManager();
        _wm.getDefaultDisplay().getSize(point);

        int barHeight = 230;
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams _lp = dialogWindow.getAttributes();

        _lp.gravity = Gravity.TOP;
        _lp.y = barHeight;//top:220
        _lp.height = point.y - _lp.y;//如果有footbar 那么应该在这里减去对应的高度
        _lp.width = point.x;
        _lp.alpha = 0.95f;

        dialogWindow.setAttributes(_lp);

        return dialog;
    }


    /**
     * 自动关闭的信息框
     * @param text
     */
    public void toast(String text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    /**
     * 小的提示框
     * @param view
     * @param msg
     * @param actName
     * @param listener
     */
    public void snackBar(View view, String msg, String actName, View.OnClickListener listener){
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction(actName, listener).show();
    }

    private void showToast(final String text, final int duration) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            show(text, duration);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    show(text, duration);
                }
            });
        }
    }

    /**
     * 信息框
     * @param text
     * @param duration 延时关闭时间
     */
    private void show(String text, int duration) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(mContext, text, duration);
        toast.show();
    }

}
