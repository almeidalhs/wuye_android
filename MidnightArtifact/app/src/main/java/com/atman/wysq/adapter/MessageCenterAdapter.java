package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.bean.TouChuanOtherNotice;
import com.atman.wysq.utils.MyTools;
import com.base.baselibs.iimp.AdapterInterface;

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
public class MessageCenterAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<TouChuanOtherNotice> dataList;
    private AdapterInterface mAdapterInterface;
    private boolean isChange = false;

    public MessageCenterAdapter(Context context, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.dataList = new ArrayList<>();
    }

    public void addBody(TouChuanOtherNotice data) {
        this.dataList.add(data);
        notifyDataSetChanged();
    }

    public void addBody(List<TouChuanOtherNotice> data) {
        this.dataList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearUnreadNum(int p) {
        this.dataList.get(p).setIsEmbalmed(true);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public TouChuanOtherNotice getItem(int position) {
        return dataList.get(position);
    }

    public void setChange(boolean change) {
        isChange = change;
    }

    public void deleteItemById(int id) {
        dataList.remove(id);
        isChange = true;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_messagecenter_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemMessagecenterTypeTx.setText("");
        holder.itemMessagecenterOkBt.setVisibility(View.GONE);
        holder.itemMessagecenterCancelBt.setVisibility(View.GONE);

        holder.itemMessagecenterNameTx.setText(dataList.get(position).getSend_nickName() + ":");

        if (dataList.get(position).getNoticeType() == 1) {
            if (dataList.get(position).getIsEmbalmed()) {
                holder.itemMessagecenterOkBt.setVisibility(View.GONE);
                holder.itemMessagecenterCancelBt.setVisibility(View.GONE);
            } else {
                holder.itemMessagecenterOkBt.setVisibility(View.VISIBLE);
                holder.itemMessagecenterCancelBt.setVisibility(View.VISIBLE);
            }
            if (dataList.get(position).getAddfriendType()==1) {
                holder.itemMessagecenterNameTx.setText(dataList.get(position).getSend_nickName() + ":");
                holder.itemMessagecenterTypeTx.setText("请求加你为好友");
            } else if (dataList.get(position).getAddfriendType()==2) {
                holder.itemMessagecenterNameTx.setText(dataList.get(position).getSend_nickName() + ":");
                holder.itemMessagecenterOkBt.setVisibility(View.GONE);
                holder.itemMessagecenterCancelBt.setVisibility(View.GONE);
                if (dataList.get(position).getPropMessage()==null || dataList.get(position).getPropMessage().isEmpty()) {
                    holder.itemMessagecenterTypeTx.setText("我们已经成为朋友啦");
                } else {
                    holder.itemMessagecenterTypeTx.setText(dataList.get(position).getPropMessage());
                }
            } else if (dataList.get(position).getAddfriendType()==3) {
                holder.itemMessagecenterNameTx.setText(dataList.get(position).getReceive_nickName() + ":");
                holder.itemMessagecenterOkBt.setVisibility(View.GONE);
                holder.itemMessagecenterCancelBt.setVisibility(View.GONE);
                if (dataList.get(position).getPropMessage()==null ||dataList.get(position).getPropMessage().isEmpty()) {
                    holder.itemMessagecenterTypeTx.setText("拒绝与你成为朋友");
                } else {
                    holder.itemMessagecenterTypeTx.setText(dataList.get(position).getPropMessage());
                }
            }
        } else if (dataList.get(position).getNoticeType() == 8 || dataList.get(position).getNoticeType() == 4) {
            holder.itemMessagecenterTypeTx.setText(dataList.get(position).getGiftMessage());
        }
        String time = String.valueOf(dataList.get(position).getTime());
        long str;
        if (time.contains(".")) {
            str = Long.parseLong(time.split("\\.")[0]);
        } else {
            str = Long.parseLong(time);
        }
        holder.itemMessagecenterTimeTx.setText(MyTools.convertTimeS(str));

        holder.itemMessagecenterOkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });
        holder.itemMessagecenterCancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });
        holder.itemMessagecenterRootLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });

        return convertView;
    }

    public void clearData() {
        dataList.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_messagecenter_name_tx)
        TextView itemMessagecenterNameTx;
        @Bind(R.id.item_messagecenter_type_tx)
        TextView itemMessagecenterTypeTx;
        @Bind(R.id.item_messagecenter_time_tx)
        TextView itemMessagecenterTimeTx;
        @Bind(R.id.item_messagecenter_cancel_bt)
        Button itemMessagecenterCancelBt;
        @Bind(R.id.item_messagecenter_ok_bt)
        Button itemMessagecenterOkBt;
        @Bind(R.id.item_messagecenter_root_ll)
        LinearLayout itemMessagecenterRootLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
