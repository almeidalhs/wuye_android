package com.atman.wysq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.MallCategoryModel;
import com.atman.wysq.model.response.TwoCategoryModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tangbingliang on 17/4/1.
 */

public class MallGoodsAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<TwoCategoryModel.BodyBean> dataList;
    private AdapterInterface mAdapterInterface;
    private OnChildClickListener mOnChildClickListener;
    private int index = -1;
    private EditText mEditText;
    private LinearLayout.LayoutParams params,params2;
    private MallCategoryModel.BodyBean adModel;

    protected LayoutInflater layoutInflaterGroup;
    protected LayoutInflater layoutInflaterChild;

    private GroupViewHolder groupHolder;
    private ChildViewHolder childHolder;

    public MallGoodsAdapter(Context context, int mWight, List<TwoCategoryModel.BodyBean> temp
            , MallCategoryModel.BodyBean adModelStr, AdapterInterface mAdapterInterface
            , OnChildClickListener mOnChildClickListener) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        this.mOnChildClickListener = mOnChildClickListener;
        this.adModel = adModelStr;
        this.dataList = temp;
        layoutInflaterGroup = LayoutInflater.from(context);
        layoutInflaterChild = LayoutInflater.from(context);

        int w = mWight*3/4 - DensityUtil.dp2px(context, 20);
        params = new LinearLayout.LayoutParams(w, w*80/310);
        w = w - DensityUtil.dp2px(context, 10);
        params2 = new LinearLayout.LayoutParams(w/2, w/2);
    }

    public MallCategoryModel.BodyBean getAdModel() {
        return adModel;
    }

    public void adddata(List<TwoCategoryModel.BodyBean> temp) {
        this.dataList.addAll(temp);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 获取一级标签总数
     */
    @Override
    public int getGroupCount() {
        return dataList.size();
    }

    /**
     * 获取一级标签下二级标签的总数
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        if (dataList.get(groupPosition).getLeafCategorieList().size()==0) {
            return 0;
        } else if (dataList.get(groupPosition).getLeafCategorieList().size()%2==0) {
            return dataList.get(groupPosition).getLeafCategorieList().size()/2;
        } else {
            return dataList.get(groupPosition).getLeafCategorieList().size()/2+1;
        }
    }

    /**
     * 获取一级标签内容
     */
    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    /**
     * 获取一级标签下二级标签的内容
     */
    @Override
    public TwoCategoryModel.BodyBean.LeafCategorieListBean getChild(int groupPosition, int childPosition) {
        return dataList.get(groupPosition).getLeafCategorieList().get(childPosition);
    }

    /**
     * 获取一级标签的ID
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取二级标签的ID
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 指定位置相应的组视图
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 对一级标签进行设置
     */
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView==null) {
            convertView = layoutInflaterGroup.inflate(R.layout.item_mall_two_category_group_view, null);
            groupHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupViewHolder) convertView.getTag();
        }

        groupHolder.itemMallCategoryTwoGronpTv.setText("------"+dataList.get(groupPosition).getName()+"------");

        if (adModel!=null && groupPosition==0) {
            groupHolder.itemMallCategoryTwoGronpIv.setLayoutParams(params);
            groupHolder.itemMallCategoryTwoGronpIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+adModel.getAd_pic()
                    , groupHolder.itemMallCategoryTwoGronpIv);
        } else {
            groupHolder.itemMallCategoryTwoGronpIv.setVisibility(View.GONE);
        }

        groupHolder.itemMallCategoryTwoGronpIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, groupPosition);
            }
        });
        return convertView;
    }

    /**
     * 对一级标签下的二级标签进行设置
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {

        if (convertView==null) {
            convertView = layoutInflaterChild.inflate(R.layout.item_mall_two_category_child_view, null);
            childHolder = new ChildViewHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildViewHolder) convertView.getTag();
        }

        childHolder.itemMallCategoryChildOneIv.setLayoutParams(params2);
        if ((childPosition*2)>dataList.get(groupPosition).getLeafCategorieList().size()-1) {
            childHolder.itemMallCategoryChildOneLl.setVisibility(View.INVISIBLE);
        } else {
            childHolder.itemMallCategoryChildOneLl.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(groupPosition)
                    .getLeafCategorieList().get(childPosition*2).getAd_pic()
                    , childHolder.itemMallCategoryChildOneIv, MyBaseApplication.getApplication().getOptions());
            childHolder.itemMallCategoryChildOneTv.setText(dataList.get(groupPosition)
                    .getLeafCategorieList().get(childPosition*2).getName());
        }

        childHolder.itemMallCategoryChildTwoIv.setLayoutParams(params2);
        if ((childPosition*2+1)>dataList.get(groupPosition).getLeafCategorieList().size()-1) {
            childHolder.itemMallCategoryChildTwoLl.setVisibility(View.INVISIBLE);
        } else {
            childHolder.itemMallCategoryChildTwoLl.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(groupPosition)
                            .getLeafCategorieList().get(childPosition*2+1).getAd_pic()
                    , childHolder.itemMallCategoryChildTwoIv, MyBaseApplication.getApplication().getOptions());
            childHolder.itemMallCategoryChildTwoTv.setText(dataList.get(groupPosition)
                    .getLeafCategorieList().get(childPosition*2+1).getName());
        }

        initChildoneListener(groupPosition, childPosition);

        return convertView;
    }

    //添加item控件监听
    private void initChildoneListener(final int groupPosition, final int childPosition) {
        childHolder.itemMallCategoryChildOneLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnChildClickListener.onClick(v, groupPosition, childPosition);
            }
        });

        childHolder.itemMallCategoryChildTwoLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnChildClickListener.onClick(v, groupPosition, childPosition);
            }
        });
    }

    /**
     * 当选择子节点的时候，调用该方法
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        @Bind(R.id.item_mall_category_two_gronp_iv)
        ImageView itemMallCategoryTwoGronpIv;
        @Bind(R.id.item_mall_category_two_gronp_tv)
        TextView itemMallCategoryTwoGronpTv;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnChildClickListener {
        void onClick(View v, int groupId, int childId);
    }

    class ChildViewHolder {

        @Bind(R.id.item_mall_category_two_child_one_ll)
        LinearLayout itemMallCategoryChildOneLl;
        @Bind(R.id.item_mall_category_two_child_one_iv)
        ShapeImageView itemMallCategoryChildOneIv;
        @Bind(R.id.item_mall_category_two_child_one_tv)
        TextView itemMallCategoryChildOneTv;
        @Bind(R.id.item_mall_category_two_child_two_ll)
        LinearLayout itemMallCategoryChildTwoLl;
        @Bind(R.id.item_mall_category_two_child_two_iv)
        ShapeImageView itemMallCategoryChildTwoIv;
        @Bind(R.id.item_mall_category_two_child_two_tv)
        TextView itemMallCategoryChildTwoTv;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
