package com.atman.wysq.ui.discover;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.LiveHallGridViewAdapter;
import com.atman.wysq.model.response.GetLiveHallModel;
import com.atman.wysq.model.response.HeadImgResultModel;
import com.atman.wysq.model.response.HeadImgSuccessModel;
import com.atman.wysq.model.response.MyLiveInfoModel;
import com.atman.wysq.model.response.ToLiveEorrModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.personal.RechargeActivity;
import com.atman.wysq.utils.BitmapTools;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.UiHelper;
import com.atman.wysq.widget.ShowLivePopWindow;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.PromptDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.tbl.okhttputils.OkHttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/12/28.
 */

public class LiveHallActivity extends MyBaseActivity implements AdapterInterface, View.OnClickListener {

    @Bind(R.id.pullToRefreshGridView)
    PullToRefreshGridView pullToRefreshGridView;

    private Context mContext = LiveHallActivity.this;
    private LiveHallGridViewAdapter mAdapter;
    private View mEmpty;
    private TextView mEmptyTX;
    private GetLiveHallModel mGetLiveHallModel;
    private TextView mLiveRightTX;
    private RelativeLayout mLiveRightRL;

    private int page = 1;
    private RelativeLayout.LayoutParams params;
    private ShowLivePopWindow pop;
    private SimpleDraweeView popSimpleDraweeView;
    private MyLiveInfoModel mMyLiveInfoModel;
    private TextView partLivepopGoliveTx;

    private boolean myRoomTitleInfoSta = false;
    private boolean myRoomPicInfoSta = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livehall);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("直播大厅");

        if (MyBaseApplication.getApplication().mGetMyUserIndexModel==null) {
            finish();
            return;
        }

        if (MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody()
                .getUserDetailBean().getUserExt().getCan_live_room()==1) {

            mLiveRightTX = setBarRightTx(" 我要直播");
            Drawable drawable = getResources().getDrawable(R.mipmap.live_main_img_livecr);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mLiveRightTX.setCompoundDrawables(drawable, null, null, null);

            mLiveRightRL = getBarRightRl();
            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                    , RelativeLayout.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.rightMargin = DensityUtil.dp2px(mContext, 10);
            mLiveRightRL.setLayoutParams(params);
            mLiveRightRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop = new ShowLivePopWindow(LiveHallActivity.this, v, getmWidth());
                    OkHttpUtils.get().url(Common.Url_GetMyLiveInfo)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .tag(Common.NET_GETMYLIVEINFO_ID).id(Common.NET_GETMYLIVEINFO_ID).build()
                            .execute(new MyStringCallback(mContext, LiveHallActivity.this, true));
                }
            });
        }


        initGridView();
    }

    private void initGridView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshGridView);

        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无夜友直播！");

        mAdapter = new LiveHallGridViewAdapter(mContext, getmWidth(), this);
        pullToRefreshGridView.setEmptyView(mEmpty);
        pullToRefreshGridView.setAdapter(mAdapter);
        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody()
                        .getUserDetailBean().getUserExt().getGold_coin()>0){
                    startActivity(ListenLiveActivity.buildIntent(mContext, mAdapter.getItem(position)));
                } else {
                    PromptDialog.Builder builder = new PromptDialog.Builder(mContext);
                    builder.setMessage("金币不足，请先获取足够金币");
                    builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setNeutralButton("购买金币", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startActivity(RechargeActivity.buildIntent(mContext
                                    , MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getGold_coin()
                                    , MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getConvert_coin()));
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();

        dohttp(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GETLIVELIST_ID) {
            mGetLiveHallModel = mGson.fromJson(data, GetLiveHallModel.class);
            if (mGetLiveHallModel.getBody() == null
                    || mGetLiveHallModel.getBody().size() == 0) {
                if (mAdapter!=null && mAdapter.getCount()>0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshGridView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshGridView);
                mAdapter.addBody(mGetLiveHallModel.getBody());
            }
        } else if (id == Common.NET_GETMYLIVEINFO_ID) {
            mMyLiveInfoModel = mGson.fromJson(data, MyLiveInfoModel.class);
            pop.showAtLocation(pop.getV(), Gravity.CENTER, 0, 0);
            pop.setTitle(mMyLiveInfoModel.getBody().getRoom_name());
            popSimpleDraweeView = pop.setBg(Common.ImageUrl + mMyLiveInfoModel.getBody().getPic_url());
            popSimpleDraweeView.setOnClickListener(this);
            partLivepopGoliveTx = pop.getPartLivepopGoliveTx();
            partLivepopGoliveTx.setOnClickListener(this);
        } else if (id == Common.NET_RESET_HEAD) {
            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            if (mHeadImgResultModel!=null && mHeadImgResultModel.getFiles().size()>0 ) {
                if (!mHeadImgResultModel.getFiles().get(0).isSuccessful()) {
                    showToast("封面图片修改失败");
                } else {
                    HeadImgSuccessModel mHeadImgSuccessModel = mGson.fromJson(data, HeadImgSuccessModel.class);
                    upLiveData(mHeadImgSuccessModel.getFiles().get(0).getUrl(), pop.getTitlle());
                }
            }
        } else if (id == Common.NET_UPDATA_MYLIVEINFO_ID) {
            mMyLiveInfoModel = mGson.fromJson(data, MyLiveInfoModel.class);
            if (mMyLiveInfoModel.getBody()==null) {
                ToLiveEorrModel mToLiveEorrModel = mGson.fromJson(data, ToLiveEorrModel.class);
                showWraning(mToLiveEorrModel.getResult());
            } else {
                toMyLive(mMyLiveInfoModel);
            }
        }
    }

    private void toMyLive(MyLiveInfoModel temp) {
        startActivity(MyLiveRoomActivity.buildIntent(mContext, temp));
    }

    private void upLiveData(String url, String titlle) {
        Map<String, String> p = new HashMap<>();
        p.put("pic_url", url);
        p.put("room_name", titlle);
        p.put("description", titlle);
        OkHttpUtils.postString().url(Common.Url_UpData_MyLiveInfo).id(Common.NET_UPDATA_MYLIVEINFO_ID)
                .content(mGson.toJson(p)).mediaType(Common.JSON).tag(Common.NET_UPDATA_MYLIVEINFO_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie()).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        page = 1;
        onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshGridView);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        page += 1;
        dohttp(false);
    }

    private void dohttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_GetLiveList + page)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GETLIVELIST_ID).id(Common.NET_GETLIVELIST_ID).build()
                .execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        page = 1;
        mAdapter.clearData();
        dohttp(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GETLIVELIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GETMYLIVEINFO_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_RESET_HEAD);
        OkHttpUtils.getInstance().cancelTag(Common.NET_UPDATA_MYLIVEINFO_ID);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.part_livepop_bg_iv:
                showHeadImg(v);
                break;
            case R.id.part_livepop_golive_tx:
                if (pop.getTitlle()==null || pop.getTitlle().trim().isEmpty()) {
                    showWraning("主题描述不能为空");
                    return;
                }
                if ((mMyLiveInfoModel.getBody()==null || mMyLiveInfoModel.getBody().getPic_url()==null)
                        && resulturl == null) {
                    showWraning("封面图片不能为空");
                    return;
                }
                pop.dismiss();
                if (!pop.getTitlle().equals(mMyLiveInfoModel.getBody().getRoom_name())) {
                    myRoomTitleInfoSta = true;
                }
                if (myRoomTitleInfoSta && !myRoomPicInfoSta) {
                    upLiveData(mMyLiveInfoModel.getBody().getPic_url(), pop.getTitlle());
                } else {
                    if (myRoomPicInfoSta) {
                        if (resulturl != null) {
                            OkHttpUtils.post().url(Common.Url_Reset_Head)
                                    .addParams("uploadType", "img").addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                                    .addFile("files0_name", StringUtils.getFileName(resulturl),
                                            new File(resulturl)).id(Common.NET_RESET_HEAD)
                                    .tag(Common.NET_RESET_HEAD).build().execute(new MyStringCallback(mContext, this, true));
                        }
                    } else {
                        toMyLive(mMyLiveInfoModel);
                    }
                }
                break;
        }
    }

    private Uri imageUri;//The Uri to store the big bitmap
    private String resulturl;//The Uri to store the big bitmap
    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;
    private String path = "";
    private void showHeadImg(View view) {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setItems(new String[]{"拍照", "相册选取"}, new DialogInterface.OnClickListener() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
            imageUri = data.getData();
        } else if (requestCode == TAKE_BIG_PICTURE) {
            imageUri = Uri.parse("file:///" + path);
        }
        if (imageUri != null) {
            myRoomPicInfoSta = true;
            File temp = null;
            try {
                temp = BitmapTools.revitionImage(mContext, imageUri);
                if (temp==null) {
                    showToast("发送失败");
                    return;
                }
                resulturl = temp.getPath();
                popSimpleDraweeView.setImageURI("file:///" +resulturl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
