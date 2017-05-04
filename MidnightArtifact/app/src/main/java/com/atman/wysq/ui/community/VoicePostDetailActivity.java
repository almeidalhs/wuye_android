package com.atman.wysq.ui.community;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atman.wysq.R;
import com.atman.wysq.adapter.BlogDetailGoodsListAdapter;
import com.atman.wysq.adapter.PostingsDetailsCommentAdapter;
import com.atman.wysq.adapter.RewardGridViewAdapter;
import com.atman.wysq.model.danmaku.MyDanmakuModel;
import com.atman.wysq.model.request.AddCommentModel;
import com.atman.wysq.model.response.AddCommentResultModel;
import com.atman.wysq.model.response.DanmakuModel;
import com.atman.wysq.model.response.GetBlogDetailModel;
import com.atman.wysq.model.response.GetPostingsDetailsCommentListModel;
import com.atman.wysq.model.response.GetUserIndexModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.mall.GoodsDetailActivity;
import com.atman.wysq.ui.yunxinfriend.OtherPersonalActivity;
import com.atman.wysq.ui.yunxinfriend.SelectGiftActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.atman.wysq.utils.ShareHelper;
import com.atman.wysq.utils.Tools;
import com.atman.wysq.widget.ShareDialog;
import com.atman.wysq.widget.face.FaceRelativeLayout;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.MyListView;
import com.base.baselibs.widget.PromptDialog;
import com.bumptech.glide.Glide;
import com.dl7.player.media.IjkPlayerView;
import com.dl7.player.utils.CenteredImageSpan;
import com.dl7.player.utils.CircleImageDrawable;
import com.dl7.player.utils.CreateSpannableTextUtil;
import com.dl7.player.utils.DpOrSp2PxUtil;
import com.dl7.player.utils.WindowUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tbl.okhttputils.OkHttpUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by tangbingliang on 17/4/7.
 */

public class VoicePostDetailActivity extends MyBaseActivity implements AdapterInterface
        , UMShareListener {

    @Bind(R.id.blogdetail_comment_lv)
    PullToRefreshListView blogdetailCommentLv;
    @Bind(R.id.postings_totop_iv)
    ImageView postingsTotopIv;
    @Bind(R.id.blogdetail_addcomment_et)
    MyCleanEditText blogdetailAddcommentEt;
    @Bind(R.id.blogdetail_addemol_iv)
    ImageView blogdetailAddemolIv;
    @Bind(R.id.ll_facechoose)
    RelativeLayout llFacechoose;
    @Bind(R.id.blogdetail_send_bt)
    Button blogdetailSendBt;
    @Bind(R.id.player_view)
    IjkPlayerView playerView;
    @Bind(R.id.FaceRelativeLayout)
    FaceRelativeLayout FaceRelativeLayout;
    @Bind(R.id.postings_line_iv)
    ImageView postingsLineIv;
    @Bind(R.id.postings_temp_iv)
    ImageView postingsTempIv;

    private Context mContext = VoicePostDetailActivity.this;
    private ListView mListView;

    private DanmakuModel mDanmakuModel;

    private String tilte;
    private long bolgId;
    private int page = 1;
    private boolean isLast = false;
    private int mPosition = -1;

    private GetBlogDetailModel mGetBlogDetailModel;
    private PostingsDetailsCommentAdapter mAdapter;
    private GetPostingsDetailsCommentListModel mGetPostingsDetailsCommentListModel;

    private int favoriteId = 0;
    private int vipLevel = 0;
    private String favoriteStr = "收藏";
    private boolean isMy = false;
    private long blogUserId;
    private boolean isAnonymity = false;
    private int isReplay = 0;
    private String anonymityImg;
    private String imgStr = "";

    private View headView;
    private LinearLayout blogdetailGoodsLl;
    private MyListView blogdetailGoodsLv;
    private ImageView blogdetailHeadImg;
    private ImageView blogdetailSvipIv;
    private ImageView blogdetailVerifyImg;
    private ImageView blogdetailGenderImg;
    private ImageView blogdetailFlowerIv;
    private ImageView blogdetailTopVoiceStartIv;
    private SimpleDraweeView blogdetailTopVoiceBgIv;
    private TextView blogdetailVipTx;
    private TextView blogdetailNameTx;
    private TextView blogdetailLevelTx;
    private TextView bloglistTimeTx;
    private TextView blogdetailFlowerTv;
    private TextView bloglistRelationTx;
    private RelativeLayout blogdetailHeadRl;
    private RelativeLayout blogdetailTopVoiceRl;
    private GridView blogdetailFlowerGv;
    private RewardGridViewAdapter mRewardListAdapter;

    private ImageView isHeartIv;
    private GetUserIndexModel mGetMyUserIndexModel;

    private RelativeLayout.LayoutParams rlParams;
    private LinearLayout.LayoutParams llParams;
    private String mImgUrl;
    private String mAudioUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_voice_postdetail);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, String tilte, long id, boolean isMy, int vipLevel) {
        Intent intent = new Intent(context, VoicePostDetailActivity.class);
        intent.putExtra("tilte", tilte);
        intent.putExtra("id", id);
        intent.putExtra("isMy", isMy);
        intent.putExtra("vipLevel", vipLevel);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        tilte = getIntent().getStringExtra("tilte");
        bolgId = getIntent().getLongExtra("id", -1);
        vipLevel = getIntent().getIntExtra("vipLevel", 0);
        isMy = getIntent().getBooleanExtra("isMy", false);
        LogUtils.e("id:" + bolgId + ",isMy:" + isMy);

        setBarTitleTx(tilte);

        initRefreshView(PullToRefreshBase.Mode.BOTH, blogdetailCommentLv);
        mListView = blogdetailCommentLv.getRefreshableView();
        headView = LayoutInflater.from(mContext).inflate(R.layout.part_voice_postdetail_head_view, null);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    cancelIM(view);
                }
                if (llFacechoose.getVisibility() == View.VISIBLE) {
                    llFacechoose.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View c = mListView.getChildAt(0);
                if (c == null) {
                    return;
                }
                int firstVisiblePosition = mListView.getFirstVisiblePosition();
                int top = c.getTop();
                if ((headView.getHeight() >= getmHight() && (-top >= getmHight() || firstVisiblePosition > 1))
                        || (headView.getHeight() < getmHight()) && (headView.getHeight() + firstVisiblePosition * c.getHeight()) >= getmHight()) {
                    postingsTotopIv.setVisibility(View.VISIBLE);
                } else {
                    postingsTotopIv.setVisibility(View.GONE);
                }
            }
        });

        setBarRightIv(R.mipmap.postingsdetails_right_ic, true);
        getBarRightRl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomImg(v);
            }
        });

        initListView();

        blogdetailCommentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapter != null && mAdapter.getShop().size() > 0 && position > 1) {
                    GetPostingsDetailsCommentListModel.BodyEntity mBodyEntity = mAdapter.getItem(position - 2);
                    startActivity(CommentChildrenListActivity.buildIntent(mContext, mBodyEntity.getBlog_id(), mBodyEntity.getBlog_comment_id()
                            , mBodyEntity.getIcon(), mBodyEntity.getVerify_status(), mBodyEntity.getUser_name()
                            , mBodyEntity.getSex(), mBodyEntity.getUserLevel(), mBodyEntity.getCreate_time()
                            , mBodyEntity.getUser_id(), mBodyEntity.getContent(), blogUserId, isAnonymity, anonymityImg, isReplay, mBodyEntity.getVip_level()));
                }
            }
        });

        changeMyHeart();

        isHeartIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                if (favoriteId > 0) {//已收藏，点击取消收藏
                    OkHttpUtils.delete().url(Common.Url_Get_BlogCollection_Not + bolgId)
                            .id(Common.NET_GET_BLOGCOLLECTION_NOT)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .tag(Common.NET_GET_BLOGCOLLECTION_NOT).build()
                            .execute(new MyStringCallback(mContext, VoicePostDetailActivity.this, true));
                } else {//未收藏，点击收藏
                    OkHttpUtils.postString().url(Common.Url_Get_BlogCollection + bolgId)
                            .id(Common.NET_GET_BLOGCOLLECTION).content("{}").mediaType(Common.JSON)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .tag(Common.NET_GET_BLOGCOLLECTION).build()
                            .execute(new MyStringCallback(mContext, VoicePostDetailActivity.this, true));
                }
            }
        });
    }

    private void initPlayView() {
        Glide.with(this).load(mImgUrl).centerCrop().into(playerView.mPlayerThumb);
        playerView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                playerView.setVisibility(View.GONE);
                onLoad(PullToRefreshBase.Mode.BOTH, blogdetailCommentLv);
                if (WindowUtils.getScreenOrientation(VoicePostDetailActivity.this)
                        == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    playerView._toggleFullScreen();
                }
            }
        });
        playerView.init().isVideo(false)
                .enableDanmaku()
                .setVideoSource(null, mAudioUrl, null, null, null)
                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);
        playerView.setVisibility(View.VISIBLE);
        n = 0;
        playerView.start();
        onLoad(PullToRefreshBase.Mode.DISABLED, blogdetailCommentLv);
        Message message = new Message();
        message.what = 0;
        mHandler.sendMessage(message);
    }

    private int n = 0;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (playerView.getDanmakuViewStat()) {
                        Message message = new Message();
                        message.what = 1;
                        mHandler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.what = 0;
                        mHandler.sendMessage(message);
                    }
                    break;
                case 1:
                    if (n>=mDanmakuModel.getBody().size()) {
                        return;
                    }
                    final String str = mDanmakuModel.getBody().get(n).getContent();
                    ImageLoader.getInstance().displayImage(Common.ImageUrl + mDanmakuModel.getBody().get(n).getIcon()
                            , postingsTempIv
                            , MyBaseApplication.getApplication().getOptionsNot(), new ImageLoadingListener() {
                                @Override
                                public void onLoadingStarted(String s, View view) {

                                }

                                @Override
                                public void onLoadingFailed(String s, View view, FailReason failReason) {
                                    playerView.sendDanmaku(CreateSpannableTextUtil.getSpannableText(mContext
                                            , null, str, playerView.getmDanmakuTextSize()), n*1000, true);
                                    Message message = new Message();
                                    message.what = 1;
                                    mHandler.sendMessage(message);
                                    n++;
                                }

                                @Override
                                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                    playerView.sendDanmaku(CreateSpannableTextUtil.getSpannableText(mContext
                                            , bitmap, str, playerView.getmDanmakuTextSize()), n*1000, true);
                                    Message message = new Message();
                                    message.what = 1;
                                    mHandler.sendMessage(message);
                                    n++;
                                }

                                @Override
                                public void onLoadingCancelled(String s, View view) {

                                }
                            });
                    break;
            }
        }
    };

    private void changeMyHeart() {
        if (favoriteId > 0) {//已收藏
            isHeartIv = setBarRightTwoIv(R.mipmap.ic_heart_red);
        } else {//未收藏
            isHeartIv = setBarRightTwoIv(R.mipmap.ic_heart_normal);
        }
    }

    private void sendMessage(View v) {
        if (isIMOpen()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
        }

        if (llFacechoose.getVisibility() == View.VISIBLE) {
            llFacechoose.setVisibility(View.GONE);
        }
        String str = blogdetailAddcommentEt.getText().toString().trim();
        LogUtils.e(">>>>str:"+str);
        if (!str.isEmpty()) {
            AddCommentModel mAddCommentModel = new AddCommentModel(bolgId, str);
            LogUtils.e(">>>>mGson.toJson(mAddCommentModel):"+mGson.toJson(mAddCommentModel));
            OkHttpUtils.postString().url(Common.Url_Add_Comment).mediaType(Common.JSON)
                    .content(mGson.toJson(mAddCommentModel))
                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .id(Common.NET_ADD_COMMENT).tag(Common.NET_ADD_COMMENT)
                    .build().execute(new MyStringCallback(mContext, VoicePostDetailActivity.this, true));
        }
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_BlogDetail + bolgId).id(Common.NET_GET_BLOGDETAIL)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_BLOGDETAIL).build().execute(new MyStringCallback(mContext, this, true));
        doHttp(true);
    }

    private void doHttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Get_BlogDetail_CommentList + bolgId + "/" + page).id(Common.NET_GET_BLOGDETAIL_COMMENTLIST)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_BLOGDETAIL_COMMENTLIST).build().execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerView.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (playerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (playerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        playerView.configurationChanged(newConfig);
        if (playerView.ismIsFullscreen()) {
            hideTitleBar();
            FaceRelativeLayout.setVisibility(View.GONE);
            postingsLineIv.setVisibility(View.GONE);
            blogdetailCommentLv.setVisibility(View.GONE);
        } else {
            showTitleBar();
            FaceRelativeLayout.setVisibility(View.VISIBLE);
            postingsLineIv.setVisibility(View.VISIBLE);
            blogdetailCommentLv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_BLOGDETAIL) {
            mGetBlogDetailModel = mGson.fromJson(data, GetBlogDetailModel.class);
            updateUI();
            OkHttpUtils.get().url(Common.Url_Get_UserIndex + "/" + blogUserId)
                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .tag(Common.NET_GET_USERINDEX).id(Common.NET_GET_USERINDEX).build()
                    .execute(new MyStringCallback(mContext, this, true));
        } else if (id == Common.NET_GET_BLOGDETAIL_COMMENTLIST) {
            mGetPostingsDetailsCommentListModel = mGson.fromJson(data, GetPostingsDetailsCommentListModel.class);
            if (mGetPostingsDetailsCommentListModel.getBody() == null
                    || mGetPostingsDetailsCommentListModel.getBody().size() == 0) {
                if (mAdapter != null && mAdapter.getCount() > 0 && mGetPostingsDetailsCommentListModel.getBody().size() > 0) {
                    showToast("没有更多");
                }
                isLast = true;
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, blogdetailCommentLv);
            } else {
                if (mGetPostingsDetailsCommentListModel.getBody().size() < 20) {
                    isLast = true;
                } else {
                    isLast = false;
                }
                onLoad(PullToRefreshBase.Mode.BOTH, blogdetailCommentLv);
                mAdapter.addBody(mGetPostingsDetailsCommentListModel.getBody());
            }
        } else if (id == Common.NET_GET_BLOGCOLLECTION) {
            favoriteId = 1;
            changeMyHeart();
            showToast("收藏成功");
        } else if (id == Common.NET_GET_BLOGCOLLECTION_NOT) {
            favoriteId = 0;
            changeMyHeart();
            showToast("已取消收藏");
        } else if (id == Common.NET_ADD_COMMENT) {
            if (isLast) {
                AddCommentResultModel mAddCommentResultModel = mGson.fromJson(data, AddCommentResultModel.class);
                GetPostingsDetailsCommentListModel.BodyEntity body = new GetPostingsDetailsCommentListModel.BodyEntity();
                body.setContent(blogdetailAddcommentEt.getText().toString().trim());
                body.setCreate_time(System.currentTimeMillis());
                body.setIcon(MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon());
                body.setSex(MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex());
                body.setVerify_status(MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status());
                body.setUserLevel(MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getUserLevel());
                body.setUser_name(MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getNick_name());
                body.setUser_id(MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getUser_id());
                body.setBlog_comment_id(mAddCommentResultModel.getBody());
                body.setVip_level(MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level());
                body.setBlog_id(bolgId);
                mAdapter.addBody(body);
            }
            blogdetailAddcommentEt.setText("");
            showToast("评论成功");
            if (isReplay == 0) {
                Toast.makeText(mContext, "+1经验值", Toast.LENGTH_LONG).show();
                isReplay = 1;
            }
        } else if (id == Common.NET_ADD_LIKE) {
            mAdapter.setLikeById(mPosition);
        } else if (id == Common.NET_ADD_BLACKLIST) {
            showToast("已成功拉黑");
        } else if (id == Common.NET_DELETE_POST) {
            Intent mIntent = new Intent();
            mIntent.putExtra("id", id);
            setResult(RESULT_OK, mIntent);
            finish();
        } else if (id == Common.NET_GET_USERINDEX) {
            mGetMyUserIndexModel = mGson.fromJson(data, GetUserIndexModel.class);
            if (mGetMyUserIndexModel.getBody().getUserFelation() == 1) {
                bloglistRelationTx.setText("取消关注");
            } else if (mGetMyUserIndexModel.getBody().getUserFelation() == 0) {
                bloglistRelationTx.setText("＋关注");
            }
        } else if (id == Common.NET_CANCEL_MYCONCERNLIST_ID) {
            showToast("取消关注成功");
            mGetMyUserIndexModel.getBody().setUserFelation(0);
            bloglistRelationTx.setText("+关注");
            MyBaseApplication.getApplication().getDaoSession().getAddFriendRecordDao().deleteAll();
        } else if (id == Common.NET_ADD_FOLLOW_ID) {
            showToast("关注成功");
            if (mGetMyUserIndexModel.getBody().getUserFelation() == 0) {
                mGetMyUserIndexModel.getBody().setUserFelation(1);
            } else {
                mGetMyUserIndexModel.getBody().setUserFelation(3);
            }
            bloglistRelationTx.setText("取消关注");
        } else if (id == Common.NET_GET_DANMAKU_ID) {
            mDanmakuModel = mGson.fromJson(data, DanmakuModel.class);
            initPlayView();
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        page = 1;
        onLoad(PullToRefreshBase.Mode.BOTH, blogdetailCommentLv);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        page += 1;
        doHttp(false);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        page = 1;
        mAdapter.clearData();
        doHttp(false);
    }

    private void initListView() {
        rlParams = new RelativeLayout.LayoutParams(getmWidth(), getmWidth() * 240 / 430);
        llParams = new LinearLayout.LayoutParams(getmWidth(), getmWidth() * 240 / 430);

        blogdetailGoodsLl = (LinearLayout) headView.findViewById(R.id.blogdetail_goods_ll);
        blogdetailGoodsLv = (MyListView) headView.findViewById(R.id.blogdetail_goods_lv);
        blogdetailHeadImg = (ImageView) headView.findViewById(R.id.blogdetail_head_img);
        blogdetailVerifyImg = (ImageView) headView.findViewById(R.id.blogdetail_verify_img);
        blogdetailGenderImg = (ImageView) headView.findViewById(R.id.blogdetail_gender_img);
        blogdetailSvipIv = (ImageView) headView.findViewById(R.id.blogdetail_svip_iv);
        blogdetailNameTx = (TextView) headView.findViewById(R.id.blogdetail_name_tx);
        blogdetailVipTx = (TextView) headView.findViewById(R.id.blogdetail_vip_tx);
        bloglistRelationTx = (TextView) headView.findViewById(R.id.bloglist_relation_tx);
        blogdetailLevelTx = (TextView) headView.findViewById(R.id.blogdetail_level_tx);
        bloglistTimeTx = (TextView) headView.findViewById(R.id.bloglist_time_tx);
        blogdetailHeadRl = (RelativeLayout) headView.findViewById(R.id.blogdetail_head_rl);
        blogdetailHeadRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGetBlogDetailModel != null && mGetBlogDetailModel.getBody().get(0).getAnonymityUser() == null) {
                    if (MyBaseApplication.getApplication().mGetMyUserIndexModel != null && mGetBlogDetailModel.getBody().get(0).getUser_id() ==
                            MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId()) {
                        showWraning("亲，这是你自己哦！");
                        return;
                    }
                    startActivity(OtherPersonalActivity.buildIntent(mContext, mGetBlogDetailModel.getBody().get(0).getUser_id()));
                }
            }
        });
        blogdetailTopVoiceRl = (RelativeLayout) headView.findViewById(R.id.blogdetail_top_voice_rl);
        blogdetailTopVoiceRl.setLayoutParams(llParams);
        blogdetailTopVoiceBgIv = (SimpleDraweeView) headView.findViewById(R.id.blogdetail_top_voice_bg_iv);
        blogdetailTopVoiceBgIv.setLayoutParams(rlParams);
        blogdetailTopVoiceStartIv = (ImageView) headView.findViewById(R.id.blogdetail_top_voice_start_iv);
        blogdetailTopVoiceStartIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.get().url(Common.Url_Get_Danmaku + bolgId).id(Common.NET_GET_DANMAKU_ID)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_GET_DANMAKU_ID).build()
                        .execute(new MyStringCallback(mContext, VoicePostDetailActivity.this, true));
            }
        });
        blogdetailFlowerGv = (GridView) headView.findViewById(R.id.blogdetail_flower_gv);
        blogdetailFlowerTv = (TextView) headView.findViewById(R.id.blogdetail_flower_tv);
        blogdetailFlowerIv = (ImageView) headView.findViewById(R.id.blogdetail_flower_iv);
        blogdetailFlowerIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    showLogin();
                } else {
                    if (mGetBlogDetailModel.getBody().get(0).getUser_id()
                            == MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId()) {
                        showToast("亲，这是你自己的帖子哦");
                    } else {
                        startActivityForResult(SelectGiftActivity.buildIntent(mContext
                                , String.valueOf(bolgId), false, 1), Common.toSelectGift);
                    }
                }
            }
        });

        bloglistRelationTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                if (blogUserId == MyBaseApplication.getApplication().mGetMyUserIndexModel
                        .getBody().getUserDetailBean().getUserId()) {
                    showWraning("亲，这是你自己哦！");
                    return;
                }
                if (mGetMyUserIndexModel.getBody().getUserFelation() == 1
                        || mGetMyUserIndexModel.getBody().getUserFelation() == 3) {
                    PromptDialog.Builder builder = new PromptDialog.Builder(mContext);
                    builder.setMessage("确定取消关注TA吗?");
                    builder.setPositiveButton("不了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消关注", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            OkHttpUtils.postString()
                                    .url(Common.Url_Cancel_MyConcernList + blogUserId)
                                    .tag(Common.NET_CANCEL_MYCONCERNLIST_ID).id(Common.NET_CANCEL_MYCONCERNLIST_ID)
                                    .content(mGson.toJson(""))
                                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                    .build().execute(new MyStringCallback(mContext, VoicePostDetailActivity.this, true));
                        }
                    });
                    builder.show();
                } else {
                    Map<String, Long> p = new HashMap<>();
                    p.put("follow_user_id", blogUserId);
                    OkHttpUtils.postString().url(Common.Url_Add_Follow).content(mGson.toJson(p))
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .mediaType(Common.JSON)
                            .tag(Common.NET_ADD_FOLLOW_ID).id(Common.NET_ADD_FOLLOW_ID).build()
                            .execute(new MyStringCallback(mContext, VoicePostDetailActivity.this, true));
                }
            }
        });

        mAdapter = new PostingsDetailsCommentAdapter(mContext, this);
        blogdetailCommentLv.getRefreshableView().addHeaderView(headView, null, true);
        blogdetailCommentLv.getRefreshableView().setHeaderDividersEnabled(false);
        blogdetailCommentLv.setAdapter(mAdapter);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(com.base.baselibs.R.anim.activity_bottom_in, com.base.baselibs.R.anim.activity_bottom_out);
    }

    private void updateUI() {
        if (mGetBlogDetailModel.getBody().size() == 0) {
            return;
        }
        GetBlogDetailModel.BodyEntity mBodyEntity = mGetBlogDetailModel.getBody().get(0);

        blogUserId = mBodyEntity.getUser_id();
        favoriteId = mBodyEntity.getFavorite_id();
        isReplay = mBodyEntity.getReplay_flag();
        vipLevel = mBodyEntity.getUserLevel();

        setBarTitleTx(mBodyEntity.getTitle());
        changeMyHeart();

        blogdetailTopVoiceBgIv.setImageURI(Common.ImageUrl + mBodyEntity.getImg());

        mImgUrl = Common.ImageUrl + mBodyEntity.getImg();
        mAudioUrl = Common.ImageUrl + mBodyEntity.getUrl();

        blogdetailFlowerTv.setText(mGetBlogDetailModel.getBody().get(0).getFlower_num() + "");
        if (mGetBlogDetailModel.getBody().get(0).getFlower_num() > 0) {
            mRewardListAdapter = new RewardGridViewAdapter(mContext, mGetBlogDetailModel.getBody().get(0).getGiftList());
            blogdetailFlowerGv.setAdapter(mRewardListAdapter);
        }
        blogdetailFlowerGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(BlogRewardListActivty.buildIntent(mContext, mGetBlogDetailModel.getBody().get(0).getBlog_id()));
            }
        });

        if (mBodyEntity.getSex().equals("M")) {
            blogdetailGenderImg.setImageResource(R.mipmap.personal_man_ic);
        } else {
            blogdetailGenderImg.setImageResource(R.mipmap.personal_weman_ic);
        }

        if (mBodyEntity.getAnonymityUser() != null) {//匿名用户
            isAnonymity = true;
            anonymityImg = Common.ImageUrl + mBodyEntity.getAnonymityUser().getIcon();
            ImageLoader.getInstance().displayImage(anonymityImg
                    , blogdetailHeadImg, MyBaseApplication.getApplication().getOptionsNot());
            blogdetailNameTx.setText(mBodyEntity.getAnonymityUser().getNick_name());
            blogdetailLevelTx.setVisibility(View.GONE);
            blogdetailGenderImg.setVisibility(View.GONE);
            blogdetailVerifyImg.setVisibility(View.GONE);
            blogdetailVipTx.setVisibility(View.GONE);
            blogdetailSvipIv.setVisibility(View.GONE);
        } else {
            isAnonymity = false;
            blogdetailLevelTx.setVisibility(View.VISIBLE);
            if (mBodyEntity.getVerify_status() == 1) {
                blogdetailVerifyImg.setVisibility(View.VISIBLE);
                blogdetailGenderImg.setVisibility(View.GONE);
            } else {
                blogdetailVerifyImg.setVisibility(View.GONE);
                blogdetailGenderImg.setVisibility(View.VISIBLE);
            }
            blogdetailNameTx.setText(mBodyEntity.getUser_name());
            blogdetailLevelTx.setText("Lv " + mBodyEntity.getUserLevel());
            if (vipLevel >= 3) {
                blogdetailNameTx.setTextColor(getResources().getColor(R.color.color_red));
            } else {
                blogdetailNameTx.setTextColor(getResources().getColor(R.color.color_333333));
            }
            ImageLoader.getInstance().displayImage(Common.ImageUrl + mBodyEntity.getIcon()
                    , blogdetailHeadImg, MyBaseApplication.getApplication().getOptionsNot());

            if (vipLevel >= 4) {
                blogdetailVipTx.setVisibility(View.GONE);
                blogdetailSvipIv.setVisibility(View.VISIBLE);
            } else {
                blogdetailSvipIv.setVisibility(View.GONE);
                if (vipLevel == 0) {
                    blogdetailVipTx.setVisibility(View.GONE);
                } else {
                    blogdetailVipTx.setText("VIP." + vipLevel);
                    blogdetailVipTx.setVisibility(View.VISIBLE);
                }
            }
        }


        if (mBodyEntity.getGoodsList().size() > 0) {
            blogdetailGoodsLl.setVisibility(View.VISIBLE);
            final BlogDetailGoodsListAdapter tempAdaper = new BlogDetailGoodsListAdapter(mContext
                    , mBodyEntity.getGoodsList());
            blogdetailGoodsLv.setAdapter(tempAdaper);
            blogdetailGoodsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(GoodsDetailActivity.buildIntent(mContext,
                            (int) tempAdaper.getItem(position).getGoods_id()));
                }
            });
        }

        bloglistTimeTx.setText(MyTools.convertTimeS(mBodyEntity.getCreate_time()));

        mAdapter.setBlogUserId(blogUserId);
        mAdapter.setAnonymity(isAnonymity);
        mAdapter.setAnonymityImg(anonymityImg);
    }

    private void showBottomImg(View view) {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        String[] str;
        if (isMy) {
            str = new String[]{"分享", "删除"};
        } else {
            str = new String[]{"分享", "举报", "把TA加入黑名单"};
        }
        builder.setItems(str, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (which == 0) {//分享
                    ShareDialog.Builder builder = new ShareDialog.Builder(mContext);
                    builder.setTitle("分享");
                    builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    ShareDialog dailog = builder.show();
                    dailog.setShareListener(new ShareDialog.ShareListener() {
                        @Override
                        public void onShare(int type) {
                            SHARE_MEDIA Platform = null;
                            if (type == 1) {//分享微信
                                Platform = SHARE_MEDIA.WEIXIN;
                            } else if (type == 2) {//朋友圈
                                Platform = SHARE_MEDIA.WEIXIN_CIRCLE;
                            } else if (type == 3) {//分享新浪微博
                                Platform = SHARE_MEDIA.SINA;
                            } else if (type == 4) {//qq
                                Platform = SHARE_MEDIA.QQ;
                            } else if (type == 5) {
                                Tools.copy("午夜神器" + "\n"
                                        + MyBaseApplication.mDownLoad_URL, mContext);
                            }
                            MyBaseApplication.getApplication().setFilterLock(true);
                            ShareHelper.share(VoicePostDetailActivity.this, Platform
                                    , MyBaseApplication.mDownLoad_URL, VoicePostDetailActivity.this);
                        }
                    });
                } else if (which == 1) {//举报
                    if (!isLogin()) {
                        showLogin();
                        return;
                    }
                    if (isMy) {
                        deletePost();
                    } else {
                        startActivity(ReportListActivity.buildIntent(mContext, (long) bolgId, 2));
                    }
                } else if (which == 2) {//把TA加入黑名单
                    if (!isLogin()) {
                        showLogin();
                        return;
                    }
                    if (mGetBlogDetailModel.getBody().size() > 0
                            && MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getUser_id()
                            == mGetBlogDetailModel.getBody().get(0).getUser_id()) {
                        showToast("不能将自己加入黑名单");
                        return;
                    }
                    OkHttpUtils.postString().url(Common.Url_Add_BlackList)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .content("{\"black_user_id\":" + mGetBlogDetailModel.getBody().get(0).getUser_id() + "}")
                            .mediaType(Common.JSON).id(Common.NET_ADD_BLACKLIST).tag(Common.NET_ADD_BLACKLIST)
                            .build().execute(new MyStringCallback(mContext, VoicePostDetailActivity.this, true));
                } else if (which == 3) {//删除
                    if (!isLogin()) {
                        showLogin();
                        return;
                    }
                    deletePost();
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

    private void deletePost() {
        OkHttpUtils.postString().url(Common.Url_Delete_Post + bolgId).mediaType(Common.JSON)
                .content("{}")
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .id(Common.NET_DELETE_POST).tag(Common.NET_DELETE_POST)
                .build().execute(new MyStringCallback(mContext, VoicePostDetailActivity.this, true));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerView.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGDETAIL);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGDETAIL_COMMENTLIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGCOLLECTION);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGCOLLECTION_NOT);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_COMMENT);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_LIKE);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_BLACKLIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_DELETE_POST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_DANMAKU_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_FOLLOW_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CANCEL_MYCONCERNLIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_USERINDEX);
    }

    @Override
    public void onItemClick(View view, int position) {
        mPosition = position;
        switch (view.getId()) {
            case R.id.item_postingsdetail_comment_like_tv:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                OkHttpUtils.postString().url(Common.Url_Add_Like + "1/" + mAdapter.getItem(position).getBlog_comment_id())
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .content("{}").mediaType(Common.JSON).id(Common.NET_ADD_LIKE).tag(Common.NET_ADD_LIKE)
                        .build().execute(new MyStringCallback(mContext, VoicePostDetailActivity.this, true));
                break;
            case R.id.item_postingsdetail_comment_head_rl:
                if (MyBaseApplication.getApplication().mGetMyUserIndexModel != null && mAdapter.getItem(position).getUser_id() ==
                        MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId()) {
                    showWraning("亲，这是你自己哦！");
                    return;
                }
                startActivity(OtherPersonalActivity.buildIntent(mContext, mAdapter.getItem(position).getUser_id()));
                break;
        }
    }

    @OnClick({R.id.postings_totop_iv, R.id.blogdetail_send_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.postings_totop_iv:
                mListView.setSelection(0);
                break;
            case R.id.blogdetail_send_bt:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                LogUtils.e(">>>>>blogdetail_send_bt");
                sendMessage(view);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.toSelectGift) {
            int num = data.getIntExtra("price", 0);
            if (mGetBlogDetailModel.getBody().size() > 0) {
                long n = mGetBlogDetailModel.getBody().get(0).getFlower_num();
                mGetBlogDetailModel.getBody().get(0).setFlower_num(n + num / 2);
                blogdetailFlowerTv.setText(mGetBlogDetailModel.getBody().get(0).getFlower_num() + "");
            } else {
                blogdetailFlowerTv.setText(num / 2 + "");
            }
            GetBlogDetailModel.BodyEntity.GiftListEntity temp = new GetBlogDetailModel.BodyEntity.GiftListEntity();
            temp.setIcon(MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon());
            if (mRewardListAdapter == null) {
                mRewardListAdapter = new RewardGridViewAdapter(mContext, temp);
                blogdetailFlowerGv.setAdapter(mRewardListAdapter);
            } else {
                mRewardListAdapter.addData(temp);
            }
        }
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }
}
