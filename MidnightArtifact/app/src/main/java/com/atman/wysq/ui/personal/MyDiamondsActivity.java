package com.atman.wysq.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.MyDiamondsAdapter;
import com.atman.wysq.model.response.AllRankingModel;
import com.atman.wysq.model.response.GetChildrenCommentModel;
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

public class MyDiamondsActivity extends MyBaseActivity implements View.OnClickListener {

    @Bind(R.id.blogdetail_comment_lv)
    PullToRefreshListView blogdetailCommentLv;

    private Context mContext = MyDiamondsActivity.this;

    private View headView;
    private TextView mydiamondsTocionTv;
    private TextView mydiamondsNumTv;
    private TextView rmydiamondsWithdrawalsTv;
    private RelativeLayout mydiamondsExchangeRecordRl;
    private RelativeLayout mydiamondsPresentationRecordRl;
    private TextView mydiamondsPresentationRecordTv;
    private TextView mydiamondsExchangeRecordTv;
    private View mydiamondsPresentationRecordLine;
    private View mydiamondsExchangeRecordLine;

    private MyDiamondsAdapter mAdapter;
    private int typeId = 0;
    private int mPage = 1;
    private MyDiamondsRecordModel mMyDiamondsRecordModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydiamonds);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("我的钻石");

        headView = LayoutInflater.from(mContext).inflate(R.layout.part_mydiamonds_head_view, null);
        mydiamondsNumTv = (TextView) headView.findViewById(R.id.mydiamonds_num_tv);
        mydiamondsTocionTv = (TextView) headView.findViewById(R.id.mydiamonds_tocion_tv);
        rmydiamondsWithdrawalsTv = (TextView) headView.findViewById(R.id.rmydiamonds_withdrawals_tv);
        mydiamondsPresentationRecordTv = (TextView) headView.findViewById(R.id.mydiamonds_presentation_record_tv);
        mydiamondsExchangeRecordTv = (TextView) headView.findViewById(R.id.mydiamonds_exchange_record_tv);
        mydiamondsExchangeRecordLine = headView.findViewById(R.id.mydiamonds_exchange_record_line);
        mydiamondsPresentationRecordLine = headView.findViewById(R.id.mydiamonds_presentation_record_line);
        mydiamondsExchangeRecordRl = (RelativeLayout) headView.findViewById(R.id.mydiamonds_exchange_record_rl);
        mydiamondsPresentationRecordRl = (RelativeLayout) headView.findViewById(R.id.mydiamonds_presentation_record_rl);

        mydiamondsTocionTv.setOnClickListener(this);
        mydiamondsExchangeRecordRl.setOnClickListener(this);
        mydiamondsPresentationRecordRl.setOnClickListener(this);

        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, blogdetailCommentLv);

        mAdapter = new MyDiamondsAdapter(mContext);
        blogdetailCommentLv.getRefreshableView().addHeaderView(headView);
        blogdetailCommentLv.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mydiamondsNumTv.setText(""+MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getConvert_coin());
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
        switch (v.getId()) {
            case R.id.mydiamonds_tocion_tv://钻石兑金币
                startActivity(new Intent(mContext, DiamondsToCoinActivity.class));
                break;
            case R.id.rmydiamonds_withdrawals_tv://提现
                startActivity(new Intent(mContext, WithdrawCashActivity.class));
                break;
            case R.id.mydiamonds_exchange_record_rl://兑换记录
                mydiamondsPresentationRecordTv.setTextColor(getResources().getColor(R.color.color_black));
                mydiamondsExchangeRecordTv.setTextColor(getResources().getColor(R.color.color_17c7eb));
                mydiamondsPresentationRecordLine.setVisibility(View.GONE);
                mydiamondsExchangeRecordLine.setVisibility(View.VISIBLE);
                typeId = 0;
                mPage = 1;
                mAdapter.clearData();
                mAdapter.setTypeID(typeId);

                doHttp(true);
                break;
            case R.id.mydiamonds_presentation_record_rl://提现记录
                mydiamondsPresentationRecordTv.setTextColor(getResources().getColor(R.color.color_17c7eb));
                mydiamondsExchangeRecordTv.setTextColor(getResources().getColor(R.color.color_black));
                mydiamondsPresentationRecordLine.setVisibility(View.VISIBLE);
                mydiamondsExchangeRecordLine.setVisibility(View.GONE);
                typeId = 1;
                mPage = 1;
                mAdapter.clearData();
                mAdapter.setTypeID(typeId);

                doHttp(true);
                break;
        }
    }
}
