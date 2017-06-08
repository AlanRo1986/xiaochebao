package cn.xiaochebao.app.base;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.xiaochebao.app.R;
import cn.xiaochebao.app.libs.HttpInterfaceServer;
import cn.xiaochebao.app.libs.Logger;
import cn.xiaochebao.app.utils.DialogUtil;

/**
 * 基础碎片类
 * Created by Alan on 2016/08/11 0011.
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 全局对话框
     */
    public DialogUtil mDialog;
    public HttpInterfaceServer mHttp;

    public BaseFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaseFragment();
    }

    public abstract void bindModel();

    /**
     * 子类必须实现OnCreateView方法,用于构造显示对象
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 初始化碎片
     */
    private void initBaseFragment() {
        mDialog = getBaseActivity().mDialogUtil;
    }

    public void onConnect(int type) {

    }

    public void onDisConnect() {

    }

    /**
     * 显示加载框
     * @param msg
     */
    public void showLoadingDialog(String msg) {
        BaseActivity activity = getBaseActivity();
        if (msg == null){
            msg = getString(R.string.wait);
        }
        if (activity != null) {
            activity.showLoadingDialog(msg);
        }
    }

    /**
     * 隐藏加载框
     */
    public void hideLoadingDialog(boolean isInterrupt) {
        BaseActivity activity = getBaseActivity();
        if (activity != null) {
            activity.hideLoadingDialog();
        }
        if (mHttp != null && isInterrupt == true){
            mHttp.doInterrupt(true);
        }
    }

    /**
     * 显示View方法
     */
    public void onShowView() {
        // TODO Auto-generated method stub
        Logger.info("OnShowView");
    }

    /**
     * 隐藏View方法
     */
    public void onHideView() {
        // TODO Auto-generated method stub
        Logger.info("onHideView");

    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
