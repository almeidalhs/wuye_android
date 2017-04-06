package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/3/24.
 */

public class CommunityNewModel {
    /**
     * result : 1
     * body : [{"blog_id":185857,"blog_board_id":3,"user_id":450214548,"title":"发布","content":" 1 <wysqimg=/imageServer/03A1d295e946e6bc4d91be31cd21f0b721a0.jpg=wysqimg> 2 <wysqimg=/imageServer/8A234ae3172aed424be3ae7c60f804f61231.jpg=wysqimg> 3 <wysqimg=/imageServer/8B07c8b0ccc266444ca2bc2c328a87f0dca9.jpg=wysqimg> 4 <wysqimg=/imageServer/55ECe66471f5d12046e095a4edd534699b29.jpg=wysqimg> 5 <wysqimg=/imageServer/8DCC218432da3b674d0383aae9fa304ec4b4.jpg=wysqimg> 6 <wysqimg=/imageServer/B73866710fb5fc6b48bfbcba95d446c8024b.jpg=wysqimg> 7 <wysqimg=/imageServer/97795aa3a5ee1d7b44f39567e84680b628b5.jpg=wysqimg> 8 <wysqimg=/imageServer/447Ce7202a34e53e4e7fb8eadc8785494178.jpg=wysqimg> 9 <wysqimg=/imageServer/9BA3ddd04c34d64a4ab691e960c64ef4e76d.jpg=wysqimg> 10","create_time":1480472130000,"update_time":1481613075000,"status":2,"type":1,"stick":0,"comment_count":0,"favorite_count":0,"view_count":2,"blogComments":[],"anonymity":1,"anonymityUser":{"icon":"/imageServer/9C66zhongxing.png","next_level_integral":1,"userLevel":0,"nick_name":"匿名用户","gold_coin":0,"charm":0},"replay_flag":0,"unread_flag":0,"user_name":"狼心狗肺","icon":"/imageServer/103B4977ebd4d68c41cb94ace9ab10dd6605.jpg","sex":"M","verify_status":0,"userLevel":10,"achieve_level_pic":"/propServer/achievement/new/color_dj_6.png","thumb_pic":"/imageServer/03A1d295e946e6bc4d91be31cd21f0b721a0-s.jpg","goods_id":0,"wysqImglist":["/imageServer/03A1d295e946e6bc4d91be31cd21f0b721a0-s.jpg","/imageServer/8A234ae3172aed424be3ae7c60f804f61231-s.jpg","/imageServer/8B07c8b0ccc266444ca2bc2c328a87f0dca9-s.jpg","/imageServer/55ECe66471f5d12046e095a4edd534699b29-s.jpg","/imageServer/8DCC218432da3b674d0383aae9fa304ec4b4-s.jpg","/imageServer/B73866710fb5fc6b48bfbcba95d446c8024b-s.jpg","/imageServer/97795aa3a5ee1d7b44f39567e84680b628b5-s.jpg","/imageServer/447Ce7202a34e53e4e7fb8eadc8785494178-s.jpg","/imageServer/9BA3ddd04c34d64a4ab691e960c64ef4e76d-s.jpg"],"vip_level":0,"comment_type":0,"achieve_gold_pic":"/propServer/achievement/new/color_cf_5.png","giftList":[],"goodsList":[],"blogGoodsList":[],"img":"/imageServer/03A1d295e946e6bc4d91be31cd21f0b721a0-s.jpg","url":"","file_length":0,"category":1}]
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
         * blog_id : 185857
         * blog_board_id : 3
         * user_id : 450214548
         * title : 发布
         * content :  1 <wysqimg=/imageServer/03A1d295e946e6bc4d91be31cd21f0b721a0.jpg=wysqimg> 2 <wysqimg=/imageServer/8A234ae3172aed424be3ae7c60f804f61231.jpg=wysqimg> 3 <wysqimg=/imageServer/8B07c8b0ccc266444ca2bc2c328a87f0dca9.jpg=wysqimg> 4 <wysqimg=/imageServer/55ECe66471f5d12046e095a4edd534699b29.jpg=wysqimg> 5 <wysqimg=/imageServer/8DCC218432da3b674d0383aae9fa304ec4b4.jpg=wysqimg> 6 <wysqimg=/imageServer/B73866710fb5fc6b48bfbcba95d446c8024b.jpg=wysqimg> 7 <wysqimg=/imageServer/97795aa3a5ee1d7b44f39567e84680b628b5.jpg=wysqimg> 8 <wysqimg=/imageServer/447Ce7202a34e53e4e7fb8eadc8785494178.jpg=wysqimg> 9 <wysqimg=/imageServer/9BA3ddd04c34d64a4ab691e960c64ef4e76d.jpg=wysqimg> 10
         * create_time : 1480472130000
         * update_time : 1481613075000
         * status : 2
         * type : 1
         * stick : 0
         * comment_count : 0
         * favorite_count : 0
         * view_count : 2
         * blogComments : []
         * anonymity : 1
         * anonymityUser : {"icon":"/imageServer/9C66zhongxing.png","next_level_integral":1,"userLevel":0,"nick_name":"匿名用户","gold_coin":0,"charm":0}
         * replay_flag : 0
         * unread_flag : 0
         * user_name : 狼心狗肺
         * icon : /imageServer/103B4977ebd4d68c41cb94ace9ab10dd6605.jpg
         * sex : M
         * verify_status : 0
         * userLevel : 10
         * achieve_level_pic : /propServer/achievement/new/color_dj_6.png
         * thumb_pic : /imageServer/03A1d295e946e6bc4d91be31cd21f0b721a0-s.jpg
         * goods_id : 0
         * wysqImglist : ["/imageServer/03A1d295e946e6bc4d91be31cd21f0b721a0-s.jpg","/imageServer/8A234ae3172aed424be3ae7c60f804f61231-s.jpg","/imageServer/8B07c8b0ccc266444ca2bc2c328a87f0dca9-s.jpg","/imageServer/55ECe66471f5d12046e095a4edd534699b29-s.jpg","/imageServer/8DCC218432da3b674d0383aae9fa304ec4b4-s.jpg","/imageServer/B73866710fb5fc6b48bfbcba95d446c8024b-s.jpg","/imageServer/97795aa3a5ee1d7b44f39567e84680b628b5-s.jpg","/imageServer/447Ce7202a34e53e4e7fb8eadc8785494178-s.jpg","/imageServer/9BA3ddd04c34d64a4ab691e960c64ef4e76d-s.jpg"]
         * vip_level : 0
         * comment_type : 0
         * achieve_gold_pic : /propServer/achievement/new/color_cf_5.png
         * giftList : []
         * goodsList : []
         * blogGoodsList : []
         * img : /imageServer/03A1d295e946e6bc4d91be31cd21f0b721a0-s.jpg
         * url :
         * file_length : 0
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
        private String user_name;
        private String icon;
        private String sex;
        private int verify_status;
        private int userLevel;
        private String achieve_level_pic;
        private String thumb_pic;
        private int goods_id;
        private int vip_level;
        private int comment_type;
        private String achieve_gold_pic;
        private String img;
        private String url;
        private int file_length;
        private int category;
        private String like_num;
        private List<?> blogComments;
        private List<String> wysqImglist;
        private List<?> giftList;
        private List<?> goodsList;
        private List<?> blogGoodsList;

        public String getLike_num() {
            return like_num;
        }

        public void setLike_num(String like_num) {
            this.like_num = like_num;
        }

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

        public String getThumb_pic() {
            return thumb_pic;
        }

        public void setThumb_pic(String thumb_pic) {
            this.thumb_pic = thumb_pic;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getFile_length() {
            return file_length;
        }

        public void setFile_length(int file_length) {
            this.file_length = file_length;
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

        public List<String> getWysqImglist() {
            return wysqImglist;
        }

        public void setWysqImglist(List<String> wysqImglist) {
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
            private long next_level_integral;
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
}
