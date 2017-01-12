package com.atman.wysq.model.response;

/**
 * Created by tangbingliang on 17/1/9.
 */

public class ChatRoomMessageModel {

    /**
     * isself : 0
     * giftId : 0
     * type : 0
     * id : 30234
     * user : {"userId":6000000313,"icon":"/IMAGE/9C/66/male.png","nickName":"红红火火","userLevel":2,"verify_status":0,"sex":"M"}
     * content : 刚刚好
     * sendTime : 1.483955752975928E9
     * isAnchorImage : 0
     * cmdType : 0
     * banChatUserId : 0
     */

    private int isself;
    private int giftId;
    private int type;
    private String id;
    private UserBean user;
    private String content;
    private String giftName;
    private String giftMessage;
    private double sendTime;
    private int isAnchorImage;
    private int cmdType;
    private int banChatUserId;

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftMessage() {
        return giftMessage;
    }

    public void setGiftMessage(String giftMessage) {
        this.giftMessage = giftMessage;
    }

    public int getIsself() {
        return isself;
    }

    public void setIsself(int isself) {
        this.isself = isself;
    }

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getSendTime() {
        return sendTime;
    }

    public void setSendTime(double sendTime) {
        this.sendTime = sendTime;
    }

    public int getIsAnchorImage() {
        return isAnchorImage;
    }

    public void setIsAnchorImage(int isAnchorImage) {
        this.isAnchorImage = isAnchorImage;
    }

    public int getCmdType() {
        return cmdType;
    }

    public void setCmdType(int cmdType) {
        this.cmdType = cmdType;
    }

    public int getBanChatUserId() {
        return banChatUserId;
    }

    public void setBanChatUserId(int banChatUserId) {
        this.banChatUserId = banChatUserId;
    }

    public static class UserBean {
        /**
         * userId : 6000000313
         * icon : /IMAGE/9C/66/male.png
         * nickName : 红红火火
         * userLevel : 2
         * verify_status : 0
         * sex : M
         */

        private long userId;
        private String icon;
        private String nickName;
        private int userLevel;
        private int verify_status;
        private String sex;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public int getVerify_status() {
            return verify_status;
        }

        public void setVerify_status(int verify_status) {
            this.verify_status = verify_status;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
