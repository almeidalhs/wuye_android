package com.atman.wysq.model.request;

import java.util.List;
import java.util.Map;

/**
 * Created by tangbingliang on 17/4/7.
 */

public class CreateMultiMediaPostModel {
    /**
     * img : video/2017/03/21/11/1011219720759e2173.jpg
     * url : video/2017/03/09/10/IMG_0206.mp3
     * title : 发布音频帖测试
     * file_length : 1000
     * anonymity : 1
     * goodsList : [{"goods_id":1},{"goods_id":2},{"goods_id":3}]
     */

    private String img;
    private String url;
    private String title;
    private int file_length;
    private int anonymity;
    private List<Map<String, Integer>> goodsList;

    public CreateMultiMediaPostModel(String img, String url, String title, int file_length
            , int anonymity, List<Map<String, Integer>> goodsList) {
        this.img = img;
        this.url = url;
        this.title = title;
        this.file_length = file_length;
        this.anonymity = anonymity;
        this.goodsList = goodsList;
    }
}
