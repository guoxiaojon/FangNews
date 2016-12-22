package com.example.jon.fangnews.ui.zhihu.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.App;
import com.example.jon.fangnews.base.BaseActivity;
import com.example.jon.fangnews.component.ImageLoader;
import com.example.jon.fangnews.di.component.DaggerActivityComponent;
import com.example.jon.fangnews.di.module.ActivityModule;
import com.example.jon.fangnews.model.bean.ZhiHuThemeContentBean;
import com.example.jon.fangnews.presenter.ThemeContentPresenter;
import com.example.jon.fangnews.presenter.contract.ThemeContentContract;
import com.example.jon.fangnews.ui.zhihu.adapter.ThemeDetailAdapter;
import com.example.jon.fangnews.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by jon on 2016/12/12.
 */

public class ThemeActivity extends BaseActivity<ThemeContentPresenter> implements ThemeContentContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.sr_zhihu_theme_detail)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.appbarlayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.iv_zhihu_theme_detail_bg)
    ImageView mIVBackground;
    @BindView(R.id.iv_zhihu_theme_detail_blur)
    ImageView mBlurImage;
    @BindView(R.id.tv_zhihu_theme_detail_describe)
    TextView mTVDescribe;
    @BindView(R.id.rv_zhihu_theme_detail_list)
    RecyclerView mRVThemeList;
    @BindView(R.id.view_progress)
    ProgressBar mProgressBar;

    private List<ZhiHuThemeContentBean.StoryBean> mStories;
    private ThemeDetailAdapter mAdapter;

    private int id;



    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);

        mStories = new ArrayList<>();
        mAdapter = new ThemeDetailAdapter(mContext, mStories, new ThemeDetailAdapter.OnItemClickListener() {
            @Override
            public void OnClick(int id,int position) {
                mAdapter.setReaded(position);
                mAdapter.notifyItemChanged(position);
                mPresenter.insertReaded(id);
                Intent intent = new Intent(mContext,ZhiHuDetailActivity.class);
                intent.putExtra("id",id);
                mContext.startActivity(intent);
            }
        });
        mRVThemeList.setLayoutManager(new LinearLayoutManager(mContext));
        mRVThemeList.setAdapter(mAdapter);

        mPresenter.getContent(id);
        mProgressBar.setVisibility(View.VISIBLE);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getContent(id);
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset==0){
                    mRefreshLayout.setEnabled(true);
                }else {
                    mRefreshLayout.setEnabled(false);
                    float alpha = (SystemUtil.dp2px(mContext,260)+verticalOffset*2)/SystemUtil.dp2px(mContext,260);
                    Log.d("data","=====>>>"+alpha);
                    if(alpha >= 0)
                         mIVBackground.setAlpha(alpha);

                }


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
        return R.layout.activity_zhihu_theme_detail;
    }

    @Override
    public void showContent(ZhiHuThemeContentBean contentBean) {
        setToolBar(mToolBar,contentBean.getName());
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.GONE);
        } else{
            mRefreshLayout.setRefreshing(false);
        }
        ImageLoader.load(mContext,contentBean.getBackground(),mIVBackground);
        Glide.with(mContext).load(contentBean.getBackground()).bitmapTransform(new BlurTransformation(mContext)).into(mBlurImage);
        mTVDescribe.setText(contentBean.getDescription());
        mStories.clear();
        mStories.addAll(contentBean.getStories());
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void showError(String msg) {

    }

    @Override
    protected void onBackPressedSupport() {
        super.onBackPressedSupport();
        finish();
    }
}
