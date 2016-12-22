package com.example.jon.fangnews.component;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by jon on 2016/12/6.
 */

public class ImageLoader {
    public static void load(Activity activity, String url, ImageView imageView){
        if(!activity.isDestroyed()){
            Glide.with(activity)
                    .load(url)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }

    public static void load(Context context,String url,ImageView imageView){
        Glide.with(context)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
}
