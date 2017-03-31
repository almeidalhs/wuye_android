package com.atman.wysq.ui.community;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.CommunityNewAdapter;
import com.atman.wysq.model.response.CommunityNewModel;
import com.atman.wysq.model.response.HeadImgResultModel;
import com.atman.wysq.model.response.HeadImgSuccessModel;
import com.atman.wysq.model.response.MyLiveInfoModel;
import com.atman.wysq.model.response.ToLiveEorrModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.ui.discover.MyLiveRoomActivity;
import com.atman.wysq.utils.BitmapTools;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.SpaceItemDecorationGrivView;
import com.atman.wysq.utils.UiHelper;
import com.atman.wysq.widget.ShowLivePopWindow;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.BottomDialog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.tbl.okhttputils.OkHttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/3/24.
 */

public class CommunityNewFragment extends MyBaseFragment implements AdapterInterface, View.OnClickListener {

    @Bind(R.id.community_empty_tx)
    TextView communityEmptyTx;
    @Bind(R.id.community_recycler)
    PullToRefreshRecyclerView communityRecycler;
    @Bind(R.id.community_tab_dynamic)
    RadioButton communityTabDynamic;
    @Bind(R.id.community_tab_voice)
    RadioButton communityTabVoice;
    @Bind(R.id.community_tab_video)
    RadioButton communityTabVideo;
    @Bind(R.id.community_tab_hot)
    RadioButton communityTabHot;
    @Bind(R.id.part_community_topleft_ll)
    LinearLayout partCommunityTopleftLl;

    private CommunityNewAdapter mCommunityNewAdapter;
    private RecyclerView mRecyclerView;
    private CommunityNewModel mCommunityNewModel;

    private int mPage = 1;
    private int mTpyeId = 1;
    private boolean isError = true;

    private ShowLivePopWindow pop;
    private MyLiveInfoModel mMyLiveInfoModel;
    private SimpleDraweeView popSimpleDraweeView;
    private TextView partLivepopGoliveTx;
    private boolean myRoomTitleInfoSta = false;
    private boolean myRoomPicInfoSta = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_communitynew, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        initRecycler();
    }

    private void initRecycler() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, communityRecycler);

        mCommunityNewAdapter = new CommunityNewAdapter(getActivity(), getmWidth(), this);

        mRecyclerView = communityRecycler.getRefreshableView();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_dp_5);
        mRecyclerView.addItemDecoration(new SpaceItemDecorationGrivView(spacingInPixels));
        mRecyclerView.setAdapter(mCommunityNewAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {//滑动停止
                    Fresco.getImagePipeline().resume();//开启图片加载
                } else {
                    Fresco.getImagePipeline().pause();//暂停图片加载
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getActivity() != null) {
            if (isError) {
                isError = false;
                if (MyBaseApplication.getApplication().mGetMyUserIndexModel!=null && MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody()
                        .getUserDetailBean().getUserExt().getCan_live_room() == 1) {
                    partCommunityTopleftLl.setVisibility(View.VISIBLE);
                } else {
                    partCommunityTopleftLl.setVisibility(View.INVISIBLE);
                }
                doHttp(true);
            }
        }
    }

    private void doHttp(boolean b) {
        String url = "";
        if (mPage == 1) {
            if (mTpyeId == 4) {
                showToast("开发中");
                return;
            } else {
                url = Common.Url_Get_Community_First + mTpyeId;
            }
        } else {
            switch (mTpyeId) {
                case 1:
                    url = Common.Url_Get_Community_Dynamic + mPage;
                    break;
                case 2:
                    url = Common.Url_Get_Community_Audio + mPage;
                    break;
                case 3:
                    url = Common.Url_Get_Community_Video + mPage;
                    break;
                case 4:
                    showToast("开发中");
                    break;
            }
        }
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_COMMUNITY_FIRST_ID);
        OkHttpUtils.get().url(url)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_COMMUNITY_FIRST_ID).id(Common.NET_GET_COMMUNITY_FIRST_ID).build()
                .execute(new MyStringCallback(getActivity(), this, b));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_COMMUNITY_FIRST_ID) {
            mCommunityNewModel = mGson.fromJson(data, CommunityNewModel.class);
            if (mCommunityNewModel.getBody() == null
                    || mCommunityNewModel.getBody().size() == 0) {
                if (mCommunityNewAdapter != null && mCommunityNewAdapter.getItemCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, communityRecycler);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, communityRecycler);
                if (mPage == 1) {
                    mCommunityNewAdapter.clearData();
                }
                mCommunityNewAdapter.addData(mCommunityNewModel.getBody());
            }

            if (mCommunityNewAdapter != null && mCommunityNewAdapter.getItemCount() > 0) {
                communityEmptyTx.setVisibility(View.GONE);
                communityRecycler.setVisibility(View.VISIBLE);
            } else {
                communityEmptyTx.setVisibility(View.VISIBLE);
                communityRecycler.setVisibility(View.GONE);
            }
        } else if (id == Common.NET_GETMYLIVEINFO_ID) {
            mMyLiveInfoModel = mGson.fromJson(data, MyLiveInfoModel.class);
            pop.showAtLocation(pop.getV(), Gravity.CENTER, 0, 0);
            pop.setTitle(mMyLiveInfoModel.getBody().getRoom_name());
            popSimpleDraweeView = pop.setBg(Common.ImageUrl + mMyLiveInfoModel.getBody().getPic_url());
            popSimpleDraweeView.setOnClickListener(this);
            partLivepopGoliveTx = pop.getPartLivepopGoliveTx();
            partLivepopGoliveTx.setOnClickListener(this);
        } else if (id == Common.NET_UPDATA_MYLIVEINFO_ID || id == Common.NET_ADD_LIVE_ID) {
            mMyLiveInfoModel = mGson.fromJson(data, MyLiveInfoModel.class);
            if (mMyLiveInfoModel.getBody()==null) {
                ToLiveEorrModel mToLiveEorrModel = mGson.fromJson(data, ToLiveEorrModel.class);
                showWraning(mToLiveEorrModel.getResult());
            } else {
                toMyLive(mMyLiveInfoModel);
            }
        } else if (id == Common.NET_RESET_HEAD) {
            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            if (mHeadImgResultModel!=null && mHeadImgResultModel.getFiles().size()>0 ) {
                if (!mHeadImgResultModel.getFiles().get(0).isSuccessful()) {
                    showToast("封面图片修改失败");
                } else {
                    HeadImgSuccessModel mHeadImgSuccessModel = mGson.fromJson(data, HeadImgSuccessModel.class);
                    if (mMyLiveInfoModel.getBody().getLive_room_id()==0) {
                        addLiveData(mHeadImgSuccessModel.getFiles().get(0).getUrl(), pop.getTitlle());
                    } else {
                        upLiveData(mHeadImgSuccessModel.getFiles().get(0).getUrl(), pop.getTitlle());
                    }
                }
            }
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        mPage = 1;
        mCommunityNewAdapter.clearData();
        doHttp(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        mPage += 1;
        doHttp(false);
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        mPage = 1;
        onLoad(PullToRefreshBase.Mode.BOTH, communityRecycler);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_COMMUNITY_FIRST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GETMYLIVEINFO_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_UPDATA_MYLIVEINFO_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_LIVE_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_RESET_HEAD);
    }

    @OnClick({R.id.part_community_topleft_ll, R.id.community_tab_dynamic, R.id.community_tab_voice
            , R.id.community_tab_video, R.id.community_tab_hot, R.id.part_community_topright_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.part_community_topleft_ll:
                if (!isLogin()) {
                    showLogin();
                } else {
                    if (MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody()
                            .getUserDetailBean().getUserExt().getUserLevel() >= 2) {
                        pop = new ShowLivePopWindow(getActivity(), view, getmWidth());
                        OkHttpUtils.get().url(Common.Url_GetMyLiveInfo)
                                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                .tag(Common.NET_GETMYLIVEINFO_ID).id(Common.NET_GETMYLIVEINFO_ID).build()
                                .execute(new MyStringCallback(getActivity(), CommunityNewFragment.this, true));
                    } else {
                        showWraning("很抱歉，该功能仅对2级及以上等级用户开放！");
                    }
                }
                break;
            case R.id.community_tab_dynamic:
                mTpyeId = 1;
                mPage = 1;
                mCommunityNewAdapter.clearData();
                doHttp(false);
                break;
            case R.id.community_tab_voice:
                mTpyeId = 2;
                mPage = 1;
                mCommunityNewAdapter.clearData();
                doHttp(false);
                break;
            case R.id.community_tab_video:
                mTpyeId = 3;
                mPage = 1;
                mCommunityNewAdapter.clearData();
                doHttp(false);
                break;
            case R.id.community_tab_hot:
                mTpyeId = 4;
                mPage = 1;
                mCommunityNewAdapter.clearData();
                doHttp(false);
                break;
            case R.id.part_community_topright_ll:
                showPopupWindow(view);
                break;
            case R.id.part_livepop_bg_iv:
                showHeadImg(view);
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
                if (mMyLiveInfoModel.getBody().getLive_room_id()==0) {
                    upPicToService();
                } else {
                    if (!pop.getTitlle().equals(mMyLiveInfoModel.getBody().getRoom_name())) {
                        myRoomTitleInfoSta = true;
                    }
                    if (myRoomTitleInfoSta && !myRoomPicInfoSta) {
                        upLiveData(mMyLiveInfoModel.getBody().getPic_url(), pop.getTitlle());
                    } else {
                        if (myRoomPicInfoSta) {
                            upPicToService();
                        } else {
                            toMyLive(mMyLiveInfoModel);
                        }
                    }
                }
                break;
        }
    }

    private View popView;
    private PopupWindow popupWindow;
    private void showPopupWindow(View view) {
        LayoutInflater inflaterGroup = LayoutInflater.from(getActivity());
        popView = inflaterGroup.inflate(R.layout.pop_creatpost_view, null);
        popupWindow = new PopupWindow(popView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.color.color_88000000));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
    }

    private void toMyLive(MyLiveInfoModel temp) {
        startActivity(MyLiveRoomActivity.buildIntent(getActivity(), temp));
    }

    private void upLiveData(String url, String titlle) {
        Map<String, String> p = new HashMap<>();
        p.put("pic_url", url);
        p.put("room_name", titlle);
        p.put("description", titlle);
        OkHttpUtils.postString().url(Common.Url_UpData_MyLiveInfo).id(Common.NET_UPDATA_MYLIVEINFO_ID)
                .content(mGson.toJson(p)).mediaType(Common.JSON).tag(Common.NET_UPDATA_MYLIVEINFO_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie()).build()
                .execute(new MyStringCallback(getActivity(), this, true));
    }

    private void addLiveData(String url, String titlle) {
        Map<String, String> p = new HashMap<>();
        p.put("pic_url", url);
        p.put("room_name", titlle);
        p.put("description", titlle);
        OkHttpUtils.postString().url(Common.Url_Add_Live).id(Common.NET_ADD_LIVE_ID)
                .content(mGson.toJson(p)).mediaType(Common.JSON).tag(Common.NET_ADD_LIVE_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie()).build()
                .execute(new MyStringCallback(getActivity(), this, true));
    }

    private void upPicToService() {
        if (resulturl != null) {
            OkHttpUtils.post().url(Common.Url_Reset_Head)
                    .addParams("uploadType", "img").addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                    .addFile("files0_name", StringUtils.getFileName(resulturl),
                            new File(resulturl)).id(Common.NET_RESET_HEAD)
                    .tag(Common.NET_RESET_HEAD).build().execute(new MyStringCallback(getActivity(), this, true));
        }
    }

    private Uri imageUri;//The Uri to store the big bitmap
    private String resulturl;//The Uri to store the big bitmap
    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;
    private String path = "";
    private void showHeadImg(View view) {
        BottomDialog.Builder builder = new BottomDialog.Builder(getActivity());
        builder.setItems(new String[]{"拍照", "相册选取"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {//拍照
                    path = UiHelper.photo(getActivity(), path, TAKE_BIG_PICTURE);
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
                temp = BitmapTools.revitionImage(getActivity(), imageUri);
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

    @Override
    public void onItemClick(View view, int position) {
        CommunityNewModel.BodyBean temp = mCommunityNewAdapter.getItemData(position);
        int CategoryId = temp.getCategory();
        switch (CategoryId) {
            case 1://图文
                startActivity(PostingsDetailActivity.buildIntent(getActivity()
                        , temp.getTitle(), temp.getBlog_id(), false, temp.getVip_level()));
                break;
            case 2://语音
                break;
            case 3://视频
                break;
            case 4://直播
                break;
        }
    }
}
