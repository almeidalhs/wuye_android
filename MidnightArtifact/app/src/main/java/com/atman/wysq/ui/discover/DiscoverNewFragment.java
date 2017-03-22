package com.atman.wysq.ui.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.DiscoverNewFindAdapter;
import com.atman.wysq.adapter.RankingListAdapter;
import com.atman.wysq.model.response.AllRankingModel;
import com.atman.wysq.model.response.DiscoverNewModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.ui.yunxinfriend.OtherPersonalActivity;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.DensityUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/3/20.
 */

public class DiscoverNewFragment extends MyBaseFragment implements AdapterInterface, View.OnClickListener {

    @Bind(R.id.discovernew_find_tab_recommend)
    RadioButton discovernewFindTabRecommend;
    @Bind(R.id.discovernew_find_tab_popularity)
    RadioButton discovernewFindTabPopularity;
    @Bind(R.id.discovernew_find_tab_new)
    RadioButton discovernewFindTabNew;
    @Bind(R.id.discovernew_find_tab_man)
    RadioButton discovernewFindTabMan;
    @Bind(R.id.discovernew_find_tabs_rg)
    RadioGroup discovernewFindTabsRg;
    @Bind(R.id.discovernew_find_empty_tx)
    TextView discovernewFindEmptyTx;
    @Bind(R.id.discovernew_find_recycler)
    PullToRefreshRecyclerView discovernewFindRecycler;
    @Bind(R.id.discover_new_find_ll)
    LinearLayout discoverNewFindLl;
    @Bind(R.id.ranking_tab_week_rt)
    RadioButton rankingTabWeekRt;
    @Bind(R.id.ranking_tab_month_rt)
    RadioButton rankingTabMonthRt;
    @Bind(R.id.ranking_tab_total_rt)
    RadioButton rankingTabTotalRt;
    @Bind(R.id.ranking_top_tab_rg)
    RadioGroup rankingTopTabRg;
    @Bind(R.id.fragment_ranking_empty_tx)
    TextView fragmentRankingEmptyTx;
    @Bind(R.id.pullToRefreshListView)
    ListView pullToRefreshListView;
    @Bind(R.id.postings_totop_iv)
    ImageView postingsTotopIv;
    @Bind(R.id.fragment_ranking_rl)
    RelativeLayout fragmentRankingRl;
    @Bind(R.id.discover_new_ranking_ll)
    LinearLayout discoverNewRankingLl;
    @Bind(R.id.part_newdiscover_topleft_ll)
    LinearLayout partNewdiscoverTopleftLl;
    @Bind(R.id.tab_find)
    RadioButton tabFind;
    @Bind(R.id.tab_charmlist)
    RadioButton tabCharmlist;
    @Bind(R.id.tab_fortunelist)
    RadioButton tabFortunelist;
    @Bind(R.id.tabs_rg)
    RadioGroup tabsRg;
    @Bind(R.id.part_newdiscover_topright_ll)
    LinearLayout partNewdiscoverToprightLl;
    
    private boolean isError = true;
    private DiscoverNewFindAdapter mDiscoverNewFindAdapter;
    private RecyclerView mRecyclerView;
    private DiscoverNewModel mDiscoverNewModel;

    private int mPage = 1;
    private int mTpyeId = 0;

    private RankingListAdapter mAdapter;
    private AllRankingModel mAllRankingModel;
    private List<AllRankingModel.BodyBean> topRankingList = new ArrayList<>();
    private View mTopView;
    private LinearLayout mTopOneLl, mTopTwoLl, mTopThreeLl;
    private SimpleDraweeView mTopOneHeadIv, mTopTwoHeadIv, mTopThreeHeadIv;
    private TextView mTopOneNametTv, mTopTwoNametTv, mTopThreeNametTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discovernew, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        initRecycler();
        initListView();
    }

    private void initRecycler() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, discovernewFindRecycler);

        mDiscoverNewFindAdapter = new DiscoverNewFindAdapter(getActivity(), getmWidth(), this);

        mRecyclerView = discovernewFindRecycler.getRefreshableView();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));//这里用线性显示 类似于listview
        mRecyclerView.setAdapter(mDiscoverNewFindAdapter);
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

    private void initListView() {

        mTopView = LayoutInflater.from(getActivity()).inflate(R.layout.part_allranking_top_view, null);
        mTopOneLl = (LinearLayout) mTopView.findViewById(R.id.part_ranking_one_top_ll);
        mTopOneLl.setOnClickListener(this);
        mTopTwoLl = (LinearLayout) mTopView.findViewById(R.id.part_ranking_two_top_ll);
        mTopTwoLl.setOnClickListener(this);
        mTopThreeLl = (LinearLayout) mTopView.findViewById(R.id.part_ranking_three_top_ll);
        mTopThreeLl.setOnClickListener(this);

        int w = (getmWidth() - DensityUtil.dp2px(getActivity(), 30)) / 3 + DensityUtil.dp2px(getActivity(), 15);
        int h = w * 260 / 150;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w, h);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mTopOneLl.setLayoutParams(params);
        RelativeLayout.LayoutParams paramsLeft = new RelativeLayout.LayoutParams(w
                , h - DensityUtil.dp2px(getActivity(), 30));
        paramsLeft.topMargin = DensityUtil.dp2px(getActivity(), 15);
        mTopTwoLl.setLayoutParams(paramsLeft);
        RelativeLayout.LayoutParams paramsRight = new RelativeLayout.LayoutParams(w
                , h - DensityUtil.dp2px(getActivity(), 30));
        paramsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        paramsRight.topMargin = DensityUtil.dp2px(getActivity(), 15);
        mTopThreeLl.setLayoutParams(paramsRight);

        mTopOneHeadIv = (SimpleDraweeView) mTopView.findViewById(R.id.part_ranking_one_top_head_iv);
        mTopTwoHeadIv = (SimpleDraweeView) mTopView.findViewById(R.id.part_ranking_two_top_head_iv);
        mTopThreeHeadIv = (SimpleDraweeView) mTopView.findViewById(R.id.part_ranking_three_top_head_iv);
        mTopOneNametTv = (TextView) mTopView.findViewById(R.id.part_ranking_one_top_name_iv);
        mTopTwoNametTv = (TextView) mTopView.findViewById(R.id.part_ranking_two_top_name_iv);
        mTopThreeNametTv = (TextView) mTopView.findViewById(R.id.part_ranking_three_top_name_iv);

        mAdapter = new RankingListAdapter(getActivity());
        pullToRefreshListView.addHeaderView(mTopView);
        pullToRefreshListView.setAdapter(mAdapter);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toOtherPersonal(mAdapter.getItem(position - 2).getUser_id());
            }
        });
        pullToRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
    public void initIntentAndMemData() {
        super.initIntentAndMemData();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        doHttp(true);
    }

    private void doHttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Find_New + mTpyeId +"/"+mPage)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_FIND_NEW_ID).id(Common.NET_FIND_NEW_ID).build()
                .execute(new MyStringCallback(getActivity(), this, b));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getActivity() != null) {
            if (isError) {
                isError = false;
            }
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        if (id == Common.NET_GET_RANKING_ID) {
            mAllRankingModel = mGson.fromJson(data, AllRankingModel.class);
            if (mAllRankingModel.getBody() == null
                    || mAllRankingModel.getBody().size() == 0) {
                if (mAdapter != null && mAdapter.getCount() > 0) {
                    showToast("没有更多");
                }
                fragmentRankingEmptyTx.setVisibility(View.VISIBLE);
                fragmentRankingRl.setVisibility(View.GONE);
            } else {
                fragmentRankingEmptyTx.setVisibility(View.GONE);
                fragmentRankingRl.setVisibility(View.VISIBLE);

                int n = Math.min(3, mAllRankingModel.getBody().size());
                topRankingList.clear();
                for (int i = 0; i < n; i++) {
                    topRankingList.add(mAllRankingModel.getBody().get(i));
                }
                UpdataView();

                mAdapter.clearData();
                mAdapter.addBody(mAllRankingModel.getBody());
            }
            super.onStringResponse(data, response, id);
        } else if (id == Common.NET_FIND_NEW_ID) {
            super.onStringResponse(data, response, id);
            mDiscoverNewModel = mGson.fromJson(data, DiscoverNewModel.class);
            if (mDiscoverNewModel.getBody() == null
                    || mDiscoverNewModel.getBody().size() == 0) {
                if (mDiscoverNewFindAdapter != null && mDiscoverNewFindAdapter.getItemCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, discovernewFindRecycler);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, discovernewFindRecycler);
                if (mPage == 1) {
                    mDiscoverNewFindAdapter.clearData();
                }
                mDiscoverNewFindAdapter.addData(mDiscoverNewModel.getBody());
            }

            if (mDiscoverNewFindAdapter!=null && mDiscoverNewFindAdapter.getItemCount()>0) {
                discovernewFindEmptyTx.setVisibility(View.GONE);
                discovernewFindRecycler.setVisibility(View.VISIBLE);
            } else {
                discovernewFindEmptyTx.setVisibility(View.VISIBLE);
                discovernewFindRecycler.setVisibility(View.GONE);
            }
        }
    }

    private void UpdataView() {
        SimpleDraweeView[] images = {mTopOneHeadIv, mTopTwoHeadIv, mTopThreeHeadIv};
        TextView[] names = {mTopOneNametTv, mTopTwoNametTv, mTopThreeNametTv};
        LinearLayout[] Ll = {mTopOneLl, mTopTwoLl, mTopThreeLl};
        for (int i = 0; i < 3; i++) {
            Ll[i].setVisibility(View.GONE);
        }
        for (int i = 0; i < topRankingList.size(); i++) {
            Ll[i].setVisibility(View.VISIBLE);
            images[i].setImageURI(Common.ImageUrl + topRankingList.get(i).getIcon());
            names[i].setText(topRankingList.get(i).getNick_name());
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        mPage = 1;
        mDiscoverNewFindAdapter.clearData();
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
        onLoad(PullToRefreshBase.Mode.BOTH, discovernewFindRecycler);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_RANKING_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_FIND_NEW_ID);

        ButterKnife.unbind(this);
    }

    @OnClick({R.id.part_newdiscover_topleft_ll, R.id.part_newdiscover_topright_ll,
            R.id.discovernew_find_tab_recommend, R.id.discovernew_find_tab_popularity, 
            R.id.discovernew_find_tab_new, R.id.discovernew_find_tab_man, R.id.ranking_tab_week_rt, 
            R.id.ranking_tab_month_rt, R.id.ranking_tab_total_rt, R.id.postings_totop_iv, 
            R.id.tab_find, R.id.tab_charmlist, R.id.tab_fortunelist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.part_newdiscover_topleft_ll://场景
                showToast("场景");
                break;
            case R.id.part_newdiscover_topright_ll://声音
                showToast("声音");
                break;
            case R.id.discovernew_find_tab_recommend://发现——推荐
                mTpyeId = 0;
                mPage = 1;
                mDiscoverNewFindAdapter.clearData();
                doHttp(true);
                break;
            case R.id.discovernew_find_tab_popularity://发现——人气
                mTpyeId = 1;
                mPage = 1;
                mDiscoverNewFindAdapter.clearData();
                doHttp(true);
                break;
            case R.id.discovernew_find_tab_new://发现——新人
                mTpyeId = 2;
                mPage = 1;
                mDiscoverNewFindAdapter.clearData();
                doHttp(true);
                break;
            case R.id.discovernew_find_tab_man://发现——男神
                mTpyeId = 4;
                mPage = 1;
                mDiscoverNewFindAdapter.clearData();
                doHttp(true);
                break;
            case R.id.ranking_tab_week_rt://周排行
                mAdapter.clearData();
                typeId = typeListId[0];
                doRankingHttp(true);
                break;
            case R.id.ranking_tab_month_rt://月排行
                mAdapter.clearData();
                typeId = typeListId[1];
                doRankingHttp(true);
                break;
            case R.id.ranking_tab_total_rt://总排行
                mAdapter.clearData();
                typeId = typeListId[2];
                doRankingHttp(true);
                break;
            case R.id.postings_totop_iv:
                break;
            case R.id.tab_find://发现
                discoverNewFindLl.setVisibility(View.VISIBLE);
                discoverNewRankingLl.setVisibility(View.GONE);
                break;
            case R.id.tab_charmlist://魅力榜
                discoverNewFindLl.setVisibility(View.GONE);
                discoverNewRankingLl.setVisibility(View.VISIBLE);

                mAdapter.clearData();
                selectModelId = modelListId[0];
                typeId = typeListId[0];
                rankingTabWeekRt.performClick();
                doRankingHttp(true);
                break;
            case R.id.tab_fortunelist://财富榜
                discoverNewFindLl.setVisibility(View.GONE);
                discoverNewRankingLl.setVisibility(View.VISIBLE);

                mAdapter.clearData();
                selectModelId = modelListId[1];
                typeId = typeListId[0];
                rankingTabWeekRt.performClick();
                doRankingHttp(true);
                break;
            case R.id.part_ranking_one_top_ll://榜单第一
                if (topRankingList != null && topRankingList.size() >= 1) {
                    toOtherPersonal(topRankingList.get(0).getUser_id());
                }
                break;
            case R.id.part_ranking_two_top_ll://榜单第二
                if (topRankingList != null && topRankingList.size() >= 2) {
                    toOtherPersonal(topRankingList.get(1).getUser_id());
                }
                break;
            case R.id.part_ranking_three_top_ll://榜单第三
                if (topRankingList != null && topRankingList.size() >= 3) {
                    toOtherPersonal(topRankingList.get(2).getUser_id());
                }
                break;
        }
    }

    private String mUrl;
    private String[] typeListId = {"1", "2", ""}; //0：周排行 2:月排行 3:总排行
    private int[] modelListId = {88, 99}; //88：魅力 99:财富
    private String typeId = typeListId[0];
    private int selectModelId = modelListId[0];

    private void doRankingHttp(boolean b) {
        if (selectModelId == 88) {
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

    private void toOtherPersonal(long userId) {
        startActivity(OtherPersonalActivity.buildIntent(getActivity(), userId));
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(OtherPersonalActivity.buildIntent(getActivity()
                , mDiscoverNewFindAdapter.getListData().get(position).getUser_id()));
    }
}
