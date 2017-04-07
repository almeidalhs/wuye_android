package com.atman.wysq.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.DiscoverNewModel;
import com.atman.wysq.model.response.DiscoverNewModel.BodyBean;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tangbingliang on 17/2/22.
 */

public class DiscoverNewFindAdapter extends RecyclerView.Adapter<DiscoverNewFindAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private AdapterInterface mItemClick;
    private List<DiscoverNewModel.BodyBean> listData;
    private RelativeLayout.LayoutParams params;
    private String str;

    public DiscoverNewFindAdapter(Context context, int mWight, AdapterInterface mItemClick){
        this.mContext = context;
        this.listData = new ArrayList<>();
        this.mItemClick = mItemClick;
        this.mInflater = LayoutInflater.from(context);

        int w = mWight/3;
        params = new RelativeLayout.LayoutParams(w, w * 100 / 67);
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void addData(List<DiscoverNewModel.BodyBean> mList) {
        this.listData.addAll(mList);

        notifyDataSetChanged();
    }

    public List<DiscoverNewModel.BodyBean> getListData() {
        return listData;
    }

    public List<DiscoverNewModel.BodyBean> getListData(int n) {
        if (n!=0) {
            Collections.swap(listData, 0, n);
        }
        return listData;
    }

    public DiscoverNewModel.BodyBean getItemData(int p) {
        return listData.get(p);
    }

    @Override
    public DiscoverNewFindAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_discovernew_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.itemDiscovernewIv = (SimpleDraweeView) view.findViewById(R.id.item_discovernew_iv);
        viewHolder.itemDiscovernewNameTv = (TextView) view.findViewById(R.id.item_discovernew_name_tv);
        viewHolder.itemDiscovernewNumTv = (TextView) view.findViewById(R.id.item_discovernew_num_tv);
        viewHolder.itemDiscovernewRootRl = (RelativeLayout) view.findViewById(R.id.item_discovernew_root_rl);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DiscoverNewFindAdapter.ViewHolder holder, final int position) {

        holder.itemDiscovernewRootRl.setLayoutParams(params);
        holder.itemDiscovernewIv.setLayoutParams(params);

        holder.itemDiscovernewNumTv.setText(" "+listData.get(position).getView_count()+"");
        holder.itemDiscovernewNameTv.setText(listData.get(position).getNick_name());
        ImageLoader.getInstance().displayImage(Common.ImageUrl+listData.get(position).getPic_url1()
                , holder.itemDiscovernewIv, MyBaseApplication.getApplication().getOptionsNot());
//        holder.itemDiscovernewIv.setImageURI(Common.ImageUrl+listData.get(position).getPic_url1());

        holder.itemDiscovernewIv.setOnClickListener(new View.OnClickListener() {
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

        SimpleDraweeView itemDiscovernewIv;
        TextView itemDiscovernewNameTv;
        TextView itemDiscovernewNumTv;
        RelativeLayout itemDiscovernewRootRl;
    }
}
