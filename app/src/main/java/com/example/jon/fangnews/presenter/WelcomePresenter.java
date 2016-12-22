package com.example.jon.fangnews.presenter;

import android.util.Log;

import com.example.jon.fangnews.base.RxPresenter;
import com.example.jon.fangnews.model.bean.WelcomeBean;
import com.example.jon.fangnews.model.http.RetrofitHelper;
import com.example.jon.fangnews.presenter.contract.WelcomeContract;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jon on 2016/12/5.
 */

public class WelcomePresenter extends RxPresenter<WelcomeContract.View> implements WelcomeContract.Presenter {

    private static final String RES = "1080*1776";

    private RetrofitHelper mRetrofitHelper;

    private static final int COUNT_DOWN_TIME = 2200;

    @Inject
    public WelcomePresenter(RetrofitHelper retrofitHelper){
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getWelcomeData() {
        Subscription subscription = mRetrofitHelper.fetchWelcomeInfo(RES)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WelcomeBean>() {
                    @Override
                    public void call(WelcomeBean welcomeBean) {
                        mView.showContent(welcomeBean);
                        Log.d("data", "==================="+welcomeBean.getImg());
                        countDown();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("data","=+++++++++++++++++++++"+throwable);
                        mView.showError("获取图像出错啦" );
                        mView.jumpToMainActivity();
                    }
                });
        addSubscribe(subscription);

    }

    private void countDown() {
        Subscription subscription = Observable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS)
               .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mView.jumpToMainActivity();
                    }
                });
        addSubscribe(subscription);
    }


}
