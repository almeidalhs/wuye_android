package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/5 10:53
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetRewardListNewModel {
    /**
     * result : 1
     * body : [{"user_id":100003790,"user_name":"夜友-515857","icon":"/imageServer/9C66male.png","sex":"M","userLevel":1,"gift_num":1,"gift_name":"气球","pic_url":"/imageServer/prop/gift/balloon.png"}]
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
         * user_id : 100003790
         * user_name : 夜友-515857
         * icon : /imageServer/9C66male.png
         * sex : M
         * userLevel : 1
         * gift_num : 1
         * gift_name : 气球
         * pic_url : /imageServer/prop/gift/balloon.png
         */

        private long user_id;
        private String user_name;
        private String icon;
        private String sex;
        private int userLevel;
        private int gift_num;
        private String gift_name;
        private String pic_url;

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public int getGift_num() {
            return gift_num;
        }

        public void setGift_num(int gift_num) {
            this.gift_num = gift_num;
        }

        public String getGift_name() {
            return gift_name;
        }

        public void setGift_name(String gift_name) {
            this.gift_name = gift_name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }
    }
}
