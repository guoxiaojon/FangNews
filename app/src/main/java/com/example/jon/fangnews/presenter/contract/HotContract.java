package com.example.jon.fangnews.presenter.contract;

import com.example.jon.fangnews.base.BasePresenter;
import com.example.jon.fangnews.base.BaseView;
import com.example.jon.fangnews.model.bean.ZhiHuHotListBean;

/**
 * Created by jon on 2016/12/13.
 */

public interface HotContract {
    interface View extends BaseView{
        void showHotList(ZhiHuHotListBean hotListBean);
    }
    interface Presenter extends BasePresenter<View>{
        void getHotList();
        void insertReadedToDB(int id);
    }

}
