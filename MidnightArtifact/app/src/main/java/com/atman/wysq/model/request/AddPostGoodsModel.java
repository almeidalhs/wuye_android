package com.atman.wysq.model.request;

/**
 * Created by tangbingliang on 17/4/1.
 */

public class AddPostGoodsModel {
    private long goodsId;
    private String price;
    private String name;
    private String img;

    public AddPostGoodsModel(long goodsId, String price, String name, String img) {
        this.goodsId = goodsId;
        this.price = price;
        this.name = name;
        this.img = img;
    }
}
