package com.example.jon.fangnews.model.bean;

import java.util.List;

/**
 * Created by jon on 2016/12/10.
 */

public class ZhiHuThemeContentBean {
    /**
     * {
     stories: [
     {
     images: [
     "http://pic1.zhimg.com/84dadf360399e0de406c133153fc4ab8_t.jpg"
     ],
     type: 0,
     id: 4239728,
     title: "前苏联监狱纹身百科图鉴"
     },
     ...
     ],

     description: "为你发现最有趣的新鲜事，建议在 WiFi 下查看",
     background: "http://pic1.zhimg.com/a5128188ed788005ad50840a42079c41.jpg",

     color: 8307764,
     name: "不许无聊",
     image: "http://pic3.zhimg.com/da1fcaf6a02d1223d130d5b106e828b9.jpg",
     editors: [
     {
     url: "http://www.zhihu.com/people/wezeit",
     bio: "微在 Wezeit 主编",
     id: 70,
     avatar: "http://pic4.zhimg.com/068311926_m.jpg",
     name: "益康糯米"
     },
     ...
     ],
     image_source: ""
     }*/

    private List<StoryBean> stories;
    private String description;
    private String background;
    private int color;
    private String name;
    private String image;
    private List<EditorBean> editors;

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    private String image_source;

    public List<StoryBean> getStories() {
        return stories;
    }

    public void setStories(List<StoryBean> stories) {
        this.stories = stories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<EditorBean> getEditors() {
        return editors;
    }

    public void setEditors(List<EditorBean> editors) {
        this.editors = editors;
    }

    public class EditorBean {
        private String url;
        private String bio;
        private int id;
        private String avatar;
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }













    public class StoryBean {
        public boolean isReaded() {
            return readed;
        }

        public void setReaded(boolean readed) {
            this.readed = readed;
        }

        private boolean readed;
        private int type;
        private int id;
        private String title;
        List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
