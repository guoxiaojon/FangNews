package com.example.jon.fangnews.presenter;

import android.util.Log;

import com.example.jon.fangnews.base.RxPresenter;
import com.example.jon.fangnews.component.RxBus;
import com.example.jon.fangnews.model.bean.WeChatBean;
import com.example.jon.fangnews.model.bean.WeChatSearchEvent;
import com.example.jon.fangnews.model.http.RetrofitHelper;
import com.example.jon.fangnews.model.http.WeChatHttpResponse;
import com.example.jon.fangnews.presenter.contract.WeChatContract;

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
 * Created by jon on 2016/12/14.
 */

public class WeChatPresenter extends RxPresenter<WeChatContract.View> implements WeChatContract.Presenter{


    private RetrofitHelper mRetrofitHelper;

    private static final int NUM = 15;

    private String mSearch;
    private int mPage = 1;

    @Inject
    public WeChatPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
        registerEvent();
    }

    private void registerEvent() {
        Subscription subscription = RxBus.getDefault().toObservable(WeChatSearchEvent.class)
                .subscribe(new Action1<WeChatSearchEvent>() {
                    @Override
                    public void call(WeChatSearchEvent weChatSearchEvent) {
                        if(weChatSearchEvent.getSearchWord() != null && !weChatSearchEvent.getSearchWord().trim().equals("")){
                            mSearch = weChatSearchEvent.getSearchWord();
                            if(mSearch != null && !mSearch.trim().equals(""))
                                getHotList();
                        }
                    }
                });
        addSubscribe(subscription);
    }

    @Override
    public void getHotList() {
        mPage = 1;
        Log.d("data","getHotList");
        getHotMoreList();

    }

    @Override
    public void getHotMoreList() {
        Observable<WeChatHttpResponse<List<WeChatBean>>> observable = null;
        if(mSearch != null && !mSearch.trim().equals("")){
            observable = mRetrofitHelper.fetchWeChatHotSearch(NUM,mSearch,mPage++);

        }else {
            observable = mRetrofitHelper.fetchWeChatHotList(NUM,mPage++);
        }
        Subscription rxSubscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<WeChatHttpResponse<List<WeChatBean>>, Observable<List<WeChatBean>>>() {
                    @Override
                    public Observable<List<WeChatBean>> call(final WeChatHttpResponse<List<WeChatBean>> listWeChatHttpResponse) {
                        return Observable.create(new Observable.OnSubscribe<List<WeChatBean>>() {
                            @Override
                            public void call(Subscriber<? super List<WeChatBean>> subscriber) {
                                if(listWeChatHttpResponse.getCode() == 200){
                                    subscriber.onNext(listWeChatHttpResponse.getNewslist());
                                    subscriber.onCompleted();
                                }else {
                                    subscriber.onError(new Throwable(String.valueOf(listWeChatHttpResponse.getCode())));
                                }

                            }
                        });
                    }
                })
                .subscribe(new Action1<List<WeChatBean>>() {
                    @Override
                    public void call(List<WeChatBean> weChatBeen) {
                        if(mPage > 2){
                            mView.showHotMoreList(weChatBeen);
                        }
                        else {
                            mView.showHotList(weChatBeen);
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("data","===="+throwable.getMessage());
                        if(throwable.getMessage().equals("250")){
                            mView.showError("暂无更多~");
                        } else {
                            mView.showError("出错啦");
                        }

                    }
                });
        addSubscribe(rxSubscription);

    }


}
