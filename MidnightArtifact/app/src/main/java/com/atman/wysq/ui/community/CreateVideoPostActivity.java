package com.atman.wysq.ui.community;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.CreateVoicePostAdapter;
import com.atman.wysq.model.request.CreateMultiMediaPostModel;
import com.atman.wysq.model.response.GoodsListModel;
import com.atman.wysq.model.response.HeadImgResultModel;
import com.atman.wysq.model.response.HeadImgSuccessModel;
import com.atman.wysq.model.response.UpVoiceModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.BitmapTools;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.SelectVideoUtil;
import com.atman.wysq.utils.UiHelper;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.DensityUtil;
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
import mabeijianxi.camera.LocalMediaCompress;
import mabeijianxi.camera.model.AutoVBRMode;
import mabeijianxi.camera.model.BaseMediaBitrateConfig;
import mabeijianxi.camera.model.LocalMediaConfig;
import mabeijianxi.camera.model.OnlyCompressOverBean;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/4/6.
 */

public class CreateVideoPostActivity extends MyBaseActivity implements View.OnClickListener
        , AdapterInterface {

    @Bind(R.id.create_videopost_lv)
    ListView createVideopostLv;

    private Context mContext = CreateVideoPostActivity.this;
    private String path = "";
    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;
    public static int CHOOSE_CODE = 2417;

    private LinearLayout.LayoutParams paramsBg;

    private CreateVoicePostAdapter mAdapter;
    private View headView;
    private MyCleanEditText partVideoTitleEt;
    private RelativeLayout partVideoRl;
    private ImageView partVideoBgIv;
    private LinearLayout partVideoLl;
    private Button partVideoChangevideoBt, partVideoChangeimgBt;
    private TextView partVideoAddTv;
    private EditText mFocusEditText;

    private File videoFile;

    private int mAnonymity = 0;
    private String upImgUrl = "";
    private String upVideoUrl;
    private String upTitle;
    private int upTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_videopost);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("发布视频");

        setBarRightIv(R.mipmap.bt_create_ok);
        getBarRightRl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upTitle = partVideoTitleEt.getText().toString().trim();
                if (upTitle.isEmpty()) {
                    showToast("请输入标题");
                    return;
                }
                if (upVideoUrl==null || upVideoUrl.isEmpty()) {
                    showToast("请选择视频");
                    return;
                }
                File f = new File(upVideoUrl);
                if (f==null || !f.exists()) {
                    showToast("请选择视频");
                    return;
                }
                if (f == null || f.length() > 10 * 1024 * 1024) {
                    showToast("所选视频文件不能大于10M");
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
        int w = getmWidth()- DensityUtil.dp2px(mContext, 20);
        paramsBg = new LinearLayout.LayoutParams(w, w*3/5);

        headView = LayoutInflater.from(mContext).inflate(R.layout.part_video_post_head_view, null);
        partVideoTitleEt = (MyCleanEditText) headView.findViewById(R.id.part_video_title_et);
        partVideoRl = (RelativeLayout) headView.findViewById(R.id.part_video_rl);
        partVideoBgIv = (ImageView) headView.findViewById(R.id.part_video_bg_iv);
        partVideoLl = (LinearLayout) headView.findViewById(R.id.part_video_ll);
        partVideoChangevideoBt = (Button) headView.findViewById(R.id.part_video_changevideo_bt);
        partVideoChangeimgBt = (Button) headView.findViewById(R.id.part_video_changeimg_bt);
        partVideoAddTv= (TextView) headView.findViewById(R.id.part_video_add_tv);

        partVideoRl.setLayoutParams(paramsBg);
        partVideoChangevideoBt.setOnClickListener(this);
        partVideoChangeimgBt.setOnClickListener(this);
        partVideoAddTv.setOnClickListener(this);

        partVideoTitleEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditFocus(partVideoTitleEt, 0);
            }
        });
        partVideoTitleEt.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setEditFocus(partVideoTitleEt, 0);
                }
            }
        });

        mAdapter = new CreateVoicePostAdapter(mContext, this);
        createVideopostLv.addHeaderView(headView, null, true);
        createVideopostLv.setHeaderDividersEnabled(false);
        createVideopostLv.setAdapter(mAdapter);
    }

    private void setEditFocus(EditText postTitleEt, int i) {
        mFocusEditText = postTitleEt;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getIntExtra("Goods_id", -1)!=-1 && mAdapter!=null && MyBaseApplication.isRelation>0) {
            GoodsListModel temp = new GoodsListModel(getIntent().getIntExtra("Goods_id", -1)
                    , getIntent().getStringExtra("Pic_img"), getIntent().getStringExtra("Title")
                    , getIntent().getStringExtra("Discount_price"));
            MyBaseApplication.isRelation = 0;
            mAdapter.clearData();
            mAdapter.addBody(MyBaseApplication.creatPostGoods);
            boolean isRelationed = false;
            for (int i=0;i<MyBaseApplication.creatPostGoods.size();i++) {
                if (MyBaseApplication.creatPostGoods.get(i).getGoods_id()==temp.getGoods_id()) {
                    isRelationed = true;
                    showToast("此商品已关联");
                    break;
                }
            }
            if (!isRelationed) {
                mAdapter.addBody(temp);
            }
            partVideoTitleEt.setText(MyBaseApplication.imagetextPostTitle);
            if (!MyBaseApplication.videoPath.isEmpty()
                    && new File(MyBaseApplication.videoPath).exists()) {
                upVideoUrl = MyBaseApplication.videoPath;
                upTime = MyBaseApplication.videoLength;
                upImgUrl = MyBaseApplication.videoImg;
                if (!upImgUrl.isEmpty()) {
                    displayImg(upImgUrl);
                }
            }
            getIntent().removeExtra("Goods_id");
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        if (id == Common.NET_RESET_HEAD) {
            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            if (mHeadImgResultModel!=null && mHeadImgResultModel.getFiles().size()>0 ) {
                if (!mHeadImgResultModel.getFiles().get(0).isSuccessful()) {
                    showToast("封面图片修改失败");
                    cancelLoading();
                } else {
                    HeadImgSuccessModel mHeadImgSuccessModel = mGson.fromJson(data, HeadImgSuccessModel.class);
                    upImgUrl = mHeadImgSuccessModel.getFiles().get(0).getUrl();

                    OkHttpUtils.post().url(Common.Url_Reset_Head).addParams("uploadType", "mp4")
                            .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                            .addFile("files0_name", StringUtils.getFileName(upVideoUrl), new File(upVideoUrl))
                            .id(Common.NET_UP_VIDEO_ID).tag(Common.NET_UP_VIDEO_ID).build()
                            .connTimeOut(Common.timeOutTwo).readTimeOut(Common.timeOutTwo).writeTimeOut(Common.timeOutTwo)
                            .execute(new MyStringCallback(mContext, CreateVideoPostActivity.this, true));
                }
            }
        } else if (id == Common.NET_UP_VIDEO_ID) {
            UpVoiceModel mUpVoiceModel = mGson.fromJson(data, UpVoiceModel.class);

            if (mUpVoiceModel.getFiles().size()==0
                    || !mUpVoiceModel.getFiles().get(0).isSuccessful()) {
                showToast("上传失败");
                cancelLoading();
                return;
            }
            upVideoUrl = mUpVoiceModel.getFiles().get(0).getUrl();
            List<Map<String, Integer>> maps = new ArrayList<>();
            for (int i=0;i<mAdapter.getGoodsList().size();i++) {
                Map<String, Integer> map = new HashMap<>();
                map.put("goods_id", mAdapter.getGoodsList().get(i).getGoods_id());
                maps.add(map);
            }

            CreateMultiMediaPostModel temp = new CreateMultiMediaPostModel(upImgUrl, upVideoUrl
                    , upTitle, upTime, mAnonymity, maps);
            LogUtils.e(">>>>mGson.toJson(temp):"+mGson.toJson(temp));
            OkHttpUtils.postString().url(Common.Url_Create_Video_Post)
                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .mediaType(Common.JSON).content(mGson.toJson(temp))
                    .id(Common.NET_CREATE_VIDEO_POST_ID).tag(Common.NET_CREATE_VIDEO_POST_ID)
                    .build().execute(new MyStringCallback(mContext, this, true));
        } else if (id == Common.NET_CREATE_VIDEO_POST_ID) {
            cancelLoading();
            showToast("发布成功！");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clear();

        OkHttpUtils.getInstance().cancelTag(Common.NET_RESET_HEAD);
        OkHttpUtils.getInstance().cancelTag(Common.NET_UP_VIDEO_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CREATE_VIDEO_POST_ID);
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

                OkHttpUtils.post().url(Common.Url_Reset_Head).addParams("uploadType", "img")
                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .addFile("files0_name", StringUtils.getFileName(upImgUrl), new File(upImgUrl))
                        .id(Common.NET_RESET_HEAD).tag(Common.NET_RESET_HEAD).build()
                        .connTimeOut(Common.timeOutTwo).readTimeOut(Common.timeOutTwo).writeTimeOut(Common.timeOutTwo)
                        .execute(new MyStringCallback(mContext, CreateVideoPostActivity.this, true));
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

    private void changeImg() {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setTitle(Html.fromHtml("<font color=\"#f9464a\">更换封面</font>"));
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
            for (int i = 0; i< Bimp.drr.size(); i++) {
                displayImg(Bimp.drr.get(i));
            }
        } else if (requestCode == TAKE_BIG_PICTURE) {
            if (!path.isEmpty()) {
                displayImg(path);
            }
        } else if (requestCode == CHOOSE_CODE) {

            upVideoUrl = SelectVideoUtil.getPathOne(mContext, data.getData());
            if (upVideoUrl!=null) {
                videoFile = new File(upVideoUrl);
            }

            if (!videoFile.exists()) {
                showToast("选择的视频文件不存在");
                return;
            }

            if (!upVideoUrl.substring(upVideoUrl.lastIndexOf(".")+1).equals("mp4") ) {
                showWraning("目前只支持上传格式为.mp4的视频，请重新选择");
                partVideoBgIv.setImageBitmap(null);
                partVideoBgIv.setVisibility(View.GONE);
                partVideoLl.setVisibility(View.GONE);
                if (mFocusEditText!=null)
                    mFocusEditText.setVisibility(View.VISIBLE);
                upVideoUrl = "";
                return;
            }

            BaseMediaBitrateConfig compressMode = new AutoVBRMode();
            compressMode.setVelocity("fast");
            LocalMediaConfig.Buidler buidler = new LocalMediaConfig.Buidler();
            final LocalMediaConfig config = buidler
                    .setVideoPath(upVideoUrl)
                    .captureThumbnailsTime(1)
                    .doH264Compress(new AutoVBRMode())
                    .setFramerate(15)
                    .build();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showProgress("","压缩中...",-1);
                        }
                    });
                    final OnlyCompressOverBean onlyCompressOverBean = new LocalMediaCompress(config).startCompress();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hideProgress();
                            upVideoUrl = onlyCompressOverBean.getVideoPath();
                            upTime = getPlayTime(upVideoUrl);
                            displayImg(onlyCompressOverBean.getPicPath());
                        }
                    });
                }
            }).start();
        }
    }

    private void displayImg(String path) {
        Bitmap bm = null;
        if (path==null || path.equals("")) {
            bm = getVideoThumb2(upVideoUrl);
            upImgUrl = BitmapTools.saveBitmap(bm).getPath();
            try {
                upImgUrl = BitmapTools.revitionImage(upImgUrl).getPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            upImgUrl = path;
            try {
                bm = Bimp.revitionImageSize(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        partVideoBgIv.setImageBitmap(bm);
        partVideoAddTv.setVisibility(View.GONE);
        partVideoBgIv.setVisibility(View.VISIBLE);
        partVideoLl.setVisibility(View.VISIBLE);
    }

    private ProgressDialog mProgressDialog;
    private void showProgress(String title, String message, int theme) {
        if (mProgressDialog == null) {
            if (theme > 0)
                mProgressDialog = new ProgressDialog(this, theme);
            else
                mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setCanceledOnTouchOutside(false);// 不能取消
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);// 设置进度条是否不明确
        }

        if (!StringUtils.isEmpty(title))
            mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    private void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.part_video_changeimg_bt:
                changeImg();
                break;
            case R.id.part_video_changevideo_bt:
            case R.id.part_video_add_tv:
                if (checkWriteExternalPermission()) {
                    Intent it = new Intent(Intent.ACTION_GET_CONTENT,
                            android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    it.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video/*");
                    startActivityForResult(it, CHOOSE_CODE);
                    MyBaseApplication.getApplication().setFilterLock(true);
                } else {
                    showWraning("请确认是否已打开存储权限！");
                }
                break;
        }
    }

    //如读写权限
    private boolean checkWriteExternalPermission() {
        String permission = "android.permission.READ_EXTERNAL_STORAGE";
        int res = mContext.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_childtwo_delete_iv:
                mAdapter.deleteDataById(position);
                break;
            case R.id.item_childtwo_add_ll:
                MyBaseApplication.creatPostGoods.clear();
                MyBaseApplication.creatPostGoods.addAll(mAdapter.getGoodsList());
                mAdapter.clearData();
                MyBaseApplication.isRelation = 3;
                MyBaseApplication.imagetextPostTitle = partVideoTitleEt.getText().toString();
                if (videoFile!=null && videoFile.exists()) {
                    MyBaseApplication.videoPath = upVideoUrl;
                    MyBaseApplication.videoImg = upImgUrl;
                    MyBaseApplication.videoLength = upTime;
                }
                startActivity(new Intent(mContext, MallActivity.class));
                break;
        }
    }

    /**
     * 获取视频文件截图
     *
     * @param path 视频文件的路径
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }
    /**
     * 获取视频文件缩略图 API>=8(2.2)
     *
     * @param path 视频文件的路径
     * @param kind 缩略图的分辨率：MINI_KIND、MICRO_KIND、FULL_SCREEN_KIND
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb2(String path, int kind) {
        return ThumbnailUtils.createVideoThumbnail(path, kind);
    }
    public static Bitmap getVideoThumb2(String path) {
        return getVideoThumb2(path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
    }

    private int getPlayTime(String mUri) {
        String duration = "0";
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();
        try {
            if (mUri != null) {
                HashMap<String, String> headers = null;
                if (headers == null) {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(mUri, headers);
            } else {
                //mmr.setDataSource(mFD, mOffset, mLength);
            }

            duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
            String width = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);//宽
            String height = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);//高
        } catch (Exception ex) {
            LogUtils.e("MediaMetadataRetriever exception " + ex);
        } finally {
            mmr.release();
        }
        if (duration!=null && !duration.equals("")) {
            return Integer.valueOf(duration);
        } else {
            return 0;
        }
    }
}
