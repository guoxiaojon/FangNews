package com.example.jon.fangnews.ui.zhihu.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.base.SimpleFragment;
import com.example.jon.fangnews.ui.zhihu.adapter.ZhiHuMainAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jon on 2016/12/6.
 */

public class ZhiHuMainFragment extends SimpleFragment {

    @BindView(R.id.tab_zhihu_main)
    TabLayout mTabLayout;
    @BindView(R.id.vp_zhihu_main)
    ViewPager mViewPager;

    String[] titles = new String[]{"日报","主题","专栏","热门"};
    List<Fragment> mFragments = new ArrayList<>();
    ZhiHuMainAdapter mZhiHuMainAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhihu_main;
    }

    @Override
    protected void initEventAndData() {
        mFragments.add(new DailyFragment());
        mFragments.add(new ThemeFragment());
        mFragments.add(new SectionFragment());
        mFragments.add(new HotFragment());
        mZhiHuMainAdapter = new ZhiHuMainAdapter(getChildFragmentManager(),mFragments);
        mViewPager.setAdapter(mZhiHuMainAdapter);

        for(int i=0;i<4;i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(titles[0]));
        }
        mTabLayout.setupWithViewPager(mViewPager);
        for(int i=0;i<4;i++){
            mTabLayout.getTabAt(i).setText(titles[i]);
        }




    }
}
