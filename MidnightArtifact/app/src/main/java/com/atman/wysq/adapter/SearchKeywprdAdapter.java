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
import com.atman.wysq.model.response.SearchKeywordModel;
import com.base.baselibs.iimp.AdapterInterface;

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
public class SearchKeywprdAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<SearchKeywordModel.BodyBean> shop;
    private AdapterInterface mAdapterInterface;
    private int selectId = 0;

    public SearchKeywprdAdapter(Context context, List<SearchKeywordModel.BodyBean> data
            , AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.shop = data;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return shop.size();
    }

    @Override
    public SearchKeywordModel.BodyBean getItem(int position) {
        return shop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_search_keyword_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemSearchKeywordNumTx.setText(""+(position+1));
        holder.itemSearchKeywordTx.setText(shop.get(position).getName());

        return convertView;
    }

    public void clearData() {
        shop.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_search_keyword_num_tx)
        TextView itemSearchKeywordNumTx;
        @Bind(R.id.item_search_keyword_tx)
        TextView itemSearchKeywordTx;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
