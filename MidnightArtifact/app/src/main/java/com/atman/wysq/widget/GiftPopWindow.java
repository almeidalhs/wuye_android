package com.atman.wysq.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.atman.wysq.R;

/**
 * Created by tangbingliang on 16/12/29.
 */

public class GiftPopWindow extends PopupWindow {

    private Activity mContext;

    public GiftPopWindow(Activity mContext){
        super(mContext);
        this.mContext = mContext;

        View view = LayoutInflater.from(mContext).inflate(R.layout.part_gift_pop_view, null);
        this.setContentView(view);

        LinearLayout partRootLl = (LinearLayout) view.findViewById(R.id.root_ll);

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);
        partRootLl.setLayoutParams(mParams);

        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 实例化一个ColorDrawable颜色为透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
//        this.setBackgroundDrawable(new BitmapDrawable());
        this.setAnimationStyle(R.style.take_gift_anim);

        showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
