package com.atman.wysq.ui.discover;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetMyUserIndexModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.widget.PromptDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/1/5.
 */

public class MyLiveRoomActivity extends MyBaseActivity {

    @Bind(R.id.myliveroom_bg_iv)
    ImageView myliveroomBgIv;
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

    private Context mContext = MyLiveRoomActivity.this;
    private long roomId;
    private String pic_url;
    private String title;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myliveroom);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, String title, String pic_url, long roomId) {
        Intent intent = new Intent(context, MyLiveRoomActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("pic_url", pic_url);
        intent.putExtra("roomId", roomId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        hideTitleBar();

        roomId = getIntent().getLongExtra("roomId", -1);
        pic_url = getIntent().getStringExtra("pic_url");
        title = getIntent().getStringExtra("title");

        myliveroomTitleTv.setText(title);
        if (pic_url != null && !pic_url.isEmpty()) {
            ImageLoader.getInstance().displayImage(Common.ImageUrl + pic_url, myliveroomBgIv);
        }

        GetMyUserIndexModel mGetUserIndexModel = MyBaseApplication.getApplication().mGetMyUserIndexModel;
        myliveroomLevelTx.setText("Lv." + mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getUserLevel());
        if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level()>=4) {
            myliveroomVipTx.setVisibility(View.GONE);
            myliveroomSvipIv.setVisibility(View.VISIBLE);
        } else {
            myliveroomSvipIv.setVisibility(View.GONE);
            if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level()==0) {
                myliveroomVipTx.setVisibility(View.GONE);
            } else {
                myliveroomVipTx.setText("VIP."+mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level());
                myliveroomVipTx.setVisibility(View.VISIBLE);
            }
        }
        myliveroomNameTv.setText(mGetUserIndexModel.getBody().getUserDetailBean().getNickName());

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
        myliveroomHeadIv.setImageURI(Common.ImageUrl+mGetUserIndexModel.getBody()
                .getUserDetailBean().getUserExt().getIcon());

        animationDrawable = (AnimationDrawable) myliveroomAnimIv.getBackground();
        animationDrawable.start();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }

    @OnClick({R.id.myliveroom_back_iv, R.id.myliveroom_pic_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myliveroom_back_iv:
                showWarnExit();
                break;
            case R.id.myliveroom_pic_iv:
                break;
        }
    }

    private void showWarnExit() {
        PromptDialog.Builder builder = new PromptDialog.Builder(MyLiveRoomActivity.this);
        builder.setTitle("确定要结束本次直播吗？");
        builder.setMessage("直播开始：今天 13:41\n直播持续：18分钟25秒");
        builder.setPositiveButton("再来一会", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("结束直播", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }
}
