package cn.xiaochebao.app.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import cn.xiaochebao.app.R;
import cn.xiaochebao.app.adapter.DealAdapter;
import cn.xiaochebao.app.base.BaseCurl;
import cn.xiaochebao.app.base.BaseFragment;
import cn.xiaochebao.app.config.Constant;
import cn.xiaochebao.app.core.FrameApp;
import cn.xiaochebao.app.core.SDinject;
import cn.xiaochebao.app.interfaces.OnDealListInteractionListener;
import cn.xiaochebao.app.interfaces.ViewInject;
import cn.xiaochebao.app.libs.HttpRequestHandler;
import cn.xiaochebao.app.libs.Logger;
import cn.xiaochebao.app.model.DealInfoModel;
import cn.xiaochebao.app.model.DealsModel;
import cn.xiaochebao.app.model.InitModel;
import cn.xiaochebao.app.utils.DialogUtil;
import cn.xiaochebao.app.utils.JsonUtils;

/**
 * Created by Administrator on 2017/04/26 0026.
 */
public class DealsFragment extends BaseFragment {

    @ViewInject(id = R.id.frag_deals_recycler)
    private RecyclerView viewRecycler = null;

    @ViewInject(id = R.id.frag_deals_refresh)
    private SwipeRefreshLayout viewRefresh = null;

    private DealsModel mDealsModel;
    private Context mContext;
    private DealAdapter mAdapter;

    public DealsFragment(){

    }

    public static DealsFragment newInstance(int id){
        DealsFragment fragment = new DealsFragment();

        Bundle args = new Bundle();
        args.putInt(Constant.FRAG_ARGS_NAME_ID, id);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_deals, container, false);

        SDinject.injectView(this,view);
        init();
        return view;
    }

    public void init() {
        mDealsModel = (DealsModel) FrameApp.getPrevModel(DealsModel.class.getName());
        if(mDealsModel instanceof Object == false || (mDealsModel instanceof Object && mDealsModel.getExpiredTime() == true)){
            initRequest(false);
        }else{
            bindModel();
        }

        viewRefresh.setColorSchemeColors(R.color.magenta);
        viewRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRequest(true);
            }
        });



    }

    private void initRequest(final boolean isRefresh) {
        if(mDealsModel == null){
            mDealsModel = new DealsModel();
        }else {
            //设置参数,等待下一次发送请求时直接发送请求
            mDealsModel.setPage(mDealsModel.getPage() + 1);
            mDealsModel.setParams("page="+mDealsModel.getPage());
            if(mDealsModel.getPageObj().getPageTotal() < mDealsModel.getPage()){
                DialogUtil.getInstance(mContext).toast(getString(R.string.no_more));
                viewRefresh.setRefreshing(false);
                return;
            }
        }

        mHttp.getInstance(mDealsModel, new HttpRequestHandler() {
            @Override
            public void onStart() {
                viewRefresh.setRefreshing(true);
            }

            @Override
            public void onProgress(long totalSize, long currentSize) {

            }

            @Override
            public void onSuccess(Map<String, Object> data, int code) {

                Object object = JsonUtils.parseObject((Map<String, Object>) data.get(BaseCurl.response_data), DealsModel.class);

                mDealsModel = (DealsModel) object;
                if(FrameApp.getPrevModel(DealsModel.class.getName()) != null && isRefresh == false){
                    ((DealsModel)FrameApp.getPrevModel(DealsModel.class.getName())).addItemAll(mDealsModel.getItem());
                    ((DealsModel)FrameApp.getPrevModel(DealsModel.class.getName())).setPage(mDealsModel.getPageObj());
                    mAdapter.notifyDataSetChanged();
                }else {
                    FrameApp.setPrevModel(mDealsModel,DealsModel.class.getName());
                    bindModel();
                }

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

    @Override
    public void bindModel() {

        if (mDealsModel == null){
            return;
        }

        initTitle();
        initItem();
    }

    private void initItem() {
        if(mDealsModel.getItem().size() == 0){
            return;
        }

        Logger.info(mDealsModel.getItem().get(0).getId());
        mAdapter = DealAdapter.getInstance(mDealsModel.getItem(), new OnDealListInteractionListener() {
            @Override
            public void onListInteraction(DealInfoModel item) {
                Logger.info(item.getName());
            }
        });

        viewRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        viewRecycler.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        viewRecycler.setHasFixedSize(true);
        viewRecycler.setLayoutManager(layoutManager);

    }

    private void initTitle() {
        getActivity().setTitle(mDealsModel.getProgramTitle());

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
