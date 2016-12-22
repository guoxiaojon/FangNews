package com.example.jon.fangnews.ui.zhihu.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.Constants;
import com.example.jon.fangnews.base.BaseFragment;
import com.example.jon.fangnews.model.bean.ZhiHuCommentBean;
import com.example.jon.fangnews.presenter.ZhiHuCommentPresenter;
import com.example.jon.fangnews.presenter.contract.ZhiHuCommentContract;
import com.example.jon.fangnews.ui.zhihu.adapter.CommentContentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jon on 2016/12/11.
 */

public class CommentFragment extends BaseFragment<ZhiHuCommentPresenter> implements ZhiHuCommentContract.View {
    @BindView(R.id.rv_zhihu_comment)
    RecyclerView mRVCommentList;
    @BindView(R.id.view_progress)
    ProgressBar mProgressBar;

    private int mId;
    private int mType;


    private CommentContentAdapter mAdapter;
    private List<ZhiHuCommentBean.CommentBean> mList;

    @Override
    protected void initInject() {
        getDaggerFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhihu_comment;
    }

    @Override
    protected void initEventAndData() {
        Bundle bundle = getArguments();
        mId = bundle.getInt("id");
        mType = bundle.getInt("type");
        mList = new ArrayList<>();
        mAdapter = new CommentContentAdapter(mList,mContext);
        mRVCommentList.setLayoutManager(new LinearLayoutManager(mContext));
        mRVCommentList.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.VISIBLE);
        if(mType == Constants.TYPE_LONG_COMMENT){
            Log.d("data","longlllllllllllllllll");
            mPresenter.getLongComments(mId);
        }else if(mType == Constants.TYPE_SHORT_COMMENT){
            mPresenter.getShortComments(mId);
            Log.d("data","shortssssssssssssssssssssssssssss");
        }



    }


    @Override
    public void showComments(ZhiHuCommentBean zhiHuCommentBean) {
        mProgressBar.setVisibility(View.GONE);
        mList.clear();
        mList.addAll(zhiHuCommentBean.getComments());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg) {

    }
}
