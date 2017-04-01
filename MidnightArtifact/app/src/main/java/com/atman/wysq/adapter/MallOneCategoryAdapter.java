package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.MallCategoryModel;
import com.base.baselibs.iimp.AdapterInterface;

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
public class MallOneCategoryAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<MallCategoryModel.BodyBean> shop;
    private AdapterInterface mAdapterInterface;
    private int selectId = 0;

    public MallOneCategoryAdapter(Context context, List<MallCategoryModel.BodyBean> data
            , AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.shop = data;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return shop.size();
    }

    @Override
    public MallCategoryModel.BodyBean getItem(int position) {
        return shop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_mall_one_category_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemMallCategoryOneTv.setText(shop.get(position).getName());
        if (shop.get(position).getDescription()!=null && shop.get(position).getImg_bg_url()!=null) {
            holder.itemMallCategoryOneTipTv.setVisibility(View.VISIBLE);
            holder.itemMallCategoryOneTipTv.setText(shop.get(position).getDescription());
        } else {
            holder.itemMallCategoryOneTipTv.setVisibility(View.GONE);
        }

        if (shop.get(position).getImg_bg_url()==null || shop.get(position).getImg_url()==null) {
            holder.itemMallCategoryOneBgIv.setVisibility(View.GONE);
            if (position == selectId) {
                holder.itemMallCategoryOneTv.setTextColor(context.getResources().getColor(R.color.color_fed676));
            } else {
                holder.itemMallCategoryOneTv.setTextColor(context.getResources().getColor(R.color.color_787878));
            }
        } else {
            holder.itemMallCategoryOneTv.setTextColor(context.getResources().getColor(R.color.color_white));
            holder.itemMallCategoryOneTipTv.setTextColor(context.getResources().getColor(R.color.color_white));

            holder.itemMallCategoryOneBgIv.setVisibility(View.VISIBLE);
            holder.itemMallCategoryOneBgIv.setBackgroundResource(R.color.color_red);
        }

        if (position == selectId) {
            holder.itemMallCategoryOneIv.setBackgroundResource(R.color.color_f5dc91);
            holder.itemMallCategoryOneRootLl.setBackgroundResource(R.color.all_bg);
        } else {
            holder.itemMallCategoryOneRootLl.setBackgroundResource(R.color.color_white);
            holder.itemMallCategoryOneIv.setBackgroundResource(R.color.color_white);
        }

        return convertView;
    }

    public void clearData() {
        shop.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_mall_category_one_iv)
        ImageView itemMallCategoryOneIv;
        @Bind(R.id.item_mall_category_one_bg_iv)
        ImageView itemMallCategoryOneBgIv;
        @Bind(R.id.item_mall_category_one_tv)
        TextView itemMallCategoryOneTv;
        @Bind(R.id.item_mall_category_one_tip_tv)
        TextView itemMallCategoryOneTipTv;
        @Bind(R.id.item_mall_category_one_root_ll)
        LinearLayout itemMallCategoryOneRootLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
