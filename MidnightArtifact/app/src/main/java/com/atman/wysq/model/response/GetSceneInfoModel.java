package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/3/22.
 */

public class GetSceneInfoModel {
    /**
     * result : 1
     * body : [{"back_ground_id":605464,"user_id":450000010,"prologue":"棒子能解决的问题，都不是问题！","pic_url1":"/imageServer/99342616839b6ad94866a2dee6d9c60714c9.jpg","pic_url2":"","pic_url3":"","pic_url4":"","pic_url5":"","type":0,"status":1,"create_time":1478755184000,"update_time":1478755193000}]
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
         * back_ground_id : 605464
         * user_id : 450000010
         * prologue : 棒子能解决的问题，都不是问题！
         * pic_url1 : /imageServer/99342616839b6ad94866a2dee6d9c60714c9.jpg
         * pic_url2 :
         * pic_url3 :
         * pic_url4 :
         * pic_url5 :
         * type : 0
         * status : 1
         * create_time : 1478755184000
         * update_time : 1478755193000
         */

        private long back_ground_id;
        private long user_id;
        private String prologue;
        private String pic_url1;
        private String pic_url2;
        private String pic_url3;
        private String pic_url4;
        private String pic_url5;
        private int type;
        private int status;
        private long create_time;
        private long update_time;

        public long getBack_ground_id() {
            return back_ground_id;
        }

        public void setBack_ground_id(long back_ground_id) {
            this.back_ground_id = back_ground_id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getPrologue() {
            return prologue;
        }

        public void setPrologue(String prologue) {
            this.prologue = prologue;
        }

        public String getPic_url1() {
            return pic_url1;
        }

        public void setPic_url1(String pic_url1) {
            this.pic_url1 = pic_url1;
        }

        public String getPic_url2() {
            return pic_url2;
        }

        public void setPic_url2(String pic_url2) {
            this.pic_url2 = pic_url2;
        }

        public String getPic_url3() {
            return pic_url3;
        }

        public void setPic_url3(String pic_url3) {
            this.pic_url3 = pic_url3;
        }

        public String getPic_url4() {
            return pic_url4;
        }

        public void setPic_url4(String pic_url4) {
            this.pic_url4 = pic_url4;
        }

        public String getPic_url5() {
            return pic_url5;
        }

        public void setPic_url5(String pic_url5) {
            this.pic_url5 = pic_url5;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }
    }
}
