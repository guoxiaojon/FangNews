package com.example.jon.fangnews.ui.gank.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.Constants;
import com.example.jon.fangnews.base.BaseFragment;
import com.example.jon.fangnews.model.bean.GankItemBean;
import com.example.jon.fangnews.presenter.GankOtherPresenter;
import com.example.jon.fangnews.presenter.contract.GankOtherContract;
import com.example.jon.fangnews.ui.gank.activity.GankGirlDetailActivity;
import com.example.jon.fangnews.ui.gank.adapter.GankGirlAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.view.View.GONE;

/**
 * Created by jon on 2016/12/15.
 */

public class GankGirlFragment extends BaseFragment<GankOtherPresenter> implements GankOtherContract.View {
    @BindView(R.id.rv_gank_girl)
    RecyclerView mRVGirlList;
    @BindView(R.id.sr_gank_girl)
    SwipeRefreshLayout mSRefreshLayout;
    @BindView(R.id.view_progress)
    ProgressBar mProgressBar;

    private List<GankItemBean> mList;
    private GankGirlAdapter mAdapter;
    private boolean mIsLoadingMore;

    @Override
    protected void initInject() {
        getDaggerFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_girl;
    }

    @Override
    protected void initEventAndData() {
        mList = new ArrayList<>();
        mAdapter = new GankGirlAdapter(mList, new GankGirlAdapter.OnItemClickListener() {
            @Override
            public void onClick(GankItemBean bean, View shareView) {
                Intent intent = new Intent(mContext, GankGirlDetailActivity.class);
                intent.putExtra("url",bean.getUrl());
                intent.putExtra("id",bean.get_id());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options  = options = ActivityOptions.makeSceneTransitionAnimation(mActivity,shareView,"shareView");
                    mContext.startActivity(intent,options.toBundle());
                }else {
                    mContext.startActivity(intent);
                }
            }
        }, mContext);

        mRVGirlList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(mIsLoadingMore)
                    return;
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) mRVGirlList.getLayoutManager();
                int[] lastPosition = new int[2];
                manager.findLastVisibleItemPositions(lastPosition);
                int maxPosition = lastPosition[0]>lastPosition[1]?lastPosition[0]:lastPosition[1];
                if(maxPosition >= manager.getItemCount() - 6){
                    mPresenter.getGankMoreData(Constants.TYPE_GIRL);
                    mIsLoadingMore = true;
                }


            }
        });

        mSRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.clearSearch();
                mPresenter.getGankData(Constants.TYPE_GIRL);

            }
        });
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //解决重排问题
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        mRVGirlList.setAdapter(mAdapter);
        mRVGirlList.setLayoutManager(manager);
        mProgressBar.setVisibility(View.VISIBLE);
        mPresenter.getGankData(Constants.TYPE_GIRL);

    }

    @Override
    public void showContent(List<GankItemBean> gankItemBean) {
        mRVGirlList.smoothScrollToPosition(0);
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(GONE);
        }else {

            mSRefreshLayout.setRefreshing(false);
        }
        mList.clear();
        mList.addAll(gankItemBean);
        mAdapter.notifyDataSetChanged();
        mIsLoadingMore = false;

    }

    @Override
    public void showMoreContent(List<GankItemBean> gankItemBean) {
        mList.addAll(gankItemBean);
        mAdapter.notifyDataSetChanged();
        mIsLoadingMore = false;
    }

    @Override
    public void showGirl(List<GankItemBean> gankItemBeen) {
        //此处用不着
    }

    @Override
    public void showProgress() {
        if(!mSRefreshLayout.isRefreshing())
            mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String msg) {
        mProgressBar.setVisibility(GONE);
        mSRefreshLayout.setRefreshing(false);
        Snackbar.make(mActivity.getWindow().getDecorView(),msg,Snackbar.LENGTH_LONG).show();
    }
}
