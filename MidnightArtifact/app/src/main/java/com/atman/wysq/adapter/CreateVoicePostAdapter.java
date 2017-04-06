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
import com.atman.wysq.model.response.GoodsListModel;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
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
public class CreateVoicePostAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GoodsListModel> goodsList;
    private AdapterInterface mAdapterInterface;

    public CreateVoicePostAdapter(Context context, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.goodsList = new ArrayList<>();
    }

    public void addBody(List<GoodsListModel> shop) {
        this.goodsList.addAll(shop);
        notifyDataSetChanged();
    }

    public void addBody(GoodsListModel shop) {
        this.goodsList.add(shop);
        notifyDataSetChanged();
    }

    public List<GoodsListModel> getGoodsList() {
        return goodsList;
    }

    public void deleteDataById(int n){
        getGoodsList().remove(n);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (goodsList.size()==0) {
            return 1;
        } else if (goodsList.size()>3) {
            return 3;
        } else {
            return goodsList.size();
        }
    }

    @Override
    public GoodsListModel getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_create_imageview_childtwo_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (goodsList.size()>=3 || (goodsList.size()>0 && position!=goodsList.size()-1)) {
            holder.itemChildtwoAddLl.setVisibility(View.GONE);
        } else {
            holder.itemChildtwoAddLl.setVisibility(View.VISIBLE);
        }

        if (goodsList.size()==0) {
            holder.itemChildtwoLl.setVisibility(View.GONE);
            holder.itemChildtwoDeleteIv.setVisibility(View.GONE);
        } else {
            holder.itemChildtwoLl.setVisibility(View.VISIBLE);
            holder.itemChildtwoDeleteIv.setVisibility(View.VISIBLE);

            ImageLoader.getInstance().displayImage(Common.ImageUrl+goodsList.get(position).getPic_img()
                    , holder.itemChildtwoHeadIv);
            holder.itemChildtwoGoodsNameTv.setText(goodsList.get(position).getTitle());
            holder.itemChildtwoGoodsPriceTv.setText(""+goodsList.get(position).getDiscount_price());
        }

        holder.itemChildtwoAddLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });

        holder.itemChildtwoDeleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });

        return convertView;
    }

    public void clearData() {
        goodsList.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_childtwo_head_iv)
        ImageView itemChildtwoHeadIv;
        @Bind(R.id.item_childtwo_goods_name_tv)
        TextView itemChildtwoGoodsNameTv;
        @Bind(R.id.item_childtwo_goods_price_tv)
        TextView itemChildtwoGoodsPriceTv;
        @Bind(R.id.item_childtwo_ll)
        LinearLayout itemChildtwoLl;
        @Bind(R.id.item_childtwo_delete_iv)
        ImageView itemChildtwoDeleteIv;
        @Bind(R.id.item_childtwo_add_ll)
        LinearLayout itemChildtwoAddLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
