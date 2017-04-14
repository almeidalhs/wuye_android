package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/4/14.
 */

public class LiveDetailModel {
    /**
     * result : 1
     * body : {"live_room_id":148,"room_name":"法国哈哈就","user_id":450000534,"cid":"b73c77e4995e4ac2ac5f8e97d36542f4","pic_url":"/imageServer/82561bf426118542478194fdea3c8b9eb30f.jpg","description":"法国哈哈就","member_count":1,"status":0,"create_time":1492141137000,"update_time":1492141137000,"room_id":8464958,"currentRecord":{"live_record_id":2873,"live_room_id":148,"channel":"b73c77e4995e4ac2ac5f8e97d36542f4","pushUrl":"rtmp://p201.live.126.net/live/b73c77e4995e4ac2ac5f8e97d36542f4?wsSecret=067589929386b0cfca314559b945f7a1&wsTime=1492146074","rtmpPullUrl":"http://v201.live.126.net/live/b73c77e4995e4ac2ac5f8e97d36542f4.flv?netease=v201.live.126.net","start_time":1492146194677},"userExt":{"user_id":450000534,"integral":"53","secret_type":0,"type":1,"icon":"/imageServer/687C0e630b76f75f46ed9b110777ba5b47ad.jpg","sex":"F","create_time":1439865955000,"update_time":1439865955000,"name_change":0,"next_level_integral":142,"userLevel":2,"mobile":"13820000024","nick_name":"夜友-11685","status":2,"verify_status":0,"user_token":"21408dd7d63261c2bf072211c7e0d27e","can_live_room":1,"gold_coin":57320,"charm":19416,"can_chat":1,"can_shake":1,"convert_coin":11622,"vip_level":0,"like_num":3,"recommend_user":1,"left_coin":45698,"in_chart":1,"chat_count":5,"accrue_coin":57666},"gold_num":"0","like_num":"3","userList":[{"user_id":450215851,"integral":"0","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1491463464000,"update_time":1491463464000,"name_change":0,"next_level_integral":1,"userLevel":0,"mobile":"15700001036","nick_name":"夜友-517128","status":2,"verify_status":0,"user_token":"73202f7231b4ca4bd9cf68fe148585df","can_live_room":0,"gold_coin":30,"charm":0,"can_chat":1,"can_shake":1,"convert_coin":0,"vip_level":0,"like_num":0,"left_coin":30,"enterLiveRoomTime":1492141559451,"in_chart":1,"accrue_coin":30}]}
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
         * live_room_id : 148
         * room_name : 法国哈哈就
         * user_id : 450000534
         * cid : b73c77e4995e4ac2ac5f8e97d36542f4
         * pic_url : /imageServer/82561bf426118542478194fdea3c8b9eb30f.jpg
         * description : 法国哈哈就
         * member_count : 1
         * status : 0
         * create_time : 1492141137000
         * update_time : 1492141137000
         * room_id : 8464958
         * currentRecord : {"live_record_id":2873,"live_room_id":148,"channel":"b73c77e4995e4ac2ac5f8e97d36542f4","pushUrl":"rtmp://p201.live.126.net/live/b73c77e4995e4ac2ac5f8e97d36542f4?wsSecret=067589929386b0cfca314559b945f7a1&wsTime=1492146074","rtmpPullUrl":"http://v201.live.126.net/live/b73c77e4995e4ac2ac5f8e97d36542f4.flv?netease=v201.live.126.net","start_time":1492146194677}
         * userExt : {"user_id":450000534,"integral":"53","secret_type":0,"type":1,"icon":"/imageServer/687C0e630b76f75f46ed9b110777ba5b47ad.jpg","sex":"F","create_time":1439865955000,"update_time":1439865955000,"name_change":0,"next_level_integral":142,"userLevel":2,"mobile":"13820000024","nick_name":"夜友-11685","status":2,"verify_status":0,"user_token":"21408dd7d63261c2bf072211c7e0d27e","can_live_room":1,"gold_coin":57320,"charm":19416,"can_chat":1,"can_shake":1,"convert_coin":11622,"vip_level":0,"like_num":3,"recommend_user":1,"left_coin":45698,"in_chart":1,"chat_count":5,"accrue_coin":57666}
         * gold_num : 0
         * like_num : 3
         * userList : [{"user_id":450215851,"integral":"0","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1491463464000,"update_time":1491463464000,"name_change":0,"next_level_integral":1,"userLevel":0,"mobile":"15700001036","nick_name":"夜友-517128","status":2,"verify_status":0,"user_token":"73202f7231b4ca4bd9cf68fe148585df","can_live_room":0,"gold_coin":30,"charm":0,"can_chat":1,"can_shake":1,"convert_coin":0,"vip_level":0,"like_num":0,"left_coin":30,"enterLiveRoomTime":1492141559451,"in_chart":1,"accrue_coin":30}]
         */

        private int live_room_id;
        private String room_name;
        private long user_id;
        private String cid;
        private String pic_url;
        private String description;
        private int member_count;
        private int status;
        private long create_time;
        private long update_time;
        private int room_id;
        private CurrentRecordBean currentRecord;
        private UserExtBean userExt;
        private String gold_num;
        private String like_num;
        private List<UserListBean> userList;

        public int getLive_room_id() {
            return live_room_id;
        }

        public void setLive_room_id(int live_room_id) {
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

        public int getRoom_id() {
            return room_id;
        }

        public void setRoom_id(int room_id) {
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

        public String getGold_num() {
            return gold_num;
        }

        public void setGold_num(String gold_num) {
            this.gold_num = gold_num;
        }

        public String getLike_num() {
            return like_num;
        }

        public void setLike_num(String like_num) {
            this.like_num = like_num;
        }

        public List<UserListBean> getUserList() {
            return userList;
        }

        public void setUserList(List<UserListBean> userList) {
            this.userList = userList;
        }

        public static class CurrentRecordBean {
            /**
             * live_record_id : 2873
             * live_room_id : 148
             * channel : b73c77e4995e4ac2ac5f8e97d36542f4
             * pushUrl : rtmp://p201.live.126.net/live/b73c77e4995e4ac2ac5f8e97d36542f4?wsSecret=067589929386b0cfca314559b945f7a1&wsTime=1492146074
             * rtmpPullUrl : http://v201.live.126.net/live/b73c77e4995e4ac2ac5f8e97d36542f4.flv?netease=v201.live.126.net
             * start_time : 1492146194677
             */

            private int live_record_id;
            private int live_room_id;
            private String channel;
            private String pushUrl;
            private String rtmpPullUrl;
            private long start_time;

            public int getLive_record_id() {
                return live_record_id;
            }

            public void setLive_record_id(int live_record_id) {
                this.live_record_id = live_record_id;
            }

            public int getLive_room_id() {
                return live_room_id;
            }

            public void setLive_room_id(int live_room_id) {
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
        }

        public static class UserExtBean {
            /**
             * user_id : 450000534
             * integral : 53
             * secret_type : 0
             * type : 1
             * icon : /imageServer/687C0e630b76f75f46ed9b110777ba5b47ad.jpg
             * sex : F
             * create_time : 1439865955000
             * update_time : 1439865955000
             * name_change : 0
             * next_level_integral : 142
             * userLevel : 2
             * mobile : 13820000024
             * nick_name : 夜友-11685
             * status : 2
             * verify_status : 0
             * user_token : 21408dd7d63261c2bf072211c7e0d27e
             * can_live_room : 1
             * gold_coin : 57320
             * charm : 19416
             * can_chat : 1
             * can_shake : 1
             * convert_coin : 11622
             * vip_level : 0
             * like_num : 3
             * recommend_user : 1
             * left_coin : 45698
             * in_chart : 1
             * chat_count : 5
             * accrue_coin : 57666
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
            private String user_token;
            private int can_live_room;
            private int gold_coin;
            private int charm;
            private int can_chat;
            private int can_shake;
            private int convert_coin;
            private int vip_level;
            private int like_num;
            private int recommend_user;
            private int left_coin;
            private int in_chart;
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

            public String getUser_token() {
                return user_token;
            }

            public void setUser_token(String user_token) {
                this.user_token = user_token;
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

            public int getVip_level() {
                return vip_level;
            }

            public void setVip_level(int vip_level) {
                this.vip_level = vip_level;
            }

            public int getLike_num() {
                return like_num;
            }

            public void setLike_num(int like_num) {
                this.like_num = like_num;
            }

            public int getRecommend_user() {
                return recommend_user;
            }

            public void setRecommend_user(int recommend_user) {
                this.recommend_user = recommend_user;
            }

            public int getLeft_coin() {
                return left_coin;
            }

            public void setLeft_coin(int left_coin) {
                this.left_coin = left_coin;
            }

            public int getIn_chart() {
                return in_chart;
            }

            public void setIn_chart(int in_chart) {
                this.in_chart = in_chart;
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

        public static class UserListBean {
            /**
             * user_id : 450215851
             * integral : 0
             * secret_type : 0
             * type : 1
             * icon : /imageServer/9C66male.png
             * sex : M
             * create_time : 1491463464000
             * update_time : 1491463464000
             * name_change : 0
             * next_level_integral : 1
             * userLevel : 0
             * mobile : 15700001036
             * nick_name : 夜友-517128
             * status : 2
             * verify_status : 0
             * user_token : 73202f7231b4ca4bd9cf68fe148585df
             * can_live_room : 0
             * gold_coin : 30
             * charm : 0
             * can_chat : 1
             * can_shake : 1
             * convert_coin : 0
             * vip_level : 0
             * like_num : 0
             * left_coin : 30
             * enterLiveRoomTime : 1492141559451
             * in_chart : 1
             * accrue_coin : 30
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
            private String user_token;
            private int can_live_room;
            private int gold_coin;
            private int charm;
            private int can_chat;
            private int can_shake;
            private int convert_coin;
            private int vip_level;
            private int like_num;
            private int left_coin;
            private long enterLiveRoomTime;
            private int in_chart;
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

            public String getUser_token() {
                return user_token;
            }

            public void setUser_token(String user_token) {
                this.user_token = user_token;
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

            public int getVip_level() {
                return vip_level;
            }

            public void setVip_level(int vip_level) {
                this.vip_level = vip_level;
            }

            public int getLike_num() {
                return like_num;
            }

            public void setLike_num(int like_num) {
                this.like_num = like_num;
            }

            public int getLeft_coin() {
                return left_coin;
            }

            public void setLeft_coin(int left_coin) {
                this.left_coin = left_coin;
            }

            public long getEnterLiveRoomTime() {
                return enterLiveRoomTime;
            }

            public void setEnterLiveRoomTime(long enterLiveRoomTime) {
                this.enterLiveRoomTime = enterLiveRoomTime;
            }

            public int getIn_chart() {
                return in_chart;
            }

            public void setIn_chart(int in_chart) {
                this.in_chart = in_chart;
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
