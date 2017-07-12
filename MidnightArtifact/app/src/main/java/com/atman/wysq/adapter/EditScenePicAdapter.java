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
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

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

    public void selectById (int id) {
        if (listData.get(id).isUped()) {
            listData.get(id).setSelect(true);
        } else {
            listData.get(id).setSelect(false);
        }
        notifyItemChanged(id);
    }

    public void deleteById (int id) {
        listData.remove(id);
        if (listData.size()<5 && !listData.get(listData.size()-1).getUrl().equals("-1")) {
            listData.add(new ScenePicList("-1", true));
        }
        notifyDataSetChanged();
    }

    public void addData(ScenePicList mList) {
        this.listData.add(listData.size()-1,mList);
        notifyDataSetChanged();
    }

    public List<ScenePicList> getNotUp () {
        List<ScenePicList> temp = new ArrayList<>();
        for (int i=0;i<listData.size();i++) {
            if (!listData.get(i).isUped() && !listData.get(i).getUrl().equals("-1")) {
                temp.add(listData.get(i));
            }
        }
        return temp;
    }

    public List<ScenePicList> getUped () {
        List<ScenePicList> temp = new ArrayList<>();
        for (int i=0;i<listData.size();i++) {
            if (listData.get(i).isUped() && !listData.get(i).getUrl().equals("-1")) {
                temp.add(listData.get(i));
            }
        }
        return temp;
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

        viewHolder.itemEditscenepicIv = (ShapeImageView) view.findViewById(R.id.item_editscenepic_iv);
        viewHolder.itemEditscenepicSelectIv = (ImageView) view.findViewById(R.id.item_editscenepic_select_iv);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EditScenePicAdapter.ViewHolder holder, final int position) {

        holder.itemEditscenepicIv.setLayoutParams(params);
        if (listData.get(position).isSelect()) {
            holder.itemEditscenepicSelectIv.setVisibility(View.VISIBLE);
        } else {
            holder.itemEditscenepicSelectIv.setVisibility(View.GONE);
        }

        if (listData.get(position).getUrl().equals("-1")) {
            holder.itemEditscenepicIv.setImageResource(R.mipmap.bt_create_addimg);
        } else {
            if (listData.get(position).isUped()) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+listData.get(position).getUrl(), holder.itemEditscenepicIv);
            } else {
                ImageLoader.getInstance().displayImage("file://" + listData.get(position).getUrl(), holder.itemEditscenepicIv);
            }
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
        if (listData.size()>=5) {
            return 5;
        } else {
            return listData.size();
        }
    }

    public void clearData() {
        this.listData.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ShapeImageView itemEditscenepicIv;
        ImageView itemEditscenepicSelectIv;
    }
}
