package com.example.jon.fangnews.model.bean;

import java.util.List;

/**
 * Created by jon on 2016/12/10.
 */

public class ZhiHuSectionListBean {
    /**
     * {
     data: [
     {
     id: 1,
     thumbnail: "http://p2.zhimg.com/10/b8/10b8193dd6a3404d31b2c50e1e232c87.jpg",
     name: "深夜食堂",
     description: "睡前宵夜，用别人的故事下酒"
     },
     ...
     ]
     }*/

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean{
        private int id;
        private String thumbnail;
        private String name;
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
