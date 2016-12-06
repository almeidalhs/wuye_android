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
import com.atman.wysq.model.response.GetFansListModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
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
public class GetFansListAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetFansListModel.BodyBean> body;
    private AdapterInterface mAdapterInterface;

    public GetFansListAdapter(Context context, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        this.body = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    public void addBody(List<GetFansListModel.BodyBean> body) {
        this.body.addAll(body);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public GetFansListModel.BodyBean getItem(int position) {
        return body.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_myfanslist_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final GetFansListModel.BodyBean mBodyEntity = body.get(position);

        holder.itemMyfansNameTv.setText(mBodyEntity.getNick_name());
        holder.itemMyfansLevelTx.setText("Lv." + mBodyEntity.getUserLevel());

        ImageLoader.getInstance().displayImage(Common.ImageUrl + mBodyEntity.getIcon(),
                holder.itemMyfansHeadIv, MyBaseApplication.getApplication().getOptionsNot());

        if (mBodyEntity.getVip_level() >= 4) {
            holder.itemMyfansVipTx.setVisibility(View.GONE);
            holder.itemMyfansSvipIv.setVisibility(View.VISIBLE);
            holder.itemMyfansNameTv.setTextColor(context.getResources().getColor(R.color.color_red));
        } else {
            holder.itemMyfansNameTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
            holder.itemMyfansSvipIv.setVisibility(View.GONE);
            if (mBodyEntity.getVip_level() == 0) {
                holder.itemMyfansVipTx.setVisibility(View.GONE);
            } else {
                holder.itemMyfansVipTx.setText("VIP." + mBodyEntity.getVip_level());
                holder.itemMyfansVipTx.setVisibility(View.VISIBLE);
            }
        }

        if (mBodyEntity.getSex().equals("M")) {
            holder.itemMyfansGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            holder.itemMyfansGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }

        if (mBodyEntity.getVerify_status() == 1) {
            holder.itemMyfansVerifyIv.setVisibility(View.VISIBLE);
            holder.itemMyfansGenderIv.setVisibility(View.GONE);
        } else {
            holder.itemMyfansVerifyIv.setVisibility(View.GONE);
            holder.itemMyfansGenderIv.setVisibility(View.VISIBLE);
        }

        holder.itemMyfansRootLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });

        return convertView;
    }

    public void clearData() {
        body.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_myfans_head_iv)
        CustomImageView itemMyfansHeadIv;
        @Bind(R.id.item_myfans_gender_iv)
        ImageView itemMyfansGenderIv;
        @Bind(R.id.item_myfans_verify_iv)
        ImageView itemMyfansVerifyIv;
        @Bind(R.id.item_myfans_name_tv)
        TextView itemMyfansNameTv;
        @Bind(R.id.item_myfans_level_tx)
        TextView itemMyfansLevelTx;
        @Bind(R.id.item_myfans_vip_tx)
        TextView itemMyfansVipTx;
        @Bind(R.id.item_myfans_svip_iv)
        ImageView itemMyfansSvipIv;
        @Bind(R.id.item_myfans_root_ll)
        LinearLayout itemMyfansRootLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
