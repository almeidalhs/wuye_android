package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetBlogDetailModel;
import com.atman.wysq.utils.Common;
import com.nostra13.universalimageloader.core.ImageLoader;

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
public class BlogDetailGoodsListAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetBlogDetailModel.BodyEntity.GoodsListEntity> shop;

    public BlogDetailGoodsListAdapter(Context context, List<GetBlogDetailModel.BodyEntity.GoodsListEntity> data) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.shop = data;
    }

    @Override
    public int getCount() {
        return shop.size();
    }

    @Override
    public GetBlogDetailModel.BodyEntity.GoodsListEntity getItem(int position) {
        return shop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.part_detail_goodslist_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(Common.ImageUrl+shop.get(position).getPic_img()
                ,holder.partDetailGoodslistIv);
        holder.partDetailGoodslistNameTv.setText(shop.get(position).getTitle());
        holder.partDetailGoodslistPriceTv.setText(""+shop.get(position).getDiscount_price());

        return convertView;
    }

    public void clearData() {
        shop.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.part_detail_goodslist_iv)
        ImageView partDetailGoodslistIv;
        @Bind(R.id.part_detail_goodslist_name_tv)
        TextView partDetailGoodslistNameTv;
        @Bind(R.id.part_detail_goodslist_price_tv)
        TextView partDetailGoodslistPriceTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
