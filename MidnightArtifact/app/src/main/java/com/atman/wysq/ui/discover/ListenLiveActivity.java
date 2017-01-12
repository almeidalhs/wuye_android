package com.atman.wysq.ui.discover;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.ChatRoomAdapter;
import com.atman.wysq.model.bean.ImMessage;
import com.atman.wysq.model.response.ChatRoomMessageModel;
import com.atman.wysq.model.response.GetLiveHallModel;
import com.atman.wysq.model.response.ListenLiveRoomInfoModel;
import com.atman.wysq.ui.PictureBrowsingActivity;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.yunxinfriend.OtherPersonalActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.yunxin.model.ChatRoomTypeInter;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/1/11.
 */

public class ListenLiveActivity extends MyBaseActivity implements lsMessageHandler
        , ChatRoomAdapter.RoomAdapterInter {

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

    private Context mContext = ListenLiveActivity.this;
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

    private ListenLiveRoomInfoModel mListenLiveRoomInfoModel;

    private NELivePlayer mMediaPlayer = null;
    private ChatRoomAdapter mAdapter;
    private Observer<List<ChatRoomMessage>> incomingChatRoomMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listenlive);
        ButterKnife.bind(this);
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

        initListenLive();

        initChatRoom();
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
                        LogUtils.e(">>>>>>>>:"+messages.get(i).getRemoteExtension());
                        if (messages.get(i).getRemoteExtension()!=null) {
                            temp = mGson.fromJson(mGson.toJson(messages.get(i).getRemoteExtension())
                                    , ChatRoomMessageModel.class);
                        }
                    }
                    ImMessage mImMessage = null;
                    int n = Integer.parseInt(listenliveNumTv.getText().toString());
                    LogUtils.e("n:"+n);
                    if (temp!=null) {
                        if (temp.getType() == ChatRoomTypeInter.ChatRoomTypeSystem) {
                            listenliveNumTv.setText(String.valueOf(n+1));
                        } else if (temp.getType() == ChatRoomTypeInter.ChatRoomTypeSystemCMD) {
                            if (n-1<0) {
                                listenliveNumTv.setText("0");
                            } else {
                                listenliveNumTv.setText(String.valueOf(n-1));
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
                                if (temp.getIsAnchorImage()==1) {
                                    listenliveBgIv.setImageURI(url);
                                }
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
                            mAdapter.addImMessageDao(mImMessage);
                        }
                    }
                }
            }
        };

        receiveRegister(true);
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
    NELivePlayer.OnInfoListener mInfoListener = new NELivePlayer.OnInfoListener(){

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
            showToast("播放出错");
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
        OkHttpUtils.postString().url(Common.Url_Live_Money + roomId +"/"+listenTime)
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

            setLiveNum(mListenLiveRoomInfoModel.getBody().getMember_count()+"");
        } else if (id == Common.NET_LIVE_USERLOG_ID) {

        } else if (id == Common.NET_LIVE_MONEY_ID) {

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
                receiveRegister(false);
                mMediaPlayer.release();
                finish();
            }
        });
        builder.show();
    }

    @OnClick({R.id.listenlive_bg_iv, R.id.listenlive_pic_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.listenlive_bg_iv:
                showWarnExit();
                break;
            case R.id.listenlive_pic_iv:
                break;
        }
    }

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

    @Override
    public void handleMessage(int i, Object o) {

    }
}
