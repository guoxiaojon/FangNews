package com.example.jon.fangnews.presenter.contract;

import com.example.jon.fangnews.base.BasePresenter;
import com.example.jon.fangnews.base.BaseView;
import com.example.jon.fangnews.model.bean.ZhiHuThemeContentBean;

/**
 * Created by jon on 2016/12/12.
 */

public interface ThemeContentContract {
    interface View extends BaseView{
        void showContent(ZhiHuThemeContentBean contentBean);
    }
    interface Presenter extends BasePresenter<View>{
        void getContent(int id);
        void insertReaded(int id);
    }
}
