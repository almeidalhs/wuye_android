package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.MallModel;
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
public class RecommendForYouAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<MallModel.BodyBean.GoodsListBean> body;
    private int wight;
    private LinearLayout.LayoutParams params;

    public RecommendForYouAdapter(Context context, int wight) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.wight = (wight - DensityUtil.dp2px(context, 6)) / 2;
        this.body = new ArrayList<>();
        params = new LinearLayout.LayoutParams(this.wight,
                this.wight);
    }

    public void addBody(List<MallModel.BodyBean.GoodsListBean> body) {
        this.body.addAll(body);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public MallModel.BodyBean.GoodsListBean getItem(int position) {
        return body.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_recommendforyou_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemRecommendforyouIv.setLayoutParams(params);
        holder.itemRecommendforyouIv.setImageResource(R.color.color_white);
        ImageLoader.getInstance().displayImage(Common.ImageUrl + body.get(position).getPic_img()
                , holder.itemRecommendforyouIv, MyBaseApplication.getApplication().getOptions());
        holder.itemRecommendforyouNameTx.setText(body.get(position).getTitle());
        holder.itemRecommendforyouPriceTx.setText("¥ " + body.get(position).getDiscount_price());

        return convertView;
    }

    public void clearData() {
        body.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_recommendforyou_iv)
        ShapeImageView itemRecommendforyouIv;
        @Bind(R.id.item_recommendforyou_name_tx)
        TextView itemRecommendforyouNameTx;
        @Bind(R.id.item_recommendforyou_price_tx)
        TextView itemRecommendforyouPriceTx;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
