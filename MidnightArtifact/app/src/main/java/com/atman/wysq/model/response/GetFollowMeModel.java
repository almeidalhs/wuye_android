package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/4/17.
 */

public class GetFollowMeModel {
    /**
     * result : 1
     * body : [{"user_id":100003799,"integral":"51123","secret_type":0,"type":1,"icon":"/imageServer/9B0E76fd50df35e64b7d87e9699410a8beeb.jpg","sex":"M","create_time":1462931458000,"update_time":1462931458000,"name_change":1,"next_level_integral":48869,"userLevel":8,"mobile":"13860000039","nick_name":"鳄鱼精","status":2,"verify_status":0,"can_live_room":1,"gold_coin":11007453,"charm":30,"convert_coin":21,"vip_level":0,"like_num":0,"recommend_user":1,"left_coin":11007432,"chat_count":1,"accrue_coin":10009540},{"user_id":450214623,"integral":"159050","secret_type":0,"type":1,"icon":"/imageServer/8359d6c672e22575440a9a66a5b4691c88b8.jpg","sex":"M","create_time":1458618425000,"update_time":1491027112000,"credit":1,"name_change":1,"next_level_integral":40894,"userLevel":9,"mobile":"13860000022","nick_name":"后规模","status":2,"verify_status":0,"can_live_room":1,"gold_coin":9201868,"charm":1050899,"can_chat":1,"can_shake":1,"convert_coin":634139,"around_site":"强大g","vip_level":4,"like_num":0,"pay_password":"*A4558C22F60D3BC9A4D478983E8EBDE993DEDF8F","recommend_user":1,"left_coin":8567729,"chat_count":66,"accrue_coin":10993034}]
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
         * user_id : 100003799
         * integral : 51123
         * secret_type : 0
         * type : 1
         * icon : /imageServer/9B0E76fd50df35e64b7d87e9699410a8beeb.jpg
         * sex : M
         * create_time : 1462931458000
         * update_time : 1462931458000
         * name_change : 1
         * next_level_integral : 48869
         * userLevel : 8
         * mobile : 13860000039
         * nick_name : 鳄鱼精
         * status : 2
         * verify_status : 0
         * can_live_room : 1
         * gold_coin : 11007453
         * charm : 30
         * convert_coin : 21
         * vip_level : 0
         * like_num : 0
         * recommend_user : 1
         * left_coin : 11007432
         * chat_count : 1
         * accrue_coin : 10009540
         * credit : 1
         * can_chat : 1
         * can_shake : 1
         * around_site : 强大g
         * pay_password : *A4558C22F60D3BC9A4D478983E8EBDE993DEDF8F
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
        private int gold_coin;
        private int charm;
        private int convert_coin;
        private int vip_level;
        private int like_num;
        private int recommend_user;
        private int left_coin;
        private int chat_count;
        private int accrue_coin;
        private int credit;
        private int can_chat;
        private int can_shake;
        private String around_site;
        private String pay_password;

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

        public int getConvert_coin() {
            return convert_coin;
        }

        public void setConvert_coin(int convert_coin) {
            this.convert_coin = convert_coin;
        }

        public int getVip_level() {
            return vip_level;
        }

        public void setVip_level(int vip_level) {
            this.vip_level = vip_level;
        }

        public int getLike_num() {
            return like_num;
        }

        public void setLike_num(int like_num) {
            this.like_num = like_num;
        }

        public int getRecommend_user() {
            return recommend_user;
        }

        public void setRecommend_user(int recommend_user) {
            this.recommend_user = recommend_user;
        }

        public int getLeft_coin() {
            return left_coin;
        }

        public void setLeft_coin(int left_coin) {
            this.left_coin = left_coin;
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

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
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

        public String getAround_site() {
            return around_site;
        }

        public void setAround_site(String around_site) {
            this.around_site = around_site;
        }

        public String getPay_password() {
            return pay_password;
        }

        public void setPay_password(String pay_password) {
            this.pay_password = pay_password;
        }
    }
}
