package com.atman.wysq.ui.discover;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.ChatRoomAdapter;
import com.atman.wysq.model.bean.ImMessage;
import com.atman.wysq.model.response.ChatRoomMessageModel;
import com.atman.wysq.model.response.GetLiveHallModel;
import com.atman.wysq.model.response.GetMyUserIndexModel;
import com.atman.wysq.model.response.ListenLiveRoomInfoModel;
import com.atman.wysq.model.response.MyLiveInfoModel;
import com.atman.wysq.ui.PictureBrowsingActivity;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.yunxinfriend.OtherPersonalActivity;
import com.atman.wysq.ui.yunxinfriend.SelectGiftActivity;
import com.atman.wysq.utils.BitmapTools;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.UiHelper;
import com.atman.wysq.yunxin.model.ChatRoomTypeInter;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.PromptDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.netease.LSMediaCapture.lsMessageHandler;
import com.netease.neliveplayer.NELivePlayer;
import com.netease.neliveplayer.NEMediaPlayer;
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
import com.netease.nimlib.sdk.media.record.AudioRecorder;
import com.netease.nimlib.sdk.media.record.IAudioRecordCallback;
import com.netease.nimlib.sdk.media.record.RecordType;
import com.netease.nimlib.sdk.msg.attachment.AudioAttachment;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.nostra13.universalimageloader.core.ImageLoader;
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
 * Created by tangbingliang on 17/1/11.
 */

public class ListenLiveActivity extends MyBaseActivity implements lsMessageHandler
        , ChatRoomAdapter.RoomAdapterInter, EditCheckBack, IAudioRecordCallback {

    @Bind(R.id.listenlive_bg_iv)
    SimpleDraweeView listenliveBgIv;
    @Bind(R.id.listenlive_head_iv)
    SimpleDraweeView listenliveHeadIv;
    @Bind(R.id.listenlive_gender_iv)
    ImageView listenliveGenderIv;
    @Bind(R.id.listenlive_verify_iv)
    ImageView listenliveVerifyIv;
    @Bind(R.id.listenlive_name_tv)
    TextView listenliveNameTv;
    @Bind(R.id.listenlive_title_tv)
    TextView listenliveTitleTv;
    @Bind(R.id.listenlive_level_tx)
    TextView listenliveLevelTx;
    @Bind(R.id.listenlive_vip_tx)
    TextView listenliveVipTx;
    @Bind(R.id.listenlive_svip_iv)
    ImageView listenliveSvipIv;
    @Bind(R.id.listenlive_num_tv)
    TextView listenliveNumTv;
    @Bind(R.id.listenlive_anim_iv)
    ImageView listenliveAnimIv;
    @Bind(R.id.chatroom_lv)
    PullToRefreshListView chatroomLv;
    @Bind(R.id.timer)
    Chronometer timer;
    @Bind(R.id.timer_tip)
    TextView timerTip;
    @Bind(R.id.timer_tip_container)
    LinearLayout timerTipContainer;
    @Bind(R.id.layoutPlayAudio)
    FrameLayout layoutPlayAudio;
    @Bind(R.id.listenlive_gift_iv)
    ImageView listenliveGiftIv;
    @Bind(R.id.p2pchat_record_iv)
    ImageView p2pchatRecordIv;
    @Bind(R.id.blogdetail_addcomment_et)
    MyCleanEditText blogdetailAddcommentEt;
    @Bind(R.id.p2pchat_record_bt)
    Button p2pchatRecordBt;
    @Bind(R.id.blogdetail_addemol_iv)
    ImageView blogdetailAddemolIv;
    @Bind(R.id.p2pchat_pic_iv)
    ImageView p2pchatPicIv;
    @Bind(R.id.p2pchat_send_bt)
    Button p2pchatSendBt;
    @Bind(R.id.ll1)
    LinearLayout ll1;
    @Bind(R.id.vp_contains)
    ViewPager vpContains;
    @Bind(R.id.iv_image)
    LinearLayout ivImage;
    @Bind(R.id.ll_facechoose)
    RelativeLayout llFacechoose;
    @Bind(R.id.FaceRelativeLayout)
    com.atman.wysq.widget.face.FaceRelativeLayout FaceRelativeLayout;

    private Context mContext = ListenLiveActivity.this;
    private GetMyUserIndexModel.BodyBean.UserDetailBeanBean.UserExtBean temp;

    private GetLiveHallModel.BodyBean mBodyBean;
    private long roomId;
    private long chatRoomId;
    private String mliveStreamingURL;
    private String Pic_url;
    private String title;
    private AnimationDrawable animationDrawable;
    private AudioPlayer player;
    private long listenTime = 0;
    private int positionAudio;

    private boolean touched = false; // 是否按着
    private final int maxDuration = 60;
    protected AudioRecorder audioMessageHelper;
    private boolean started = false;
    private boolean cancelled = false;
    private boolean isToLimit = false;
    private boolean mIsBalck = false;
    private File mFile;
    private String mUuid;
    private String mText;
    private String giftId;

    private ListenLiveRoomInfoModel mListenLiveRoomInfoModel;

    private NELivePlayer mMediaPlayer = null;
    private ChatRoomAdapter mAdapter;
    private Observer<List<ChatRoomMessage>> incomingChatRoomMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listenlive);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static Intent buildIntent(Context context, GetLiveHallModel.BodyBean temp) {
        Intent intent = new Intent(context, ListenLiveActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("temp", temp);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        hideTitleBar();
        setSwipeBackEnable(false);

        mBodyBean = (GetLiveHallModel.BodyBean) getIntent().getSerializableExtra("temp");
        roomId = mBodyBean.getLive_room_id();
        chatRoomId = mBodyBean.getRoom_id();
        Pic_url = mBodyBean.getPic_url();
        mliveStreamingURL = mBodyBean.getCurrentRecord().getRtmpPullUrl();
        title = mBodyBean.getRoom_name();

        temp = MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt();

        listenliveTitleTv.setText(title);
        if (Pic_url != null && !Pic_url.isEmpty()) {
            listenliveBgIv.setImageURI(Common.ImageUrl + Pic_url);
        }
        listenliveLevelTx.setText("Lv." + mBodyBean.getUserExt().getUserLevel());
        if (mBodyBean.getUserExt().getVip_level() >= 4) {
            listenliveVipTx.setVisibility(View.GONE);
            listenliveSvipIv.setVisibility(View.VISIBLE);
        } else {
            listenliveSvipIv.setVisibility(View.GONE);
            if (mBodyBean.getUserExt().getVip_level() == 0) {
                listenliveVipTx.setVisibility(View.GONE);
            } else {
                listenliveVipTx.setText("VIP." + mBodyBean.getUserExt().getVip_level());
                listenliveVipTx.setVisibility(View.VISIBLE);
            }
        }
        listenliveNameTv.setText(mBodyBean.getUserExt().getNick_name());
        player = new AudioPlayer(mContext);

        if (mBodyBean.getUserExt().getSex().equals("M")) {
            listenliveGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            listenliveGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }
        if (mBodyBean.getUserExt().getVerify_status() == 1) {
            listenliveVerifyIv.setVisibility(View.VISIBLE);
            listenliveGenderIv.setVisibility(View.GONE);
        } else {
            listenliveVerifyIv.setVisibility(View.GONE);
            listenliveGenderIv.setVisibility(View.VISIBLE);
        }
        listenliveHeadIv.setImageURI(Common.ImageUrl + mBodyBean.getUserExt().getIcon());

        p2pchatRecordBt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mIsBalck) {
                        showWraning("你已被对方加入黑名单");
                        return true;
                    }
                    touched = true;
                    initAudioRecord();
                    onStartAudioRecord();
                } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                        || event.getAction() == MotionEvent.ACTION_UP) {
                    if (mIsBalck) {
                        return true;
                    }
                    touched = false;
                    onEndAudioRecord(isCancelled(v, event));
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (mIsBalck) {
                        return true;
                    }
                    touched = false;
                    cancelAudioRecord(isCancelled(v, event));
                }
                return false;
            }
        });

        blogdetailAddcommentEt.addTextChangedListener(new MyTextWatcherTwo(this));

        initListenLive();

        initChatRoom();
    }

    /**
     * 初始化AudioRecord
     */
    private void initAudioRecord() {
        if (audioMessageHelper == null) {
            audioMessageHelper = new AudioRecorder(mContext, RecordType.AAC, maxDuration, this);
        }
    }

    /**
     * 开始语音录制
     */
    private void onStartAudioRecord() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        started = audioMessageHelper.startRecord();
        cancelled = false;
        if (started == false) {
            showToast("初始化录音失败");
            return;
        }
        isToLimit = false;
        if (!touched) {
            return;
        }

        p2pchatRecordBt.setText("松开  结束");

        updateTimerTip(false); // 初始化语音动画状态
        playAudioRecordAnim();
    }

    /**
     * 开始语音录制动画
     */
    private void playAudioRecordAnim() {
        layoutPlayAudio.setVisibility(View.VISIBLE);
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
//                if ("01:00".equals(chronometer.getText().toString())) {
//                    Message message = new Message();
//                    message.what = 1;
//                    hand.sendMessage(message);
//                }
            }
        });
    }

    /**
     * 正在进行语音录制和取消语音录制，界面展示
     *
     * @param cancel
     */
    private void updateTimerTip(boolean cancel) {
        if (cancel) {
            timerTip.setText("松开手指，取消发送");
        } else {
            timerTip.setText("手指上滑，取消发送");
        }
    }

    /**
     * 结束语音录制
     *
     * @param cancel
     */
    private void onEndAudioRecord(boolean cancel) {
        getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        audioMessageHelper.completeRecord(cancel);
        p2pchatRecordBt.setText("按住 说话");
        stopAudioRecordAnim();
    }

    // 上滑取消录音判断
    private static boolean isCancelled(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth()
                || event.getRawY() < location[1] - 40) {
            return true;
        }

        return false;
    }

    /**
     * 结束语音录制动画
     */
    private void stopAudioRecordAnim() {
        layoutPlayAudio.setVisibility(View.GONE);
        timer.stop();
        timer.setBase(SystemClock.elapsedRealtime());
    }

    /**
     * 取消语音录制
     *
     * @param cancel
     */
    private void cancelAudioRecord(boolean cancel) {
        // reject
        if (!started) {
            return;
        }
        // no change
        if (cancelled == cancel) {
            return;
        }

        cancelled = cancel;
        updateTimerTip(cancel);
    }

    private void initChatRoom() {
        initRefreshView(PullToRefreshBase.Mode.DISABLED, chatroomLv);
        mAdapter = new ChatRoomAdapter(mContext, getmWidth(), chatroomLv, false, handler, runnable, this);
        chatroomLv.setAdapter(mAdapter);

        LogUtils.e(">>>chatRoomId:" + chatRoomId);
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
                LogUtils.e(">>>>>messages:" + messages.size());
                LogUtils.e(">>>>>messages:" + messages.get(0).getContent());
                LogUtils.e(">>>>>messages:" + messages.get(0).getMsgType());
                for (int i = 0; i < messages.size(); i++) {
                    ChatRoomMessageModel temp = null;
                    if (messages.get(i).getContent() != null) {
                        temp = mGson.fromJson(messages.get(i).getContent().toString()
                                , ChatRoomMessageModel.class);
                    } else {
                        LogUtils.e(">>>>>>>>:" + messages.get(i).getRemoteExtension());
                        if (messages.get(i).getRemoteExtension() != null) {
                            temp = mGson.fromJson(mGson.toJson(messages.get(i).getRemoteExtension())
                                    , ChatRoomMessageModel.class);
                        }
                    }
                    ImMessage mImMessage = null;
                    if (temp != null) {
                        if (temp.getType() == ChatRoomTypeInter.ChatRoomTypeSystem
                                || temp.getType() == ChatRoomTypeInter.ChatRoomTypeSystemCMD) {
                            if (temp.getGiftId()<=0) {
                                getLiveNum();
                            }
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
                                String url = ((FileAttachment) messages.get(i).getAttachment()).getUrl();
                                String urlThumb = ((FileAttachment) messages.get(i).getAttachment()).getThumbPathForSave();
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
                                if (temp.getIsAnchorImage() == 1) {
                                    listenliveBgIv.setImageURI(url);
                                }
                            } else if (messages.get(i).getMsgType() == MsgTypeEnum.audio) {
                                String urlAudio = ((AudioAttachment) messages.get(i).getAttachment()).getUrl();
                                String pathAudio = ((AudioAttachment) messages.get(i).getAttachment()).getPathForSave();
                                long Duration = ((AudioAttachment) messages.get(i).getAttachment()).getDuration();
                                LogUtils.e("urlAudio:" + urlAudio);
                                LogUtils.e("pathAudio:" + pathAudio);
                                LogUtils.e("Duration:" + Duration);
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
                                        , "[语音]", "", "", "", "", "", "", pathAudio, urlAudio, Duration / 1000, 0, false, 1);
                            }
                            mAdapter.addImMessageDao(mImMessage);
                        }
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
                .execute(new MyStringCallback(mContext, ListenLiveActivity.this, false));
    }

    private void receiveRegister(boolean b) {
        NIMClient.getService(ChatRoomServiceObserver.class)
                .observeReceiveMessage(incomingChatRoomMsg, b);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            chatroomLv.getRefreshableView().smoothScrollToPosition(mAdapter.getCount());
        }
    };

    private void initListenLive() {
        NEMediaPlayer neMediaPlayer = new NEMediaPlayer();
        mMediaPlayer = neMediaPlayer;

        mMediaPlayer.setBufferStrategy(0); //0为直播低延时模式，1为直播流畅模式, 2为点播抗抖动模式
        mMediaPlayer.setHardwareDecoder(false); //0为软件解码，1为硬件解码

        mMediaPlayer.setOnPreparedListener(mPreparedListener);
        mMediaPlayer.setOnErrorListener(mErrorListener);
        mMediaPlayer.setOnInfoListener(mInfoListener);
        int ret = -1;
        try {
            ret = mMediaPlayer.setDataSource(mliveStreamingURL);//设置数据源，返回0正常，返回-1地址非法
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ret < 0) {
            showWraning("地址非法！");
            return;
        }
        mMediaPlayer.setScreenOnWhilePlaying(true); //true:播放过程中屏幕长亮
        mMediaPlayer.setPlaybackTimeout(30000);
        mMediaPlayer.prepareAsync(mContext);//需要传入VideoView的上下文信息
    }

    /*
    * 在缓冲开始、缓冲结束时调用，可以在该函数内添加处理逻辑
    * info标识缓冲开始 NELP_BUFFERING_START;
    * info标识缓冲结束 NELP_BUFFERING_END;
    * info标识视频第一帧显示 NELP_FIRST_VIDEO_RENDERED;
    * info标识音频第一帧显示 NELP_FIRST_AUDIO_RENDERED;
    *
    * 该监听器用于监听播放器的状态消息，播放过程中状态发生变化时会触发该回调，用户可以根据自己应用层的业务逻辑来做相应的处理。
    * 比如收到NELP_BUFFERING_START消息，说明此时网络状况不好，播放处于卡顿状态，此时可以在播放界面上加载一个缓冲中的动画，
    * 等收到NELP_BUFFERING_END的消息，说明缓冲结束，可以开始播放了，此时可以把缓冲动画关闭。
    * */
    NELivePlayer.OnInfoListener mInfoListener = new NELivePlayer.OnInfoListener() {

        /**
         * 在缓冲开始、缓冲结束时调用，可以在该函数内添加处理逻辑
         * @param neLivePlayer 播放器实例
         * @param what info类型
         * @param extra 附加信息
         */
        @Override
        public boolean onInfo(NELivePlayer neLivePlayer, int what, int extra) {
            return false;
        }
    };

    /*
    * 该监听器用于监听播放过程中发生的错误消息，发生任何错误，都会触发该回调。在播放发生错误的时候，用户想继续播放,
    * 则可以在收到该回调后释放上次播放的资源，重新初始化，播放。
    * */
    NELivePlayer.OnPreparedListener mPreparedListener = new NELivePlayer.OnPreparedListener() {
        public void onPrepared(NELivePlayer mp) {
            mMediaPlayer.start();
            showToast("收听中");
            String str = "【"+temp.getNick_name()+"】进入了电台";
            ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomTextMessage(
                    String.valueOf(chatRoomId), str);
            seedMessge(message, ChatRoomTypeInter.ChatRoomTypeSystem, "", str, "0");
            animationDrawable = (AnimationDrawable) listenliveAnimIv.getBackground();
            animationDrawable.start();
        }
    };

    /*
    * 该监听器用于监听播放过程中发生的错误消息，发生任何错误，都会触发该回调。在播放发生错误的时候，用户想继续播放，
    * 则可以在收到该回调后释放上次播放的资源，重新初始化，播放。
    * */
    NELivePlayer.OnErrorListener mErrorListener = new NELivePlayer.OnErrorListener() {

        /**
         * 播放发生错误时调用，可以在该函数内添加处理逻辑
         * @param neLivePlayer 播放器实例
         * @param what 错误类型
         * @param extra 附加信息
         */
        @Override
        public boolean onError(NELivePlayer neLivePlayer, int what, int extra) {
            showWraning("当前电台可能已关闭，\n去收听别的电台吧");
            return false;
        }
    };

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
        payListenMoney();
    }

    private void payListenMoney() {
        //扣金币接口，每分钟调用一下
        OkHttpUtils.postString().url(Common.Url_Live_Money + roomId + "/" + listenTime)
                .id(Common.NET_LIVE_MONEY_ID).content(mGson.toJson(""))
                .mediaType(Common.JSON).tag(Common.NET_LIVE_MONEY_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie()).build()
                .execute(new MyStringCallback(mContext, this, false));
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        if (id == Common.NET_LIVE_ENTER_ID) {

        } else if (id == Common.NET_LIVE_USERLOG_ID) {

        } else if (id == Common.NET_LIVE_MONEY_ID) {

        } else {
            super.onError(call, e, code, id);
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_LIVE_ENTER_ID) {
            mListenLiveRoomInfoModel = mGson.fromJson(data, ListenLiveRoomInfoModel.class);

            setLiveNum(mListenLiveRoomInfoModel.getBody().getMember_count() + "");
        } else if (id == Common.NET_LIVE_USERLOG_ID) {

        } else if (id == Common.NET_LIVE_MONEY_ID) {

        } else if (id == Common.NET_LIVE_NUM_ID) {
            MyLiveInfoModel mMyLiveInfoModel = mGson.fromJson(data, MyLiveInfoModel.class);
            setLiveNum(mMyLiveInfoModel.getBody().getMember_count()+"");
        }
    }

    private void setLiveNum(String s) {
        listenliveNumTv.setText(s);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_LIVE_ENTER_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_LIVE_USERLOG_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_LIVE_MONEY_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_LIVE_NUM_ID);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 返回键
            showWarnExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showWarnExit() {
        PromptDialog.Builder builder = new PromptDialog.Builder(ListenLiveActivity.this);
        builder.setMessage("确定要离开本店台吗？");
        builder.setPositiveButton("不走", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("先走一步", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String str = "【"+temp.getNick_name()+"】离开了电台";
                ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomTextMessage(
                        String.valueOf(chatRoomId), str);
                seedMessge(message, ChatRoomTypeInter.ChatRoomTypeSystemCMD, "", str, "0");
                receiveRegister(false);
                mMediaPlayer.release();
                finish();
            }
        });
        builder.show();
    }

    @OnClick({R.id.listenlive_bg_iv, R.id.listenlive_gift_iv, R.id.p2pchat_record_iv, R.id.blogdetail_addemol_iv
            , R.id.p2pchat_pic_iv, R.id.p2pchat_send_bt, R.id.listenlive_back_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.listenlive_bg_iv:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                if (isIMOpen()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                }
                blogdetailAddemolIv.setImageResource(R.mipmap.chat_face_ic);
                p2pchatRecordIv.setImageResource(R.mipmap.chat_record_ic);
                blogdetailAddcommentEt.setVisibility(View.VISIBLE);
                llFacechoose.setVisibility(View.GONE);
                p2pchatRecordBt.setVisibility(View.GONE);
                break;
            case R.id.listenlive_back_iv:
                showWarnExit();
                break;
            case R.id.listenlive_gift_iv:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                startActivityForResult(SelectGiftActivity.buildIntent(mContext, String.valueOf(temp.getUser_id()), true)
                        , Common.toSelectGift);
                break;
            case R.id.p2pchat_record_iv:
                if (layoutPlayAudio.getVisibility() == View.VISIBLE) {
                    return;
                }
                if (llFacechoose.getVisibility() == View.VISIBLE) {
                    llFacechoose.setVisibility(View.GONE);
                }
                if (blogdetailAddcommentEt.getVisibility() == View.VISIBLE) {
                    if (isIMOpen()) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                    }
                    blogdetailAddcommentEt.setVisibility(View.GONE);
                    p2pchatRecordBt.setVisibility(View.VISIBLE);
                    p2pchatRecordIv.setImageResource(R.mipmap.chat_key_ic);
                } else {
                    blogdetailAddcommentEt.setVisibility(View.VISIBLE);
                    p2pchatRecordBt.setVisibility(View.GONE);
                    p2pchatRecordIv.setImageResource(R.mipmap.chat_record_ic);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                }
                blogdetailAddemolIv.setImageResource(R.mipmap.chat_face_ic);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.blogdetail_addemol_iv:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                if (llFacechoose.getVisibility() == View.GONE) {
                    if (isIMOpen()) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                    }
                    llFacechoose.setVisibility(View.VISIBLE);
                    blogdetailAddemolIv.setImageResource(R.mipmap.chat_key_ic);
                } else {
                    llFacechoose.setVisibility(View.GONE);
                    blogdetailAddemolIv.setImageResource(R.mipmap.chat_face_ic);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                }
                blogdetailAddcommentEt.setVisibility(View.VISIBLE);
                p2pchatRecordBt.setVisibility(View.GONE);
                p2pchatRecordIv.setImageResource(R.mipmap.chat_record_ic);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.p2pchat_pic_iv:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                selectImg();
                break;
            case R.id.p2pchat_send_bt:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                if (blogdetailAddcommentEt.getText().toString().trim().isEmpty()) {
                    showToast("请输入内容");
                    return;
                }
                if (mIsBalck) {
                    showWraning("你已被对方加入黑名单");
                    return;
                }
                // 创建文本消息
                String text = blogdetailAddcommentEt.getText().toString().trim();
                ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomTextMessage(
                        String.valueOf(chatRoomId), text);
                seedMessge(message, ChatRoomTypeInter.ChatRoomTypeText, "", text, "0");
                blogdetailAddcommentEt.setText("");
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
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(com.base.baselibs.R.anim.activity_bottom_in, com.base.baselibs.R.anim.activity_bottom_out);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.toSelectGift) {
//            mText = data.getStringExtra("text");
//            String name = data.getStringExtra("name");
            giftId = String.valueOf(data.getIntExtra("giftId", 0));
//            File file = ImageLoader.getInstance().getDiskCache().get(Common.ImageUrl+data.getStringExtra("url"));
//            ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomImageMessage(
//                    String.valueOf(chatRoomId), file, "");
//            seedMessge(message, ChatRoomTypeInter.ChatRoomTypeImage, file.getPath(), "", giftId);
//            mUuid = message.getUuid();

            String str = "【"+temp.getNick_name()+"】赠送主播礼物："+data.getStringExtra("name");
            ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomTextMessage(
                    String.valueOf(chatRoomId), str);
            seedMessge(message, ChatRoomTypeInter.ChatRoomTypeText, "", str, giftId);
        } else {
            if (mIsBalck) {
                showWraning("你已被对方加入黑名单");
                return;
            }
            if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
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
                        ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomImageMessage(
                                String.valueOf(chatRoomId), temp, "");
                        seedMessge(message, ChatRoomTypeInter.ChatRoomTypeImage, temp.getPath(), "", "0");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void seedMessge(ChatRoomMessage me, int type, String url, String text, String giftId) {
        // 创建消息

        Map<String, Object> mUserMap = new HashMap<>();
        mUserMap.put("verify_status", temp.getVerify_status());
        mUserMap.put("nickName", temp.getNick_name());
        mUserMap.put("icon", temp.getIcon());
        mUserMap.put("sex", temp.getSex());
        mUserMap.put("userId", temp.getUser_id());
        mUserMap.put("userLevel", temp.getUserLevel());
        Map<String, Object> mMap = new HashMap<>();
        mMap.put("isAnchorImage",0);
        mMap.put("user",mUserMap);
        mMap.put("sendTime",me.getTime());
        if (Integer.parseInt(giftId)>0 || type>3) {
            mMap.put("type",3);
        } else {
            mMap.put("type",type);
        }
        mMap.put("content",text);
        mMap.put("id",chatRoomId);
        mMap.put("giftId",giftId);

        // 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。
        ImMessage mImMessage = null;
        switch (type) {
            case ChatRoomTypeInter.ChatRoomTypeText:
                me.setContent(mGson.toJson(mMap));
                if (Integer.parseInt(giftId)>0) {
                    mImMessage = new ImMessage(null, me.getUuid()
                            , String.valueOf(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId())
                            , me.getSessionId()
                            , me.getFromAccount()
                            , temp.getNick_name()
                            , temp.getIcon()
                            , temp.getSex()
                            , temp.getVerify_status()
                            , true, me.getTime()
                            , ChatRoomTypeInter.ChatRoomTypeText
                            , text, "", "", "", "", "", "", "", "", 0, 0, true, 1);
                } else {
                    mImMessage = new ImMessage(null, me.getUuid()
                            , String.valueOf(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId())
                            , me.getSessionId()
                            , me.getFromAccount()
                            , temp.getNick_name()
                            , temp.getIcon()
                            , temp.getSex()
                            , temp.getVerify_status()
                            , true, me.getTime()
                            , ChatRoomTypeInter.ChatRoomTypeText
                            , text, "", "", "", "", "", "", "", "", 0, 0, false, 1);
                }
                mAdapter.addImMessageDao(mImMessage);
                break;
            case ChatRoomTypeInter.ChatRoomTypeImage:
                me.setRemoteExtension(mMap);
                mImMessage = new ImMessage(null, me.getUuid()
                        , String.valueOf(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId())
                        , me.getSessionId()
                        , me.getFromAccount()
                        , temp.getNick_name()
                        , temp.getIcon()
                        , temp.getSex()
                        , temp.getVerify_status()
                        , true, me.getTime()
                        , ChatRoomTypeInter.ChatRoomTypeImage
                        , "[图片]", url, url, url, "", "", "", "", "", 0, 0, false, 1);
                mAdapter.addImMessageDao(mImMessage);
                break;
            case ChatRoomTypeInter.ChatRoomTypeAudio:
                me.setRemoteExtension(mMap);
                String urlAudio = ((AudioAttachment)me.getAttachment()).getUrl();
                String pathAudio = ((AudioAttachment)me.getAttachment()).getPathForSave();
                long Duration = ((AudioAttachment)me.getAttachment()).getDuration();
                mImMessage = new ImMessage(null, me.getUuid()
                        , String.valueOf(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId())
                        , me.getSessionId()
                        , me.getFromAccount()
                        , temp.getNick_name()
                        , temp.getIcon()
                        , temp.getSex()
                        , temp.getVerify_status()
                        , true, me.getTime()
                        , ChatRoomTypeInter.ChatRoomTypeAudio
                        , "[语音]", "", "", "", "", "", "", pathAudio, urlAudio, Duration/1000, 0, false, 1);
                mAdapter.addImMessageDao(mImMessage);
                break;
            default:
                me.setContent(mGson.toJson(mMap));
                me.setRemoteExtension(mMap);
                break;
        }
        LogUtils.e(">>>>:"+me.getRemoteExtension().toString());
        LogUtils.e(">>>>:"+me.getMsgType());
        NIMClient.getService(ChatRoomService.class).sendMessage(me, true);
    }

    @Override
    public void onItem(View v, int position) {
        switch (v.getId()) {
            case R.id.item_p2pchat_text_headleft_iv:
                startActivity(OtherPersonalActivity.buildIntent(mContext, Long.valueOf(mAdapter.getItem(position).getUserId())));
                break;
            case R.id.item_p2pchat_root_Rl:
                if (layoutPlayAudio.getVisibility()==View.VISIBLE) {
                    return;
                }
                if (isIMOpen()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                }
                blogdetailAddemolIv.setImageResource(R.mipmap.chat_face_ic);
                p2pchatRecordIv.setImageResource(R.mipmap.chat_record_ic);
                blogdetailAddcommentEt.setVisibility(View.VISIBLE);
                llFacechoose.setVisibility(View.GONE);
                p2pchatRecordBt.setVisibility(View.GONE);
                break;
            case R.id.item_p2pchat_image_left_iv:
            case R.id.item_p2pchat_image_right_iv:
                String imagePath = "";
                if (mAdapter.getItem(position).getImageThumUrl().startsWith("http")) {
                    imagePath = mAdapter.getItem(position).getImageUrl();
                } else {
                    File mFile = new File(mAdapter.getItem(position).getImageFilePath());
                    if (mFile.exists()) {
                        imagePath = "file://" + mAdapter.getItem(position).getImageFilePath();
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
                    if (player != null && positionAudio != position) {
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

    @Override
    public void handleMessage(int i, Object o) {

    }

    @Override
    public void isNull() {
        if (TextUtils.isEmpty(blogdetailAddcommentEt.getText().toString())) {
            p2pchatSendBt.setVisibility(View.GONE);
            p2pchatPicIv.setVisibility(View.VISIBLE);
        } else {
            p2pchatSendBt.setVisibility(View.VISIBLE);
            p2pchatPicIv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRecordReady() {

    }

    @Override
    public void onRecordStart(File file, RecordType recordType) {
        mFile = file;
    }

    @Override
    public void onRecordSuccess(File file, long l, RecordType recordType) {
        ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomAudioMessage(
                String.valueOf(chatRoomId), file, l);
        seedMessge(message, ChatRoomTypeInter.ChatRoomTypeAudio, "", "", "0");
    }

    @Override
    public void onRecordFail() {

    }

    @Override
    public void onRecordCancel() {

    }

    @Override
    public void onRecordReachedMaxTime(int i) {
        Message message = new Message();
        message.what = 1;
        hand.sendMessage(message);
        ChatRoomMessage temp = ChatRoomMessageBuilder.createChatRoomAudioMessage(
                String.valueOf(chatRoomId), mFile, 60000);
        seedMessge(temp, ChatRoomTypeInter.ChatRoomTypeAudio, "", "", "0");
    }

    Handler hand = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    touched = false;
                    onEndAudioRecord(false);
                    isToLimit = true;
                    break;
            }
            super.handleMessage(msg);
        }

    };
}
