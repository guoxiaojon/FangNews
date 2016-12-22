package com.example.jon.fangnews.presenter;

import android.util.Log;

import com.example.jon.fangnews.app.Constants;
import com.example.jon.fangnews.base.RxPresenter;
import com.example.jon.fangnews.model.bean.RealmLikeBean;
import com.example.jon.fangnews.model.bean.ZhiHuDetailBean;
import com.example.jon.fangnews.model.bean.ZhiHuDetailExtraBean;
import com.example.jon.fangnews.model.db.RealmHelper;
import com.example.jon.fangnews.model.http.RetrofitHelper;
import com.example.jon.fangnews.presenter.contract.ZhiHuDetailContract;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jon on 2016/12/10.
 */

public class ZhiHuDetailPresenter extends RxPresenter<ZhiHuDetailContract.View> implements ZhiHuDetailContract.Presenter {
    RetrofitHelper mRetrofitHelper;
    RealmHelper mRealmHelper;
    private ZhiHuDetailBean mDetailBean;

    @Inject
    public ZhiHuDetailPresenter(RetrofitHelper retrofitHelper,RealmHelper realmHelper){
        this.mRetrofitHelper = retrofitHelper;
        this.mRealmHelper = realmHelper;
    }
    @Override
    public void getDetailData(final int id) {
        Subscription rxSubscription = mRetrofitHelper.fetchZhiHuDetailInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ZhiHuDetailBean>() {
                    @Override
                    public void call(ZhiHuDetailBean detailBean) {
                        mView.showContent(detailBean);
                        mDetailBean = detailBean;
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("出错啦/(ㄒoㄒ)/~~");
                    }
                });
        addSubscribe(rxSubscription);
    }

    @Override
    public void getDetailExtraData(int id) {
        Subscription rxSubscription = mRetrofitHelper.fetchZhiHuDetailExtraInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ZhiHuDetailExtraBean>() {
                    @Override
                    public void call(ZhiHuDetailExtraBean detailExtraBean) {
                        mView.showExtraContent(detailExtraBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("出错啦~");
                        Logger.e(throwable.toString());
                    }
                });
        addSubscribe(rxSubscription);

    }

    @Override
    public void insertLikeData() {
        Log.d("data","detailbean"+mDetailBean);
       if(mDetailBean != null){
           RealmLikeBean likeBean = new RealmLikeBean();
           likeBean.setId(String.valueOf(mDetailBean.getId()));
           likeBean.setImage(mDetailBean.getImage());
           likeBean.setTime(System.currentTimeMillis());
           likeBean.setTitle(mDetailBean.getTitle());
           likeBean.setType(Constants.TYPE_ZHIHU);

           mRealmHelper.insertLikeBean(likeBean);
       }


    }

    @Override
    public void deleteLikeData() {
        if(mDetailBean != null){
            mRealmHelper.deteleLikeBean(String.valueOf(mDetailBean.getId()));
        }

    }

    @Override
    public void queryLikeData(int id) {
        mView.setLikeButtonState(mRealmHelper.queryLikeBean(String.valueOf(id)));
    }
}
