package com.atman.wysq.ui.discover;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetLiveHallModel;
import com.atman.wysq.model.response.ListenLiveRoomInfoModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.widget.PromptDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.netease.nimlib.sdk.media.player.AudioPlayer;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/1/11.
 */

public class ListenLiveActivity extends MyBaseActivity {

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

    private ListenLiveRoomInfoModel mListenLiveRoomInfoModel;

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
        mliveStreamingURL = mBodyBean.getCurrentRecord().getPushUrl();
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
}
