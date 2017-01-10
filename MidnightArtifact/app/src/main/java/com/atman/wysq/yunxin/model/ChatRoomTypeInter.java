package com.atman.wysq.yunxin.model;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/2 11:39
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public interface ChatRoomTypeInter {
    int ChatRoomTypeText = 0;//文本
    int ChatRoomTypeImage = 1;//图片
    int ChatRoomTypeAudio = 2;//语音
    int ChatRoomTypeSystem = 3;//系统消息，只展示content
    int ChatRoomTypeSystemCMD = 4;//系统消息 透传消息 不展示
}
