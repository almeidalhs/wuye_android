package com.atman.wysq.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.widget.ShapeImageView;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

/**
 * Created by tangbingliang on 16/12/1.
 */

public class ViewGroupExampleAdapter extends FancyCoverFlowAdapter {

    private int[] images = {R.drawable.ic_app
            , R.drawable.ic_app
            , R.drawable.ic_app
            , R.drawable.ic_app};

    private Context context;

    public ViewGroupExampleAdapter (Context context){
        this.context = context;
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Integer getItem(int i) {
        return images[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
        CustomViewGroup customViewGroup = null;

        if (reuseableView != null) {
            customViewGroup = (CustomViewGroup) reuseableView;
        } else {
            customViewGroup = new CustomViewGroup(viewGroup.getContext());
            customViewGroup.setBackgroundResource(R.drawable.white_hollow_stroke_radius_bg);
            customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(DensityUtil.dp2px(context,90)
                    , DensityUtil.dp2px(context,140)));
        }
        customViewGroup.setPadding(DensityUtil.dp2px(context,2), DensityUtil.dp2px(context,2)
                , DensityUtil.dp2px(context,2), DensityUtil.dp2px(context,2));
        customViewGroup.getImageView().setImageResource(this.getItem( i % images.length));
        customViewGroup.getTextView().setText(String.format("Item %d", i % images.length));

        return customViewGroup;
    }

    private static class CustomViewGroup extends RelativeLayout {

        private TextView textView;
        private ShapeImageView imageView;

        private CustomViewGroup(Context context) {
            super(context);

            this.textView = new TextView(context);
            this.imageView = new ShapeImageView(context);

            RelativeLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT);
            this.textView.setGravity(Gravity.CENTER);
            this.textView.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams layoutParams2 = new LayoutParams(DensityUtil.dp2px(context,90)
                    , DensityUtil.dp2px(context,140));
            this.imageView.setLayoutParams(layoutParams2);
            this.imageView.setRoundRadius(DensityUtil.dp2px(context,3));

            this.addView(this.imageView);
            this.addView(this.textView);
        }

        private TextView getTextView() {
            return textView;
        }

        private ImageView getImageView() {
            return imageView;
        }
    }
}
