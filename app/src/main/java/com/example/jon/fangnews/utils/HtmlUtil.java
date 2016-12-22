package com.example.jon.fangnews.utils;

import java.util.List;

/**
 * Created by jon on 2016/12/10.
 */

public class HtmlUtil {
    //css样式,隐藏header
    private static final String HIDE_HEADER_STYLE = "<style>div.headline{display:none;}</style>";

    //css style tag,需要格式化
    private static final String NEEDED_FORMAT_CSS_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";

    // js script tag,需要格式化
    private static final String NEEDED_FORMAT_JS_TAG = "<script src=\"%s\"></script>";

    public static final String MIME_TYPE = "text/html; charset=utf-8";

    public static final String ENCODING = "utf-8";

    private HtmlUtil() {}

    public static String createCssTag(String url){
        return String.format(NEEDED_FORMAT_CSS_TAG,url);
    }
    public static String createJsTag(String url){
        return String.format(NEEDED_FORMAT_JS_TAG,url);
    }

    public static String createCssTag(List<String> urls){
        StringBuilder builder = new StringBuilder();
        for(String url: urls){
            builder.append(String.format(NEEDED_FORMAT_CSS_TAG,url));
        }
        return builder.toString();
    }

    public static String createJsTag(List<String> urls){
        StringBuilder builder = new StringBuilder();
        for (String url: urls){
            builder.append(String.format(NEEDED_FORMAT_JS_TAG,url));
        }
        return builder.toString();
    }
    public static String createHtmlDate(String html,List<String> cssList,List<String> jsList){
        String css = createCssTag(cssList);
        String js = createJsTag(jsList);
        return css.concat(HIDE_HEADER_STYLE).concat(html).concat(js);

    }
}
