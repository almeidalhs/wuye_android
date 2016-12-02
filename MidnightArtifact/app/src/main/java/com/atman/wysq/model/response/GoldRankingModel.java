package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/12/2.
 */

public class GoldRankingModel {
    /**
     * result : 1
     * body : [{"user_id":450215758,"integral":"68","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1480558506000,"update_time":1480558506000,"name_change":0,"next_level_integral":130,"userLevel":2,"mobile":"15555555010","nick_name":"夜友-516933","status":2,"verify_status":0,"gold_coin":35011,"charm":0,"can_chat":1,"can_shake":1,"vip_level":0,"accrue_coin":35011},{"user_id":100003810,"integral":"799","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1480490140000,"update_time":1480490140000,"name_change":0,"next_level_integral":2199,"userLevel":4,"mobile":"13860000046","nick_name":"夜友-516926","status":2,"verify_status":0,"gold_coin":10450,"charm":0,"vip_level":0,"accrue_coin":10500},{"user_id":450215733,"integral":"800","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1480412600000,"update_time":1480412600000,"name_change":0,"next_level_integral":2190,"userLevel":4,"mobile":"15968800013","nick_name":"夜友-516901","status":2,"verify_status":0,"gold_coin":2680,"charm":0,"can_chat":1,"can_shake":1,"vip_level":0,"accrue_coin":2830},{"user_id":450214926,"integral":"8425","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1480392323000,"update_time":1480392323000,"name_change":0,"next_level_integral":11575,"userLevel":6,"mobile":"17751000205","nick_name":"夜友-516094","status":2,"verify_status":0,"gold_coin":2080,"charm":0,"can_chat":1,"can_shake":1,"vip_level":0,"accrue_coin":67335},{"user_id":450214934,"integral":"13225","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1480392330000,"update_time":1480392330000,"name_change":0,"next_level_integral":6775,"userLevel":6,"mobile":"17751000213","nick_name":"夜友-516102","status":2,"verify_status":0,"gold_coin":2080,"charm":0,"can_chat":1,"can_shake":1,"vip_level":0,"accrue_coin":76120}]
     */

    private String result;
    /**
     * user_id : 450215758
     * integral : 68
     * secret_type : 0
     * type : 1
     * icon : /imageServer/9C66male.png
     * sex : M
     * create_time : 1480558506000
     * update_time : 1480558506000
     * name_change : 0
     * next_level_integral : 130
     * userLevel : 2
     * mobile : 15555555010
     * nick_name : 夜友-516933
     * status : 2
     * verify_status : 0
     * gold_coin : 35011
     * charm : 0
     * can_chat : 1
     * can_shake : 1
     * vip_level : 0
     * accrue_coin : 35011
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
        private long gold_coin;
        private int charm;
        private int can_chat;
        private int can_shake;
        private int vip_level;
        private int accrue_coin;

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

        public int getVip_level() {
            return vip_level;
        }

        public void setVip_level(int vip_level) {
            this.vip_level = vip_level;
        }

        public int getAccrue_coin() {
            return accrue_coin;
        }

        public void setAccrue_coin(int accrue_coin) {
            this.accrue_coin = accrue_coin;
        }
    }
}
