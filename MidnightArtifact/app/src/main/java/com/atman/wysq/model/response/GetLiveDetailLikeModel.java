package com.atman.wysq.model.response;

/**
 * Created by tangbingliang on 17/4/14.
 */

public class GetLiveDetailLikeModel {
    /**
     * result : 1
     * body : {"like_num":"3","gold_num":"0"}
     */

    private String result;
    private BodyBean body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * like_num : 3
         * gold_num : 0
         */

        private String like_num;
        private String gold_num;

        public String getLike_num() {
            return like_num;
        }

        public void setLike_num(String like_num) {
            this.like_num = like_num;
        }

        public String getGold_num() {
            return gold_num;
        }

        public void setGold_num(String gold_num) {
            this.gold_num = gold_num;
        }
    }
}
