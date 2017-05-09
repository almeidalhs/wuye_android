package com.atman.wysq.ui.community;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.CreateVoicePostAdapter;
import com.atman.wysq.model.request.CreateMultiMediaPostModel;
import com.atman.wysq.model.response.GoodsListModel;
import com.atman.wysq.model.response.HeadImgResultModel;
import com.atman.wysq.model.response.HeadImgSuccessModel;
import com.atman.wysq.model.response.UpVoiceModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.UiHelper;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.PromptDialog;
import com.choicepicture_library.ImageGridActivity;
import com.choicepicture_library.tools.Bimp;
import com.netease.nimlib.sdk.media.player.AudioPlayer;
import com.netease.nimlib.sdk.media.player.OnPlayListener;
import com.netease.nimlib.sdk.media.record.AudioRecorder;
import com.netease.nimlib.sdk.media.record.IAudioRecordCallback;
import com.netease.nimlib.sdk.media.record.RecordType;
import com.tbl.okhttputils.OkHttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/4/6.
 */

public class CreateVoicePostActivity extends MyBaseActivity implements AdapterInterface
        ,View.OnClickListener, IAudioRecordCallback {

    @Bind(R.id.create_voicepost_lv)
    ListView createVoicepostLv;

    private Context mContext = CreateVoicePostActivity.this;

    private CreateVoicePostAdapter mAdapter;
    private View headView;
    private MyCleanEditText postTitleEt;
    private ImageView postBgIv;
    private Button postListeningtestBt, postRecordingBt;
    private TextView postTimeTx,postChangeImgTx;
    private RelativeLayout partPostRl;
    private EditText mFocusEditText;

    private LinearLayout.LayoutParams paramsBg;

    private boolean isRecording = false;
    protected AudioRecorder audioMessageHelper;
    private final int maxDuration = 60;
    private boolean started = false;
    private Chronometer postTimer;
    private int time;
    private File voiceFile;
    private String imgPath;
    private AudioPlayer player;
    private String path = "";
    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;

    private int mAnonymity = 0;
    private String upImgUrl = "";
    private String upVoiceUrl;
    private String upTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_voicepost);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("发布语音");

        setBarRightIv(R.mipmap.bt_create_ok);
        getBarRightRl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upTitle = postTitleEt.getText().toString().trim();
                if (upTitle.isEmpty()) {
                    showToast("请输入标题");
                    return;
                }
                if (voiceFile==null || !voiceFile.exists()) {
                    showToast("请进行录音");
                    return;
                }
                if (time<5) {
                    showToast("录音时间过短");
                    return;
                }
                showWarn();
            }
        });
        getBarBackLl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDonot();
            }
        });
        getBarBackIv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDonot();
            }
        });

        initListview();
    }

    private void initListview() {
        int w = getmWidth()- DensityUtil.dp2px(mContext, 20);
        paramsBg = new LinearLayout.LayoutParams(w, w*3/5);

        headView = LayoutInflater.from(mContext).inflate(R.layout.part_voice_post_head_view, null);
        partPostRl = (RelativeLayout) headView.findViewById(R.id.part_post_rl);
        postTitleEt = (MyCleanEditText) headView.findViewById(R.id.post_title_et);
        postBgIv = (ImageView) headView.findViewById(R.id.post_bg_iv);
        postTimeTx = (TextView) headView.findViewById(R.id.post_time_tx);
        postListeningtestBt = (Button) headView.findViewById(R.id.post_listeningtest_bt);
        postRecordingBt = (Button) headView.findViewById(R.id.post_recording_bt);
        postChangeImgTx = (TextView) headView.findViewById(R.id.post_change_img_tx);
        postTimer = (Chronometer) headView.findViewById(R.id.post_timer);

        postListeningtestBt.setOnClickListener(this);
        postRecordingBt.setOnClickListener(this);
        postChangeImgTx.setOnClickListener(this);
        postTitleEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditFocus(postTitleEt, 0);
            }
        });
        postTitleEt.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setEditFocus(postTitleEt, 0);
                }
            }
        });

        partPostRl.setLayoutParams(paramsBg);

        mAdapter = new CreateVoicePostAdapter(mContext, this);
        createVoicepostLv.addHeaderView(headView, null, true);
        createVoicepostLv.setHeaderDividersEnabled(false);
        createVoicepostLv.setAdapter(mAdapter);
    }

    private void setEditFocus(EditText postTitleEt, int i) {
        mFocusEditText = postTitleEt;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getIntExtra("Goods_id", -1)!=-1 && mAdapter!=null && MyBaseApplication.isRelation>0) {
            GoodsListModel temp = new GoodsListModel(getIntent().getIntExtra("Goods_id", -1)
                    , getIntent().getStringExtra("Pic_img"), getIntent().getStringExtra("Title")
                    , getIntent().getStringExtra("Discount_price"));
            MyBaseApplication.isRelation = 0;
            mAdapter.clearData();
            mAdapter.addBody(MyBaseApplication.creatPostGoods);
            boolean isRelationed = false;
            for (int i=0;i<MyBaseApplication.creatPostGoods.size();i++) {
                if (MyBaseApplication.creatPostGoods.get(i).getGoods_id()==temp.getGoods_id()) {
                    isRelationed = true;
                    showToast("此商品已关联");
                    break;
                }
            }
            if (!isRelationed) {
                mAdapter.addBody(temp);
            }
            displayImg(MyBaseApplication.videoImg);
            postTitleEt.setText(MyBaseApplication.imagetextPostTitle);
            if (!MyBaseApplication.voicePath.isEmpty()
                    && new File(MyBaseApplication.voicePath).exists()) {
                voiceFile = new File(MyBaseApplication.voicePath);
                time = MyBaseApplication.voiceLength;
                postListeningtestBt.setClickable(true);
                postListeningtestBt.setEnabled(true);
                String str = "00:"+time;
                if (time<10) {
                    str = "00:0"+time;
                }
                postTimer.setText(str);
                postRecordingBt.setText("重录");
            }
            getIntent().removeExtra("Goods_id");
        }
    }

    private void displayImg(String path) {
        Bitmap bm = null;
        imgPath = path;
        try {
            bm = Bimp.revitionImageSize(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        postBgIv.setImageBitmap(bm);
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        if (id == Common.NET_RESET_HEAD) {
            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            if (mHeadImgResultModel!=null && mHeadImgResultModel.getFiles().size()>0 ) {
                if (!mHeadImgResultModel.getFiles().get(0).isSuccessful()) {
                    showToast("封面图片修改失败");
                    cancelLoading();
                } else {
                    HeadImgSuccessModel mHeadImgSuccessModel = mGson.fromJson(data, HeadImgSuccessModel.class);
                    upImgUrl = mHeadImgSuccessModel.getFiles().get(0).getUrl();
                    upVoice();
                }
            }
        } else if (id == Common.NET_UP_VOICE_ID) {
            UpVoiceModel mUpVoiceModel = mGson.fromJson(data, UpVoiceModel.class);

            if (mUpVoiceModel.getFiles().size()==0
                    || !mUpVoiceModel.getFiles().get(0).isSuccessful()) {
                showToast("上传失败");
                cancelLoading();
                return;
            }

            upVoiceUrl = mUpVoiceModel.getFiles().get(0).getUrl();

            List<Map<String, Integer>> maps = new ArrayList<>();
            for (int i=0;i<mAdapter.getGoodsList().size();i++) {
                Map<String, Integer> map = new HashMap<>();
                map.put("goods_id", mAdapter.getGoodsList().get(i).getGoods_id());
                maps.add(map);
            }

            CreateMultiMediaPostModel temp = new CreateMultiMediaPostModel(upImgUrl, upVoiceUrl
                    , upTitle, time, mAnonymity, maps);
            OkHttpUtils.postString().url(Common.Url_Create_Audio_Post)
                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .mediaType(Common.JSON).content(mGson.toJson(temp))
                    .id(Common.NET_CREATE_AUDIO_POST_ID).tag(Common.NET_CREATE_AUDIO_POST_ID)
                    .build().execute(new MyStringCallback(mContext, this, true));
        } else if (id == Common.NET_CREATE_AUDIO_POST_ID) {
            cancelLoading();
            showToast("发布成功！");
            finish();
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        if (id == Common.NET_UP_VOICE_ID) {
            cancelLoading();
        } else {
            super.onError(call, e, code, id);
        }
    }

    private void upVoice() {
        OkHttpUtils.post().url(Common.Url_Reset_Head).addParams("uploadType", "aac")
                .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                .addFile("files0_name", StringUtils.getFileName(voiceFile.getPath()), voiceFile)
                .id(Common.NET_UP_VOICE_ID).tag(Common.NET_UP_VOICE_ID).build()
                .connTimeOut(Common.timeOut).readTimeOut(Common.timeOut).writeTimeOut(Common.timeOut)
                .execute(new MyStringCallback(mContext, CreateVoicePostActivity.this, true));
    }

    private void showWarn() {
        if (imgPath!=null && !imgPath.isEmpty() && new File(imgPath).exists()) {
            BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
            builder.setItems(new String[]{"发布", "匿名发布"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (which == 0) {//发布
                        mAnonymity = 0;
                    } else {//匿名发布
                        mAnonymity = 1;
                    }

                    OkHttpUtils.post().url(Common.Url_Reset_Head).addParams("uploadType", "img")
                            .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                            .addFile("files0_name", StringUtils.getFileName(imgPath), new File(imgPath))
                            .id(Common.NET_RESET_HEAD).tag(Common.NET_RESET_HEAD).build()
                            .execute(new MyStringCallback(mContext, CreateVoicePostActivity.this, true));
                }
            });
            builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        } else {
            showToast("请选择一张图片作为封面");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player!=null) {
            player.stop();
        }
        if (audioMessageHelper != null) {
            overRecording(false);
            audioMessageHelper.completeRecord(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clear();

        OkHttpUtils.getInstance().cancelTag(Common.NET_RESET_HEAD);
        OkHttpUtils.getInstance().cancelTag(Common.NET_UP_VOICE_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CREATE_AUDIO_POST_ID);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_childtwo_delete_iv:
                mAdapter.deleteDataById(position);
                break;
            case R.id.item_childtwo_add_ll:
                MyBaseApplication.creatPostGoods.clear();
                MyBaseApplication.creatPostGoods.addAll(mAdapter.getGoodsList());
                mAdapter.clearData();
                MyBaseApplication.isRelation = 2;
                MyBaseApplication.videoImg = imgPath;
                MyBaseApplication.imagetextPostTitle = postTitleEt.getText().toString();
                if (voiceFile!=null && voiceFile.exists()) {
                    MyBaseApplication.voicePath = voiceFile.getPath();
                    MyBaseApplication.voiceLength = time;
                }
                startActivity(new Intent(mContext, MallActivity.class));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_listeningtest_bt:
                if (voiceFile==null || !voiceFile.exists()) {
                    showToast("播放文件不存在");
                    return;
                }
                if (player==null) {
                    player = new AudioPlayer(mContext);
                }
                if (player.isPlaying()) {
                    player.stop();
                    postListeningtestBt.setText("试听");
                    postRecordingBt.setClickable(true);
                    postRecordingBt.setEnabled(true);
                    return;
                }
                player.setDataSource(voiceFile.getPath());
                player.setOnPlayListener(new OnPlayListener() {
                    @Override
                    public void onPrepared() {
                        postTimeTx.setVisibility(View.VISIBLE);
                        postTimer.setVisibility(View.GONE);
                        postTimeTx.setText("00/"+time);
                        postListeningtestBt.setText("停止");
                        postRecordingBt.setClickable(false);
                        postRecordingBt.setEnabled(false);
                    }

                    @Override
                    public void onCompletion() {
                        postTimeTx.setVisibility(View.GONE);
                        postTimer.setVisibility(View.VISIBLE);
                        postListeningtestBt.setText("试听");
                        postRecordingBt.setClickable(true);
                        postRecordingBt.setEnabled(true);
                    }

                    @Override
                    public void onInterrupt() {
                    }

                    @Override
                    public void onError(String s) {
                        showToast("播放出错");
                        postListeningtestBt.setText("试听");
                        postRecordingBt.setClickable(true);
                        postRecordingBt.setEnabled(true);
                    }

                    @Override
                    public void onPlaying(long l) {
                        String str = "";
                        if ((l/1000)<10) {
                            str = "0"+String.valueOf(l/1000);
                        } else {
                            str = String.valueOf(l/1000);
                        }
                        if (time<10) {
                            str = str + "/0" + time;
                        } else {
                            str = str + "/" + time;
                        }
                        postTimeTx.setText(str);
                    }
                });
                player.start(AudioManager.STREAM_MUSIC);
                break;
            case R.id.post_recording_bt:
                if (!isRecording) {
                    postTimeTx.setVisibility(View.GONE);
                    postTimer.setVisibility(View.VISIBLE);
                    postRecordingBt.setText("停止");
                    isRecording = true;
                    if (voiceFile!=null && voiceFile.exists()) {
                        voiceFile.delete();
                    }
                    initAudioRecord();
                    onStartAudioRecord();
                } else {
                    overRecording(true);
                    audioMessageHelper.completeRecord(true);
                }
                break;
            case R.id.post_change_img_tx:
                changeImg();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyBaseApplication.getApplication().setFilterLock(false);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
            for (int i=0;i<Bimp.drr.size();i++) {
                imgPath = Bimp.drr.get(i);
                Bitmap bm = null;
                try {
                    bm = Bimp.revitionImageSize(Bimp.drr.get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                postBgIv.setImageBitmap(bm);
            }
        } else if (requestCode == TAKE_BIG_PICTURE) {
            if (!path.isEmpty()) {
                imgPath = path;
                Bitmap bm = null;
                try {
                    bm = Bimp.revitionImageSize(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                postBgIv.setImageBitmap(bm);
            }
        }
    }

    private void changeImg() {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setTitle(Html.fromHtml("<font color=\"#f9464a\">更换封面</font>"));
        builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MyBaseApplication.getApplication().setFilterLock(true);
                if (which == 0) {//拍照
                    path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
                } else {//选择照片
                    Bimp.max = 1;
                    clear();
                    startActivityForResult(new Intent(mContext, ImageGridActivity.class), CHOOSE_BIG_PICTURE);
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

    private void clear() {
        Bimp.num = 0;
        Bimp.drr.clear();
        Bimp.drr_or.clear();
        Bimp.bmp.clear();
    }

    private void overRecording(boolean isNormal) {
        postTimer.stop();
        isRecording = false;
        postRecordingBt.setText("重录");
        if (isNormal) {
            time = (int) (SystemClock.elapsedRealtime() - postTimer.getBase())/1000;// 保存这次记录了的时间
        }
        if (voiceFile!=null && voiceFile.exists()) {
            postListeningtestBt.setClickable(true);
            postListeningtestBt.setEnabled(true);
        } else {
            postListeningtestBt.setClickable(false);
            postListeningtestBt.setEnabled(false);
        }
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
        if (started == false) {
            showToast("初始化录音失败");
            PromptDialog.Builder builder = new PromptDialog.Builder(mContext);
            builder.setMessage("您的录音权限未开启，请至“权限管理”中设置!");
            builder.setPositiveButton("去设置", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(UiHelper.getAppDetailSettingIntent(mContext));
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            overRecording(true);
            return;
        }
        postTimer.setBase(SystemClock.elapsedRealtime());
        postTimer.start();

        postListeningtestBt.setClickable(false);
        postListeningtestBt.setEnabled(false);
    }

    @Override
    public void onRecordReady() {

    }

    @Override
    public void onRecordStart(File file, RecordType recordType) {
        voiceFile = file;
    }

    @Override
    public void onRecordSuccess(File file, long l, RecordType recordType) {
        voiceFile = file;
    }

    @Override
    public void onRecordFail() {
    }

    @Override
    public void onRecordCancel() {
    }

    @Override
    public void onRecordReachedMaxTime(int i) {
        overRecording(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 返回键
            showDonot();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showDonot() {
        PromptDialog.Builder builder = new PromptDialog.Builder(this);
        builder.setMessage("确定取消发布？");
        builder.setPositiveButton("不发了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("继续编辑", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
