package com.atman.wysq.ui.mall;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.MallCategoryListAdapter;
import com.atman.wysq.adapter.RecommendForYouAdapter;
import com.atman.wysq.model.response.MallGetCategoryResponseModel;
import com.atman.wysq.model.response.MallGetTwoCategoryResponseModel;
import com.atman.wysq.model.response.MallModel;
import com.atman.wysq.model.response.MallTopResponseModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.ui.login.LoginActivity;
import com.atman.wysq.ui.mall.order.MyOrderListActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.UiHelper;
import com.atman.wysq.widget.NoScrollGridView;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.adview.ADInfo;
import com.base.baselibs.widget.adview.CycleViewPager;
import com.base.baselibs.widget.adview.ViewFactory;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述 商城
 * 作者 tangbingliang
 * 时间 16/7/1 18:09
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MallFragment extends MyBaseFragment implements View.OnClickListener
        , MallCategoryListAdapter.CategoryAdapterInterface {

    @Bind(R.id.fragment_bar_title_iv)
    ImageView fragmentBarTitleIv;
    LinearLayout mallTopAdLl;
    @Bind(R.id.mall_category_listview)
    ListView mallCategoryListview;
    @Bind(R.id.fragment_bar_right_tx)
    TextView fragmentBarRightTx;

    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;

    private String[] imageUrls;
    private MallCategoryListAdapter mAdapter;

    private View categoryHeadview;
    private View categoryBottomview;
    private LinearLayout partRecomForYouTopLl;
    private NoScrollGridView partRecomforyouGv;
    private RecommendForYouAdapter mRecommendForYouAdapter;

    private MallModel mMallModel;

    private boolean isError = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mall, null);
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
        fragmentBarTitleIv.setImageResource(R.mipmap.top_mall_ic);

        categoryHeadview = LayoutInflater.from(getActivity()).inflate(R.layout.part_category_head_view, null);
        mallTopAdLl = (LinearLayout) categoryHeadview.findViewById(R.id.mall_top_ad_ll);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getmWidth(),
                getmWidth() * 168 / 413);
        mallTopAdLl.setLayoutParams(params);

        categoryBottomview = LayoutInflater.from(getActivity()).inflate(R.layout.part_recommendforyou_boot_view, null);
        partRecomForYouTopLl = (LinearLayout) categoryBottomview.findViewById(R.id.part_recomforyou_top_ll);
        partRecomForYouTopLl.setOnClickListener(this);
        partRecomforyouGv = (NoScrollGridView) categoryBottomview.findViewById(R.id.part_recomforyou_gv);
        mRecommendForYouAdapter = new RecommendForYouAdapter(getActivity(), getmWidth());
        partRecomforyouGv.setAdapter(mRecommendForYouAdapter);
        partRecomforyouGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(GoodsDetailActivity.buildIntent(getActivity()
                        , mRecommendForYouAdapter.getItem(position).getGoods_id()));
            }
        });

        mallCategoryListview.addHeaderView(categoryHeadview);
        mallCategoryListview.addFooterView(categoryBottomview);
        fragmentBarRightTx.setVisibility(View.INVISIBLE);
        fragmentBarRightTx.setText("订单");
        fragmentBarRightTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    //需要登陆状态，跳转到登陆界面
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MyOrderListActivity.class));
                }
            }
        });

    }

    private void initialize() {
        imageUrls = new String[mMallModel.getBody().getAdListOne().size()];
        for (int i = 0; i < mMallModel.getBody().getAdListOne().size(); i++) {
            imageUrls[i] = Common.ImageUrl + mMallModel.getBody().getAdListOne().get(i)
                    .getAd_pic().replace("<wysqimg=","").replace("=wysqimg>","");
        }

        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);

        infos.clear();
        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }

        views.clear();
        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(getActivity(), infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            ImageView img = ViewFactory.getImageView(getActivity(), infos.get(i).getUrl());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views.add(img);
        }
        // 将第一个ImageView添加进来
        ImageView imageView = ViewFactory.getImageView(getActivity(), infos.get(0).getUrl());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        views.add(imageView);

        // 设置循环，在调用setData方法前调用
        if (infos.size()<=1) {
            cycleViewPager.setCycle(false);
            cycleViewPager.setScrollable(false);
            cycleViewPager.setWheel(false);
        } else {
            cycleViewPager.setCycle(true);
            cycleViewPager.setScrollable(true);
            cycleViewPager.setWheel(true);
            cycleViewPager.setTime(5000);
        }
        cycleViewPager.setData(views, infos, mAdCycleViewListener);

        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorRight();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
            }
            if (position<0) {
                position = 0;
            }
            UiHelper.toActivity(getActivity(), mMallModel.getBody().getAdListOne().get(position), isLogin());
        }

    };

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
        if (isVisibleToUser && getActivity() != null && isError) {
            isError = false;

            OkHttpUtils.get().url(Common.Url_Get_Mall)
                    .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                    .tag(Common.NET_GET_MALL_ID).id(Common.NET_GET_MALL_ID).build()
                    .execute(new MyStringCallback(getActivity(), this, true));
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_MALL_ID) {
            mMallModel = mGson.fromJson(data, MallModel.class);

            initialize();
            initListView();
            mRecommendForYouAdapter.addBody(mMallModel.getBody().getGoodsList());
        }
    }

    private void initListView() {
        mAdapter = new MallCategoryListAdapter(getActivity(), getmWidth(), mMallModel.getBody().getCategoryList(), this);
        mallCategoryListview.setAdapter(mAdapter);
        mallCategoryListview.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        isError = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_MALL_ID);
    }

    @Override
    public void onItemClick(View view, int id, String name) {
        startActivity(TwoLevelCategoryListActivity.buildIntent(getActivity(), id, name, false));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.part_recomforyou_top_ll:
                break;
        }
    }
}
