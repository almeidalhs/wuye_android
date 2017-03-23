package com.atman.wysq.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.atman.wysq.R;
import com.atman.wysq.model.request.ScenePicList;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tangbingliang on 17/2/22.
 */

public class EditScenePicAdapter extends RecyclerView.Adapter<EditScenePicAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private AdapterInterface mItemClick;
    private List<ScenePicList> listData;
    private RelativeLayout.LayoutParams params;
    private String str;

    public EditScenePicAdapter(Context context, int mWight, AdapterInterface mItemClick){
        this.mContext = context;
        this.listData = new ArrayList<>();
        this.mItemClick = mItemClick;
        this.mInflater = LayoutInflater.from(context);
        params = new RelativeLayout.LayoutParams(mWight, mWight);
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void addData(List<ScenePicList> mList) {
        this.listData.addAll(mList);

        notifyDataSetChanged();
    }

    public List<ScenePicList> getListData() {
        return listData;
    }

    public List<ScenePicList> getListData(int n) {
        if (n!=0) {
            Collections.swap(listData, 0, n);
        }
        return listData;
    }

    public ScenePicList getItemData(int p) {
        return listData.get(p);
    }

    @Override
    public EditScenePicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_editscenepic_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.itemEditscenepicIv = (SimpleDraweeView) view.findViewById(R.id.item_editscenepic_iv);
        viewHolder.itemEditscenepicSelectIv = (ImageView) view.findViewById(R.id.item_editscenepic_select_iv);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EditScenePicAdapter.ViewHolder holder, final int position) {

        holder.itemEditscenepicIv.setLayoutParams(params);
        holder.itemEditscenepicSelectIv.setVisibility(View.GONE);

        if (listData.get(position).getUrl().equals("-1")) {
            Uri uri = Uri.parse("res:///" + R.mipmap.bt_create_addimg);
            holder.itemEditscenepicIv.setImageURI(uri);
        } else {
            holder.itemEditscenepicIv.setImageURI(Common.ImageUrl+listData.get(position).getUrl());
            LogUtils.e(">>>>>:"+(Common.ImageUrl+listData.get(position).getUrl()));
        }

        holder.itemEditscenepicIv.setOnClickListener(new View.OnClickListener() {
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

        SimpleDraweeView itemEditscenepicIv;
        ImageView itemEditscenepicSelectIv;
    }
}