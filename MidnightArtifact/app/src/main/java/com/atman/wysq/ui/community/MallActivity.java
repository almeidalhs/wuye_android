package com.atman.wysq.ui.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
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
import com.atman.wysq.utils.Common;
import com.atman.wysq.widget.SearchEditText;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/4/1.
 */

public class MallActivity extends MyBaseActivity implements AdapterInterface, SearchEditText.OnIsShowProListener {

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

    private Context mContext = MallActivity.this;

    private SearchKeywordModel mSearchKeywordModel;
    private SearchKeywprdAdapter searchAdapter;

    private MallCategoryModel mMallCategoryModel;
    private MallOneCategoryAdapter oneAdapter;

    private MallGoodsAdapter mMallGoodsAdapter;
    private TwoCategoryModel mTwoCategoryModel;

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
                if (event.getAction()==MotionEvent.ACTION_DOWN) {
                    relationGoodsSv.setFocusable(true);
                    relationGoodsSv.setFocusableInTouchMode(true);
                }
                return false;
            }
        });
        relationGoodsSv.setIsShowListener(this);
        relationGoodsSv.setCancelTv(relationGoodsSearchCacelTx);

        initExListView();
    }

    private void initExListView() {
        mMallGoodsAdapter = new MallGoodsAdapter(mContext, getmWidth(), this);
        relationGoodsGoodsLv.setGroupIndicator(null);
        relationGoodsGoodsLv.setAdapter(mMallGoodsAdapter);
        for(int i = 0; i < mMallGoodsAdapter.getGroupCount(); i++){
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
                        mMallGoodsAdapter.setAdModel(mMallCategoryModel.getBody().get(position));
                        getTwoCategoryById(mMallCategoryModel.getBody().get(position).getGoods_category_id());
                    }
                });
                getTwoCategoryById(mMallCategoryModel.getBody().get(0).getGoods_category_id());
            }
        } else if (id == Common.NET_GET_SEARCH_KEYWORD_ID) {
            mSearchKeywordModel = mGson.fromJson(data, SearchKeywordModel.class);
            if (mSearchKeywordModel.getBody().size()>0) {
                searchAdapter = new SearchKeywprdAdapter(mContext, mSearchKeywordModel.getBody(), this);
                relationGoodsSearchLv.setAdapter(searchAdapter);
                relationGoodsSearchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        relationGoodsSv.setText(searchAdapter.getItem(position).getName());
                    }
                });
            }
        } else if (id == Common.NET_GET_CATEGOYP_TWO_ID) {
            mTwoCategoryModel = mGson.fromJson(data, TwoCategoryModel.class);
            mMallGoodsAdapter.clearData();
            mMallGoodsAdapter.adddata(mTwoCategoryModel.getBody());
        }
    }

    private void getTwoCategoryById(int id) {
        OkHttpUtils.get().url(Common.Url_Get_Two_Category+id).id(Common.NET_GET_CATEGOYP_TWO_ID)
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

    }

    @OnClick({R.id.relation_goods_search_cacel_tx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relation_goods_search_cacel_tx:
                relationGoodsSv.setFocusable(false);
                relationGoodsSv.setFocusableInTouchMode(false);
                cancelIM(view);
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
}
