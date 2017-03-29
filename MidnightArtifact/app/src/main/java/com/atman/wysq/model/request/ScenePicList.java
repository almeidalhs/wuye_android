package com.atman.wysq.model.request;

/**
 * Created by tangbingliang on 17/3/22.
 */

public class ScenePicList {

    private String url;
    private boolean isUped;
    private boolean isSelect;

    public ScenePicList(String url, boolean isUped) {
        this.url = url;
        this.isUped = isUped;
        this.isSelect = false;
    }

    public boolean isUped() {
        return isUped;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getUrl() {
        return url;
    }

    public boolean isSelect() {
        return isSelect;
    }
}
