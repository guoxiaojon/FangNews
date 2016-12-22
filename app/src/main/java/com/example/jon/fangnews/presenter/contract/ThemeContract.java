package com.example.jon.fangnews.presenter.contract;

import com.example.jon.fangnews.base.BasePresenter;
import com.example.jon.fangnews.base.BaseView;
import com.example.jon.fangnews.model.bean.ZhiHuThemeListBean;

/**
 * Created by jon on 2016/12/12.
 */

public interface ThemeContract {
    interface View extends BaseView{
        void showTheme(ZhiHuThemeListBean listBean);
    }
    interface Presenter extends BasePresenter<View>{
        void getThemeList();
    }

}
