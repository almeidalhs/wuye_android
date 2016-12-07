package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/26 16:32
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GetUserIndexModel {

    private String result;
    /**
     * userDetailBean : {"userId":450214712,"userName":"w15700001001","nickName":"五七零一。","addTime":1478248172000,"userExt":{"user_id":450214712,"integral":"30696","secret_type":0,"type":1,"icon":"/imageServer/82450f281ac749a742c3a04d3d128d4fc03c.jpg","sex":"F","create_time":1478248172000,"update_time":1478248172000,"name_change":1,"next_level_integral":19289,"userLevel":7,"mobile":"15700001001","nick_name":"五七零一。","status":2,"verify_status":1,"can_live_room":1,"gold_coin":417584,"charm":820215,"can_chat":0,"can_shake":1,"convert_coin":417574,"vip_level":0,"chat_count":14,"accrue_coin":556514},"follow_count":12,"followed_count":478814964,"favorited_count":0,"comment_count":15,"achieve_level_pic":"/propServer/achievement/new/color_dj_4.png","achieve_charm_pic":"/propServer/achievement/new/corlo_ml_6.png","achieve_gold_pic":"/propServer/achievement/new/color_cf_6.png"}
     * guardlist : [{"user_id":2200019,"integral":"162090","secret_type":0,"type":1,"icon":"/imageServer/760E3db40e8e9a3244cd98cb039dc529ae21.jpg","sex":"F","create_time":1478249959000,"name_change":1,"next_level_integral":37897,"userLevel":9,"mobile":"13500000002","nick_name":"红花非花","status":2,"verify_status":1,"can_live_room":1,"gold_coin":777880,"charm":491964,"can_chat":1,"can_shake":1,"convert_coin":14318,"vip_level":0,"chat_count":12,"accrue_coin":184045}]
     * achieveList : ["倾国倾城","富可敌国","炉火纯青"]
     * blogImageMap : {"dataList":["/imageServer/8D3C38978511498d47f898da5d707e93bbc0-s.jpg","/imageServer/3188d391af011f6f4117ab44dac2e23272f5-s.jpg","/imageServer/8C51c5c36bbfa7ab48fbb97b59a37aeefb62-s.jpg"],"dataSize":4}
     * visitorMap : {"visitorSize":16,"visitorList":[{"user_id":100003790,"integral":"1","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1481017667000,"name_change":0,"next_level_integral":48,"userLevel":1,"mobile":"18578909061","nick_name":"夜友-515857","status":2,"verify_status":0,"gold_coin":50,"charm":0,"vip_level":0,"accrue_coin":50}]}
     * voiceUrl : /imageServer/5184466300f6dd5b43ef8809849543674c8a.caf
     * voiceLength : 3
     * signin : 0
     * userFelation : 0
     * isBlack : 0
     * friend : false
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
        /**
         * userId : 450214712
         * userName : w15700001001
         * nickName : 五七零一。
         * addTime : 1478248172000
         * userExt : {"user_id":450214712,"integral":"30696","secret_type":0,"type":1,"icon":"/imageServer/82450f281ac749a742c3a04d3d128d4fc03c.jpg","sex":"F","create_time":1478248172000,"update_time":1478248172000,"name_change":1,"next_level_integral":19289,"userLevel":7,"mobile":"15700001001","nick_name":"五七零一。","status":2,"verify_status":1,"can_live_room":1,"gold_coin":417584,"charm":820215,"can_chat":0,"can_shake":1,"convert_coin":417574,"vip_level":0,"chat_count":14,"accrue_coin":556514}
         * follow_count : 12
         * followed_count : 478814964
         * favorited_count : 0
         * comment_count : 15
         * achieve_level_pic : /propServer/achievement/new/color_dj_4.png
         * achieve_charm_pic : /propServer/achievement/new/corlo_ml_6.png
         * achieve_gold_pic : /propServer/achievement/new/color_cf_6.png
         */

        private UserDetailBeanBean userDetailBean;
        /**
         * dataList : ["/imageServer/8D3C38978511498d47f898da5d707e93bbc0-s.jpg","/imageServer/3188d391af011f6f4117ab44dac2e23272f5-s.jpg","/imageServer/8C51c5c36bbfa7ab48fbb97b59a37aeefb62-s.jpg"]
         * dataSize : 4
         */

        private BlogImageMapBean blogImageMap;
        /**
         * visitorSize : 16
         * visitorList : [{"user_id":100003790,"integral":"1","secret_type":0,"type":1,"icon":"/imageServer/9C66male.png","sex":"M","create_time":1481017667000,"name_change":0,"next_level_integral":48,"userLevel":1,"mobile":"18578909061","nick_name":"夜友-515857","status":2,"verify_status":0,"gold_coin":50,"charm":0,"vip_level":0,"accrue_coin":50}]
         */

        private VisitorMapBean visitorMap;
        private String voiceUrl;
        private String voiceLength;
        private int signin;
        private int userFelation;
        private int isBlack;
        private boolean friend;
        /**
         * user_id : 2200019
         * integral : 162090
         * secret_type : 0
         * type : 1
         * icon : /imageServer/760E3db40e8e9a3244cd98cb039dc529ae21.jpg
         * sex : F
         * create_time : 1478249959000
         * name_change : 1
         * next_level_integral : 37897
         * userLevel : 9
         * mobile : 13500000002
         * nick_name : 红花非花
         * status : 2
         * verify_status : 1
         * can_live_room : 1
         * gold_coin : 777880
         * charm : 491964
         * can_chat : 1
         * can_shake : 1
         * convert_coin : 14318
         * vip_level : 0
         * chat_count : 12
         * accrue_coin : 184045
         */

        private List<GuardlistBean> guardlist;
        private List<String> achieveList;

        public UserDetailBeanBean getUserDetailBean() {
            return userDetailBean;
        }

        public void setUserDetailBean(UserDetailBeanBean userDetailBean) {
            this.userDetailBean = userDetailBean;
        }

        public BlogImageMapBean getBlogImageMap() {
            return blogImageMap;
        }

        public void setBlogImageMap(BlogImageMapBean blogImageMap) {
            this.blogImageMap = blogImageMap;
        }

        public VisitorMapBean getVisitorMap() {
            return visitorMap;
        }

        public void setVisitorMap(VisitorMapBean visitorMap) {
            this.visitorMap = visitorMap;
        }

        public String getVoiceUrl() {
            return voiceUrl;
        }

        public void setVoiceUrl(String voiceUrl) {
            this.voiceUrl = voiceUrl;
        }

        public String getVoiceLength() {
            return voiceLength;
        }

        public void setVoiceLength(String voiceLength) {
            this.voiceLength = voiceLength;
        }

        public int getSignin() {
            return signin;
        }

        public void setSignin(int signin) {
            this.signin = signin;
        }

        public int getUserFelation() {
            return userFelation;
        }

        public void setUserFelation(int userFelation) {
            this.userFelation = userFelation;
        }

        public int getIsBlack() {
            return isBlack;
        }

        public void setIsBlack(int isBlack) {
            this.isBlack = isBlack;
        }

        public boolean isFriend() {
            return friend;
        }

        public void setFriend(boolean friend) {
            this.friend = friend;
        }

        public List<GuardlistBean> getGuardlist() {
            return guardlist;
        }

        public void setGuardlist(List<GuardlistBean> guardlist) {
            this.guardlist = guardlist;
        }

        public List<String> getAchieveList() {
            return achieveList;
        }

        public void setAchieveList(List<String> achieveList) {
            this.achieveList = achieveList;
        }

        public static class UserDetailBeanBean {
            private long userId;
            private String userName;
            private String nickName;
            private long addTime;
            /**
             * user_id : 450214712
             * integral : 30696
             * secret_type : 0
             * type : 1
             * icon : /imageServer/82450f281ac749a742c3a04d3d128d4fc03c.jpg
             * sex : F
             * create_time : 1478248172000
             * update_time : 1478248172000
             * name_change : 1
             * next_level_integral : 19289
             * userLevel : 7
             * mobile : 15700001001
             * nick_name : 五七零一。
             * status : 2
             * verify_status : 1
             * can_live_room : 1
             * gold_coin : 417584
             * charm : 820215
             * can_chat : 0
             * can_shake : 1
             * convert_coin : 417574
             * vip_level : 0
             * chat_count : 14
             * accrue_coin : 556514
             */

            private UserExtBean userExt;
            private int follow_count;
            private int followed_count;
            private int favorited_count;
            private int comment_count;
            private String achieve_level_pic;
            private String achieve_charm_pic;
            private String achieve_gold_pic;

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public long getAddTime() {
                return addTime;
            }

            public void setAddTime(long addTime) {
                this.addTime = addTime;
            }

            public UserExtBean getUserExt() {
                return userExt;
            }

            public void setUserExt(UserExtBean userExt) {
                this.userExt = userExt;
            }

            public int getFollow_count() {
                return follow_count;
            }

            public void setFollow_count(int follow_count) {
                this.follow_count = follow_count;
            }

            public int getFollowed_count() {
                return followed_count;
            }

            public void setFollowed_count(int followed_count) {
                this.followed_count = followed_count;
            }

            public int getFavorited_count() {
                return favorited_count;
            }

            public void setFavorited_count(int favorited_count) {
                this.favorited_count = favorited_count;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public String getAchieve_level_pic() {
                return achieve_level_pic;
            }

            public void setAchieve_level_pic(String achieve_level_pic) {
                this.achieve_level_pic = achieve_level_pic;
            }

            public String getAchieve_charm_pic() {
                return achieve_charm_pic;
            }

            public void setAchieve_charm_pic(String achieve_charm_pic) {
                this.achieve_charm_pic = achieve_charm_pic;
            }

            public String getAchieve_gold_pic() {
                return achieve_gold_pic;
            }

            public void setAchieve_gold_pic(String achieve_gold_pic) {
                this.achieve_gold_pic = achieve_gold_pic;
            }

            public static class UserExtBean {
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
                private String around_site;
                private int status;
                private int verify_status;
                private int can_live_room;
                private int gold_coin;
                private int charm;
                private int can_chat;
                private int can_shake;
                private int convert_coin;
                private int vip_level;
                private int chat_count;
                private int accrue_coin;

                public String getAround_site() {
                    return around_site;
                }

                public void setAround_site(String around_site) {
                    this.around_site = around_site;
                }

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

        public static class BlogImageMapBean {
            private int dataSize;
            private List<String> dataList;

            public int getDataSize() {
                return dataSize;
            }

            public void setDataSize(int dataSize) {
                this.dataSize = dataSize;
            }

            public List<String> getDataList() {
                return dataList;
            }

            public void setDataList(List<String> dataList) {
                this.dataList = dataList;
            }
        }

        public static class VisitorMapBean {
            private int visitorSize;
            /**
             * user_id : 100003790
             * integral : 1
             * secret_type : 0
             * type : 1
             * icon : /imageServer/9C66male.png
             * sex : M
             * create_time : 1481017667000
             * name_change : 0
             * next_level_integral : 48
             * userLevel : 1
             * mobile : 18578909061
             * nick_name : 夜友-515857
             * status : 2
             * verify_status : 0
             * gold_coin : 50
             * charm : 0
             * vip_level : 0
             * accrue_coin : 50
             */

            private List<VisitorListBean> visitorList;

            public int getVisitorSize() {
                return visitorSize;
            }

            public void setVisitorSize(int visitorSize) {
                this.visitorSize = visitorSize;
            }

            public List<VisitorListBean> getVisitorList() {
                return visitorList;
            }

            public void setVisitorList(List<VisitorListBean> visitorList) {
                this.visitorList = visitorList;
            }

            public static class VisitorListBean {
                private long user_id;
                private String integral;
                private int secret_type;
                private int type;
                private String icon;
                private String sex;
                private long create_time;
                private int name_change;
                private long next_level_integral;
                private int userLevel;
                private String mobile;
                private String nick_name;
                private int status;
                private int verify_status;
                private int gold_coin;
                private int charm;
                private int vip_level;
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

                public int getVip_level() {
                    return vip_level;
                }

                public void setVip_level(int vip_level) {
                    this.vip_level = vip_level;
                }

                public int getAccrue_coin() {
                    return accrue_coin;
                }

                public void setAccrue_coin(int accrue_coin) {
                    this.accrue_coin = accrue_coin;
                }
            }
        }

        public static class GuardlistBean {
            private long user_id;
            private String integral;
            private int secret_type;
            private int type;
            private String icon;
            private String sex;
            private long create_time;
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
