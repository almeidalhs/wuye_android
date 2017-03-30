package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetReportListModel;

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
public class ReportListAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetReportListModel.BodyBean> body;

    public ReportListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        body = new ArrayList<>();
    }

    public void setBody(List<GetReportListModel.BodyBean> body) {
        this.body = body;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public GetReportListModel.BodyBean getItem(int position) {
        return body.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GetReportListModel.BodyBean mBodyEntity = body.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_report_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemReportTx.setText(body.get(position).getDescription());

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_report_tx)
        TextView itemReportTx;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
