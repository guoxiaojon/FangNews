package com.example.jon.fangnews.di.component;

import android.app.Activity;

import com.example.jon.fangnews.di.module.FragmentModule;
import com.example.jon.fangnews.di.scope.PerFragment;
import com.example.jon.fangnews.ui.gank.fragment.GankGirlFragment;
import com.example.jon.fangnews.ui.gank.fragment.GankOtherFragment;
import com.example.jon.fangnews.ui.main.fragment.LikeFragment;
import com.example.jon.fangnews.ui.wechat.fragment.WeChatMainFragment;
import com.example.jon.fangnews.ui.zhihu.fragment.CommentFragment;
import com.example.jon.fangnews.ui.zhihu.fragment.DailyFragment;
import com.example.jon.fangnews.ui.zhihu.fragment.HotFragment;
import com.example.jon.fangnews.ui.zhihu.fragment.SectionFragment;
import com.example.jon.fangnews.ui.zhihu.fragment.ThemeFragment;

import dagger.Component;

/**
 * Created by jon on 2016/12/19.
 */
@PerFragment
@Component(modules = FragmentModule.class,dependencies = AppComponent.class)
public interface FragmentComponent {
    Activity getActivity();
    void inject(LikeFragment likeFragment);
    void inject(GankGirlFragment gankGirlFragment);
    void inject(GankOtherFragment gankOtherFragment);
    void inject(WeChatMainFragment weChatMainFragment);
    void inject(CommentFragment commentFragment);
    void inject(DailyFragment dailyFragment);
    void inject(HotFragment hotFragment);
    void inject(SectionFragment sectionFragment);
    void inject(ThemeFragment themeFragment);
}
