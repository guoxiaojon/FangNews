package com.example.jon.fangnews.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jon on 2016/12/7.
 */

public abstract class SimpleFragment extends Fragment {

    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    protected Unbinder mUnbinder;
    protected boolean isInited = false;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (Activity)context;
    }






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(),null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this,view);
        if(!isHidden()){
            initEventAndData();
            isInited = true;
        }


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden && !isInited){
            initEventAndData();
            isInited = true;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    protected abstract int getLayoutId();
    protected abstract void initEventAndData();
}
