package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/12/29.
 */

public class GetLiveHallModel {
    /**
     * result : 1
     * body : [{"live_room_id":107,"room_name":"一个人是因为","user_id":450000638,"cid":"bea474b46ddb40db8f88676e08075c0f","pic_url":"/imageServer/5EA0d929365a8fd44465a7ce991e0390975f.jpg","description":"一个人是因为","member_count":0,"status":1,"create_time":1470732074000,"update_time":1470732074000,"room_id":3816042,"currentRecord":{"live_record_id":838,"live_room_id":107,"channel":"bea474b46ddb40db8f88676e08075c0f","pushUrl":"rtmp://p201.live.126.net/live/bea474b46ddb40db8f88676e08075c0f?wsSecret=6cc5f8fb8ae64c3132f8f6e8b53dfe62&wsTime=1470732067","rtmpPullUrl":"http://v201.live.126.net/live/bea474b46ddb40db8f88676e08075c0f.flv","start_time":1470732075000,"end_time":1470732074000,"create_time":1470732074000,"update_time":1470732074000},"userExt":{"user_id":450000638,"integral":"10057","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"F","create_time":1440562844000,"update_time":1440562844000,"name_change":0,"next_level_integral":9942,"userLevel":6,"mobile":"13920000027","nick_name":"夜友-11789","status":2,"verify_status":1,"can_live_room":1,"gold_coin":20846,"charm":21,"can_chat":1,"can_shake":1,"convert_coin":14,"vip_level":0,"chat_count":7,"accrue_coin":27413},"cost_gold":5}]
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
         * live_room_id : 107
         * room_name : 一个人是因为
         * user_id : 450000638
         * cid : bea474b46ddb40db8f88676e08075c0f
         * pic_url : /imageServer/5EA0d929365a8fd44465a7ce991e0390975f.jpg
         * description : 一个人是因为
         * member_count : 0
         * status : 1
         * create_time : 1470732074000
         * update_time : 1470732074000
         * room_id : 3816042
         * currentRecord : {"live_record_id":838,"live_room_id":107,"channel":"bea474b46ddb40db8f88676e08075c0f","pushUrl":"rtmp://p201.live.126.net/live/bea474b46ddb40db8f88676e08075c0f?wsSecret=6cc5f8fb8ae64c3132f8f6e8b53dfe62&wsTime=1470732067","rtmpPullUrl":"http://v201.live.126.net/live/bea474b46ddb40db8f88676e08075c0f.flv","start_time":1470732075000,"end_time":1470732074000,"create_time":1470732074000,"update_time":1470732074000}
         * userExt : {"user_id":450000638,"integral":"10057","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"F","create_time":1440562844000,"update_time":1440562844000,"name_change":0,"next_level_integral":9942,"userLevel":6,"mobile":"13920000027","nick_name":"夜友-11789","status":2,"verify_status":1,"can_live_room":1,"gold_coin":20846,"charm":21,"can_chat":1,"can_shake":1,"convert_coin":14,"vip_level":0,"chat_count":7,"accrue_coin":27413}
         * cost_gold : 5
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
        private int cost_gold;

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

        public int getCost_gold() {
            return cost_gold;
        }

        public void setCost_gold(int cost_gold) {
            this.cost_gold = cost_gold;
        }

        public static class CurrentRecordBean {
            /**
             * live_record_id : 838
             * live_room_id : 107
             * channel : bea474b46ddb40db8f88676e08075c0f
             * pushUrl : rtmp://p201.live.126.net/live/bea474b46ddb40db8f88676e08075c0f?wsSecret=6cc5f8fb8ae64c3132f8f6e8b53dfe62&wsTime=1470732067
             * rtmpPullUrl : http://v201.live.126.net/live/bea474b46ddb40db8f88676e08075c0f.flv
             * start_time : 1470732075000
             * end_time : 1470732074000
             * create_time : 1470732074000
             * update_time : 1470732074000
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
             * user_id : 450000638
             * integral : 10057
             * secret_type : 0
             * type : 1
             * icon : /imageServer/9C66male.png
             * sex : F
             * create_time : 1440562844000
             * update_time : 1440562844000
             * name_change : 0
             * next_level_integral : 9942
             * userLevel : 6
             * mobile : 13920000027
             * nick_name : 夜友-11789
             * status : 2
             * verify_status : 1
             * can_live_room : 1
             * gold_coin : 20846
             * charm : 21
             * can_chat : 1
             * can_shake : 1
             * convert_coin : 14
             * vip_level : 0
             * chat_count : 7
             * accrue_coin : 27413
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
            private long gold_coin;
            private int charm;
            private int can_chat;
            private int can_shake;
            private int convert_coin;
            private int vip_level;
            private long chat_count;
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

            public long getGold_coin() {
                return gold_coin;
            }

            public void setGold_coin(long gold_coin) {
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

            public int getVip_level() {
                return vip_level;
            }

            public void setVip_level(int vip_level) {
                this.vip_level = vip_level;
            }

            public long getChat_count() {
                return chat_count;
            }

            public void setChat_count(long chat_count) {
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
