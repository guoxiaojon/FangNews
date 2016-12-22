package com.example.jon.fangnews.presenter.contract;

import com.example.jon.fangnews.base.BasePresenter;
import com.example.jon.fangnews.base.BaseView;
import com.example.jon.fangnews.model.bean.RealmLikeBean;

import java.util.List;

/**
 * Created by jon on 2016/12/16.
 */

public interface MainLikeContract  {
    interface View extends BaseView{
        void showContent(List<RealmLikeBean> list);
    }
    interface Presenter extends BasePresenter<View>{
        void getLikedBeanList();
        void deleteLikedBean( String id);
        void changeLikedTime(String id,long time,boolean isPlus);
    }

}
