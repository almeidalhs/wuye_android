package com.atman.wysq.utils;

import com.atman.wysq.model.request.SeedMessageModel;
import com.atman.wysq.model.response.GetMessageModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.yunxin.model.ContentTypeInter;
import com.base.baselibs.util.LogUtils;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangbingliang on 17/5/8.
 */

public class SeedActionMessageUtils {

    public static void seed(String id){
        IMMessage messagetext = MessageBuilder.createTextMessage(id, SessionTypeEnum.P2P
                , "我已经关注你啦！有没有空和我聊聊天呢？");
        SeedMessageModel mSeedMessageModel = new SeedMessageModel(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex()
                , MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                , MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status()
                , MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getNickName());
        GetMessageModel mGetMessageModel = new GetMessageModel(ContentTypeInter.contentTypeText
                , 0, "", mSeedMessageModel);
        Map<String, Object> mMap = new HashMap<>();
        mMap.put("extra",new Gson().toJson(mGetMessageModel));
        messagetext.setRemoteExtension(mMap);
        CustomMessageConfig config = new CustomMessageConfig();
        config.enableRoaming  = false; // 该消息不漫游
        messagetext.setConfig(config);
        NIMClient.getService(MsgService.class).sendMessage(messagetext, false);
    }

    public static void seedGift(String id, String title, String giftName){
        LogUtils.e(">>>>id:"+id+",title:"+title+",giftName:"+giftName);
        IMMessage messagetext = MessageBuilder.createTextMessage(id, SessionTypeEnum.P2P
                , "我很喜欢你发的贴子"+title+"送给你一个"+giftName+"当礼物希望你能喜欢");
        SeedMessageModel mSeedMessageModel = new SeedMessageModel(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex()
                , MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                , MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status()
                , MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getNickName());
        GetMessageModel mGetMessageModel = new GetMessageModel(ContentTypeInter.contentTypeText
                , 0, "", mSeedMessageModel);
        Map<String, Object> mMap = new HashMap<>();
        mMap.put("extra",new Gson().toJson(mGetMessageModel));
        messagetext.setRemoteExtension(mMap);
        CustomMessageConfig config = new CustomMessageConfig();
        config.enableRoaming  = false; // 该消息不漫游
        messagetext.setConfig(config);
        NIMClient.getService(MsgService.class).sendMessage(messagetext, false);
    }
}
