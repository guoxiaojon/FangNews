package com.example.jon.fangnews.di.component;

import android.app.Activity;

import com.example.jon.fangnews.di.module.ActivityModule;
import com.example.jon.fangnews.di.scope.PerActivity;
import com.example.jon.fangnews.ui.gank.activity.GankGirlDetailActivity;
import com.example.jon.fangnews.ui.main.WelcomeActivity;
import com.example.jon.fangnews.ui.main.activity.MainActivity;
import com.example.jon.fangnews.ui.wechat.activity.WGDetailActivity;
import com.example.jon.fangnews.ui.zhihu.activity.SectionActivity;
import com.example.jon.fangnews.ui.zhihu.activity.ThemeActivity;
import com.example.jon.fangnews.ui.zhihu.activity.ZhiHuDetailActivity;

import dagger.Component;

/**
 * Created by jon on 2016/12/19.
 */
@PerActivity
@Component(modules = ActivityModule.class,dependencies = AppComponent.class)
public interface ActivityComponent {
    Activity getActivity();
    void inject(MainActivity mainActivity);
    void inject(WelcomeActivity welcomeActivity);
    void inject(SectionActivity sectionActivity);
    void inject(ThemeActivity themeActivity);
    void inject(ZhiHuDetailActivity zhiHuDetailActivity);
    void inject(GankGirlDetailActivity gankGirlDetailActivity);
    void inject(WGDetailActivity wgDetailActivity);

}
