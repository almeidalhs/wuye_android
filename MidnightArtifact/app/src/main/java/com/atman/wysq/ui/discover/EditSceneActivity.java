package com.atman.wysq.ui.discover;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.atman.wysq.R;
import com.atman.wysq.adapter.EditScenePicAdapter;
import com.atman.wysq.model.request.ScenePicList;
import com.atman.wysq.model.response.GetSceneInfoModel;
import com.atman.wysq.model.response.HeadImgResultModel;
import com.atman.wysq.model.response.HeadImgSuccessModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.ContentUriUtil;
import com.atman.wysq.utils.SpaceItemDecoration;
import com.atman.wysq.utils.Tools;
import com.atman.wysq.utils.UiHelper;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.MyCleanEditText;
import com.tbl.okhttputils.OkHttpUtils;
import com.tbl.okhttputils.builder.PostFormBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
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

    private boolean isHead = false;
    private String mHeadImgUrl;

    private String mPrologue;

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
                mPrologue = editsceneStartwordEt.getText().toString().trim();
                if (mPrologue.isEmpty()) {
                    showToast("请输入开场白");
                    return;
                }
                if (mAdapter.getNotUp().size()==0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("prologue", mPrologue);
                    for (int i=0;i<mAdapter.getUped().size();i++) {
                        map.put("pic_url"+(i+1), mAdapter.getUped().get(i).getUrl());
                    }
                    upEditData(map, mAdapter.getUped().size());
                    return;
                }
                PostFormBuilder mPostFormBuilder = OkHttpUtils.post().url(Common.Url_Reset_Head).addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .addParams("uploadType", "img").id(Common.NET_RESET_HEAD).tag(Common.NET_RESET_HEAD);
                for (int i=0;i<mAdapter.getNotUp().size();i++) {
                    String str = mAdapter.getNotUp().get(i).getUrl();
                    mPostFormBuilder.addFile("files"+i+"_name", StringUtils.getFileName(str), new File(str));
                }
                mPostFormBuilder.build().connTimeOut(Common.timeOutTwo).readTimeOut(Common.timeOutTwo)
                        .writeTimeOut(Common.timeOutTwo).execute(new MyStringCallback(mContext, EditSceneActivity.this, true));
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
        if (!isHead) {
            MyBaseApplication.getApplication().setFilterLock(false);
        }
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        if (MyBaseApplication.mGetMyUserIndexModel==null) {
            return;
        }
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
                if (mGetSceneInfoModel.getBody().get(0).getPic_url1()!=null
                        && !mGetSceneInfoModel.getBody().get(0).getPic_url1().isEmpty()) {
                    ScenePicList temp = new ScenePicList(mGetSceneInfoModel.getBody().get(0).getPic_url1(), true);
                    mScenePicList.add(temp);
                }
                if (mGetSceneInfoModel.getBody().get(0).getPic_url2()!=null
                        && !mGetSceneInfoModel.getBody().get(0).getPic_url2().isEmpty()) {
                    ScenePicList temp = new ScenePicList(mGetSceneInfoModel.getBody().get(0).getPic_url2(), true);
                    mScenePicList.add(temp);
                }
                if (mGetSceneInfoModel.getBody().get(0).getPic_url3()!=null
                        && !mGetSceneInfoModel.getBody().get(0).getPic_url3().isEmpty()) {
                    ScenePicList temp = new ScenePicList(mGetSceneInfoModel.getBody().get(0).getPic_url3(), true);
                    mScenePicList.add(temp);
                }
                if (mGetSceneInfoModel.getBody().get(0).getPic_url4()!=null
                        && !mGetSceneInfoModel.getBody().get(0).getPic_url4().isEmpty()) {
                    ScenePicList temp = new ScenePicList(mGetSceneInfoModel.getBody().get(0).getPic_url4(), true);
                    mScenePicList.add(temp);
                }
                if (mGetSceneInfoModel.getBody().get(0).getPic_url5()!=null
                        && !mGetSceneInfoModel.getBody().get(0).getPic_url5().isEmpty()) {
                    ScenePicList temp = new ScenePicList(mGetSceneInfoModel.getBody().get(0).getPic_url5(), true);
                    mScenePicList.add(temp);
                }
                if (mScenePicList.size()<5) {
                    ScenePicList temp = new ScenePicList("-1", true);
                    mScenePicList.add(temp);
                }
                mAdapter.addData(mScenePicList);
            } else {
                ScenePicList temp = new ScenePicList("-1", true);
                mScenePicList.add(temp);
                mAdapter.addData(mScenePicList);
            }
        } else if (id == Common.NET_RESET_HEAD) {
            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            if (mHeadImgResultModel!=null && mHeadImgResultModel.getFiles().size()>0 ) {
                if (!mHeadImgResultModel.getFiles().get(0).isSuccessful()) {
                    showToast("图片上传失败");
                    isHead = false;
                    MyBaseApplication.getApplication().setFilterLock(false);
                } else {
                    HeadImgSuccessModel mHeadImgSuccessModel = mGson.fromJson(data, HeadImgSuccessModel.class);
                    mHeadImgUrl = mHeadImgSuccessModel.getFiles().get(0).getUrl();
                    Map<String, Object> map = new HashMap<>();
                    map.put("prologue", mPrologue);
                    int n = 1;
                    for (int i=0;i<mAdapter.getUped().size();i++) {
                        map.put("pic_url"+n, mAdapter.getUped().get(i).getUrl());
                        n++;
                    }
                    for (int i=0;i<mHeadImgSuccessModel.getFiles().size();i++) {
                        map.put("pic_url"+n, mHeadImgSuccessModel.getFiles().get(i).getUrl());
                        n++;
                    }
                    upEditData(map, n-1);
                }
            }
        } else if (id == Common.NET_ADD_CHAT_BACKGROUNG_ID) {
            isHead = false;
            MyBaseApplication.getApplication().setFilterLock(false);
            showToast("修改成功，请耐心等待审核！");
            finish();
        }
    }

    private void upEditData(Map<String, Object> map, int size) {
        if (size<5) {
            for (int i=size+1;i<=5;i++) {
                map.put("pic_url"+i, "");
            }
        }
        OkHttpUtils.postString().url(Common.Url_Add_Chat_Background).id(Common.NET_ADD_CHAT_BACKGROUNG_ID)
                .content(mGson.toJson(map))
                .mediaType(Common.JSON).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_ADD_CHAT_BACKGROUNG_ID).build().execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        isHead = false;
        MyBaseApplication.getApplication().setFilterLock(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_SCENEINFO_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_RESET_HEAD);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_CHAT_BACKGROUNG_ID);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mAdapter.getListData().size()==0 && mAdapter.getItemCount()==1) {
            showHeadImg(view);
        } else {
            if (mAdapter.getItemData(position).getUrl().equals("-1")) {
                showHeadImg(view);
            } else {
                mAdapter.selectById(position);
                showDeleteImg(view, position);
            }
        }
    }

    private Uri imageUri;
    private String path = "";
    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;

    private void showHeadImg(View view) {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setItems(new String[]{"拍照", "从相册选取"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {//拍照
                    path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
                } else {//选择照片
                    Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                    getAlbum.setType("image/*");
                    startActivityForResult(getAlbum, CHOOSE_BIG_PICTURE);
                }
                MyBaseApplication.getApplication().setFilterLock(true);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
            imageUri = data.getData();
        } else if (requestCode == TAKE_BIG_PICTURE) {
            imageUri = Uri.parse("file:///" + path);
        }
        LogUtils.e(">>>imageUri:"+imageUri);
        LogUtils.e(">>>imageUri.getPath():"+imageUri.getPath());
        LogUtils.e(">>>imageUri>>:"+ getRealPathFromUri(mContext, imageUri));
        if (imageUri != null) {
            if (!imageUri.getPath().startsWith("/storage")) {
                imageUri = Uri.parse("file:///" + ContentUriUtil.getPath(mContext, imageUri));
            } else if (imageUri.getPath().contains("/document")) {
                imageUri = Uri.parse("file:///" + ContentUriUtil.getPath(mContext, imageUri));
            } else if (imageUri.getPath().contains("external")) {
                imageUri = Uri.parse("file:///" + getRealPathFromUri(mContext, imageUri));
            }
            LogUtils.e(">>>imageUri333:"+imageUri);
            File f = new File(imageUri.getPath().replace("//","/"));
            if (!f.exists()) {
                showToast("图片不存在");
                return;
            } else {
                try {
                    imageUri = Uri.parse("file:///"+Tools.revitionImage(imageUri.getPath()));
                    ScenePicList temp = new ScenePicList(f.getPath(), false);
                    mAdapter.addData(temp);
                } catch (IOException e) {
                    LogUtils.e(">>>e:"+e.toString());
                    showToast("图片压缩失败");
                    e.printStackTrace();
                }
            }
        } else {
            showToast("图片不存在");
        }
    }

    private void showDeleteImg(View view, final int p) {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setItems(new String[]{"删除"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {//删除
                    dialog.dismiss();
                    mAdapter.deleteById(p);
                }
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mAdapter.selectById(p);
            }
        });
        builder.show();
    }

    public String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
