package com.example.jon.fangnews.model.bean;

import java.util.List;

/**
 * Created by jon on 2016/12/8.
 */

public class DailyBeforeListBean {
    private String date;
    private List<DailyListBean.StoryBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DailyListBean.StoryBean> getStories() {
        return stories;
    }

    public void setStories(List<DailyListBean.StoryBean> stories) {
        this.stories = stories;
    }
}
