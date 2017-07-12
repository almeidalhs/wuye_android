package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.RecommendUserModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.widget.ShapeImageView;
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
public class RecommendUsersAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<RecommendUserModel.BodyBean> body;
    private AbsListView.LayoutParams layoutParamsRl;
    private RelativeLayout.LayoutParams layoutParams;

    public RecommendUsersAdapter(Context context, int wight) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.body = new ArrayList<>();
        float dis = context.getResources().getDimension(R.dimen.dimen_dp_5);
        int w = wight / 2 - DensityUtil.dp2px(context, (int) dis);
        layoutParamsRl = new AbsListView.LayoutParams(w
                , w * 320 / 200);
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

        holder.itemRecommenduserRootRl.setLayoutParams(layoutParamsRl);
        holder.itemRecommenduserNumTv.setText(" " + temp.getChat_count());
        holder.itemRecommenduserNameTx.setText(temp.getNick_name());
        holder.itemRecommenduserIv.setLayoutParams(layoutParams);
        ImageLoader.getInstance().displayImage(Common.ImageUrl + temp.getPic_url1(), holder.itemRecommenduserIv
                , MyBaseApplication.getApplication().getOptions());

        ImageLoader.getInstance().displayImage(Common.ImageUrl + temp.getIcon(), holder.itemRecommenduserHeadIv
                , MyBaseApplication.getApplication().getOptionsHead());
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
        ShapeImageView itemRecommenduserIv;
        @Bind(R.id.item_recommenduser_num_tv)
        TextView itemRecommenduserNumTv;
        @Bind(R.id.item_recommenduser_head_iv)
        ShapeImageView itemRecommenduserHeadIv;
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
