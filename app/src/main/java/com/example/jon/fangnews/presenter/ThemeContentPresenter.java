package com.example.jon.fangnews.presenter;

import android.content.Context;

import com.example.jon.fangnews.base.RxPresenter;
import com.example.jon.fangnews.model.bean.ZhiHuThemeContentBean;
import com.example.jon.fangnews.model.db.RealmHelper;
import com.example.jon.fangnews.model.http.RetrofitHelper;
import com.example.jon.fangnews.presenter.contract.ThemeContentContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jon on 2016/12/12.
 */

public class ThemeContentPresenter extends RxPresenter<ThemeContentContract.View> implements ThemeContentContract.Presenter{
    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;
    private Context mContext;

    @Inject
    public ThemeContentPresenter(RetrofitHelper retrofitHelper,RealmHelper realmHelper,Context context){
        this.mRetrofitHelper = retrofitHelper;
        this.mContext = context;
        this.mRealmHelper = realmHelper;
    }
    @Override
    public void getContent(int id) {
        Subscription rxSubscription = mRetrofitHelper.fetchZhiHuThemeContentInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ZhiHuThemeContentBean, ZhiHuThemeContentBean>() {
                    @Override
                    public ZhiHuThemeContentBean call(ZhiHuThemeContentBean contentBean) {
                        List<ZhiHuThemeContentBean.StoryBean> stories = contentBean.getStories();
                        for(ZhiHuThemeContentBean.StoryBean story : stories){
                            story.setReaded(mRealmHelper.queryIsReaded(story.getId()));
                        }
                        return contentBean;
                    }
                })
                .subscribe(new Action1<ZhiHuThemeContentBean>() {
                    @Override
                    public void call(ZhiHuThemeContentBean contentBean) {
                        mView.showContent(contentBean);
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
    public void insertReaded(int id) {
        mRealmHelper.insertReadedId(id);

    }
}
