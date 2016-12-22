package com.example.jon.fangnews.ui.zhihu.fragment;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.base.BaseFragment;
import com.example.jon.fangnews.model.bean.ZhiHuHotListBean;
import com.example.jon.fangnews.presenter.HotPresenter;
import com.example.jon.fangnews.presenter.contract.HotContract;
import com.example.jon.fangnews.ui.zhihu.activity.ZhiHuDetailActivity;
import com.example.jon.fangnews.ui.zhihu.adapter.HotAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.view.View.GONE;

/**
 * Created by jon on 2016/12/7.
 */

public class HotFragment extends BaseFragment<HotPresenter> implements HotContract.View{
    @BindView(R.id.sr_zhihu_hot)
    SwipeRefreshLayout mSRefreshLayout;
    @BindView(R.id.rv_zhihu_hot)
    RecyclerView mRVHotList;
    @BindView(R.id.view_progress)
    ProgressBar mProgressBar;

    List<ZhiHuHotListBean.RecentBean> mList;
    HotAdapter mAdapter;

    @Override
    protected void initInject() {
        getDaggerFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhihu_hot;
    }

    @Override
    protected void initEventAndData() {
        mList = new ArrayList<>();
        mAdapter = new HotAdapter(mList, new HotAdapter.OnItemClickListener() {
            @Override
            public void onClick(int id, int position) {
                mAdapter.setReaded(position);
                mAdapter.notifyItemChanged(position);
                mPresenter.insertReadedToDB(id);
                Intent intent = new Intent(mContext, ZhiHuDetailActivity.class);
                intent.putExtra("id",id);
                mContext.startActivity(intent);

            }
        },mContext);
        mRVHotList.setLayoutManager(new LinearLayoutManager(mContext));
        mRVHotList.setAdapter(mAdapter);
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
    public void showHotList(ZhiHuHotListBean hotListBean) {
        if(mSRefreshLayout.isRefreshing()){
            mSRefreshLayout.setRefreshing(false);
        }else {
            mProgressBar.setVisibility(GONE);
        }
        mList.clear();
        mList.addAll(hotListBean.getRecent());
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError(String msg) {
        if(mSRefreshLayout.isRefreshing()){
            mSRefreshLayout.setRefreshing(false);
        }else {
            mProgressBar.setVisibility(GONE);
        }
        Snackbar.make(mActivity.getWindow().getDecorView(),msg,Snackbar.LENGTH_LONG).show();
    }
}
