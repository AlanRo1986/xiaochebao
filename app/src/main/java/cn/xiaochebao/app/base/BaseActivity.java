package cn.xiaochebao.app.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.xiaochebao.app.utils.DialogUtil;

/**
 * Created by Alan on 2016/08/11 0011.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public DialogUtil mDialogUtil;
    public Dialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBase();
    }

    private void initBase() {
        mDialogUtil = new DialogUtil(this);

    }

    public void addFragment(BaseFragment fragment, int containerId) {
        getSupportFragmentManager().beginTransaction().add(containerId, fragment).commitAllowingStateLoss();
    }

    public void replaceFragment(BaseFragment fragment, int containerId) {
        getSupportFragmentManager().beginTransaction().replace(containerId, fragment).commitAllowingStateLoss();
    }

    public void showFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().show(fragment).commitAllowingStateLoss();
        fragment.onShowView();
    }

    public void hideFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().hide(fragment).commitAllowingStateLoss();
        fragment.onHideView();
    }

    public void showLoadingDialog(String msg) {
        if (msg != null) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                mDialog = null;
            }

            mDialog = mDialogUtil.showLoading(msg,true);
        }
    }

    public void hideLoadingDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

}
