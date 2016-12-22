package com.example.jon.fangnews.presenter;

import android.util.Log;

import com.example.jon.fangnews.base.RxPresenter;
import com.example.jon.fangnews.component.RxBus;
import com.example.jon.fangnews.model.bean.DailyBeforeListBean;
import com.example.jon.fangnews.model.bean.DailyListBean;
import com.example.jon.fangnews.model.db.RealmHelper;
import com.example.jon.fangnews.model.http.RetrofitHelper;
import com.example.jon.fangnews.presenter.contract.DailyContract;
import com.example.jon.fangnews.utils.DateUtils;
import com.orhanobut.logger.Logger;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jon on 2016/12/8.
 */

public class DailyPresenter extends RxPresenter<DailyContract.View> implements DailyContract.Presenter {
    private RealmHelper mRealmHelper;
    private RetrofitHelper mRetrofitHelper;

    private static final int INTERVAL = 6;

    private Subscription mIntervalSubscription;

    private int mTopCount = 0;
    private int mCurrentTopCount = 0;

    @Inject
    public DailyPresenter(RetrofitHelper retrofitHelper, RealmHelper realmHelper){
        this.mRealmHelper = realmHelper;
        this.mRetrofitHelper = retrofitHelper;
        registerEvent();
    }


    private void registerEvent(){
        Subscription rxSubscription = RxBus.getDefault().toObservable(CalendarDay.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CalendarDay, String>() {
                    @Override
                    public String call(CalendarDay calendarDay) {
                        mView.showProgress();
                        StringBuilder builder = new StringBuilder();
                        String year = String.valueOf(calendarDay.getYear());
                        String month = String.valueOf(calendarDay.getMonth()+1);
                        String day = String.valueOf(calendarDay.getDay()+1);
                        Log.d("data","选择日期"+year+":"+month+":"+day);
                        Log.d("data","currentDate : "+DateUtils.getTomorrowDate());
                        if(month.length() == 1){
                            month = "0"+month;
                        }
                        if(day.length() == 1){
                            day = "0"+day;

                        }
                        Log.d("data",year+month+day);


                        return builder.append(year).append(month).append(day).toString();
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        if(s.equals(DateUtils.getTomorrowDate())){
                            getDailyData();
                            return false;
                        }
                        return true;
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Func1<String, Observable<DailyBeforeListBean>>() {
                    @Override
                    public Observable<DailyBeforeListBean> call(String s) {
                        return mRetrofitHelper.fetchDailyBeforeListInfo(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<DailyBeforeListBean, DailyBeforeListBean>() {
                    @Override
                    public DailyBeforeListBean call(DailyBeforeListBean dailyBeforeListBean) {
                        List<DailyListBean.StoryBean> stories = dailyBeforeListBean.getStories();
                        for (DailyListBean.StoryBean story : stories){
                            story.setReaded(mRealmHelper.queryIsReaded(story.getId()));
                        }
                        return dailyBeforeListBean;
                    }
                })
                .subscribe(new Action1<DailyBeforeListBean>() {
                    @Override
                    public void call(DailyBeforeListBean dailyBeforeListBean) {
                        mView.showMoreContent(dailyBeforeListBean.getDate(), dailyBeforeListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.toString());
                        mView.showError("加载出错了/(ㄒoㄒ)/~~");
                    }
                });
        addSubscribe(rxSubscription);


    }
    @Override
    public void getDailyData() {
        mView.showProgress();
        Log.d("data","加载");
        Subscription subscription = mRetrofitHelper.fetchDailyListInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<DailyListBean, DailyListBean>() {
                    @Override
                    public DailyListBean call(DailyListBean dailyListBean) {
                        Log.d("data","这到了吗");
                        List<DailyListBean.StoryBean> lists = dailyListBean.getStories();
                        for(DailyListBean.StoryBean story : lists){
                           story.setReaded(mRealmHelper.queryIsReaded(story.getId()));
                        }
                        return dailyListBean;
                    }
                })
                .subscribe(new Action1<DailyListBean>() {
                    @Override
                    public void call(DailyListBean dailyListBean) {

                        mTopCount = dailyListBean.getTop_stories().size();
                        mView.showContent(dailyListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.d(throwable);
                        mView.showError("加载出错啦o(╯□╰)o");

                    }
                });
        addSubscribe(subscription);

    }

    @Override
    public void getBeforeData(String date) {
        mView.showProgress();
        Subscription subscription = mRetrofitHelper.fetchDailyBeforeListInfo(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<DailyBeforeListBean, DailyBeforeListBean>() {
                    @Override
                    public DailyBeforeListBean call(DailyBeforeListBean dailyBeforeListBean) {
                        List<DailyListBean.StoryBean> list  = dailyBeforeListBean.getStories();
                        for(DailyListBean.StoryBean storyBean : list){
                            storyBean.setReaded(mRealmHelper.queryIsReaded(storyBean.getId()));
                        }
                        return dailyBeforeListBean;
                    }
                })
                .subscribe(new Action1<DailyBeforeListBean>() {
                    @Override
                    public void call(DailyBeforeListBean dailyBeforeListBean) {
                        int year = Integer.valueOf(dailyBeforeListBean.getDate().substring(0,4));
                        int month = Integer.valueOf(dailyBeforeListBean.getDate().substring(4,6));
                        int day = Integer.valueOf(dailyBeforeListBean.getDate().substring(6,8));

                        mView.showMoreContent(String.format("%d年%d月%d日",year,month,day),dailyBeforeListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("加载出错( ⊙ o ⊙ )！");
                    }
                });
        addSubscribe(subscription);


    }

    @Override
    public void startInterval() {
        mIntervalSubscription = Observable.interval(INTERVAL, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if(mCurrentTopCount >= mTopCount)
                            mCurrentTopCount = 0;
                        mView.doInterval(mCurrentTopCount++);
                    }
                });
        addSubscribe(mIntervalSubscription);
    }

    @Override
    public void stopInterval() {
        if(mIntervalSubscription != null){
            mIntervalSubscription.unsubscribe();
        }


    }

    @Override
    public void insertReadedToDB(int id) {
       mRealmHelper.insertReadedId(id);

    }
}
