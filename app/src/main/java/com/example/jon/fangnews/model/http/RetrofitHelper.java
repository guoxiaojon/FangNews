package com.example.jon.fangnews.model.http;

import com.example.jon.fangnews.app.Constants;
import com.example.jon.fangnews.model.bean.DailyBeforeListBean;
import com.example.jon.fangnews.model.bean.DailyListBean;
import com.example.jon.fangnews.model.bean.GankItemBean;
import com.example.jon.fangnews.model.bean.WeChatBean;
import com.example.jon.fangnews.model.bean.WelcomeBean;
import com.example.jon.fangnews.model.bean.ZhiHuCommentBean;
import com.example.jon.fangnews.model.bean.ZhiHuDetailBean;
import com.example.jon.fangnews.model.bean.ZhiHuDetailExtraBean;
import com.example.jon.fangnews.model.bean.ZhiHuHotListBean;
import com.example.jon.fangnews.model.bean.ZhiHuSectionContentBean;
import com.example.jon.fangnews.model.bean.ZhiHuSectionListBean;
import com.example.jon.fangnews.model.bean.ZhiHuThemeContentBean;
import com.example.jon.fangnews.model.bean.ZhiHuThemeListBean;
import com.example.jon.fangnews.utils.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;


/**
 * Created by jon on 2016/12/5.
 */

public class RetrofitHelper {

    private static OkHttpClient mOkHttpClient;
    private static ZhiHuApis mZhiHuApiService;
    private static WeChatApis mWeChatApis;
    private static GankApis mGankApis;


    public RetrofitHelper(){
        init();

    }

    private void init() {
        initOkHttpClient();
        mZhiHuApiService = getmZhiHuApiService();
        mWeChatApis = getWeChatApiSetvice();
        mGankApis = getGankApiService();

    }

    private void initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Interceptor cacheIntercept = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if(!SystemUtil.isNetAvailable()){
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);

                return response;
            }
        };


        //缓存
        long availabeSpace = SystemUtil.getPhoneInnerAvailiabeSpaceMB();
        boolean canCache = false;
        if(availabeSpace > 50) canCache = true;
        if(canCache){
            File cacheFile = new File(Constants.PATH_CACHE);
            Cache cache = new Cache(cacheFile,1024 * 1024 * 50);
            builder.cache(cache);
            builder.addInterceptor(cacheIntercept);
            builder.addNetworkInterceptor(cacheIntercept);
        }

        //超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20,TimeUnit.SECONDS);
        builder.writeTimeout(20,TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        mOkHttpClient = builder.build();
    }

    private ZhiHuApis getmZhiHuApiService(){
        Retrofit zhihuRetrofit = new Retrofit.Builder()
                .baseUrl(ZhiHuApis.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
        return zhihuRetrofit.create(ZhiHuApis.class);

    }
    private WeChatApis getWeChatApiSetvice(){
        Retrofit weChatRetrofit = new Retrofit.Builder()
                .baseUrl(WeChatApis.HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build();
        return weChatRetrofit.create(WeChatApis.class);
    }

    public GankApis getGankApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankApis.HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build();
        return retrofit.create(GankApis.class);
    }

    //知乎

    public Observable<WelcomeBean> fetchWelcomeInfo(String res){
        return mZhiHuApiService.getWelcomeInfo(res);
    }
    public Observable<DailyListBean> fetchDailyListInfo(){
        return mZhiHuApiService.getDailyList();
    }

    public Observable<DailyBeforeListBean> fetchDailyBeforeListInfo(String date){
        return mZhiHuApiService.getDailyBeforeList(date);
    }
    public Observable<ZhiHuThemeListBean> fetchZhiHuThemeListInfo(){
        return mZhiHuApiService.getThemeList();
    }
    public Observable<ZhiHuThemeContentBean> fetchZhiHuThemeContentInfo(int id){
        return mZhiHuApiService.getThemeContent(id);
    }
    public Observable<ZhiHuSectionListBean> fetchZhiHuSectionInfo(){
        return mZhiHuApiService.getSectionList();
    }
    public Observable<ZhiHuSectionContentBean> fetchZhiHuSectionContentInfo(int id){
        return mZhiHuApiService.getSectionContent(id);
    }
    public Observable<ZhiHuHotListBean> fetchZhiHuHotListInfo(){
        return mZhiHuApiService.getHotList();
    }
    public Observable<ZhiHuDetailBean> fetchZhiHuDetailInfo(int id){
        return mZhiHuApiService.getDetail(id);
    }
    public Observable<ZhiHuDetailExtraBean> fetchZhiHuDetailExtraInfo(int id){
        return mZhiHuApiService.getDetailExtra(id);
    }
    public Observable<ZhiHuCommentBean> fetchZhiHuLongComments(int id){
        return mZhiHuApiService.getLongComments(id);
    }
    public Observable<ZhiHuCommentBean> fetchZhiHuShortComments(int id){
        return mZhiHuApiService.getShortComments(id);
    }

    //微信

    public Observable<WeChatHttpResponse<List<WeChatBean>>> fetchWeChatHotList(int num,int page){
        return mWeChatApis.getWeChatHotList(Constants.WECHAT_KEY, num, page);
    }

    public Observable<WeChatHttpResponse<List<WeChatBean>>> fetchWeChatHotSearch(int num,String word,int page){
        return mWeChatApis.getWeChatHotSearchList(Constants.WECHAT_KEY, num, word, page);
    }

    //干货集中营

    public Observable<GankHttpResponse<List<GankItemBean>>> fetchGankDataList(String type,int num,int page){
        return mGankApis.getGankDataList(type, num, page);

    }

    public Observable<GankHttpResponse<List<GankItemBean>>> fetchRandomGirlDataList(int num){
        return mGankApis.getRandomDataList("福利",num);
    }
    public Observable<GankHttpResponse<List<GankItemBean>>> fetchGankDataListWithSearch(String query,String type,int num,int page){
        return mGankApis.getGankDateListWithSearch(query, type, num, page);
    }



}
