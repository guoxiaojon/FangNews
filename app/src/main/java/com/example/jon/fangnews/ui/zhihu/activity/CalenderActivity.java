package com.example.jon.fangnews.ui.zhihu.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.example.jon.fangnews.R;
import com.example.jon.fangnews.base.SimpleActivity;
import com.example.jon.fangnews.component.RxBus;
import com.example.jon.fangnews.utils.DateUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jon on 2016/12/8.
 */

public class CalenderActivity extends SimpleActivity {
    @BindView(R.id.view_calender)
    MaterialCalendarView mCalender;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private CalendarDay mDate;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_calender;
    }

    @Override
    protected void initEventAndDate() {
        setToolBar(mToolbar,"选择日期");
        mCalender.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(2014,10,1))
                .setMaximumDate(CalendarDay.from(DateUtils.getCurrentYear(),DateUtils.getCurrentMonth(),DateUtils.getCurrentDay()))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        mCalender.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                mDate = date;
            }
        });

    }

    @OnClick(R.id.tv_calendar_enter)
    public void chooseDate(){
        RxBus.getDefault().post(mDate);
        finishAfterTransition();
    }
}
