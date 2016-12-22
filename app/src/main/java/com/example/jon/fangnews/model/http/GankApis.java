package com.example.jon.fangnews.model.http;

import com.example.jon.fangnews.model.bean.GankItemBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jon on 2016/12/15.
 */

public interface GankApis {
    String HOST = "http://gank.io/api/";
    /**
     * http://gank.io/api/data/Android/10/1
     * 获取数据
     * */
    @GET("data/{type}/{num}/{page}")
    Observable<GankHttpResponse<List<GankItemBean>>> getGankDataList(
            @Path("type") String type,
            @Path("num" )int num,
            @Path("page") int page);



    /**
     *  http://gank.io/api/random/data/分类/个数
     *  获取随机数据
     * */
    @GET("random/data/{type}/{num}")
    Observable<GankHttpResponse<List<GankItemBean>>> getRandomDataList(
            @Path("type") String type,
            @Path("num") int num);

    /**
     * http://gank.io/api/search/query/listview/category/Android/count/10/page/1
     * 搜索
     * */
    @GET("search/query/{query}/category/{type}/count/{num}/page/{page}")
    Observable<GankHttpResponse<List<GankItemBean>>> getGankDateListWithSearch(
            @Path("query") String query,
            @Path("type") String type,
            @Path("num") int num,
            @Path("page") int page);
}
