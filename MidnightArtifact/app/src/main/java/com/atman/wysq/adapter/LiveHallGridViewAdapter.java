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
import com.atman.wysq.model.response.GetLiveHallModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
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
public class LiveHallGridViewAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetLiveHallModel.BodyBean> body;
    private AdapterInterface mAdapterInterface;
    private int wight;
    private LinearLayout.LayoutParams params;
    private ImageLoader mImageLoader;

    public LiveHallGridViewAdapter(Context context, int wight, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.wight = (wight - DensityUtil.dp2px(context, 6)) / 2;
        this.body = new ArrayList<>();
        params = new LinearLayout.LayoutParams(this.wight,
                this.wight*7/10);
        mImageLoader = ImageLoader.getInstance();
    }

    public void addBody(List<GetLiveHallModel.BodyBean> body) {
        this.body.addAll(body);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public GetLiveHallModel.BodyBean getItem(int position) {
        return body.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_livehall_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetLiveHallModel.BodyBean temp = body.get(position);

        holder.itemLivehallIv.setLayoutParams(params);
        holder.itemLivehallIv.setImageResource(R.color.color_white);
        String url = temp.getPic_url();
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        ImageLoader.getInstance().displayImage(Common.ImageUrl + url, holder.itemLivehallIv
                , MyBaseApplication.getApplication().getOptions());
        holder.itemLivehallNameTv.setText(temp.getUserExt().getNick_name());
        int num = temp.getMember_count();
        if (num == 0) {
            num = 1;
        }
        holder.itemLivehallNumTv.setText(" "+num);
        holder.itemLivehallTitleTv.setText(temp.getRoom_name());

        ImageLoader.getInstance().displayImage(Common.ImageUrl + temp.getUserExt().getIcon(), holder.itemLivehallHeadIv
                , MyBaseApplication.getApplication().getOptionsHead());
        if (temp.getUserExt().getSex().equals("M")) {
            holder.itemLivehallGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            holder.itemLivehallGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }

        if (temp.getUserExt().getVerify_status() == 1) {
            holder.itemLivehallVerifyIv.setVisibility(View.VISIBLE);
            holder.itemLivehallGenderIv.setVisibility(View.GONE);
        } else {
            holder.itemLivehallVerifyIv.setVisibility(View.GONE);
            holder.itemLivehallGenderIv.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    public void clearData() {
        body.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_livehall_iv)
        ShapeImageView itemLivehallIv;
        @Bind(R.id.item_livehall_head_iv)
        ShapeImageView itemLivehallHeadIv;
        @Bind(R.id.item_livehall_gender_iv)
        ImageView itemLivehallGenderIv;
        @Bind(R.id.item_livehall_verify_iv)
        ImageView itemLivehallVerifyIv;
        @Bind(R.id.item_livehall_name_tv)
        TextView itemLivehallNameTv;
        @Bind(R.id.item_livehall_num_tv)
        TextView itemLivehallNumTv;
        @Bind(R.id.item_livehall_title_tv)
        TextView itemLivehallTitleTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
