package com.atman.wysq.model.response;

/**
 * Created by tangbingliang on 16/12/6.
 */

public class GetFansNumModel {
    /**
     * result : 1
     * body : {"follows":360,"fans":359}
     */

    private String result;
    /**
     * follows : 360
     * fans : 359
     */

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
        private int follows;
        private int fans;

        public int getFollows() {
            return follows;
        }

        public void setFollows(int follows) {
            this.follows = follows;
        }

        public int getFans() {
            return fans;
        }

        public void setFans(int fans) {
            this.fans = fans;
        }
    }
}
