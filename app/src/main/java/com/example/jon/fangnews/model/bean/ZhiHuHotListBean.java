package com.example.jon.fangnews.model.bean;

import java.util.List;

/**
 * Created by jon on 2016/12/10.
 */

public class ZhiHuHotListBean {
    /**
     * {
     recent: [
     {
     news_id: 3748552,
     url: "http://daily.zhihu.com/api/2/news/3748552",
     thumbnail: "http://p3.zhimg.com/67/6a/676a8337efec71a100eea6130482091b.jpg",
     title: "长得漂亮能力出众性格单纯的姑娘为什么会没有男朋友？"
     },
     ...
     ]
     }*/
    private List<RecentBean> recent;

    public List<RecentBean> getRecent() {
        return recent;
    }

    public void setRecent(List<RecentBean> recent) {
        this.recent = recent;
    }


    public class RecentBean{
        private int new_id;
        private String url;
        private String thumbnail;
        private String title;

        public boolean isReaded() {
            return readed;
        }

        public void setReaded(boolean readed) {
            this.readed = readed;
        }

        private boolean readed;

        public int getNew_id() {
            return new_id;
        }

        public void setNew_id(int new_id) {
            this.new_id = new_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
