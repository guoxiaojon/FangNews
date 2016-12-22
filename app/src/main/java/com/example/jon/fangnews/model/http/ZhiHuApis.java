package com.example.jon.fangnews.model.http;

import com.example.jon.fangnews.model.bean.DailyBeforeListBean;
import com.example.jon.fangnews.model.bean.DailyListBean;
import com.example.jon.fangnews.model.bean.WelcomeBean;
import com.example.jon.fangnews.model.bean.ZhiHuCommentBean;
import com.example.jon.fangnews.model.bean.ZhiHuDetailBean;
import com.example.jon.fangnews.model.bean.ZhiHuDetailExtraBean;
import com.example.jon.fangnews.model.bean.ZhiHuHotListBean;
import com.example.jon.fangnews.model.bean.ZhiHuSectionContentBean;
import com.example.jon.fangnews.model.bean.ZhiHuSectionListBean;
import com.example.jon.fangnews.model.bean.ZhiHuThemeContentBean;
import com.example.jon.fangnews.model.bean.ZhiHuThemeListBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jon on 2016/12/5.
 */

public interface ZhiHuApis {

    String HOST =  "http://news-at.zhihu.com/api/4/";

    /**
     * 启动界面图片
     * */
    @GET("start-image/{res}")
    Observable<WelcomeBean> getWelcomeInfo(@Path("res") String res);

    /**
     * 当天日报
     * */
    @GET("news/latest")
    Observable<DailyListBean> getDailyList();

    /**
     * 往期日报
     * */
    @GET("news/before/{date}")
    Observable<DailyBeforeListBean> getDailyBeforeList(@Path("date") String date);

    /**
     * 主题日报 "themes"
     * */
    @GET("themes")
    Observable<ZhiHuThemeListBean> getThemeList();

    /**
     * 主题日报详情 "theme/{id}"
     * */
    @GET("theme/{id}")
    Observable<ZhiHuThemeContentBean> getThemeContent(@Path("id") int id);
    /**
     * 专栏日报 "sections"
     * */

    @GET("sections")
    Observable<ZhiHuSectionListBean> getSectionList();

    /**
     * 专栏日报详情 "section/{id}"
     * */

    @GET("section/{id}")
    Observable<ZhiHuSectionContentBean> getSectionContent(@Path("id") int id);

    /**
     * 热门日报 "news/hot"
     * */

    @GET("news/hot")
    Observable<ZhiHuHotListBean> getHotList();



    /**
     *日报详情 "news/{id}"
     * */
    @GET("news/{id}")
    Observable<ZhiHuDetailBean> getDetail(@Path("id") int id);
    /**
     * 日报额外信息 "story-extra/{id}"
     * */

    @GET("story-extra/{id}")
    Observable<ZhiHuDetailExtraBean> getDetailExtra(@Path("id") int id);

    /**
     *长评论 "story/{id}/long-comments"
     **/
    @GET("story/{id}/long-comments")
    Observable<ZhiHuCommentBean> getLongComments(@Path("id") int id);

    /**
     * 短评论 "story/{id}/short-comments"
     * */
    @GET("story/{id}/short-comments")
    Observable<ZhiHuCommentBean> getShortComments(@Path("id") int id);


}
