package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/3/20.
 */

public class DiscoverNewModel {
    /**
     * result : 1
     * body : [{"user_id":450000547,"integral":"49","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"F","create_time":1439865967000,"update_time":1439865967000,"name_change":0,"next_level_integral":150,"userLevel":2,"mobile":"13820000037","nick_name":"我破公司你了你寂寞哦","status":2,"verify_status":1,"can_live_room":1,"gold_coin":316,"charm":0,"can_chat":1,"can_shake":1,"convert_coin":0,"pic_url1":"/imageServer/prop/userbackground/man/3349/1.jpg","chat_count":0,"accrue_coin":496,"view_count":"470"}]
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
         * user_id : 450000547
         * integral : 49
         * secret_type : 0
         * type : 1
         * icon : /imageServer/9C66male.png
         * sex : F
         * create_time : 1439865967000
         * update_time : 1439865967000
         * name_change : 0
         * next_level_integral : 150
         * userLevel : 2
         * mobile : 13820000037
         * nick_name : 我破公司你了你寂寞哦
         * status : 2
         * verify_status : 1
         * can_live_room : 1
         * gold_coin : 316
         * charm : 0
         * can_chat : 1
         * can_shake : 1
         * convert_coin : 0
         * pic_url1 : /imageServer/prop/userbackground/man/3349/1.jpg
         * chat_count : 0
         * accrue_coin : 496
         * view_count : 470
         */

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
        private int can_live_room;
        private long gold_coin;
        private int charm;
        private int can_chat;
        private int can_shake;
        private int convert_coin;
        private String pic_url1;
        private int chat_count;
        private int accrue_coin;
        private String view_count;

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

        public int getCan_live_room() {
            return can_live_room;
        }

        public void setCan_live_room(int can_live_room) {
            this.can_live_room = can_live_room;
        }

        public long getGold_coin() {
            return gold_coin;
        }

        public void setGold_coin(long gold_coin) {
            this.gold_coin = gold_coin;
        }

        public int getCharm() {
            return charm;
        }

        public void setCharm(int charm) {
            this.charm = charm;
        }

        public int getCan_chat() {
            return can_chat;
        }

        public void setCan_chat(int can_chat) {
            this.can_chat = can_chat;
        }

        public int getCan_shake() {
            return can_shake;
        }

        public void setCan_shake(int can_shake) {
            this.can_shake = can_shake;
        }

        public int getConvert_coin() {
            return convert_coin;
        }

        public void setConvert_coin(int convert_coin) {
            this.convert_coin = convert_coin;
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

        public int getAccrue_coin() {
            return accrue_coin;
        }

        public void setAccrue_coin(int accrue_coin) {
            this.accrue_coin = accrue_coin;
        }

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }
    }
}