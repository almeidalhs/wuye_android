package com.atman.wysq.ui.discover;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.atman.wysq.R;
import com.atman.wysq.adapter.EditScenePicAdapter;
import com.atman.wysq.model.request.ScenePicList;
import com.atman.wysq.model.response.GetSceneInfoModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.MyCleanEditText;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/3/22.
 */

public class EditSceneActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.editscene_startword_et)
    MyCleanEditText editsceneStartwordEt;
    @Bind(R.id.editscene_bg_rv)
    RecyclerView editsceneBgRv;

    private Context mContext = EditSceneActivity.this;

    private EditScenePicAdapter mAdapter;
    private GetSceneInfoModel mGetSceneInfoModel;
    private List<ScenePicList> mScenePicList = new ArrayList<>();

    private LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editscene);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("编辑魔聊场景");
        setBarRightIv(R.mipmap.bt_create_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("创建");
            }
        });

        initListView();
    }

    private void initListView() {
        int w = getmWidth() - DensityUtil.dp2px(mContext, 50);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, w/5);

        editsceneBgRv.setLayoutParams(params);

        mAdapter = new EditScenePicAdapter(mContext, w/5, this);
        //设置间距
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_dp_5);
        editsceneBgRv.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        editsceneBgRv.setLayoutManager(new GridLayoutManager(mContext, 5));
        editsceneBgRv.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        LogUtils.e(">>>:"+MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId());
        OkHttpUtils.get().url(Common.Url_Get_SceneInfo + MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_SCENEINFO_ID).id(Common.NET_GET_SCENEINFO_ID).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_SCENEINFO_ID) {
            mGetSceneInfoModel = mGson.fromJson(data, GetSceneInfoModel.class);

            if (mGetSceneInfoModel.getBody().size()>0) {
                editsceneStartwordEt.setText(mGetSceneInfoModel.getBody().get(0).getPrologue());
                if (!mGetSceneInfoModel.getBody().get(0).getPic_url1().isEmpty()) {
                    ScenePicList temp = new ScenePicList(mGetSceneInfoModel.getBody().get(0).getPic_url1());
                    mScenePicList.add(temp);
                }
                if (!mGetSceneInfoModel.getBody().get(0).getPic_url2().isEmpty()) {
                    ScenePicList temp = new ScenePicList(mGetSceneInfoModel.getBody().get(0).getPic_url2());
                    mScenePicList.add(temp);
                }
                if (!mGetSceneInfoModel.getBody().get(0).getPic_url3().isEmpty()) {
                    ScenePicList temp = new ScenePicList(mGetSceneInfoModel.getBody().get(0).getPic_url3());
                    mScenePicList.add(temp);
                }
                if (!mGetSceneInfoModel.getBody().get(0).getPic_url4().isEmpty()) {
                    ScenePicList temp = new ScenePicList(mGetSceneInfoModel.getBody().get(0).getPic_url4());
                    mScenePicList.add(temp);
                }
                if (!mGetSceneInfoModel.getBody().get(0).getPic_url5().isEmpty()) {
                    ScenePicList temp = new ScenePicList(mGetSceneInfoModel.getBody().get(0).getPic_url5());
                    mScenePicList.add(temp);
                }
                if (mScenePicList.size()<5) {
                    ScenePicList temp = new ScenePicList("-1");
                    mScenePicList.add(temp);
                }
                mAdapter.addData(mScenePicList);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_SCENEINFO_ID);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if(parent.getChildPosition(view) != 0)
                outRect.left = space;
        }
    }
}
