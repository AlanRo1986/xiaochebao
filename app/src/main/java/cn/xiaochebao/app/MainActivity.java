package cn.xiaochebao.app;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import cn.xiaochebao.app.base.BaseFragment;
import cn.xiaochebao.app.event.OpentionItemSelectedEvent;
import cn.xiaochebao.app.fragment.DealsFragment;
import cn.xiaochebao.app.fragment.MainFragment;
import cn.xiaochebao.app.base.BaseActivity;
import cn.xiaochebao.app.libs.Logger;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Context mContext;
    private BaseFragment mFragment = null;
    private BaseFragment mFragLast = null;

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity_main);

        mContext = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        //mToolbar.setLogo(R.mipmap.logo);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        init();
        StartFragment(new MainFragment());
    }

    private void init() {

    }


    /**
     * 打开碎片
     * @param fragemt
     */
    public void StartFragment(BaseFragment fragemt) {
        mFragment = fragemt;
        replaceFragment(mFragment,R.id.main_ctl_frame);
        showFragment(mFragment);

        if (mFragLast != null)
            hideFragment(mFragLast);

        mFragLast = mFragment;
    }


    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //Handover the OptionsItemSelected Event.
        OpentionItemSelectedEvent.getInstance(mContext).OnClick(item);

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_home:
                StartFragment(new MainFragment());
                break;
            case R.id.nav_deals:
                StartFragment(new DealsFragment());
                break;
            case R.id.nav_mall:
                Logger.info("积分商城");
                break;
            case R.id.nav_ucenter:
                Logger.info("用户中心");
                break;
            case R.id.nav_notice:
                Logger.info("最新动态");
                break;
            case R.id.nav_about:
                Logger.info("关于我们");
                break;
            case R.id.nav_calc:
                Logger.info("计算器");
                break;
            default:
                Logger.info("Not find the id.");
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
