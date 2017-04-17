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
import com.atman.wysq.model.response.GetFollowMeModel;
import com.atman.wysq.model.response.GiftListModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.widget.CustomImageView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/8 11:59
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class InvitationAdapter extends BaseAdapter {

    private Context context;
    private List<GetFollowMeModel.BodyBean> data;
    protected LayoutInflater layoutInflater;
    private ViewHolder holder;

    public InvitationAdapter(Context context, List<GetFollowMeModel.BodyBean> data) {
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (data==null) {
            return 0;
        } else {
            return data.size();
        }
    }

    @Override
    public GetFollowMeModel.BodyBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_invitation_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetFollowMeModel.BodyBean temp = data.get(position);

        holder.itemInvitationNameTv.setText(temp.getNick_name());
        holder.itemInvitationLevelTx.setText("Lv." + temp.getUserLevel());

        ImageLoader.getInstance().displayImage(Common.ImageUrl + temp.getIcon(),
                holder.itemInvitationHeadIv, MyBaseApplication.getApplication().getOptionsNot());

        if (temp.getVip_level() >= 4) {
            holder.itemInvitationVipTx.setVisibility(View.GONE);
            holder.itemInvitationSvipIv.setVisibility(View.VISIBLE);
            holder.itemInvitationNameTv.setTextColor(context.getResources().getColor(R.color.color_red));
        } else {
            holder.itemInvitationNameTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
            holder.itemInvitationSvipIv.setVisibility(View.GONE);
            if (temp.getVip_level() == 0) {
                holder.itemInvitationVipTx.setVisibility(View.GONE);
            } else {
                holder.itemInvitationVipTx.setText("VIP." + temp.getVip_level());
                holder.itemInvitationVipTx.setVisibility(View.VISIBLE);
            }
        }

        if (temp.getSex().equals("M")) {
            holder.itemInvitationGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            holder.itemInvitationGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }

        if (temp.getVerify_status() == 1) {
            holder.itemInvitationVerifyIv.setVisibility(View.VISIBLE);
            holder.itemInvitationGenderIv.setVisibility(View.GONE);
        } else {
            holder.itemInvitationVerifyIv.setVisibility(View.GONE);
            holder.itemInvitationGenderIv.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_invitation_head_iv)
        CustomImageView itemInvitationHeadIv;
        @Bind(R.id.item_invitation_gender_iv)
        ImageView itemInvitationGenderIv;
        @Bind(R.id.item_invitation_verify_iv)
        ImageView itemInvitationVerifyIv;
        @Bind(R.id.item_invitation_name_tv)
        TextView itemInvitationNameTv;
        @Bind(R.id.item_invitation_level_tx)
        TextView itemInvitationLevelTx;
        @Bind(R.id.item_invitation_vip_tx)
        TextView itemInvitationVipTx;
        @Bind(R.id.item_invitation_svip_iv)
        ImageView itemInvitationSvipIv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
