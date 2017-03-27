package com.atman.wysq.model.response;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/13 15:29
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ConfigModel {
    /**
     * api : http://api.5ys7.com
     * wuye : https://itunes.apple.com/us/app/wu-ye-shen-qi/id948764399?l=zh&ls=1&mt=8
     * wuyeandroid : http://api.5ys7.com:7000/apk/wuye_android.apk
     * img : http://www.5ys7.com
     * shop : [{"description":"无优惠\n每日首充+送12金币","title":"120金币","price":"¥6.00","name":"com.atman.wysq.coin.1"},{"description":"+送12金币\n每日首充+送24金币","title":"240金币","price":"¥12.00","name":"com.atman.wysq.coin.2"},{"description":"+送60金币\n每日首充+送60金币","title":"600金币","price":"¥30.00","name":"com.atman.wysq.coin.3"},{"description":"+送205金币\n每日首充+送136金币","title":"1360金币","price":"¥68.00","name":"com.atman.wysq.coin.4"},{"description":"+送672金币\n每日首充+送336金币","title":"3360金币","price":"¥168.00","name":"com.atman.wysq.coin.5"},{"description":"+送1640金币\n每日首充+送656金币","title":"6560金币","price":"¥328.00","name":"com.atman.wysq.coin.6"},{"description":"+送3888金币\n每日首充+送1296金币","title":"12960金币","price":"¥648.00","name":"com.atman.wysq.coin.7"}]
     * vip : [{"gain":"1","unlock":"1"},{"gain":"2","unlock":"2"},{"gain":"3","unlock":"3"},{"gain":"4","unlock":"4"}]
     * levelAuth : [{"name":"跟帖","type":"1","unlock":"1","tip":"很抱歉，该功能仅对1级及以上等级用户开放！"},{"name":"发帖","type":"2","unlock":"2","tip":"很抱歉，该功能仅对2级及以上等级用户开放！"},{"name":"语音广场","type":"3","unlock":"3","tip":"很抱歉，该功能仅对3级及以上等级用户开放！"},{"name":"聊天消息","type":"5","unlock":"2","tip":"很抱歉，该功能仅对2级及以上等级用户开放！"},{"name":"心愿墙","type":"6","unlock":"4","tip":"很抱歉，该功能仅对4级及以上等级用户开放！"},{"name":"魔图","type":"7","unlock":"5","tip":"很抱歉，该功能仅对5级及以上等级用户开放！"},{"name":"午夜FM","type":"8","unlock":"2","tip":"很抱歉，该功能仅对2级及以上等级用户开放！"},{"name":"商城","type":"9","unlock":"0","tip":"很抱歉，该功能仅对0级及以上等级用户开放！"},{"name":"一元购","type":"10","unlock":"2","tip":"很抱歉，该功能仅对2级及以上等级用户开放！"},{"name":"抢聊","type":"11","unlock":"3","tip":"很抱歉，该功能仅对3级及以上等级用户开放！"},{"name":"魔聊","type":"12","unlock":"3","tip":"很抱歉，该功能仅对3级及以上等级用户开放！"},{"name":"魔聊","type":"12","unlock":"3","tip":"很抱歉，该功能仅对3级及以上等级用户开放！"},{"name":"语音介绍","type":"14","unlock":"2","tip":"很抱歉，该功能仅对2级及以上等级用户开放！"}]
     * kUrlSchemeStatus : 1
     * KDiamondChargeCoin : 2:3
     * KDiamondCash : 40:1
     * KDiamondCashStart : 50
     * kShopBackImgUrl : http://api.5ys7.com:8001/imageServer/prop/temp/shop_back_img.png
     * kStartUpAdUrl : http://api.5ys7.com:8001/imageServer/prop/temp/640-960-textsmzdk.png
     * kStartUPAdBgUrl : http://api.5ys7.com:8001/imageServer/prop/temp/640-960-bgsmzdk.png
     * kLiveRoomMainUrl : http://api.5ys7.com:8001/imageServer/prop/temp/live_main_imgdoor3.png
     * kLiveRoomFlag : 0
     * kUserSexSwich : 0
     * kStartUpAdGoodsId : 6
     * kStartUpAdType : 10
     * kStartUpAdWebUrl : http://t.cn/RqkTYqQ
     * kGoldenOrderShow : 1
     * kPrivateChatCost : 2
     * kMagicChatLevel : 2
     */

    private String api;
    private String wuye;
    private String wuyeandroid;
    private String img;
    private String kUrlSchemeStatus;
    private String KDiamondChargeCoin;
    private String KDiamondCash;
    private String KDiamondCashStart;
    private String kShopBackImgUrl;
    private String kStartUpAdUrl;
    private String kStartUPAdBgUrl;
    private String kLiveRoomMainUrl;
    private String kLiveRoomFlag;
    private String kUserSexSwich;
    private String kStartUpAdGoodsId;
    private String kStartUpAdType;
    private String kStartUpAdWebUrl;
    private String kGoldenOrderShow;
    private String kPrivateChatCost;
    private String kMagicChatLevel;
    private List<ShopBean> shop;
    private List<VipBean> vip;
    private List<LevelAuthBean> levelAuth;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getWuye() {
        return wuye;
    }

    public void setWuye(String wuye) {
        this.wuye = wuye;
    }

    public String getWuyeandroid() {
        return wuyeandroid;
    }

    public void setWuyeandroid(String wuyeandroid) {
        this.wuyeandroid = wuyeandroid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getKUrlSchemeStatus() {
        return kUrlSchemeStatus;
    }

    public void setKUrlSchemeStatus(String kUrlSchemeStatus) {
        this.kUrlSchemeStatus = kUrlSchemeStatus;
    }

    public String getKDiamondChargeCoin() {
        return KDiamondChargeCoin;
    }

    public void setKDiamondChargeCoin(String KDiamondChargeCoin) {
        this.KDiamondChargeCoin = KDiamondChargeCoin;
    }

    public String getKDiamondCash() {
        return KDiamondCash;
    }

    public void setKDiamondCash(String KDiamondCash) {
        this.KDiamondCash = KDiamondCash;
    }

    public String getKDiamondCashStart() {
        return KDiamondCashStart;
    }

    public void setKDiamondCashStart(String KDiamondCashStart) {
        this.KDiamondCashStart = KDiamondCashStart;
    }

    public String getKShopBackImgUrl() {
        return kShopBackImgUrl;
    }

    public void setKShopBackImgUrl(String kShopBackImgUrl) {
        this.kShopBackImgUrl = kShopBackImgUrl;
    }

    public String getKStartUpAdUrl() {
        return kStartUpAdUrl;
    }

    public void setKStartUpAdUrl(String kStartUpAdUrl) {
        this.kStartUpAdUrl = kStartUpAdUrl;
    }

    public String getKStartUPAdBgUrl() {
        return kStartUPAdBgUrl;
    }

    public void setKStartUPAdBgUrl(String kStartUPAdBgUrl) {
        this.kStartUPAdBgUrl = kStartUPAdBgUrl;
    }

    public String getKLiveRoomMainUrl() {
        return kLiveRoomMainUrl;
    }

    public void setKLiveRoomMainUrl(String kLiveRoomMainUrl) {
        this.kLiveRoomMainUrl = kLiveRoomMainUrl;
    }

    public String getKLiveRoomFlag() {
        return kLiveRoomFlag;
    }

    public void setKLiveRoomFlag(String kLiveRoomFlag) {
        this.kLiveRoomFlag = kLiveRoomFlag;
    }

    public String getKUserSexSwich() {
        return kUserSexSwich;
    }

    public void setKUserSexSwich(String kUserSexSwich) {
        this.kUserSexSwich = kUserSexSwich;
    }

    public String getKStartUpAdGoodsId() {
        return kStartUpAdGoodsId;
    }

    public void setKStartUpAdGoodsId(String kStartUpAdGoodsId) {
        this.kStartUpAdGoodsId = kStartUpAdGoodsId;
    }

    public String getKStartUpAdType() {
        return kStartUpAdType;
    }

    public void setKStartUpAdType(String kStartUpAdType) {
        this.kStartUpAdType = kStartUpAdType;
    }

    public String getKStartUpAdWebUrl() {
        return kStartUpAdWebUrl;
    }

    public void setKStartUpAdWebUrl(String kStartUpAdWebUrl) {
        this.kStartUpAdWebUrl = kStartUpAdWebUrl;
    }

    public String getKGoldenOrderShow() {
        return kGoldenOrderShow;
    }

    public void setKGoldenOrderShow(String kGoldenOrderShow) {
        this.kGoldenOrderShow = kGoldenOrderShow;
    }

    public String getKPrivateChatCost() {
        return kPrivateChatCost;
    }

    public void setKPrivateChatCost(String kPrivateChatCost) {
        this.kPrivateChatCost = kPrivateChatCost;
    }

    public String getKMagicChatLevel() {
        return kMagicChatLevel;
    }

    public void setKMagicChatLevel(String kMagicChatLevel) {
        this.kMagicChatLevel = kMagicChatLevel;
    }

    public List<ShopBean> getShop() {
        return shop;
    }

    public void setShop(List<ShopBean> shop) {
        this.shop = shop;
    }

    public List<VipBean> getVip() {
        return vip;
    }

    public void setVip(List<VipBean> vip) {
        this.vip = vip;
    }

    public List<LevelAuthBean> getLevelAuth() {
        return levelAuth;
    }

    public void setLevelAuth(List<LevelAuthBean> levelAuth) {
        this.levelAuth = levelAuth;
    }

    public static class ShopBean {
        /**
         * description : 无优惠
         每日首充+送12金币
         * title : 120金币
         * price : ¥6.00
         * name : com.atman.wysq.coin.1
         */

        private String description;
        private String title;
        private String price;
        private String name;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class VipBean {
        /**
         * gain : 1
         * unlock : 1
         */

        private String gain;
        private String unlock;

        public String getGain() {
            return gain;
        }

        public void setGain(String gain) {
            this.gain = gain;
        }

        public String getUnlock() {
            return unlock;
        }

        public void setUnlock(String unlock) {
            this.unlock = unlock;
        }
    }

    public static class LevelAuthBean {
        /**
         * name : 跟帖
         * type : 1
         * unlock : 1
         * tip : 很抱歉，该功能仅对1级及以上等级用户开放！
         */

        private String name;
        private String type;
        private String unlock;
        private String tip;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUnlock() {
            return unlock;
        }

        public void setUnlock(String unlock) {
            this.unlock = unlock;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }
    }
}
