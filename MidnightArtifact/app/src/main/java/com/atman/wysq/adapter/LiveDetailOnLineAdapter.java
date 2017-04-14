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
import com.atman.wysq.model.response.LiveDetailModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tangbingliang on 17*4/5*4/52.
 */

public class LiveDetailOnLineAdapter extends RecyclerView.Adapter<LiveDetailOnLineAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private AdapterInterface mItemClick;
    private List<LiveDetailModel.BodyBean.UserListBean> listData;

    public LiveDetailOnLineAdapter(Context context, AdapterInterface mItemClick){
        this.mContext = context;
        this.listData = new ArrayList<>();
        this.mItemClick = mItemClick;
        this.mInflater = LayoutInflater.from(context);
    }

    public void addData(List<LiveDetailModel.BodyBean.UserListBean> mList) {
        this.listData.addAll(mList);
        notifyDataSetChanged();
    }

    public LiveDetailModel.BodyBean.UserListBean getItemData(int p) {
        return listData.get(p);
    }

    @Override
    public LiveDetailOnLineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_online_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.itemOnlineIv = (SimpleDraweeView) view.findViewById(R.id.item_online_iv);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LiveDetailOnLineAdapter.ViewHolder holder, final int position) {

        String imgUrl = listData.get(position).getIcon();
        if (imgUrl!=null && !imgUrl.startsWith("/")) {
            imgUrl = "/" + imgUrl;
        }

        holder.itemOnlineIv.setImageURI(Common.ImageUrl+imgUrl);

        holder.itemOnlineIv.setOnClickListener(new View.OnClickListener() {
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

        SimpleDraweeView itemOnlineIv;
    }
}
