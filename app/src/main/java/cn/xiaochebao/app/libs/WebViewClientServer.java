package cn.xiaochebao.app.libs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.xiaochebao.app.R;
import cn.xiaochebao.app.utils.DialogUtil;

/**
 * Created by Alan on 2017/04/18 0018.
 */
public class WebViewClientServer extends WebViewClient {

    private Context mContext;
    private Dialog mDialog;

    public WebViewClientServer(Context ctx){
        mContext = ctx;
        mDialog = DialogUtil.getInstance(mContext).showLoading(mContext.getString(R.string.wait),true);
    }

    public static WebViewClientServer getInstance(Context ctx){
        return new WebViewClientServer(ctx);
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (mDialog != null){
            mDialog.show();
        }
    }

    public void onPageFinished(WebView view, String url) {
        if(mDialog != null){
            mDialog.dismiss();
        }
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        DialogUtil.getInstance(mContext).toast(mContext.getString(R.string.inValidNetwork));
        Logger.info("err:::"+errorCode);
    }



}
