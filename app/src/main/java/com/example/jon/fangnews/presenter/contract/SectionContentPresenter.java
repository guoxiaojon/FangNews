package com.example.jon.fangnews.presenter.contract;

import com.example.jon.fangnews.base.RxPresenter;
import com.example.jon.fangnews.model.bean.ZhiHuSectionContentBean;
import com.example.jon.fangnews.model.db.RealmHelper;
import com.example.jon.fangnews.model.http.RetrofitHelper;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jon on 2016/12/13.
 */

public class SectionContentPresenter extends RxPresenter<SectionContentContract.View> implements SectionContentContract.Presenter {
    private RealmHelper mRealmHelper;
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public SectionContentPresenter(RealmHelper realmHelper,RetrofitHelper retrofitHelper) {
        this.mRealmHelper = realmHelper;
        this.mRetrofitHelper = retrofitHelper;

    }

    @Override
    public void getSectionContent(int id) {
        Subscription rxSubscription = mRetrofitHelper.fetchZhiHuSectionContentInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ZhiHuSectionContentBean, ZhiHuSectionContentBean>() {
                    @Override
                    public ZhiHuSectionContentBean call(ZhiHuSectionContentBean zhiHuSectionContentBean) {
                        List<ZhiHuSectionContentBean.StoryBean> stories = zhiHuSectionContentBean.getStories();
                        for(ZhiHuSectionContentBean.StoryBean story :stories){
                            story.setReaded(mRealmHelper.queryIsReaded(story.getId()));
                        }
                        return zhiHuSectionContentBean;
                    }
                })
                .subscribe(new Action1<ZhiHuSectionContentBean>() {
                    @Override
                    public void call(ZhiHuSectionContentBean zhiHuSectionContentBean) {
                        mView.showSectionContent(zhiHuSectionContentBean);
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
