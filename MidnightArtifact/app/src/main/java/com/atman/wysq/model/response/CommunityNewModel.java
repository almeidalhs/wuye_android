package com.atman.wysq.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangbingliang on 17/3/24.
 */

public class CommunityNewModel implements Serializable {
    /**
     * result : 1
     * body : [{"category":4,"title":"什么是","like_num":"1000万","icon":"/imageServer/3EE637bb21d637614e15a224c4b79e0ede98.jpg","img":"/imageServer/6C43cff7316e7b004089be2d1eb162492e68.jpg","liveRoom":{"live_room_id":143,"room_name":"什么是","user_id":450215827,"cid":"f7e600df491045ffaa42ea155d64ac7c","pic_url":"/imageServer/6C43cff7316e7b004089be2d1eb162492e68.jpg","description":"什么是","member_count":0,"status":1,"create_time":1491468070000,"update_time":1491468070000,"room_id":8281987,"currentRecord":{"live_record_id":2775,"live_room_id":143,"channel":"f7e600df491045ffaa42ea155d64ac7c","pushUrl":"rtmp://p201.live.126.net/live/f7e600df491045ffaa42ea155d64ac7c?wsSecret=8ad7fd689f3f429af5e6a919355801f8&wsTime=1491994030","rtmpPullUrl":"http://v201.live.126.net/live/f7e600df491045ffaa42ea155d64ac7c.flv?netease=v201.live.126.net","start_time":1491994150000,"end_time":1491994149000,"create_time":1491994149000,"update_time":1491994149000},"userExt":{"user_id":450215827,"integral":"10000031","secret_type":0,"type":1,"icon":"/imageServer/3EE637bb21d637614e15a224c4b79e0ede98.jpg","sex":"F","create_time":1491025950000,"update_time":1491025950000,"name_change":0,"next_level_integral":9999999999999999,"userLevel":10,"mobile":"15555555233","nick_name":"夜友-517104","status":2,"verify_status":0,"user_token":"8f13a48916f8baf19e7d453d7fb1b911","can_live_room":1,"gold_coin":49038,"charm":20,"can_chat":1,"can_shake":1,"convert_coin":1007,"vip_level":2,"like_num":10000020,"pay_password":"*955A167AF04DC059522D4030188DE1451BECD820","left_coin":48031,"in_chart":1,"chat_count":1,"accrue_coin":39682},"userList":[]},"live_room_id":143}]
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

    public static class BodyBean implements Serializable {
        /**
         * category : 4
         * title : 什么是
         * like_num : 1000万
         * icon : /imageServer/3EE637bb21d637614e15a224c4b79e0ede98.jpg
         * img : /imageServer/6C43cff7316e7b004089be2d1eb162492e68.jpg
         * liveRoom : {"live_room_id":143,"room_name":"什么是","user_id":450215827,"cid":"f7e600df491045ffaa42ea155d64ac7c","pic_url":"/imageServer/6C43cff7316e7b004089be2d1eb162492e68.jpg","description":"什么是","member_count":0,"status":1,"create_time":1491468070000,"update_time":1491468070000,"room_id":8281987,"currentRecord":{"live_record_id":2775,"live_room_id":143,"channel":"f7e600df491045ffaa42ea155d64ac7c","pushUrl":"rtmp://p201.live.126.net/live/f7e600df491045ffaa42ea155d64ac7c?wsSecret=8ad7fd689f3f429af5e6a919355801f8&wsTime=1491994030","rtmpPullUrl":"http://v201.live.126.net/live/f7e600df491045ffaa42ea155d64ac7c.flv?netease=v201.live.126.net","start_time":1491994150000,"end_time":1491994149000,"create_time":1491994149000,"update_time":1491994149000},"userExt":{"user_id":450215827,"integral":"10000031","secret_type":0,"type":1,"icon":"/imageServer/3EE637bb21d637614e15a224c4b79e0ede98.jpg","sex":"F","create_time":1491025950000,"update_time":1491025950000,"name_change":0,"next_level_integral":9999999999999999,"userLevel":10,"mobile":"15555555233","nick_name":"夜友-517104","status":2,"verify_status":0,"user_token":"8f13a48916f8baf19e7d453d7fb1b911","can_live_room":1,"gold_coin":49038,"charm":20,"can_chat":1,"can_shake":1,"convert_coin":1007,"vip_level":2,"like_num":10000020,"pay_password":"*955A167AF04DC059522D4030188DE1451BECD820","left_coin":48031,"in_chart":1,"chat_count":1,"accrue_coin":39682},"userList":[]}
         * live_room_id : 143
         */

        private int category;
        private String title;
        private String like_num;
        private String icon;
        private String img;
        private LiveRoomBean liveRoom;
        private int live_room_id;
        private String comment_count;
        private int favorite_count;
        private String view_count;
        private int stick;
        private int replay_flag;
        private int vip_level;
        private int anonymity;
        private long blog_id;

        public int getReplay_flag() {
            return replay_flag;
        }

        public void setReplay_flag(int replay_flag) {
            this.replay_flag = replay_flag;
        }

        public int getVip_level() {
            return vip_level;
        }

        public void setVip_level(int vip_level) {
            this.vip_level = vip_level;
        }

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public int getFavorite_count() {
            return favorite_count;
        }

        public void setFavorite_count(int favorite_count) {
            this.favorite_count = favorite_count;
        }

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }

        public int getStick() {
            return stick;
        }

        public void setStick(int stick) {
            this.stick = stick;
        }

        public int getAnonymity() {
            return anonymity;
        }

        public void setAnonymity(int anonymity) {
            this.anonymity = anonymity;
        }

        public long getBlog_id() {
            return blog_id;
        }

        public void setBlog_id(long blog_id) {
            this.blog_id = blog_id;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLike_num() {
            return like_num;
        }

        public void setLike_num(String like_num) {
            this.like_num = like_num;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public LiveRoomBean getLiveRoom() {
            return liveRoom;
        }

        public void setLiveRoom(LiveRoomBean liveRoom) {
            this.liveRoom = liveRoom;
        }

        public int getLive_room_id() {
            return live_room_id;
        }

        public void setLive_room_id(int live_room_id) {
            this.live_room_id = live_room_id;
        }

        public static class LiveRoomBean implements Serializable {
            /**
             * live_room_id : 143
             * room_name : 什么是
             * user_id : 450215827
             * cid : f7e600df491045ffaa42ea155d64ac7c
             * pic_url : /imageServer/6C43cff7316e7b004089be2d1eb162492e68.jpg
             * description : 什么是
             * member_count : 0
             * status : 1
             * create_time : 1491468070000
             * update_time : 1491468070000
             * room_id : 8281987
             * currentRecord : {"live_record_id":2775,"live_room_id":143,"channel":"f7e600df491045ffaa42ea155d64ac7c","pushUrl":"rtmp://p201.live.126.net/live/f7e600df491045ffaa42ea155d64ac7c?wsSecret=8ad7fd689f3f429af5e6a919355801f8&wsTime=1491994030","rtmpPullUrl":"http://v201.live.126.net/live/f7e600df491045ffaa42ea155d64ac7c.flv?netease=v201.live.126.net","start_time":1491994150000,"end_time":1491994149000,"create_time":1491994149000,"update_time":1491994149000}
             * userExt : {"user_id":450215827,"integral":"10000031","secret_type":0,"type":1,"icon":"/imageServer/3EE637bb21d637614e15a224c4b79e0ede98.jpg","sex":"F","create_time":1491025950000,"update_time":1491025950000,"name_change":0,"next_level_integral":9999999999999999,"userLevel":10,"mobile":"15555555233","nick_name":"夜友-517104","status":2,"verify_status":0,"user_token":"8f13a48916f8baf19e7d453d7fb1b911","can_live_room":1,"gold_coin":49038,"charm":20,"can_chat":1,"can_shake":1,"convert_coin":1007,"vip_level":2,"like_num":10000020,"pay_password":"*955A167AF04DC059522D4030188DE1451BECD820","left_coin":48031,"in_chart":1,"chat_count":1,"accrue_coin":39682}
             * userList : []
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
            private List<?> userList;

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

            public List<?> getUserList() {
                return userList;
            }

            public void setUserList(List<?> userList) {
                this.userList = userList;
            }

            public static class CurrentRecordBean implements Serializable {
                /**
                 * live_record_id : 2775
                 * live_room_id : 143
                 * channel : f7e600df491045ffaa42ea155d64ac7c
                 * pushUrl : rtmp://p201.live.126.net/live/f7e600df491045ffaa42ea155d64ac7c?wsSecret=8ad7fd689f3f429af5e6a919355801f8&wsTime=1491994030
                 * rtmpPullUrl : http://v201.live.126.net/live/f7e600df491045ffaa42ea155d64ac7c.flv?netease=v201.live.126.net
                 * start_time : 1491994150000
                 * end_time : 1491994149000
                 * create_time : 1491994149000
                 * update_time : 1491994149000
                 */

                private int live_record_id;
                private int live_room_id;
                private String channel;
                private String pushUrl;
                private String rtmpPullUrl;
                private long start_time;
                private long end_time;
                private long create_time;
                private long update_time;

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

            public static class UserExtBean implements Serializable {
                /**
                 * user_id : 450215827
                 * integral : 10000031
                 * secret_type : 0
                 * type : 1
                 * icon : /imageServer/3EE637bb21d637614e15a224c4b79e0ede98.jpg
                 * sex : F
                 * create_time : 1491025950000
                 * update_time : 1491025950000
                 * name_change : 0
                 * next_level_integral : 9999999999999999
                 * userLevel : 10
                 * mobile : 15555555233
                 * nick_name : 夜友-517104
                 * status : 2
                 * verify_status : 0
                 * user_token : 8f13a48916f8baf19e7d453d7fb1b911
                 * can_live_room : 1
                 * gold_coin : 49038
                 * charm : 20
                 * can_chat : 1
                 * can_shake : 1
                 * convert_coin : 1007
                 * vip_level : 2
                 * like_num : 10000020
                 * pay_password : *955A167AF04DC059522D4030188DE1451BECD820
                 * left_coin : 48031
                 * in_chart : 1
                 * chat_count : 1
                 * accrue_coin : 39682
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
                private String pay_password;
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

                public String getPay_password() {
                    return pay_password;
                }

                public void setPay_password(String pay_password) {
                    this.pay_password = pay_password;
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
        }
    }
}
