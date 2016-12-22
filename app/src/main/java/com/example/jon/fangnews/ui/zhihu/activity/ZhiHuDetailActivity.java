package com.example.jon.fangnews.ui.zhihu.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.App;
import com.example.jon.fangnews.base.BaseActivity;
import com.example.jon.fangnews.component.ImageLoader;
import com.example.jon.fangnews.di.component.DaggerActivityComponent;
import com.example.jon.fangnews.di.module.ActivityModule;
import com.example.jon.fangnews.model.bean.ZhiHuDetailBean;
import com.example.jon.fangnews.model.bean.ZhiHuDetailExtraBean;
import com.example.jon.fangnews.presenter.ZhiHuDetailPresenter;
import com.example.jon.fangnews.presenter.contract.ZhiHuDetailContract;
import com.example.jon.fangnews.utils.HtmlUtil;
import com.example.jon.fangnews.utils.SharePreferenceUtil;
import com.example.jon.fangnews.utils.ShareUtil;
import com.example.jon.fangnews.utils.SystemUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.GONE;

/**
 * Created by jon on 2016/12/8.
 */

public class ZhiHuDetailActivity extends BaseActivity<ZhiHuDetailPresenter> implements ZhiHuDetailContract.View {

    @BindView(R.id.ctl_detail_barscollcontainer)
    CollapsingToolbarLayout mCTLScollContainer;
    @BindView(R.id.iv_detail_bar_image)
    ImageView mIVDetailBarImage;
    @BindView(R.id.tv_detail_bar_copyright)
    TextView mTVDetailBarCopyRight;
    @BindView(R.id.detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fal_detail_like)
    FloatingActionButton mFalLikeButton;
    @BindView(R.id.nsv_detail_scoller)
    NestedScrollView mNSVScroller;
    @BindView(R.id.wv_detail_content)
    WebView mWVContent;
    @BindView(R.id.fl_detail_bottom)
    FrameLayout mFLBottomContainer;
    @BindView(R.id.tv_detail_bottom_like)
    TextView mTVBottomLikes;
    @BindView(R.id.tv_detail_bottom_comment)
    TextView mTVBottomComments;
    @BindView(R.id.tv_detail_bottom_share)
    TextView mTVBottomShare;
    @BindView(R.id.view_progress)
    ProgressBar mProgressBar;


    private boolean isAnimating = false;
    private int id;
    private boolean isTransparent = false;


    private String mImgUrl;
    private String mShareUrl;

    private int mNumOfLongComment;
    private int mNumOfShortComment;
    private int mNumOfComment;



    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        id  =intent.getIntExtra("id",-1);
        isTransparent = intent.getBooleanExtra("isTransparent",false);
        setToolBar(mToolbar,"");
        mNSVScroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //向下滑动，显示
                if(isAnimating) return;
                isAnimating = true;
                if(scrollY - oldScrollY >0 ){

                    mFLBottomContainer.animate().translationY(mFLBottomContainer.getHeight()).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isAnimating = false;
                        }
                    });

                }else {
                    mFLBottomContainer.animate().translationY(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isAnimating = false;
                        }
                    });


                }

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    ImageLoader.load(mContext,mImgUrl,mIVDetailBarImage);
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }

        WebSettings settings = mWVContent.getSettings();
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
        if(SharePreferenceUtil.getNoImage()){
            settings.setBlockNetworkImage(false);
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


        mProgressBar.setVisibility(View.VISIBLE);
        mPresenter.getDetailData(id);
        mPresenter.getDetailExtraData(id);
        mPresenter.queryLikeData(id);


    }

    @Override
    public void onBackPressed() {
        if(mWVContent.canGoBack()){
            mWVContent.goBack();
        }else {
            super.onBackPressed();
        }

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
        return R.layout.activity_zhihu_detail;
    }

    @Override
    protected void onBackPressedSupport() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }else {
            finish();
        }
    }

    @Override
    public void showError(String msg) {
        mProgressBar.setVisibility(GONE);
        Snackbar.make(getWindow().getDecorView(),msg,Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void showContent(ZhiHuDetailBean detailBean) {
        mProgressBar.setVisibility(GONE);
        mImgUrl = detailBean.getImage();
        mShareUrl = detailBean.getShare_url();
        if(!isTransparent) {
            ImageLoader.load(this,detailBean.getImage(),mIVDetailBarImage);
        }

        mCTLScollContainer.setTitle(detailBean.getTitle());
        mTVDetailBarCopyRight.setText(detailBean.getImage_source());
        mWVContent.loadData(HtmlUtil.createHtmlDate(detailBean.getBody(),detailBean.getCss(),detailBean.getJs()),HtmlUtil.MIME_TYPE,HtmlUtil.ENCODING);

    }

    @Override
    public void showExtraContent(ZhiHuDetailExtraBean detailExtraBean) {
        mProgressBar.setVisibility(GONE);
        mNumOfLongComment = detailExtraBean.getLong_comments();
        mNumOfShortComment = detailExtraBean.getShort_comments();
        mNumOfComment = detailExtraBean.getComments();
        mTVBottomLikes.setText(String.format("%d个赞",detailExtraBean.getPopularity()));
        mTVBottomComments.setText(String.format("%d条评论",detailExtraBean.getComments()));
    }

    @Override
    public void setLikeButtonState(boolean like) {
        mFalLikeButton.setSelected(like);
    }

    @OnClick(R.id.fal_detail_like)
    public void setLike(){
        if(mFalLikeButton.isSelected()){
            mFalLikeButton.setSelected(false);
            mPresenter.deleteLikeData();
        }else {
            mFalLikeButton.setSelected(true);
            mPresenter.insertLikeData();
        }
    }

    @OnClick(R.id.tv_detail_bottom_share)
    public void shareText(){
        ShareUtil.shareText(mContext,mShareUrl,"分享~");
    }
    @OnClick(R.id.tv_detail_bottom_comment)
    public void gotoCommentActivity(){
        Intent intent = new Intent(mContext,CommentActivity.class);
        intent.putExtra("longComment",mNumOfLongComment);
        intent.putExtra("shortComment",mNumOfShortComment);
        intent.putExtra("comment",mNumOfComment);
        intent.putExtra("id",id);
        startActivity(intent);
    }

}
