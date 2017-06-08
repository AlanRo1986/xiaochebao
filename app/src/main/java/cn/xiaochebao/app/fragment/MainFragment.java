package cn.xiaochebao.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import cn.xiaochebao.app.WebViewActivity;
import cn.xiaochebao.app.R;
import cn.xiaochebao.app.adapter.DealAdapter;
import cn.xiaochebao.app.adapter.SlideAdapter;
import cn.xiaochebao.app.base.BaseCurl;
import cn.xiaochebao.app.base.BaseFragment;
import cn.xiaochebao.app.config.Constant;
import cn.xiaochebao.app.core.FrameApp;
import cn.xiaochebao.app.core.SDinject;
import cn.xiaochebao.app.interfaces.OnDealListInteractionListener;
import cn.xiaochebao.app.interfaces.OnSlideClickListener;
import cn.xiaochebao.app.interfaces.ViewInject;
import cn.xiaochebao.app.libs.HttpRequestHandler;
import cn.xiaochebao.app.libs.Logger;
import cn.xiaochebao.app.model.DealInfoModel;
import cn.xiaochebao.app.model.InitModel;
import cn.xiaochebao.app.utils.DialogUtil;
import cn.xiaochebao.app.utils.JsonUtils;
import cn.xiaochebao.app.view.SlideView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainFragment extends BaseFragment {

    private Context mContext;
    private InitModel mInitModel;

    @ViewInject(id = R.id.frag_main_refresh)
    private SwipeRefreshLayout viewRefresh = null;

    @ViewInject(id = R.id.frag_main_recycler)
    private RecyclerView viewRecycler = null;

    @ViewInject(id = R.id.frag_main_viewFlipper)
    private SlideView viewFlipper = null;

    @ViewInject(id = R.id.frag_main_bottom_layout)
    private LinearLayout bottom_layout = null;

    @ViewInject(id = R.id.frag_main_tv_1)
    private TextView tv_1 = null;

    @ViewInject(id = R.id.frag_main_tv_2)
    private TextView tv_2 = null;

    @ViewInject(id = R.id.frag_main_tv_3)
    private TextView tv_3 = null;

    public MainFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main, container, false);

        SDinject.injectView(this,view);
        init();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void init() {
        mInitModel = (InitModel) FrameApp.getPrevModel(InitModel.class.getName());
        if(mInitModel instanceof Object == false || (mInitModel instanceof Object && mInitModel.getExpiredTime() == true)){
            initRequest();
        }else{
            bindModel();
        }

        viewRefresh.setColorSchemeColors(R.color.magenta);
        viewRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Logger.info("onRefresh");
                initRequest();
            }
        });

    }

    @Override
    public void bindModel() {
        if(mInitModel == null){
            return;
        }

        initTitle();
        initAdImage();
        initItem();
    }

    /**
     * 绑定首页的项目
     */
    private void initItem() {
        if(mInitModel.getIndexList().getDealList().size() == 0){
            return;
        }

        DealAdapter adapter = DealAdapter.getInstance(mInitModel.getIndexList().getDealList(), new OnDealListInteractionListener() {
            @Override
            public void onListInteraction(DealInfoModel item) {
                Logger.info(item.getName());
            }
        });
        viewRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        viewRecycler.setAdapter(adapter);
    }

    /**
     * 绑定文字类的对象
     */
    private void initTitle() {
        getActivity().setTitle(mInitModel.getProgramTitle());
        if(TextUtils.isEmpty(mInitModel.getVirtualMoney1())){
            bottom_layout.setVisibility(View.GONE);
        }else{
            tv_1.setText(mInitModel.getVirtualMoney1());
            tv_2.setText(mInitModel.getVirtualMoney2());
            tv_3.setText(mInitModel.getVirtualMoney3());
        }
    }

    /**
     * 绑定广告图片
     */
    private void initAdImage() {
        //如果有项目了,就不进行处理
        if (mInitModel.getIndexList().getAdvList().size() > 0 && viewFlipper.getCount() == 0){

            List<ImageView> viewList = new ArrayList<>();
            for (int i = 0; i < mInitModel.getIndexList().getAdvList().size();i++){
                ImageView image = new ImageView(mContext);
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                viewList.add(image);
                FrameApp.getImageLoader().showImage(mInitModel.getIndexList().getAdvList().get(i).getImage(),image);
            }
            viewFlipper.setImage(viewList);
            viewFlipper.setOnSlideClickListener(new OnSlideClickListener() {
                @Override
                public void onClick(int index) {
                    String uri = mInitModel.getIndexList().getAdvList().get(index).getData();
                    Logger.info(uri);

                    if (!TextUtils.isEmpty(uri) && uri.indexOf("://") != -1){
                        Intent intent = new Intent();
                        intent.setClass(mContext, WebViewActivity.class);
                        intent.putExtra(Constant.WEBBVIEW_URI_NAME,uri);
                        startActivity(intent);
                    }
                }
            });


        }
    }

    /**
     * 请求数据
     */
    private void initRequest(){

        mInitModel = new InitModel();

        mHttp.getInstance(mInitModel, new HttpRequestHandler() {
            @Override
            public void onStart() {
                viewRefresh.setRefreshing(true);
            }

            @Override
            public void onProgress(long totalSize, long currentSize) {

            }

            @Override
            public void onSuccess(Map<String, Object> data, int code) {
                Object object = JsonUtils.parseObject((Map<String, Object>) data.get(BaseCurl.response_data), InitModel.class);

                mInitModel = (InitModel) object;
                FrameApp.setPrevModel(mInitModel,InitModel.class.getName());

                bindModel();
            }

            @Override
            public void onError(Map<String, Object> data, int code) {
                DialogUtil.getInstance(mContext).toast(getString(R.string.inValidNetwork));
            }

            @Override
            public void onFinish() {
                viewRefresh.setRefreshing(false);
            }
        }).get();

    }

}
