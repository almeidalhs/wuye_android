package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by vavid on 2016/9/22.
 */
public class GiftMessageModel {
    /**
     * WYNotice : WYNotice
     * enter_center : true
     * type : 1
     * addvalue : []
     * push_content : 昵称 落霞与孤雾齐飞 给您送了 气球
     * center_content : 给您送了 气球
     * center_user_id : 450000168
     * center_user_name : 落霞与孤雾齐飞
     * center_user_avatar : /imageServer/C029174175f689b3451f9a80f8c9c28005d0.jpg
     * center_time : 1474513302777
     */

    private String WYNotice;
    private boolean enter_center;
    private int type;
    private int giftId;
    private int add_money;
    private String push_content;
    private String center_content;
    private long center_user_id;
    private String center_user_name;
    private String center_user_avatar;
    private long center_time;
    private List<String> addvalue;
    private adInfoBean ad_info;
    private long chat_id;
    private long sender_id;
    private String sender_name;
    private String sender_sex;
    private String sender_avatar;
    private String chat_content;
    private long chat_time;

    public long getChat_id() {
        return chat_id;
    }

    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
    }

    public long getSender_id() {
        return sender_id;
    }

    public void setSender_id(long sender_id) {
        this.sender_id = sender_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_sex() {
        return sender_sex;
    }

    public void setSender_sex(String sender_sex) {
        this.sender_sex = sender_sex;
    }

    public String getSender_avatar() {
        return sender_avatar;
    }

    public void setSender_avatar(String sender_avatar) {
        this.sender_avatar = sender_avatar;
    }

    public String getChat_content() {
        return chat_content;
    }

    public void setChat_content(String chat_content) {
        this.chat_content = chat_content;
    }

    public long getChat_time() {
        return chat_time;
    }

    public void setChat_time(long chat_time) {
        this.chat_time = chat_time;
    }

    public adInfoBean getAd_info() {
        return ad_info;
    }

    public void setAd_info(adInfoBean ad_info) {
        this.ad_info = ad_info;
    }

    public static class adInfoBean {
        /**
         * ad_goods_id : 186456
         * ad_info_id : 0
         * ad_pic :
         * ad_url :
         * content :
         * create_time : null
         * description :
         * name :
         * series : 0
         * sort : 0
         * type : 6
         * update_time : null
         */

        private long ad_goods_id;
        private int ad_info_id;
        private String ad_pic;
        private String ad_url;
        private String content;
        private Object create_time;
        private String description;
        private String name;
        private int series;
        private int sort;
        private int type;
        private Object update_time;

        public long getAd_goods_id() {
            return ad_goods_id;
        }

        public void setAd_goods_id(long ad_goods_id) {
            this.ad_goods_id = ad_goods_id;
        }

        public int getAd_info_id() {
            return ad_info_id;
        }

        public void setAd_info_id(int ad_info_id) {
            this.ad_info_id = ad_info_id;
        }

        public String getAd_pic() {
            return ad_pic;
        }

        public void setAd_pic(String ad_pic) {
            this.ad_pic = ad_pic;
        }

        public String getAd_url() {
            return ad_url;
        }

        public void setAd_url(String ad_url) {
            this.ad_url = ad_url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSeries() {
            return series;
        }

        public void setSeries(int series) {
            this.series = series;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }
    }

    public int getAdd_money() {
        return add_money;
    }

    public void setAdd_money(int add_money) {
        this.add_money = add_money;
    }

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public String getWYNotice() {
        return WYNotice;
    }

    public void setWYNotice(String WYNotice) {
        this.WYNotice = WYNotice;
    }

    public boolean isEnter_center() {
        return enter_center;
    }

    public void setEnter_center(boolean enter_center) {
        this.enter_center = enter_center;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPush_content() {
        return push_content;
    }

    public void setPush_content(String push_content) {
        this.push_content = push_content;
    }

    public String getCenter_content() {
        return center_content;
    }

    public void setCenter_content(String center_content) {
        this.center_content = center_content;
    }

    public long getCenter_user_id() {
        return center_user_id;
    }

    public void setCenter_user_id(long center_user_id) {
        this.center_user_id = center_user_id;
    }

    public String getCenter_user_name() {
        return center_user_name;
    }

    public void setCenter_user_name(String center_user_name) {
        this.center_user_name = center_user_name;
    }

    public String getCenter_user_avatar() {
        return center_user_avatar;
    }

    public void setCenter_user_avatar(String center_user_avatar) {
        this.center_user_avatar = center_user_avatar;
    }

    public long getCenter_time() {
        return center_time;
    }

    public void setCenter_time(long center_time) {
        this.center_time = center_time;
    }

    public List<String> getAddvalue() {
        return addvalue;
    }

    public void setAddvalue(List<String> addvalue) {
        this.addvalue = addvalue;
    }
}
