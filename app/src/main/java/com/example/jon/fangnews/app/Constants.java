package com.example.jon.fangnews.app;

import java.io.File;

/**
 * Created by jon on 2016/12/5.
 */

public class Constants {
    //TYPE

    public static final int TYPE_ZHIHU = 0x1;
    public static final int TYPE_WECHAT = 0x2;
    public static final int TYPE_GANK = 0x3;
    public static final int TYPE_LIKE = 0x4;
    public static final int TYPE_SETTING = 0x5;
    public static final int TYPE_ABOUT = 0x6;

    public static final int TYPE_LONG_COMMENT = 0x7;
    public static final int TYPE_SHORT_COMMENT = 0x8;

    public static final int TYPE_ANDROID = 0x9;
    public static final int TYPE_VIDEO = 0xB;
    public static final int TYPE_GIRL = 0xC;
    public static final int TYPE_RECOMM = 0xD;

    //PATH
    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath()+ File.separator+"data";
    public static final String PATH_CACHE = PATH_DATA + File.separator + "NetCache";

    //SP NAME

    public static final String SP_NIGHT_MODE = "night_mode";
    public static final String SP_CURRENT_ITEM = "current_item";
    public static final String SP_NO_IMAGE = "no_image";
    public static final String SP_AUTO_CACHE = "auto_cache";

    //KEY
    public static final String WECHAT_KEY = "52b7ec3471ac3bec6846577e79f20e4c";
}
