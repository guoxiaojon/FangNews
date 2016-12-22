package com.example.jon.fangnews.presenter.contract;

import com.example.jon.fangnews.base.BasePresenter;
import com.example.jon.fangnews.base.BaseView;
import com.example.jon.fangnews.model.bean.WelcomeBean;

/**
 * Created by jon on 2016/12/5.
 */

public interface WelcomeContract {
    interface View extends BaseView{
        void showContent(WelcomeBean welcomeBean);
        void jumpToMainActivity();

    }
    interface Presenter extends BasePresenter<View>{
        void getWelcomeData();

    }


}
