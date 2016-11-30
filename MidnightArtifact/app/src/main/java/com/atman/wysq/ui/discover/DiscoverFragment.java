package com.atman.wysq.ui.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述 发现
 * 作者 tangbingliang
 * 时间 16/7/1 18:12
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class DiscoverFragment extends MyBaseFragment {

    @Bind(R.id.fragment_bar_title_iv)
    ImageView fragmentBarTitleIv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, null);
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
        fragmentBarTitleIv.setImageResource(R.mipmap.top_discover_ic);

        initTop3D();
    }

    private void initTop3D() {
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
}
