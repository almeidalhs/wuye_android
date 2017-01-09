package com.atman.wysq.model.response;

/**
 * Created by tangbingliang on 17/1/9.
 */

public class MyLiveStatusModel {
    /**
     * result : 1
     * body : {"live_room_id":120,"room_name":"啊哈哈","user_id":6000000313,"cid":"f33e8d3dcc4c48e89f39e7b6cbc770f4","pic_url":"/imageServer/E0C23e5be3e4d53046a6838db0c3d01615ae.jpg","description":"啊哈哈","member_count":0,"status":1,"create_time":1483695025000,"update_time":1483695025000,"room_id":6588097}
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
         * live_room_id : 120
         * room_name : 啊哈哈
         * user_id : 6000000313
         * cid : f33e8d3dcc4c48e89f39e7b6cbc770f4
         * pic_url : /imageServer/E0C23e5be3e4d53046a6838db0c3d01615ae.jpg
         * description : 啊哈哈
         * member_count : 0
         * status : 1
         * create_time : 1483695025000
         * update_time : 1483695025000
         * room_id : 6588097
         */

        private long live_room_id;
        private String room_name;
        private long user_id;
        private String cid;
        private String pic_url;
        private String description;
        private int member_count;
        private int status;
        private long create_time;
        private long update_time;
        private long room_id;

        public long getLive_room_id() {
            return live_room_id;
        }

        public void setLive_room_id(long live_room_id) {
            this.live_room_id = live_room_id;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getMember_count() {
            return member_count;
        }

        public void setMember_count(int member_count) {
            this.member_count = member_count;
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

        public long getRoom_id() {
            return room_id;
        }

        public void setRoom_id(long room_id) {
            this.room_id = room_id;
        }
    }
}
