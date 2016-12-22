package com.example.jon.fangnews.model.bean;

/**
 * Created by jon on 2016/12/15.
 */

public class GankItemBean {
    /**
     *"_id": "585096f2421aa93437406727",
     "createdAt": "2016-12-14T08:48:50.506Z",
     "desc": "12-14",
     "publishedAt": "2016-12-14T11:39:22.686Z",
     "source": "chrome",
     "type": "\u798f\u5229",
     "url": "http://ww2.sinaimg.cn/large/610dc034gw1faq15nnc0xj20u00u0wlq.jpg",
     "used": true,
     "who": "\u4ee3\u7801\u5bb6"*/

    private String _id;
    private String createAt;
    private String desc;
    private String publishedAt;
    private String chrome;
    private String type;
    private String url;
    private String who;

    public int getHeigh() {
        return heigh;
    }

    public void setHeigh(int heigh) {
        this.heigh = heigh;
    }

    private int heigh;

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    private boolean readed;

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getChrome() {
        return chrome;
    }

    public void setChrome(String chrome) {
        this.chrome = chrome;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
