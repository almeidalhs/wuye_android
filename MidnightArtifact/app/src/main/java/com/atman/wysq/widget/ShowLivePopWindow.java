package com.atman.wysq.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.widget.MyCleanEditText;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by tangbingliang on 16/12/29.
 */

public class ShowLivePopWindow extends PopupWindow {

    private Activity mContext;
    private View v;
    private MyCleanEditText partLivepopTitleEt;
    private SimpleDraweeView partLivepopBgIv;
    private TextView partLivepopGoliveTx;

    public TextView getPartLivepopGoliveTx() {
        return partLivepopGoliveTx;
    }

    public View getV() {
        return v;
    }

    public void setTitle (String str) {
        if (partLivepopTitleEt!=null) {
            partLivepopTitleEt.setText(str);
        }
    }

    public String getTitlle () {
        if (partLivepopTitleEt!=null) {
            String s = partLivepopTitleEt.getText().toString();
            if (s.length()>20) {
                s = s.substring(0, 20);
            }
            return s;
        } else {
            return "";
        }
    }

    public SimpleDraweeView setBg (String url) {
        if (partLivepopBgIv!=null) {
            partLivepopBgIv.setImageURI(url);
            return partLivepopBgIv;
        } else {
            return null;
        }
    }

    public ShowLivePopWindow (Activity mContext, View v, int width){
        super(mContext);
        this.mContext = mContext;
        this.v = v;

        View view = LayoutInflater.from(mContext).inflate(R.layout.part_live_pop_view, null);
        this.setContentView(view);

        RelativeLayout partRootRl = (RelativeLayout) view.findViewById(R.id.part_root_rl);
        RelativeLayout partLivepopRootRl = (RelativeLayout) view.findViewById(R.id.part_livepop_root_rl);
        ImageView partLivepopCloseIv = (ImageView) view.findViewById(R.id.part_livepop_close_iv);
        partLivepopTitleEt = (MyCleanEditText) view.findViewById(R.id.part_livepop_title_et);
        partLivepopBgIv = (SimpleDraweeView) view.findViewById(R.id.part_livepop_bg_iv);
        partLivepopGoliveTx = (TextView) view.findViewById(R.id.part_livepop_golive_tx);

        int w = width- DensityUtil.dp2px(mContext, 100);
        RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(w, w*4/3);
        mParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        partLivepopRootRl.setLayoutParams(mParams);
        partLivepopRootRl.setOnClickListener(new View.OnClickListener() {
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

        partLivepopCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        w = w - DensityUtil.dp2px(mContext, 20);
        LinearLayout.LayoutParams mParamsLl = new LinearLayout.LayoutParams(w, w/2);
        partLivepopBgIv.setLayoutParams(mParamsLl);

        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x60000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
//        this.setBackgroundDrawable(new BitmapDrawable());
        this.setAnimationStyle(R.style.take_photo_anim);
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
}
