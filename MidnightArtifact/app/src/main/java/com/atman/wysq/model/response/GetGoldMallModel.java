package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/12/7.
 */

public class GetGoldMallModel {
    /**
     * result : 1
     * body : [{"goods_id":90,"title":"商品A2","pic_img":"/imageServer/129Aae18889fa6bc4b6fbf7ba77631faf089.jpg","store":"112","basic_info":"款式介绍：浪漫的黑色蕾丝面料，私处开档设计，一只美丽的蝴蝶瞬间飞入花丛\u2026\u2026 颜色：紫色 材质：蕾丝 成份：氨纶5%，锦纶95% 尺码：均码。弹性极好，普通体重和身高范围请放心选择。","price":"0","discount_price":"101","sales":46,"goods_category_id":92,"special_edition_id":13,"status":2,"goods_index":3,"key_words":"商品","gold_coin":18,"integral":2,"chat_prop_id":4,"update_time":1428138660000,"goods_type":5,"source":1,"postage":0,"gold_coin_price":444,"charm_price":555,"icon":"/imageServer/912027751ccd3dd44e8ea78782cf052bef60.jpg"}]
     */

    private String result;
    /**
     * goods_id : 90
     * title : 商品A2
     * pic_img : /imageServer/129Aae18889fa6bc4b6fbf7ba77631faf089.jpg
     * store : 112
     * basic_info : 款式介绍：浪漫的黑色蕾丝面料，私处开档设计，一只美丽的蝴蝶瞬间飞入花丛…… 颜色：紫色 材质：蕾丝 成份：氨纶5%，锦纶95% 尺码：均码。弹性极好，普通体重和身高范围请放心选择。
     * price : 0
     * discount_price : 101
     * sales : 46
     * goods_category_id : 92
     * special_edition_id : 13
     * status : 2
     * goods_index : 3
     * key_words : 商品
     * gold_coin : 18
     * integral : 2
     * chat_prop_id : 4
     * update_time : 1428138660000
     * goods_type : 5
     * source : 1
     * postage : 0
     * gold_coin_price : 444
     * charm_price : 555
     * icon : /imageServer/912027751ccd3dd44e8ea78782cf052bef60.jpg
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
        private int goods_id;
        private String title;
        private String pic_img;
        private String store;
        private String basic_info;
        private String price;
        private String discount_price;
        private int sales;
        private int goods_category_id;
        private int special_edition_id;
        private int status;
        private int goods_index;
        private String key_words;
        private int gold_coin;
        private int integral;
        private int chat_prop_id;
        private long update_time;
        private int goods_type;
        private int source;
        private int postage;
        private String gold_coin_price;
        private String charm_price;
        private String icon;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic_img() {
            return pic_img;
        }

        public void setPic_img(String pic_img) {
            this.pic_img = pic_img;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public String getBasic_info() {
            return basic_info;
        }

        public void setBasic_info(String basic_info) {
            this.basic_info = basic_info;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiscount_price() {
            return discount_price;
        }

        public void setDiscount_price(String discount_price) {
            this.discount_price = discount_price;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public int getGoods_category_id() {
            return goods_category_id;
        }

        public void setGoods_category_id(int goods_category_id) {
            this.goods_category_id = goods_category_id;
        }

        public int getSpecial_edition_id() {
            return special_edition_id;
        }

        public void setSpecial_edition_id(int special_edition_id) {
            this.special_edition_id = special_edition_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getGoods_index() {
            return goods_index;
        }

        public void setGoods_index(int goods_index) {
            this.goods_index = goods_index;
        }

        public String getKey_words() {
            return key_words;
        }

        public void setKey_words(String key_words) {
            this.key_words = key_words;
        }

        public int getGold_coin() {
            return gold_coin;
        }

        public void setGold_coin(int gold_coin) {
            this.gold_coin = gold_coin;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getChat_prop_id() {
            return chat_prop_id;
        }

        public void setChat_prop_id(int chat_prop_id) {
            this.chat_prop_id = chat_prop_id;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getPostage() {
            return postage;
        }

        public void setPostage(int postage) {
            this.postage = postage;
        }

        public String getGold_coin_price() {
            return gold_coin_price;
        }

        public void setGold_coin_price(String gold_coin_price) {
            this.gold_coin_price = gold_coin_price;
        }

        public String getCharm_price() {
            return charm_price;
        }

        public void setCharm_price(String charm_price) {
            this.charm_price = charm_price;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
