package com.example.jon.fangnews.ui.wechat.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.App;
import com.example.jon.fangnews.base.SimpleActivity;
import com.example.jon.fangnews.di.component.DaggerActivityComponent;
import com.example.jon.fangnews.di.module.ActivityModule;
import com.example.jon.fangnews.model.bean.RealmLikeBean;
import com.example.jon.fangnews.model.db.RealmHelper;
import com.example.jon.fangnews.utils.SharePreferenceUtil;
import com.example.jon.fangnews.utils.ShareUtil;
import com.example.jon.fangnews.utils.SystemUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import javax.inject.Inject;

import butterknife.BindView;

import static android.view.View.GONE;

/**
 * Created by jon on 2016/12/14.
 */

public class WGDetailActivity extends SimpleActivity{
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.wv_wg_detail_content)
    WebView mWVContent;
    @BindView(R.id.tv_wg_progress)
    TextView mTVProgress;

    private MenuItem mLikeMenuItem;
    private boolean mIsLike;
    private String mUrl;
    private int mType;
    private String mId;
    private String mTitle;//微信用

    @Inject
    RealmHelper mRealmHelper;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_wg_detail;
    }

    @Override
    protected void initEventAndDate() {
        DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mType = intent.getIntExtra("type",-1);
        mId = intent.getStringExtra("id");
        mTitle = intent.getStringExtra("title");
        mIsLike = mRealmHelper.queryLikeBean(mId);

        setToolBar(mToolbar,mTitle);

        WebSettings settings = mWVContent.getSettings();
        if(SharePreferenceUtil.getNoImage()){
            settings.setBlockNetworkImage(false);
        }
        if(SharePreferenceUtil.getAutoCache()){
            settings.setAppCacheEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);
            if(!SystemUtil.isNetAvailable()){
                settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            }else {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            }
        }

        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWVContent.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        mWVContent.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if(mTVProgress == null)
                    return;
                if(i == 100){
                    mTVProgress.setVisibility(GONE);
                }else {
                    mTVProgress.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = mTVProgress.getLayoutParams();
                    params.width = (int)(App.SCREEN_WIDTH * i/100.0f);
                }
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                if(mToolbar != null){
                    setToolBar(mToolbar,s);
                }

            }
        });


            mWVContent.loadUrl(mUrl);


    }

    @Override
    protected void onSupportBacKPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }else {
            super.onSupportBacKPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_wg,menu);
        mLikeMenuItem = menu.findItem(R.id.aciton_like);
        setLikedState(mRealmHelper.queryLikeBean(mId));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.aciton_like:
                if(mIsLike){
                    mIsLike = false;
                    setLikedState(mIsLike);
                    mRealmHelper.deteleLikeBean(mId);
                }else {
                    mIsLike = true;
                    setLikedState(mIsLike);
                    RealmLikeBean likeBean = new RealmLikeBean();
                    likeBean.setType(mType);
                    likeBean.setId(mId);
                    likeBean.setTime(System.currentTimeMillis());
                    likeBean.setImage(mUrl);
                    likeBean.setTitle(mTitle);
                    mRealmHelper.insertLikeBean(likeBean);
                }
                break;
            case R.id.aciton_copy:
                SystemUtil.copyToClip(mContext,mUrl);
                break;
            case R.id.aciton_share:
                ShareUtil.shareText(mContext,mUrl,"分享~");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setLikedState(boolean like){
        if(like){
            mLikeMenuItem.setIcon(R.mipmap.ic_toolbar_like_p);
        }else {
            mLikeMenuItem.setIcon(R.mipmap.ic_toolbar_like_n);
        }

    }



}
