package com.atman.wysq.ui.community;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.CommunityNewAdapter;
import com.atman.wysq.model.response.CommunityNewModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.SpaceItemDecorationGrivView;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/3/24.
 */

public class CommunityNewFragment extends MyBaseFragment implements AdapterInterface {

    @Bind(R.id.community_empty_tx)
    TextView communityEmptyTx;
    @Bind(R.id.community_recycler)
    PullToRefreshRecyclerView communityRecycler;
    @Bind(R.id.community_tab_dynamic)
    RadioButton communityTabDynamic;
    @Bind(R.id.community_tab_voice)
    RadioButton communityTabVoice;
    @Bind(R.id.community_tab_video)
    RadioButton communityTabVideo;
    @Bind(R.id.community_tab_hot)
    RadioButton communityTabHot;

    private CommunityNewAdapter mCommunityNewAdapter;
    private RecyclerView mRecyclerView;
    private CommunityNewModel mCommunityNewModel;

    private int mPage = 1;
    private int mTpyeId = 1;
    private boolean isError = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_communitynew, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        initRecycler();
    }

    private void initRecycler() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, communityRecycler);

        mCommunityNewAdapter = new CommunityNewAdapter(getActivity(), getmWidth(), this);

        mRecyclerView = communityRecycler.getRefreshableView();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_dp_5);
        mRecyclerView.addItemDecoration(new SpaceItemDecorationGrivView(spacingInPixels));
        mRecyclerView.setAdapter(mCommunityNewAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState==0) {//滑动停止
                    Fresco.getImagePipeline().resume();//开启图片加载
                } else {
                    Fresco.getImagePipeline().pause();//暂停图片加载
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getActivity() != null) {
            if (isError) {
                isError = false;
                doHttp(true);
            }
        }
    }

    private void doHttp(boolean b) {
        String url = "";
        if (mPage==1) {
            if (mTpyeId==4) {
                showToast("开发中");
                return;
            } else {
                url = Common.Url_Get_Community_First + mTpyeId;
            }
        } else {
            switch (mTpyeId) {
                case 1:
                    url = Common.Url_Get_Community_Dynamic + mPage;
                    break;
                case 2:
                    url = Common.Url_Get_Community_Audio + mPage;
                    break;
                case 3:
                    url = Common.Url_Get_Community_Video + mPage;
                    break;
                case 4:
                    showToast("开发中");
                    break;
            }
        }
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_COMMUNITY_FIRST_ID);
        OkHttpUtils.get().url(url)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_COMMUNITY_FIRST_ID).id(Common.NET_GET_COMMUNITY_FIRST_ID).build()
                .execute(new MyStringCallback(getActivity(), this, b));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_COMMUNITY_FIRST_ID) {
            mCommunityNewModel = mGson.fromJson(data, CommunityNewModel.class);
            if (mCommunityNewModel.getBody() == null
                    || mCommunityNewModel.getBody().size() == 0) {
                if (mCommunityNewAdapter != null && mCommunityNewAdapter.getItemCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, communityRecycler);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, communityRecycler);
                if (mPage == 1) {
                    mCommunityNewAdapter.clearData();
                }
                mCommunityNewAdapter.addData(mCommunityNewModel.getBody());
            }

            if (mCommunityNewAdapter!=null && mCommunityNewAdapter.getItemCount()>0) {
                communityEmptyTx.setVisibility(View.GONE);
                communityRecycler.setVisibility(View.VISIBLE);
            } else {
                communityEmptyTx.setVisibility(View.VISIBLE);
                communityRecycler.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        mPage = 1;
        mCommunityNewAdapter.clearData();
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_COMMUNITY_FIRST_ID);
    }

    @OnClick({R.id.part_community_topleft_ll, R.id.community_tab_dynamic, R.id.community_tab_voice, R.id.community_tab_video, R.id.community_tab_hot, R.id.part_community_topright_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.part_community_topleft_ll:
                break;
            case R.id.community_tab_dynamic:
                mTpyeId = 1;
                mPage = 1;
                mCommunityNewAdapter.clearData();
                doHttp(false);
                break;
            case R.id.community_tab_voice:
                mTpyeId = 2;
                mPage = 1;
                mCommunityNewAdapter.clearData();
                doHttp(false);
                break;
            case R.id.community_tab_video:
                mTpyeId = 3;
                mPage = 1;
                mCommunityNewAdapter.clearData();
                doHttp(false);
                break;
            case R.id.community_tab_hot:
                mTpyeId = 4;
                mPage = 1;
                mCommunityNewAdapter.clearData();
                doHttp(false);
                break;
            case R.id.part_community_topright_ll:
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        CommunityNewModel.BodyBean temp = mCommunityNewAdapter.getItemData(position);
        int CategoryId = temp.getCategory();
        switch (CategoryId) {
            case 1://图文
                startActivity(PostingsDetailActivity.buildIntent(getActivity()
                        , temp.getTitle(), temp.getBlog_board_id(), false, temp.getVip_level()));
                break;
            case 2://语音
                break;
            case 3://视频
                break;
            case 4://直播
                break;
        }
    }
}
