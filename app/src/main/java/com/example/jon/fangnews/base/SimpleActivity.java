package com.example.jon.fangnews.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jon.fangnews.app.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jon on 2016/12/8.
 */

public abstract class SimpleActivity extends AppCompatActivity {
    protected Activity mContext;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        App.getInstance().addActivity(this);
        initEventAndDate();
    }

    protected void setToolBar(Toolbar toolBar,String title){
        toolBar.setTitle(title);
        toolBar.setTitleTextColor(ActivityCompat.getColor(mContext,android.R.color.white));
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportBacKPressed();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        App.getInstance().removeActivity(this);
    }

    protected void onSupportBacKPressed(){
        onBackPressed();
    }

    protected abstract int getLayoutId();
    protected abstract void initEventAndDate();
}
