package com.example.jon.fangnews.presenter;

import android.util.Log;

import com.example.jon.fangnews.base.RxPresenter;
import com.example.jon.fangnews.model.bean.ZhiHuCommentBean;
import com.example.jon.fangnews.model.http.RetrofitHelper;
import com.example.jon.fangnews.presenter.contract.ZhiHuCommentContract;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jon on 2016/12/12.
 */

public class ZhiHuCommentPresenter extends RxPresenter<ZhiHuCommentContract.View> implements ZhiHuCommentContract.Presenter  {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public ZhiHuCommentPresenter(RetrofitHelper retrofitHelper){
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getLongComments(int id) {
        Log.d("data","longcomment");
        Subscription rxSubscription = mRetrofitHelper.fetchZhiHuLongComments(id)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Action1<ZhiHuCommentBean>() {
                       @Override
                       public void call(ZhiHuCommentBean zhiHuCommentBean) {
                           mView.showComments(zhiHuCommentBean);
                       }
                   });
        addSubscribe(rxSubscription);
    }

    @Override
    public void getShortComments(int id) {
        Log.d("date","short");
        Subscription rxSubscription = mRetrofitHelper.fetchZhiHuShortComments(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ZhiHuCommentBean>() {
                    @Override
                    public void call(ZhiHuCommentBean zhiHuCommentBean) {
                        mView.showComments(zhiHuCommentBean);
                    }
                });
        addSubscribe(rxSubscription);

    }
}
