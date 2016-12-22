package com.example.jon.fangnews.presenter;

import android.util.Log;

import com.example.jon.fangnews.app.Constants;
import com.example.jon.fangnews.base.RxPresenter;
import com.example.jon.fangnews.component.RxBus;
import com.example.jon.fangnews.model.bean.GankItemBean;
import com.example.jon.fangnews.model.bean.GankSearchEvent;
import com.example.jon.fangnews.model.http.GankHttpResponse;
import com.example.jon.fangnews.model.http.RetrofitHelper;
import com.example.jon.fangnews.presenter.contract.GankOtherContract;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jon on 2016/12/15.
 */

public class GankOtherPresenter extends RxPresenter<GankOtherContract.View> implements GankOtherContract.Presenter {
    private static final int NUM = 15;
    private static String TYPE_ANDROID = "Android";
    private static String TYPE_VIDEO = "休息视频";
    private static String TYPE_GIRL = "福利";
    private static String TYPE_RECOMM = "瞎推荐";

    private int mType;
    private int mPage = 1;
    private String mQuery;

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public GankOtherPresenter(RetrofitHelper retrofitHelper){
        this.mRetrofitHelper = retrofitHelper;
        registEvent();
    }


    @Override
    public void getGankData(int type) {
        mType = type;
        mPage = 1;
        mView.showProgress();
        getGankMoreData(mType);

    }


    private void registEvent() {

        Subscription subscription = RxBus.getDefault().toObservable(GankSearchEvent.class)
                .filter(new Func1<GankSearchEvent, Boolean>() {
                    @Override
                    public Boolean call(GankSearchEvent gankSearchEvent) {
                        return gankSearchEvent.getType() == mType;
                    }
                })
                .subscribe(new Action1<GankSearchEvent>() {
                    @Override
                    public void call(GankSearchEvent gankSearchEvent) {
                        mQuery = gankSearchEvent.getQuery();
                        Log.d("data","查询");
                        getGankData(mType);
                    }
                });
        addSubscribe(subscription);
    }

    @Override
    public void getGankMoreData(int type) {
        Observable<GankHttpResponse<List<GankItemBean>>> observable;
        if(mQuery != null && !mQuery.trim().equals("")){
            observable = mRetrofitHelper.fetchGankDataListWithSearch(mQuery,getTypeString(mType),NUM,++mPage);
        }else {
            observable = mRetrofitHelper.fetchGankDataList(getTypeString(mType),NUM,++mPage);
        }

        Subscription rxSubscription =  observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<GankHttpResponse<List<GankItemBean>>, Observable<List<GankItemBean>>>() {
                    @Override
                    public Observable<List<GankItemBean>> call(final GankHttpResponse<List<GankItemBean>> listGankHttpResponse) {
                        return Observable.create(new Observable.OnSubscribe<List<GankItemBean>>() {
                            @Override
                            public void call(Subscriber<? super List<GankItemBean>> subscriber) {
                                if(listGankHttpResponse.isError()){
                                    subscriber.onError(new Throwable("错误"));
                                }else {
                                    subscriber.onNext(listGankHttpResponse.getResults());
                                    subscriber.onCompleted();
                                }
                            }
                        });
                    }
                })
                .subscribe(new Action1<List<GankItemBean>>() {
                    @Override
                    public void call(List<GankItemBean> gankItemBeen) {
                        if(mPage == 2){
                            mView.showContent(gankItemBeen);
                        }else {
                            mView.showMoreContent(gankItemBeen);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("出错啦~");
                    }
                });
        addSubscribe(rxSubscription);


    }

    @Override
    public void getRandomGirl() {
        Subscription subscription = mRetrofitHelper.fetchRandomGirlDataList(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<GankHttpResponse<List<GankItemBean>>, Observable<List<GankItemBean>>>() {
                    @Override
                    public Observable<List<GankItemBean>> call(final GankHttpResponse<List<GankItemBean>> listGankHttpResponse) {
                        return Observable.create(new Observable.OnSubscribe<List<GankItemBean>>() {
                            @Override
                            public void call(Subscriber<? super List<GankItemBean>> subscriber) {
                                if(listGankHttpResponse.isError()){
                                    subscriber.onError(new Throwable("error"));
                                }else {
                                    subscriber.onNext(listGankHttpResponse.getResults());
                                    subscriber.onCompleted();
                                }
                            }
                        });
                    }
                }).subscribe(new Action1<List<GankItemBean>>() {
            @Override
            public void call(List<GankItemBean> gankItemBeen) {
                mView.showGirl(gankItemBeen);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                mView.showError("出错啦~");
            }
        });
        addSubscribe(subscription);
    }

    @Override
    public void clearSearch() {
        mQuery = null;
    }

    public String getTypeString(int type) {
        switch (type) {
            case Constants.TYPE_ANDROID:
                return TYPE_ANDROID;
            case Constants.TYPE_VIDEO:
                return TYPE_VIDEO;
            case Constants.TYPE_RECOMM:
                return TYPE_RECOMM;
            case Constants.TYPE_GIRL:
                return TYPE_GIRL;
            default:
                return null;

        }
    }


}
