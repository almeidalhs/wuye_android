package com.atman.wysq.utils;

import okhttp3.MediaType;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/6 09:48
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class Common {

    /**************************http通用设置*****************************/
    public static int timeOut = 15000;
    public static int timeOutTwo = 120000;
    public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String ContentType = "application/json; charset=utf-8";
    /**************************http通用设置*****************************/



    /**************************http基础访问路径*****************************/
    public static String hostUrl = "http://192.168.1.134:8080/";
    public static String ImageUrl = "http://192.168.1.134:8001";

//    public static String hostUrl = "http://api.5ys7.com/";
//    public static String ImageUrl = "http://api.5ys7.com";
    /**************************http基础访问路径*****************************/



    /**************************http访问路径*****************************/
    //登录
    public static String Url_Login = hostUrl + "login";
    //发送验证码
    public static String Url_SeedCode = hostUrl + "rest/message/";
    //注册
    public static String Url_Register = hostUrl + "rest/register";
    //忘记密码
    public static String Url_NewPW = hostUrl + "rest/forgotAndChangePwd";
    //获取用户信息
    public static String Url_GetUserInfo = hostUrl + "rest/user/info/";
    //修改密码
    public static String Url_Reset_PW = hostUrl + "rest/user/password";
    //修改昵称
    public static String Url_Reset_Nick = hostUrl + "rest/user/username/";
    //修改头像
    public static String Url_Reset_Head = hostUrl + "uploadFile";
    //退出
    public static String Url_Logout = hostUrl + "rest/user/logout";
    //获取任务列表
    public static String Url_Get_Task = hostUrl + "rest/task/all";
    //获取任务奖励
    public static String Url_Rewarded = hostUrl + "rest/task/rewarded";
    //完成任务
    public static String Url_Finish_Task = hostUrl + "rest/task/usertask";
    //修改头像
    public static String Url_Modify_Head = hostUrl + "rest/user/icon";
    //充值－－增加订单
    public static String Url_Recharge_Add_Order = hostUrl + "rest/order/addgoldenorder";
    //充值－－增加订单－－支付宝
    public static String Url_Recharge_Add_Order_Alipay = hostUrl + "rest/order/generatepayparam/";
    //充值－－增加订单－－微信
    public static String Url_Recharge_Add_Order_WeiXin = hostUrl + "rest/order/weixin/prepay";
    //轮播图获取,1 广告系列 2 俱乐部顶部  3 俱乐部顶部banner之下  4 秘密说首页
    public static String Url_AdList = hostUrl + "rest/category/serieslist/";
    //获取俱乐部分类
    public static String Url_Category_List = hostUrl + "rest/category/list";
    //获取俱乐部二级分类
    public static String Url_Two_Category_List = hostUrl + "rest/category/leaflist/";
    //获取分类下商品
    public static String Url_Get_Category_List = hostUrl + "rest/goods/category/";
    public static String Url_Get_Type_List = hostUrl + "rest/goods/type/";
    //获取商品详情
    public static String Url_Get_Category_Detail = hostUrl + "rest/goods/detail/";
    //获取商品评论
    public static String Url_Get_Goods_comment = hostUrl + "rest/goods/comment/";
    //获取用户收货地址
    public static String Url_Get_Address_List = hostUrl + "rest/user/address";
    public static String Url_Get_Address_ByOrderId = hostUrl + "rest/order/address/";
    //添加订单
    public static String Url_Add_Order = hostUrl + "rest/order/add";
    //获取省数据
    public static String Url_Get_Province = hostUrl + "rest/order/getProvince";
    //获取市数据
    public static String Url_Get_City = hostUrl + "rest/order/getCity/";
    //获取县数据
    public static String Url_Get_District = hostUrl + "rest/order/getDistrict/";
    //添加新地址
    public static String Url_Add_Address = hostUrl + "rest/user/address";
    //删除地址
    public static String Url_Delete_Address = hostUrl + "rest/user/address/";
    //获取我的订单
    public static String Url_Get_OrderList = hostUrl + "rest/order/my/";
    //获取订单详情
    public static String Url_Get_OrderDetail = hostUrl + "rest/order/status/";
    //获取公用配置信息
    public static String Url_Get_GoldenRole = hostUrl + "rest/user/goldenrole";
    //检查版本更新
    public static String Url_Get_Version = hostUrl + "rest/androidUpdate";
    //获取社区模块分类
    public static String Url_Get_BlogBoard = hostUrl + "rest/blog/board";
    //获取论坛帖子列表 rest/blog/boardlist/{blog_board_id}/{type}/{update_time}
    public static String Url_Get_BlogList = hostUrl + "rest/blog/bloglist/";
    //获取帖子详情
    public static String Url_Get_BlogDetail = hostUrl + "rest/blog/detail/";
    //获取帖子评论
    public static String Url_Get_BlogDetail_CommentList = hostUrl + "rest/blog/comment/";
    //收藏帖子
    public static String Url_Get_BlogCollection = hostUrl + "rest/blog/favorite/";
    //取消收藏帖子
    public static String Url_Get_BlogCollection_Not = hostUrl + "rest/blog/favorite/";
    //举报
    public static String Url_RePort = hostUrl + "rest/user/feedback";
    //增加评论
    public static String Url_Add_Comment = hostUrl + "rest/blog/comment";
    //评论点赞
    public static String Url_Add_Like = hostUrl + "rest/magic/commentlike/";
    //拉黑
    public static String Url_Add_BlackList = hostUrl + "rest/corruption/add";
    //帖子评论的评论列表
    public static String Url_SubComment_List = hostUrl + "rest/blog/subcomment/";
    //发帖
    public static String Url_Add_Post = hostUrl + "rest/blog/add";
    //添加打赏
    public static String Url_Add_Award = hostUrl + "rest/award/add";
    //获取打赏列表
    public static String Url_Get_Award_List = hostUrl + "rest/award/list/";
    //获取我的收藏
    public static String Url_Get_My_Collection = hostUrl + "rest/blog/favorite/";
    //获取我的秘密
    public static String Url_Get_My_Sceret = hostUrl + "rest/blog/user/";
    //获取我的评论或评论我的
    public static String Url_Get_UserComment = hostUrl + "rest/blog/usercomment/";
    //添加帖子浏览量
    public static String Url_Add_Browse = hostUrl + "rest/blog/increaseviewcount/";
    //删除帖子
    public static String Url_Delete_Post = hostUrl + "rest/blog/delete/";
    //性别认证
    public static String Url_Verify = hostUrl + "rest/user/verify";
    //获取聊天token
    public static String Url_Get_ChatToken = hostUrl + "rest/chat/chattoken";
    //获取好友列表
    public static String Url_Get_Fans = hostUrl + "rest/user/fans";
    //获取用户基本信息
    public static String Url_Get_UserIndex = hostUrl + "rest/user/index";
    //获取访客信息
    public static String Url_Get_Browse = hostUrl + "rest/user/browse/";
    //获取守护者信息
    public static String Url_Get_Guard = hostUrl + "rest/user/guard/";
    //加好友
    public static String Url_Add_Friends = hostUrl + "rest/user/addfan/";
    //删除好友
    public static String Url_Delete_Friends = hostUrl + "rest/user/follow/";
    //获取物流URL
    public static String Url_Get_Logistics = hostUrl + "rest/order/logistics/";
    //获取礼物列表
    public static String Url_Get_GiftList = hostUrl + "rest/gift/list";
    //购买礼物
    public static String Url_Pay_GiftList = hostUrl + "rest/gift/buy/";
    //发消息扣金币
    public static String Url_SeedMessage_Pay = hostUrl + "rest/chat/send";
    //获取我的用户礼物列表
    public static String Url_Get_MyGiftList = hostUrl + "rest/gift/userList";
    //获取礼物清单
    public static String Url_Get_GiftDetailedList = hostUrl + "rest/gift/userList/";
    //获取推荐魔友
    public static String Url_Get_RecommendFriends = hostUrl + "rest/user/recommenduser";
    //获取魅力排行
    public static String Url_Get_Char_Ranking = hostUrl + "rest/user/charmest/";
    //获取财富排行
    public static String Url_Get_Gold_Ranking = hostUrl + "rest/user/goldcoinest/";
    //获取夜友数量
    public static String Url_Get_FansNum = hostUrl + "rest/user/getMyFansNum";
    //获取我的粉丝
    public static String Url_Get_FansList = hostUrl + "rest/user/followed/";
    //获取黑名单
    public static String Url_Get_BlackList = hostUrl + "rest/corruption/blacklist/";
    //从黑名单移除
    public static String Url_Cancel_BlackList = hostUrl + "rest/corruption/cancel/";
    //获取我的关注
    public static String Url_Get_MyConcernList = hostUrl + "rest/user/follow/";
    //取消我的关注
    public static String Url_Cancel_MyConcernList = hostUrl + "rest/user/followCancel/";
    //关注
    public static String Url_Add_Follow = hostUrl + "rest/user/followAdd";
    //商城数据获取
    public static String Url_Get_Mall = hostUrl + "rest/category/club";
    //金币商城商品
    public static String Url_Get_GoldMall = hostUrl + "rest/mall/goods/1/";
    //充值－－增加订单－－金币
    public static String Url_Recharge_Add_Order_Gold = hostUrl + "rest/mall/pay/";
    //判断是否被对方拉黑
    public static String Url_IsTaBalck = hostUrl + "rest/corruption/isBlack/";
    //获取直播列表
    public static String Url_GetLiveList = hostUrl + "rest/live/list/";
    //获取我的直播数据
    public static String Url_GetMyLiveInfo = hostUrl + "rest/live/myroom";
    //更新直播信息
    public static String Url_UpData_MyLiveInfo = hostUrl + "rest/live/update";
    //访客进入直播间
    public static String Url_Live_Enter = hostUrl + "rest/live/enter/";
    //访客进入直播间 人流量统计
    public static String Url_Live_UserLog = hostUrl + "rest/live/userlog/";
    //更新直播状态
    public static String Url_Live_Status = hostUrl + "rest/live/updateStatus/";
    //收听直播付钱
    public static String Url_Live_Money = hostUrl + "rest/live/money/";
    //聊天室在线人数
    public static String Url_Live_Num = hostUrl + "rest/live/";
    //添加创建聊天室
    public static String Url_Add_Live = hostUrl + "rest/live/add";
    //发现新接口 type: 0-推荐、1-人气、2-新人、3-声音控、4-男神
    public static String Url_Find_New = hostUrl + "rest/user/find/";
    //获取场景信息
    public static String Url_Get_SceneInfo = hostUrl + "rest/chat/background/";
    //获取社区（动态，语音，视频）第一页数据 /rest/blog/index/{category}
    public static String Url_Get_Community_First = hostUrl + "rest/blog/index/";
    //获取社区-动态,page从2开始 rest/blog/list/allBlogs/{page}
    public static String Url_Get_Community_Dynamic = hostUrl + "rest/blog/list/allBlogs/";
    //获取社区-语音,page从2开始 rest/blog/list/audios/{page}
    public static String Url_Get_Community_Audio = hostUrl + "rest/blog/list/audios/";
    //获取社区-视频,page从2开始 rest/blog/list/videos/{page}
    public static String Url_Get_Community_Video = hostUrl + "rest/blog/list/videos/";
    //获取提现帐号列表 rest/wallet/list
    public static String Url_Get_Withdrawals_List = hostUrl + "rest/wallet/list";
    //添加提现帐号列表 rest/wallet/add
    public static String Url_Add_Withdrawals_Account = hostUrl + "rest/wallet/add";
    //获取兑换记录 rest/user/chargerecord/1
    public static String Url_Get_Exchange_Record_List = hostUrl + "rest/user/chargerecord/";
    //获取提现记录 rest/user/cashrecord/1
    public static String Url_Get_Cash_Record_List = hostUrl + "rest/user/cashrecord/";
    //修改提现账号 rest/wallet/update/{wallet_channel_id}
    public static String Url_Modify_Account_List = hostUrl + "rest/wallet/update/";
    //钻石兑换金币 rest/user/charge/{diamond}
    public static String Url_DiamondsToCoin = hostUrl + "rest/user/charge";
    //设置支付密码发送短信验证码 rest/wallet/message/{mobile}/{type}  type（1：设置密码 2：忘记重置密码）
    public static String Url_SeedPayMessage = hostUrl + "rest/wallet/message/";
    //设置支付密码 rest/wallet/setPayPassword
    public static String Url_SetPay_PW = hostUrl + "rest/wallet/setPayPassword";
    //忘记密码设置支付密码 rest/wallet/forgotAndChangePwd
    public static String Url_SetPay_PW_Forgot = hostUrl + "rest/wallet/forgotAndChangePwd";
    //修改支付密码 rest/wallet/password
    public static String Url_ResetPay_PW = hostUrl + "rest/wallet/password";
    //提现 rest/user/cash
    public static String Url_Cash_PW = hostUrl + "rest/user/cash";
    //添加魔聊背景 rest/chat/background/add
    public static String Url_Add_Chat_Background = hostUrl + "rest/chat/background/add";
    //帖子打赏 rest/gift/buytoblog/{gift_id}/{blog_id}
    public static String Url_Reward = hostUrl + "rest/gift/buytoblog/";
    //获取举报列表 rest/corruption/reason/{type} 备注：type（1：针对人 2：针对帖子 ； is_other（1：表示为 其他 ）
    public static String Url_Get_Report_List = hostUrl + "rest/corruption/reason/";
    //获取获得打赏列表 rest/gift/giver/185973/1
    public static String Url_Get_Reward_List = hostUrl + "rest/gift/giver/";
    //创建图文帖 rest/blog/addImageText
    public static String Url_Create_ImageText_Post = hostUrl + "rest/blog/addImageText";
    //获取商城一级分类
    public static String Url_Get_One_Category = hostUrl + "rest/category/list";
    //获取热搜关键字
    public static String Url_Get_Search_Keyword = hostUrl + "rest/goods/searchKeyword";
    //获取商城二级分类
    public static String Url_Get_Two_Category = hostUrl + "rest/category/leaflist/";
    //关键字搜索
    public static String Url_Search = hostUrl + "rest/goods/search?keywords=";
    //创建语音帖 rest/blog/addAudio
    public static String Url_Create_Audio_Post = hostUrl + "rest/blog/addAudio";
    //热榜 rest/blog/hot/{page}
    public static String Url_Get_Hot_Post = hostUrl + "rest/blog/hot/";
    //获取弹幕 rest/blog/commentsList/{blog_id}
    public static String Url_Get_Danmaku = hostUrl + "rest/blog/commentsList/";
    //创建视频帖 rest/blog/addVideo
    public static String Url_Create_Video_Post = hostUrl + "rest/blog/addVideo";
    //直播详情 rest/live/148
    public static String Url_Live_Detail_Post = hostUrl + "rest/live/";
    //定时获取kiss和钱 rest/live/like/450000534/0
    public static String Url_Get_Like_Post = hostUrl + "rest/live/like/";
    //获取关注我的人 rest/user/followme
    public static String Url_Get_FollowMe_Post = hostUrl + "rest/user/followme";
    //邀请好友 rest/live/invite
    public static String Url_Invite_Friends_Post = hostUrl + "rest/live/invite";
    /**************************http访问路径*****************************/



    /**************************http访问回应识别码*****************************/
    //登录
    public static int NET_LOGIN_ID = 1;
    //发送验证码
    public static int NET_SMS_ID = 2;
    //注册
    public static int NET_REGISTER_ID = 3;
    //忘记密码
    public static int NET_NEWPW = 4;
    //获取用户信息
    public static int NET_GETUSERINFO = 5;
    //修改密码
    public static int NET_RESET_PW = 6;
    //修改昵称
    public static int NET_RESET_NICK = 7;
    //修改头像
    public static int NET_RESET_HEAD = 8;
    //退出
    public static int NET_LOGOUT = 9;
    //任务列表
    public static int NET_GET_RASK = 10;
    //获取任务奖励
    public static int NET_REWARDED = 11;
    //完成任务
    public static int NET_FINISH_TASK = 12;
    //修改头像
    public static int NET_MODIFY_HEAD = 13;
    //充值－－增加订单
    public static int NET_RECHARGE_ADD_ORDER = 14;
    //充值－－增加订单－－支付宝
    public static int NET_RECHARGE_ADD_ORDER_ALIPAY = 15;
    //充值－－增加订单－－微信
    public static int NET_RECHARGE_ADD_ORDER_WEIXIN = 16;
    //轮播图获取
    public static int NET_AD_LIST = 17;
    //获取俱乐部分类
    public static int NET_CATEGORY_LIST = 18;
    //获取俱乐部二级分类
    public static int NET_TWO_CATEGORY_LIST = 19;
    //获取分类下商品
    public static int NET_GET_CATEGORY_LIST = 20;
    //获取商品详情
    public static int NET_GET_CATEGORY_DETAIL = 21;
    //获取商品评论
    public static int NET_GET_GOODS_COMMENT = 22;
    //获取用户收货地址
    public static int NET_GET_ADDRESS_LIST = 23;
    //添加订单
    public static int NET_ADD_ORDER = 24;
    //获取省数据
    public static int NET_GET_PROVINCE = 25;
    //获取市数据
    public static int NET_GET_CITY = 26;
    //获取县数据
    public static int NET_GET_DISTRICT = 27;
    //添加新地址
    public static int NET_ADD_ADDRESS = 28;
    //删除地址
    public static int NET_DELETE_ADDRESS = 29;
    //获取我的订单
    public static int NET_GET_ORDERLIST = 30;
    //获取订单详情
    public static int NET_GET_ORDERDETAIL = 31;
    //获取公用配置信息
    public static int NET_GET_GOLDENROLE = 32;
    //检查版本更新
    public static int NET_GET_VERSION = 33;
    //获取社区模块分类
    public static int NET_GET_BLOGBOARD = 34;
    //获取论坛帖子列表
    public static int NET_GET_BLOGLIST = 35;
    //获取帖子详情
    public static int NET_GET_BLOGDETAIL = 36;
    //获取帖子评论
    public static int NET_GET_BLOGDETAIL_COMMENTLIST = 37;
    //收藏帖子
    public static int NET_GET_BLOGCOLLECTION = 38;
    //取消收藏帖子
    public static int NET_GET_BLOGCOLLECTION_NOT = 39;
    //举报
    public static int NET_REPORT = 40;
    //增加评论
    public static int NET_ADD_COMMENT = 41;
    //评论点赞
    public static int NET_ADD_LIKE = 42;
    //拉黑
    public static int NET_ADD_BLACKLIST = 43;
    //帖子评论的评论列表
    public static int NET_SUBCOMMENT_LIST = 44;
    //发帖
    public static int NET_ADD_POST = 45;
    //添加打赏
    public static int NET_ADD_AWARD = 46;
    //获取打赏列表
    public static int NET_GET_AWARDLIST = 47;
    //获取我的收藏
    public static int NET_GET_MYCOLLECTION = 48;
    //获取我的秘密
    public static int NET_GET_MYSECRET = 49;
    //获取我的评论或评论我的
    public static int NET_GET_USERCOMMENT = 50;
    //添加帖子浏览量
    public static int NET_ADD_BROWSE = 51;
    //删除帖子
    public static int NET_DELETE_POST = 52;
    //性别认证
    public static int NET_VERIFY = 53;
    //性别认证照相
    public static int NET_RESET_HEAD_TWO = 54;
    //获取聊天token
    public static int NET_GET_CHATTOKEN = 55;
    //获取好友列表
    public static int NET_GET_FANS = 56;
    //获取用户基本信息
    public static int NET_GET_USERINDEX = 57;
    //获取访客信息
    public static int NET_GET_BROWSE = 58;
    //获取守护者信息
    public static int NET_GET_GUARD = 59;
    //加好友
    public static int NET_ADD_FRIEND = 60;
    //删除好友
    public static int NET_DLELTE_FRIEND = 61;
    //获取物流URL
    public static int NET_GET_LOGISTICS = 62;
    //获取礼物列表
    public static int NET_GET_GIFTLIST = 63;
    //购买礼物
    public static int NET_PAY_GIFTLIST = 64;
    //发消息扣金币
    public static int NET_SEEDMESSAGE_PAY = 65;
    //获取我的用户礼物列表
    public static int NET_GET_MYGIFTLIST = 66;
    //获取礼物清单
    public static int NET_GET_GIFTDETAILEDLIST = 67;
    //获取推荐魔友
    public static int NET_GET_RECOMMENDFRIENDS = 68;
    //获取魅力排行
    public static int NET_GET_CHAR_RANKING_ID = 69;
    //获取财富排行
    public static int NET_GET_GOLD_RANKING_ID = 70;
    //喜欢的人
    public static int NET_GET_FINDLIKE_ID = 71;
    //排行榜
    public static int NET_GET_RANKING_ID = 72;
    //获取夜友数量
    public static int NET_GET_FANSNUM_ID = 73;
    //获取粉丝列表
    public static int NET_GET_FANSLIST_ID = 74;
    //获取黑名单
    public static int NET_GET_BLACKLIST_ID = 75;
    //从黑名单移除
    public static int NET_CANCEL_BLACKLIST_ID = 76;
    //获取我的关注
    public static int NET_Get_MYCONCERNLIST_ID = 77;
    //取消我的关注
    public static int NET_CANCEL_MYCONCERNLIST_ID = 78;
    //关注
    public static int NET_ADD_FOLLOW_ID = 79;
    //商城数据获取
    public static int NET_GET_MALL_ID = 80;
    //金币商城商品
    public static int NET_GET_GOLDMALL_ID = 81;
    //充值－－增加订单－－金币
    public static int NET_RECHARGE_ADD_ORDER_GOLD_ID = 82;
    //判断是否被对方拉黑
    public static int NET_ISTABALCK_ID = 83;
    //获取直播列表
    public static int NET_GETLIVELIST_ID = 84;
    //获取我的直播数据
    public static int NET_GETMYLIVEINFO_ID = 85;
    //更新直播信息
    public static int NET_UPDATA_MYLIVEINFO_ID = 86;
    //访客进入直播间
    public static int NET_LIVE_ENTER_ID = 87;
    //访客进入直播间 人流量统计
    public static int NET_LIVE_USERLOG_ID = 88;
    //更新直播状态
    public static int NET_LIVE_STATUS_ID = 89;
    //收听直播付钱
    public static int NET_LIVE_MONEY_ID = 90;
    //聊天室在线人数
    public static int NET_LIVE_NUM_ID = 91;
    //添加创建聊天室
    public static int NET_ADD_LIVE_ID = 92;
    //发现新接口
    public static int NET_FIND_NEW_ID = 93;
    //获取场景信息
    public static int NET_GET_SCENEINFO_ID = 94;
    //获取社区（动态，语音，视频）第一页数据
    public static int NET_GET_COMMUNITY_FIRST_ID = 95;
    //获取社区-动态,page从2开始
    public static int NET_GET_COMMUNITY_DYNAMIC_ID = 96;
    //获取社区-语音,page从2开始
    public static int NET_GET_COMMUNITY_AUDIO_ID = 97;
    //获取社区-视频,page从2开始
    public static int NET_GET_COMMUNITY_VIDEO_ID = 98;
    //获取支付帐号列表
    public static int NET_GET_WITHDRAEALS_LIST_ID = 99;
    //添加支付帐号
    public static int NET_ADD_WITHDRAEALS_ACCOUNT_ID = 100;
    //获取兑换记录
    public static int NET_GET_EXCHANGE_RECORD_ID = 101;
    //获取提现记录
    public static int NET_GET_CASH_RECORD_ID = 102;
    //修改提现账号
    public static int NET_MODIFY_ACCOUNT_ID = 103;
    //钻石兑换金币
    public static int NET_DIAMONDS_TO_COIN_ID = 104;
    //设置支付密码发送短信验证码
    public static int NET_SEED_PAY_MEESAGE_ID = 105;
    //设置支付密码
    public static int NET_SET_PAY_PW_ID = 106;
    //忘记密码设置支付密码
    public static int NET_SET_PAY_PW_FOEGOT_ID = 107;
    //修改支付密码
    public static int NET_RESET_PAY_PW_ID = 108;
    //提现
    public static int NET_CASH_ID = 109;
    //添加魔聊背景
    public static int NET_ADD_CHAT_BACKGROUNG_ID = 110;
    //帖子打赏
    public static int NET_REWARD_ID = 111;
    //获取举报列表
    public static int NET_GET_REPORT_LIST_ID = 112;
    //获取获得打赏列表
    public static int NET_GET_REPWARD_LIST_ID = 113;
    //创建图文帖子
    public static int NET_CREATE_IAMGETEXT_POST_ID = 114;
    //获取商城一级分类
    public static int NET_GET_CATEGOYP_ONE_ID = 115;
    //获取热搜关键字
    public static int NET_GET_SEARCH_KEYWORD_ID = 116;
    //获取商城二级分类
    public static int NET_GET_CATEGOYP_TWO_ID = 117;
    //关键字搜索
    public static int NET_SEARCH_ID = 118;
    //上传语音文件
    public static int NET_UP_VOICE_ID = 119;
    //创建语音帖
    public static int NET_CREATE_AUDIO_POST_ID = 120;
    //获取弹幕
    public static int NET_GET_DANMAKU_ID = 121;
    //上传视频文件
    public static int NET_UP_VIDEO_ID = 122;
    //创建视频帖
    public static int NET_CREATE_VIDEO_POST_ID = 123;
    //直播详情
    public static int NET_LIVE_DETAIL_ID = 124;
    //定时获取kiss和钱
    public static int NET_GET_LIVE_LIKE_ID = 125;
    //获取关注我的人
    public static int NET_GET_FOLLOWME_ID = 126;
    //邀请好友
    public static int NET_INVITE_FRIENDS_ID = 127;
    /**************************http访问回应识别码*****************************/



    /**************************跳转码*****************************/
    public static int toResetPW = 1000;
    public static int toRegister = 1001;
    public static int toResetNICK = 1002;
    public static int toMyInfo = 1003;
    public static int toLogin = 1004;
    public static int toCreateGesrure = 1005;
    public static int toLoginCreateGesrure = 1006;
    public static int toSettingGesrure = 1007;
    public static int toManageAddress = 1008;
    public static int toAddAddress = 1009;
    public static int toOrderDetail = 1010;
    public static int toPostDetail = 1011;
    public static int toSelectGift = 1012;
    public static int toOtherPersonal = 1013;
    public static int fromCreateImageText = 1014;
    /**************************跳转码*****************************/
}
