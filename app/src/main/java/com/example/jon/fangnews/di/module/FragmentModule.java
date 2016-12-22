package com.example.jon.fangnews.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.example.jon.fangnews.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jon on 2016/12/19.
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment){
        this.mFragment = fragment;
    }

    @PerFragment
    @Provides
    public Activity provideActivity(){
        return mFragment.getActivity();
    }

}
