package com.example.jon.fangnews.presenter.contract;

import com.example.jon.fangnews.base.BasePresenter;
import com.example.jon.fangnews.base.BaseView;
import com.example.jon.fangnews.model.bean.ZhiHuCommentBean;

/**
 * Created by jon on 2016/12/12.
 */

public interface ZhiHuCommentContract {
    interface View extends BaseView{
        void showComments(ZhiHuCommentBean zhiHuCommentBean);
    }
    interface Presenter extends BasePresenter<View>{
        void getLongComments(int id);
        void getShortComments(int id);

    }

}
