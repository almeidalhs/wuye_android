package com.atman.wysq.ui.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.DiscoverNewGridViewAdapter;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.widget.MyGridView;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/3/20.
 */

public class DiscoverNewChildFragment extends MyBaseFragment implements AdapterInterface {

    @Bind(R.id.black_empty_tx)
    TextView blackEmptyTx;
    @Bind(R.id.discoverchild_gridview)
    MyGridView discoverchildGridview;

    private boolean isError = true;

    private DiscoverNewGridViewAdapter mAdapter;
    private String title;
    private String categoryId;
    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discovernewchild, null);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        title = b.getString("TITLES");
        categoryId = b.getString("typeId");
        page = 0;
        return view;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        initListView();
    }

    private void initListView() {
        mAdapter = new DiscoverNewGridViewAdapter(getActivity(), getmWidth(), title, this);

        discoverchildGridview.setAdapter(mAdapter);
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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
