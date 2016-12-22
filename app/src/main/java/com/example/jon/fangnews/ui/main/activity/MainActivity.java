package com.example.jon.fangnews.ui.main.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.App;
import com.example.jon.fangnews.app.Constants;
import com.example.jon.fangnews.base.BaseActivity;
import com.example.jon.fangnews.component.RxBus;
import com.example.jon.fangnews.di.component.DaggerActivityComponent;
import com.example.jon.fangnews.di.module.ActivityModule;
import com.example.jon.fangnews.model.bean.GankSearchEvent;
import com.example.jon.fangnews.model.bean.WeChatSearchEvent;
import com.example.jon.fangnews.presenter.MainPresenter;
import com.example.jon.fangnews.presenter.contract.MainContract;
import com.example.jon.fangnews.ui.gank.fragment.GankMainFragment;
import com.example.jon.fangnews.ui.main.fragment.AboutFragment;
import com.example.jon.fangnews.ui.main.fragment.LikeFragment;
import com.example.jon.fangnews.ui.main.fragment.SettingFragment;
import com.example.jon.fangnews.ui.wechat.fragment.WeChatMainFragment;
import com.example.jon.fangnews.ui.zhihu.fragment.ZhiHuMainFragment;
import com.example.jon.fangnews.utils.FragmentUtils;
import com.example.jon.fangnews.utils.SharePreferenceUtil;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.main_view_search)
    MaterialSearchView mSearchView;


    MenuItem mSearchMenuItem;
    MenuItem mLastItem;

    boolean isNull;


    ZhiHuMainFragment mZhiHuMainFragment;
    WeChatMainFragment mWeChatMainFragment;
    GankMainFragment mGankMainFragment;
    LikeFragment mLikeFragment;
    SettingFragment mSettingFragment;
    AboutFragment mAboutFragment;


    int mHideFragment = Constants.TYPE_ZHIHU;
    int mShowFragment = Constants.TYPE_ZHIHU;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNull = savedInstanceState == null;
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            SharePreferenceUtil.setNightMode(false);
        } else {

            mSettingFragment = (SettingFragment) getSupportFragmentManager().findFragmentByTag(SettingFragment.class.getSimpleName());
            mZhiHuMainFragment = (ZhiHuMainFragment) getSupportFragmentManager().findFragmentByTag(ZhiHuMainFragment.class.getSimpleName());
            mWeChatMainFragment = (WeChatMainFragment) getSupportFragmentManager().findFragmentByTag(WeChatMainFragment.class.getSimpleName());
            mGankMainFragment = (GankMainFragment) getSupportFragmentManager().findFragmentByTag(GankMainFragment.class.getSimpleName());
            mLikeFragment = (LikeFragment)getSupportFragmentManager().findFragmentByTag(LikeFragment.class.getSimpleName());
            mAboutFragment = (AboutFragment)getSupportFragmentManager().findFragmentByTag(AboutFragment.class.getSimpleName());
            mShowFragment = SharePreferenceUtil.getCurrentItem();
            mNavigationView.getMenu().findItem(R.id.nav_zhihu).setChecked(false);
            mNavigationView.getMenu().findItem(getCurrentItem(mShowFragment)).setChecked(true);
            mToolBar.setTitle(mNavigationView.getMenu().findItem(getCurrentItem(mShowFragment)).getTitle().toString());

            mHideFragment = mShowFragment;


        }
        //内存重启恢复夜间模式
        mSearchView.post(new Runnable() {
            @Override
            public void run() {
                Log.d("data","是不是夜间模式 ："+SharePreferenceUtil.getNightMode());
                Log.d("data","夜间模式辅助变量"+SharePreferenceUtil.IsNightSetByEvent());
                if( SharePreferenceUtil.getNightMode() && !SharePreferenceUtil.IsNightSetByEvent()){
                    SharePreferenceUtil.setNightSetByEvent(true);
                    useNightMode(true);


                }else {
                    SharePreferenceUtil.setNightSetByEvent(false);
                }
            }
        });


    }



    @Override
    protected void onBackPressedSupport() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            showExitDialog();
        }
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出FangNews吗");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                App.getInstance().exitApp();
            }
        });
        builder.show();
    }

    private int getCurrentItem(int item) {
        switch (item) {
            case Constants.TYPE_ZHIHU:
                return R.id.nav_zhihu;
            case Constants.TYPE_WECHAT:
                return R.id.nav_wechat;
            case Constants.TYPE_GANK:
                return R.id.nav_gank;
            case Constants.TYPE_LIKE:
                return R.id.nav_like;
            case Constants.TYPE_SETTING:
                return R.id.nav_setting;
            case Constants.TYPE_ABOUT:
                return R.id.nav_about;
        }
        return R.id.nav_about;
    }


    private Fragment getTargetFragment(int id) {
        switch (id) {
            case Constants.TYPE_ZHIHU:
                return mZhiHuMainFragment;
            case Constants.TYPE_WECHAT:
                return mWeChatMainFragment;
            case Constants.TYPE_GANK:
                return mGankMainFragment;
            case Constants.TYPE_LIKE:
                return mLikeFragment;
            case Constants.TYPE_SETTING:
                return mSettingFragment;
            case Constants.TYPE_ABOUT:
                return mAboutFragment;
        }
        return mZhiHuMainFragment;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(mToolBar, "知乎日报");
        if (isNull) {
            mZhiHuMainFragment = new ZhiHuMainFragment();
            mWeChatMainFragment = new WeChatMainFragment();
            mGankMainFragment = new GankMainFragment();
            mLikeFragment = new LikeFragment();
            mSettingFragment = new SettingFragment();
            mAboutFragment = new AboutFragment();
            mLastItem = mNavigationView.getMenu().findItem(R.id.nav_zhihu);
            FragmentUtils.loadMultipleFragment(
                    R.id.fl_main_container,
                    0,//显示第0个
                    getSupportFragmentManager(),
                    mZhiHuMainFragment, mWeChatMainFragment, mGankMainFragment, mLikeFragment, mSettingFragment, mAboutFragment);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (mShowFragment == Constants.TYPE_WECHAT) {
                    RxBus.getDefault().post(new WeChatSearchEvent(query));
                } else if (mShowFragment == Constants.TYPE_GANK) {
                    GankSearchEvent event = new GankSearchEvent();
                    event.setQuery(query);
                    ((GankMainFragment) getTargetFragment(mShowFragment)).doSearch(event);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(App.getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            onBackPressedSupport();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        if(mShowFragment == Constants.TYPE_WECHAT || mShowFragment == Constants.TYPE_GANK){
            item.setVisible(true);
        }
        mSearchView.setMenuItem(item); //支持MaterialSearchView;
        mSearchMenuItem = item;

        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_zhihu) {
            mShowFragment = Constants.TYPE_ZHIHU;
            mSearchMenuItem.setVisible(false);
            // Handle the camera action
        } else if (id == R.id.nav_wechat) {
            mShowFragment = Constants.TYPE_WECHAT;
            mSearchMenuItem.setVisible(true);

        } else if (id == R.id.nav_gank) {
            mShowFragment = Constants.TYPE_GANK;
            mSearchMenuItem.setVisible(true);

        } else if (id == R.id.nav_like) {
            mShowFragment = Constants.TYPE_LIKE;
            mSearchMenuItem.setVisible(false);

        } else if (id == R.id.nav_setting) {
            mShowFragment = Constants.TYPE_SETTING;
            mSearchMenuItem.setVisible(false);

        } else if (id == R.id.nav_about) {
            mShowFragment = Constants.TYPE_ABOUT;
            mSearchMenuItem.setVisible(false);

        }
        if (mLastItem != null)
            mLastItem.setChecked(false);
        mLastItem = item;
        mLastItem.setChecked(true);
        mToolBar.setTitle(mLastItem.getTitle());
        mDrawerLayout.closeDrawer(GravityCompat.START);
        SharePreferenceUtil.setCurrentItem(mShowFragment);
        Log.d("data","showFr"+getTargetFragment(mShowFragment));
        Log.d("data","hidFr"+getTargetFragment(mHideFragment));
        FragmentUtils.showAndHideFragment(getSupportFragmentManager(), getTargetFragment(mShowFragment), getTargetFragment(mHideFragment));
        mHideFragment = mShowFragment;

        return true;
    }

    @Override
    public void showError(String msg) {

    }


}
