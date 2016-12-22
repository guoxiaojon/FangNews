package com.example.jon.fangnews.ui.zhihu.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.App;
import com.example.jon.fangnews.base.BaseActivity;
import com.example.jon.fangnews.di.component.DaggerActivityComponent;
import com.example.jon.fangnews.di.module.ActivityModule;
import com.example.jon.fangnews.model.bean.ZhiHuSectionContentBean;
import com.example.jon.fangnews.presenter.contract.SectionContentContract;
import com.example.jon.fangnews.presenter.contract.SectionContentPresenter;
import com.example.jon.fangnews.ui.zhihu.adapter.SectionDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.view.View.GONE;

/**
 * Created by jon on 2016/12/13.
 */

public class SectionActivity extends BaseActivity<SectionContentPresenter> implements SectionContentContract.View{
    @BindView(R.id.rv_zhihu_section_detail_list)
    RecyclerView mRVSectionList;
    @BindView(R.id.view_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sr_zhihu_section_detail)
    SwipeRefreshLayout mSRefeshLayout;

    List<ZhiHuSectionContentBean.StoryBean> mList;
    SectionDetailAdapter mAdapter;


    private int mId;



    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        mId = intent.getIntExtra("id",-1);
        mList = new ArrayList<>();
        mAdapter = new SectionDetailAdapter(mList, mContext, new SectionDetailAdapter.OnItemClickListener() {
            @Override
            public void onClick(int id, int position) {
                mPresenter.insertReadedToDB(id);
                mAdapter.setReaded(position);
                mAdapter.notifyItemChanged(position);
                Intent intent = new Intent(mContext,ZhiHuDetailActivity.class);
                intent.putExtra("id",id);
                mContext.startActivity(intent);

            }
        });
        mRVSectionList.setAdapter(mAdapter);
        mRVSectionList.setLayoutManager(new LinearLayoutManager(mContext));
        mProgressBar.setVisibility(View.VISIBLE);
        mPresenter.getSectionContent(mId);
        mSRefeshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getSectionContent(mId);
            }
        });

    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhihu_section_detail;
    }



    @Override
    public void showError(String msg) {
        mProgressBar.setVisibility(GONE);
        Snackbar.make(getWindow().getDecorView(),msg,Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void showSectionContent(ZhiHuSectionContentBean zhiHuSectionContentBean) {
        if(mSRefeshLayout.isRefreshing()){
            mSRefeshLayout.setRefreshing(false);
        }else {
            mProgressBar.setVisibility(GONE);
        }
        setToolBar(mToolbar,zhiHuSectionContentBean.getName());
        mList.clear();
        mList.addAll(zhiHuSectionContentBean.getStories());
        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onBackPressedSupport() {
        super.onBackPressedSupport();
        finish();
    }
}
