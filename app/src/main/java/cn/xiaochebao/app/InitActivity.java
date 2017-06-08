package cn.xiaochebao.app;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import cn.xiaochebao.app.adapter.SlideAdapter;
import cn.xiaochebao.app.base.BaseCurl;
import cn.xiaochebao.app.config.Config;
import cn.xiaochebao.app.config.Constant;
import cn.xiaochebao.app.core.FrameApp;
import cn.xiaochebao.app.core.SDinject;
import cn.xiaochebao.app.interfaces.ViewInject;
import cn.xiaochebao.app.libs.HttpInterfaceServer;
import cn.xiaochebao.app.libs.HttpRequestHandler;
import cn.xiaochebao.app.libs.Logger;
import cn.xiaochebao.app.model.AdInfoModel;
import cn.xiaochebao.app.model.InitModel;
import cn.xiaochebao.app.service.UpgradeService;
import cn.xiaochebao.app.utils.DialogUtil;
import cn.xiaochebao.app.utils.HandlerUtil;
import cn.xiaochebao.app.utils.JsonUtils;
import cn.xiaochebao.app.utils.UtilsEx;

public class InitActivity extends AppCompatActivity {

    private Context mContext;
    private SlideAdapter mAdapter;
    private AdInfoModel adItemModel;

    @ViewInject(id = R.id.init_container)
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity_init);

        mContext = this;

        //注入ID
        SDinject.injectView(this);

        mAdapter = new SlideAdapter(getSupportFragmentManager());

        /**
         * 这里可以http读取广告内容
         */
        Map<String,Object> map = new HashMap<>();
        map.put("id",0);
        map.put("name", "v"+UtilsEx.getVersionName());
        map.put("url",Config.CFG_API_PRE+Config.CFG_API_URI);
        map.put("img","");

        adItemModel = new AdInfoModel();
        adItemModel = AdInfoModel.getInstance();
        adItemModel.bindModel(map);

        mAdapter.addAdItemModels(adItemModel);
        mViewPager.setAdapter(mAdapter);

        startUpgradeService();
        requestInitActInterFace();

    }

    /**
     * 启动检查更新服务
     */
    private void startUpgradeService() {
        Intent in = new Intent();
        in.setClass(this, UpgradeService.class);
        in.setAction(Constant.ActionName.UPGRADE_APP);
        startService(in);
    }

    /**
     * 启动初始化接口程序
     * 如果数据读取成功则关闭此窗口
     */
    private void requestInitActInterFace() {
        //http://xiaochebao.com/mapi/index.php?act=init&email=a123456&pwd=dc483e80a7a0bd9ef71d8cf973673924&r_type=2
        //starActivity();

        InitModel model = new InitModel();
        HttpInterfaceServer.getInstance(model, new HttpRequestHandler() {
            @Override
            public void onStart() {
                Logger.info("HttpRequestHandler:onStart");
            }

            @Override
            public void onProgress(long totalSize, long currentSize) {
                Logger.info("HttpRequestHandler:onProgress totalSize:"+totalSize+ " currentSize:" +currentSize);
            }

            @Override
            public void onSuccess(Map<String, Object> data, int code) {
                Logger.info("HttpRequestHandler:onSuccess");

                if(data != null){
                    Object object = JsonUtils.parseObject((Map<String, Object>) data.get(BaseCurl.response_data), InitModel.class);

                    /**
                     * 复制Model给APP,等下一个窗口出来后直接调用
                     */
                    FrameApp.setPrevModel((InitModel) object,InitModel.class.getName());
                }


            }

            @Override
            public void onError(Map<String, Object> data, int code) {
                Logger.info("HttpRequestHandler:onError");
                DialogUtil.getInstance(mContext).toast(data.get(BaseCurl.response_err).toString());
            }

            @Override
            public void onFinish() {
                Logger.info("HttpRequestHandler:onFinish");
                starActivity();
            }
        }).get();
    }

    private void starActivity(){
        HandlerUtil.runDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(InitActivity.this,MainActivity.class);
                InitActivity.this.startActivity(intent);
                InitActivity.this.finish();
            }
        },2000);
    }

}
