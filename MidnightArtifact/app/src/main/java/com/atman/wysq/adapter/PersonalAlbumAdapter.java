package com.atman.wysq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetMyUserIndexModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tangbingliang on 17/2/22.
 */

public class PersonalAlbumAdapter extends RecyclerView.Adapter<PersonalAlbumAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private AdapterInterface mItemClick;
    private List<GetMyUserIndexModel.BodyBean.UserDetailBeanBean.PhotoListBean> listData;
    private RelativeLayout.LayoutParams params;
    private String str;
    private boolean isSelectStats = false;

    public PersonalAlbumAdapter(Context context, int mWight, AdapterInterface mItemClick){
        this.mContext = context;
        this.listData = new ArrayList<>();
        this.mItemClick = mItemClick;
        this.mInflater = LayoutInflater.from(context);

        int w = (mWight - DensityUtil.dp2px(context, 20))/3;
        params = new RelativeLayout.LayoutParams(w, w);
    }

    public boolean isSelectStats() {
        return isSelectStats;
    }

    public void setSelectStats(boolean selectStats) {
        isSelectStats = selectStats;
        notifyDataSetChanged();
    }

    public void setSelect(int p) {
        boolean b = listData.get(p).isSelect();
        if (b) {
            b = false;
        } else {
            b = true;
        }
        listData.get(p).setSelect(b);
        notifyItemChanged(p);
    }

    public void deleteDataById(int id) {
        for (int i=0;i<listData.size();i++) {
            LogUtils.e(">>>i:"+i);
            if (listData.get(i).getPhoto_id()==id) {
                listData.remove(i);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void addData(GetMyUserIndexModel.BodyBean.UserDetailBeanBean.PhotoListBean mList) {
        this.listData.add(mList);
        notifyDataSetChanged();
    }

    public void addData(List<GetMyUserIndexModel.BodyBean.UserDetailBeanBean.PhotoListBean> mList) {
        this.listData.addAll(mList);
        notifyDataSetChanged();
    }

    public List<GetMyUserIndexModel.BodyBean.UserDetailBeanBean.PhotoListBean> getListData() {
        return listData;
    }

    public List<GetMyUserIndexModel.BodyBean.UserDetailBeanBean.PhotoListBean> getSelectListData() {
        List<GetMyUserIndexModel.BodyBean.UserDetailBeanBean.PhotoListBean> list = new ArrayList<>();
        for (int i=0;i<listData.size();i++) {
            if (listData.get(i).isSelect()) {
                list.add(listData.get(i));
            }
        }
        return list;
    }

    public List<GetMyUserIndexModel.BodyBean.UserDetailBeanBean.PhotoListBean> getListData(int n) {
        if (n!=0) {
            Collections.swap(listData, 0, n);
        }
        return listData;
    }

    public GetMyUserIndexModel.BodyBean.UserDetailBeanBean.PhotoListBean getItemData(int p) {
        return listData.get(p);
    }

    @Override
    public PersonalAlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_ablum_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.itemPersonalAblumIv = (ShapeImageView) view.findViewById(R.id.item_personal_ablum_iv);
        viewHolder.itemPersonalAblumSelectIc = (ImageView) view.findViewById(R.id.item_personal_ablum_select_ic);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PersonalAlbumAdapter.ViewHolder holder, final int position) {

        holder.itemPersonalAblumIv.setLayoutParams(params);

        if (position >= listData.size()) {
            holder.itemPersonalAblumIv.setImageResource(R.mipmap.bt_create_addimg);
            if (isSelectStats || position > 4) {
                holder.itemPersonalAblumIv.setVisibility(View.GONE);
            } else {
                holder.itemPersonalAblumIv.setVisibility(View.VISIBLE);
            }
        } else {
            File mFile = new File(listData.get(position).getPic_url());
            if (mFile.exists()) {
                ImageLoader.getInstance().displayImage("file://" + listData.get(position).getPic_url()
                        , holder.itemPersonalAblumIv, MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+listData.get(position).getPic_url()
                        , holder.itemPersonalAblumIv, MyBaseApplication.getApplication().getOptionsNot());
            }

            if (listData.get(position).isSelect()) {
                holder.itemPersonalAblumSelectIc.setImageResource(R.mipmap.select_choose_icon);
            } else {
                holder.itemPersonalAblumSelectIc.setImageResource(R.mipmap.select_notchoose_icon);
            }
        }

        if (isSelectStats) {
            holder.itemPersonalAblumSelectIc.setVisibility(View.VISIBLE);
        } else {
            holder.itemPersonalAblumSelectIc.setVisibility(View.GONE);
        }

        holder.itemPersonalAblumIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClick.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listData.size()>=5 || isSelectStats) {
            return listData.size();
        } else {
            return listData.size() + 1;
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

        ShapeImageView itemPersonalAblumIv;
        ImageView itemPersonalAblumSelectIc;
    }
}
