package com.example.jon.fangnews.presenter;

import com.example.jon.fangnews.base.RxPresenter;
import com.example.jon.fangnews.model.bean.ZhiHuSectionListBean;
import com.example.jon.fangnews.model.http.RetrofitHelper;
import com.example.jon.fangnews.presenter.contract.SectionContract;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jon on 2016/12/13.
 */

public class SectionPresenter extends RxPresenter<SectionContract.View> implements SectionContract.Presenter {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public SectionPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;

    }

    @Override
    public void getSectionList() {
        Subscription subscription = mRetrofitHelper.fetchZhiHuSectionInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ZhiHuSectionListBean>() {
                    @Override
                    public void call(ZhiHuSectionListBean zhiHuSectionListBean) {
                        mView.showSection(zhiHuSectionListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("出错啦~");
                    }
                });
        addSubscribe(subscription);

    }
}
