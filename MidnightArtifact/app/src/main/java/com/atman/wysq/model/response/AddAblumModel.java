package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/4/18.
 */

public class AddAblumModel {
    /**
     * result : 1
     * body : [{"photo_id":381,"user_id":450214627,"pic_url":"/imageServer/5094bf771358e01149f9a250162aee43e13a.jpg","state":1}]
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
         * photo_id : 381
         * user_id : 450214627
         * pic_url : /imageServer/5094bf771358e01149f9a250162aee43e13a.jpg
         * state : 1
         */

        private long photo_id;
        private long user_id;
        private String pic_url;
        private int state;

        public long getPhoto_id() {
            return photo_id;
        }

        public void setPhoto_id(long photo_id) {
            this.photo_id = photo_id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
