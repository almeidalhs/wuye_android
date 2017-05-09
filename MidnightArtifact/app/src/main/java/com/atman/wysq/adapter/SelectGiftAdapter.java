package com.atman.wysq.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GiftListModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
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
public class SelectGiftAdapter extends BaseAdapter {

    private Context context;
    private List<GiftListModel.BodyEntity> data;
    private AdapterInterface mAdapterInterface;
    protected LayoutInflater layoutInflater;
    private ViewHolder holder;
    private LinearLayout.LayoutParams params,params2;
    private int myCion;
    private int pageSize;

    public SelectGiftAdapter(Context context,int mWight, List<GiftListModel.BodyEntity> data, int pageSize, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.data = data;
        this.pageSize = pageSize;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        int n = pageSize/2;
        int w = (mWight - DensityUtil.dp2px(context,5)*(n-1))/n;
        params = new LinearLayout.LayoutParams(w - DensityUtil.dp2px(context,30), w*330/270);
    }

    public void setMyCion(int myCion) {
        this.myCion = myCion;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (data.size()>=pageSize) {
            return pageSize;
        } else {
            return data.size();
        }
    }

    @Override
    public GiftListModel.BodyEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_selectgift_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GiftListModel.BodyEntity temp = data.get(position);

        holder.itemSelectgiftCionTv.setText(temp.getPrice()+"金币");
        holder.itemSelectgiftNameTv.setText(temp.getName());
        holder.itemSelectgiftIv.setLayoutParams(params);
        if (temp.getPrice() <= myCion) {
            ImageLoader.getInstance().displayImage(Common.ImageUrl+temp.getPic_url()
                    , holder.itemSelectgiftIv, MyBaseApplication.getApplication().getOptions());
//            holder.itemSelectgiftIv.setImageURI(Common.ImageUrl+temp.getPic_url());
        } else {
            ImageLoader.getInstance().displayImage(Common.ImageUrl+temp.getPic_url()
                    , holder.itemSelectgiftIv, MyBaseApplication.getApplication().getOptions());
//            holder.itemSelectgiftIv.setImageURI(Common.ImageUrl+temp.getGray_pic_url());
        }

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_selectgift_iv)
        SimpleDraweeView itemSelectgiftIv;
        @Bind(R.id.item_selectgift_cion_tv)
        TextView itemSelectgiftCionTv;
        @Bind(R.id.item_selectgift_name_tv)
        TextView itemSelectgiftNameTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
