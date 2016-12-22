package com.example.jon.fangnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.jon.fangnews.app.App;
import com.example.jon.fangnews.app.Constants;

/**
 * Created by jon on 2016/12/6.
 */

public class SharePreferenceUtil {

    private static final boolean DEFAULT_NIGHT_MODE = false;

    private static final int DEFAULT_CURRENT_ITEM = Constants.TYPE_ZHIHU;
    private static final boolean DEFAULT_NO_IMAGE = false;
    private static final boolean DEFAULT_AUTO_CACHE = false;

    private static final String SP_NAME = "my_sharepreferences";

    private static SharedPreferences getAppSP(){
        return App.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    //夜间模式
    public static void setNightMode(boolean isNight){
        getAppSP().edit().putBoolean(Constants.SP_NIGHT_MODE,isNight).apply();
    }
    public static boolean getNightMode(){
        return getAppSP().getBoolean(Constants.SP_NIGHT_MODE,DEFAULT_NIGHT_MODE);
    }

    public static boolean IsNightSetByEvent(){
        return getAppSP().getBoolean("setbyEvent",false);
    }
    public static void setNightSetByEvent(boolean setByEvent){
        getAppSP().edit().putBoolean("setbyEvent",setByEvent).apply();
    }

    //当前页
    public static void setCurrentItem(int currentItem){
        getAppSP().edit().putInt(Constants.SP_CURRENT_ITEM,currentItem).apply();
    }
    public static int getCurrentItem(){
        return getAppSP().getInt(Constants.SP_CURRENT_ITEM,DEFAULT_CURRENT_ITEM);
    }

    //是否无图模式
    public static void setNoImage(boolean noImage){
        getAppSP().edit().putBoolean(Constants.SP_NO_IMAGE,noImage).apply();
    }
    public static boolean getNoImage(){
        return getAppSP().getBoolean(Constants.SP_NO_IMAGE,DEFAULT_NO_IMAGE);
    }
    //是否自动缓存
    public static void setAutoCache(boolean autoCache){
        getAppSP().edit().putBoolean(Constants.SP_AUTO_CACHE,autoCache).apply();
    }
    public static boolean getAutoCache(){
        return getAppSP().getBoolean(Constants.SP_AUTO_CACHE,DEFAULT_AUTO_CACHE);
    }

}
