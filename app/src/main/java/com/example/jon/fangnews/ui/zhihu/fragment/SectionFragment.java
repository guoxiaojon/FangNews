package com.example.jon.fangnews.ui.zhihu.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.base.BaseFragment;
import com.example.jon.fangnews.model.bean.ZhiHuSectionListBean;
import com.example.jon.fangnews.presenter.SectionPresenter;
import com.example.jon.fangnews.presenter.contract.SectionContract;
import com.example.jon.fangnews.ui.zhihu.activity.SectionActivity;
import com.example.jon.fangnews.ui.zhihu.adapter.SectionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jon on 2016/12/7.
 */

public class SectionFragment extends BaseFragment<SectionPresenter> implements SectionContract.View {
    @BindView(R.id.rv_zhihu_section_list)
    RecyclerView mRVSectionList;
    @BindView(R.id.view_progress)
    ProgressBar mProgressBar;


    private List<ZhiHuSectionListBean.DataBean> mList;
    private SectionAdapter mAdapter;

    @Override
    protected void initInject() {
        getDaggerFragmentComponent().inject(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhihu_section_list;
    }

    @Override
    protected void initEventAndData() {
        mList = new ArrayList<>();
        mAdapter = new SectionAdapter(mContext, mList, new SectionAdapter.OnItemClickListener() {
            @Override
            public void onClick(int id) {
                Intent intent = new Intent(mContext, SectionActivity.class);
                intent.putExtra("id",id);
                mContext.startActivity(intent);
            }
        });
        mRVSectionList.setLayoutManager(new GridLayoutManager(mContext,2));
        mRVSectionList.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.VISIBLE);
        mPresenter.getSectionList();

    }

    @Override
    public void showSection(ZhiHuSectionListBean zhiHuSectionListBean) {
        mProgressBar.setVisibility(View.GONE);
        mList.clear();
        mList.addAll(zhiHuSectionListBean.getData());
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError(String msg) {

    }
}
