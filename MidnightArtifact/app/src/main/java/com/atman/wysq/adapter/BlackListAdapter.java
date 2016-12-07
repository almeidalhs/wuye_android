package com.atman.wysq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetFansListModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.widget.SlidingButtonView;
import com.base.baselibs.widget.CustomImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/5/17 17:31
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class BlackListAdapter extends RecyclerView.Adapter<BlackListAdapter.MyViewHolder>
        implements SlidingButtonView.IonSlidingButtonListener {

    private Context context;
    private int widht;

    private IonSlidingViewClickListener mIDeleteBtnClickListener;

    private List<GetFansListModel.BodyBean> mList;

    private SlidingButtonView mMenu = null;
    private String state;

    public BlackListAdapter(Context context, int widht, String state, IonSlidingViewClickListener mListener) {
        this.widht = widht;
        this.context = context;
        this.mIDeleteBtnClickListener = mListener;
        this.state = state;
        mList = new ArrayList<>();
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.itemBlackNameTv.setText(mList.get(position).getNick_name());
        holder.itemBlackLevelTx.setText("Lv." + mList.get(position).getUserLevel());

        ImageLoader.getInstance().displayImage(Common.ImageUrl + mList.get(position).getIcon(),
                holder.itemBlackHeadIv, MyBaseApplication.getApplication().getOptionsNot());

        if (mList.get(position).getVip_level() >= 4) {
            holder.itemBlackVipTx.setVisibility(View.GONE);
            holder.itemBlackSvipIv.setVisibility(View.VISIBLE);
            holder.itemBlackNameTv.setTextColor(context.getResources().getColor(R.color.color_red));
        } else {
            holder.itemBlackNameTv.setTextColor(context.getResources().getColor(R.color.color_2B2B2B));
            holder.itemBlackSvipIv.setVisibility(View.GONE);
            if (mList.get(position).getVip_level() == 0) {
                holder.itemBlackVipTx.setVisibility(View.GONE);
            } else {
                holder.itemBlackVipTx.setText("VIP." + mList.get(position).getVip_level());
                holder.itemBlackVipTx.setVisibility(View.VISIBLE);
            }
        }

        if (mList.get(position).getSex().equals("M")) {
            holder.itemBlackGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            holder.itemBlackGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }

        if (mList.get(position).getVerify_status() == 1) {
            holder.itemBlackVerifyIv.setVisibility(View.VISIBLE);
            holder.itemBlackGenderIv.setVisibility(View.GONE);
        } else {
            holder.itemBlackVerifyIv.setVisibility(View.GONE);
            holder.itemBlackGenderIv.setVisibility(View.VISIBLE);
        }

        //设置内容布局的宽为屏幕宽度
        holder.layoutContent.getLayoutParams().width = widht;
        holder.layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onItemClick(v, n);
                }

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = holder.getLayoutPosition();
                mIDeleteBtnClickListener.onDeleteBtnCilck(v, n);
            }
        });

        holder.mSlidingButtonView.changeWidth(1);
        if (state.equals("1")) {
            holder.btnDelete.setText("取消关注");
        } else {
            holder.btnDelete.setText("移除");
        }
        holder.mSlidingButtonView.closeMenu();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_black_listview, arg0,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView btnDelete;
        public TextView btnEdit;
        public ViewGroup layoutContent;
        public RelativeLayout itemBlackRootRl;
        public SlidingButtonView mSlidingButtonView;

        public CustomImageView itemBlackHeadIv;
        public ImageView itemBlackGenderIv;
        public ImageView itemBlackVerifyIv;
        public TextView itemBlackNameTv;
        public TextView itemBlackLevelTx;
        public TextView itemBlackVipTx;
        public ImageView itemBlackSvipIv;

        public MyViewHolder(View itemView) {
            super(itemView);
            btnDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            btnEdit = (TextView) itemView.findViewById(R.id.tv_edit);
            itemBlackRootRl = (RelativeLayout) itemView.findViewById(R.id.item_black_root_rl);
            layoutContent = (ViewGroup) itemView.findViewById(R.id.layout_content);

            itemBlackHeadIv = (CustomImageView) itemView.findViewById(R.id.item_black_head_iv);
            itemBlackGenderIv = (ImageView) itemView.findViewById(R.id.item_black_gender_iv);
            itemBlackVerifyIv = (ImageView) itemView.findViewById(R.id.item_black_verify_iv);
            itemBlackNameTv = (TextView) itemView.findViewById(R.id.item_black_name_tv);
            itemBlackLevelTx = (TextView) itemView.findViewById(R.id.item_black_level_tx);
            itemBlackVipTx = (TextView) itemView.findViewById(R.id.item_black_vip_tx);
            itemBlackSvipIv = (ImageView) itemView.findViewById(R.id.item_black_svip_iv);
            
            mSlidingButtonView = (SlidingButtonView) itemView;
            mSlidingButtonView.setSlidingButtonListener(BlackListAdapter.this);
        }
    }

    public void addData(List<GetFansListModel.BodyBean> mList) {
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    public GetFansListModel.BodyBean getItemById(int n) {
        return mList.get(n);
    }

    public void removeData(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void clearData(){
        mList.clear();
        notifyDataSetChanged();
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    /**
     * 滑动或者点击了Item监听
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if(menuIsOpen()){
            if(mMenu != slidingButtonView){
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }
    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if(mMenu != null){
            return true;
        }
        return false;
    }

    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);
        void onDeleteBtnCilck(View view, int position);
    }
}

