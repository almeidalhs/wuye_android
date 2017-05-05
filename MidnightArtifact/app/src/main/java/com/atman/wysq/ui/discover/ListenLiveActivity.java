package com.atman.wysq.ui.discover;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.ChatRoomAdapter;
import com.atman.wysq.adapter.InvitationAdapter;
import com.atman.wysq.adapter.LiveDetailOnLineAdapter;
import com.atman.wysq.model.bean.ImMessage;
import com.atman.wysq.model.response.ChatRoomMessageModel;
import com.atman.wysq.model.response.CommunityNewModel;
import com.atman.wysq.model.response.GetFollowMeModel;
import com.atman.wysq.model.response.GetLiveDetailLikeModel;
import com.atman.wysq.model.response.GetMyUserIndexModel;
import com.atman.wysq.model.response.GetUserIndexModel;
import com.atman.wysq.model.response.GiftListModel;
import com.atman.wysq.model.response.ListenLiveRoomInfoModel;
import com.atman.wysq.model.response.LiveDetailModel;
import com.atman.wysq.ui.PictureBrowsingActivity;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.community.ReportActivity;
import com.atman.wysq.ui.community.ReportListActivity;
import com.atman.wysq.ui.yunxinfriend.SelectGiftActivity;
import com.atman.wysq.utils.BitmapTools;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.ContentUriUtil;
import com.atman.wysq.utils.MyTools;
import com.atman.wysq.utils.UiHelper;
import com.atman.wysq.widget.ShowHeadPopWindow;
import com.atman.wysq.widget.face.FaceRelativeLayout;
import com.atman.wysq.yunxin.model.ChatRoomTypeInter;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.PromptDialog;
import com.base.baselibs.widget.addflowers.PeriscopeLayout;
import com.facebook.drawee.backends.pipeline.Fresco;
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
import com.netease.nimlib.sdk.msg.attachment.AudioAttachment;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tbl.okhttputils.OkHttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/1/11.
 */

public class ListenLiveActivity extends MyBaseActivity implements lsMessageHandler
        , ChatRoomAdapter.RoomAdapterInter, EditCheckBack, FaceRelativeLayout.onEditListener
        , ShowHeadPopWindow.onHeadPopClickListenner, AdapterInterface {

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
    @Bind(R.id.chatroom_lv)
    PullToRefreshListView chatroomLv;
    @Bind(R.id.listenlive_gift_iv)
    ImageView listenliveGiftIv;
    @Bind(R.id.blogdetail_addcomment_et)
    MyCleanEditText blogdetailAddcommentEt;
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
    FaceRelativeLayout FaceRelativeLayout;
    @Bind(R.id.listenlive_kissnum_tv)
    TextView listenliveKissnumTv;
    @Bind(R.id.listenlive_money_tv)
    TextView listenliveMoneyTv;
    @Bind(R.id.periscope)
    PeriscopeLayout periscope;
    @Bind(R.id.listenlive_recyclerview)
    RecyclerView listenliveRecyclerview;
    @Bind(R.id.listenlive_antion_tv)
    TextView listenliveAntionTv;
    @Bind(R.id.listenlive_gift_head_iv)
    SimpleDraweeView listenliveGiftHeadIv;
    @Bind(R.id.listenlive_gift_name_tv)
    TextView listenliveGiftNameTv;
    @Bind(R.id.listenlive_gift_content_tv)
    TextView listenliveGiftContentTv;
    @Bind(R.id.listenlive_gift_inner_iv)
    SimpleDraweeView listenliveGiftInnerIv;
    @Bind(R.id.listenlive_gift_rl)
    RelativeLayout listenliveGiftRl;

    private Context mContext = ListenLiveActivity.this;
    private GetMyUserIndexModel.BodyBean.UserDetailBeanBean.UserExtBean mMyUserInfo;

    private CommunityNewModel.BodyBean.LiveRoomBean mBodyBean;
    private long roomId;
    private long userId;
    private long chatRoomId;
    private String mliveStreamingURL;
    private String Pic_url;
    private String title;
    private AudioPlayer player;
    private int positionAudio;

    private boolean mIsBalck = false;
    private String giftId;

    private ListenLiveRoomInfoModel mListenLiveRoomInfoModel;
    private LiveDetailModel mLiveDetailModel;
    private LiveDetailOnLineAdapter onLineAdapter;
    private int waitUpNum = 0;

    private NELivePlayer mMediaPlayer = null;
    private ChatRoomAdapter mAdapter;
    private Observer<List<ChatRoomMessage>> incomingChatRoomMsg;
    private List<GiftListModel.BodyEntity> mGiftList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listenlive);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static Intent buildIntent(Context context, CommunityNewModel.BodyBean.LiveRoomBean temp) {
        Intent intent = new Intent(context, ListenLiveActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("temp", temp);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyBaseApplication.getApplication().mLiveStatue = "2";
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        hideTitleBar();
        setSwipeBackEnable(false);

        mBodyBean = (CommunityNewModel.BodyBean.LiveRoomBean) getIntent().getSerializableExtra("temp");
        roomId = mBodyBean.getLive_room_id();
        userId = mBodyBean.getUser_id();
        chatRoomId = mBodyBean.getRoom_id();
        Pic_url = mBodyBean.getPic_url();
        mliveStreamingURL = mBodyBean.getCurrentRecord().getRtmpPullUrl();
        title = mBodyBean.getRoom_name();

        FaceRelativeLayout.setmOnEditListener(this);

        mMyUserInfo = MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody()
                .getUserDetailBean().getUserExt();

        listenliveTitleTv.setText("话题:" + title);
        if (Pic_url != null && !Pic_url.isEmpty()) {
            listenliveBgIv.setImageURI(Common.ImageUrl + Pic_url);
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

        blogdetailAddcommentEt.addTextChangedListener(new MyTextWatcherTwo(this));

        initListenLive();

        initChatRoom();
        initOnlineList();
    }

    private void initOnlineList() {
        onLineAdapter = new LiveDetailOnLineAdapter(mContext, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        listenliveRecyclerview.setLayoutManager(linearLayoutManager);
        listenliveRecyclerview.setAdapter(onLineAdapter);
        listenliveRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {//滑动停止
                    Fresco.getImagePipeline().resume();//开启图片加载
                } else {
                    Fresco.getImagePipeline().pause();//暂停图片加载
                }
            }
        });
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
                        getLiveNum();
                    }

                    @Override
                    public void onFailed(int i) {
                        showWraning("消息服务器连接失败，请稍后再试，或者重新登录！");
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        showWraning("消息服务器连接失败，请稍后再试，或者重新登录！");
                    }
                });


        incomingChatRoomMsg = new Observer<List<ChatRoomMessage>>() {
            @Override
            public void onEvent(List<ChatRoomMessage> messages) {
                // 处理新收到的消息
                for (int i = 0; i < messages.size(); i++) {
                    if (String.valueOf(chatRoomId).equals(messages.get(0).getSessionId())) {
                        ChatRoomMessageModel temp = null;
                        if (messages.get(i).getContent() != null) {
                            temp = mGson.fromJson(messages.get(i).getContent().toString()
                                    , ChatRoomMessageModel.class);
                        } else {
                            if (messages.get(i).getRemoteExtension() != null) {
                                temp = mGson.fromJson(mGson.toJson(messages.get(i).getRemoteExtension())
                                        , ChatRoomMessageModel.class);
                            }
                        }
                        ImMessage mImMessage = null;
                        if (temp != null) {
                            if (temp.getType() == ChatRoomTypeInter.ChatRoomTypeSystem
                                    || temp.getType() == ChatRoomTypeInter.ChatRoomTypeSystemCMD) {
                                if (temp.getGiftId() <= 0) {
                                    getLiveNum();
                                } else {
                                    String url = "";
                                    for (int z = 0; z < mGiftList.size(); z++) {
                                        if (temp.getGiftId() == mGiftList.get(z).getGift_id()) {
                                            url = Common.ImageUrl + mGiftList.get(z).getPic_url();
                                            break;
                                        }
                                    }
                                    giftUserId = temp.getUser().getUserId();
                                    GiftAnimation(temp.getUser().getIcon()
                                            , temp.getUser().getNickName()
                                            , temp.getContent().split(":")[1], url);
                                }
                                String noStr = temp.getContent();
                                if (temp.getBanChatUserId() == mMyUserInfo.getUser_id()) {
                                    noStr = "主播请你15分钟后再发言";
                                    PreferenceUtil.saveLongPreference(mContext, PreferenceUtil.PARM_GAG_TIME, (long) temp.getSendTime() * 1000);
                                }
                                if (noStr != null && !noStr.isEmpty()) {
                                    mImMessage = new ImMessage(null, messages.get(i).getUuid()
                                            , String.valueOf(mMyUserInfo.getUser_id())
                                            , messages.get(i).getSessionId()
                                            , messages.get(i).getFromAccount()
                                            , temp.getUser().getNickName()
                                            , temp.getUser().getIcon()
                                            , temp.getUser().getSex()
                                            , temp.getUser().getVerify_status()
                                            , false, System.currentTimeMillis()
                                            , ChatRoomTypeInter.ChatRoomTypeText
                                            , noStr, "", "", "", "", "", "", "", "", 0, 0, true, 1);
                                }
                            } else {
                                if (messages.get(i).getMsgType() == MsgTypeEnum.text) {
                                    mImMessage = new ImMessage(null, messages.get(i).getUuid()
                                            , String.valueOf(mMyUserInfo.getUser_id())
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
                                            , String.valueOf(mMyUserInfo.getUser_id())
                                            , messages.get(i).getSessionId()
                                            , messages.get(i).getFromAccount()
                                            , temp.getUser().getNickName()
                                            , temp.getUser().getIcon()
                                            , temp.getUser().getSex()
                                            , temp.getUser().getVerify_status()
                                            , false, System.currentTimeMillis()
                                            , ChatRoomTypeInter.ChatRoomTypeImage
                                            , "[图片]", url, url, urlThumb, "", "", "", "", "", 0, 0, false, 1);
                                    //替换背景图
//                                    if (temp.getIsAnchorImage() == 1) {
//                                        listenliveBgIv.setImageURI(url);
//                                    }
                                } else if (messages.get(i).getMsgType() == MsgTypeEnum.audio) {
                                    String urlAudio = ((AudioAttachment) messages.get(i).getAttachment()).getUrl();
                                    String pathAudio = ((AudioAttachment) messages.get(i).getAttachment()).getPathForSave();
                                    long Duration = ((AudioAttachment) messages.get(i).getAttachment()).getDuration();
                                    LogUtils.e("urlAudio:" + urlAudio);
                                    LogUtils.e("pathAudio:" + pathAudio);
                                    LogUtils.e("Duration:" + Duration);
                                    mImMessage = new ImMessage(null, messages.get(i).getUuid()
                                            , String.valueOf(mMyUserInfo.getUser_id())
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
                            }
                            if (mImMessage != null) {
                                mAdapter.addImMessageDao(mImMessage);
                            }
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
            String str = "【" + mMyUserInfo.getNick_name() + "】进入了电台";
            ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomTextMessage(
                    String.valueOf(chatRoomId), str);
            seedMessge(message, ChatRoomTypeInter.ChatRoomTypeSystem, "", str, "0");
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
        OkHttpUtils.get().url(Common.Url_Get_GiftList).id(Common.NET_GET_GIFTLIST)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_GIFTLIST).build().execute(new MyStringCallback(mContext, this, true));

        getLiveDetail();
    }

    private void getLiveDetail() {
        OkHttpUtils.get().url(Common.Url_Live_Detail_Post + roomId).id(Common.NET_LIVE_DETAIL_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_LIVE_DETAIL_ID).build().execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        if (id == Common.NET_LIVE_ENTER_ID) {
        } else if (id == Common.NET_LIVE_USERLOG_ID) {
        } else if (id == Common.NET_LIVE_MONEY_ID) {
        } else if (id == Common.NET_LIVE_LOGOUT_ID) {
            liveLogout();
        } else if (id == Common.NET_GET_LIVE_LIKE_ID) {
            startGetOnLine();
        } else {
            super.onError(call, e, code, id);
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_LIVE_ENTER_ID) {
            mListenLiveRoomInfoModel = mGson.fromJson(data, ListenLiveRoomInfoModel.class);
        } else if (id == Common.NET_LIVE_USERLOG_ID) {

        } else if (id == Common.NET_LIVE_NUM_ID) {
            LiveDetailModel temp = mGson.fromJson(data, LiveDetailModel.class);
            onLineAdapter.clearData();
            if (temp.getBody().getUserList().size() > 0) {
                onLineAdapter.addData(temp.getBody().getUserList());
            }
        } else if (id == Common.NET_GET_USERINDEX) {
            GetUserIndexModel mGetMyUserIndexModel = mGson.fromJson(data, GetUserIndexModel.class);
            if (mGetMyUserIndexModel.getBody().getIsBlack() == 1) {
                mIsBalck = true;
            } else {
                mIsBalck = false;
            }
            boolean isAnchor = false;
            new ShowHeadPopWindow(ListenLiveActivity.this, getmWidth(), isAnchor
                    , mGetMyUserIndexModel.getBody().getUserFelation()
                    , mGetMyUserIndexModel.getBody().getUserDetailBean(), this);
        } else if (id == Common.NET_ADD_FOLLOW_ID) {
            if (userId == tempId) {
                listenliveAntionTv.setText("已关注");
            }
        } else if (id == Common.NET_CANCEL_BLACKLIST_ID) {
            if (userId == tempId) {
                listenliveAntionTv.setText("+ 关注");
            }
            mIsBalck = false;
        } else if (id == Common.NET_ADD_BLACKLIST) {
            mIsBalck = true;
        } else if (id == Common.NET_CANCEL_MYCONCERNLIST_ID) {

        } else if (id == Common.NET_LIVE_DETAIL_ID) {
            mLiveDetailModel = mGson.fromJson(data, LiveDetailModel.class);

            listenliveKissnumTv.setText(" " + mLiveDetailModel.getBody().getLike_num());
            listenliveMoneyTv.setText(" " + mLiveDetailModel.getBody().getGold_num());

            onLineAdapter.clearData();
            if (mLiveDetailModel.getBody().getUserList().size() > 0) {
                onLineAdapter.addData(mLiveDetailModel.getBody().getUserList());
            }
            mHanlder.removeMessages(0);
            startGetOnLine();
        } else if (id == Common.NET_GET_FOLLOWME_ID) {
            GetFollowMeModel mGetFollowMeModel = mGson.fromJson(data, GetFollowMeModel.class);

            if (mGetFollowMeModel.getBody().size() == 0) {
                showToast("还没有好友关注您！");
                return;
            }

            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_invitation_view, null);
            TextView dialogClose = (TextView) view.findViewById(R.id.dialog_invit_close_tx);
            ListView dialogListview = (ListView) view.findViewById(R.id.dialog_invit_listview);

            final InvitationAdapter temp = new InvitationAdapter(mContext, mGetFollowMeModel.getBody());
            dialogListview.setAdapter(temp);

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.dialog_center);
            builder.setCancelable(false).setView(view);
            final AlertDialog dialog = builder.create();
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.width = (int) (getmWidth() * 0.75);    //宽度设置为屏幕的0.75
            p.height = (int) (getmHight() * 0.6);    //高度设置为屏幕的0.6
            dialog.show();

            dialogListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Map<String, Object> p = new HashMap<>();
                    p.put("follow_user_id", temp.getItem(position).getUser_id());
                    p.put("liveroom_id", roomId);
                    OkHttpUtils.postString().url(Common.Url_Invite_Friends_Post).content(mGson.toJson(p))
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie()).mediaType(Common.JSON)
                            .tag(Common.NET_INVITE_FRIENDS_ID).id(Common.NET_INVITE_FRIENDS_ID).build()
                            .execute(new MyStringCallback(mContext, ListenLiveActivity.this, false));
                }
            });
            dialogClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        } else if (id == Common.NET_INVITE_FRIENDS_ID) {
            showToast("邀请好友成功！");
        } else if (id == Common.NET_GET_LIVE_LIKE_ID) {
            startGetOnLine();

            GetLiveDetailLikeModel mGetLiveDetailLikeModel = mGson.fromJson(data, GetLiveDetailLikeModel.class);

            int LikeNum = Integer.valueOf(listenliveKissnumTv.getText().toString().trim());
            int tempLikeNum = Integer.valueOf(mGetLiveDetailLikeModel.getBody().getLike_num());

            addFlowrs(tempLikeNum - LikeNum);

            listenliveKissnumTv.setText(" " + mGetLiveDetailLikeModel.getBody().getLike_num());
            listenliveMoneyTv.setText(" " + mGetLiveDetailLikeModel.getBody().getGold_num());
        } else if (id == Common.NET_GET_GIFTLIST) {
            GiftListModel mGiftListModel = mGson.fromJson(data, GiftListModel.class);
            mGiftList = mGiftListModel.getBody();
        } else if (id==Common.NET_LIVE_LOGOUT_ID) {
            liveLogout();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyBaseApplication.getApplication().mLiveStatue = "0";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_LIVE_ENTER_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_LIVE_USERLOG_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_LIVE_NUM_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_USERINDEX);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_FOLLOW_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CANCEL_BLACKLIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_BLACKLIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CANCEL_MYCONCERNLIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_LIVE_DETAIL_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_FOLLOWME_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_INVITE_FRIENDS_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_LIVE_LIKE_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_GIFTLIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_LIVE_LOGOUT_ID);
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
        builder.setMessage("确定要离开本电台吗？");
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
                OkHttpUtils.postString().url(Common.Url_Live_Logout+roomId).content("{}").mediaType(Common.JSON)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_LIVE_LOGOUT_ID).id(Common.NET_LIVE_LOGOUT_ID).build()
                        .execute(new MyStringCallback(mContext, ListenLiveActivity.this, false));
            }
        });
        builder.show();
    }

    private void liveLogout() {
        String str = "【" + mMyUserInfo.getNick_name() + "】离开了电台";
        ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomTextMessage(
                String.valueOf(chatRoomId), str);
        seedMessge(message, ChatRoomTypeInter.ChatRoomTypeSystemCMD, "", str, "0");
        receiveRegister(false);
        mMediaPlayer.release();
        finish();
    }

    @OnClick({R.id.listenlive_invitation_iv, R.id.listenlive_flowrs_iv, R.id.listenlive_bg_iv
            , R.id.listenlive_gift_iv, R.id.blogdetail_addemol_iv, R.id.p2pchat_pic_iv, R.id.listenlive_gift_head_iv
            , R.id.p2pchat_send_bt, R.id.listenlive_back_iv, R.id.listenlive_head_iv, R.id.listenlive_antion_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.listenlive_gift_head_iv:
                checkOtherHead(giftUserId);
                break;
            case R.id.listenlive_antion_tv:
                if (listenliveAntionTv.getText().toString().contains("已")) {
                    tempId = userId;
                    OkHttpUtils.postString()
                            .url(Common.Url_Cancel_BlackList + userId)
                            .tag(Common.NET_CANCEL_BLACKLIST_ID).id(Common.NET_CANCEL_BLACKLIST_ID)
                            .content(mGson.toJson(""))
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .build().execute(new MyStringCallback(mContext, ListenLiveActivity.this, true));
                } else {
                    onAddFriend(userId);
                }
                break;
            case R.id.listenlive_invitation_iv://邀请好友
                OkHttpUtils.get().url(Common.Url_Get_FollowMe_Post).id(Common.NET_GET_FOLLOWME_ID)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_GET_FOLLOWME_ID).build()
                        .execute(new MyStringCallback(mContext, this, true));
                break;
            case R.id.listenlive_flowrs_iv:
                waitUpNum += 1;
                int LikeNum = Integer.valueOf(listenliveKissnumTv.getText().toString().trim());
                listenliveKissnumTv.setText(" " + (LikeNum + 1));
                periscope.addHeart();
                break;
            case R.id.listenlive_head_iv:
                checkOtherHead(mBodyBean.getUser_id());
                break;
            case R.id.listenlive_bg_iv:
                if (isIMOpen()) {
                    cancelIM(view);
                }
                blogdetailAddemolIv.setImageResource(R.mipmap.chat_face_ic);
                blogdetailAddcommentEt.setVisibility(View.VISIBLE);
                llFacechoose.setVisibility(View.GONE);
                break;
            case R.id.listenlive_back_iv:
                showWarnExit();
                break;
            case R.id.listenlive_gift_iv:
                startActivityForResult(SelectGiftActivity.buildIntent(mContext, String.valueOf(mMyUserInfo.getUser_id()), true, 2)
                        , Common.toSelectGift);
                break;
            case R.id.blogdetail_addemol_iv:
                if (llFacechoose.getVisibility() == View.GONE) {
                    if (isIMOpen()) {
                        cancelIM(view);
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
                handler.postDelayed(runnable, 200);
                break;
            case R.id.p2pchat_pic_iv:
                if (!isCanSeed()) {
                    showWraning("主播请你" + diTime + "后再发言");
                    return;
                }
                selectImg();
                break;
            case R.id.p2pchat_send_bt:
                if (!isCanSeed()) {
                    showWraning("主播请你" + diTime + "后再发言");
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
                if (isIMOpen()) {
                    cancelIM(view);
                }
                llFacechoose.setVisibility(View.GONE);
                blogdetailAddemolIv.setImageResource(R.mipmap.chat_face_ic);
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

    private Animation animation = null;

    private void GiftAnimation(String icon, String nick, String giftName, String url) {

        listenliveGiftRl.clearAnimation();
        listenliveGiftRl.setVisibility(View.GONE);

        listenliveGiftHeadIv.setImageURI(Common.ImageUrl+icon);
        listenliveGiftNameTv.setText(nick);

        listenliveGiftContentTv.setText(giftName);

        if (animation == null) {
            animation = AnimationUtils.loadAnimation(mContext, R.anim.gift_anim);
        }
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                listenliveGiftRl.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                myliveroomGiftRl.clearAnimation();
//                myliveroomGiftRl.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        listenliveGiftInnerIv.setImageURI(url);
        listenliveGiftRl.startAnimation(animation);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.toSelectGift) {
            giftId = String.valueOf(data.getIntExtra("giftId", 0));
            String str = "【" + mMyUserInfo.getNick_name() + "】赠送主播礼物：" + data.getStringExtra("name");
            String url = Common.ImageUrl + data.getStringExtra("url");
            giftUserId = mMyUserInfo.getUser_id();
            GiftAnimation(mMyUserInfo.getIcon(), mMyUserInfo.getNick_name(), data.getStringExtra("name"), url);
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
                    if (!imageUri.getPath().startsWith("/storage")) {
                        imageUri = Uri.parse("file:///" + ContentUriUtil.getPath(mContext, imageUri));
                    }
                    try {
                        File temp = BitmapTools.revitionImage(mContext, imageUri);
                        if (temp == null) {
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
        mUserMap.put("verify_status", mMyUserInfo.getVerify_status());
        mUserMap.put("nickName", mMyUserInfo.getNick_name());
        mUserMap.put("icon", mMyUserInfo.getIcon());
        mUserMap.put("sex", mMyUserInfo.getSex());
        mUserMap.put("userId", mMyUserInfo.getUser_id());
        mUserMap.put("userLevel", mMyUserInfo.getUserLevel());
        Map<String, Object> mMap = new HashMap<>();
        mMap.put("isAnchorImage", 0);
        mMap.put("user", mUserMap);
        mMap.put("sendTime", me.getTime());
        if (type == 4) {
            mMap.put("cmdType", 1);
        }
        if (Integer.parseInt(giftId) > 0) {
            mMap.put("type", 3);
        } else {
            mMap.put("type", type);
        }
        mMap.put("content", text);
        mMap.put("id", chatRoomId);
        mMap.put("giftId", giftId);

        // 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。
        ImMessage mImMessage = null;
        switch (type) {
            case ChatRoomTypeInter.ChatRoomTypeText:
                me.setContent(mGson.toJson(mMap));
                if (Integer.parseInt(giftId) > 0) {
                    mImMessage = new ImMessage(null, me.getUuid()
                            , String.valueOf(mMyUserInfo.getUser_id())
                            , me.getSessionId()
                            , me.getFromAccount()
                            , mMyUserInfo.getNick_name()
                            , mMyUserInfo.getIcon()
                            , mMyUserInfo.getSex()
                            , mMyUserInfo.getVerify_status()
                            , true, me.getTime()
                            , ChatRoomTypeInter.ChatRoomTypeText
                            , text, "", "", "", "", "", "", "", "", 0, 0, true, 1);
                } else {
                    mImMessage = new ImMessage(null, me.getUuid()
                            , String.valueOf(mMyUserInfo.getUser_id())
                            , me.getSessionId()
                            , me.getFromAccount()
                            , mMyUserInfo.getNick_name()
                            , mMyUserInfo.getIcon()
                            , mMyUserInfo.getSex()
                            , mMyUserInfo.getVerify_status()
                            , true, me.getTime()
                            , ChatRoomTypeInter.ChatRoomTypeText
                            , text, "", "", "", "", "", "", "", "", 0, 0, false, 1);
                }
                mAdapter.addImMessageDao(mImMessage);
                break;
            case ChatRoomTypeInter.ChatRoomTypeImage:
                me.setRemoteExtension(mMap);
                mImMessage = new ImMessage(null, me.getUuid()
                        , String.valueOf(mMyUserInfo.getUser_id())
                        , me.getSessionId()
                        , me.getFromAccount()
                        , mMyUserInfo.getNick_name()
                        , mMyUserInfo.getIcon()
                        , mMyUserInfo.getSex()
                        , mMyUserInfo.getVerify_status()
                        , true, me.getTime()
                        , ChatRoomTypeInter.ChatRoomTypeImage
                        , "[图片]", url, url, url, "", "", "", "", "", 0, 0, false, 1);
                mAdapter.addImMessageDao(mImMessage);
                break;
            case ChatRoomTypeInter.ChatRoomTypeAudio:
                me.setRemoteExtension(mMap);
                String urlAudio = ((AudioAttachment) me.getAttachment()).getUrl();
                String pathAudio = ((AudioAttachment) me.getAttachment()).getPathForSave();
                long Duration = ((AudioAttachment) me.getAttachment()).getDuration();
                mImMessage = new ImMessage(null, me.getUuid()
                        , String.valueOf(mMyUserInfo.getUser_id())
                        , me.getSessionId()
                        , me.getFromAccount()
                        , mMyUserInfo.getNick_name()
                        , mMyUserInfo.getIcon()
                        , mMyUserInfo.getSex()
                        , mMyUserInfo.getVerify_status()
                        , true, me.getTime()
                        , ChatRoomTypeInter.ChatRoomTypeAudio
                        , "[语音]", "", "", "", "", "", "", pathAudio, urlAudio, Duration / 1000, 0, false, 1);
                mAdapter.addImMessageDao(mImMessage);
                break;
            default:
                me.setContent(mGson.toJson(mMap));
                me.setRemoteExtension(mMap);
                break;
        }
        NIMClient.getService(ChatRoomService.class).sendMessage(me, true);
    }

    @Override
    public void onItem(View v, int position) {
        switch (v.getId()) {
            case R.id.item_p2pchat_noti_tx:
            case R.id.item_p2pchat_text_headleft_iv:
                checkOtherHead(Long.parseLong(mAdapter.getItem(position).getUserId()));
                break;
            case R.id.item_p2pchat_root_Rl:
                if (isIMOpen()) {
                    cancelIM(v);
                }
                blogdetailAddemolIv.setImageResource(R.mipmap.chat_face_ic);
                blogdetailAddcommentEt.setVisibility(View.VISIBLE);
                llFacechoose.setVisibility(View.GONE);
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
    public void isNull(boolean b) {
        if (b) {
            p2pchatSendBt.setVisibility(View.GONE);
            p2pchatPicIv.setVisibility(View.VISIBLE);
        } else {
            p2pchatSendBt.setVisibility(View.VISIBLE);
            p2pchatPicIv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEditClick() {
        blogdetailAddemolIv.setImageResource(R.mipmap.chat_face_ic);
    }

    private long tempId;

    @Override
    public void onAddFriend(long userId) {
        tempId = userId;
        Map<String, Long> p = new HashMap<>();
        p.put("follow_user_id", userId);
        OkHttpUtils.postString().url(Common.Url_Add_Follow).content(mGson.toJson(p))
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .mediaType(Common.JSON).tag(Common.NET_ADD_FOLLOW_ID)
                .id(Common.NET_ADD_FOLLOW_ID).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    public void onDeleteFriend(long id) {
        OkHttpUtils.postString()
                .url(Common.Url_Cancel_MyConcernList + id)
                .tag(Common.NET_CANCEL_MYCONCERNLIST_ID).id(Common.NET_CANCEL_MYCONCERNLIST_ID)
                .content(mGson.toJson(""))
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .build().execute(new MyStringCallback(mContext, ListenLiveActivity.this, true));
    }

    @Override
    public void onGag(long userId, String nick) {
    }

    @Override
    public void onMore(long userId, PopupWindow ob) {
        showBottomImg(userId, ob);
    }

    private void showBottomImg(final long id, final PopupWindow ob) {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        String[] str = new String[]{"举报", "把TA加入黑名单"};
        if (mIsBalck) {
            str[1] = "从黑名单移除";
        }
        builder.setItems(str, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                if (which == 0) {//举报
                    startActivity(ReportListActivity.buildIntent(mContext, id, 1));
//                    startActivity(ReportActivity.buildIntent(mContext, id, 1));
                } else if (which == 1) {//把TA加入黑名单
                    ob.dismiss();
                    if (mIsBalck) {
                        OkHttpUtils.postString()
                                .url(Common.Url_Cancel_BlackList + id)
                                .tag(Common.NET_CANCEL_BLACKLIST_ID).id(Common.NET_CANCEL_BLACKLIST_ID)
                                .content(mGson.toJson(""))
                                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                .build().execute(new MyStringCallback(mContext, ListenLiveActivity.this, true));
                    } else {
                        OkHttpUtils.postString().url(Common.Url_Add_BlackList)
                                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                .content("{\"black_user_id\":" + id + "}")
                                .mediaType(Common.JSON).id(Common.NET_ADD_BLACKLIST).tag(Common.NET_ADD_BLACKLIST)
                                .build().execute(new MyStringCallback(mContext, ListenLiveActivity.this, true));
                    }
                }
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

    private String diTime = "15分钟";

    private boolean isCanSeed() {
        boolean iscan = false;
        long startTime = PreferenceUtil.getLongPreferences(mContext, PreferenceUtil.PARM_GAG_TIME);
        long endTime = System.currentTimeMillis();
        diTime = MyTools.getTwoTime15Count(startTime, endTime);
        LogUtils.e("MyTools.getGapCountM(startTime, endTime):" + MyTools.getGapCountM(startTime, endTime));
        if (startTime == 0 || MyTools.getGapCountM(startTime, endTime) > 15) {
            iscan = true;
        }
        return iscan;
    }

    private Timer mTimer = new Timer();
    private int n = 0;

    private void addFlowrs(final int disNum) {
        if (disNum == 0)
            return;
        int sleepTime = disTime / disNum;
        if (sleepTime > 1000) {
            sleepTime = 1000;
        } else if (sleepTime < 200) {
            sleepTime = 200;
        }
        n = 0;
        if (mTimer==null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (n >= disNum) {
                    mTimer.cancel();
                    mTimer.purge();
                    mTimer = null;
                    return;
                }
                n++;
                Message message = new Message();
                message.what = 1;
                mHanlder.sendMessage(message);
            }
        }, 0, sleepTime);
    }

    int disTime = 30000;

    private void startGetOnLine() {
        Message message = new Message();
        message.what = 0;
        mHanlder.sendMessageDelayed(message, disTime);
    }

    Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    OkHttpUtils.postString().url(Common.Url_Get_Like_Post + userId + "/" + waitUpNum).content("{}")
                            .id(Common.NET_GET_LIVE_LIKE_ID).tag(Common.NET_GET_LIVE_LIKE_ID)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie()).build()
                            .execute(new MyStringCallback(mContext, ListenLiveActivity.this, false));
                    waitUpNum = 0;
                    break;
                case 1:
                    periscope.addHeart();
                    break;
            }
        }
    };

    @Override
    public void onItemClick(View view, int position) {
        checkOtherHead(onLineAdapter.getItemData(position).getUser_id());
    }

    private long giftUserId;
    private void checkOtherHead(long UserId) {
        if (UserId == MyBaseApplication.getApplication()
                .mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getUser_id()) {
            return;
        }
        OkHttpUtils.get().url(Common.Url_Get_UserIndex + "/" + UserId)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_USERINDEX).id(Common.NET_GET_USERINDEX).build()
                .execute(new MyStringCallback(mContext, this, true));
    }
}
