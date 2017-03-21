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
import com.atman.wysq.model.response.DiscoverNewModel;
import com.atman.wysq.model.response.GetRewardListModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.widget.RoundImageView;
import com.facebook.drawee.view.SimpleDraweeView;
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
public class DiscoverNewGridViewAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private AdapterInterface mItemClick;
    private List<DiscoverNewModel> listData;
    private RelativeLayout.LayoutParams params;
    private String str;

    public DiscoverNewGridViewAdapter(Context context, int mWight, String str, AdapterInterface mItemClick){
        this.context = context;
        this.listData = new ArrayList<>();
        this.mItemClick = mItemClick;
        this.str = str;
        this.layoutInflater = LayoutInflater.from(context);

        int w = mWight/2;
        params = new RelativeLayout.LayoutParams(w, w * 137 / 204);
    }

    @Override
    public int getCount() {

//        return listData.size();
        return 15;
    }

    @Override
    public DiscoverNewModel getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_discovernew_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemDiscovernewRootRl.setLayoutParams(params);
        holder.itemDiscovernewIv.setLayoutParams(params);
        holder.itemDiscovernewNameTv.setText(str);

        holder.itemDiscovernewIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClick.onItemClick(view, position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_discovernew_iv)
        SimpleDraweeView itemDiscovernewIv;
        @Bind(R.id.item_discovernew_name_tv)
        TextView itemDiscovernewNameTv;
        @Bind(R.id.item_discovernew_num_tv)
        TextView itemDiscovernewNumTv;
        @Bind(R.id.item_discovernew_root_rl)
        RelativeLayout itemDiscovernewRootRl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
