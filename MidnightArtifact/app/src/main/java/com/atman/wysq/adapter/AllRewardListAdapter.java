package com.atman.wysq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetRewardListNewModel;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.widget.CustomImageView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tangbingliang on 17*4/5*4/52.
 */

public class AllRewardListAdapter extends RecyclerView.Adapter<AllRewardListAdapter.ViewHolder> {

    @Bind(R.id.item_rewardlistview_head_img)
    CustomImageView itemRewardlistviewHeadImg;
    @Bind(R.id.item_rewardlistview_name_tv)
    TextView itemRewardlistviewNameTv;
    @Bind(R.id.item_rewardlistview_level_tx)
    TextView itemRewardlistviewLevelTx;
    @Bind(R.id.item_rewardlistview_name_ll)
    LinearLayout itemRewardlistviewNameLl;
    @Bind(R.id.item_rewardlistview_time_tv)
    TextView itemRewardlistviewTimeTv;
    @Bind(R.id.item_rewardlistview_num_tv)
    TextView itemRewardlistviewNumTv;
    private Context mContext;
    private LayoutInflater mInflater;
    private AdapterInterface mItemClick;
    private List<GetRewardListNewModel.BodyBean> listData;
    private RelativeLayout.LayoutParams params;
    private String str;

    public AllRewardListAdapter(Context context, int mWight, AdapterInterface mItemClick) {
        this.mContext = context;
        this.listData = new ArrayList<>();
        this.mItemClick = mItemClick;
        this.mInflater = LayoutInflater.from(context);

        int w = (mWight - DensityUtil.dp2px(context, 30)) * 4 / 5;
        params = new RelativeLayout.LayoutParams(w, w * 190 / 340);
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void addData(List<GetRewardListNewModel.BodyBean> mList) {
        this.listData.addAll(mList);

        notifyDataSetChanged();
    }

    public List<GetRewardListNewModel.BodyBean> getListData() {
        return listData;
    }

    public List<GetRewardListNewModel.BodyBean> getListData(int n) {
        if (n != 0) {
            Collections.swap(listData, 0, n);
        }
        return listData;
    }

    public GetRewardListNewModel.BodyBean getItemData(int p) {
        return listData.get(p);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_rewardlistview_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.itemRewardlistviewHeadImg.setImageURI(Common.ImageUrl+listData.get(position).getIcon());
        holder.itemRewardlistviewNameTv.setText(listData.get(position).getUser_name());
        holder.itemRewardlistviewLevelTx.setText("Lv "+listData.get(position).getUserLevel());
//        holder.itemRewardlistviewTimeTv.setText(MyTools.convertTimeS(listData.get(position).getCreate_time()));
        holder.itemRewardlistviewNumTv.setText("  "+listData.get(position).getGift_num());
        holder.itemRewardlistviewGiftImg.setImageURI(Common.ImageUrl+listData.get(position).getPic_url());

        holder.itemRewardlistviewHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClick.onItemClick(v, position);
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_rewardlistview_head_img)
        SimpleDraweeView itemRewardlistviewHeadImg;
        @Bind(R.id.item_rewardlistview_gift_img)
        SimpleDraweeView itemRewardlistviewGiftImg;
        @Bind(R.id.item_rewardlistview_name_tv)
        TextView itemRewardlistviewNameTv;
        @Bind(R.id.item_rewardlistview_level_tx)
        TextView itemRewardlistviewLevelTx;
        @Bind(R.id.item_rewardlistview_name_ll)
        LinearLayout itemRewardlistviewNameLl;
        @Bind(R.id.item_rewardlistview_time_tv)
        TextView itemRewardlistviewTimeTv;
        @Bind(R.id.item_rewardlistview_num_tv)
        TextView itemRewardlistviewNumTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
