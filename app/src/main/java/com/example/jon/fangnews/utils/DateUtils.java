package com.example.jon.fangnews.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jon on 2016/12/8.
 */

public class DateUtils {
    private static Calendar sCalendar = Calendar.getInstance();
    public static int getCurrentYear(){
        return sCalendar.get(Calendar.YEAR);
    }
    public static int getCurrentMonth(){
        return sCalendar.get(Calendar.MONTH);
    }
    public static int getCurrentDay(){
        return sCalendar.get(Calendar.DATE);
    }

    public static String getTomorrowDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return String.valueOf(Integer.valueOf(dateFormat.format(new Date()))+1);
    }
    public static String getCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(new Date());
    }
    public static String formatTime2String(long time){
        String str = "";
        long distance = System.currentTimeMillis()/1000 - time;
        if(distance < 300){
            str = "刚刚";
        }else if(distance<600 && distance>=300){
            str = "5分钟前";
        }else if(distance <1200 && distance >= 600){
            str = "10分钟前";
        }else if(distance <1800 && distance >= 1200){
            str = "20分钟前";
        }else if(distance <3000 && distance >= 1800){
            str = "半小时前";
        }else {
            Date date = new Date(time*1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
            str = sdf.format(date);

        }
        return str+"时";
    }
}
