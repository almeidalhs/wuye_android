package com.atman.wysq.ui.discover;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.ChatRoomAdapter;
import com.atman.wysq.model.bean.ImMessage;
import com.atman.wysq.model.response.ChatRoomMessageModel;
import com.atman.wysq.model.response.GetMyUserIndexModel;
import com.atman.wysq.model.response.MyLiveInfoModel;
import com.atman.wysq.model.response.MyLiveStatusModel;
import com.atman.wysq.ui.PictureBrowsingActivity;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.yunxinfriend.OtherPersonalActivity;
import com.atman.wysq.utils.BitmapTools;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.atman.wysq.utils.UiHelper;
import com.atman.wysq.yunxin.model.ChatRoomTypeInter;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.PromptDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.netease.LSMediaCapture.lsLogUtil;
import com.netease.LSMediaCapture.lsMediaCapture;
import com.netease.LSMediaCapture.lsMessageHandler;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.chatroom.ChatRoomMessageBuilder;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.ChatRoomServiceObserver;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;
import com.netease.nimlib.sdk.media.player.AudioPlayer;
import com.netease.nimlib.sdk.media.player.OnPlayListener;
import com.netease.nimlib.sdk.msg.attachment.AudioAttachment;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.tbl.okhttputils.OkHttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/1/5.
 */

public class MyLiveRoomActivity extends MyBaseActivity implements lsMessageHandler
        , ChatRoomAdapter.RoomAdapterInter {

    @Bind(R.id.myliveroom_bg_iv)
    SimpleDraweeView myliveroomBgIv;
    @Bind(R.id.myliveroom_head_iv)
    SimpleDraweeView myliveroomHeadIv;
    @Bind(R.id.myliveroom_gender_iv)
    ImageView myliveroomGenderIv;
    @Bind(R.id.myliveroom_verify_iv)
    ImageView myliveroomVerifyIv;
    @Bind(R.id.myliveroom_name_tv)
    TextView myliveroomNameTv;
    @Bind(R.id.myliveroom_title_tv)
    TextView myliveroomTitleTv;
    @Bind(R.id.myliveroom_level_tx)
    TextView myliveroomLevelTx;
    @Bind(R.id.myliveroom_vip_tx)
    TextView myliveroomVipTx;
    @Bind(R.id.myliveroom_svip_iv)
    ImageView myliveroomSvipIv;
    @Bind(R.id.myliveroom_anim_iv)
    ImageView myliveroomAnimIv;
    @Bind(R.id.chatroom_lv)
    PullToRefreshListView chatroomLv;
    @Bind(R.id.myliveroom_num_tv)
    TextView myliveroomNumTv;

    private Context mContext = MyLiveRoomActivity.this;
    private long roomId;
    private long chatRoomId;
    private String mliveStreamingURL;
    private String Pic_url;
    private String title;
    private AnimationDrawable animationDrawable;

    private MyLiveInfoModel mMyLiveInfoModel;
    private long startTime;
    private long endTime;

    private Thread mThread;
    private boolean m_liveStreamingOn = false;
    private boolean m_tryToStopLivestreaming = false;
    private lsMediaCapture mLSMediaCapture = null;
    //SDK统计相关变量
    private lsMediaCapture.LSLiveStreamingParaCtx mLSLiveStreamingParaCtx = null;
    private boolean m_liveStreamingInitFinished = false;
    private long mLastVideoProcessErrorAlertTime = 0;
    private long mLastAudioProcessErrorAlertTime = 0;
    private Intent mIntentLiveStreamingStopFinished = new Intent("LiveStreamingStopFinished");
    private boolean mHardWareEncEnable = false;

    public static final int CAMERA_POSITION_BACK = 0;
    public static final int LS_AUDIO_CODEC_AAC = 0;
    public static final int RTMP = 1;
    public static final int HAVE_AUDIO = 0;
    public static final int OpenQoS = 0;

    private Observer<List<ChatRoomMessage>> incomingChatRoomMsg;
    private ChatRoomAdapter mAdapter;
    private AudioPlayer player;
    private int positionAudio;
    private String resulturl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_myliveroom);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, MyLiveInfoModel temp) {
        Intent intent = new Intent(context, MyLiveRoomActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("temp", temp);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        hideTitleBar();

        setSwipeBackEnable(false);

        mMyLiveInfoModel = (MyLiveInfoModel) getIntent().getSerializableExtra("temp");
        roomId = mMyLiveInfoModel.getBody().getLive_room_id();
        chatRoomId = mMyLiveInfoModel.getBody().getRoom_id();
        Pic_url = mMyLiveInfoModel.getBody().getPic_url();
        mliveStreamingURL = mMyLiveInfoModel.getBody().getCurrentRecord().getPushUrl();
        title = mMyLiveInfoModel.getBody().getRoom_name();

        myliveroomTitleTv.setText(title);
        if (Pic_url != null && !Pic_url.isEmpty()) {
            myliveroomBgIv.setImageURI(Common.ImageUrl + Pic_url);
        }

        GetMyUserIndexModel mGetUserIndexModel = MyBaseApplication.getApplication().mGetMyUserIndexModel;
        myliveroomLevelTx.setText("Lv." + mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getUserLevel());
        if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level() >= 4) {
            myliveroomVipTx.setVisibility(View.GONE);
            myliveroomSvipIv.setVisibility(View.VISIBLE);
        } else {
            myliveroomSvipIv.setVisibility(View.GONE);
            if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level() == 0) {
                myliveroomVipTx.setVisibility(View.GONE);
            } else {
                myliveroomVipTx.setText("VIP." + mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level());
                myliveroomVipTx.setVisibility(View.VISIBLE);
            }
        }
        myliveroomNameTv.setText(mGetUserIndexModel.getBody().getUserDetailBean().getNickName());
        player = new AudioPlayer(mContext);

        if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("M")) {
            myliveroomGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            myliveroomGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }
        if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status() == 1) {
            myliveroomVerifyIv.setVisibility(View.VISIBLE);
            myliveroomGenderIv.setVisibility(View.GONE);
        } else {
            myliveroomVerifyIv.setVisibility(View.GONE);
            myliveroomGenderIv.setVisibility(View.VISIBLE);
        }
        myliveroomHeadIv.setImageURI(Common.ImageUrl + mGetUserIndexModel.getBody()
                .getUserDetailBean().getUserExt().getIcon());

        m_liveStreamingOn = false;
        m_tryToStopLivestreaming = false;

        //以下为SDK调用主要步骤，请用户参考使用
        //1、创建直播实例
        lsMediaCapture.LsMediaCapturePara lsMediaCapturePara = new lsMediaCapture.LsMediaCapturePara();
        lsMediaCapturePara.Context = getApplicationContext();
        lsMediaCapturePara.lsMessageHandler = this;
        lsMediaCapturePara.videoPreviewWidth = 0;
        lsMediaCapturePara.videoPreviewHeight = 0;
        lsMediaCapturePara.useFilter = false;
        lsMediaCapturePara.logLevel = lsLogUtil.LogLevel.INFO;
        lsMediaCapturePara.uploadLog = false; //是否上传SDK日志
        mLSMediaCapture = new lsMediaCapture(lsMediaCapturePara);
        //2、设置直播参数
        paraSet();

        //9、Demo控件的初始化（Demo层实现，用户不需要添加该操作）
        buttonInit();

        initChatRoom();
    }

    private void initChatRoom() {
        initRefreshView(PullToRefreshBase.Mode.DISABLED, chatroomLv);
        mAdapter = new ChatRoomAdapter(mContext, getmWidth(), chatroomLv, false, handler, runnable, this);
        chatroomLv.setAdapter(mAdapter);

        EnterChatRoomData data = new EnterChatRoomData(chatRoomId + "");
        NIMClient.getService(ChatRoomService.class).enterChatRoom(data)
                .setCallback(new RequestCallback<EnterChatRoomResultData>() {
                    @Override
                    public void onSuccess(EnterChatRoomResultData enterChatRoomResultData) {
                        LogUtils.e(">>>>onSuccess:");
                        getLiveNum();
                    }

                    @Override
                    public void onFailed(int i) {
                        LogUtils.e(">>>>onFailed:" + i);
                        showWraning("消息服务器连接失败，请稍后再试，或者重新登录！");
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LogUtils.e(">>>>onException:" + throwable.toString());
                        showWraning("消息服务器连接失败，请稍后再试，或者重新登录！");
                    }
                });


        incomingChatRoomMsg = new Observer<List<ChatRoomMessage>>() {
            @Override
            public void onEvent(List<ChatRoomMessage> messages) {
                // 处理新收到的消息
                LogUtils.e(">>>>>messages.size():" + messages.size());
                LogUtils.e(">>>>>messages.get(0).getContent():" + messages.get(0).getContent());
                LogUtils.e(">>>>>messages.get(0).getMsgType():" + messages.get(0).getMsgType());
                for (int i = 0; i < messages.size(); i++) {
                    ChatRoomMessageModel temp = null;
                    if (messages.get(i).getContent() != null) {
                        temp = mGson.fromJson(messages.get(i).getContent().toString()
                                , ChatRoomMessageModel.class);
                        LogUtils.e("temp.getGiftName():"+temp.getGiftName());
                        LogUtils.e("temp.getGiftMessage():"+temp.getGiftMessage());
                        LogUtils.e("temp.getContent():"+temp.getContent());
                    } else {
                        LogUtils.e(">>>>>>>>:"+messages.get(i).getRemoteExtension());
                        if (messages.get(i).getRemoteExtension()!=null) {
                            temp = mGson.fromJson(mGson.toJson(messages.get(i).getRemoteExtension())
                                    , ChatRoomMessageModel.class);
                        }
                    }
                    ImMessage mImMessage = null;
                    if (temp!=null) {
                        if (temp.getType() == ChatRoomTypeInter.ChatRoomTypeSystem
                                || temp.getType() == ChatRoomTypeInter.ChatRoomTypeSystemCMD) {
                            if (temp.getGiftId()<=0) {
                                getLiveNum();
                            }
                            mImMessage = new ImMessage(null, messages.get(i).getUuid()
                                    , String.valueOf(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId())
                                    , messages.get(i).getSessionId()
                                    , messages.get(i).getFromAccount()
                                    , temp.getUser().getNickName()
                                    , temp.getUser().getIcon()
                                    , temp.getUser().getSex()
                                    , temp.getUser().getVerify_status()
                                    , false, System.currentTimeMillis()
                                    , ChatRoomTypeInter.ChatRoomTypeText
                                    , temp.getContent(), "", "", "", "", "", "", "", "", 0, 0, true, 1);
                        } else {
                            if (messages.get(i).getMsgType() == MsgTypeEnum.text) {
                                mImMessage = new ImMessage(null, messages.get(i).getUuid()
                                        , String.valueOf(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId())
                                        , messages.get(i).getSessionId()
                                        , messages.get(i).getFromAccount()
                                        , temp.getUser().getNickName()
                                        , temp.getUser().getIcon()
                                        , temp.getUser().getSex()
                                        , temp.getUser().getVerify_status()
                                        , false, System.currentTimeMillis()
                                        , ChatRoomTypeInter.ChatRoomTypeText
                                        , temp.getContent(), "", "", "", "", "", "", "", "", 0, 0, false, 1);
                            } else if (messages.get(i).getMsgType() == MsgTypeEnum.image) {
                                String url = ((FileAttachment)messages.get(i).getAttachment()).getUrl();
                                String urlThumb = ((FileAttachment)messages.get(i).getAttachment()).getThumbPathForSave();
                                mImMessage = new ImMessage(null, messages.get(i).getUuid()
                                        , String.valueOf(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId())
                                        , messages.get(i).getSessionId()
                                        , messages.get(i).getFromAccount()
                                        , temp.getUser().getNickName()
                                        , temp.getUser().getIcon()
                                        , temp.getUser().getSex()
                                        , temp.getUser().getVerify_status()
                                        , false, System.currentTimeMillis()
                                        , ChatRoomTypeInter.ChatRoomTypeImage
                                        , "[图片]", url, url, urlThumb, "", "", "", "", "", 0, 0, false, 1);
                            } else if (messages.get(i).getMsgType() == MsgTypeEnum.audio) {
                                String urlAudio = ((AudioAttachment)messages.get(i).getAttachment()).getUrl();
                                String pathAudio = ((AudioAttachment)messages.get(i).getAttachment()).getPathForSave();
                                long Duration = ((AudioAttachment)messages.get(i).getAttachment()).getDuration();
                                LogUtils.e("urlAudio:"+urlAudio);
                                LogUtils.e("pathAudio:"+pathAudio);
                                LogUtils.e("Duration:"+Duration);
                                mImMessage = new ImMessage(null, messages.get(i).getUuid()
                                        , String.valueOf(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId())
                                        , messages.get(i).getSessionId()
                                        , messages.get(i).getFromAccount()
                                        , temp.getUser().getNickName()
                                        , temp.getUser().getIcon()
                                        , temp.getUser().getSex()
                                        , temp.getUser().getVerify_status()
                                        , false, System.currentTimeMillis()
                                        , ChatRoomTypeInter.ChatRoomTypeAudio
                                        , "[语音]", "", "", "", "", "", "", pathAudio, urlAudio, Duration/1000, 0, false, 1);
                            }
                        }
                        mAdapter.addImMessageDao(mImMessage);
                    } else {
                        LogUtils.e("temp is null");
                    }
                }
            }
        };

        receiveRegister(true);
    }

    private void getLiveNum() {
        OkHttpUtils.get().url(Common.Url_Live_Num + roomId)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_LIVE_NUM_ID).id(Common.NET_LIVE_NUM_ID).build()
                .execute(new MyStringCallback(mContext, MyLiveRoomActivity.this, false));
    }

    private void receiveRegister(boolean b) {
        NIMClient.getService(ChatRoomServiceObserver.class)
                .observeReceiveMessage(incomingChatRoomMsg, b);
    }

    //按钮初始化
    public void buttonInit() {
        //8、初始化直播推流
        if (mThread != null) {
            return;
        }
        showToast("正在链接服务器，请稍后...");
        LogUtils.e("mliveStreamingURL:" + mliveStreamingURL);
        mThread = new Thread() {
            public void run() { //正常网络下initLiveStream 1、2s就可完成，当网络很差时initLiveStream可能会消耗5-10s，因此另起线程防止UI卡住
                m_liveStreamingInitFinished = mLSMediaCapture.initLiveStream(mliveStreamingURL, mLSLiveStreamingParaCtx);
                if (m_liveStreamingInitFinished) {
                    startAV();
                } else {
                    if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showEorrExit();
                            }
                        });
                    }
                }
                mThread = null;
            }
        };
        mThread.start();
    }

    //开始直播
    private void startAV() {
        if (mLSMediaCapture != null && m_liveStreamingInitFinished) {
            //8、开始直播
            mLSMediaCapture.startLiveStreaming();
            m_liveStreamingOn = true;
        }
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();

        OkHttpUtils.postString().url(Common.Url_Live_Enter + roomId).id(Common.NET_LIVE_ENTER_ID)
                .content(mGson.toJson("")).mediaType(Common.JSON).tag(Common.NET_LIVE_ENTER_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie()).build()
                .execute(new MyStringCallback(mContext, this, false));
        OkHttpUtils.postString().url(Common.Url_Live_UserLog + roomId).id(Common.NET_LIVE_USERLOG_ID)
                .content(mGson.toJson("")).mediaType(Common.JSON).tag(Common.NET_LIVE_USERLOG_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie()).build()
                .execute(new MyStringCallback(mContext, this, false));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        m_tryToStopLivestreaming = true;
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        if (id == Common.NET_LIVE_ENTER_ID) {

        } else if (id == Common.NET_LIVE_USERLOG_ID) {

        } else if (id == Common.NET_LIVE_STATUS_ID) {

        } else {
            super.onError(call, e, code, id);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLSMediaCapture != null && m_liveStreamingOn) {
            if (mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_AUDIO) {
                //关闭推流静音帧
                mLSMediaCapture.resumeAudioEncode();
            }
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_LIVE_ENTER_ID) {

        } else if (id == Common.NET_LIVE_USERLOG_ID) {

        } else if (id == Common.NET_LIVE_STATUS_ID) {
            MyLiveStatusModel mMyLiveStatusModel = mGson.fromJson(data, MyLiveStatusModel.class);
            if (mMyLiveStatusModel.getBody().getStatus() == 1) {
                startTime = System.currentTimeMillis();
                showToast("直播中...");
                animationDrawable = (AnimationDrawable) myliveroomAnimIv.getBackground();
                animationDrawable.start();
                m_liveStreamingOn = true;
            } else {
                MyLiveRoomActivity.this.finish();
            }
        } else if (id == Common.NET_LIVE_NUM_ID) {
            MyLiveInfoModel mMyLiveInfoModel = mGson.fromJson(data, MyLiveInfoModel.class);
            myliveroomNumTv.setText(mMyLiveInfoModel.getBody().getMember_count()+"");
        }
    }

    private void seedMessge() {
        // 创建文本消息
        GetMyUserIndexModel.BodyBean.UserDetailBeanBean.UserExtBean temp = null;
        temp = MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt();

        String url = resulturl;
        ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomImageMessage(String.valueOf(chatRoomId)
                , new File(url), "");

        Map<String, Object> mUserMap = new HashMap<>();
        mUserMap.put("verify_status", temp.getVerify_status());
        mUserMap.put("nickName", temp.getNick_name());
        mUserMap.put("icon", temp.getIcon());
        mUserMap.put("sex", temp.getSex());
        mUserMap.put("userId", temp.getUser_id());
        mUserMap.put("userLevel", temp.getUserLevel());
        Map<String, Object> mMap = new HashMap<>();
        mMap.put("isAnchorImage",1);
        mMap.put("user",mUserMap);
        mMap.put("sendTime",message.getTime());
        mMap.put("type",1);
        mMap.put("id",chatRoomId);
        message.setRemoteExtension(mMap);
        // 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。
        ImMessage mImMessage = new ImMessage(null, message.getUuid()
                , String.valueOf(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId())
                , message.getSessionId()
                , message.getFromAccount()
                , temp.getNick_name()
                , temp.getIcon()
                , temp.getSex()
                , temp.getVerify_status()
                , true, message.getTime()
                , ChatRoomTypeInter.ChatRoomTypeImage
                , "[图片]", url, url, url, "", "", "", "", "", 0, 0, false, 1);
        mAdapter.addImMessageDao(mImMessage);
        NIMClient.getService(ChatRoomService.class).sendMessage(message, false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player!=null) {
            player.stop();
        }
    }

    @Override
    protected void onDestroy() {
        closeLive();
        receiveRegister(false);
        NIMClient.getService(ChatRoomService.class).exitChatRoom(chatRoomId + "");
        super.onDestroy();
        if (animationDrawable != null) {
            animationDrawable.stop();
        }

        OkHttpUtils.getInstance().cancelTag(Common.NET_LIVE_ENTER_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_LIVE_USERLOG_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_LIVE_NUM_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_LIVE_STATUS_ID);
    }

    private void closeLive() {
        //停止直播调用相关API接口
        if (mLSMediaCapture != null && m_liveStreamingOn) {

            //停止直播，释放资源
            mLSMediaCapture.stopLiveStreaming();

            //反初始化推流实例，当它与stopLiveStreaming连续调用时，参数为false
            mLSMediaCapture.uninitLsMediaCapture(false);
            mLSMediaCapture = null;

            mIntentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished", 2);
            sendBroadcast(mIntentLiveStreamingStopFinished);
        } else if (!m_liveStreamingInitFinished) {
            mIntentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished", 1);
            sendBroadcast(mIntentLiveStreamingStopFinished);

            //反初始化推流实例，当它不与stopLiveStreaming连续调用时，参数为true
            mLSMediaCapture.uninitLsMediaCapture(true);
        }

        if (m_liveStreamingOn) {
            m_liveStreamingOn = false;
        }
    }

    @OnClick({R.id.myliveroom_back_iv, R.id.myliveroom_pic_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myliveroom_back_iv:
                showWarnExit();
                break;
            case R.id.myliveroom_pic_iv:
                selectImg();
                break;
        }
    }

    private Uri imageUri;//The Uri to store the big bitmap
    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;
    private String path = "";
    private void selectImg() {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setItems(new String[]{"拍照", "相册选取"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {//拍照
                    path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
                } else {//选择照片
                    Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                    getAlbum.setType("image/*");
                    startActivityForResult(getAlbum, CHOOSE_BIG_PICTURE);
                }
                MyBaseApplication.getApplication().setFilterLock(true);
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
            imageUri = data.getData();
        } else if (requestCode == TAKE_BIG_PICTURE) {
            imageUri = Uri.parse("file:///" + path);
        }
        if (imageUri != null) {
            if (imageUri != null) {
                try {
                    File temp = BitmapTools.revitionImage(mContext, imageUri);
                    if (temp==null) {
                        showToast("发送失败");
                        return;
                    }
                    resulturl = temp.getPath();
                    myliveroomBgIv.setImageURI(imageUri);
                    seedMessge();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showEorrExit() {
        PromptDialog.Builder builder = new PromptDialog.Builder(MyLiveRoomActivity.this);
        builder.setMessage("直播开启失败，请仔细检查推流地址!");
        builder.setNegativeButton("退出直播", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

    private void showWarnExit() {
        endTime = System.currentTimeMillis();

        PromptDialog.Builder builder = new PromptDialog.Builder(MyLiveRoomActivity.this);
        builder.setTitle("确定要结束本次直播吗？");
        builder.setMessage("直播开始：" + MyTools.convertTimeS(startTime)
                + "\n直播持续：" + MyTools.getTwoTimeCount(startTime, endTime));
        builder.setPositiveButton("再来一会", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("结束直播", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mLSMediaCapture.stopLiveStreaming();
                showToast("结束中...");
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //处理SDK抛上来的异常和事件，用户需要在这里监听各种消息，进行相应的处理。
    //例如监听断网消息，用户根据断网消息进行直播重连
    //注意：直播重连请使用restartLiveStream，在网络带宽较低导致发送码率帧率降低时，可以调用这个接口重启直播，改善直播质量
    //在网络断掉的时候（用户可以监听 MSG_RTMP_ URL_ERROR 和 MSG_BAD_NETWORK_DETECT ），用户不可以立即调用改接口，而是应该在网络重新连接以后，主动调用这个接口。
    //如果在网络没有重新连接便调用这个接口，直播将不会重启
    @Override
    public void handleMessage(int msg, Object object) {
        switch (msg) {
            case MSG_INIT_LIVESTREAMING_OUTFILE_ERROR://初始化直播出错
            case MSG_INIT_LIVESTREAMING_VIDEO_ERROR:
            case MSG_INIT_LIVESTREAMING_AUDIO_ERROR:
                showToast("初始化直播出错");
                break;
            case MSG_START_LIVESTREAMING_ERROR://开始直播出错
                break;
            case MSG_STOP_LIVESTREAMING_ERROR://停止直播出错
                if (m_liveStreamingOn) {
                    showToast("MSG_STOP_LIVESTREAMING_ERROR  停止直播出错");
                }
                break;
            case MSG_AUDIO_PROCESS_ERROR://音频处理出错
                if (m_liveStreamingOn && System.currentTimeMillis() - mLastAudioProcessErrorAlertTime >= 10000) {
                    showToast("音频处理出错");
                    mLastAudioProcessErrorAlertTime = System.currentTimeMillis();
                }

                break;
            case MSG_VIDEO_PROCESS_ERROR://视频处理出错
                if (m_liveStreamingOn && System.currentTimeMillis() - mLastVideoProcessErrorAlertTime >= 10000) {
                    showToast("视频处理出错");
                    mLastVideoProcessErrorAlertTime = System.currentTimeMillis();
                }
                break;
            case MSG_START_PREVIEW_ERROR://视频预览出错，可能是获取不到camera的使用权限
                LogUtils.e("test: in handleMessage, MSG_START_PREVIEW_ERROR");
                showToast("无法打开相机，可能没有相关的权限");
                break;
            case MSG_AUDIO_RECORD_ERROR://音频采集出错，获取不到麦克风的使用权限
                showToast("无法开启；录音，可能没有相关的权限");
                LogUtils.e("test: in handleMessage, MSG_AUDIO_RECORD_ERROR");
                break;
            case MSG_RTMP_URL_ERROR://断网消息
                LogUtils.e("test: in handleMessage, MSG_RTMP_URL_ERROR");
                showToast("MSG_RTMP_URL_ERROR，推流已停止");
                break;
            case MSG_URL_NOT_AUTH://直播URL非法，URL格式不符合视频云要求
                showToast("MSG_URL_NOT_AUTH  直播地址不合法");
                break;
            case MSG_AUDIO_SAMPLE_RATE_NOT_SUPPORT_ERROR://音频采集参数不支持
                LogUtils.e("test: in handleMessage, MSG_AUDIO_SAMPLE_RATE_NOT_SUPPORT_ERROR");
                break;
            case MSG_AUDIO_PARAMETER_NOT_SUPPORT_BY_HARDWARE_ERROR://音频参数不支持
                LogUtils.e("test: in handleMessage, MSG_AUDIO_PARAMETER_NOT_SUPPORT_BY_HARDWARE_ERROR");
                break;
            case MSG_NEW_AUDIORECORD_INSTANCE_ERROR://音频实例初始化出错
                LogUtils.e("test: in handleMessage, MSG_NEW_AUDIORECORD_INSTANCE_ERROR");
                break;
            case MSG_AUDIO_START_RECORDING_ERROR://音频采集出错
                LogUtils.e("test: in handleMessage, MSG_AUDIO_START_RECORDING_ERROR");
                break;
            case MSG_QOS_TO_STOP_LIVESTREAMING://网络QoS极差，视频码率档次降到最低
                showToast("MSG_QOS_TO_STOP_LIVESTREAMING");
                LogUtils.e("test: in handleMessage, MSG_QOS_TO_STOP_LIVESTREAMING");
                break;
            case MSG_START_LIVESTREAMING_FINISHED://开始直播完成
                upLiveStatus(1);
                break;
            case MSG_STOP_LIVESTREAMING_FINISHED://停止直播完成
                LogUtils.e("test: MSG_STOP_LIVESTREAMING_FINISHED");
                m_liveStreamingOn = false;
            {
                mIntentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished", 1);
                sendBroadcast(mIntentLiveStreamingStopFinished);
            }
            upLiveStatus(0);
            break;
            case MSG_STOP_VIDEO_CAPTURE_FINISHED:
                LogUtils.e("test: in handleMessage: MSG_STOP_VIDEO_CAPTURE_FINISHED");
                break;
            case MSG_STOP_RESUME_VIDEO_CAPTURE_FINISHED:
                LogUtils.e("test: in handleMessage: MSG_STOP_RESUME_VIDEO_CAPTURE_FINISHED");
                break;
            case MSG_STOP_AUDIO_CAPTURE_FINISHED:
                LogUtils.e("test: in handleMessage: MSG_STOP_AUDIO_CAPTURE_FINISHED");
                break;
            case MSG_STOP_RESUME_AUDIO_CAPTURE_FINISHED:
                LogUtils.e("test: in handleMessage: MSG_STOP_RESUME_AUDIO_CAPTURE_FINISHED");
                break;
            case MSG_SWITCH_CAMERA_FINISHED://切换摄像头完成
                int cameraId = (Integer) object;//切换之后的camera id
                break;
            case MSG_SEND_STATICS_LOG_FINISHED://发送统计信息完成
                //LogUtils.e("test: in handleMessage, MSG_SEND_STATICS_LOG_FINISHED");
                break;
            case MSG_SERVER_COMMAND_STOP_LIVESTREAMING://服务器下发停止直播的消息反馈，暂时不使用
                //LogUtils.e("test: in handleMessage, MSG_SERVER_COMMAND_STOP_LIVESTREAMING");
                break;
            case MSG_BAD_NETWORK_DETECT://如果连续一段时间（10s）实际推流数据为0，会反馈这个错误消息
                showToast("MSG_BAD_NETWORK_DETECT");
                //LogUtils.e("test: in handleMessage, MSG_BAD_NETWORK_DETECT");
                break;
            case MSG_SCREENSHOT_FINISHED://视频截图完成后的消息反馈
                break;
            case MSG_SET_CAMERA_ID_ERROR://设置camera出错（对于只有一个摄像头的设备，如果调用了不存在的摄像头，会反馈这个错误消息）
                //LogUtils.e("test: in handleMessage, MSG_SET_CAMERA_ID_ERROR");
                break;
            case MSG_SET_GRAFFITI_ERROR://设置涂鸦出错消息反馈
                //LogUtils.e("test: in handleMessage, MSG_SET_GRAFFITI_ERROR");
                break;
            case MSG_MIX_AUDIO_FINISHED://伴音一首MP3歌曲结束后的反馈
                //LogUtils.e("test: in handleMessage, MSG_MIX_AUDIO_FINISHED");
                break;
            case MSG_URL_FORMAT_NOT_RIGHT://推流url格式不正确
                //LogUtils.e("test: in handleMessage, MSG_URL_FORMAT_NOT_RIGHT");
                LogUtils.e("MSG_URL_FORMAT_NOT_RIGHT");
                break;
            case MSG_URL_IS_EMPTY://推流url为空
                //LogUtils.e("test: in handleMessage, MSG_URL_IS_EMPTY");
                break;

            default:
                break;

        }
    }

    private void upLiveStatus(int i) {
        OkHttpUtils.postString().url(Common.Url_Live_Status + i).id(Common.NET_LIVE_STATUS_ID)
                .content(mGson.toJson("")).mediaType(Common.JSON).tag(Common.NET_LIVE_STATUS_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie()).build()
                .execute(new MyStringCallback(mContext, this, false));
    }

    //音视频参数设置
    public void paraSet() {

        //创建参数实例
        mLSLiveStreamingParaCtx = mLSMediaCapture.new LSLiveStreamingParaCtx();
        mLSLiveStreamingParaCtx.eHaraWareEncType = mLSLiveStreamingParaCtx.new HardWareEncEnable();
        mLSLiveStreamingParaCtx.eOutFormatType = mLSLiveStreamingParaCtx.new OutputFormatType();
        mLSLiveStreamingParaCtx.eOutStreamType = mLSLiveStreamingParaCtx.new OutputStreamType();
        mLSLiveStreamingParaCtx.sLSAudioParaCtx = mLSLiveStreamingParaCtx.new LSAudioParaCtx();
        mLSLiveStreamingParaCtx.sLSAudioParaCtx.codec = mLSLiveStreamingParaCtx.sLSAudioParaCtx.new LSAudioCodecType();
        mLSLiveStreamingParaCtx.sLSVideoParaCtx = mLSLiveStreamingParaCtx.new LSVideoParaCtx();
        mLSLiveStreamingParaCtx.sLSVideoParaCtx.codec = mLSLiveStreamingParaCtx.sLSVideoParaCtx.new LSVideoCodecType();
        mLSLiveStreamingParaCtx.sLSVideoParaCtx.cameraPosition = mLSLiveStreamingParaCtx.sLSVideoParaCtx.new CameraPosition();
        mLSLiveStreamingParaCtx.sLSVideoParaCtx.interfaceOrientation = mLSLiveStreamingParaCtx.sLSVideoParaCtx.new CameraOrientation();
        mLSLiveStreamingParaCtx.sLSQoSParaCtx = mLSLiveStreamingParaCtx.new LSQoSParaCtx();

        //设置摄像头信息，并开始本地视频预览
        mLSLiveStreamingParaCtx.sLSVideoParaCtx.cameraPosition.cameraPosition = CAMERA_POSITION_BACK;//默认后置摄像头，用户可以根据需要调整

        //输出格式：视频、音频和音视频
        mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType = HAVE_AUDIO;//默认音视频推流

        //输出封装格式
        mLSLiveStreamingParaCtx.eOutFormatType.outputFormatType = RTMP;//默认RTMP推流

        //输出FLV文件的路径和名称
        mLSLiveStreamingParaCtx.eOutFormatType.outputFormatFileName = "/sdcard/media.flv";//当outputFormatType为FLV或者RTMP_AND_FLV时有效

        //音频编码参数配置
        mLSLiveStreamingParaCtx.sLSAudioParaCtx.samplerate = 44100;
        mLSLiveStreamingParaCtx.sLSAudioParaCtx.bitrate = 64000;
        mLSLiveStreamingParaCtx.sLSAudioParaCtx.frameSize = 2048;
        mLSLiveStreamingParaCtx.sLSAudioParaCtx.audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
        mLSLiveStreamingParaCtx.sLSAudioParaCtx.channelConfig = AudioFormat.CHANNEL_IN_MONO;
        mLSLiveStreamingParaCtx.sLSAudioParaCtx.codec.audioCODECType = LS_AUDIO_CODEC_AAC;

        //硬件编码参数设置
        mLSLiveStreamingParaCtx.eHaraWareEncType.hardWareEncEnable = mHardWareEncEnable;//默认关闭硬件编码

        //网络QoS开关
        mLSLiveStreamingParaCtx.sLSQoSParaCtx.qosType = OpenQoS;//默认打开QoS
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 返回键
            showWarnExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            chatroomLv.getRefreshableView().smoothScrollToPosition(mAdapter.getCount());
        }
    };

    @Override
    public void onItem(View v, int position) {
        switch (v.getId()) {
            case R.id.item_p2pchat_text_headleft_iv:
                startActivity(OtherPersonalActivity.buildIntent(mContext, Long.valueOf(mAdapter.getItem(position).getUserId())));
                break;
            case R.id.item_p2pchat_image_left_iv:
            case R.id.item_p2pchat_image_right_iv:
                String imagePath = "";
                if (mAdapter.getItem(position).getImageThumUrl().startsWith("http")) {
                    imagePath = mAdapter.getItem(position).getImageUrl();
                } else {
                    File mFile = new File(mAdapter.getItem(position).getImageFilePath());
                    if (mFile.exists()) {
                        imagePath = "file://"+mAdapter.getItem(position).getImageFilePath();
                    } else {
                        imagePath = mAdapter.getItem(position).getImageUrl();
                    }
                }

                Intent intent = new Intent();
                intent.putExtra("image", imagePath);
                intent.putExtra("num", 0);
                intent.setClass(mContext, PictureBrowsingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemAudio(View v, int position, final AnimationDrawable animationDrawable) {
        switch (v.getId()) {
            case R.id.item_p2pchat_audio_right_ll:
            case R.id.item_p2pchat_audio_left_ll:
                if ((new File(mAdapter.getItem(position).getAudioFilePath()).exists())) {
                    if (player!=null && positionAudio!=position) {
                        player.stop();
                    }
                    player.setDataSource(mAdapter.getItem(position).getAudioFilePath());
                    player.setOnPlayListener(new OnPlayListener() {
                        @Override
                        public void onPrepared() {

                        }

                        @Override
                        public void onCompletion() {
                            animationDrawable.stop();
                            animationDrawable.selectDrawable(0);
                        }

                        @Override
                        public void onInterrupt() {
                            animationDrawable.stop();
                            animationDrawable.selectDrawable(0);
                        }

                        @Override
                        public void onError(String s) {
                            animationDrawable.stop();
                            animationDrawable.selectDrawable(0);
                        }

                        @Override
                        public void onPlaying(long l) {
                            animationDrawable.start();
                        }
                    });
                    if (animationDrawable.isRunning()) {
                        player.stop();
                    } else {
                        player.start(AudioManager.STREAM_MUSIC);
                    }
                } else {
                    showToast("没有文件");
                }
                positionAudio = position;
                break;
        }
    }
}
