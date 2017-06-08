package cn.xiaochebao.app.libs;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import cn.xiaochebao.app.R;
import cn.xiaochebao.app.config.Config;
import cn.xiaochebao.app.utils.DialogUtil;

/**
 * Created by Alan on 2017/04/17 0017.
 */
public class WebChromeClientServer extends WebChromeClient {

    private Context mContext;

    public WebChromeClientServer(Context ctx){
        mContext = ctx;
    }

    public static WebChromeClientServer getInstance(Context ctx){
        return new WebChromeClientServer(ctx);
    }

    public void onProgressChanged(WebView view, int newProgress) {
        String url = view.getUrl();

        if (newProgress == 100) {
            //网页加载完毕
            if (view.getTitle().length() > 0) {
                setTitle(view.getTitle());
            } else {
                setTitle(mContext.getString(R.string.app_name));
            }

        }else {
            //加载中
            if (getTitle() != null && view.getTitle().length() > 0) {
                setTitle(view.getTitle() + "...");
            } else {
                setTitle(mContext.getString(R.string.app_name));
            }
        }
    }

    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Logger.info(message);
        DialogUtil.getInstance(mContext).alert(mContext.getString(R.string.informationSuccess),message);
        result.cancel();
        return true;
    }
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return false;
    }

    private CharSequence getTitle(){
        return ((Activity)mContext).getTitle();
    }

    private void setTitle(CharSequence title){
        if (title.toString().indexOf(Config.CFG_API_URI) == -1){
            ((Activity)mContext).setTitle(title);
        }

    }


}
