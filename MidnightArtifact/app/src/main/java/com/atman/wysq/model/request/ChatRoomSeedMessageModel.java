package com.atman.wysq.model.request;

/**
 * Created by tangbingliang on 17/1/10.
 */

public class ChatRoomSeedMessageModel {
    /**
     * banChatUserId : 0
     * id : 30234
     * giftId : 0
     * isself : 0
     * sendTime : 1.484035694480216E9
     * isAnchorImage : 0
     * user : {"verify_status":0,"nickName":"红红火火","icon":"/IMAGE/9C/66/male.png","sex":"M","userId":6000000313,"userLevel":3}
     * type : 1
     * cmdType : 0
     */

    private long banChatUserId;
    private long id;
    private int giftId;
    private int isself;
    private long sendTime;
    private int isAnchorImage;
    private UserBean user;
    private int type;
    private int cmdType;

    public long getBanChatUserId() {
        return banChatUserId;
    }

    public void setBanChatUserId(long banChatUserId) {
        this.banChatUserId = banChatUserId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public int getIsself() {
        return isself;
    }

    public void setIsself(int isself) {
        this.isself = isself;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public int getIsAnchorImage() {
        return isAnchorImage;
    }

    public void setIsAnchorImage(int isAnchorImage) {
        this.isAnchorImage = isAnchorImage;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCmdType() {
        return cmdType;
    }

    public void setCmdType(int cmdType) {
        this.cmdType = cmdType;
    }

    public static class UserBean {
        /**
         * verify_status : 0
         * nickName : 红红火火
         * icon : /IMAGE/9C/66/male.png
         * sex : M
         * userId : 6000000313
         * userLevel : 3
         */

        private int verify_status;
        private String nickName;
        private String icon;
        private String sex;
        private long userId;
        private int userLevel;

        public int getVerify_status() {
            return verify_status;
        }

        public void setVerify_status(int verify_status) {
            this.verify_status = verify_status;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
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

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }
    }
}
