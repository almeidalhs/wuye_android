package com.atman.wysq.ui.receiver;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.os.Vibrator;
import android.text.TextUtils;
import android.widget.Toast;

import com.atman.wysq.model.bean.ImMessage;
import com.atman.wysq.model.bean.ImSession;
import com.atman.wysq.model.bean.TouChuanOtherNotice;
import com.atman.wysq.model.event.GiftEvent;
import com.atman.wysq.model.event.YunXinAddFriendEvent;
import com.atman.wysq.model.event.YunXinMessageEvent;
import com.atman.wysq.model.greendao.gen.ImSessionDao;
import com.atman.wysq.model.response.ChatAudioModel;
import com.atman.wysq.model.response.GetMessageModel;
import com.atman.wysq.model.response.GiftMessageModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.widget.ResidentNotificationHelper;
import com.atman.wysq.yunxin.model.ContentTypeInter;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by tangbingliang on 16/12/12.
 */

public class RootMessageObserver implements Observer<List<IMMessage>> {

    private Context context;

    private String nick = "";
    private String sex = "F";
    private String icon = "";
    String verify_status = "0";
    private long userId;
    private String content;

    public RootMessageObserver(Context context){
        this.context = context;
    }
    
    @Override
    public void onEvent(List<IMMessage> messages) {
        nick = "";
        sex = "F";
        icon = "";
        verify_status = "0";
        userId = 0;
        content = "";

        // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
        LogUtils.e("messages.size():"+messages.size());
        for (int i=0;i<messages.size();i++) {
            try {
                LogUtils.e("messages.get(i).getContent():"+messages.get(i).getContent());
                GiftMessageModel mGiftMessageModel = new Gson().fromJson(messages.get(i).getContent(), GiftMessageModel.class);
                userId = System.currentTimeMillis();
                if (mGiftMessageModel!=null) {
                    if (mGiftMessageModel.getAddvalue()!=null && mGiftMessageModel.getAddvalue().size()>0) {
                        for (int x=0;x<mGiftMessageModel.getAddvalue().size();x++) {
                            if (!mGiftMessageModel.getAddvalue().get(x).contains("解锁")) {
                                if (x!=0) {
                                    content += ",";
                                }
                                content += mGiftMessageModel.getAddvalue().get(x);
                            }
                        }
                        if (!content.contains("您发送了一条私信")) {
                            LogUtils.e(">>>>content:"+content);
                            if (MyBaseApplication.getApplication().mLiveStatue.equals("2") && mGiftMessageModel.getType()==1) {
                                if (content.contains(",")) {
                                    if (isAppOnFreground(context)
                                            && !isApplicationBroughtToBackgroundByTask(context)) {//不在后台
                                        Toast.makeText(context, content.split(",")[1], Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //直播间处理
                            switch (mGiftMessageModel.getType()) {
                                case 11:
                                    EventBus.getDefault().post(new GiftEvent(mGiftMessageModel.getType()
                                            ,0, mGiftMessageModel.getAdd_money()));
                                    break;
                                case 1:
                                    EventBus.getDefault().post(new GiftEvent(mGiftMessageModel.getType()
                                            , mGiftMessageModel.getGiftId(), 0));
                                    break;
                                default:
                                    if (content.contains("您在直播间消耗")) {
                                        content = content.replace("您在直播间消耗","-");
                                    }
                                    if (isAppOnFreground(context)
                                            && !isApplicationBroughtToBackgroundByTask(context)) {//不在后台
                                        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                            }
                        }
                        return;
                    } else {
                        content = mGiftMessageModel.getCenter_user_name() +":" + mGiftMessageModel.getCenter_content();
                    }
                }
                if (content.contains("我正在直播中")){
                    return;
                }
                nick = "午夜神器";
                if (mGiftMessageModel!=null && mGiftMessageModel.getType()==1) {//礼物通知
                    if (mGiftMessageModel.getCenter_user_name()==null || String.valueOf(mGiftMessageModel.getCenter_user_id())
                            .equals(PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID))) {
                        continue;
                    }
                    TouChuanOtherNotice mTouChuanOtherNotice = new TouChuanOtherNotice();
                    mTouChuanOtherNotice.setReceive_userId(Long.valueOf(PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID)));
                    mTouChuanOtherNotice.setSend_userId(mGiftMessageModel.getCenter_user_id());
                    mTouChuanOtherNotice.setSend_nickName(mGiftMessageModel.getCenter_user_name());
                    mTouChuanOtherNotice.setGiftMessage(mGiftMessageModel.getCenter_content());
                    mTouChuanOtherNotice.setTime(String.valueOf(System.currentTimeMillis()));
                    mTouChuanOtherNotice.setNoticeType(8);
                    MyBaseApplication.getApplication().getDaoSession().getTouChuanOtherNoticeDao().insert(mTouChuanOtherNotice);
                } else if (mGiftMessageModel!=null && mGiftMessageModel.getType()==4) {//帖子回复
                    if (mGiftMessageModel.getCenter_user_name()==null || String.valueOf(mGiftMessageModel.getCenter_user_id())
                            .equals(PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID))) {
                        continue;
                    }
                    TouChuanOtherNotice mTouChuanOtherNotice = new TouChuanOtherNotice();
                    mTouChuanOtherNotice.setReceive_userId(Long.valueOf(PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID)));
                    mTouChuanOtherNotice.setSend_userId(mGiftMessageModel.getCenter_user_id());
                    mTouChuanOtherNotice.setSend_nickName(mGiftMessageModel.getCenter_user_name());
                    mTouChuanOtherNotice.setGiftMessage(mGiftMessageModel.getCenter_content());
                    mTouChuanOtherNotice.setTime(String.valueOf(System.currentTimeMillis()));
                    mTouChuanOtherNotice.setNoticeType(4);
                    MyBaseApplication.getApplication().getDaoSession().getTouChuanOtherNoticeDao().insert(mTouChuanOtherNotice);
                } else if (mGiftMessageModel!=null && mGiftMessageModel.getType()==3) {//注册打招呼
                    if (mGiftMessageModel.getCenter_user_name()==null || String.valueOf(mGiftMessageModel.getCenter_user_id())
                            .equals(PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID))) {
                        continue;
                    }
                    TouChuanOtherNotice mTouChuanOtherNotice = new TouChuanOtherNotice();
                    mTouChuanOtherNotice.setReceive_userId(Long.valueOf(PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID)));
                    mTouChuanOtherNotice.setSend_userId(mGiftMessageModel.getCenter_user_id());
                    mTouChuanOtherNotice.setSend_nickName(mGiftMessageModel.getCenter_user_name());
                    mTouChuanOtherNotice.setGiftMessage(mGiftMessageModel.getCenter_content());
                    mTouChuanOtherNotice.setTime(String.valueOf(System.currentTimeMillis()));
                    mTouChuanOtherNotice.setNoticeType(3);
                    MyBaseApplication.getApplication().getDaoSession().getTouChuanOtherNoticeDao().insert(mTouChuanOtherNotice);
                }  else if (mGiftMessageModel!=null && mGiftMessageModel.getType()==20030) {//踢下线
                    MyBaseApplication.getApplication().logout();
                }
                EventBus.getDefault().post(new YunXinAddFriendEvent());
            } catch (JsonSyntaxException e){
                LogUtils.e("e:"+e.toString());
            }

            if (messages.get(i).getRemoteExtension()!=null && messages.get(i).getRemoteExtension().get("extra")!=null) {
                ImMessage temp = null;
                boolean isMy = false;
                if (messages.get(i).getFromAccount().equals(PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID))) {
                    isMy = true;
                }
                String data = new Gson().toJson(messages.get(i).getRemoteExtension().get("extra"));
                data = data.replace("\\","").replace("u003d",":").replace("u0027","\"").replace("\"{","{").replace("}\"","}")
                        .replace("\"contentFinger\":\"\"","\"contentFinger\":0").replace("\"fingerValue\":\"\"","\"fingerValue\":0");
                GetMessageModel mGetMessageModel = null;
                LogUtils.e("data:"+data);
                try {
                    mGetMessageModel = new Gson().fromJson(data, GetMessageModel.class);
                } catch (JsonSyntaxException e){
                    LogUtils.e(">>>>>e:"+e.toString());
                }
                if (mGetMessageModel == null) {
                    continue;
                }
                int messageType = mGetMessageModel.getContentType();
                userId = Long.parseLong(messages.get(i).getSessionId());
                if (messageType == ContentTypeInter.contentTypeText) {
                    temp = new ImMessage(null, messages.get(i).getUuid()
                            , PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID), messages.get(i).getSessionId()
                            , messages.get(i).getFromAccount(), mGetMessageModel.getSendUser().getNickName()
                            , mGetMessageModel.getSendUser().getIcon(), mGetMessageModel.getSendUser().getSex()
                            , mGetMessageModel.getSendUser().getVerify_status()
                            , isMy, messages.get(i).getTime(), messageType
                            , messages.get(i).getContent(), "", "", "", "", "", "", "", "", 0, 0, false, 1);
                } else if (messageType == ContentTypeInter.contentTypeImage) {
                    temp = new ImMessage(null, messages.get(i).getUuid()
                            , PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID), messages.get(i).getSessionId()
                            , messages.get(i).getFromAccount(), mGetMessageModel.getSendUser().getNickName()
                            , mGetMessageModel.getSendUser().getIcon(), mGetMessageModel.getSendUser().getSex()
                            , mGetMessageModel.getSendUser().getVerify_status()
                            , isMy, messages.get(i).getTime(), messageType
                            , "[图片]", ((FileAttachment)messages.get(i).getAttachment()).getPathForSave()
                            , ((FileAttachment)messages.get(i).getAttachment()).getUrl()
                            , ((FileAttachment)messages.get(i).getAttachment()).getThumbPathForSave(), "", "", "", "", "", 0, 0, false, 1);
                    if (isOriginImageHasDownloaded(messages.get(i))) {
                        AbortableFuture future = NIMClient.getService(MsgService.class).downloadAttachment(messages.get(i), true);
                        future.setCallback(callback);
                    }
                } else if (messageType == ContentTypeInter.contentTypeFinger) {
                    String contentFinger = String.valueOf(mGetMessageModel.getContentFinger());
                    int fingerValue = 1;
                    String str = "[石头]";
                    if (contentFinger.equals("2")) {
                        str = "[剪刀]";
                        fingerValue = 2;
                    } else if (contentFinger.equals("3")) {
                        str = "[布]";
                        fingerValue = 3;
                    }
                    temp = new ImMessage(null, messages.get(i).getUuid()
                            , PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID), messages.get(i).getSessionId()
                            , messages.get(i).getFromAccount(), mGetMessageModel.getSendUser().getNickName()
                            , mGetMessageModel.getSendUser().getIcon(), mGetMessageModel.getSendUser().getSex()
                            , mGetMessageModel.getSendUser().getVerify_status()
                            , isMy, messages.get(i).getTime(), messageType
                            , str, "", "", "", "", "", "", "", "", 0, fingerValue, false, 1);
                } else if (messageType == ContentTypeInter.contentTypeAudio) {
                    ChatAudioModel mChatAudioModel = new Gson().fromJson(messages.get(i).getAttachment().toJson(true), ChatAudioModel.class);
                    temp = new ImMessage(null, messages.get(i).getUuid()
                            , PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID), messages.get(i).getSessionId()
                            , messages.get(i).getFromAccount(), mGetMessageModel.getSendUser().getNickName()
                            , mGetMessageModel.getSendUser().getIcon(), mGetMessageModel.getSendUser().getSex()
                            , mGetMessageModel.getSendUser().getVerify_status()
                            , isMy, messages.get(i).getTime(), messageType
                            , "[语音]", "", "", "", "", "", "",
                            ((FileAttachment)messages.get(i).getAttachment()).getPathForSave()
                            , ((FileAttachment)messages.get(i).getAttachment()).getUrl(), mChatAudioModel.getDur(), 0, false, 1);
                } else if (messageType == ContentTypeInter.contentTypeImageSmall) {
                    String url = "";
                    if (mGetMessageModel.getContentImageSUrl().contains("http:")) {
                        url = mGetMessageModel.getContentImageSUrl();
                    } else {
                        url = ((FileAttachment)messages.get(i).getAttachment()).getUrl();
                    }
                    temp = new ImMessage(null, messages.get(i).getUuid()
                            , PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID), messages.get(i).getSessionId()
                            , messages.get(i).getFromAccount(), mGetMessageModel.getSendUser().getNickName()
                            , mGetMessageModel.getSendUser().getIcon(), mGetMessageModel.getSendUser().getSex()
                            , mGetMessageModel.getSendUser().getVerify_status()
                            , isMy, messages.get(i).getTime(), messageType
                            , "[图片]", "", "", "", url
                            , url
                            , url, "", "", 0, 0, false, 1);
                    if (isOriginImageHasDownloaded(messages.get(i))) {
                        AbortableFuture future = NIMClient.getService(MsgService.class).downloadAttachment(messages.get(i), true);
                        future.setCallback(callback);
                    }
                }
                content = temp.getContent();
                if (!messages.get(i).getSessionId().equals(PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID))) {
                    MyBaseApplication.getApplication().getDaoSession().getImMessageDao().insertOrReplace(temp);

                    ImSession mImSession = MyBaseApplication.getApplication().getDaoSession().getImSessionDao().queryBuilder().where(ImSessionDao.Properties.UserId.eq(messages.get(i).getSessionId())
                            , ImSessionDao.Properties.LoginUserId.eq(PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID))).build().unique();
                    if (!messages.get(i).getFromAccount().equals(String.valueOf(
                            PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID)))) {
                        nick = mGetMessageModel.getSendUser().getNickName();
                        sex = mGetMessageModel.getSendUser().getSex();
                        icon = mGetMessageModel.getSendUser().getIcon();
                        verify_status = String.valueOf(mGetMessageModel.getSendUser().getVerify_status());
                    }
                    if (mImSession==null) {
                        ImSession mImSessionTemp = new ImSession(messages.get(i).getSessionId()
                                , PreferenceUtil.getPreferences(context, PreferenceUtil.PARM_USERID), temp.getContent()
                                , nick, icon, sex, Integer.parseInt(verify_status), System.currentTimeMillis(), 1);
                        MyBaseApplication.getApplication().getDaoSession().getImSessionDao().insertOrReplace(mImSessionTemp);
                    } else {
                        mImSession.setContent(temp.getContent());
                        mImSession.setNickName(nick);
                        mImSession.setSex(sex);
                        mImSession.setIcon(icon);
                        mImSession.setVerify_status(Integer.parseInt(verify_status));
                        mImSession.setTime(System.currentTimeMillis());
                        mImSession.setUnreadNum(mImSession.getUnreadNum()+1);
                        MyBaseApplication.getApplication().getDaoSession().getImSessionDao().update(mImSession);
                    }
                }
            }
        }
        EventBus.getDefault().post(new YunXinMessageEvent());

        if (icon.isEmpty()) {
            showNotification(null);
        } else {
            new AsyncTask<String, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(String... params) {
                    try {
                        URL url = new URL(params[0]);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(6000);//设置超时
                        conn.setDoInput(true);
                        conn.setUseCaches(false);//不缓存
                        conn.connect();
                        int code = conn.getResponseCode();
                        Bitmap bitmap = null;
                        if(code==200) {
                            InputStream is = conn.getInputStream();//获得图片的数据流
                            bitmap = BitmapFactory.decodeStream(is);
                        }
                        return bitmap;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Bitmap result) {
                    super.onPostExecute(result);
                    if (result != null) {
                        showNotification(result);
                    }
                }
            }.execute(Common.ImageUrl+icon);
        }
    }

    private void showNotification(Bitmap result) {
        LogUtils.e("content:"+content);
        LogUtils.e("nick:"+nick);
        if (content.isEmpty() || content.equals("") || content.contains("null")
                || content.contains("我正在直播中") || content.contains("您发送了一条私信")) {
            return;
        }
        if (isScreenOn(context)) {//屏幕亮的
            if (!isAppOnFreground(context)
                    || isApplicationBroughtToBackgroundByTask(context)) {//在后台
                ResidentNotificationHelper.sendResidentNoticeType0(context
                        , System.currentTimeMillis(), content, userId, nick, result);
            } else {//不在后台
                /** 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到**/
                if (userId != Long.parseLong(MyBaseApplication.mLocationID)) {
                    Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启
                    vibrator.vibrate(pattern,-1);           //重复两次上面的pattern 如果只想震动一次，index设为-1
                }
            }
        } else {//屏幕不亮
            ResidentNotificationHelper.sendResidentNoticeType0(context
                    , System.currentTimeMillis(), content, userId, nick, result);
        }
    }

    public boolean isOriginImageHasDownloaded(final IMMessage message) {
        if (message.getAttachStatus() == AttachStatusEnum.transferred &&
                !TextUtils.isEmpty(((FileAttachment) message.getAttachment()).getPath())) {
            return true;
        }
        return false;
    }

    private RequestCallback<List<IMMessage>> callback = new RequestCallback<List<IMMessage>>() {
        @Override
        public void onSuccess(List<IMMessage> imMessages) {
            if (imMessages!=null) {
            } else {
            }
        }

        @Override
        public void onFailed(int i) {

        }

        @Override
        public void onException(Throwable throwable) {

        }
    };

    public boolean isScreenOn(Context context){
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
        if (!pm.isScreenOn()) {
            //点亮屏幕
            wl.acquire();
        }
        return pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
    }

    /**
     * 是否在后台
     * @return false 在后台; true 在前台
     * @return
     */
    public boolean isAppOnFreground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String curPackageName = MyBaseApplication.getApplication().getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> app = am.getRunningAppProcesses();
        if (app == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo a : app) {
            if (a.processName.equals(curPackageName) &&
                    a.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前应用程序是否处于后台，通过getRunningTasks的方式
     * @return true 在后台; false 在前台
     */
    public  boolean isApplicationBroughtToBackgroundByTask(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals("com.atman.wysq")) {
                return true;
            }
        }
        return false;
    }
}
