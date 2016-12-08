package com.atman.wysq.ui.yunxinfriend;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.MyConcernListAdapter;
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

public class MyConcernActivity extends MyBaseActivity
        implements MyConcernListAdapter.IonSlidingViewClickListener {

    @Bind(R.id.pull_refresh_recycler)
    PullToRefreshRecyclerView pullRefreshRecycler;
    @Bind(R.id.myconcern_empty_tx)
    TextView myconcernEmptyTx;

    private Context mContext = MyConcernActivity.this;
    private MyConcernListAdapter mAdapter;
    private GetFansListModel mGetFansListModel;
    private RecyclerView mRecyclerView;
    private int mPage = 1;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myconcern);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("我的关注");

        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullRefreshRecycler);
        mAdapter = new MyConcernListAdapter(mContext, getmWidth(), "1", this);

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
        OkHttpUtils.get().url(Common.Url_Get_MyConcernList + mPage)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_Get_MYCONCERNLIST_ID).id(Common.NET_Get_MYCONCERNLIST_ID).build()
                .execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_Get_MYCONCERNLIST_ID) {
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
        } else if (id == Common.NET_CANCEL_MYCONCERNLIST_ID) {
            mAdapter.removeData(mPosition);
        }
        isNullChange();
    }

    private void isNullChange() {
        if (mAdapter!=null && mAdapter.getItemCount()>0) {
            myconcernEmptyTx.setVisibility(View.GONE);
            pullRefreshRecycler.setVisibility(View.VISIBLE);
        } else {
            myconcernEmptyTx.setVisibility(View.VISIBLE);
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
        OkHttpUtils.getInstance().cancelTag(Common.NET_Get_MYCONCERNLIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CANCEL_MYCONCERNLIST_ID);
    }

    @Override
    public void onItemClick(View view, int position) {
        mPosition = position;
        startActivityForResult(OtherPersonalActivity.buildIntent(mContext
                , mAdapter.getItemById(position).getUser_id()), Common.toOtherPersonal);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.toOtherPersonal) {
            mAdapter.removeData(mPosition);
        }
    }

    @Override
    public void onDeleteBtnCilck(View view, final int position) {
        mPosition = position;
        switch (view.getId()) {
            case R.id.tv_edit:
                break;
            case R.id.tv_delete:
                PromptDialog.Builder builder = new PromptDialog.Builder(this);
                builder.setMessage("确定取消关注TA吗?");
                builder.setPositiveButton("不了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消关注", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        OkHttpUtils.postString()
                                .url(Common.Url_Cancel_MyConcernList + mAdapter.getItemById(position).getUser_id())
                                .tag(Common.NET_CANCEL_MYCONCERNLIST_ID).id(Common.NET_CANCEL_MYCONCERNLIST_ID)
                                .content(mGson.toJson(""))
                                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                .build().execute(new MyStringCallback(mContext, MyConcernActivity.this, true));
                    }
                });
                builder.show();
                break;
        }
    }
}
