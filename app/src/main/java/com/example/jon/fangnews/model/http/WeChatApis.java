package com.example.jon.fangnews.model.http;

import com.example.jon.fangnews.model.bean.WeChatBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jon on 2016/12/14.
 */

public interface WeChatApis {
    String HOST =  "http://api.tianapi.com/";

    /**
     * key APIKEY
     * num 每页返回的最大数据,15
     * word 查询关键字
     * page 第几页，默认1
     * */
    /**
     * 热门精选列表*/
    @GET("wxnew")
    Observable<WeChatHttpResponse<List<WeChatBean>>> getWeChatHotList(
            @Query("key") String key,
            @Query("num") int num,
            @Query("page") int page);

    @GET("wxnew")
    Observable<WeChatHttpResponse<List<WeChatBean>>> getWeChatHotSearchList(
            @Query("key") String key,
            @Query("num") int num,
            @Query("word") String word,
            @Query("page") int page);



}
