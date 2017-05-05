package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/5/3.
 */

public class ReplayListModel {
    /**
     * result : 1
     * body : [{"blog":{"blog_id":186423,"blog_board_id":1,"user_id":450214625,"title":"八点","content":"啊咯","create_time":1493973345000,"update_time":1493973413000,"status":2,"type":1,"stick":0,"comment_count":3,"favorite_count":0,"view_count":5,"blogComments":[],"anonymity":1,"anonymityUser":{"icon":"/imageServer/9C66zhongxing.png","next_level_integral":1,"userLevel":0,"nick_name":"匿名用户","gold_coin":0,"charm":0},"replay_flag":1,"unread_flag":1,"board_title":"午夜活动专区","user_name":"大于利","icon":"/imageServer/D6111b63634823824924b780c95183d99ddc.jpg","sex":"F","verify_status":1,"userLevel":8,"achieve_level_pic":"/propServer/achievement/new/color_dj_4.png","goods_id":0,"wysqImglist":[],"vip_level":0,"comment_type":0,"achieve_gold_pic":"/propServer/achievement/new/color_cf_1.png","giftList":[],"goodsList":[],"blogGoodsList":[],"img":"/propServer/icon/blog.png","category":1},"parentComment":{"blog_comment_id":276031,"blog_id":186110,"user_id":450214609,"content":"好奇","status":1,"create_time":1490853443000,"user_name":"待我长发及腰","icon":"/imageServer/06E2329b77e9538444fa9013960afa4495de.jpg","sex":"F","userLevel":3,"unread_flag":1,"achieve_level_pic":"/propServer/achievement/new/color_dj_2.png","achieve_charm_pic":"/propServer/achievement/new/corlo_ml_1.png","achieve_gold_pic":"/propServer/achievement/new/color_cf_2.png","vip_level":0},"comment":{"blog_comment_id":276413,"blog_id":186423,"user_id":450214712,"content":"unmolested","status":1,"create_time":1493973387000,"user_name":"五七零一。","icon":"/imageServer/CE9D38d6a4cc1e2542809be6d7b5d497bf95.jpg","sex":"F","userLevel":8,"unread_flag":1,"achieve_level_pic":"/propServer/achievement/new/color_dj_4.png","achieve_charm_pic":"/propServer/achievement/new/corlo_ml_6.png","achieve_gold_pic":"/propServer/achievement/new/color_cf_6.png","vip_level":0}}]
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
         * blog : {"blog_id":186423,"blog_board_id":1,"user_id":450214625,"title":"八点","content":"啊咯","create_time":1493973345000,"update_time":1493973413000,"status":2,"type":1,"stick":0,"comment_count":3,"favorite_count":0,"view_count":5,"blogComments":[],"anonymity":1,"anonymityUser":{"icon":"/imageServer/9C66zhongxing.png","next_level_integral":1,"userLevel":0,"nick_name":"匿名用户","gold_coin":0,"charm":0},"replay_flag":1,"unread_flag":1,"board_title":"午夜活动专区","user_name":"大于利","icon":"/imageServer/D6111b63634823824924b780c95183d99ddc.jpg","sex":"F","verify_status":1,"userLevel":8,"achieve_level_pic":"/propServer/achievement/new/color_dj_4.png","goods_id":0,"wysqImglist":[],"vip_level":0,"comment_type":0,"achieve_gold_pic":"/propServer/achievement/new/color_cf_1.png","giftList":[],"goodsList":[],"blogGoodsList":[],"img":"/propServer/icon/blog.png","category":1}
         * parentComment : {"blog_comment_id":276031,"blog_id":186110,"user_id":450214609,"content":"好奇","status":1,"create_time":1490853443000,"user_name":"待我长发及腰","icon":"/imageServer/06E2329b77e9538444fa9013960afa4495de.jpg","sex":"F","userLevel":3,"unread_flag":1,"achieve_level_pic":"/propServer/achievement/new/color_dj_2.png","achieve_charm_pic":"/propServer/achievement/new/corlo_ml_1.png","achieve_gold_pic":"/propServer/achievement/new/color_cf_2.png","vip_level":0}
         * comment : {"blog_comment_id":276413,"blog_id":186423,"user_id":450214712,"content":"unmolested","status":1,"create_time":1493973387000,"user_name":"五七零一。","icon":"/imageServer/CE9D38d6a4cc1e2542809be6d7b5d497bf95.jpg","sex":"F","userLevel":8,"unread_flag":1,"achieve_level_pic":"/propServer/achievement/new/color_dj_4.png","achieve_charm_pic":"/propServer/achievement/new/corlo_ml_6.png","achieve_gold_pic":"/propServer/achievement/new/color_cf_6.png","vip_level":0}
         */

        private BlogBean blog;
        private ParentCommentBean parentComment;
        private CommentBean comment;

        public BlogBean getBlog() {
            return blog;
        }

        public void setBlog(BlogBean blog) {
            this.blog = blog;
        }

        public ParentCommentBean getParentComment() {
            return parentComment;
        }

        public void setParentComment(ParentCommentBean parentComment) {
            this.parentComment = parentComment;
        }

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public static class BlogBean {
            /**
             * blog_id : 186423
             * blog_board_id : 1
             * user_id : 450214625
             * title : 八点
             * content : 啊咯
             * create_time : 1493973345000
             * update_time : 1493973413000
             * status : 2
             * type : 1
             * stick : 0
             * comment_count : 3
             * favorite_count : 0
             * view_count : 5
             * blogComments : []
             * anonymity : 1
             * anonymityUser : {"icon":"/imageServer/9C66zhongxing.png","next_level_integral":1,"userLevel":0,"nick_name":"匿名用户","gold_coin":0,"charm":0}
             * replay_flag : 1
             * unread_flag : 1
             * board_title : 午夜活动专区
             * user_name : 大于利
             * icon : /imageServer/D6111b63634823824924b780c95183d99ddc.jpg
             * sex : F
             * verify_status : 1
             * userLevel : 8
             * achieve_level_pic : /propServer/achievement/new/color_dj_4.png
             * goods_id : 0
             * wysqImglist : []
             * vip_level : 0
             * comment_type : 0
             * achieve_gold_pic : /propServer/achievement/new/color_cf_1.png
             * giftList : []
             * goodsList : []
             * blogGoodsList : []
             * img : /propServer/icon/blog.png
             * category : 1
             */

            private long blog_id;
            private int blog_board_id;
            private long user_id;
            private String title;
            private String content;
            private long create_time;
            private long update_time;
            private int status;
            private int type;
            private int stick;
            private int comment_count;
            private int favorite_count;
            private int view_count;
            private int anonymity;
            private AnonymityUserBean anonymityUser;
            private int replay_flag;
            private int unread_flag;
            private String board_title;
            private String user_name;
            private String icon;
            private String sex;
            private int verify_status;
            private int userLevel;
            private String achieve_level_pic;
            private int goods_id;
            private int vip_level;
            private int comment_type;
            private String achieve_gold_pic;
            private String img;
            private int category;
            private List<?> blogComments;
            private List<?> wysqImglist;
            private List<?> giftList;
            private List<?> goodsList;
            private List<?> blogGoodsList;

            public long getBlog_id() {
                return blog_id;
            }

            public void setBlog_id(long blog_id) {
                this.blog_id = blog_id;
            }

            public int getBlog_board_id() {
                return blog_board_id;
            }

            public void setBlog_board_id(int blog_board_id) {
                this.blog_board_id = blog_board_id;
            }

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getStick() {
                return stick;
            }

            public void setStick(int stick) {
                this.stick = stick;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public int getFavorite_count() {
                return favorite_count;
            }

            public void setFavorite_count(int favorite_count) {
                this.favorite_count = favorite_count;
            }

            public int getView_count() {
                return view_count;
            }

            public void setView_count(int view_count) {
                this.view_count = view_count;
            }

            public int getAnonymity() {
                return anonymity;
            }

            public void setAnonymity(int anonymity) {
                this.anonymity = anonymity;
            }

            public AnonymityUserBean getAnonymityUser() {
                return anonymityUser;
            }

            public void setAnonymityUser(AnonymityUserBean anonymityUser) {
                this.anonymityUser = anonymityUser;
            }

            public int getReplay_flag() {
                return replay_flag;
            }

            public void setReplay_flag(int replay_flag) {
                this.replay_flag = replay_flag;
            }

            public int getUnread_flag() {
                return unread_flag;
            }

            public void setUnread_flag(int unread_flag) {
                this.unread_flag = unread_flag;
            }

            public String getBoard_title() {
                return board_title;
            }

            public void setBoard_title(String board_title) {
                this.board_title = board_title;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
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

            public int getVerify_status() {
                return verify_status;
            }

            public void setVerify_status(int verify_status) {
                this.verify_status = verify_status;
            }

            public int getUserLevel() {
                return userLevel;
            }

            public void setUserLevel(int userLevel) {
                this.userLevel = userLevel;
            }

            public String getAchieve_level_pic() {
                return achieve_level_pic;
            }

            public void setAchieve_level_pic(String achieve_level_pic) {
                this.achieve_level_pic = achieve_level_pic;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getVip_level() {
                return vip_level;
            }

            public void setVip_level(int vip_level) {
                this.vip_level = vip_level;
            }

            public int getComment_type() {
                return comment_type;
            }

            public void setComment_type(int comment_type) {
                this.comment_type = comment_type;
            }

            public String getAchieve_gold_pic() {
                return achieve_gold_pic;
            }

            public void setAchieve_gold_pic(String achieve_gold_pic) {
                this.achieve_gold_pic = achieve_gold_pic;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getCategory() {
                return category;
            }

            public void setCategory(int category) {
                this.category = category;
            }

            public List<?> getBlogComments() {
                return blogComments;
            }

            public void setBlogComments(List<?> blogComments) {
                this.blogComments = blogComments;
            }

            public List<?> getWysqImglist() {
                return wysqImglist;
            }

            public void setWysqImglist(List<?> wysqImglist) {
                this.wysqImglist = wysqImglist;
            }

            public List<?> getGiftList() {
                return giftList;
            }

            public void setGiftList(List<?> giftList) {
                this.giftList = giftList;
            }

            public List<?> getGoodsList() {
                return goodsList;
            }

            public void setGoodsList(List<?> goodsList) {
                this.goodsList = goodsList;
            }

            public List<?> getBlogGoodsList() {
                return blogGoodsList;
            }

            public void setBlogGoodsList(List<?> blogGoodsList) {
                this.blogGoodsList = blogGoodsList;
            }

            public static class AnonymityUserBean {
                /**
                 * icon : /imageServer/9C66zhongxing.png
                 * next_level_integral : 1
                 * userLevel : 0
                 * nick_name : 匿名用户
                 * gold_coin : 0
                 * charm : 0
                 */

                private String icon;
                private int next_level_integral;
                private int userLevel;
                private String nick_name;
                private int gold_coin;
                private int charm;

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public int getNext_level_integral() {
                    return next_level_integral;
                }

                public void setNext_level_integral(int next_level_integral) {
                    this.next_level_integral = next_level_integral;
                }

                public int getUserLevel() {
                    return userLevel;
                }

                public void setUserLevel(int userLevel) {
                    this.userLevel = userLevel;
                }

                public String getNick_name() {
                    return nick_name;
                }

                public void setNick_name(String nick_name) {
                    this.nick_name = nick_name;
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
            }
        }

        public static class ParentCommentBean {
            /**
             * blog_comment_id : 276031
             * blog_id : 186110
             * user_id : 450214609
             * content : 好奇
             * status : 1
             * create_time : 1490853443000
             * user_name : 待我长发及腰
             * icon : /imageServer/06E2329b77e9538444fa9013960afa4495de.jpg
             * sex : F
             * userLevel : 3
             * unread_flag : 1
             * achieve_level_pic : /propServer/achievement/new/color_dj_2.png
             * achieve_charm_pic : /propServer/achievement/new/corlo_ml_1.png
             * achieve_gold_pic : /propServer/achievement/new/color_cf_2.png
             * vip_level : 0
             */

            private long blog_comment_id;
            private long blog_id;
            private long user_id;
            private String content;
            private int status;
            private long create_time;
            private String user_name;
            private String icon;
            private String sex;
            private int userLevel;
            private int unread_flag;
            private String achieve_level_pic;
            private String achieve_charm_pic;
            private String achieve_gold_pic;
            private int vip_level;

            public long getBlog_comment_id() {
                return blog_comment_id;
            }

            public void setBlog_comment_id(long blog_comment_id) {
                this.blog_comment_id = blog_comment_id;
            }

            public long getBlog_id() {
                return blog_id;
            }

            public void setBlog_id(long blog_id) {
                this.blog_id = blog_id;
            }

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
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

            public int getUserLevel() {
                return userLevel;
            }

            public void setUserLevel(int userLevel) {
                this.userLevel = userLevel;
            }

            public int getUnread_flag() {
                return unread_flag;
            }

            public void setUnread_flag(int unread_flag) {
                this.unread_flag = unread_flag;
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

            public int getVip_level() {
                return vip_level;
            }

            public void setVip_level(int vip_level) {
                this.vip_level = vip_level;
            }
        }

        public static class CommentBean {
            /**
             * blog_comment_id : 276413
             * blog_id : 186423
             * user_id : 450214712
             * content : unmolested
             * status : 1
             * create_time : 1493973387000
             * user_name : 五七零一。
             * icon : /imageServer/CE9D38d6a4cc1e2542809be6d7b5d497bf95.jpg
             * sex : F
             * userLevel : 8
             * unread_flag : 1
             * achieve_level_pic : /propServer/achievement/new/color_dj_4.png
             * achieve_charm_pic : /propServer/achievement/new/corlo_ml_6.png
             * achieve_gold_pic : /propServer/achievement/new/color_cf_6.png
             * vip_level : 0
             */

            private long blog_comment_id;
            private long blog_id;
            private long user_id;
            private String content;
            private int status;
            private long create_time;
            private String user_name;
            private String icon;
            private String sex;
            private int userLevel;
            private int unread_flag;
            private String achieve_level_pic;
            private String achieve_charm_pic;
            private String achieve_gold_pic;
            private int vip_level;

            public long getBlog_comment_id() {
                return blog_comment_id;
            }

            public void setBlog_comment_id(long blog_comment_id) {
                this.blog_comment_id = blog_comment_id;
            }

            public long getBlog_id() {
                return blog_id;
            }

            public void setBlog_id(long blog_id) {
                this.blog_id = blog_id;
            }

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
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

            public int getUserLevel() {
                return userLevel;
            }

            public void setUserLevel(int userLevel) {
                this.userLevel = userLevel;
            }

            public int getUnread_flag() {
                return unread_flag;
            }

            public void setUnread_flag(int unread_flag) {
                this.unread_flag = unread_flag;
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

            public int getVip_level() {
                return vip_level;
            }

            public void setVip_level(int vip_level) {
                this.vip_level = vip_level;
            }
        }
    }
}
