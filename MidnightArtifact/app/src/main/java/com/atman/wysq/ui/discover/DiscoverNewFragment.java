package com.atman.wysq.ui.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.TabAdapter;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.base.baselibs.widget.MyTwoViewPager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

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

public class DiscoverNewFragment extends MyBaseFragment {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.info_viewpager)
    MyTwoViewPager infoViewpager;
    @Bind(R.id.pullToRefreshScrollView)
    PullToRefreshScrollView pullToRefreshScrollView;
    @Bind(R.id.part_newdiscover_topleft_iv)
    ImageView partNewdiscoverTopleftIv;
    @Bind(R.id.part_newdiscover_find_tv)
    ImageView partNewdiscoverFindTv;
    @Bind(R.id.part_newdiscover_topright_iv)
    ImageView partNewdiscoverToprightIv;

    private boolean isError = true;
    private TabAdapter fragPagerAdapter;
    private String[] topStr = {"推荐", "人气", "新人", "男神"};
    private String[] topStrId = {"0", "1", "2", "3"};

    private int mPage = 1;

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

        initScrollView();
        initCategoryView();
    }

    private void initScrollView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshScrollView);
    }

    private List<Fragment> fragments;

    private void initCategoryView() {
        fragments = new ArrayList<>();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < topStr.length; i++) {
            list.add(topStr[i]);
            DiscoverNewChildFragment oneFragment = new DiscoverNewChildFragment();
            Bundle bundle = new Bundle();
            bundle.putString("TITLES", topStr[i]);
            bundle.putString("typeId", topStrId[i]);
            oneFragment.setArguments(bundle);
            fragments.add(oneFragment);
        }
        fragPagerAdapter = new TabAdapter(getChildFragmentManager());
        //设置显示的标题
        fragPagerAdapter.setList(list);
        //设置需要进行滑动的页面Fragment
        fragPagerAdapter.setFragments(fragments);

        infoViewpager.setAdapter(fragPagerAdapter);
        infoViewpager.setOffscreenPageLimit(topStr.length);
        tabLayout.setupWithViewPager(infoViewpager);
        //设置TabLayout模式 -该使用Tab数量比较多的情况
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                infoViewpager.setCurrentItem(tabLayout.getSelectedTabPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
            }
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        mPage = 1;
//        mAdapter.clearData();
//        doHttp(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        mPage += 1;
//        doHttp(false);
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        mPage = 1;
        onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshScrollView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.part_newdiscover_topleft_iv, R.id.part_newdiscover_find_tv, R.id.part_newdiscover_topright_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.part_newdiscover_topleft_iv:
                break;
            case R.id.part_newdiscover_topright_iv:
                startActivity(new Intent(getActivity(), AllRankingListActivity.class));
                break;
        }
    }
}
