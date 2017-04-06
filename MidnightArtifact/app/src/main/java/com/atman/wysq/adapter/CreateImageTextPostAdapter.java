package com.atman.wysq.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.request.AddPostContentModel;
import com.atman.wysq.model.response.GoodsListModel;
import com.atman.wysq.utils.Common;
import com.atman.wysq.widget.face.SmileUtils;
import com.base.baselibs.iimp.AdapterInterface;
import com.choicepicture_library.tools.Bimp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tangbingliang on 17/4/1.
 */

public class CreateImageTextPostAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<AddPostContentModel> contentList;
    private List<GoodsListModel> goodsList;
    private AdapterInterface mAdapterInterface;
    private int index = -1;
    private EditText mEditText;

    protected LayoutInflater layoutInflaterGroup;
    protected LayoutInflater layoutInflaterChild;
    protected LayoutInflater layoutInflaterChildTwo;

    private ChildViewHolder childHolder;
    private ChildTwoViewHolder childTwoHolder;

    public CreateImageTextPostAdapter(Context context, AdapterInterface mAdapterInterface) {
        this.context = context;
        this.mAdapterInterface = mAdapterInterface;
        layoutInflaterGroup = LayoutInflater.from(context);
        layoutInflaterChild = LayoutInflater.from(context);
        layoutInflaterChildTwo = LayoutInflater.from(context);

        contentList = new ArrayList<>();
        goodsList = new ArrayList<>();

        addContent(new AddPostContentModel("", "", "", null));
    }

    public void setLocalUrl(int mPosition, String url) {
        if (contentList.size()<mPosition+1) {
            addContent(new AddPostContentModel("", "", "", null));
        }
        contentList.get(mPosition).setBitmap(null);
        contentList.get(mPosition).setLocalUrl(url);
        notifyDataSetChanged();
    }

    public int getIndex() {
        return index;
    }

    public EditText getmEditText() {
        return mEditText;
    }

    public void addContent(List<AddPostContentModel> temp) {
        this.contentList.addAll(temp);
        notifyDataSetChanged();
    }

    public List<AddPostContentModel> getContentList() {
        return contentList;
    }

    public List<GoodsListModel> getGoodsList() {
        return goodsList;
    }

    public void addContent(AddPostContentModel temp) {
        this.contentList.add(temp);
        notifyDataSetChanged();
    }

    public void clearAll () {
        this.contentList.clear();
        this.goodsList.clear();
    }

    public void deleteContent(int p) {
        this.contentList.remove(p);
        notifyDataSetChanged();
    }

    public void addGoods(List<GoodsListModel> temp) {
        this.goodsList.addAll(temp);
        notifyDataSetChanged();
    }

    public void addGoods(GoodsListModel temp) {
        this.goodsList.add(temp);
        notifyDataSetChanged();
    }

    public void deleteGoods(int p) {
        this.goodsList.remove(p);
        notifyDataSetChanged();
    }

    /**
     * 获取一级标签总数
     */
    @Override
    public int getGroupCount() {
        return 2;
    }

    /**
     * 获取一级标签下二级标签的总数
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 0) {
            if (contentList.size()==0) {
                return 1;
            } else if (contentList.size()>10) {
                return 10;
            } else {
                return contentList.size();
            }
        } else {
            if (goodsList.size()==0) {
                return 1;
            } else if (goodsList.size()>3) {
                return 3;
            } else {
                return goodsList.size();
            }
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
    public Object getChild(int groupPosition, int childPosition) {
        if (groupPosition == 0) {
            return contentList.get(childPosition);
        } else {
            return goodsList.get(childPosition);
        }
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // 为视图对象指定布局
        convertView = layoutInflaterGroup.inflate(R.layout.item_create_imageview_group_view, null);
        return convertView;
    }

    /**
     * 对一级标签下的二级标签进行设置
     */
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {

        if (groupPosition==0) {
            convertView = layoutInflaterChild.inflate(R.layout.item_create_imageview_childone_view, null);
            childHolder = new ChildViewHolder(convertView);

            if (contentList.size() == 0) {
                childHolder.itemChildoneDeleteIv.setVisibility(View.GONE);
                childHolder.itemChildoneHeadRl.setVisibility(View.GONE);
                childHolder.itemChildoneHeadLl.setVisibility(View.VISIBLE);
                childHolder.itemChildoneAddLl.setVisibility(View.VISIBLE);
            } else {
                if (contentList.size() == 1) {
                    childHolder.itemChildoneDeleteIv.setVisibility(View.GONE);
                } else {
                    childHolder.itemChildoneDeleteIv.setVisibility(View.VISIBLE);
                }

                if (contentList.size()>=10 || childPosition!=contentList.size()-1) {
                    childHolder.itemChildoneAddLl.setVisibility(View.GONE);
                } else {
                    childHolder.itemChildoneAddLl.setVisibility(View.VISIBLE);
                }

                childHolder.itemChildoneContentEt.setText(SmileUtils.getEmotionContent(context
                        , childHolder.itemChildoneContentEt, contentList.get(childPosition).getContent()));

                if (contentList.get(childPosition).getLocalUrl().isEmpty()) {
                    childHolder.itemChildoneHeadRl.setVisibility(View.GONE);
                    childHolder.itemChildoneHeadLl.setVisibility(View.VISIBLE);
                } else {
                    childHolder.itemChildoneHeadLl.setVisibility(View.GONE);
                    childHolder.itemChildoneHeadRl.setVisibility(View.VISIBLE);
                    if (contentList.get(childPosition).getBitmap()!=null) {
                        childHolder.itemChildoneHeadIv.setImageBitmap(contentList.get(childPosition).getBitmap());
                    } else {
                        Bitmap bm = null;
                        try {
                            bm = Bimp.revitionImageSize(contentList.get(childPosition).getLocalUrl());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        contentList.get(childPosition).setBitmap(bm);
                        childHolder.itemChildoneHeadIv.setImageBitmap(bm);
                    }
                }
            }

            initChildoneListener(childPosition);
            managerFocus(childPosition);
        } else {
            convertView = layoutInflaterChildTwo.inflate(R.layout.item_create_imageview_childtwo_view, null);
            childTwoHolder = new ChildTwoViewHolder(convertView);

            if (goodsList.size()>=3 || (goodsList.size()>0 && childPosition!=goodsList.size()-1)) {
                childTwoHolder.itemChildtwoAddLl.setVisibility(View.GONE);
            } else {
                childTwoHolder.itemChildtwoAddLl.setVisibility(View.VISIBLE);
            }

            if (goodsList.size()==0) {
                childTwoHolder.itemChildtwoLl.setVisibility(View.GONE);
                childTwoHolder.itemChildtwoDeleteIv.setVisibility(View.GONE);
            } else {
                childTwoHolder.itemChildtwoLl.setVisibility(View.VISIBLE);
                childTwoHolder.itemChildtwoDeleteIv.setVisibility(View.VISIBLE);

                ImageLoader.getInstance().displayImage(Common.ImageUrl+goodsList.get(childPosition).getPic_img()
                        , childTwoHolder.itemChildtwoHeadIv);
                childTwoHolder.itemChildtwoGoodsNameTv.setText(goodsList.get(childPosition).getTitle());
                childTwoHolder.itemChildtwoGoodsPriceTv.setText(""+goodsList.get(childPosition).getDiscount_price());
            }

            childTwoHolder.itemChildtwoAddLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapterInterface.onItemClick(v, childPosition);
                }
            });

            childTwoHolder.itemChildtwoDeleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapterInterface.onItemClick(v, childPosition);
                }
            });
        }

        return convertView;
    }

    //焦点管理
    private void managerFocus(final int childPosition) {
        childHolder.itemChildoneContentEt.addTextChangedListener(new postTextWatcher(context
                , childHolder.itemChildoneContentEt, childPosition));
        childHolder.itemChildoneContentEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);//解决listview截获滑动监听
                if(event.getAction() == MotionEvent.ACTION_UP
                        || event.getAction() == MotionEvent.ACTION_MOVE) {
                    index= childPosition;
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
        childHolder.itemChildoneContentEt.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEditText = childHolder.itemChildoneContentEt;
                } else {
                }
            }
        });

        childHolder.itemChildoneContentEt.clearFocus();
        if(index!= -1 && index == childPosition) {
            // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
            childHolder.itemChildoneContentEt.requestFocus();
        }
    }

    //添加item控件监听
    private void initChildoneListener(final int childPosition) {
        childHolder.itemChildoneAddLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, childPosition);
            }
        });

        childHolder.itemChildoneDeleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, childPosition);
            }
        });
        childHolder.itemChildonePhotographIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, childPosition);
            }
        });
        childHolder.itemChildoneAlbumIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, childPosition);
            }
        });
        childHolder.itemChildoneHeadIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterInterface.onItemClick(v, childPosition);
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

    public class postTextWatcher implements TextWatcher {
        private EditText myCleanEditText;
        private Context mContext;
        private int num;

        public postTextWatcher(Context mContext, EditText myCleanEditText, int num) {
            this.myCleanEditText = myCleanEditText;
            this.mContext = mContext;
            this.num = num;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String content = s == null ? null : s.toString();
            if (s == null || s.length() == 0) {
                return;
            }
            int size = content.trim().length();
            contentList.get(num).setContent(myCleanEditText.getText().toString());
        }
    }

    class ChildViewHolder {
        @Bind(R.id.item_childone_head_iv)
        ImageView itemChildoneHeadIv;
        @Bind(R.id.item_childone_postcontent_tx)
        TextView itemChildonePostcontentTx;
        @Bind(R.id.item_childone_photograph_iv)
        ImageView itemChildonePhotographIv;
        @Bind(R.id.item_childone_album_iv)
        ImageView itemChildoneAlbumIv;
        @Bind(R.id.item_childone_head_ll)
        LinearLayout itemChildoneHeadLl;
        @Bind(R.id.item_childone_head_rl)
        RelativeLayout itemChildoneHeadRl;
        @Bind(R.id.item_childone_content_et)
        EditText itemChildoneContentEt;
        @Bind(R.id.item_childone_delete_iv)
        ImageView itemChildoneDeleteIv;
        @Bind(R.id.item_childone_add_ll)
        LinearLayout itemChildoneAddLl;
        @Bind(R.id.item_childone_ll)
        LinearLayout itemChildoneLl;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildTwoViewHolder {

        @Bind(R.id.item_childtwo_goods_name_tv)
        TextView itemChildtwoGoodsNameTv;
        @Bind(R.id.item_childtwo_goods_price_tv)
        TextView itemChildtwoGoodsPriceTv;
        @Bind(R.id.item_childtwo_delete_iv)
        ImageView itemChildtwoDeleteIv;
        @Bind(R.id.item_childtwo_head_iv)
        ImageView itemChildtwoHeadIv;
        @Bind(R.id.item_childtwo_add_ll)
        LinearLayout itemChildtwoAddLl;
        @Bind(R.id.item_childtwo_ll)
        LinearLayout itemChildtwoLl;

        ChildTwoViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
