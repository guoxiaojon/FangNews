package com.example.jon.fangnews.component;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import com.example.jon.fangnews.app.App;
import com.orhanobut.logger.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by jon on 2016/12/5.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static Thread.UncaughtExceptionHandler sDefaultExceptionHandler = null;
    private static String TAG = CrashHandler.class.getSimpleName();

    private Context mContext;

    public CrashHandler(Context context){
        mContext = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Logger.e(throwable.toString());
        Logger.e(TAG,getCrashInfo(throwable));
        Logger.e(TAG,collectCrashDeviceInfo());
        sDefaultExceptionHandler.uncaughtException(thread,throwable);
        Toast.makeText(mContext,"抱歉，程序发生异常，退出",Toast.LENGTH_LONG).show();
        App.getInstance().exitApp();

    }

    public String getCrashInfo(Throwable throwable){
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        throwable.printStackTrace(printWriter);
        return printWriter.toString();

    }

    public String collectCrashDeviceInfo(){
        try {
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES);
            String versionName = packageInfo.versionName;
            String model = Build.MODEL;//手机型号
            String androidVersion = packageInfo.versionName;
            String manufacturer = Build.MANUFACTURER;
            return versionName+"  "+model+"  "+androidVersion+"  "+manufacturer;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public static void init(CrashHandler crashHandler){
        sDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }






}
