package com.example.jon.fangnews.presenter.contract;

import com.example.jon.fangnews.base.BasePresenter;
import com.example.jon.fangnews.base.BaseView;
import com.example.jon.fangnews.model.bean.ZhiHuSectionListBean;

/**
 * Created by jon on 2016/12/13.
 */

public interface SectionContract  {
    interface View extends BaseView{
        void showSection(ZhiHuSectionListBean zhiHuSectionListBean);
    }
    interface Presenter extends BasePresenter<View>{
        void getSectionList();
    }
}
