package com.atman.wysq.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetBolgListModel;
import com.atman.wysq.model.response.GetMyCollectionModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.atman.wysq.widget.face.SmileUtils;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
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
public class MyCollectionListAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<GetMyCollectionModel.BodyEntity> shop;
    private AdapterInterface mAdapterInterface;
    private RelativeLayout.LayoutParams params;

    public MyCollectionListAdapter(Context context, int widht, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.shop = new ArrayList<>();
        params = new RelativeLayout.LayoutParams(widht - DensityUtil.dp2px(context, 30),
                (widht - DensityUtil.dp2px(context, 30)) * 200 / 320);
    }

    public void addBody(List<GetMyCollectionModel.BodyEntity> shop) {
        this.shop.addAll(shop);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return shop.size();
    }

    @Override
    public GetMyCollectionModel.BodyEntity getItem(int position) {
        return shop.get(position);
    }

    public void deleteById(int num) {
        shop.remove(num);
        notifyDataSetChanged();
    }

    public int setFavoriteById(int isOk, int num) {
        if (isOk == 1) {//收藏
            shop.get(num).setFavorite_id(1);
            shop.get(num).setFavorite_count(shop.get(num).getFavorite_count() + 1);
        } else {//取消收藏
            shop.get(num).setFavorite_id(0);
            shop.get(num).setFavorite_count(shop.get(num).getFavorite_count() - 1);
        }
        return num;
    }

    public int addBrowse(int num) {
        int p=0;
        for (int i = 0; i < shop.size(); i++) {
            if (num == shop.get(i).getBlog_id()) {
                shop.get(i).setView_count(shop.get(i).getView_count() + 1);
                p=i;
            }
        }
        return p;
    }

    public void updataView(int posi, ListView listView, int headNum) {
        int num = headNum;
        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        if (visibleFirstPosi != 0) {
            visibleFirstPosi -= headNum;
            visibleLastPosi -= headNum;
            num = 0;
        }
        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi+num);
            ViewHolder holder = (ViewHolder) view.getTag();

            if (holder==null) {
                return;
            }
            holder.itemBloglistBrowseTx.setText(shop.get(posi).getView_count()+"");
            Drawable drawable = null;
            if (shop.get(posi).getFavorite_id() > 0) {
                drawable = context.getResources().getDrawable(R.mipmap.square_like_press);
            } else {
                drawable = context.getResources().getDrawable(R.mipmap.square_like_default);
            }
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.itemBloglistCollectionImgTx.setCompoundDrawables(drawable, null, null, null);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_bloglist_listview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetMyCollectionModel.BodyEntity mBodyEntity = shop.get(position);

        if (mBodyEntity.getType() == 2) {//2是精品
            holder.itemBloglistHighlyTx.setVisibility(View.VISIBLE);
        } else {
            holder.itemBloglistHighlyTx.setVisibility(View.GONE);
        }

        if (mBodyEntity.getGoods_id()>0) {
            holder.itemBloglistTopRl.setVisibility(View.GONE);
            holder.itemBloglistBottomLl.setVisibility(View.GONE);
            holder.itemBloglistTitleTx.setVisibility(View.GONE);
            holder.itemBloglistHighlyTx.setVisibility(View.GONE);
        } else {
            holder.itemBloglistTopRl.setVisibility(View.VISIBLE);
            holder.itemBloglistBottomLl.setVisibility(View.VISIBLE);
            holder.itemBloglistTitleTx.setVisibility(View.VISIBLE);
        }

//        if (mBodyEntity.getVerify_status()==1) {
//            holder.itemBloglistVerifyImg.setVisibility(View.VISIBLE);
//            holder.itemBloglistGenderImg.setVisibility(View.GONE);
//        } else {
//            holder.itemBloglistVerifyImg.setVisibility(View.GONE);
//            holder.itemBloglistGenderImg.setVisibility(View.VISIBLE);
//        }

        Drawable drawable = null;
        if (mBodyEntity.getFavorite_id()>0) {
            drawable = context.getResources().getDrawable(R.mipmap.square_like_press);
        } else {
            drawable = context.getResources().getDrawable(R.mipmap.square_like_default);
        }
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        holder.itemBloglistCollectionImgTx.setCompoundDrawables(drawable,null,null,null);

        if (mBodyEntity.getSex().equals("M")) {
            holder.itemBloglistGenderImg.setImageResource(R.mipmap.personal_man_ic);
        } else {
            holder.itemBloglistGenderImg.setImageResource(R.mipmap.personal_weman_ic);
        }
        holder.itemBloglistTitleTx.setText(mBodyEntity.getTitle());
        holder.itemBloglistCommentTx.setText(mBodyEntity.getComment_count() + "");
        holder.itemBloglistBrowseTx.setText(mBodyEntity.getView_count() + "");
        holder.itemBloglistCollectionTx.setText(mBodyEntity.getFavorite_count() + "");
        holder.itemBloglistTimeTx.setText(MyTools.convertTimeS(mBodyEntity.getCreate_time()));

        String temp = mBodyEntity.getContent();
        if (temp.contains("<wysqimg=")) {
            holder.itemBloglistContentTx.setVisibility(View.GONE);
            holder.itemBloglistHaveimgRl.setVisibility(View.VISIBLE);
            String img = temp.substring(temp.indexOf("<wysqimg=") + 9, temp.indexOf("=wysqimg>"));
            String str = temp.substring(0, temp.indexOf("<wysqimg="));
            if (!img.startsWith("/")) {
                img = "/"+img;
            }
            holder.itemBloglistContentimgTx.setText(SmileUtils.getEmotionContent(context, holder.itemBloglistContentimgTx, str));
            ImageLoader.getInstance().displayImage(Common.ImageUrl + img
                    , holder.itemBloglistImgIv, MyBaseApplication.getApplication().getOptionsNot());
            holder.itemBloglistHaveimgRl.setLayoutParams(params);
        } else {
            holder.itemBloglistImgIv.setImageResource(R.mipmap.ic_launcher);
            holder.itemBloglistContentTx.setVisibility(View.VISIBLE);
            holder.itemBloglistHaveimgRl.setVisibility(View.GONE);
            holder.itemBloglistContentTx.setText(SmileUtils.getEmotionContent(context, holder.itemBloglistContentTx, temp));
        }

        holder.itemBloglistRootLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });
        holder.itemBloglistCommentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });
        holder.itemBloglistCollectionLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });
        holder.itemBloglistBrowseLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, position);
            }
        });

        if (mBodyEntity.getAnonymityUser() != null) {//匿名用户
            ImageLoader.getInstance().displayImage(Common.ImageUrl + mBodyEntity.getAnonymityUser().getIcon()
                    , holder.itemBloglistHeadImg, MyBaseApplication.getApplication().getOptionsNot());
            holder.itemBloglistNameTx.setText(mBodyEntity.getAnonymityUser().getNick_name());
            holder.itemBloglistLevelTx.setVisibility(View.GONE);
            holder.itemBloglistGenderImg.setVisibility(View.GONE);
            holder.itemBloglistVerifyImg.setVisibility(View.GONE);
            holder.itemBloglistVipTx.setVisibility(View.GONE);
            holder.itemBloglistSvipIv.setVisibility(View.GONE);
            holder.itemBloglistNameTx.setTextColor(context.getResources().getColor(R.color.color_333333));
        } else {
            holder.itemBloglistLevelTx.setVisibility(View.VISIBLE);
            if (mBodyEntity.getVerify_status()==1) {
                holder.itemBloglistVerifyImg.setVisibility(View.VISIBLE);
                holder.itemBloglistGenderImg.setVisibility(View.GONE);
            } else {
                holder.itemBloglistVerifyImg.setVisibility(View.GONE);
                holder.itemBloglistGenderImg.setVisibility(View.VISIBLE);
            }
            holder.itemBloglistNameTx.setText(mBodyEntity.getUser_name());
            holder.itemBloglistLevelTx.setText("Lv " + mBodyEntity.getUserLevel());
            if (shop.get(position).getVip_level() >= 4) {
                holder.itemBloglistVipTx.setVisibility(View.GONE);
                holder.itemBloglistSvipIv.setVisibility(View.VISIBLE);
            } else {
                holder.itemBloglistSvipIv.setVisibility(View.GONE);
                if (shop.get(position).getVip_level() == 0) {
                    holder.itemBloglistVipTx.setVisibility(View.GONE);
                } else {
                    holder.itemBloglistVipTx.setText("VIP." + shop.get(position).getVip_level());
                    holder.itemBloglistVipTx.setVisibility(View.VISIBLE);
                }
            }
            if (shop.get(position).getVip_level()>=3) {
                holder.itemBloglistNameTx.setTextColor(context.getResources().getColor(R.color.color_red));
            } else {
                holder.itemBloglistNameTx.setTextColor(context.getResources().getColor(R.color.color_333333));
            }
            ImageLoader.getInstance().displayImage(Common.ImageUrl + mBodyEntity.getIcon()
                    , holder.itemBloglistHeadImg, MyBaseApplication.getApplication().getOptionsNot());
        }

        holder.itemBloglistHeadRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shop.get(position).getAnonymityUser() == null) {
                    mAdapterInterface.onItemClick(v, position);
                }
            }
        });

        return convertView;
    }

    public void clearData() {
        shop.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_bloglist_head_img)
        CustomImageView itemBloglistHeadImg;
        @Bind(R.id.item_bloglist_gender_img)
        ImageView itemBloglistGenderImg;
        @Bind(R.id.item_bloglist_verify_img)
        CustomImageView itemBloglistVerifyImg;
        @Bind(R.id.item_bloglist_head_rl)
        RelativeLayout itemBloglistHeadRl;
        @Bind(R.id.item_bloglist_name_tx)
        TextView itemBloglistNameTx;
        @Bind(R.id.item_bloglist_level_tx)
        TextView itemBloglistLevelTx;
        @Bind(R.id.item_bloglist_time_tx)
        TextView itemBloglistTimeTx;
        @Bind(R.id.item_bloglist_top_rl)
        RelativeLayout itemBloglistTopRl;
        @Bind(R.id.item_bloglist_title_tx)
        TextView itemBloglistTitleTx;
        @Bind(R.id.item_bloglist_content_tx)
        TextView itemBloglistContentTx;
        @Bind(R.id.item_bloglist_img_iv)
        ImageView itemBloglistImgIv;
        @Bind(R.id.item_bloglist_contentimg_tx)
        TextView itemBloglistContentimgTx;
        @Bind(R.id.item_bloglist_haveimg_rl)
        RelativeLayout itemBloglistHaveimgRl;
        @Bind(R.id.item_bloglist_rl)
        RelativeLayout itemBloglistRl;
        @Bind(R.id.item_bloglist_bottom_iv)
        ImageView itemBloglistBottomIv;
        @Bind(R.id.item_bloglist_browse_tx)
        TextView itemBloglistBrowseTx;
        @Bind(R.id.item_bloglist_browse_ll)
        LinearLayout itemBloglistBrowseLl;
        @Bind(R.id.item_bloglist_vip_tx)
        TextView itemBloglistVipTx;
        @Bind(R.id.item_bloglist_svip_iv)
        ImageView itemBloglistSvipIv;
        @Bind(R.id.item_bloglist_highly_tx)
        TextView itemBloglistHighlyTx;
        @Bind(R.id.item_bloglist_collection_tx)
        TextView itemBloglistCollectionTx;
        @Bind(R.id.item_bloglist_collectionimg_tx)
        TextView itemBloglistCollectionImgTx;
        @Bind(R.id.item_bloglist_collection_ll)
        LinearLayout itemBloglistCollectionLl;
        @Bind(R.id.item_bloglist_comment_tx)
        TextView itemBloglistCommentTx;
        @Bind(R.id.item_bloglist_comment_ll)
        LinearLayout itemBloglistCommentLl;
        @Bind(R.id.item_bloglist_bottom_ll)
        LinearLayout itemBloglistBottomLl;
        @Bind(R.id.item_bloglist_root_ll)
        LinearLayout itemBloglistRootLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
