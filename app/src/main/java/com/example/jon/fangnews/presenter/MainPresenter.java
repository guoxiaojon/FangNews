package com.example.jon.fangnews.presenter;

import com.example.jon.fangnews.base.RxPresenter;
import com.example.jon.fangnews.component.RxBus;
import com.example.jon.fangnews.model.bean.NightEvent;
import com.example.jon.fangnews.presenter.contract.MainContract;
import com.example.jon.fangnews.utils.SharePreferenceUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;


/**
 * Created by jon on 2016/12/5.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(){
        Subscription subscription = RxBus.getDefault().toObservable(NightEvent.class)
                .subscribe(new Action1<NightEvent>() {
                    @Override
                    public void call(NightEvent nightEvent) {
                        mView.useNightMode(nightEvent.isNight());
                        SharePreferenceUtil.setNightSetByEvent(true);
                    }
                });
        addSubscribe(subscription);
    }



}
