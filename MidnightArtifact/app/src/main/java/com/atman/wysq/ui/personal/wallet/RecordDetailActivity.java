package com.atman.wysq.ui.personal.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atman.wysq.R;
import com.atman.wysq.adapter.MyDiamondsAdapter;
import com.atman.wysq.model.response.MyDiamondsRecordModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/3/27.
 */

public class RecordDetailActivity extends MyBaseActivity implements View.OnClickListener {

    @Bind(R.id.blogdetail_comment_lv)
    PullToRefreshListView blogdetailCommentLv;

    private Context mContext = RecordDetailActivity.this;

    private MyDiamondsAdapter mAdapter;
    private int typeId = 0;
    private int mPage = 1;
    private MyDiamondsRecordModel mMyDiamondsRecordModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorddeitail);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent (Context context, int typeId) {
        Intent intent = new Intent(context, RecordDetailActivity.class);
        intent.putExtra("typeId", typeId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        typeId = getIntent().getIntExtra("typeId", 0);
        if (typeId==0) {
            setBarTitleTx("兑换记录");
        } else {
            setBarTitleTx("提现记录");
        }

        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, blogdetailCommentLv);

        mAdapter = new MyDiamondsAdapter(mContext);
        mAdapter.setTypeID(typeId);
        blogdetailCommentLv.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        doHttp(true);
    }

    private void doHttp(boolean b) {
        String url = Common.Url_Get_Exchange_Record_List + mPage;
        int reponseId = Common.NET_GET_EXCHANGE_RECORD_ID;
        if (typeId == 1) {
            url = Common.Url_Get_Cash_Record_List + mPage;
            reponseId = Common.NET_GET_CASH_RECORD_ID;
        }
        OkHttpUtils.get().url(url).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .id(reponseId).tag(reponseId).build().execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_EXCHANGE_RECORD_ID //兑换记录
                || id == Common.NET_GET_CASH_RECORD_ID) {//提现记录
            mMyDiamondsRecordModel = mGson.fromJson(data, MyDiamondsRecordModel.class);
            if (mMyDiamondsRecordModel.getBody() == null
                    || mMyDiamondsRecordModel.getBody().size() == 0) {
                if (mAdapter != null && mAdapter.getBody().size() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, blogdetailCommentLv);
            } else {
                mAdapter.clearData();
                mAdapter.addBody(mMyDiamondsRecordModel.getBody());
                onLoad(PullToRefreshBase.Mode.BOTH, blogdetailCommentLv);
            }
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        mPage = 1;
        onLoad(PullToRefreshBase.Mode.BOTH, blogdetailCommentLv);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        mPage += 1;
        doHttp(false);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        mPage = 1;
        mAdapter.clearData();
        doHttp(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_EXCHANGE_RECORD_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_CASH_RECORD_ID);
    }

    @Override
    public void onClick(View v) {
    }
}
