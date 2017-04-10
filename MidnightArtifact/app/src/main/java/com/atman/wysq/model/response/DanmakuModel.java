package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/4/10.
 */

public class DanmakuModel {
    /**
     * result : 1
     * body : [{"blog_comment_id":275850,"blog_id":185979,"user_id":6000000266,"content":"是阿卡","status":1,"create_time":1490355782000,"icon":"/imageServer/DC1Ec5c24d27e1894e16aaee5502e7d84da0.jpg","unread_flag":1},{"blog_comment_id":275849,"blog_id":185979,"user_id":6000000266,"content":"路途太过美好","status":1,"create_time":1490355669000,"icon":"/imageServer/DC1Ec5c24d27e1894e16aaee5502e7d84da0.jpg","unread_flag":1}]
     */

    private String result;
    private List<BodyBean> body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * blog_comment_id : 275850
         * blog_id : 185979
         * user_id : 6000000266
         * content : 是阿卡
         * status : 1
         * create_time : 1490355782000
         * icon : /imageServer/DC1Ec5c24d27e1894e16aaee5502e7d84da0.jpg
         * unread_flag : 1
         */

        private long blog_comment_id;
        private long blog_id;
        private long user_id;
        private String content;
        private int status;
        private long create_time;
        private String icon;
        private int unread_flag;

        public long getBlog_comment_id() {
            return blog_comment_id;
        }

        public void setBlog_comment_id(long blog_comment_id) {
            this.blog_comment_id = blog_comment_id;
        }

        public long getBlog_id() {
            return blog_id;
        }

        public void setBlog_id(long blog_id) {
            this.blog_id = blog_id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

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

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getUnread_flag() {
            return unread_flag;
        }

        public void setUnread_flag(int unread_flag) {
            this.unread_flag = unread_flag;
        }
    }
}
