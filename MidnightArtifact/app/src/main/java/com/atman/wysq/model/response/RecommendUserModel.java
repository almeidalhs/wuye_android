package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/12/2.
 */

public class RecommendUserModel {
    /**
     * result : 1
     * body : [{"user_id":450000520,"integral":"100199","secret_type":0,"type":1,"icon":"/imageServer/B6ECe8c8191b09f246f19450b639ffba43f6.jpg","sex":"F","create_time":1438589416000,"update_time":1438589416000,"name_change":1,"next_level_integral":99800,"userLevel":9,"mobile":"13820000013","nick_name":"qwertt","status":2,"verify_status":1,"gold_coin":94635,"charm":1,"pic_url1":"/imageServer/prop/userbackground/woman/2814/3.jpg","chat_count":0},{"user_id":450000547,"integral":"49","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"F","create_time":1439865967000,"update_time":1439865967000,"name_change":0,"next_level_integral":150,"userLevel":2,"mobile":"13820000037","nick_name":"我破公司你了你寂寞哦","status":2,"verify_status":1,"gold_coin":316,"charm":0,"pic_url1":"/imageServer/prop/userbackground/man/2278/3.jpg","chat_count":0},{"user_id":450215771,"integral":"181","secret_type":0,"type":1,"icon":"/imageServer/9C66female.png","sex":"F","create_time":1480644274000,"update_time":1480644274000,"name_change":0,"next_level_integral":19,"userLevel":2,"mobile":"15968800038","nick_name":"夜友-516947","status":2,"verify_status":0,"gold_coin":15,"charm":0,"pic_url1":"/imageServer/prop/userbackground/woman/2820/1.jpg"}]
     */

    private String result;
    /**
     * user_id : 450000520
     * integral : 100199
     * secret_type : 0
     * type : 1
     * icon : /imageServer/B6ECe8c8191b09f246f19450b639ffba43f6.jpg
     * sex : F
     * create_time : 1438589416000
     * update_time : 1438589416000
     * name_change : 1
     * next_level_integral : 99800
     * userLevel : 9
     * mobile : 13820000013
     * nick_name : qwertt
     * status : 2
     * verify_status : 1
     * gold_coin : 94635
     * charm : 1
     * pic_url1 : /imageServer/prop/userbackground/woman/2814/3.jpg
     * chat_count : 0
     */

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
        private long user_id;
        private String integral;
        private int secret_type;
        private int type;
        private String icon;
        private String sex;
        private long create_time;
        private long update_time;
        private int name_change;
        private long next_level_integral;
        private int userLevel;
        private String mobile;
        private String nick_name;
        private int status;
        private int verify_status;
        private int gold_coin;
        private int charm;
        private String pic_url1;
        private int chat_count;

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public int getSecret_type() {
            return secret_type;
        }

        public void setSecret_type(int secret_type) {
            this.secret_type = secret_type;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public int getName_change() {
            return name_change;
        }

        public void setName_change(int name_change) {
            this.name_change = name_change;
        }

        public long getNext_level_integral() {
            return next_level_integral;
        }

        public void setNext_level_integral(long next_level_integral) {
            this.next_level_integral = next_level_integral;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getVerify_status() {
            return verify_status;
        }

        public void setVerify_status(int verify_status) {
            this.verify_status = verify_status;
        }

        public int getGold_coin() {
            return gold_coin;
        }

        public void setGold_coin(int gold_coin) {
            this.gold_coin = gold_coin;
        }

        public int getCharm() {
            return charm;
        }

        public void setCharm(int charm) {
            this.charm = charm;
        }

        public String getPic_url1() {
            return pic_url1;
        }

        public void setPic_url1(String pic_url1) {
            this.pic_url1 = pic_url1;
        }

        public int getChat_count() {
            return chat_count;
        }

        public void setChat_count(int chat_count) {
            this.chat_count = chat_count;
        }
    }
}
