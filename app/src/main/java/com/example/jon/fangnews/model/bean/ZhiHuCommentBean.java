package com.example.jon.fangnews.model.bean;

import java.util.List;

/**
 * Created by jon on 2016/12/10.
 */

public class ZhiHuCommentBean {
    /**
     * {
     "comments": [
     {
     "author":"巨型黑娃儿",
     "content":"也不算逻辑问题。其实小时候刚刚听说这个玩意的时候我也奇...",
     "avatar":"http://pic3.zhimg.com/4131a3385c748c9e2d02ab80e29a0c52_im.jpg",
     "time":1479706360,
     "reply_to":{
     "content":"第二个机灵抖的还是有逻辑问题，不该说忘了，应该说没喝过啊我也不知道",
     "status":0,
     "id":27275308,
     "author":"2233155495"
     },
     "id":27276057,
     "likes":2
     },
     ...
     ]
     }
     * */
    private List<CommentBean> comments;

    public List<CommentBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }

    public class CommentBean{
        private String author;
        private String content;
        private String avatar;
        private int time;
        private ReplyToBean reply_to;
        private int id;
        private int likes;
        //判断是否需要展开
        private int expand;

        public int getExpand() {
            return expand;
        }

        public void setExpand(int expand) {
            this.expand = expand;
        }



        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public ReplyToBean getReply_to() {
            return reply_to;
        }

        public void setReply_to(ReplyToBean reply_to) {
            this.reply_to = reply_to;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }
    }

    public class ReplyToBean{
        /**
         * "content":"第二个机灵抖的还是有逻辑问题，不该说忘了，应该说没喝过啊我也不知道",
         "status":0,
         "id":27275308,
         "author":"2233155495"*/

        private String content;
        private int status;
        private int id;
        private String author;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
