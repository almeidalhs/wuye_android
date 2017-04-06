package com.atman.wysq.model.request;

import java.util.List;
import java.util.Map;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/4 17:18
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class CreateImageViewPostModel {
    private int anonymity;
    private String content;
    private String title;
    private List<Map<String, Integer>> goodsList;

    public CreateImageViewPostModel(String content, String title, int anonymity
            , List<Map<String, Integer>> map) {
        this.content = content;
        this.title = title;
        this.anonymity = anonymity;
        this.goodsList = map;
    }
}
