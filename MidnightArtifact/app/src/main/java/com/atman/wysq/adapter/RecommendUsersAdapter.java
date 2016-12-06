package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.RecommendUserModel;
import com.atman.wysq.utils.Common;
import com.base.baselibs.util.DensityUtil;
import com.facebook.drawee.view.SimpleDraweeView;

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
public class RecommendUsersAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<RecommendUserModel.BodyBean> body;
    private RelativeLayout.LayoutParams layoutParams;

    public RecommendUsersAdapter(Context context, int wight) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.body = new ArrayList<>();
        float dis = context.getResources().getDimension(R.dimen.dimen_dp_5);
        int w = wight / 2 - DensityUtil.dp2px(context, (int) dis);
        layoutParams = new RelativeLayout.LayoutParams(w
                , w * 320 / 200);
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
            convertView = layoutInflater.inflate(R.layout.item_recommendusers_gridview_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RecommendUserModel.BodyBean temp = body.get(position);

        holder.itemRecommenduserRootRl.setLayoutParams(layoutParams);
        holder.itemRecommenduserNumTv.setText(" " + temp.getChat_count());
        holder.itemRecommenduserNameTx.setText(temp.getNick_name());
        holder.itemRecommenduserIv.setLayoutParams(layoutParams);
        holder.itemRecommenduserIv.setImageURI(Common.ImageUrl + temp.getPic_url1());

        holder.itemRecommenduserHeadIv.setImageURI(Common.ImageUrl + temp.getIcon());
        if (temp.getSex().equals("M")) {
            holder.itemRecommenduserGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            holder.itemRecommenduserGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }

        if (temp.getVerify_status() == 1) {
            holder.itemRecommenduserVerifyIv.setVisibility(View.VISIBLE);
            holder.itemRecommenduserGenderIv.setVisibility(View.GONE);
        } else {
            holder.itemRecommenduserVerifyIv.setVisibility(View.GONE);
            holder.itemRecommenduserGenderIv.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    public void clearData() {
        body.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_recommenduser_iv)
        SimpleDraweeView itemRecommenduserIv;
        @Bind(R.id.item_recommenduser_num_tv)
        TextView itemRecommenduserNumTv;
        @Bind(R.id.item_recommenduser_head_iv)
        SimpleDraweeView itemRecommenduserHeadIv;
        @Bind(R.id.item_recommenduser_gender_iv)
        ImageView itemRecommenduserGenderIv;
        @Bind(R.id.item_recommenduser_verify_iv)
        ImageView itemRecommenduserVerifyIv;
        @Bind(R.id.item_recommenduser_name_tx)
        TextView itemRecommenduserNameTx;
        @Bind(R.id.item_recommenduser_root_rl)
        RelativeLayout itemRecommenduserRootRl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
