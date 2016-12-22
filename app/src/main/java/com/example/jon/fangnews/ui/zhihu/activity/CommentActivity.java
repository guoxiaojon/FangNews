package com.example.jon.fangnews.ui.zhihu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.Constants;
import com.example.jon.fangnews.base.SimpleActivity;
import com.example.jon.fangnews.ui.zhihu.adapter.CommentAdapter;
import com.example.jon.fangnews.ui.zhihu.fragment.CommentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jon on 2016/12/11.
 */

public class CommentActivity extends SimpleActivity {
    @BindView(R.id.tl_zhihu_comment)
    TabLayout mTLCommentTab;
    @BindView(R.id.tb_zhihu_comment)
    Toolbar mTBCommentnBar;
    @BindView(R.id.vp_zhihu_comment)
    ViewPager mVPCommentContent;

    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private Fragment mLongCommentFragment;
    private Fragment mShortCommentFragment;

    private int mNumOfComment;
    private int mNumOfLongComment;
    private int mNumOfShortComment;
    private int mId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhihu_comment;
    }

    @Override
    protected void initEventAndDate() {
        Intent intent = getIntent();
        mNumOfComment = intent.getIntExtra("comment",0);
        mNumOfLongComment = intent.getIntExtra("longComment",0);
        mNumOfShortComment = intent.getIntExtra("shortComment",0);
        mId = intent.getIntExtra("id",-1);
        setToolBar(mTBCommentnBar,String.format("%d条评论",mNumOfComment));

        mTLCommentTab.addTab(mTLCommentTab.newTab().setText(String.format("短评论（%d）",mNumOfShortComment)));
        mTLCommentTab.addTab(mTLCommentTab.newTab().setText(String.format("长评论（%d）",mNumOfLongComment)));

        mLongCommentFragment = new CommentFragment();
        Bundle longBundle = new Bundle();
        longBundle.putInt("id",mId);
        longBundle.putInt("type", Constants.TYPE_LONG_COMMENT);
        mLongCommentFragment.setArguments(longBundle);

        mShortCommentFragment = new CommentFragment();
        Bundle shortBundle = new Bundle();
        shortBundle.putInt("id",mId);
        shortBundle.putInt("type", Constants.TYPE_SHORT_COMMENT);
        mShortCommentFragment.setArguments(shortBundle);

        mFragments.add(mShortCommentFragment);
        mFragments.add(mLongCommentFragment);
        mVPCommentContent.setAdapter(new CommentAdapter(getSupportFragmentManager(),mFragments));
        mTLCommentTab.setupWithViewPager(mVPCommentContent);

        mTLCommentTab.getTabAt(0).setText(String.format("短评论（%d）",mNumOfShortComment));
        mTLCommentTab.getTabAt(1).setText(String.format("长评论（%d）",mNumOfLongComment));

    }
}
