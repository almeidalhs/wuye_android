package com.atman.wysq.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.CommunityNewModel;
import com.atman.wysq.model.response.DiscoverNewModel.BodyBean;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tangbingliang on 17/2/22.
 */

public class CommunityNewAdapter extends RecyclerView.Adapter<CommunityNewAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private AdapterInterface mItemClick;
    private List<CommunityNewModel.BodyBean> listData;
    private RelativeLayout.LayoutParams params;
    private String str;

    public CommunityNewAdapter(Context context, int mWight, AdapterInterface mItemClick){
        this.mContext = context;
        this.listData = new ArrayList<>();
        this.mItemClick = mItemClick;
        this.mInflater = LayoutInflater.from(context);

        int w = (mWight - DensityUtil.dp2px(context, 30))/2;
        params = new RelativeLayout.LayoutParams(w, w * 190 / 340);
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void addData(List<CommunityNewModel.BodyBean> mList) {
        this.listData.addAll(mList);

        notifyDataSetChanged();
    }

    public List<CommunityNewModel.BodyBean> getListData() {
        return listData;
    }

    public List<CommunityNewModel.BodyBean> getListData(int n) {
        if (n!=0) {
            Collections.swap(listData, 0, n);
        }
        return listData;
    }

    public CommunityNewModel.BodyBean getItemData(int p) {
        return listData.get(p);
    }

    @Override
    public CommunityNewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_communitynew_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.itemCommunitynewRootLl = (LinearLayout) view.findViewById(R.id.item_communitynew_root_ll);
        viewHolder.itemCommunitynewBgIv = (SimpleDraweeView) view.findViewById(R.id.item_communitynew_bg_iv);
        viewHolder.itemCommunitynewLivetagIv = (ImageView) view.findViewById(R.id.item_communitynew_livetag_iv);
        viewHolder.itemCommunitynewTitleTv = (TextView) view.findViewById(R.id.item_communitynew_title_tv);
        viewHolder.itemCommunitynewHeadIv = (SimpleDraweeView) view.findViewById(R.id.item_communitynew_head_iv);
        viewHolder.itemCommunitynewTopTv = (TextView) view.findViewById(R.id.item_communitynew_top_tv);
        viewHolder.itemCommunitynewLiveLl = (LinearLayout) view.findViewById(R.id.item_communitynew_live_ll);
        viewHolder.itemCommunitynewLiveNumTv = (TextView) view.findViewById(R.id.item_communitynew_live_num_tv);
        viewHolder.itemCommunitynewNotliveLl = (LinearLayout) view.findViewById(R.id.item_communitynew_notlive_ll);
        viewHolder.itemCommunitynewNotliveIv = (ImageView) view.findViewById(R.id.item_communitynew_notlive_iv);
        viewHolder.itemCommunitynewNotliveNumTv = (TextView) view.findViewById(R.id.item_communitynew_notlive_num_tv);
        viewHolder.itemCommunitynewNotliveHeartTv = (TextView) view.findViewById(R.id.item_communitynew_notlive_heart_tv);
        viewHolder.itemCommunitynewNotliveMessageTv = (TextView) view.findViewById(R.id.item_communitynew_notlive_message_tv);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommunityNewAdapter.ViewHolder holder, final int position) {

        holder.itemCommunitynewBgIv.setLayoutParams(params);
        String imgUrl = listData.get(position).getImg();
        if (!imgUrl.startsWith("/")) {
            imgUrl = "/" + imgUrl;
        }
        holder.itemCommunitynewBgIv.setImageURI(Common.ImageUrl+imgUrl);
        holder.itemCommunitynewTitleTv.setText(listData.get(position).getTitle());
        imgUrl = listData.get(position).getIcon();
        if (!imgUrl.startsWith("/")) {
            imgUrl = "/" + imgUrl;
        }
        holder.itemCommunitynewHeadIv.setImageURI(Common.ImageUrl+imgUrl);
        if (listData.get(position).getCategory()==4) {
            holder.itemCommunitynewLivetagIv.setVisibility(View.VISIBLE);
            holder.itemCommunitynewLiveLl.setVisibility(View.VISIBLE);
            holder.itemCommunitynewNotliveLl.setVisibility(View.GONE);
            holder.itemCommunitynewLiveNumTv.setText(" "+listData.get(position).getLike_num());
        } else {
            holder.itemCommunitynewLivetagIv.setVisibility(View.GONE);
            holder.itemCommunitynewLiveLl.setVisibility(View.GONE);
            holder.itemCommunitynewNotliveLl.setVisibility(View.VISIBLE);
            holder.itemCommunitynewNotliveNumTv.setText(" "+listData.get(position).getView_count());

            if (listData.get(position).getCategory()==1) {
                holder.itemCommunitynewNotliveIv.setBackgroundResource(R.mipmap.ic_normal);
            } else if (listData.get(position).getCategory()==2) {
                holder.itemCommunitynewNotliveIv.setBackgroundResource(R.mipmap.ic_audio);
            } else if (listData.get(position).getCategory()==3) {
                holder.itemCommunitynewNotliveIv.setBackgroundResource(R.mipmap.ic_video);
            }

            Drawable drawable = null;
            if (listData.get(position).getFavorite_count() > 0) {
                drawable = mContext.getResources().getDrawable(R.mipmap.square_like_press);
                holder.itemCommunitynewNotliveHeartTv.setTextColor(mContext.getResources().getColor(R.color.color_fda7a7));
            } else {
                drawable = mContext.getResources().getDrawable(R.mipmap.square_like_default);
                holder.itemCommunitynewNotliveHeartTv.setTextColor(mContext.getResources().getColor(R.color.color_bfbfbf));
            }
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.itemCommunitynewNotliveHeartTv.setCompoundDrawables(drawable, null, null, null);
            holder.itemCommunitynewNotliveHeartTv.setText(" "+listData.get(position).getFavorite_count());

            if (listData.get(position).getReplay_flag() == 1) {
                drawable = mContext.getResources().getDrawable(R.mipmap.square_comment_press);
                holder.itemCommunitynewNotliveMessageTv.setTextColor(mContext.getResources().getColor(R.color.color_fed676));
            } else {
                drawable = mContext.getResources().getDrawable(R.mipmap.square_comment);
                holder.itemCommunitynewNotliveMessageTv.setTextColor(mContext.getResources().getColor(R.color.color_bfbfbf));
            }
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.itemCommunitynewNotliveMessageTv.setCompoundDrawables(drawable, null, null, null);
            holder.itemCommunitynewNotliveMessageTv.setText(" "+listData.get(position).getComment_count());
        }

        holder.itemCommunitynewRootLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClick.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void clearData() {
        this.listData.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        LinearLayout itemCommunitynewRootLl;
        SimpleDraweeView itemCommunitynewBgIv;
        ImageView itemCommunitynewLivetagIv;
        TextView itemCommunitynewTitleTv;
        SimpleDraweeView itemCommunitynewHeadIv;
        TextView itemCommunitynewTopTv;
        LinearLayout itemCommunitynewLiveLl;
        TextView itemCommunitynewLiveNumTv;
        LinearLayout itemCommunitynewNotliveLl;
        ImageView itemCommunitynewNotliveIv;
        TextView itemCommunitynewNotliveNumTv;
        TextView itemCommunitynewNotliveHeartTv;
        TextView itemCommunitynewNotliveMessageTv;
    }
}
