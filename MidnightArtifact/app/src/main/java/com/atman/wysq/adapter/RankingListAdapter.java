package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.AllRankingModel;
import com.atman.wysq.utils.Common;
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
public class RankingListAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<AllRankingModel.BodyBean> shop;

    public RankingListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.shop = new ArrayList<>();
    }

    public void addBody(List<AllRankingModel.BodyBean> shop) {
        if (shop.size()>0) {
            int n = Math.min(3, shop.size());
            for (int i=0;i<n;i++) {
                shop.remove(0);
            }

        }
        this.shop.addAll(shop);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return shop.size();
    }

    @Override
    public AllRankingModel.BodyBean getItem(int position) {
        return shop.get(position);
    }

    public void updataView(int posi, ListView listView, int headNum) {
        int num = headNum;
        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        if (visibleFirstPosi != 0) {
            visibleFirstPosi -= headNum;
            visibleLastPosi -= headNum;
            num = 0;
        }
        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi + num);
            ViewHolder holder = (ViewHolder) view.getTag();

            if (holder == null) {
                return;
            }
//            holder.itemBloglistBrowseTx.setText(shop.get(posi).getView_count() + "");
//            Drawable drawable = null;
//            if (shop.get(posi).getFavorite_id() > 0) {
//                drawable = context.getResources().getDrawable(R.mipmap.square_like_press);
//            } else {
//                drawable = context.getResources().getDrawable(R.mipmap.square_like_default);
//            }
//            /// 这一步必须要做,否则不会显示.
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//            holder.itemBloglistCollectionimgTx.setCompoundDrawables(drawable, null, null, null);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_rankinglist_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final AllRankingModel.BodyBean mBodyEntity = shop.get(position);

        holder.itemRankingNameTv.setText(mBodyEntity.getNick_name());
        holder.itemRankingNumTv.setText(""+(position+4));
        holder.itemRankingLevelTx.setText("Lv." + mBodyEntity.getUserLevel());

        holder.itemRankingHeadIv.setImageURI(Common.ImageUrl + mBodyEntity.getIcon());

        if (mBodyEntity.getVip_level()>=4) {
            holder.itemRankingVipTx.setVisibility(View.GONE);
            holder.itemRankingSvipIv.setVisibility(View.VISIBLE);
            holder.itemRankingNameTv.setTextColor(context.getResources().getColor(R.color.color_red));
        } else {
            holder.itemRankingNameTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
            holder.itemRankingSvipIv.setVisibility(View.GONE);
            if (shop.get(position).getVip_level()==0) {
                holder.itemRankingVipTx.setVisibility(View.GONE);
            } else {
                holder.itemRankingVipTx.setText("VIP."+mBodyEntity.getVip_level());
                holder.itemRankingVipTx.setVisibility(View.VISIBLE);
            }
        }

        if (shop.get(position).getSex().equals("M")) {
            holder.itemRankingGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            holder.itemRankingGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }

        if (shop.get(position).getVerify_status() == 1) {
            holder.itemRankingVerifyIv.setVisibility(View.VISIBLE);
            holder.itemRankingGenderIv.setVisibility(View.GONE);
        } else {
            holder.itemRankingVerifyIv.setVisibility(View.GONE);
            holder.itemRankingGenderIv.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    public void clearData() {
        shop.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_ranking_head_iv)
        SimpleDraweeView itemRankingHeadIv;
        @Bind(R.id.item_ranking_gender_iv)
        ImageView itemRankingGenderIv;
        @Bind(R.id.item_ranking_verify_iv)
        ImageView itemRankingVerifyIv;
        @Bind(R.id.item_ranking_name_tv)
        TextView itemRankingNameTv;
        @Bind(R.id.item_ranking_level_tx)
        TextView itemRankingLevelTx;
        @Bind(R.id.item_ranking_vip_tx)
        TextView itemRankingVipTx;
        @Bind(R.id.item_ranking_svip_iv)
        ImageView itemRankingSvipIv;
        @Bind(R.id.item_ranking_num_tv)
        TextView itemRankingNumTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
