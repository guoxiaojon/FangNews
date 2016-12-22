package com.example.jon.fangnews.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jon.fangnews.app.App;
import com.example.jon.fangnews.di.component.DaggerFragmentComponent;
import com.example.jon.fangnews.di.component.FragmentComponent;
import com.example.jon.fangnews.di.module.FragmentModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jon on 2016/12/5.
 */

public abstract class BaseFragment <T extends BasePresenter> extends Fragment implements BaseView{

    @Inject
    protected T mPresenter;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnbinder;
    protected boolean isInited = false;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mContext = context;

    }

    protected abstract void initInject();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(),container,false);
    }

    protected FragmentComponent getDaggerFragmentComponent(){
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInject();
        mUnbinder = ButterKnife.bind(this,view);
        mPresenter.attachView(this);
        if(!isHidden()){
            isInited = true;
            initEventAndData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("data",this+"onHiddenChanged  :  " + hidden);
        if(!hidden && !isInited){
            initEventAndData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mPresenter.detachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //if(mPresenter != null)
           // mPresenter.detachView();
    }

    @Override
    public void useNightMode(boolean isNight) {

    }
    protected abstract int getLayoutId();
    protected abstract void initEventAndData();
}
