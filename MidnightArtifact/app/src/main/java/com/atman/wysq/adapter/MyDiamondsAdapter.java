package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.MyDiamondsRecordModel;
import com.atman.wysq.utils.MyTools;

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
public class MyDiamondsAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<MyDiamondsRecordModel.BodyBean> body;
    private int typeID = 0;

    public MyDiamondsAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        body = new ArrayList<>();
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
        notifyDataSetChanged();
    }

    public void addBody(List<MyDiamondsRecordModel.BodyBean> body) {
        this.body.addAll(body);
        notifyDataSetChanged();
    }

    public List<MyDiamondsRecordModel.BodyBean> getBody() {
        return body;
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public MyDiamondsRecordModel.BodyBean getItem(int position) {
        return body.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_mydiamonds_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyDiamondsRecordModel.BodyBean mBodyEntity = body.get(position);
        if (typeID == 0) {//兑换记录
            holder.itemMydiamondsExchangeLl.setVisibility(View.VISIBLE);
            holder.itemMydiamondsPresentationLl.setVisibility(View.GONE);

            holder.itemMydiamondsExchangeTv.setText("消耗"+mBodyEntity.getConsume_convert_coin()
                    +"钻石，兑换"+mBodyEntity.getChange_count()+"金币");
            holder.itemMydiamondsExchangeTimeTv.setText(MyTools.convertTime(mBodyEntity.getUpdate_time(), "yyyy-MM-dd"));
        } else {//提现记录
            holder.itemMydiamondsExchangeLl.setVisibility(View.GONE);
            holder.itemMydiamondsPresentationLl.setVisibility(View.VISIBLE);

            holder.itemMydiamondsPresentationAccountTv.setText("提现到支付宝："+mBodyEntity.getWalletChannel().getAccount());
            holder.itemMydiamondsPresentationNumTv.setText("-"+mBodyEntity.getMoney());
            holder.itemMydiamondsPresentationTimeTv.setText(MyTools.convertTime(mBodyEntity.getUpdate_time(), "yyyy-MM-dd"));
            if (mBodyEntity.getStatus()==1) {
                holder.itemMydiamondsPresentationStateTv.setText("处理中");
            } else if (mBodyEntity.getStatus()==2) {
                holder.itemMydiamondsPresentationStateTv.setText("已完成");
            } else if (mBodyEntity.getStatus()==3) {
                holder.itemMydiamondsPresentationStateTv.setText("取消");
            } else if (mBodyEntity.getStatus()==4) {
                holder.itemMydiamondsPresentationStateTv.setText("已取消");
            }
        }

        return convertView;
    }

    public void clearData() {
        body.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_mydiamonds_exchange_tv)
        TextView itemMydiamondsExchangeTv;
        @Bind(R.id.item_mydiamonds_exchange_time_tv)
        TextView itemMydiamondsExchangeTimeTv;
        @Bind(R.id.item_mydiamonds_exchange_ll)
        LinearLayout itemMydiamondsExchangeLl;
        @Bind(R.id.item_mydiamonds_presentation_account_tv)
        TextView itemMydiamondsPresentationAccountTv;
        @Bind(R.id.item_mydiamonds_presentation_time_tv)
        TextView itemMydiamondsPresentationTimeTv;
        @Bind(R.id.item_mydiamonds_presentation_num_tv)
        TextView itemMydiamondsPresentationNumTv;
        @Bind(R.id.item_mydiamonds_presentation_state_tv)
        TextView itemMydiamondsPresentationStateTv;
        @Bind(R.id.item_mydiamonds_presentation_ll)
        LinearLayout itemMydiamondsPresentationLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
