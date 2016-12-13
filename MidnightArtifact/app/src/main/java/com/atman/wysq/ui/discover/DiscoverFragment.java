package com.atman.wysq.ui.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.FindTopAdapter;
import com.atman.wysq.adapter.GalleryAdapter;
import com.atman.wysq.iimp.SpAdapterInterface;
import com.atman.wysq.model.response.CharmestRankingModel;
import com.atman.wysq.model.response.GoldRankingModel;
import com.atman.wysq.model.response.RecommendUserModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.ui.yunxinfriend.OtherPersonalActivity;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.widget.CustomImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 描述 发现
 * 作者 tangbingliang
 * 时间 16/7/1 18:12
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class DiscoverFragment extends MyBaseFragment implements SpAdapterInterface
        , View.OnClickListener, AdapterInterface {

    @Bind(R.id.fragment_bar_title_iv)
    ImageView fragmentBarTitleIv;
    @Bind(R.id.fancyCoverFlow)
    FancyCoverFlow fancyCoverFlow;
    @Bind(R.id.item_charmest_one_head_iv)
    CustomImageView itemCharmestOneHeadIv;
    @Bind(R.id.item_charmest_one_gender_iv)
    ImageView itemCharmestOneGenderIv;
    @Bind(R.id.item_charmest_one_verify_iv)
    ImageView itemCharmestOneVerifyIv;
    @Bind(R.id.item_charmest_one_name_tv)
    TextView itemCharmestOneNameTv;
    @Bind(R.id.item_charmest_one_ll)
    LinearLayout itemCharmestOneLl;
    @Bind(R.id.item_charmest_two_head_iv)
    CustomImageView itemCharmestTwoHeadIv;
    @Bind(R.id.item_charmest_two_gender_iv)
    ImageView itemCharmestTwoGenderIv;
    @Bind(R.id.item_charmest_two_verify_iv)
    ImageView itemCharmestTwoVerifyIv;
    @Bind(R.id.item_charmest_two_name_tv)
    TextView itemCharmestTwoNameTv;
    @Bind(R.id.item_charmest_two_ll)
    LinearLayout itemCharmestTwoLl;
    @Bind(R.id.item_charmest_three_head_iv)
    CustomImageView itemCharmestThreeHeadIv;
    @Bind(R.id.item_charmest_three_gender_iv)
    ImageView itemCharmestThreeGenderIv;
    @Bind(R.id.item_charmest_three_verify_iv)
    ImageView itemCharmestThreeVerifyIv;
    @Bind(R.id.item_charmest_three_name_tv)
    TextView itemCharmestThreeNameTv;
    @Bind(R.id.item_charmest_three_ll)
    LinearLayout itemCharmestThreeLl;
    @Bind(R.id.item_gold_one_head_iv)
    CustomImageView itemGoldOneHeadIv;
    @Bind(R.id.item_gold_one_gender_iv)
    ImageView itemGoldOneGenderIv;
    @Bind(R.id.item_gold_one_verify_iv)
    ImageView itemGoldOneVerifyIv;
    @Bind(R.id.item_gold_one_name_tv)
    TextView itemGoldOneNameTv;
    @Bind(R.id.item_gold_one_ll)
    LinearLayout itemGoldOneLl;
    @Bind(R.id.item_gold_two_head_iv)
    CustomImageView itemGoldTwoHeadIv;
    @Bind(R.id.item_gold_two_gender_iv)
    ImageView itemGoldTwoGenderIv;
    @Bind(R.id.item_gold_two_verify_iv)
    ImageView itemGoldTwoVerifyIv;
    @Bind(R.id.item_gold_two_name_tv)
    TextView itemGoldTwoNameTv;
    @Bind(R.id.item_gold_two_ll)
    LinearLayout itemGoldTwoLl;
    @Bind(R.id.item_gold_three_head_iv)
    CustomImageView itemGoldThreeHeadIv;
    @Bind(R.id.item_gold_three_gender_iv)
    ImageView itemGoldThreeGenderIv;
    @Bind(R.id.item_gold_three_verify_iv)
    ImageView itemGoldThreeVerifyIv;
    @Bind(R.id.item_gold_three_name_tv)
    TextView itemGoldThreeNameTv;
    @Bind(R.id.item_gold_three_ll)
    LinearLayout itemGoldThreeLl;
    @Bind(R.id.discover_find_rv)
    RecyclerView discoverFindRv;

    private boolean isError = true;
    private int mPosition;
    private RecommendUserModel mRecommendUserModel;
    private RecommendUserModel mFindLikeModel;
    private FindTopAdapter topAdapter;
    private GalleryAdapter adapter;

    private CharmestRankingModel mCharmestRankingModel;
    private GoldRankingModel mGoldRankingModel;

    private List<CustomImageView> itemCharmestHeadIv = new ArrayList<>();
    private List<ImageView> itemCharmestGenderIv = new ArrayList<>();
    private List<ImageView> itemCharmestVerifyIv = new ArrayList<>();
    private List<TextView> itemCharmestNameTv = new ArrayList<>();
    private List<LinearLayout> itemCharmestLl = new ArrayList<>();

    private List<CustomImageView> itemGoldHeadIv = new ArrayList<>();
    private List<ImageView> itemGoldGenderIv = new ArrayList<>();
    private List<ImageView> itemGoldVerifyIv = new ArrayList<>();
    private List<TextView> itemGoldNameTv = new ArrayList<>();
    private List<LinearLayout> itemGoldLl = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, null);
        ButterKnife.bind(this, view);

        initView();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        fragmentBarTitleIv.setImageResource(R.mipmap.top_discover_ic);
    }

    private void initView() {
        itemCharmestHeadIv.add(itemCharmestOneHeadIv);
        itemCharmestOneHeadIv.setOnClickListener(this);
        itemCharmestHeadIv.add(itemCharmestTwoHeadIv);
        itemCharmestTwoHeadIv.setOnClickListener(this);
        itemCharmestHeadIv.add(itemCharmestThreeHeadIv);
        itemCharmestThreeHeadIv.setOnClickListener(this);

        itemCharmestGenderIv.add(itemCharmestOneGenderIv);
        itemCharmestGenderIv.add(itemCharmestTwoGenderIv);
        itemCharmestGenderIv.add(itemCharmestThreeGenderIv);

        itemCharmestVerifyIv.add(itemCharmestOneVerifyIv);
        itemCharmestVerifyIv.add(itemCharmestTwoVerifyIv);
        itemCharmestVerifyIv.add(itemCharmestThreeVerifyIv);

        itemCharmestNameTv.add(itemCharmestOneNameTv);
        itemCharmestNameTv.add(itemCharmestTwoNameTv);
        itemCharmestNameTv.add(itemCharmestThreeNameTv);

        itemCharmestLl.add(itemCharmestOneLl);
        itemCharmestLl.add(itemCharmestTwoLl);
        itemCharmestLl.add(itemCharmestThreeLl);

        itemGoldHeadIv.add(itemGoldOneHeadIv);
        itemGoldOneHeadIv.setOnClickListener(this);
        itemGoldHeadIv.add(itemGoldTwoHeadIv);
        itemGoldTwoHeadIv.setOnClickListener(this);
        itemGoldHeadIv.add(itemGoldThreeHeadIv);
        itemGoldThreeHeadIv.setOnClickListener(this);

        itemGoldGenderIv.add(itemGoldOneGenderIv);
        itemGoldGenderIv.add(itemGoldTwoGenderIv);
        itemGoldGenderIv.add(itemGoldThreeGenderIv);

        itemGoldVerifyIv.add(itemGoldOneVerifyIv);
        itemGoldVerifyIv.add(itemGoldTwoVerifyIv);
        itemGoldVerifyIv.add(itemGoldThreeVerifyIv);

        itemGoldNameTv.add(itemGoldOneNameTv);
        itemGoldNameTv.add(itemGoldTwoNameTv);
        itemGoldNameTv.add(itemGoldThreeNameTv);

        itemGoldLl.add(itemGoldOneLl);
        itemGoldLl.add(itemGoldTwoLl);
        itemGoldLl.add(itemGoldThreeLl);
    }

    private void initLike() {

        adapter = new GalleryAdapter(getActivity(), mFindLikeModel.getBody(), this);
        //设置布局管理器
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        discoverFindRv.setLayoutManager(linearLayoutManager);
        //设置适配器
        discoverFindRv.setAdapter(adapter);
        discoverFindRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                if (newState==0) {
                    adapter.setSlecteID(position+1);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initTop3D() {
        fancyCoverFlow.setFadingEdgeLength(0);
        topAdapter = new FindTopAdapter(getActivity(), mRecommendUserModel, 1, this);
        fancyCoverFlow.setAdapter(topAdapter);
        fancyCoverFlow.setScend(false);
        fancyCoverFlow.setSelection(mRecommendUserModel.getBody().size() / 2
                + mRecommendUserModel.getBody().size() * 10000 - 1);
    }

    @Override
    public void initIntentAndMemData() {
        super.initIntentAndMemData();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
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
                //1代表周排名,2代表月排名
                OkHttpUtils.get().url(Common.Url_Get_Gold_Ranking + 1)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_GET_GOLD_RANKING_ID).id(Common.NET_GET_GOLD_RANKING_ID).build()
                        .execute(new MyStringCallback(getActivity(), this, true));
                OkHttpUtils.get().url(Common.Url_Get_Char_Ranking + 1)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_GET_CHAR_RANKING_ID).id(Common.NET_GET_CHAR_RANKING_ID).build()
                        .execute(new MyStringCallback(getActivity(), this, true));
                Map<String, String> p = new HashMap<>();
                p.put("Content-Type", "application/json; charset=utf-8");
                OkHttpUtils.get().url(Common.Url_Get_RecommendFriends)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_GET_RECOMMENDFRIENDS).id(Common.NET_GET_RECOMMENDFRIENDS).build()
                        .execute(new MyStringCallback(getActivity(), this, true));
                OkHttpUtils.get().url(Common.Url_Get_RecommendFriends + "/2")
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_GET_FINDLIKE_ID).id(Common.NET_GET_FINDLIKE_ID).build()
                        .execute(new MyStringCallback(getActivity(), this, true));
            }
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_RECOMMENDFRIENDS) {
            mRecommendUserModel = mGson.fromJson(data, RecommendUserModel.class);
            initTop3D();
        } else if (id == Common.NET_GET_FINDLIKE_ID) {
            mFindLikeModel = mGson.fromJson(data, RecommendUserModel.class);
            initLike();
        } else if (id == Common.NET_GET_CHAR_RANKING_ID) {
            mCharmestRankingModel = mGson.fromJson(data, CharmestRankingModel.class);

            CharmestRankingModel.BodyBean body = null;
            int n = 3;
            n = Math.min(n, mCharmestRankingModel.getBody().size());
            if (itemCharmestLl.size() != 0) {
                for (int i = 0; i < n; i++) {
                    itemCharmestLl.get(i).setVisibility(View.VISIBLE);
                    body = mCharmestRankingModel.getBody().get(i);
                    itemCharmestNameTv.get(i).setText(body.getNick_name());
                    if (body.getSex().equals("M")) {
                        itemCharmestGenderIv.get(i).setImageResource(R.mipmap.personal_man_ic);
                    } else {
                        itemCharmestGenderIv.get(i).setImageResource(R.mipmap.personal_weman_ic);
                    }
                    if (body.getVerify_status() == 1) {
                        itemCharmestVerifyIv.get(i).setVisibility(View.VISIBLE);
                        itemCharmestGenderIv.get(i).setVisibility(View.GONE);
                    } else {
                        itemCharmestVerifyIv.get(i).setVisibility(View.GONE);
                        itemCharmestGenderIv.get(i).setVisibility(View.VISIBLE);
                    }
                    ImageLoader.getInstance().displayImage(Common.ImageUrl + body.getIcon()
                            , itemCharmestHeadIv.get(i), MyBaseApplication.getApplication().getOptionsNot());
                }
            }
        } else if (id == Common.NET_GET_GOLD_RANKING_ID) {
            mGoldRankingModel = mGson.fromJson(data, GoldRankingModel.class);

            GoldRankingModel.BodyBean body = null;
            int n = 3;
            n = Math.min(n, mGoldRankingModel.getBody().size());
            if (itemGoldLl.size() != 0) {
                for (int i = 0; i < n; i++) {
                    itemGoldLl.get(i).setVisibility(View.VISIBLE);
                    body = mGoldRankingModel.getBody().get(i);
                    itemGoldNameTv.get(i).setText(body.getNick_name());
                    if (body.getSex().equals("M")) {
                        itemGoldGenderIv.get(i).setImageResource(R.mipmap.personal_man_ic);
                    } else {
                        itemGoldGenderIv.get(i).setImageResource(R.mipmap.personal_weman_ic);
                    }
                    if (body.getVerify_status() == 1) {
                        itemGoldVerifyIv.get(i).setVisibility(View.VISIBLE);
                        itemGoldGenderIv.get(i).setVisibility(View.GONE);
                    } else {
                        itemGoldVerifyIv.get(i).setVisibility(View.GONE);
                        itemGoldGenderIv.get(i).setVisibility(View.VISIBLE);
                    }
                    ImageLoader.getInstance().displayImage(Common.ImageUrl + body.getIcon()
                            , itemGoldHeadIv.get(i), MyBaseApplication.getApplication().getOptionsNot());
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_RECOMMENDFRIENDS);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_FINDLIKE_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_CHAR_RANKING_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_GOLD_RANKING_ID);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.item_charmest_one_ll, R.id.item_charmest_two_ll, R.id.item_charmest_three_ll
            , R.id.item_gold_one_ll, R.id.item_gold_two_ll, R.id.item_gold_three_ll, R.id.discover_more_tv
            , R.id.discover_fm_rl, R.id.discover_find_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_charmest_one_ll:
                break;
            case R.id.item_charmest_two_ll:
                break;
            case R.id.item_charmest_three_ll:
                break;
            case R.id.item_gold_one_ll:
                break;
            case R.id.item_gold_two_ll:
                break;
            case R.id.item_gold_three_ll:
                break;
            case R.id.discover_more_tv:
                startActivity(new Intent(getActivity(), AllRankingListActivity.class));
                break;
            case R.id.discover_fm_rl:
                showWraning("此功能还在开发中，敬请期待！");
                break;
            case R.id.discover_find_rl:
                startActivity(new Intent(getActivity(), RecommendUsersActivity.class));
                break;
            case R.id.item_charmest_one_head_iv:
                startActivity(OtherPersonalActivity.buildIntent(getActivity()
                        , mCharmestRankingModel.getBody().get(0).getUser_id()));
                break;
            case R.id.item_charmest_two_head_iv:
                startActivity(OtherPersonalActivity.buildIntent(getActivity()
                        , mCharmestRankingModel.getBody().get(1).getUser_id()));
                break;
            case R.id.item_charmest_three_head_iv:
                startActivity(OtherPersonalActivity.buildIntent(getActivity()
                        , mCharmestRankingModel.getBody().get(2).getUser_id()));
                break;
            case R.id.item_gold_one_head_iv:
                startActivity(OtherPersonalActivity.buildIntent(getActivity()
                        , mGoldRankingModel.getBody().get(0).getUser_id()));
                break;
            case R.id.item_gold_two_head_iv:
                startActivity(OtherPersonalActivity.buildIntent(getActivity()
                        , mGoldRankingModel.getBody().get(1).getUser_id()));
                break;
            case R.id.item_gold_three_head_iv:
                startActivity(OtherPersonalActivity.buildIntent(getActivity()
                        , mGoldRankingModel.getBody().get(2).getUser_id()));
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position, int from) {
        switch (from) {
            case 1:
                if (fancyCoverFlow.getSelectedItemPosition() == position) {
                    startActivity(OtherPersonalActivity.buildIntent(getActivity()
                            , topAdapter.getItem(fancyCoverFlow.getSelectedItemPosition()
                                    % mRecommendUserModel.getBody().size()).getUser_id()));
                } else {
                    fancyCoverFlow.setSelection(position, true);
                }
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(OtherPersonalActivity.buildIntent(getActivity()
                , adapter.getItem(position).getUser_id()));
    }
}
