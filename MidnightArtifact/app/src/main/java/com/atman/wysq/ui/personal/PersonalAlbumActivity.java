package com.atman.wysq.ui.personal;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.PersonalAlbumAdapter;
import com.atman.wysq.model.response.AddAblumModel;
import com.atman.wysq.model.response.GetMyUserIndexModel;
import com.atman.wysq.model.response.HeadImgResultModel;
import com.atman.wysq.model.response.HeadImgSuccessModel;
import com.atman.wysq.ui.PictureBrowsingActivity;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.SpaceItemDecorationAblumGrivView;
import com.atman.wysq.utils.UiHelper;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.BottomDialog;
import com.choicepicture_library.ImageGridActivity;
import com.choicepicture_library.tools.Bimp;
import com.tbl.okhttputils.OkHttpUtils;
import com.tbl.okhttputils.builder.PostFormBuilder;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/4/18.
 */

public class PersonalAlbumActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.personal_album_recycler)
    RecyclerView personalAlbumRecycler;
    @Bind(R.id.personal_album_ll)
    LinearLayout personalAlbumLl;

    private PersonalAlbumAdapter mAdapter;
    private List<GetMyUserIndexModel.BodyBean.UserDetailBeanBean.PhotoListBean> data;

    private TextView mTextView;

    private Context mContext = PersonalAlbumActivity.this;

    private String path = "";
    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalalbum);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, List<GetMyUserIndexModel.BodyBean.UserDetailBeanBean
            .PhotoListBean> data) {
        Intent intent = new Intent(context, PersonalAlbumActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("data", (Serializable) data);
        intent.putExtras(b);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("相册管理");

        data = (List<GetMyUserIndexModel.BodyBean.UserDetailBeanBean.PhotoListBean>) getIntent()
                .getSerializableExtra("data");

        initRecyclerView();

        if (data != null) {
            mAdapter.addData(data);
        }

        mTextView = setBarRightTx("选择");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.size() == 0) {
                    return;
                }
                if (mTextView.getText().toString().contains("取消")) {
                    mTextView.setText("选择");
                    personalAlbumLl.setVisibility(View.GONE);
                    mAdapter.setSelectStats(false);
                } else {
                    mTextView.setText("取消\n选择");
                    personalAlbumLl.setVisibility(View.VISIBLE);
                    mAdapter.setSelectStats(true);
                }
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new PersonalAlbumAdapter(mContext, getmWidth(), this);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_dp_5);
        personalAlbumRecycler.addItemDecoration(new SpaceItemDecorationAblumGrivView(spacingInPixels));
        personalAlbumRecycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        personalAlbumRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_DELETE_ABLUM_ID) {
            for (int i=0;i<mMapList.size();i++) {
                mAdapter.deleteDataById((Integer)mMapList.get(i).get("photo_id"));
            }
        } else if (id == Common.NET_RESET_HEAD) {
            showToast("上传成功");
            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            if (mHeadImgResultModel!=null && mHeadImgResultModel.getFiles().size()>0 ) {
                if (!mHeadImgResultModel.getFiles().get(0).isSuccessful()) {
                    showToast("上传失败");
                    MyBaseApplication.getApplication().setFilterLock(false);
                } else {
                    HeadImgSuccessModel mHeadImgSuccessModel = mGson.fromJson(data, HeadImgSuccessModel.class);
                    List<String> list = new ArrayList<>();
                    for (int i=0;i<mHeadImgSuccessModel.getFiles().size();i++) {
                        list.add(mHeadImgSuccessModel.getFiles().get(i).getUrl());
                    }
                    OkHttpUtils.postString().url(Common.Url_Add_Ablum_Post)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .mediaType(Common.JSON).content(mGson.toJson(list))
                            .id(Common.NET_ADD_ABLUM_ID).tag(Common.NET_ADD_ABLUM_ID)
                            .build().execute(new MyStringCallback(mContext, this, true));
                }
            }
        } else if (id == Common.NET_ADD_ABLUM_ID) {
            AddAblumModel mAddAblumModel = mGson.fromJson(data, AddAblumModel.class);
            for (int i=0;i<mAddAblumModel.getBody().size();i++) {
                GetMyUserIndexModel.BodyBean.UserDetailBeanBean.PhotoListBean temp =
                        new GetMyUserIndexModel.BodyBean.UserDetailBeanBean.PhotoListBean();
                temp.setPic_url(mAddAblumModel.getBody().get(i).getPic_url());
                temp.setPhoto_id(mAddAblumModel.getBody().get(i).getPhoto_id());
                mAdapter.addData(temp);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_DELETE_ABLUM_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_RESET_HEAD);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_ABLUM_ID);
    }

    private void changeImg() {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setItems(new String[]{"拍照", "相册选取"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MyBaseApplication.getApplication().setFilterLock(true);
                if (which == 0) {//拍照
                    path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
                } else {//选择照片
                    Bimp.max = 5 - mAdapter.getListData().size();
                    clear();
                    startActivityForResult(new Intent(mContext, ImageGridActivity.class), CHOOSE_BIG_PICTURE);
                }
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void clear() {
        Bimp.num = 0;
        Bimp.drr.clear();
        Bimp.drr_or.clear();
        Bimp.bmp.clear();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyBaseApplication.getApplication().setFilterLock(false);
        if (resultCode != Activity.RESULT_OK && (data == null || data.getData() == null)) {
            return;
        }

        if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
            pathList = new String[Bimp.drr.size()];
            for (int i = 0; i < Bimp.drr.size(); i++) {
                pathList[i] = Bimp.drr.get(i);
            }
        } else if (requestCode == TAKE_BIG_PICTURE) {
            pathList = new String[1];
            pathList[0] = path;

        }
        displayImg(pathList);
    }

    private String [] pathList;
    private void displayImg(String[] path) {
        PostFormBuilder mPostFormBuilder = OkHttpUtils.post().url(Common.Url_Reset_Head)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .addParams("uploadType", "img").id(Common.NET_RESET_HEAD).tag(Common.NET_RESET_HEAD);
        for (int i=0;i<path.length;i++) {
            mPostFormBuilder.addFile("files"+i+"_name", StringUtils.getFileName(path[i]), new File(path[i]));
        }
        mPostFormBuilder.build().readTimeOut(Common.timeOutTwo).writeTimeOut(Common.timeOutTwo)
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mAdapter.isSelectStats()) {
            mAdapter.setSelect(position);
        } else {
            if (position == mAdapter.getListData().size()) {
                changeImg();
            } else {
                String imagePath = "";
                for (int i = 0; i < mAdapter.getListData().size(); i++) {
                    if (i != 0) {
                        imagePath += ",";
                    }
                    imagePath += (Common.ImageUrl + mAdapter.getListData().get(i).getPic_url());
                }

                Intent intent = new Intent();
                intent.putExtra("image", imagePath);
                intent.putExtra("num", position);
                intent.setClass(mContext, PictureBrowsingActivity.class);
                startActivity(intent);
            }
        }
    }

    private List<Map<String, Integer>> mMapList;
    @OnClick({R.id.personal_album_delete_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personal_album_delete_tv:
                Map<String, Object> mMap = new HashMap();
                mMapList = new ArrayList<>();
                for (int i=0;i<mAdapter.getSelectListData().size();i++) {
                    Map<String, Integer> map = new HashMap();
                    map.put("photo_id", (int) mAdapter.getSelectListData().get(i).getPhoto_id());
                    mMapList.add(map);
                }
                mMap.put("photoIds", mMapList);
                OkHttpUtils.postString().url(Common.Url_Delete_Ablum_Post)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .mediaType(Common.JSON).content(mGson.toJson(mMap))
                        .id(Common.NET_DELETE_ABLUM_ID).tag(Common.NET_DELETE_ABLUM_ID)
                        .build().execute(new MyStringCallback(mContext, this, true));
                break;
        }
    }
}
