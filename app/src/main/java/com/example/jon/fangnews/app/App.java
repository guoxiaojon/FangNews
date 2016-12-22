package com.example.jon.fangnews.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.jon.fangnews.component.CrashHandler;
import com.example.jon.fangnews.di.component.AppComponent;
import com.example.jon.fangnews.di.component.DaggerAppComponent;
import com.example.jon.fangnews.di.module.AppModule;
import com.example.jon.fangnews.widget.AppBlockCanaryContext;
import com.github.moduth.blockcanary.BlockCanary;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jon on 2016/12/5.
 */

public class App extends Application {
    private static App sInstance;
    private Set<Activity> mAllActivities;

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGH = -1;
    public static int DIMEN_DPI = -1;
    public static float DIMEN_RATE = -1;


    private static AppComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mAllActivities = new HashSet<Activity>();
        //初始化异常处理器
        CrashHandler.init(new CrashHandler(sInstance));

        //初始化屏幕宽高
        initScreenSize();

        //Logger
        Logger.init(getPackageName()).hideThreadInfo();

        //初始化内存泄露检查工具
        LeakCanary.install(this);

        //初始化过度绘制检查工具
        BlockCanary.install(this, new AppBlockCanaryContext()).start();

        //初始化x5
        QbSdk.allowThirdPartyAppDownload(true);
        QbSdk.initX5Environment(getApplicationContext(), QbSdk.WebviewInitType.FIRSTUSE_AND_PRELOAD, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
            }

            @Override
            public void onViewInitFinished(boolean b) {
            }
        });



        //保证单例

        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();


    }

    private void initScreenSize() {
        WindowManager wm =(WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);

        SCREEN_WIDTH = metrics.widthPixels;
        SCREEN_HEIGH = metrics.heightPixels;
        DIMEN_DPI = metrics.densityDpi;
        DIMEN_RATE = metrics.density/1.0f;
        if(SCREEN_WIDTH > SCREEN_HEIGH){//保证高大于宽
            int temp = SCREEN_WIDTH;
            SCREEN_WIDTH = SCREEN_HEIGH;
            SCREEN_HEIGH = SCREEN_WIDTH;
        }

    }

    public void addActivity(Activity activity){
        if(mAllActivities != null){
            mAllActivities.add(activity);
        }
    }

    public void removeActivity(Activity activity){
        if(mAllActivities != null){
            mAllActivities.remove(activity);
        }
    }


    public void exitApp(){
        if(mAllActivities !=null){
            synchronized (mAllActivities){
                for (Activity activity:mAllActivities){
                    activity.finish();
                }
            }
        }
        Process.killProcess(Process.myPid());
        System.exit(0);


    }

    public static App getInstance() {
        return sInstance;
    }

    public static AppComponent getAppComponent(){
        return mAppComponent;
    }


}
