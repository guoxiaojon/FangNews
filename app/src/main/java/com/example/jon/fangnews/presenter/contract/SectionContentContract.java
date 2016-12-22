package com.example.jon.fangnews.presenter.contract;

import com.example.jon.fangnews.base.BasePresenter;
import com.example.jon.fangnews.base.BaseView;
import com.example.jon.fangnews.model.bean.ZhiHuSectionContentBean;

/**
 * Created by jon on 2016/12/13.
 */

public interface SectionContentContract  {
    interface View extends BaseView{
        void showSectionContent(ZhiHuSectionContentBean zhiHuSectionContentBean);
    }
    interface Presenter extends BasePresenter<View>{
        void getSectionContent(int id);
        void insertReadedToDB(int id);
    }
}
