package com.example.jon.fangnews.presenter.contract;

import com.example.jon.fangnews.base.BasePresenter;
import com.example.jon.fangnews.base.BaseView;
import com.example.jon.fangnews.model.bean.DailyBeforeListBean;
import com.example.jon.fangnews.model.bean.DailyListBean;

/**
 * Created by jon on 2016/12/8.
 */

public interface DailyContract {
    interface View extends BaseView{
        void showContent(DailyListBean dailyListBean);
        void showMoreContent(String date,DailyBeforeListBean dailyBeforeListBean);
        void showProgress();
        void doInterval(int currentCount);

    }
    interface Presenter extends BasePresenter<View> {
        void getDailyData();
        void getBeforeData(String date);
        void startInterval();
        void stopInterval();
        void insertReadedToDB(int id);



    }
}
