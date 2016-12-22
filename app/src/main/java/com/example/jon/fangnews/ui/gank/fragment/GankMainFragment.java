package com.example.jon.fangnews.ui.gank.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.app.Constants;
import com.example.jon.fangnews.base.SimpleFragment;
import com.example.jon.fangnews.component.RxBus;
import com.example.jon.fangnews.model.bean.GankSearchEvent;
import com.example.jon.fangnews.ui.gank.adapter.GankMainAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jon on 2016/12/6.
 */

public class GankMainFragment extends SimpleFragment {
    @BindView(R.id.tl_gank_main_tab)
    TabLayout mTab;
    @BindView(R.id.vp_gank_main_container)
    ViewPager mVPContainer;

    private List<Fragment> mFragments;
    private String[] mTitles = {"ANDROID","休息视频","瞎推荐","福利"};

    private GankMainAdapter mAdapter;

    private Fragment mAndroidFragment;
    private Fragment mVideoFragment;
    private Fragment mRecommedFragment;
    private Fragment mGirlFragment;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_main;
    }

    @Override
    protected void initEventAndData() {
        mFragments = new ArrayList<>();
        mAdapter = new GankMainAdapter(getChildFragmentManager(),mFragments);

        mAndroidFragment = new GankOtherFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", Constants.TYPE_ANDROID);
        mAndroidFragment.setArguments(bundle);
        mFragments.add(mAndroidFragment);

        mVideoFragment = new GankOtherFragment();
        bundle = new Bundle();
        bundle.putInt("type",Constants.TYPE_VIDEO);
        mVideoFragment.setArguments(bundle);
        mFragments.add(mVideoFragment);

        mRecommedFragment = new GankOtherFragment();
        bundle = new Bundle();
        bundle.putInt("type",Constants.TYPE_RECOMM);
        mRecommedFragment.setArguments(bundle);
        mFragments.add(mRecommedFragment);

        mGirlFragment = new GankGirlFragment();
        bundle = new Bundle();
        bundle.putInt("type",Constants.TYPE_GIRL);
        mGirlFragment.setArguments(bundle);
        mFragments.add(mGirlFragment);

        for(int i=0;i<mTitles.length;i++){
            mTab.addTab(mTab.newTab().setText(mTitles[i]));
        }
        mTab.setupWithViewPager(mVPContainer);
        mVPContainer.setAdapter(mAdapter);
        for(int i =0 ;i<mTitles.length;i++){
            mTab.getTabAt(i).setText(mTitles[i]);
        }


    }

    public void doSearch(GankSearchEvent searchEvent){
        searchEvent.setType(getType(mVPContainer.getCurrentItem()));
        RxBus.getDefault().post(searchEvent);
    }
    private int getType(int position){

        switch (position){
            case 0:
                return Constants.TYPE_ANDROID;
            case 1:
                return Constants.TYPE_VIDEO;
            case 2:
                return Constants.TYPE_RECOMM;
            case 3:
                return Constants.TYPE_GIRL;
        }
        return -1;
    }
}
