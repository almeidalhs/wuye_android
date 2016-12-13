package com.atman.wysq.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atman.wysq.R;
import com.atman.wysq.model.response.RecommendUserModel;
import com.atman.wysq.utils.Common;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.widget.CustomImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/12 13:44
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class DiscoverFindAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<RecommendUserModel.BodyBean> body;
    private LinearLayout.LayoutParams layoutParams;

    public DiscoverFindAdapter(Context context, int wight) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.body = new ArrayList<>();
        float dis = context.getResources().getDimension(R.dimen.dimen_dp_5);
        int w = wight / 2 - DensityUtil.dp2px(context, (int) dis);
        layoutParams = new LinearLayout.LayoutParams(DensityUtil.dp2px(context, 78)
                , DensityUtil.dp2px(context, 120));
    }

    public void addBody(List<RecommendUserModel.BodyBean> body) {
        this.body.addAll(body);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public RecommendUserModel.BodyBean getItem(int position) {
        return body.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_discover_find_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RecommendUserModel.BodyBean temp = body.get(position);

        holder.itemDiscoverFindLl.setPadding(0,DensityUtil.dp2px(context, 8)
                ,0, DensityUtil.dp2px(context, 8) );

        holder.itemDiscoverFindIv.setLayoutParams(layoutParams);
        holder.itemDiscoverFindIv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ImageLoader.getInstance().displayImage(Common.ImageUrl + temp.getPic_url1(), holder.itemDiscoverFindIv);

        return convertView;
    }

    public void clearData() {
        body.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_discover_find_iv)
        CustomImageView itemDiscoverFindIv;
        @Bind(R.id.item_discover_find_ll)
        LinearLayout itemDiscoverFindLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
