package com.example.jon.fangnews.presenter;

import com.example.jon.fangnews.base.RxPresenter;
import com.example.jon.fangnews.model.db.RealmHelper;
import com.example.jon.fangnews.presenter.contract.MainLikeContract;

import javax.inject.Inject;

/**
 * Created by jon on 2016/12/16.
 */

public class MainLikePresenter extends RxPresenter<MainLikeContract.View> implements MainLikeContract.Presenter {
    private RealmHelper mRealmHelper;

    @Inject
    public MainLikePresenter(RealmHelper realmHelper){
        this.mRealmHelper = realmHelper;
    }
    @Override
    public void getLikedBeanList() {
        mView.showContent(mRealmHelper.getLikeList());

    }

    @Override
    public void deleteLikedBean(String id) {
        mRealmHelper.deteleLikeBean(id);

    }

    @Override
    public void changeLikedTime(String id,long time,boolean isPlus) {
        mRealmHelper.changeLikeTime(id,time,isPlus);
    }


}
