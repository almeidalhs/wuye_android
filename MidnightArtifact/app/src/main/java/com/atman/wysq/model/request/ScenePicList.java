package com.atman.wysq.model.request;

/**
 * Created by tangbingliang on 17/3/22.
 */

public class ScenePicList {

    private String url;
    private boolean isSelect;

    public ScenePicList(String url) {
        this.url = url;
        this.isSelect = false;
    }

    public String getUrl() {
        return url;
    }

    public boolean isSelect() {
        return isSelect;
    }
}
