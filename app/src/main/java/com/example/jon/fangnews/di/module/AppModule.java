package com.example.jon.fangnews.di.module;

import android.content.Context;

import com.example.jon.fangnews.app.App;
import com.example.jon.fangnews.model.db.RealmHelper;
import com.example.jon.fangnews.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jon on 2016/12/19.
 */
@Module
public class AppModule {
    private App mApp;

    public AppModule(App app){
        this.mApp = app;
    }

    @Singleton
    @Provides
    RealmHelper provideRealmHelper(){
        return new RealmHelper(mApp);
    }

    @Singleton
    @Provides
    RetrofitHelper provideRetroFitHelper(){
        return new RetrofitHelper();
    }

    @Singleton
    @Provides
    Context provideApp(){
        return mApp;
    }

}
