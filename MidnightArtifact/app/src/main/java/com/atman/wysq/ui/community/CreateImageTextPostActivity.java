package com.atman.wysq.ui.community;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.CreateImageTextPostAdapter;
import com.atman.wysq.model.request.AddPostContentModel;
import com.atman.wysq.model.request.CreateImageViewPostModel;
import com.atman.wysq.model.response.GoodsListModel;
import com.atman.wysq.model.response.HeadImgResultModel;
import com.atman.wysq.model.response.HeadImgSuccessModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.Tools;
import com.atman.wysq.utils.UiHelper;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.PromptDialog;
import com.choicepicture_library.ImageGridActivity;
import com.choicepicture_library.tools.Bimp;
import com.tbl.okhttputils.OkHttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/4/1.
 */

public class CreateImageTextPostActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.create_imagetext_exlistview)
    ExpandableListView createImagetextExlistview;

    private Context mContext = CreateImageTextPostActivity.this;
    private TextView topRightTv;
    private View headView;
    private MyCleanEditText postTitleEt;
    private EditText mFocusEditText;

    private CreateImageTextPostAdapter mAdapter;

    private String path = "";
    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;
    private int mPosition = 0;
    private int mAnonymity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_imagetextpost);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("创建帖子");
        topRightTv = setBarRightTx("提交");
        topRightTv.setTextColor(getResources().getColor(R.color.color_red));
        topRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postTitleEt.getText().toString().trim().isEmpty()) {
                    showToast("请输入标题");
                    return;
                }
                int picNum = 0;
                int contentNum = 0;
                for(int i=0;i<mAdapter.getContentList().size();i++){
                    if (mAdapter.getContentList().get(i).getLocalUrl().isEmpty()){
                        picNum++;
                    }
                    if (mAdapter.getContentList().get(i).getContent().trim().isEmpty()) {
                        contentNum++;
                    }
                }
                if (picNum==mAdapter.getContentList().size()
                        && contentNum==mAdapter.getContentList().size() ) {
                    showToast("帖子内容不能为空");
                    return;
                }
                showWarn();
            }
        });

        getBarBackLl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDonot();
            }
        });
        getBarBackIv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDonot();
            }
        });

        initListview();
    }

    private void initListview() {

        headView = LayoutInflater.from(mContext).inflate(R.layout.part_post_head_view, null);
        postTitleEt = (MyCleanEditText) headView.findViewById(R.id.post_title_et);
        postTitleEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditFocus(postTitleEt, 0);
            }
        });
        postTitleEt.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setEditFocus(postTitleEt, 0);
                }
            }
        });

        mAdapter = new CreateImageTextPostAdapter(mContext, this);
        createImagetextExlistview.addHeaderView(headView);
        createImagetextExlistview.setAdapter(mAdapter);

        for(int i = 0; i < mAdapter.getGroupCount(); i++){
            createImagetextExlistview.expandGroup(i);
        }

        createImagetextExlistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // TODO Auto-generated method stub
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getIntExtra("Goods_id", -1)!=-1 && mAdapter!=null && MyBaseApplication.isRelation) {
            GoodsListModel temp = new GoodsListModel(getIntent().getIntExtra("Goods_id", -1)
                    , getIntent().getStringExtra("Pic_img"), getIntent().getStringExtra("Title")
                    , getIntent().getStringExtra("Discount_price"));
            MyBaseApplication.isRelation = false;
            mAdapter.clearAll();
            mAdapter.addGoods(MyBaseApplication.creatPostGoods);
            mAdapter.addContent(MyBaseApplication.creatPostContents);
            boolean isRelationed = false;
            for (int i=0;i<MyBaseApplication.creatPostGoods.size();i++) {
                if (MyBaseApplication.creatPostGoods.get(i).getGoods_id()==temp.getGoods_id()) {
                    isRelationed = true;
                    showToast("此商品已关联");
                    break;
                }
            }
            if (!isRelationed) {
                mAdapter.addGoods(temp);
            }
            postTitleEt.setText(MyBaseApplication.imagetextPostTitle);
            getIntent().removeExtra("Goods_id");
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if(id == Common.NET_RESET_HEAD){
            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            if (mHeadImgResultModel!=null && mHeadImgResultModel.getFiles().size()>0 ) {
                if (!mHeadImgResultModel.getFiles().get(0).isSuccessful()) {
                    showToast("上传失败");
                    MyBaseApplication.getApplication().setFilterLock(false);
                } else {
                    HeadImgSuccessModel mHeadImgSuccessModel = mGson.fromJson(data, HeadImgSuccessModel.class);
                    LogUtils.e("inUrl:"+inUrl+",setNetUrl:"+mHeadImgSuccessModel.getFiles().get(0).getUrl());
                    mAdapter.getContentList().get(inUrl).setNetUrl(mHeadImgSuccessModel.getFiles().get(0).getUrl());
                    inUrl += 1;
                    upLoadPic();
                }
            }
        } else if (id==Common.NET_CREATE_IAMGETEXT_POST_ID) {
            showToast("发帖成功");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        clear();
        super.onDestroy();

        OkHttpUtils.getInstance().cancelTag(Common.NET_RESET_HEAD);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CREATE_IAMGETEXT_POST_ID);
    }

    private void clear() {
        Bimp.num = 0;
        Bimp.drr.clear();
        Bimp.drr_or.clear();
        Bimp.bmp.clear();
    }

    private void setEditFocus(EditText postTitleEt, int i) {
        mFocusEditText = postTitleEt;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 返回键
            showDonot();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showDonot() {
        PromptDialog.Builder builder = new PromptDialog.Builder(this);
        builder.setMessage("确定取消发布？");
        builder.setPositiveButton("不发了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("继续编辑", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onItemClick(View view, int position) {
        mPosition = position;
        switch (view.getId()) {
            case R.id.item_childone_add_ll://添加
                AddPostContentModel temp = new AddPostContentModel("", "", "", null);
                mAdapter.addContent(temp);
                break;
            case R.id.item_childone_delete_iv://删除
                mAdapter.deleteContent(position);
                break;
            case R.id.item_childone_photograph_iv://拍照
                MyBaseApplication.getApplication().setFilterLock(true);
                path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
                break;
            case R.id.item_childone_album_iv://选择照片
                Bimp.max = 1;
                clear();
                startActivityForResult(new Intent(mContext, ImageGridActivity.class), CHOOSE_BIG_PICTURE);
                break;
            case R.id.item_childone_head_iv:
                showHeadImg();
                break;
            case R.id.item_childtwo_delete_iv:
                mAdapter.deleteGoods(position);
                break;
            case R.id.item_childtwo_add_ll:
                MyBaseApplication.creatPostContents.clear();
                MyBaseApplication.creatPostContents.addAll(mAdapter.getContentList());
                MyBaseApplication.creatPostGoods.clear();
                MyBaseApplication.creatPostGoods.addAll(mAdapter.getGoodsList());
                mAdapter.clearAll();
                MyBaseApplication.isRelation = true;
                MyBaseApplication.imagetextPostTitle = postTitleEt.getText().toString();
                startActivityForResult(new Intent(mContext, MallActivity.class)
                        , Common.fromCreateImageText);
                break;
        }
    }

    private void showHeadImg() {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setTitle(Html.fromHtml("<font color=\"#f9464a\">修改图片</font>"));
        builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MyBaseApplication.getApplication().setFilterLock(true);
                if (which == 0) {//拍照
                    path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
                } else {//选择照片
                    Bimp.max = 1;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyBaseApplication.getApplication().setFilterLock(false);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
            for (int i=0;i<Bimp.drr.size();i++) {
                mAdapter.setLocalUrl(mPosition, Bimp.drr.get(i));
            }
        } else if (requestCode == TAKE_BIG_PICTURE) {
            if (!path.isEmpty()) {
                mAdapter.setLocalUrl(mPosition, path);
            }
        }
    }

    private void showWarn() {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setItems(new String[]{"发布", "匿名发布"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (which == 0) {//发布
                    mAnonymity = 0;
                } else {//匿名发布
                    mAnonymity = 1;
                }
                boolean isGif = false;
                int num = 0;
                for (int i=0;i<mAdapter.getContentList().size();i++) {
                    String str = mAdapter.getContentList().get(i).getLocalUrl();
                    if ((str.substring(str.lastIndexOf(".")+1)).contains("gif")) {
                        isGif = true;
                        num = i;
                        if (mAdapter.getContentList().get(i).getLocalUrl().isEmpty()) {
                            num -= 1;
                        }
                        break;
                    }
                }
                if (!isGif) {
                    inUrl = 0;
                    upLoadPic();
                } else {
                    showToast("第"+(num+1)+"张是gif图片，不可选用");
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

    private int inUrl = 0;
    private void upLoadPic () {
        if (inUrl >= mAdapter.getContentList().size()) {
            String str = "";
            for (int i=0;i<mAdapter.getContentList().size();i++) {
                if (!mAdapter.getContentList().get(i).getNetUrl().isEmpty()) {
                    str += "<wysqimg="+mAdapter.getContentList().get(i).getNetUrl()+"=wysqimg>"
                            +mAdapter.getContentList().get(i).getContent();
                } else {
                    str += mAdapter.getContentList().get(i).getContent();
                }
            }
            List<Map<String, Integer>> list = new ArrayList<>();
            for (int i=0;i<mAdapter.getGoodsList().size();i++) {
                Map<String, Integer> map = new HashMap<>();
                map.put("goods_id", mAdapter.getGoodsList().get(i).getGoods_id());
                list.add(map);
            }
            CreateImageViewPostModel addPostModel = new CreateImageViewPostModel(
                    str, postTitleEt.getText().toString().trim(), mAnonymity, list);
            LogUtils.e("mGson.toJson(addPostModel):"+mGson.toJson(addPostModel));
            OkHttpUtils.postString().url(Common.Url_Create_ImageText_Post)
                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .mediaType(Common.JSON).content(mGson.toJson(addPostModel))
                    .id(Common.NET_CREATE_IAMGETEXT_POST_ID).tag(Common.NET_CREATE_IAMGETEXT_POST_ID)
                    .build().execute(new MyStringCallback(mContext, this, true));
            return;
        }
        String data = mAdapter.getContentList().get(inUrl).getLocalUrl();
        if (data.isEmpty()) {
            inUrl += 1;
            upLoadPic();
            return;
        } else {
            Uri imageUri = Uri.parse("file:///" + data);
            try {
                String path = Tools.revitionImage(imageUri.getPath());
                OkHttpUtils.post().url(Common.Url_Reset_Head)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .addParams("uploadType", "img")
                        .addFile("files0_name", StringUtils.getFileName(path),
                                new File(path)).id(Common.NET_RESET_HEAD)
                        .tag(Common.NET_RESET_HEAD).build().readTimeOut(Common.timeOutTwo).writeTimeOut(Common.timeOutTwo)
                        .execute(new MyStringCallback(mContext, this, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
