package com.atman.wysq.ui.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.MallGoodsAdapter;
import com.atman.wysq.adapter.MallOneCategoryAdapter;
import com.atman.wysq.adapter.SearchKeywprdAdapter;
import com.atman.wysq.model.response.MallCategoryModel;
import com.atman.wysq.model.response.SearchKeywordModel;
import com.atman.wysq.model.response.TwoCategoryModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.WebPageActivity;
import com.atman.wysq.ui.mall.SearchListActivity;
import com.atman.wysq.ui.mall.TwoLevelCategoryListActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.widget.SearchEditText;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/4/1.
 */

public class MallActivity extends MyBaseActivity implements AdapterInterface, MallGoodsAdapter.OnChildClickListener
        , SearchEditText.OnIsShowProListener {

    @Bind(R.id.relation_goods_sv)
    SearchEditText relationGoodsSv;
    @Bind(R.id.relation_goods_search_lv)
    ListView relationGoodsSearchLv;
    @Bind(R.id.relation_goods_category_lv)
    ListView relationGoodsCategoryLv;
    @Bind(R.id.relation_goods_goods_lv)
    ExpandableListView relationGoodsGoodsLv;
    @Bind(R.id.relation_goods_ll)
    LinearLayout relationGoodsLl;
    @Bind(R.id.relation_goods_search_cacel_tx)
    TextView relationGoodsSearchCacelTx;
    @Bind(R.id.relation_goods_bg_iv)
    ImageView relationGoodsBgIv;
    @Bind(R.id.relation_goods_bf_iv)
    ImageView relationGoodsBfIv;

    private Context mContext = MallActivity.this;

    private SearchKeywordModel mSearchKeywordModel;
    private SearchKeywprdAdapter searchAdapter;

    private MallCategoryModel mMallCategoryModel;
    private MallOneCategoryAdapter oneAdapter;

    private MallGoodsAdapter mMallGoodsAdapter;
    private TwoCategoryModel mTwoCategoryModel;
    private MallCategoryModel.BodyBean AdModelStr;
    private String imgBgUrl, imgUrl, webUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relationgoods);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, int from) {
        Intent intent = new Intent(context, MallActivity.class);
        intent.putExtra("from", from);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("商城");
        relationGoodsSv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    relationGoodsSv.setFocusable(true);
                    relationGoodsSv.setFocusableInTouchMode(true);
                }
                return false;
            }
        });
        relationGoodsSv.setIsShowListener(this);
        relationGoodsSv.setCancelTv(relationGoodsSearchCacelTx);
        relationGoodsSv.setOnSearchClickListener(new SearchEditText.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
                startActivity(SearchListActivity.buildIntent(mContext, relationGoodsSv.getText().toString()));
            }
        });

    }

    private void initExListView(List<TwoCategoryModel.BodyBean> temp) {
        mMallGoodsAdapter = new MallGoodsAdapter(mContext, getmWidth(), temp, AdModelStr, this, this);
        relationGoodsGoodsLv.setGroupIndicator(null);
        relationGoodsGoodsLv.setAdapter(mMallGoodsAdapter);
        for (int i = 0; i < mMallGoodsAdapter.getGroupCount(); i++) {
            relationGoodsGoodsLv.expandGroup(i);
        }

        relationGoodsGoodsLv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_One_Category).id(Common.NET_GET_CATEGOYP_ONE_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_CATEGOYP_ONE_ID).build()
                .execute(new MyStringCallback(mContext, this, true));
        OkHttpUtils.get().url(Common.Url_Get_Search_Keyword).id(Common.NET_GET_SEARCH_KEYWORD_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_SEARCH_KEYWORD_ID).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_CATEGOYP_ONE_ID) {
            mMallCategoryModel = mGson.fromJson(data, MallCategoryModel.class);
            if (mMallCategoryModel.getBody().size() > 0) {
                oneAdapter = new MallOneCategoryAdapter(mContext, mMallCategoryModel.getBody(), this);
                relationGoodsCategoryLv.setAdapter(oneAdapter);
                relationGoodsCategoryLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        oneAdapter.setSelectId(position);
                        getDataByCategory(position);
                    }
                });
                getDataByCategory(0);
            }
        } else if (id == Common.NET_GET_SEARCH_KEYWORD_ID) {
            mSearchKeywordModel = mGson.fromJson(data, SearchKeywordModel.class);
            if (mSearchKeywordModel.getBody().size() > 0) {
                searchAdapter = new SearchKeywprdAdapter(mContext, mSearchKeywordModel.getBody(), this);
                relationGoodsSearchLv.setAdapter(searchAdapter);
                relationGoodsSearchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        relationGoodsSv.setText(searchAdapter.getItem(position).getName());
                        startActivity(SearchListActivity.buildIntent(mContext, searchAdapter.getItem(position).getName()));
                    }
                });
            }
        } else if (id == Common.NET_GET_CATEGOYP_TWO_ID) {
            mTwoCategoryModel = mGson.fromJson(data, TwoCategoryModel.class);
            initExListView(mTwoCategoryModel.getBody());
        }
    }

    private void getDataByCategory(int position) {
        if (mMallCategoryModel.getBody().get(position).getImg_bg_url()!=null) {
            imgBgUrl = mMallCategoryModel.getBody().get(position).getImg_bg_url();
            imgUrl = mMallCategoryModel.getBody().get(position).getImg_url();
            webUrl = mMallCategoryModel.getBody().get(position).getWeb_url();

            relationGoodsGoodsLv.setVisibility(View.GONE);
            relationGoodsBgIv.setVisibility(View.VISIBLE);
            relationGoodsBfIv.setVisibility(View.VISIBLE);

            ImageLoader.getInstance().displayImage(Common.ImageUrl+imgBgUrl, relationGoodsBgIv);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+imgUrl, relationGoodsBfIv);
        } else {
            relationGoodsGoodsLv.setVisibility(View.VISIBLE);
            relationGoodsBgIv.setVisibility(View.GONE);
            relationGoodsBfIv.setVisibility(View.GONE);
            AdModelStr = mMallCategoryModel.getBody().get(position);
            getTwoCategoryById(mMallCategoryModel.getBody().get(position).getGoods_category_id());
        }
    }

    private void getTwoCategoryById(int id) {
        OkHttpUtils.get().url(Common.Url_Get_Two_Category + id).id(Common.NET_GET_CATEGOYP_TWO_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_CATEGOYP_TWO_ID).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_CATEGOYP_ONE_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_SEARCH_KEYWORD_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_CATEGOYP_TWO_ID);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_mall_category_two_gronp_iv:
                startActivity(WebPageActivity.buildIntent(mContext
                        , mMallGoodsAdapter.getAdModel().getAd_url(), ""));
                break;
        }
    }

    @OnClick({R.id.relation_goods_search_cacel_tx, R.id.relation_goods_bg_iv, R.id.relation_goods_bf_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relation_goods_search_cacel_tx:
                relationGoodsSv.setFocusable(false);
                relationGoodsSv.setFocusableInTouchMode(false);
                cancelIM(view);
                break;
            case R.id.relation_goods_bg_iv:
            case R.id.relation_goods_bf_iv:
                startActivity(WebPageActivity.buildIntent(mContext, webUrl, ""));
                break;
        }
    }

    @Override
    public void isShow(boolean show) {
        if (show) {
            relationGoodsLl.setVisibility(View.GONE);
            relationGoodsSearchLv.setVisibility(View.VISIBLE);
        } else {
            relationGoodsLl.setVisibility(View.VISIBLE);
            relationGoodsSearchLv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v, int groupId, int childId) {
        switch (v.getId()) {
            case R.id.item_mall_category_two_child_one_ll:
                startActivity(TwoLevelCategoryListActivity.buildIntent(mContext,
                        mMallGoodsAdapter.getChild(groupId, childId*2).getGoods_category_id(),
                        mMallGoodsAdapter.getChild(groupId, childId*2).getName(), false));
                break;
            case R.id.item_mall_category_two_child_two_ll:
                startActivity(TwoLevelCategoryListActivity.buildIntent(mContext,
                        mMallGoodsAdapter.getChild(groupId, childId*2+1).getGoods_category_id(),
                        mMallGoodsAdapter.getChild(groupId, childId*2+1).getName(), false));
                break;
        }
    }
}
