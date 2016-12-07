package com.atman.wysq.ui.personal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.GoldMallAdapter;
import com.atman.wysq.model.response.GetGoldMallModel;
import com.atman.wysq.model.response.TwoLevelCategoryListResponseModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.mall.GoodsDetailActivity;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/12/7.
 */

public class GoldMallActivity extends MyBaseActivity {

    @Bind(R.id.pullToRefreshGridView)
    PullToRefreshGridView pullToRefreshGridView;

    private Context mContext = GoldMallActivity.this;

    private View mEmpty;
    private TextView mEmptyTX;
    private GoldMallAdapter mAdapter;
    private int page = 1;
    private GetGoldMallModel mGetGoldMallModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goldmall);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("金币兑换商城");

        initGridView();
    }

    private void initGridView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshGridView);

        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无可以兑换商品");

        mAdapter = new GoldMallAdapter(mContext, getmWidth());
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
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        dohttp(true);
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_GOLDMALL_ID) {
            mGetGoldMallModel = mGson.fromJson(data, GetGoldMallModel.class);
            if (mGetGoldMallModel.getBody() == null
                    || mGetGoldMallModel.getBody().size() == 0) {
                if (mAdapter!=null && mAdapter.getCount()>0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshGridView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshGridView);
                mAdapter.addBody(mGetGoldMallModel.getBody());
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
        OkHttpUtils.get().url(Common.Url_Get_GoldMall+page)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_GOLDMALL_ID).id(Common.NET_GET_GOLDMALL_ID).build()
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
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_GOLDMALL_ID);
    }
}
