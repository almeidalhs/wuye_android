package com.atman.wysq.ui.discover;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.BaseFragmentAdapter;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.base.baselibs.widget.NoSwipeViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tangbingliang on 16/12/5.
 */

public class AllRankingListActivity extends MyBaseActivity {

    @Bind(R.id.ranking_tab_week_rt)
    RadioButton rankingTabWeekRt;
    @Bind(R.id.ranking_tab_month_rt)
    RadioButton rankingTabMonthRt;
    @Bind(R.id.ranking_tab_total_rt)
    RadioButton rankingTabTotalRt;
    @Bind(R.id.ranking_top_tab_rg)
    RadioGroup rankingTopTabRg;
    @Bind(R.id.ranking_viewpager)
    NoSwipeViewPager rankingViewpager;
    @Bind(R.id.part_ranking_left_iv)
    ImageView partRankingLeftIv;
    @Bind(R.id.part_ranking_right_iv)
    ImageView partRankingRightIv;
    @Bind(R.id.part_ranking_left_tv)
    TextView partRankingLeftTv;
    @Bind(R.id.part_ranking_right_tv)
    TextView partRankingRightTv;

    private Context context = AllRankingListActivity.this;
    private String[] typeId = {"1", "2", ""}; //0：周排行 2:月排行 3:总排行
    private int[] modelId = {88, 99}; //88：魅力 99:财富
    private int selectModelId = modelId[0];
    private final String[] TAG = {"week", "month", "total"};

    private BaseFragmentAdapter adapter;
    private Fragment fg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_allrankinglist);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        hideTitleBar();

        initViewpager();
        initTopBar();
    }

    private void initViewpager() {
        rankingViewpager.setPagingEnabled(true);//是否支持手势滑动
        if (adapter!=null) {
            adapter.clearData();
        }
        adapter = new BaseFragmentAdapter(getSupportFragmentManager());

        for (int i = 0; i < 3; i++) {
            RankingListFragment oneFragment = new RankingListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("modelId", selectModelId);
            bundle.putString("typeId", typeId[i]);
            oneFragment.setArguments(bundle);
            adapter.addFragment(oneFragment, TAG[i]);
        }

        rankingViewpager.setOffscreenPageLimit(3);
        rankingViewpager.setAdapter(adapter);
        rankingViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    rankingTabWeekRt.performClick();
                } else if (position == 1) {
                    rankingTabMonthRt.performClick();
                } else if (position == 2) {
                    rankingTabTotalRt.performClick();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        selectItem(0);
    }

    private void initTopBar() {
        rankingTopTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.ranking_tab_week_rt:
                        selectItem(0);
                        break;
                    case R.id.ranking_tab_month_rt:
                        selectItem(1);
                        break;
                    case R.id.ranking_tab_total_rt:
                        selectItem(2);
                        break;
                }
            }
        });
    }

    private void selectItem(int i) {
        rankingViewpager.setCurrentItem(i, true);
        fg = adapter.getItem(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.part_ranking_left_rl, R.id.part_ranking_right_rl, R.id.bar_back_ll})
    public void onClick(View view) {
//        selectItem(0);
        switch (view.getId()) {
            case R.id.part_ranking_left_rl:
                partRankingLeftIv.setVisibility(View.VISIBLE);
                partRankingRightIv.setVisibility(View.INVISIBLE);
                partRankingLeftTv.setTextColor(getResources().getColor(R.color.color_feb44a));
                partRankingRightTv.setTextColor(getResources().getColor(R.color.color_787878));

                selectModelId = modelId[0];
                for (int i = 0; i < 3; i++) {
                    ((RankingListFragment)adapter.getItem(i)).reGet(selectModelId);
                }
                break;
            case R.id.part_ranking_right_rl:
                partRankingLeftIv.setVisibility(View.INVISIBLE);
                partRankingRightIv.setVisibility(View.VISIBLE);
                partRankingLeftTv.setTextColor(getResources().getColor(R.color.color_787878));
                partRankingRightTv.setTextColor(getResources().getColor(R.color.color_feb44a));

                selectModelId = modelId[1];
                for (int i = 0; i < 3; i++) {
                    ((RankingListFragment)adapter.getItem(i)).reGet(selectModelId);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.bar_back_ll:
                finish();
                break;
        }
    }
}
