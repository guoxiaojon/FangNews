package com.example.jon.fangnews.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jon.fangnews.app.App;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jon on 2016/12/5.
 */

public abstract class BaseActivity <T extends BasePresenter> extends AppCompatActivity implements BaseView{

    @Inject
    protected T mPresenter;

    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initInject();
        if(mPresenter != null){
            mPresenter.attachView(this);
        }
        App.getInstance().addActivity(this);
        initEventAndData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detachView();
        }
        if(mUnBinder != null){
            mUnBinder.unbind();
        }
        App.getInstance().removeActivity(this);
    }

    @Override
    public void useNightMode(boolean isNight) {
        if(isNight){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    protected void setToolBar(Toolbar toolBar,String title){
        toolBar.setTitleTextColor(Color.WHITE);
        toolBar.setTitle(title);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressedSupport();

            }
        });

    }

    protected abstract void initEventAndData();
    protected abstract void initInject();
    protected abstract int getLayoutId();
    protected void onBackPressedSupport(){};

}
