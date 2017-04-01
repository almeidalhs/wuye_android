package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/4/1.
 */

public class MallCategoryModel {
    /**
     * result : 1
     * body : [{"goods_category_id":69,"name":"酒店速递","ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","ad_goods_id":"0","sort":99,"type":1,"category_type":1,"description":"十分钟必达","img_bg_url":"/imageServer/prop/mall/hotel_mall_bg.jpg","img_url":"/imageServer/prop/mall/hotel_mall.png","web_url":"http://192.168.1.134:7000/page/#/home/hotel/","ad_club_pic":"/imageServer/prop/temp/400.jpg"}]
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
         * goods_category_id : 69
         * name : 酒店速递
         * ad_pic : /imageServer/prop/adinfo/b4.png
         * ad_url :
         * ad_goods_id : 0
         * sort : 99
         * type : 1
         * category_type : 1
         * description : 十分钟必达
         * img_bg_url : /imageServer/prop/mall/hotel_mall_bg.jpg
         * img_url : /imageServer/prop/mall/hotel_mall.png
         * web_url : http://192.168.1.134:7000/page/#/home/hotel/
         * ad_club_pic : /imageServer/prop/temp/400.jpg
         */

        private int goods_category_id;
        private String name;
        private String ad_pic;
        private String ad_url;
        private String ad_goods_id;
        private String ad_club_pic;
        private int sort;
        private int type;
        private int category_type;
        private String description;
        private String img_bg_url;
        private String img_url;
        private String web_url;

        public int getGoods_category_id() {
            return goods_category_id;
        }

        public void setGoods_category_id(int goods_category_id) {
            this.goods_category_id = goods_category_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getAd_goods_id() {
            return ad_goods_id;
        }

        public void setAd_goods_id(String ad_goods_id) {
            this.ad_goods_id = ad_goods_id;
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

        public int getCategory_type() {
            return category_type;
        }

        public void setCategory_type(int category_type) {
            this.category_type = category_type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImg_bg_url() {
            return img_bg_url;
        }

        public void setImg_bg_url(String img_bg_url) {
            this.img_bg_url = img_bg_url;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }

        public String getAd_club_pic() {
            return ad_club_pic;
        }

        public void setAd_club_pic(String ad_club_pic) {
            this.ad_club_pic = ad_club_pic;
        }
    }
}
