package com.example.jon.fangnews.di.component;

import android.content.Context;

import com.example.jon.fangnews.di.module.AppModule;
import com.example.jon.fangnews.model.db.RealmHelper;
import com.example.jon.fangnews.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jon on 2016/12/19.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    Context getApp();
    RetrofitHelper getRetrofitHelper();
    RealmHelper getRealmHelper();

}
