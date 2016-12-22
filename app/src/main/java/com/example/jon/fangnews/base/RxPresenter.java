package com.example.jon.fangnews.base;

import android.util.Log;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jon on 2016/12/5.
 */

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {
    protected T mView;
    protected CompositeSubscription mCompositeSubscription;
    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        Log.d("data","mView为null");
        this.mView = null;
        unSubscribe();

    }

    protected void addSubscribe(Subscription subscription){
        if(mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);

    }

    protected  void unSubscribe(){
        if(mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription = null;
        }
    }
}
