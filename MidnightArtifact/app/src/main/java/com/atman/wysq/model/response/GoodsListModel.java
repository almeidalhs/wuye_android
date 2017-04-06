package com.atman.wysq.model.response;

/**
 * Created by tangbingliang on 17/4/6.
 */

public class GoodsListModel {
    private int goods_id;
    private String pic_img;
    private String title;
    private String discount_price;

    public GoodsListModel(int goods_id, String pic_img, String title, String discount_price) {
        this.goods_id = goods_id;
        this.pic_img = pic_img;
        this.title = title;
        this.discount_price = discount_price;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public String getPic_img() {
        return pic_img;
    }

    public String getTitle() {
        return title;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    @Override
    public String toString() {
        return "GoodsListModel{" +
                "goods_id=" + goods_id +
                ", pic_img='" + pic_img + '\'' +
                ", title='" + title + '\'' +
                ", discount_price='" + discount_price + '\'' +
                '}';
    }
}
