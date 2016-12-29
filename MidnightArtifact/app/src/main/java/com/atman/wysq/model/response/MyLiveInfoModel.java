package com.atman.wysq.model.response;

/**
 * Created by tangbingliang on 16/12/29.
 */

public class MyLiveInfoModel {
    /**
     * result : 1
     * body : {"live_room_id":38,"room_name":"超大图片","user_id":450214623,"cid":"d12a0f8f919c4140af62770227bb4e93","pic_url":"/imageServer/A5167e00147d6f7040b0bfaee1bc2d547758.jpg","description":"超大图片","member_count":0,"status":0,"create_time":1470212262000,"update_time":1458618547000,"room_id":30234,"currentRecord":{"live_record_id":767,"live_room_id":38,"channel":"77979a06f80b4e7388f72bca0fa41154","pushUrl":"rtmp://p201.live.126.net/live/77979a06f80b4e7388f72bca0fa41154?wsSecret=ba6702f1744e018d10b23891271c98e1&wsTime=1470212216","rtmpPullUrl":"rtmp://v201.live.126.net/live/77979a06f80b4e7388f72bca0fa41154","start_time":1470212218000,"end_time":1470212218000,"create_time":1470212218000,"update_time":1470212218000},"userExt":{"user_id":450214623,"integral":"158460","secret_type":0,"type":1,"icon":"/imageServer/DC1Ec5c24d27e1894e16aaee5502e7d84da0.jpg","sex":"M","create_time":1458618425000,"update_time":1458618425000,"name_change":1,"next_level_integral":41514,"userLevel":9,"mobile":"13860000022","nick_name":"后规模","status":2,"verify_status":0,"can_live_room":1,"gold_coin":10000511,"charm":3653,"can_chat":1,"can_shake":1,"convert_coin":0,"around_site":"强大g","vip_level":0,"chat_count":53,"accrue_coin":10158715}}
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
         * live_room_id : 38
         * room_name : 超大图片
         * user_id : 450214623
         * cid : d12a0f8f919c4140af62770227bb4e93
         * pic_url : /imageServer/A5167e00147d6f7040b0bfaee1bc2d547758.jpg
         * description : 超大图片
         * member_count : 0
         * status : 0
         * create_time : 1470212262000
         * update_time : 1458618547000
         * room_id : 30234
         * currentRecord : {"live_record_id":767,"live_room_id":38,"channel":"77979a06f80b4e7388f72bca0fa41154","pushUrl":"rtmp://p201.live.126.net/live/77979a06f80b4e7388f72bca0fa41154?wsSecret=ba6702f1744e018d10b23891271c98e1&wsTime=1470212216","rtmpPullUrl":"rtmp://v201.live.126.net/live/77979a06f80b4e7388f72bca0fa41154","start_time":1470212218000,"end_time":1470212218000,"create_time":1470212218000,"update_time":1470212218000}
         * userExt : {"user_id":450214623,"integral":"158460","secret_type":0,"type":1,"icon":"/imageServer/DC1Ec5c24d27e1894e16aaee5502e7d84da0.jpg","sex":"M","create_time":1458618425000,"update_time":1458618425000,"name_change":1,"next_level_integral":41514,"userLevel":9,"mobile":"13860000022","nick_name":"后规模","status":2,"verify_status":0,"can_live_room":1,"gold_coin":10000511,"charm":3653,"can_chat":1,"can_shake":1,"convert_coin":0,"around_site":"强大g","vip_level":0,"chat_count":53,"accrue_coin":10158715}
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
        private CurrentRecordBean currentRecord;
        private UserExtBean userExt;

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

        public CurrentRecordBean getCurrentRecord() {
            return currentRecord;
        }

        public void setCurrentRecord(CurrentRecordBean currentRecord) {
            this.currentRecord = currentRecord;
        }

        public UserExtBean getUserExt() {
            return userExt;
        }

        public void setUserExt(UserExtBean userExt) {
            this.userExt = userExt;
        }

        public static class CurrentRecordBean {
            /**
             * live_record_id : 767
             * live_room_id : 38
             * channel : 77979a06f80b4e7388f72bca0fa41154
             * pushUrl : rtmp://p201.live.126.net/live/77979a06f80b4e7388f72bca0fa41154?wsSecret=ba6702f1744e018d10b23891271c98e1&wsTime=1470212216
             * rtmpPullUrl : rtmp://v201.live.126.net/live/77979a06f80b4e7388f72bca0fa41154
             * start_time : 1470212218000
             * end_time : 1470212218000
             * create_time : 1470212218000
             * update_time : 1470212218000
             */

            private long live_record_id;
            private long live_room_id;
            private String channel;
            private String pushUrl;
            private String rtmpPullUrl;
            private long start_time;
            private long end_time;
            private long create_time;
            private long update_time;

            public long getLive_record_id() {
                return live_record_id;
            }

            public void setLive_record_id(long live_record_id) {
                this.live_record_id = live_record_id;
            }

            public long getLive_room_id() {
                return live_room_id;
            }

            public void setLive_room_id(long live_room_id) {
                this.live_room_id = live_room_id;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getPushUrl() {
                return pushUrl;
            }

            public void setPushUrl(String pushUrl) {
                this.pushUrl = pushUrl;
            }

            public String getRtmpPullUrl() {
                return rtmpPullUrl;
            }

            public void setRtmpPullUrl(String rtmpPullUrl) {
                this.rtmpPullUrl = rtmpPullUrl;
            }

            public long getStart_time() {
                return start_time;
            }

            public void setStart_time(long start_time) {
                this.start_time = start_time;
            }

            public long getEnd_time() {
                return end_time;
            }

            public void setEnd_time(long end_time) {
                this.end_time = end_time;
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

        public static class UserExtBean {
            /**
             * user_id : 450214623
             * integral : 158460
             * secret_type : 0
             * type : 1
             * icon : /imageServer/DC1Ec5c24d27e1894e16aaee5502e7d84da0.jpg
             * sex : M
             * create_time : 1458618425000
             * update_time : 1458618425000
             * name_change : 1
             * next_level_integral : 41514
             * userLevel : 9
             * mobile : 13860000022
             * nick_name : 后规模
             * status : 2
             * verify_status : 0
             * can_live_room : 1
             * gold_coin : 10000511
             * charm : 3653
             * can_chat : 1
             * can_shake : 1
             * convert_coin : 0
             * around_site : 强大g
             * vip_level : 0
             * chat_count : 53
             * accrue_coin : 10158715
             */

            private long user_id;
            private String integral;
            private int secret_type;
            private int type;
            private String icon;
            private String sex;
            private long create_time;
            private long update_time;
            private int name_change;
            private long next_level_integral;
            private int userLevel;
            private String mobile;
            private String nick_name;
            private int status;
            private int verify_status;
            private int can_live_room;
            private int gold_coin;
            private int charm;
            private int can_chat;
            private int can_shake;
            private int convert_coin;
            private String around_site;
            private int vip_level;
            private int chat_count;
            private int accrue_coin;

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public int getSecret_type() {
                return secret_type;
            }

            public void setSecret_type(int secret_type) {
                this.secret_type = secret_type;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
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

            public int getName_change() {
                return name_change;
            }

            public void setName_change(int name_change) {
                this.name_change = name_change;
            }

            public long getNext_level_integral() {
                return next_level_integral;
            }

            public void setNext_level_integral(long next_level_integral) {
                this.next_level_integral = next_level_integral;
            }

            public int getUserLevel() {
                return userLevel;
            }

            public void setUserLevel(int userLevel) {
                this.userLevel = userLevel;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getVerify_status() {
                return verify_status;
            }

            public void setVerify_status(int verify_status) {
                this.verify_status = verify_status;
            }

            public int getCan_live_room() {
                return can_live_room;
            }

            public void setCan_live_room(int can_live_room) {
                this.can_live_room = can_live_room;
            }

            public int getGold_coin() {
                return gold_coin;
            }

            public void setGold_coin(int gold_coin) {
                this.gold_coin = gold_coin;
            }

            public int getCharm() {
                return charm;
            }

            public void setCharm(int charm) {
                this.charm = charm;
            }

            public int getCan_chat() {
                return can_chat;
            }

            public void setCan_chat(int can_chat) {
                this.can_chat = can_chat;
            }

            public int getCan_shake() {
                return can_shake;
            }

            public void setCan_shake(int can_shake) {
                this.can_shake = can_shake;
            }

            public int getConvert_coin() {
                return convert_coin;
            }

            public void setConvert_coin(int convert_coin) {
                this.convert_coin = convert_coin;
            }

            public String getAround_site() {
                return around_site;
            }

            public void setAround_site(String around_site) {
                this.around_site = around_site;
            }

            public int getVip_level() {
                return vip_level;
            }

            public void setVip_level(int vip_level) {
                this.vip_level = vip_level;
            }

            public int getChat_count() {
                return chat_count;
            }

            public void setChat_count(int chat_count) {
                this.chat_count = chat_count;
            }

            public int getAccrue_coin() {
                return accrue_coin;
            }

            public void setAccrue_coin(int accrue_coin) {
                this.accrue_coin = accrue_coin;
            }
        }
    }
}
