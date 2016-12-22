package com.example.jon.fangnews.presenter;

import com.example.jon.fangnews.base.RxPresenter;
import com.example.jon.fangnews.model.bean.ZhiHuThemeListBean;
import com.example.jon.fangnews.model.http.RetrofitHelper;
import com.example.jon.fangnews.presenter.contract.ThemeContract;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jon on 2016/12/12.
 */

public class ThemePresenter extends RxPresenter<ThemeContract.View> implements ThemeContract.Presenter {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public ThemePresenter(RetrofitHelper retrofitHelper){
        this.mRetrofitHelper = retrofitHelper;
    }
    @Override
    public void getThemeList() {
        Subscription rxSubscription = mRetrofitHelper.fetchZhiHuThemeListInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ZhiHuThemeListBean>() {
                    @Override
                    public void call(ZhiHuThemeListBean zhiHuThemeListBean) {
                        mView.showTheme(zhiHuThemeListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("出错啦/(ㄒoㄒ)/~~");
                    }
                });
        addSubscribe(rxSubscription);
    }
}
