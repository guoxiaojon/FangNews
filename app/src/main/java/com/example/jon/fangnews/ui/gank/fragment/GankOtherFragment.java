package com.example.jon.fangnews.ui.gank.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.jon.fangnews.R;
import com.example.jon.fangnews.base.BaseFragment;
import com.example.jon.fangnews.component.ImageLoader;
import com.example.jon.fangnews.model.bean.GankItemBean;
import com.example.jon.fangnews.presenter.GankOtherPresenter;
import com.example.jon.fangnews.presenter.contract.GankOtherContract;
import com.example.jon.fangnews.ui.gank.adapter.GankOtherAdapter;
import com.example.jon.fangnews.ui.wechat.activity.WGDetailActivity;
import com.example.jon.fangnews.utils.SystemUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static android.view.View.GONE;

/**
 * Created by jon on 2016/12/15.
 */

public class GankOtherFragment extends BaseFragment<GankOtherPresenter> implements GankOtherContract.View {
    @BindView(R.id.appbarlayout_gank_other)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.sr_gank_other)
    SwipeRefreshLayout mSRefreshLayout;
    @BindView(R.id.iv_gank_other_bg)
    ImageView mIVbackground;
    @BindView(R.id.iv_gank_other_bulr)
    ImageView mIVBlur;
    @BindView(R.id.rv_gank_other)
    RecyclerView mRVList;
    @BindView(R.id.view_progress)
    ProgressBar mProgressBar;

    private int mType;
    private boolean mIsLoadingMore;

    private List<GankItemBean> mList;
    private GankOtherAdapter mAdapter;

    @Override
    protected void initInject() {
        getDaggerFragmentComponent().inject(this);
    }

    public int getType(){
        return mType;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_other;
    }

    @Override
    protected void initEventAndData() {
        mType = getArguments().getInt("type");
        mList = new ArrayList<>();
        mAdapter = new GankOtherAdapter(mType, mContext, mList, new GankOtherAdapter.OnItemClickListener() {
            @Override
            public void onClick(GankItemBean bean, View shareView) {
                Intent intent = new Intent(mContext, WGDetailActivity.class);
                intent.putExtra("id",bean.get_id());
                intent.putExtra("type",mType);
                intent.putExtra("title",bean.getDesc());
                intent.putExtra("url",bean.getUrl());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity,shareView,"shareView");
                    mContext.startActivity(intent,options.toBundle());
                }else {
                    mContext.startActivity(intent);
                }
            }
        });
        mRVList.setLayoutManager(new LinearLayoutManager(mContext));
        mRVList.setAdapter(mAdapter);
        mRVList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("data","准备加载更多"+mIsLoadingMore);
                if(mIsLoadingMore)
                    return;
                LinearLayoutManager manager = (LinearLayoutManager)recyclerView.getLayoutManager();
                Log.d("data", manager.findLastVisibleItemPosition()+": "+manager.getItemCount());
                if( manager.findLastVisibleItemPosition() >= manager.getItemCount()-1){
                    mPresenter.getGankMoreData(mType);
                    mIsLoadingMore = true;
                }

            }
        });
        mSRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.clearSearch();
                mPresenter.getRandomGirl();
                mPresenter.getGankData(mType);
            }
        });

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset==0){
                    mSRefreshLayout.setEnabled(true);
                }else {
                    mSRefreshLayout.setEnabled(false);
                    float alpha = (SystemUtil.dp2px(mContext,240)+verticalOffset*2)/SystemUtil.dp2px(mContext,240);
                    if(alpha >= 0)
                        mIVbackground.setAlpha(alpha);

                }


            }
        });


        mProgressBar.setVisibility(View.VISIBLE);
        mPresenter.getRandomGirl();
        mPresenter.getGankData(mType);

    }

    @Override
    public void showContent(List<GankItemBean> gankItemBean) {
        mAdapter.resetLoading();
        mRVList.smoothScrollToPosition(0);
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(GONE);
        }else {
            mSRefreshLayout.setRefreshing(false);
        }

        mIsLoadingMore = false;
        mList.clear();
        mList.addAll(gankItemBean);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void showMoreContent(List<GankItemBean> gankItemBean) {
        Log.d("data","showMoreContent");
        mList.addAll(gankItemBean);
        mAdapter.notifyDataSetChanged();
        mIsLoadingMore = false;
    }

    @Override
    public void showGirl(List<GankItemBean> gankItemBeen) {
        ImageLoader.load(mContext,gankItemBeen.get(0).getUrl(),mIVbackground);
        Glide.with(mContext).load(gankItemBeen.get(0).getUrl()).bitmapTransform(new BlurTransformation(mContext)).into(mIVBlur);

    }

    @Override
    public void showProgress() {
        if(!mSRefreshLayout.isRefreshing())
           mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String msg) {
        //mAdapter.resetLoading();
        mAdapter.removeLoading();
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(GONE);
        }else {
            mSRefreshLayout.setRefreshing(false);
        }
        Logger.e(msg);


    }
}
