package com.atman.wysq.ui.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.RankingListAdapter;
import com.atman.wysq.model.response.AllRankingModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.DensityUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/12/5.
 */

public class RankingListFragment extends MyBaseFragment implements View.OnClickListener {

    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;
    @Bind(R.id.postings_totop_iv)
    ImageView postingsTotopIv;

    private int modelId;
    private String typeId;
    private String mUrl;

    private View mEmpty;
    private TextView mEmptyTX;
    private RankingListAdapter mAdapter;
    private AllRankingModel mAllRankingModel;
    private List<AllRankingModel.BodyBean> topRankingList = new ArrayList<>();

    private View mTopView;
    private LinearLayout mTopOneLl, mTopTwoLl, mTopThreeLl;
    private ImageView mTopOneHeadIv, mTopTwoHeadIv, mTopThreeHeadIv;
    private TextView mTopOneNametTv, mTopTwoNametTv, mTopThreeNametTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rankinglist, null);
        Bundle b = getArguments();
        modelId = b.getInt("modelId");
        typeId = b.getString("typeId");
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);

        mEmpty = LayoutInflater.from(getActivity()).inflate(R.layout.part_list_empty, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无排行榜数据");

        mTopView = LayoutInflater.from(getActivity()).inflate(R.layout.part_allranking_top_view, null);
        mTopOneLl = (LinearLayout) mTopView.findViewById(R.id.part_ranking_one_top_ll);
        mTopOneLl.setOnClickListener(this);
        mTopTwoLl = (LinearLayout) mTopView.findViewById(R.id.part_ranking_two_top_ll);
        mTopTwoLl.setOnClickListener(this);
        mTopThreeLl = (LinearLayout) mTopView.findViewById(R.id.part_ranking_three_top_ll);
        mTopThreeLl.setOnClickListener(this);

        int w = (getmWidth() - DensityUtil.dp2px(getActivity(), 30))/3 + DensityUtil.dp2px(getActivity(), 15);
        int h = w * 290 / 150;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w, h);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mTopOneLl.setLayoutParams(params);
        RelativeLayout.LayoutParams paramsLeft = new RelativeLayout.LayoutParams(w
                , h-DensityUtil.dp2px(getActivity(), 30));
        paramsLeft.topMargin = DensityUtil.dp2px(getActivity(), 15);
        mTopTwoLl.setLayoutParams(paramsLeft);
        RelativeLayout.LayoutParams paramsRight = new RelativeLayout.LayoutParams(w
                , h-DensityUtil.dp2px(getActivity(), 30));
        paramsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        paramsRight.topMargin = DensityUtil.dp2px(getActivity(), 15);
        mTopThreeLl.setLayoutParams(paramsRight);

        mTopOneHeadIv = (ImageView) mTopView.findViewById(R.id.part_ranking_one_top_head_iv);
        mTopTwoHeadIv = (ImageView) mTopView.findViewById(R.id.part_ranking_two_top_head_iv);
        mTopThreeHeadIv = (ImageView) mTopView.findViewById(R.id.part_ranking_three_top_head_iv);
        mTopOneNametTv = (TextView) mTopView.findViewById(R.id.part_ranking_one_top_name_iv);
        mTopTwoNametTv = (TextView) mTopView.findViewById(R.id.part_ranking_two_top_name_iv);
        mTopThreeNametTv = (TextView) mTopView.findViewById(R.id.part_ranking_three_top_name_iv);

        mAdapter = new RankingListAdapter(getActivity());
        pullToRefreshListView.setEmptyView(mEmpty);
        pullToRefreshListView.getRefreshableView().addHeaderView(mTopView);
        pullToRefreshListView.setAdapter(mAdapter);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        pullToRefreshListView.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (firstVisibleItem >= 5 && totalItemCount!=0) {
//                    postingsTotopIv.setVisibility(View.VISIBLE);
//                } else {
//                    postingsTotopIv.setVisibility(View.GONE);
//                }
            }
        });
        postingsTotopIv.setVisibility(View.GONE);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        dohttp(true);
    }

    public void reGet(int id){
        modelId = id;
        dohttp(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getActivity() != null) {
//            dohttp(true);
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_RANKING_ID) {
            mAllRankingModel = mGson.fromJson(data, AllRankingModel.class);
            if (mAllRankingModel.getBody() == null
                    || mAllRankingModel.getBody().size() == 0) {
                if (mAdapter != null && mAdapter.getCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);
            } else {
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);

                int n = Math.min(3, mAllRankingModel.getBody().size());
                topRankingList.clear();
                for (int i=0;i<n;i++) {
                    topRankingList.add(mAllRankingModel.getBody().get(i));
                }
                UpdataView();

                mAdapter.clearData();
                mAdapter.addBody(mAllRankingModel.getBody());
            }
        }
    }

    private void UpdataView() {
        ImageView[] images = {mTopOneHeadIv, mTopTwoHeadIv, mTopThreeHeadIv};
        TextView[] names = {mTopOneNametTv, mTopTwoNametTv, mTopThreeNametTv};
        for (int i=0;i<topRankingList.size();i++) {
            ImageLoader.getInstance().displayImage(Common.ImageUrl + topRankingList.get(i).getIcon()
                    , images[i], MyBaseApplication.getApplication().getOptionsHead());
            names[i].setText(topRankingList.get(i).getNick_name());
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        dohttp(false);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        dohttp(false);
    }

    private void dohttp(boolean b) {
        if (modelId == 88) {
            mUrl = Common.Url_Get_Char_Ranking;
        } else {
            mUrl = Common.Url_Get_Gold_Ranking;
        }
        if (typeId.equals("1") || typeId.equals("2")) {
            mUrl += typeId;
        }
        OkHttpUtils.get().url(mUrl).id(Common.NET_GET_RANKING_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_RANKING_ID).build()
                .execute(new MyStringCallback(getActivity(), this, b));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_RANKING_ID);
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.part_ranking_one_top_ll:
                break;
            case R.id.part_ranking_two_top_ll:
                break;
            case R.id.part_ranking_three_top_ll:
                break;
        }
    }
}
