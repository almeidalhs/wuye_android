package com.atman.wysq.ui.discover;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.RecommendUsersAdapter;
import com.atman.wysq.model.response.RecommendUserModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.yunxinfriend.OtherPersonalActivity;
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
 * Created by tangbingliang on 16/12/6.
 */

public class RecommendUsersActivity extends MyBaseActivity implements View.OnClickListener {

    @Bind(R.id.pullToRefreshGridView)
    PullToRefreshGridView pullToRefreshGridView;

    private Context mContext = RecommendUsersActivity.this;
    private final String[] typeId = {"/0", "/1", "/2"};//0女用户,1男用户,2认证用户
    private String sofarTypeId = typeId[2];

    private RecommendUserModel mRecommendUserModel;
    private RecommendUsersAdapter mAdapter;

    private View mEmpty;
    private TextView mEmptyTX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendusers);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("推荐用户");
        setBarRightIv(R.mipmap.mchat_icon_more_iverify).setOnClickListener(this);

        initGridView();
    }

    private void initGridView() {
        initRefreshView(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshGridView);

        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无推荐用户");

        mAdapter = new RecommendUsersAdapter(mContext, getmWidth());
        pullToRefreshGridView.setEmptyView(mEmpty);
        pullToRefreshGridView.setAdapter(mAdapter);
        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(OtherPersonalActivity.buildIntent(mContext
                        , mAdapter.getItem(position).getUser_id()));
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
        if (id == Common.NET_GET_FINDLIKE_ID) {
            mRecommendUserModel = mGson.fromJson(data, RecommendUserModel.class);
            if (mRecommendUserModel.getBody() == null
                    || mRecommendUserModel.getBody().size() == 0) {
                if (mAdapter!=null && mAdapter.getCount()>0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshGridView);
            } else {
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshGridView);
                mAdapter.clearData();
                mAdapter.addBody(mRecommendUserModel.getBody());
            }
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshGridView);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        dohttp(false);
    }

    private void dohttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Get_RecommendFriends + sofarTypeId)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_FINDLIKE_ID).id(Common.NET_GET_FINDLIKE_ID).build()
                .execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        dohttp(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_FINDLIKE_ID);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_right_iv:
                break;
        }
    }
}
