package com.example.jon.fangnews.ui.gank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jon on 2016/12/15.
 */

public class GankMainAdapter extends FragmentPagerAdapter{
    private List<Fragment> mList;
    public GankMainAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
