package com.atman.wysq.ui.mall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.TwoLevelCategoryListAdapter;
import com.atman.wysq.model.response.TwoLevelCategoryListResponseModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/19 09:33
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class SearchListActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.pullToRefreshGridView)
    PullToRefreshGridView pullToRefreshGridView;

    private Context mContext = SearchListActivity.this;
    private TwoLevelCategoryListAdapter mAdapter;
    private View mEmpty;
    private TextView mEmptyTX;
    private TwoLevelCategoryListResponseModel mTwoLevelCategoryListResponseModel;

    private String mTitle;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_twolevelcategorylist);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, String str) {
        Intent intent = new Intent(context, SearchListActivity.class);
        intent.putExtra("str", str);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        mTitle = getIntent().getStringExtra("str");
        setBarTitleTx(mTitle);

        initGridView();
    }

    private void initGridView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshGridView);

        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("无结果");

        mAdapter = new TwoLevelCategoryListAdapter(mContext, getmWidth(), this);
        pullToRefreshGridView.setEmptyView(mEmpty);
        pullToRefreshGridView.setAdapter(mAdapter);
        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(GoodsDetailActivity.buildIntent(mContext,
                        mAdapter.getItem(position).getGoods_id()));
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
        dohttp(true);
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_SEARCH_ID) {
            mTwoLevelCategoryListResponseModel =
                    mGson.fromJson(data, TwoLevelCategoryListResponseModel.class);
            if (mTwoLevelCategoryListResponseModel.getBody() == null
                    || mTwoLevelCategoryListResponseModel.getBody().size() == 0) {
                if (mAdapter!=null && mAdapter.getCount()>0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshGridView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshGridView);
                mAdapter.addBody(mTwoLevelCategoryListResponseModel.getBody());
            }
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        page = 1;
        onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshGridView);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        page += 1;
        dohttp(false);
    }

    private void dohttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Search + mTitle + "&page=" + page)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_SEARCH_ID).id(Common.NET_SEARCH_ID).build()
                .execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        page = 1;
        mAdapter.clearData();
        dohttp(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_SEARCH_ID);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
