package com.atman.wysq.ui.discover;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.LiveHallGridViewAdapter;
import com.atman.wysq.model.response.GetLiveHallModel;
import com.atman.wysq.model.response.MyLiveInfoModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.widget.ShowLivePopWindow;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.DensityUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/12/28.
 */

public class LiveHallActivity extends MyBaseActivity implements AdapterInterface, View.OnClickListener {

    @Bind(R.id.pullToRefreshGridView)
    PullToRefreshGridView pullToRefreshGridView;

    private Context mContext = LiveHallActivity.this;
    private LiveHallGridViewAdapter mAdapter;
    private View mEmpty;
    private TextView mEmptyTX;
    private GetLiveHallModel mGetLiveHallModel;
    private TextView mLiveRightTX;
    private RelativeLayout mLiveRightRL;

    private int page = 1;
    private RelativeLayout.LayoutParams params;
    private ShowLivePopWindow pop;
    private SimpleDraweeView popSimpleDraweeView;
    private MyLiveInfoModel mMyLiveInfoModel;
    private TextView partLivepopGoliveTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livehall);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("直播大厅");

        mLiveRightTX = setBarRightTx(" 我要直播");
        Drawable drawable = getResources().getDrawable(R.mipmap.live_main_img_livecr);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mLiveRightTX.setCompoundDrawables(drawable, null, null, null);

        mLiveRightRL = getBarRightRl();
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                , RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.rightMargin = DensityUtil.dp2px(mContext, 10);
        mLiveRightRL.setLayoutParams(params);
        mLiveRightRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop = new ShowLivePopWindow(LiveHallActivity.this, v, getmWidth());
                OkHttpUtils.get().url(Common.Url_GetMyLiveInfo)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_GETMYLIVEINFO_ID).id(Common.NET_GETMYLIVEINFO_ID).build()
                        .execute(new MyStringCallback(mContext, LiveHallActivity.this, true));
            }
        });

        initGridView();
    }

    private void initGridView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshGridView);

        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无夜友直播！");

        mAdapter = new LiveHallGridViewAdapter(mContext, getmWidth(), this);
        pullToRefreshGridView.setEmptyView(mEmpty);
        pullToRefreshGridView.setAdapter(mAdapter);
        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(DirectSeedingRoomActivity.buildIntent(mContext
                        , mAdapter.getItem(position).getRoom_id()));
            }
        });
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();

        dohttp(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GETLIVELIST_ID) {
            mGetLiveHallModel = mGson.fromJson(data, GetLiveHallModel.class);
            if (mGetLiveHallModel.getBody() == null
                    || mGetLiveHallModel.getBody().size() == 0) {
                if (mAdapter!=null && mAdapter.getCount()>0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshGridView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshGridView);
                mAdapter.addBody(mGetLiveHallModel.getBody());
            }
        } else if (id == Common.NET_GETMYLIVEINFO_ID) {
            mMyLiveInfoModel = mGson.fromJson(data, MyLiveInfoModel.class);
            pop.showAtLocation(pop.getV(), Gravity.CENTER, 0, 0);
            pop.setTitle(mMyLiveInfoModel.getBody().getRoom_name());
            popSimpleDraweeView = pop.setBg(Common.ImageUrl + mMyLiveInfoModel.getBody().getPic_url());
            popSimpleDraweeView.setOnClickListener(this);
            partLivepopGoliveTx = pop.getPartLivepopGoliveTx();
            partLivepopGoliveTx.setOnClickListener(this);
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
        OkHttpUtils.get().url(Common.Url_GetLiveList + page)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GETLIVELIST_ID).id(Common.NET_GETLIVELIST_ID).build()
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
        OkHttpUtils.getInstance().cancelTag(Common.NET_GETLIVELIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GETMYLIVEINFO_ID);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.part_livepop_bg_iv:
                showToast("选择照片");
                break;
            case R.id.part_livepop_golive_tx:
                showToast("开始直播");
                pop.dismiss();
                break;
        }
    }
}
