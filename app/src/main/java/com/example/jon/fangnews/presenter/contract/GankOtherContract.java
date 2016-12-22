package com.example.jon.fangnews.presenter.contract;

import com.example.jon.fangnews.base.BasePresenter;
import com.example.jon.fangnews.base.BaseView;
import com.example.jon.fangnews.model.bean.GankItemBean;

import java.util.List;

/**
 * Created by jon on 2016/12/15.
 */

public interface GankOtherContract {
    interface View extends BaseView{
        void showContent(List<GankItemBean> gankItemBean);
        void showMoreContent(List<GankItemBean> gankItemBean);
        void showGirl(List<GankItemBean> gankItemBeen);
        void showProgress();
    }

    interface Presenter extends BasePresenter<View>{
        void getGankData(int type);
        void getGankMoreData(int type);
        void getRandomGirl();
        void clearSearch();
    }
}
