package com.example.jon.fangnews.presenter;

import android.content.Context;

import com.example.jon.fangnews.base.RxPresenter;
import com.example.jon.fangnews.model.bean.ZhiHuHotListBean;
import com.example.jon.fangnews.model.db.RealmHelper;
import com.example.jon.fangnews.model.http.RetrofitHelper;
import com.example.jon.fangnews.presenter.contract.HotContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jon on 2016/12/14.
 */

public class HotPresenter extends RxPresenter<HotContract.View> implements HotContract.Presenter {
    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;
    private Context mContext;

    @Inject
    public HotPresenter(RetrofitHelper retrofitHelper, RealmHelper realmHelper, Context context) {
        this.mRetrofitHelper = retrofitHelper;
        this.mRealmHelper = realmHelper;
        this.mContext = context;
    }

    @Override
    public void getHotList() {
        Subscription rxSubscription = mRetrofitHelper.fetchZhiHuHotListInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ZhiHuHotListBean, ZhiHuHotListBean>() {
                    @Override
                    public ZhiHuHotListBean call(ZhiHuHotListBean huHotListBean) {
                        List<ZhiHuHotListBean.RecentBean> recents = huHotListBean.getRecent();
                        for(ZhiHuHotListBean.RecentBean recent : recents){
                            String url = recent.getUrl();
                            recent.setNew_id(Integer.valueOf(url.substring(url.lastIndexOf("news/")+5,url.length())));

                            recent.setReaded(mRealmHelper.queryIsReaded(recent.getNew_id()));

                        }

                        return huHotListBean;
                    }
                })
                .subscribe(new Action1<ZhiHuHotListBean>() {
                    @Override
                    public void call(ZhiHuHotListBean huHotListBean) {
                        mView.showHotList(huHotListBean);
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
    public void insertReadedToDB(int id) {
        mRealmHelper.insertReadedId(id);
    }
}
