package com.atman.wysq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atman.wysq.R;
import com.atman.wysq.model.response.RecommendUserModel;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.CustomImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by tangbingliang on 16/12/12.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<RecommendUserModel.BodyBean> mDatas;
    private LinearLayout.LayoutParams layoutParams,layoutParams2;
    private int slecteID = 0;
    private AdapterInterface mAdapterInterface;
    private Context context;

    public void setSlecteID(int slecteID) {
        if (this.slecteID != slecteID) {
            notifyItemChanged(this.slecteID);
            this.slecteID = slecteID;
            notifyItemChanged(slecteID);
        }
    }

    public GalleryAdapter(Context context, List<RecommendUserModel.BodyBean> datats, AdapterInterface mAdapterInterface) {
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = datats;
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutParams = new LinearLayout.LayoutParams(DensityUtil.dp2px(context, 80)
                , DensityUtil.dp2px(context, 120));
        layoutParams2 = new LinearLayout.LayoutParams(DensityUtil.dp2px(context, 93)
                , DensityUtil.dp2px(context, 140));
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getType();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        CustomImageView mImg;
        LinearLayout itemDiscoverFindLl;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public RecommendUserModel.BodyBean getItem(int p){
        return mDatas.get(p);
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_discover_find_view,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (CustomImageView) view.findViewById(R.id.item_discover_find_iv);
        viewHolder.itemDiscoverFindLl = (LinearLayout) view.findViewById(R.id.item_discover_find_ll);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.mImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.mImg.setImageDrawable(null);
        ImageLoader.getInstance().displayImage(Common.ImageUrl + mDatas.get(i).getPic_url1(), viewHolder.mImg);

        LogUtils.e("slecteID:"+slecteID);
        LogUtils.e("i:"+i);
        if (slecteID==i) {
            viewHolder.mImg.setLayoutParams(layoutParams2);
            viewHolder.itemDiscoverFindLl.setPadding(DensityUtil.dp2px(context, 5), DensityUtil.dp2px(context, 0)
                    ,DensityUtil.dp2px(context, 5),DensityUtil.dp2px(context, 0));
        } else {
            viewHolder.mImg.setLayoutParams(layoutParams);
            viewHolder.itemDiscoverFindLl.setPadding(DensityUtil.dp2px(context, 5), DensityUtil.dp2px(context, 10)
                    ,DensityUtil.dp2px(context, 5),DensityUtil.dp2px(context, 10));
        }

        viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, i);
            }
        });
    }

}
