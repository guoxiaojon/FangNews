package com.example.jon.fangnews.ui.wechat.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.Constants;
import com.example.jon.fangnews.base.BaseFragment;
import com.example.jon.fangnews.model.bean.WeChatBean;
import com.example.jon.fangnews.presenter.WeChatPresenter;
import com.example.jon.fangnews.presenter.contract.WeChatContract;
import com.example.jon.fangnews.ui.wechat.activity.WGDetailActivity;
import com.example.jon.fangnews.ui.wechat.adapter.WeChatAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.view.View.GONE;

/**
 * Created by jon on 2016/12/6.
 */

public class WeChatMainFragment extends BaseFragment<WeChatPresenter> implements WeChatContract.View{
    @BindView(R.id.rv_wechat_list)
    RecyclerView mRVWeChatList;
    @BindView(R.id.view_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.sr_wechat_refresh)
    SwipeRefreshLayout mSRefreshLayout;


    private List<WeChatBean> mList;
    private WeChatAdapter mAdapter;

    @Override
    protected void initInject() {
        getDaggerFragmentComponent().inject(this);
    }

    private boolean mLoadingMore;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wechat;
    }

    @Override
    protected void initEventAndData() {
        mList = new ArrayList<>();
        mAdapter = new WeChatAdapter(mList, mContext, new WeChatAdapter.OnItemClickListener() {
            @Override
            public void onClick(WeChatBean weChatBean,View shareView) {
                Intent intent = new Intent(mContext,WGDetailActivity.class);
                intent.putExtra("id",weChatBean.getPicUrl());//微信的id就是图片URL
                intent.putExtra("title",weChatBean.getTitle());
                intent.putExtra("type", Constants.TYPE_WECHAT);
                intent.putExtra("url",weChatBean.getUrl());//微信的URL
                ActivityOptions activityOptions = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    activityOptions = ActivityOptions.makeSceneTransitionAnimation(mActivity,shareView,"shareView");
                    mContext.startActivity(intent,activityOptions.toBundle());
                }else {
                    mContext.startActivity(intent);
                }



            }
        });
        mRVWeChatList.setLayoutManager(new LinearLayoutManager(mContext));
        mRVWeChatList.setAdapter(mAdapter);
        mRVWeChatList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(manager.findLastVisibleItemPosition() >= mAdapter.getItemCount()-1){
                    if(mLoadingMore)
                        return;//防抖动
                    Log.d("data","获取更多"+manager.findLastVisibleItemPosition()+":"+ mAdapter.getItemCount());
                    mPresenter.getHotMoreList();
                    mLoadingMore = true;
                }
            }
        });
        mSRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getHotList();
            }
        });
        mProgressBar.setVisibility(View.VISIBLE);
        mPresenter.getHotList();



    }

    @Override
    public void showHotList(List<WeChatBean> list) {
        mLoadingMore = false;
        mAdapter.resetLoading();
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(GONE);
        }else {
            mSRefreshLayout.setRefreshing(false);
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showHotMoreList(List<WeChatBean> list) {
        mAdapter.resetLoading();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
        mLoadingMore = false;
    }

    @Override
    public void showError(String msg) {
        if(mSRefreshLayout.isRefreshing()){
            mSRefreshLayout.setRefreshing(false);
        }else {
            mProgressBar.setVisibility(GONE);
        }
        mAdapter.removeLoading();
        Snackbar.make(getActivity().getWindow().getDecorView(),msg, Snackbar.LENGTH_SHORT).show();

    }
}
