package com.atman.wysq.ui.yunxinfriend;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.BlackListAdapter;
import com.atman.wysq.model.response.GetFansListModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.widget.PromptDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/12/6.
 */

public class BlackListActivity extends MyBaseActivity
        implements BlackListAdapter.IonSlidingViewClickListener {

    @Bind(R.id.pull_refresh_recycler)
    PullToRefreshRecyclerView pullRefreshRecycler;
    @Bind(R.id.black_empty_tx)
    TextView blackEmptyTx;

    private Context mContext = BlackListActivity.this;
    private BlackListAdapter mAdapter;
    private GetFansListModel mGetFansListModel;
    private RecyclerView mRecyclerView;
    private int mPage = 1;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("黑名单");

        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullRefreshRecycler);
        mAdapter = new BlackListAdapter(mContext, getmWidth(), "2", this);

        mRecyclerView = pullRefreshRecycler.getRefreshableView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//这里用线性显示 类似于listview
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        doHttp(true);
    }

    private void doHttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Get_BlackList + mPage)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_BLACKLIST_ID).id(Common.NET_GET_BLACKLIST_ID).build()
                .execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_BLACKLIST_ID) {
            mGetFansListModel = mGson.fromJson(data, GetFansListModel.class);
            if (mGetFansListModel.getBody() == null
                    || mGetFansListModel.getBody().size() == 0) {
                if (mAdapter != null && mAdapter.getItemCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullRefreshRecycler);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullRefreshRecycler);
                if (mPage == 1) {
                    mAdapter.clearData();
                }
                mAdapter.addData(mGetFansListModel.getBody());
            }
        } else if (id == Common.NET_CANCEL_BLACKLIST_ID) {
            mAdapter.removeData(mPosition);
        }
        isNullChange();
    }

    private void isNullChange() {
        if (mAdapter!=null && mAdapter.getItemCount()>0) {
            blackEmptyTx.setVisibility(View.GONE);
            pullRefreshRecycler.setVisibility(View.VISIBLE);
        } else {
            blackEmptyTx.setVisibility(View.VISIBLE);
            pullRefreshRecycler.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        mPage = 1;
        mAdapter.clearData();
        doHttp(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        mPage += 1;
        doHttp(false);
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        mPage = 1;
        onLoad(PullToRefreshBase.Mode.BOTH, pullRefreshRecycler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLACKLIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CANCEL_BLACKLIST_ID);
    }

    @Override
    public void onItemClick(View view, int position) {
//        startActivity(OtherPersonalActivity.buildIntent(mContext
//                , mAdapter.getItemById(position).getUser_id()));
    }

    @Override
    public void onDeleteBtnCilck(View view, final int position) {
        mPosition = position;
        switch (view.getId()) {
            case R.id.tv_edit:
                break;
            case R.id.tv_delete:
                PromptDialog.Builder builder = new PromptDialog.Builder(this);
                builder.setMessage("确定将TA从黑名单移除吗?");
                builder.setPositiveButton("不了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("移除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        OkHttpUtils.postString()
                                .url(Common.Url_Cancel_BlackList + mAdapter.getItemById(position).getUser_id())
                                .tag(Common.NET_CANCEL_BLACKLIST_ID).id(Common.NET_CANCEL_BLACKLIST_ID)
                                .content(mGson.toJson(""))
                                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                .build().execute(new MyStringCallback(mContext, BlackListActivity.this, true));
                    }
                });
                builder.show();
                break;
        }
    }
}
