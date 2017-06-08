package cn.xiaochebao.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import cn.xiaochebao.app.config.Constant;
import cn.xiaochebao.app.core.SDinject;
import cn.xiaochebao.app.interfaces.ViewInject;
import cn.xiaochebao.app.libs.Logger;
import cn.xiaochebao.app.libs.WebChromeClientServer;
import cn.xiaochebao.app.libs.WebViewClientServer;

public class WebViewActivity extends AppCompatActivity {

    private Context mContext;

    @ViewInject(id = R.id.webView)
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity_webview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.webView_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.app_ic_menu_prev);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SDinject.injectView(this);
        mContext = this;
        init();
    }

    private void init() {
        Intent intent = getIntent();
        String uri = intent.getStringExtra(Constant.WEBBVIEW_URI_NAME);
        Logger.info(uri);

        if(TextUtils.isEmpty(uri)){
            finish();
            return;
        }

        mWebView.loadUrl(uri);

        WebSettings webSettings =  mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebView.setWebViewClient(WebViewClientServer.getInstance(mContext));
        mWebView.setWebChromeClient(WebChromeClientServer.getInstance(mContext));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
