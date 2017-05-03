package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.ReplayListModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.atman.wysq.widget.face.SmileUtils;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
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
public class ReplayListAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<ReplayListModel.BodyBean> shop;
    private AdapterInterface mAdapterInterface;
    private RelativeLayout.LayoutParams params;
    private int mStateId;

    public ReplayListAdapter(Context context, int widht, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.shop = new ArrayList<>();
        params = new RelativeLayout.LayoutParams(widht - DensityUtil.dp2px(context, 30),
                (widht - DensityUtil.dp2px(context, 30)) * 200 / 320);
    }

    public void setmStateId(int mStateId) {
        this.mStateId = mStateId;
    }

    public void addBody(List<ReplayListModel.BodyBean> shop) {
        this.shop.addAll(shop);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return shop.size();
    }

    @Override
    public ReplayListModel.BodyBean getItem(int position) {
        return shop.get(position);
    }

    public void deleteById(int num) {
        shop.remove(num);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_replaylist_listview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ReplayListModel.BodyBean mBodyEntity = shop.get(position);

        if (mBodyEntity.getBlog() != null) {
            if (mStateId == 0) {
                holder.itemRelayTimeTx.setText(MyTools.convertTimeS(mBodyEntity.getBlog().getCreate_time()));
            } else {
                holder.itemRelayTimeTx.setText(MyTools.convertTimeS(mBodyEntity.getBlog().getUpdate_time()));
            }

            holder.itemRelayRootLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapterInterface.onItemClick(v, position);
                }
            });

            if (mBodyEntity.getBlog().getAnonymityUser() != null) {//匿名用户
                ImageLoader.getInstance().displayImage(Common.ImageUrl + mBodyEntity.getBlog().getAnonymityUser().getIcon()
                        , holder.itemRelayHeadImg, MyBaseApplication.getApplication().getOptionsNot());
                holder.itemRelayNameTx.setText(mBodyEntity.getBlog().getAnonymityUser().getNick_name());
                holder.itemRelayLevelTx.setVisibility(View.GONE);
                holder.itemRelayGenderImg.setVisibility(View.GONE);
                holder.itemRelayVerifyImg.setVisibility(View.GONE);
                holder.itemRelayVipTx.setVisibility(View.GONE);
                holder.itemRelaySvipIv.setVisibility(View.GONE);
                holder.itemRelayNameTx.setTextColor(context.getResources().getColor(R.color.color_333333));
            } else {
                holder.itemRelayLevelTx.setVisibility(View.VISIBLE);
                if (mBodyEntity.getBlog().getVerify_status() == 1) {
                    holder.itemRelayVerifyImg.setVisibility(View.VISIBLE);
                    holder.itemRelayGenderImg.setVisibility(View.GONE);
                } else {
                    holder.itemRelayVerifyImg.setVisibility(View.GONE);
                    holder.itemRelayGenderImg.setVisibility(View.VISIBLE);
                }
                holder.itemRelayNameTx.setText(mBodyEntity.getBlog().getUser_name());
                holder.itemRelayLevelTx.setText("Lv " + mBodyEntity.getBlog().getUserLevel());
                if (shop.get(position).getBlog().getVip_level() >= 4) {
                    holder.itemRelayVipTx.setVisibility(View.GONE);
                    holder.itemRelaySvipIv.setVisibility(View.VISIBLE);
                } else {
                    holder.itemRelaySvipIv.setVisibility(View.GONE);
                    if (shop.get(position).getBlog().getVip_level() == 0) {
                        holder.itemRelayVipTx.setVisibility(View.GONE);
                    } else {
                        holder.itemRelayVipTx.setText("VIP." + shop.get(position).getBlog().getVip_level());
                        holder.itemRelayVipTx.setVisibility(View.VISIBLE);
                    }
                }
                if (shop.get(position).getBlog().getVip_level() >= 3) {
                    holder.itemRelayNameTx.setTextColor(context.getResources().getColor(R.color.color_red));
                } else {
                    holder.itemRelayNameTx.setTextColor(context.getResources().getColor(R.color.color_333333));
                }
                ImageLoader.getInstance().displayImage(Common.ImageUrl + mBodyEntity.getBlog().getIcon()
                        , holder.itemRelayHeadImg, MyBaseApplication.getApplication().getOptionsNot());
            }

            holder.itemRelayNameTwoTx.setText(mBodyEntity.getComment().getUser_name()+":");
            holder.itemRelayContentTwoTx.setText(SmileUtils.getEmotionContent(context
                    , holder.itemRelayContentTwoTx, mBodyEntity.getBlog().getTitle()));
            holder.itemRelayContentTx.setText(SmileUtils.getEmotionContent(context
                    , holder.itemRelayContentTx, mBodyEntity.getComment().getContent()));

            holder.itemRelayHeadRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (shop.get(position).getBlog().getAnonymityUser() == null) {
                        mAdapterInterface.onItemClick(v, position);
                    }
                }
            });
        }

        return convertView;
    }

    public void clearData() {
        shop.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_relay_head_img)
        CustomImageView itemRelayHeadImg;
        @Bind(R.id.item_relay_gender_img)
        ImageView itemRelayGenderImg;
        @Bind(R.id.item_relay_verify_img)
        ImageView itemRelayVerifyImg;
        @Bind(R.id.item_relay_head_rl)
        RelativeLayout itemRelayHeadRl;
        @Bind(R.id.item_relay_name_tx)
        TextView itemRelayNameTx;
        @Bind(R.id.item_relay_level_tx)
        TextView itemRelayLevelTx;
        @Bind(R.id.item_relay_vip_tx)
        TextView itemRelayVipTx;
        @Bind(R.id.item_relay_svip_iv)
        ImageView itemRelaySvipIv;
        @Bind(R.id.item_relay_type_tx)
        TextView itemRelayTypeTx;
        @Bind(R.id.item_relay_top_rl)
        RelativeLayout itemRelayTopRl;
        @Bind(R.id.item_relay_content_tx)
        TextView itemRelayContentTx;
        @Bind(R.id.item_relay_name_two_tx)
        TextView itemRelayNameTwoTx;
        @Bind(R.id.item_relay_content_two_tx)
        TextView itemRelayContentTwoTx;
        @Bind(R.id.item_relay_time_tx)
        TextView itemRelayTimeTx;
        @Bind(R.id.item_relay_root_ll)
        LinearLayout itemRelayRootLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
