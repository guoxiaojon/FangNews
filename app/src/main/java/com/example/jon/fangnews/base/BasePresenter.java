package com.example.jon.fangnews.base;

/**
 * Created by jon on 2016/12/5.
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);
    void detachView();
}
