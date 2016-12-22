package com.example.jon.fangnews.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by jon on 2016/12/6.
 */

public class FragmentUtils {
    public static void showAndHideFragment(FragmentManager manager,Fragment show, Fragment hide){
        Log.d("data2","show:"+show +"hide:"+hide);
        if(show == hide )return;
        manager.beginTransaction()
                .show(show)
                .hide(hide)
                .commit();
    }
    public static void loadMultipleFragment(int container,int show,FragmentManager manager,Fragment...fragments){
        FragmentTransaction transaction =  manager.beginTransaction();
        for(int i=0; i<fragments.length;i++){
            Fragment currFragment = fragments[i];
            String tag = currFragment.getClass().getSimpleName();
            transaction.add(container,currFragment,tag);
            if(i != show) transaction.hide(currFragment);

        }
        transaction.commit();
    }
}
