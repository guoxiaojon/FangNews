package com.example.jon.fangnews.model.bean;

/**
 * Created by jon on 2016/12/14.
 */

public class WeChatSearchEvent {
    public WeChatSearchEvent(String search){
        this.searchWord = search;
    }
    private String searchWord;

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
}
