package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.MallGetCategoryResponseModel;
import com.atman.wysq.model.response.MallGetTwoCategoryResponseModel;
import com.atman.wysq.model.response.MallModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
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
public class MallCategoryListAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    protected LayoutInflater layoutInflater;
    private List<MallModel.BodyBean.CategoryListBean> oneCategory;
    private List<MallModel.BodyBean.CategoryListBean.LeafCategorieListBean> twoCategory;
    private CategoryAdapterInterface mAdapterInterface;
    private int mWight;
    private LinearLayout.LayoutParams params1,params2,params3;
    private RelativeLayout.LayoutParams params4;
    private int[] imgs = {R.mipmap.category_one
            ,R.mipmap.category_two
            ,R.mipmap.category_three
            ,R.mipmap.category_four};

    public MallCategoryListAdapter(Context context, int mWight, List<MallModel.BodyBean.CategoryListBean> shop,
                                   CategoryAdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflater = LayoutInflater.from(context);
        this.oneCategory = shop;
        this.mWight = mWight;

        twoCategory = new ArrayList<>();

        params1 = new LinearLayout.LayoutParams((mWight - DensityUtil.dp2px(context,13))/2,
                ((mWight - DensityUtil.dp2px(context,13))/2) * 206 / 309);
        params2 = new LinearLayout.LayoutParams((mWight - DensityUtil.dp2px(context,16))/3,
                (mWight - DensityUtil.dp2px(context,16))/3);
        params4 = new RelativeLayout.LayoutParams((mWight - DensityUtil.dp2px(context,20)),
                ((mWight - DensityUtil.dp2px(context,20))) * 38 / 676);
        params3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int getCount() {
        return oneCategory.size();
    }

    @Override
    public MallModel.BodyBean.CategoryListBean getItem(int position) {
        return oneCategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_mallcategory_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemTwocategoryTitleTx.setText("------ "+oneCategory.get(position).getName()+" ------");
        holder.itemTwocategoryTitleIv.setImageResource(imgs[position % 4]);
        holder.itemTwocategoryTitleIv.setLayoutParams(params4);

        SimpleDraweeView[] iv = {holder.itemTwocategoryOneIv, holder.itemTwocategoryTwoIv
                , holder.itemTwocategoryThreeIv, holder.itemTwocategoryFourIv, holder.itemTwocategoryFiveIv};

        twoCategory = oneCategory.get(position).getLeafCategorieList();

        for (int i=0;i<5;i++) {
            iv[i].setVisibility(View.GONE);
        }
        if (twoCategory!=null) {
            for (int i=0,j=0;i<twoCategory.size();i++,j++) {
                if (j<5) {
                    iv[j].setImageResource(R.color.color_white);
                    iv[j].setVisibility(View.VISIBLE);
                    if (j==0 || j==1) {
                        iv[j].setLayoutParams(params1);
                    } else {
                        iv[j].setLayoutParams(params2);
                    }
                    iv[j].setImageURI(Common.ImageUrl + twoCategory.get(i).getAd_club_pic());
                }
            }
        }

        holder.itemTwocategoryOneIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oneCategory.get(position).getLeafCategorieList()!=null
                        && oneCategory.get(position).getLeafCategorieList().size()>=1) {
                    mAdapterInterface.onItemClick(v
                            , oneCategory.get(position).getLeafCategorieList().get(0).getGoods_category_id()
                            , oneCategory.get(position).getLeafCategorieList().get(0).getName());
                }
            }
        });

        holder.itemTwocategoryTwoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oneCategory.get(position).getLeafCategorieList()!=null
                        && oneCategory.get(position).getLeafCategorieList().size()>=2) {
                    mAdapterInterface.onItemClick(v
                            , oneCategory.get(position).getLeafCategorieList().get(1).getGoods_category_id()
                            , oneCategory.get(position).getLeafCategorieList().get(1).getName());
                }
            }
        });

        holder.itemTwocategoryThreeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oneCategory.get(position).getLeafCategorieList()!=null
                        && oneCategory.get(position).getLeafCategorieList().size()>=3) {
                    mAdapterInterface.onItemClick(v
                            , oneCategory.get(position).getLeafCategorieList().get(2).getGoods_category_id()
                            , oneCategory.get(position).getLeafCategorieList().get(2).getName());
                }
            }
        });

        holder.itemTwocategoryFourIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oneCategory.get(position).getLeafCategorieList()!=null
                        && oneCategory.get(position).getLeafCategorieList().size()>=4) {
                    mAdapterInterface.onItemClick(v
                            , oneCategory.get(position).getLeafCategorieList().get(3).getGoods_category_id()
                            , oneCategory.get(position).getLeafCategorieList().get(3).getName());
                }
            }
        });

        holder.itemTwocategoryFiveIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oneCategory.get(position).getLeafCategorieList()!=null
                        && oneCategory.get(position).getLeafCategorieList().size()>=5) {
                    mAdapterInterface.onItemClick(v
                            , oneCategory.get(position).getLeafCategorieList().get(4).getGoods_category_id()
                            , oneCategory.get(position).getLeafCategorieList().get(4).getName());
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_twocategory_title_iv)
        ImageView itemTwocategoryTitleIv;
        @Bind(R.id.item_twocategory_title_tx)
        TextView itemTwocategoryTitleTx;
        @Bind(R.id.item_twocategory_one_iv)
        SimpleDraweeView itemTwocategoryOneIv;
        @Bind(R.id.item_twocategory_two_iv)
        SimpleDraweeView itemTwocategoryTwoIv;
        @Bind(R.id.item_twocategory_three_iv)
        SimpleDraweeView itemTwocategoryThreeIv;
        @Bind(R.id.item_twocategory_four_iv)
        SimpleDraweeView itemTwocategoryFourIv;
        @Bind(R.id.item_twocategory_five_iv)
        SimpleDraweeView itemTwocategoryFiveIv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface CategoryAdapterInterface {

        void onItemClick(View view, int id, String name);
    }
}
