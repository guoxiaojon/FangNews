package com.example.jon.fangnews.presenter.contract;

import com.example.jon.fangnews.base.BasePresenter;
import com.example.jon.fangnews.base.BaseView;
import com.example.jon.fangnews.model.bean.ZhiHuDetailBean;
import com.example.jon.fangnews.model.bean.ZhiHuDetailExtraBean;

/**
 * Created by jon on 2016/12/10.
 */

public interface ZhiHuDetailContract {
    interface View extends BaseView{
        void showContent(ZhiHuDetailBean detailBean);
        void showExtraContent(ZhiHuDetailExtraBean detailExtraBean);
        void setLikeButtonState(boolean like);

    }
    interface Presenter extends BasePresenter<View>{
        void getDetailData(int id);
        void getDetailExtraData(int id);
        void insertLikeData();
        void deleteLikeData();
        void queryLikeData(int id);
    }

}
