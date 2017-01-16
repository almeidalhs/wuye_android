package com.atman.wysq.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.bean.ImMessage;
import com.atman.wysq.model.response.GetUserIndexModel;
import com.atman.wysq.ui.discover.ListenLiveActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.yunxin.model.ChatRoomTypeInter;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.PromptDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.netease.nimlib.sdk.chatroom.ChatRoomMessageBuilder;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.media.player.AudioPlayer;

import butterknife.OnClick;

/**
 * Created by tangbingliang on 16/12/29.
 */

public class ShowHeadPopWindow extends PopupWindow {

    private Activity mContext;
    private View v;
    private SimpleDraweeView partLiveheadHeadIv;
    private ImageView partLiveheadGenderIv;
    private ImageView partLiveheadVerifyIv;
    private TextView partLiveheadNameTv;
    private TextView liveheadLevelTx;
    private TextView partLivepopGagTx;
    private TextView partliveheadAddTx;
    private TextView partLiveheadVipTx;
    private TextView partLivepopMoreTx;
    private ImageView partLiveheadSvipIv;
    private onHeadPopClickListenner onClick;
    private long userId;
    private String userNick;
    private boolean isMyLive;

    public View getV() {
        return v;
    }

    public ShowHeadPopWindow(Activity context, int width, boolean isMyLive
            , GetUserIndexModel.BodyBean.UserDetailBeanBean mBodyBean, final onHeadPopClickListenner onClick){
        super(context);
        this.mContext = context;
        this.isMyLive = isMyLive;
        this.onClick = onClick;

        View view = LayoutInflater.from(mContext).inflate(R.layout.part_live_head_pop_view, null);
        this.setContentView(view);

        RelativeLayout partRootRl = (RelativeLayout) view.findViewById(R.id.part_root_rl);
        RelativeLayout partLiveheadRootRl = (RelativeLayout) view.findViewById(R.id.part_livehead_root_rl);
        ImageView partLiveheadCloseIv = (ImageView) view.findViewById(R.id.part_livehead_close_iv);
        partLiveheadHeadIv = (SimpleDraweeView) view.findViewById(R.id.part_livehead_head_iv);
        partLiveheadGenderIv = (ImageView) view.findViewById(R.id.part_livehead_gender_iv);
        partLiveheadVerifyIv = (ImageView) view.findViewById(R.id.part_livehead_verify_iv);
        partLiveheadNameTv = (TextView) view.findViewById(R.id.part_livehead_name_tv);
        liveheadLevelTx = (TextView) view.findViewById(R.id.livehead_level_tx);
        partLiveheadVipTx = (TextView) view.findViewById(R.id.part_livehead_vip_tx);
        partLiveheadSvipIv = (ImageView) view.findViewById(R.id.part_livehead_svip_iv);
        partliveheadAddTx = (TextView) view.findViewById(R.id.part_livehead_add_tx);
        partLivepopGagTx = (TextView) view.findViewById(R.id.part_livepop_gag_tx);
        partLivepopMoreTx = (TextView) view.findViewById(R.id.part_livepop_more_tx);

        if (isMyLive) {
            partLivepopGagTx.setVisibility(View.VISIBLE);
        } else {
            partLivepopGagTx.setVisibility(View.GONE);
        }

        userId = mBodyBean.getUserExt().getUser_id();
        liveheadLevelTx.setText("Lv." + mBodyBean.getUserExt().getUserLevel());
        if (mBodyBean.getUserExt().getVip_level() >= 4) {
            partLiveheadVipTx.setVisibility(View.GONE);
            partLiveheadSvipIv.setVisibility(View.VISIBLE);
        } else {
            partLiveheadSvipIv.setVisibility(View.GONE);
            if (mBodyBean.getUserExt().getVip_level() == 0) {
                partLiveheadVipTx.setVisibility(View.GONE);
            } else {
                partLiveheadVipTx.setText("VIP." + mBodyBean.getUserExt().getVip_level());
                partLiveheadVipTx.setVisibility(View.VISIBLE);
            }
        }

        userNick = mBodyBean.getUserExt().getNick_name();
        partLiveheadNameTv.setText(userNick);

        if (mBodyBean.getUserExt().getSex().equals("M")) {
            partLiveheadGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            partLiveheadGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }
        if (mBodyBean.getUserExt().getVerify_status() == 1) {
            partLiveheadVerifyIv.setVisibility(View.VISIBLE);
            partLiveheadGenderIv.setVisibility(View.GONE);
        } else {
            partLiveheadVerifyIv.setVisibility(View.GONE);
            partLiveheadGenderIv.setVisibility(View.VISIBLE);
        }
        partLiveheadHeadIv.setImageURI(Common.ImageUrl + mBodyBean.getUserExt().getIcon());

        partLivepopGagTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGagDialog(mContext, userNick);
            }
        });

        partliveheadAddTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onClick.onAddFriend(userId);
            }
        });

        partLivepopMoreTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onMore(userId);
            }
        });

        int w = width- DensityUtil.dp2px(mContext, 140);
        RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(w, w*6/5);
        mParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        partLiveheadRootRl.setLayoutParams(mParams);
        partLiveheadRootRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIMOpen()) {
                    cancelIM(v);
                }
            }
        });
        partRootRl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isIMOpen()) {
                    cancelIM(v);
                }
                return false;
            }
        });

        partLiveheadCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x60000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.take_photo_anim);
        showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public void cancelIM(View v) {
        if (isIMOpen()) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
        }
    }

    public boolean isIMOpen() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();//isOpen若返回true，则表示输入法打开
    }

    public interface onHeadPopClickListenner {
        void onAddFriend(long id);
        void onGag(long id);
        void onMore(long id);
    }

    private void showGagDialog(Activity context, String name) {
        PromptDialog.Builder builder = new PromptDialog.Builder(context);
        builder.setTitle("禁言【"+name+"】");
        builder.setMessage("(使其15分钟内无法发言)");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("立即禁言", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onClick.onGag(userId);
            }
        });
        builder.show();
    }
}
