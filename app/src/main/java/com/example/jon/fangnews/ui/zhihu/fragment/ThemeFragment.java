package com.example.jon.fangnews.ui.zhihu.fragment;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.base.BaseFragment;
import com.example.jon.fangnews.model.bean.ZhiHuThemeListBean;
import com.example.jon.fangnews.presenter.ThemePresenter;
import com.example.jon.fangnews.presenter.contract.ThemeContract;
import com.example.jon.fangnews.ui.zhihu.activity.ThemeActivity;
import com.example.jon.fangnews.ui.zhihu.adapter.ThemeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.view.View.GONE;

/**
 * Created by jon on 2016/12/7.
 */

public class ThemeFragment extends BaseFragment<ThemePresenter> implements ThemeContract.View {
    @BindView(R.id.rv_zhihu_theme_list)
    RecyclerView mRVThemeList;
    @BindView(R.id.view_progress)
    ProgressBar mProgressBar;

    private ThemeListAdapter mAdapter;
    private List<ZhiHuThemeListBean.Other> mList;

    @Override
    protected void initInject() {
        getDaggerFragmentComponent().inject(this);
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhihu_theme_list;
    }

    @Override
    protected void initEventAndData() {
        mList = new ArrayList<>();
        mAdapter = new ThemeListAdapter(mList, mContext, new ThemeListAdapter.OnItemClick() {
            @Override
            public void onClick(int id) {
                Intent intent = new Intent(mContext, ThemeActivity.class);
                intent.putExtra("id",id);
                mContext.startActivity(intent);
            }
        });
        mRVThemeList.setAdapter(mAdapter);
        mRVThemeList.setLayoutManager(new GridLayoutManager(mContext,2));
        mProgressBar.setVisibility(View.VISIBLE);
        mPresenter.getThemeList();


    }

    @Override
    public void showTheme(ZhiHuThemeListBean listBean) {
        mProgressBar.setVisibility(GONE);
        mList.clear();
        mList.addAll(listBean.getOthers());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(getActivity().getWindow().getDecorView(),msg,Snackbar.LENGTH_LONG).show();
    }
}
