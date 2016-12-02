package com.atman.wysq.ui.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.ViewGroupExampleAdapter;
import com.atman.wysq.model.response.RecommendUserModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.ui.yunxinfriend.OtherPersonalActivity;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.tbl.okhttputils.OkHttpUtils;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

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
    @Bind(R.id.fancyCoverFlow)
    FancyCoverFlow fancyCoverFlow;

    private boolean isError = true;
    private RecommendUserModel mRecommendUserModel;
    private ViewGroupExampleAdapter adapter;

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
    }

    private void initTop3D() {
        fancyCoverFlow.setFadingEdgeLength(0);
        adapter = new ViewGroupExampleAdapter(getActivity(), mRecommendUserModel);
        fancyCoverFlow.setAdapter(adapter);
        fancyCoverFlow.setSelection(adapter.getCount()/2);
        fancyCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.e("fancyCoverFlow.getSelectedItemPosition():"+fancyCoverFlow.getSelectedItemPosition());
                LogUtils.e("position:"+position);
                if ((fancyCoverFlow.getSelectedItemPosition()+1) == position) {
                    startActivity(OtherPersonalActivity.buildIntent(getActivity()
                            , adapter.getItem(fancyCoverFlow.getSelectedItemPosition()
                                    % mRecommendUserModel.getBody().size()).getUser_id()));
                }
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
                OkHttpUtils.get().url(Common.Url_Get_RecommendFriends)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_GET_RECOMMENDFRIENDS).id(Common.NET_GET_RECOMMENDFRIENDS).build()
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
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_RECOMMENDFRIENDS);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
