package com.atman.wysq.ui.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.atman.wysq.R;
import com.atman.wysq.adapter.AllRewardListAdapter;
import com.atman.wysq.model.response.GetRewardListNewModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.SpaceItemDecoration;
import com.atman.wysq.utils.SpaceItemDecorationLinear;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/5 11:55
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class BlogRewardListActivty extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.community_recycler)
    PullToRefreshRecyclerView communityRecycler;

    private Context mContext = BlogRewardListActivty.this;

    private int blogId = -1;
    private AllRewardListAdapter mAllRewardListAdapter;
    private int mPage = 1;
    private RecyclerView mRecyclerView;
    private GetRewardListNewModel mGetRewardListNewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activty_blogrewardlist);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, int blogId) {
        Intent intent = new Intent(context, BlogRewardListActivty.class);
        intent.putExtra("blogId", blogId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        blogId = getIntent().getIntExtra("blogId", -1);

        setBarTitleTx("所有献花");

        initRecycler();
    }

    private void initRecycler() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, communityRecycler);

        mAllRewardListAdapter = new AllRewardListAdapter(mContext, getmWidth(), this);

        mRecyclerView = communityRecycler.getRefreshableView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_dp_1);
        mRecyclerView.addItemDecoration(new SpaceItemDecorationLinear(spacingInPixels));
        mRecyclerView.setAdapter(mAllRewardListAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {//滑动停止
                    Fresco.getImagePipeline().resume();//开启图片加载
                } else {
                    Fresco.getImagePipeline().pause();//暂停图片加载
                }
            }
        });
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();

        doHttp(true);
    }

    private void doHttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Get_Reward_List + blogId +"/"+mPage)
                .id(Common.NET_GET_REPWARD_LIST_ID).tag(Common.NET_GET_REPWARD_LIST_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .build().execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_REPWARD_LIST_ID) {
            mGetRewardListNewModel = mGson.fromJson(data, GetRewardListNewModel.class);
            if (mGetRewardListNewModel.getBody() == null
                    || mGetRewardListNewModel.getBody().size() == 0) {
                if (mAllRewardListAdapter != null && mAllRewardListAdapter.getItemCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, communityRecycler);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, communityRecycler);
                if (mPage == 1) {
                    mAllRewardListAdapter.clearData();
                }
                mAllRewardListAdapter.addData(mGetRewardListNewModel.getBody());
            }
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        mPage = 1;
        mAllRewardListAdapter.clearData();
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
        onLoad(PullToRefreshBase.Mode.BOTH, communityRecycler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_REPWARD_LIST_ID);
    }

    @Override
    public void onItemClick(View view, int position) {
//        startActivity(OtherPersonalActivity.buildIntent(mContext, mRewardListViewAdapter.getItem(position).getUser_id()));
    }
}
