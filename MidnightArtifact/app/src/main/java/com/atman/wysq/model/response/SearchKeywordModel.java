package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/4/1.
 */

public class SearchKeywordModel {
    /**
     * result : 1
     * body : [{"name":"飞机杯","sort":1},{"name":"震动棒","sort":2},{"name":"情趣内衣","sort":3},{"name":"名器","sort":4},{"name":"延时","sort":5},{"name":"精油","sort":6}]
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
         * name : 飞机杯
         * sort : 1
         */

        private String name;
        private int sort;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
