package com.atman.wysq.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.iimp.SpAdapterInterface;
import com.atman.wysq.model.response.RecommendUserModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

/**
 * Created by tangbingliang on 16/12/1.
 */

public class FindLikeAdapter extends FancyCoverFlowAdapter {

    private Context context;
    private RecommendUserModel mRecommendUserModel;
    private int size;
    private Drawable drawable;
    private SpAdapterInterface mAdapterInterface;
    private int from;

    public FindLikeAdapter(Context context, RecommendUserModel mRecommendUserModel
            , int from, SpAdapterInterface mAdapterInterface){
        this.context = context;
        this.mRecommendUserModel = mRecommendUserModel;
        this.mAdapterInterface = mAdapterInterface;
        this.from = from;
        size = mRecommendUserModel.getBody().size();
        drawable = context.getResources().getDrawable(R.mipmap.mchat_icon_num);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
    }


    @Override
    public int getCount() {
        return mRecommendUserModel.getBody().size();
    }

    @Override
    public RecommendUserModel.BodyBean getItem(int i) {
        return mRecommendUserModel.getBody().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getCoverFlowItem(final int i, View reuseableView, ViewGroup viewGroup) {
        CustomViewGroup customViewGroup = null;

        if (reuseableView != null) {
            customViewGroup = (CustomViewGroup) reuseableView;
        } else {
            customViewGroup = new CustomViewGroup(viewGroup.getContext(), drawable);
            customViewGroup.setBackgroundResource(R.drawable.white_hollow_stroke_radius_bg);
            customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(DensityUtil.dp2px(context,90)
                    , DensityUtil.dp2px(context,140)));
        }
        customViewGroup.setPadding(DensityUtil.dp2px(context,2), DensityUtil.dp2px(context,2)
                , DensityUtil.dp2px(context,2), DensityUtil.dp2px(context,2));
        ImageLoader.getInstance().displayImage(Common.ImageUrl+this.getItem( i ).getPic_url1()
                , customViewGroup.getImageView(), MyBaseApplication.getApplication().getOptionsNot());
//        customViewGroup.getTextView().setText(""+this.getItem( i ).getChat_count());
        customViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, i, from);
            }
        });

        return customViewGroup;
    }

    private static class CustomViewGroup extends RelativeLayout {

        private TextView textView;
        private ShapeImageView imageView;

        private CustomViewGroup(Context context, Drawable drawable) {
            super(context);

//            this.textView = new TextView(context);
            this.imageView = new ShapeImageView(context);

//            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
//                    , ViewGroup.LayoutParams.WRAP_CONTENT);
//            this.textView.setGravity(Gravity.CENTER);
//            this.textView.setPadding(DensityUtil.dp2px(context,3), 0, 0, 0);
//            this.textView.setTextAppearance(context, R.style.AudioFileInfoOverlayText_TWO);
//            this.textView.setCompoundDrawables(drawable, null, null, null);
//            this.textView.setLayoutParams(layoutParams);

            LayoutParams layoutParams2 = new LayoutParams(DensityUtil.dp2px(context,90)
                    , DensityUtil.dp2px(context,140));
            this.imageView.setLayoutParams(layoutParams2);
            this.imageView.setRoundRadius(DensityUtil.dp2px(context,3));

            this.addView(this.imageView);
//            this.addView(this.textView);
        }

        private TextView getTextView() {
            return textView;
        }

        private ImageView getImageView() {
            return imageView;
        }

    }
}
