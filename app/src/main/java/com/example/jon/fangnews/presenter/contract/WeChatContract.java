package com.example.jon.fangnews.presenter.contract;

import com.example.jon.fangnews.base.BasePresenter;
import com.example.jon.fangnews.base.BaseView;
import com.example.jon.fangnews.model.bean.WeChatBean;

import java.util.List;

/**
 * Created by jon on 2016/12/14.
 */

public interface WeChatContract  {
    interface View extends BaseView{
        void showHotList(List<WeChatBean> list);
        void showHotMoreList(List<WeChatBean> list);
    }
    interface Presenter extends BasePresenter<View>{
        void getHotList();
        void getHotMoreList();

    }
}
