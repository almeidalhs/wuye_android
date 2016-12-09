package com.atman.wysq.ui.personal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.base.baselibs.net.YunXinAuthOutEvent;
import com.atman.wysq.model.response.GetMyUserIndexModel;
import com.atman.wysq.model.response.GetTaskAllModel;
import com.atman.wysq.model.response.HeadImgResultModel;
import com.atman.wysq.model.response.HeadImgSuccessModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.ui.login.LoginActivity;
import com.atman.wysq.ui.mall.TwoLevelCategoryListActivity;
import com.atman.wysq.ui.mall.order.MyOrderListActivity;
import com.atman.wysq.ui.yunxinfriend.HisGuardianActivity;
import com.atman.wysq.ui.yunxinfriend.HisVisitorActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.UiHelper;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.CustomImageView;
import com.base.baselibs.widget.PromptDialog;
import com.base.baselibs.widget.RoundImageView;
import com.base.baselibs.widget.pullzoom.PullZoomScrollVIew;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述 我的
 * 作者 tangbingliang
 * 时间 16/7/1 18:13
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class PersonalFragment extends MyBaseFragment implements View.OnClickListener {

    @Bind(R.id.personal_scrollview)
    PullZoomScrollVIew personalScrollview;

    private ImageView personalSettingIv;
    private ImageView personalGenderIv;
    private ImageView personalTaskIv;
    private CustomImageView personalHeadIv;
    private ImageView personalHeadVerifyImg;
    private TextView personalNameTx;
    private TextView personalGendercertificationTv;
    private GetMyUserIndexModel mGetUserIndexModel;

    private LinearLayout personalMaillistLl;
    private LinearLayout personalServiceLl;
    private LinearLayout personalTaskLl;
    private LinearLayout personalRechargeLl;
    private LinearLayout personalMyorderLl;
    private LinearLayout personalVisitorLl;
    private LinearLayout personalFriendsLl;
    private LinearLayout personalVipLl;
    private LinearLayout personalMygiftLl;

    private TextView personalVisitorNumTx;
    private TextView personalVipTx;
    private ImageView personalSVipIv;
    private TextView personalMycoinTv;
    private TextView personalMyvipstatusTv;
    private RoundImageView personalVisitorOneIv, personalVisitorTwoIv, personalVisitorThreeIv;
    private ImageView personalGiftOneIv, personalGiftTwoIv, personalGiftThreeIv, personalGiftFourIv, personalGiftFiveIv;
    private RoundImageView personalGuardianOneIv, personalGuardianTwoIv, personalGuardianThreeIv;
    private ImageView personalGuardianTopOneIv, personalGuardianTopTwoIv, personalGuardianTopThreeIv;
    private RelativeLayout personalGuardianOneRl,personalGuardianTwoRl,personalGuardianThreeRl;

    private String mHeadImgUrl;
    private boolean isHead = false;

    private int PICK_FROM_CAMERA = 777;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onUserEvent(YunXinAuthOutEvent event) {//参数必须是ClassEvent类型, 否则不会调用此方法
        hitSetring();
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 19.0F)));
        personalScrollview.setHeaderLayoutParams(localObject);

        personalSettingIv = (ImageView) personalScrollview.findViewById(R.id.personal_setting_iv);
        personalGenderIv = (ImageView) personalScrollview.findViewById(R.id.personal_gender_iv);
        personalTaskIv = (ImageView) personalScrollview.findViewById(R.id.personal_task_iv);
        personalNameTx = (TextView) personalScrollview.findViewById(R.id.personal_name_tx);
        personalGendercertificationTv = (TextView) personalScrollview.findViewById(R.id.personal_gendercertification_tv);
        personalHeadIv = (CustomImageView) personalScrollview.findViewById(R.id.personal_head_iv);
        personalHeadVerifyImg = (ImageView) personalScrollview.findViewById(R.id.personal_head_verify_img);
        personalMaillistLl = (LinearLayout) personalScrollview.findViewById(R.id.personal_maillist_ll);
        personalServiceLl = (LinearLayout) personalScrollview.findViewById(R.id.personal_service_ll);
        personalTaskLl = (LinearLayout) personalScrollview.findViewById(R.id.personal_task_ll);
        personalRechargeLl = (LinearLayout) personalScrollview.findViewById(R.id.personal_recharge_ll);
        personalMyorderLl = (LinearLayout) personalScrollview.findViewById(R.id.personal_myorder_ll);
        personalMygiftLl = (LinearLayout) personalScrollview.findViewById(R.id.personal_mygift_ll);
        personalVipLl = (LinearLayout) personalScrollview.findViewById(R.id.personal_vip_ll);
        personalVisitorNumTx = (TextView) personalScrollview.findViewById(R.id.personal_visitor_num_tx);
        personalMycoinTv = (TextView) personalScrollview.findViewById(R.id.personal_mycoin_tv);
        personalMyvipstatusTv = (TextView) personalScrollview.findViewById(R.id.personal_myvipstatus_tv);
        personalVipTx = (TextView) personalScrollview.findViewById(R.id.personal_vip_tx);
        personalSVipIv = (ImageView) personalScrollview.findViewById(R.id.personal_svip_iv);

        personalVisitorLl = (LinearLayout) personalScrollview.findViewById(R.id.personal_visitor_ll);
        personalVisitorLl.setOnClickListener(this);
        personalVisitorOneIv = (RoundImageView) personalScrollview.findViewById(R.id.personal_visitor_one_iv);
        personalVisitorTwoIv = (RoundImageView) personalScrollview.findViewById(R.id.personal_visitor_two_iv);
        personalVisitorThreeIv = (RoundImageView) personalScrollview.findViewById(R.id.personal_visitor_three_iv);

        personalGiftOneIv = (ImageView) personalScrollview.findViewById(R.id.personal_gift_one_iv);
        personalGiftTwoIv = (ImageView) personalScrollview.findViewById(R.id.personal_gift_two_iv);
        personalGiftThreeIv = (ImageView) personalScrollview.findViewById(R.id.personal_gift_three_iv);
        personalGiftFourIv = (ImageView) personalScrollview.findViewById(R.id.personal_gift_four_iv);
        personalGiftFiveIv = (ImageView) personalScrollview.findViewById(R.id.personal_gift_five_iv);

        personalFriendsLl = (LinearLayout) personalScrollview.findViewById(R.id.personal_friends_ll);
        personalFriendsLl.setOnClickListener(this);
        personalGuardianOneIv = (RoundImageView) personalScrollview.findViewById(R.id.personal_guardian_one_iv);
        personalGuardianTwoIv = (RoundImageView) personalScrollview.findViewById(R.id.personal_guardian_two_iv);
        personalGuardianThreeIv = (RoundImageView) personalScrollview.findViewById(R.id.personal_guardian_three_iv);
        personalGuardianTopOneIv = (ImageView) personalScrollview.findViewById(R.id.personal_guardian_top_one_iv);
        personalGuardianTopTwoIv = (ImageView) personalScrollview.findViewById(R.id.personal_guardian_top_two_iv);
        personalGuardianTopThreeIv = (ImageView) personalScrollview.findViewById(R.id.personal_guardian_top_three_iv);
        personalGuardianOneRl = (RelativeLayout) personalScrollview.findViewById(R.id.personal_guardian_one_rl);
        personalGuardianTwoRl = (RelativeLayout) personalScrollview.findViewById(R.id.personal_guardian_two_rl);
        personalGuardianThreeRl = (RelativeLayout) personalScrollview.findViewById(R.id.personal_guardian_three_rl);

        personalSettingIv.setOnClickListener(this);
        personalHeadIv.setOnClickListener(this);
        personalNameTx.setOnClickListener(this);
        personalMaillistLl.setOnClickListener(this);
        personalServiceLl.setOnClickListener(this);
        personalTaskLl.setOnClickListener(this);
        personalRechargeLl.setOnClickListener(this);
        personalGendercertificationTv.setOnClickListener(this);
        personalMyorderLl.setOnClickListener(this);
        personalVipLl.setOnClickListener(this);
        personalMygiftLl.setOnClickListener(this);

    }

    @Override
    public void initIntentAndMemData() {
        super.initIntentAndMemData();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();

    }

    public void doHttp(boolean b) {
        if (!isLogin()) {
            hitSetring();
        } else {
            OkHttpUtils.get().url(Common.Url_Get_UserIndex)
                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .tag(Common.NET_GET_USERINDEX).id(Common.NET_GET_USERINDEX).build()
                    .execute(new MyStringCallback(getActivity(), this, b));
        }
    }

    private void hitSetring() {
        personalHeadIv.setImageResource(R.mipmap.ic_launcher);
        personalSettingIv.setVisibility(View.INVISIBLE);
        personalGenderIv.setVisibility(View.INVISIBLE);
        personalGendercertificationTv.setVisibility(View.INVISIBLE);
        personalTaskIv.setVisibility(View.INVISIBLE);
        personalHeadVerifyImg.setVisibility(View.INVISIBLE);
        personalNameTx.setText("请点击登录");
        personalMyvipstatusTv.setText("");
        personalNameTx.setTextColor(getResources().getColor(R.color.color_7F2505));
        personalGuardianOneRl.setVisibility(View.GONE);
        personalVisitorNumTx.setVisibility(View.GONE);
        personalGuardianTwoRl.setVisibility(View.GONE);
        personalGuardianThreeRl.setVisibility(View.GONE);

        personalVisitorOneIv.setVisibility(View.GONE);
        personalVisitorTwoIv.setVisibility(View.GONE);
        personalVisitorThreeIv.setVisibility(View.GONE);

        personalGiftOneIv.setVisibility(View.GONE);
        personalGiftTwoIv.setVisibility(View.GONE);
        personalGiftThreeIv.setVisibility(View.GONE);
        personalGiftFourIv.setVisibility(View.GONE);
        personalGiftFiveIv.setVisibility(View.GONE);

        personalMycoinTv.setVisibility(View.GONE);

        personalVipTx.setVisibility(View.GONE);
        personalSVipIv.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLogin() && MyBaseApplication.mGetMyUserIndexModel!=null) {
            changView();
        }
        doHttp(false);
        if (!isHead) {
            MyBaseApplication.getApplication().setFilterLock(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void clearData() {
        super.clearData();
        if (!isLogin()) {
            hitSetring();
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_USERINDEX) {
            mGetUserIndexModel = mGson.fromJson(data, GetMyUserIndexModel.class);
            MyBaseApplication.mGetMyUserIndexModel = mGetUserIndexModel;
            UpDateUI();
            OkHttpUtils.get().url(Common.Url_Get_Task)
                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .tag(Common.NET_GET_RASK).id(Common.NET_GET_RASK).build()
                    .execute(new MyStringCallback(getActivity(), this, false));
        } else if (id == Common.NET_RESET_HEAD) {
            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            if (mHeadImgResultModel!=null && mHeadImgResultModel.getFiles().size()>0 ) {
                if (!mHeadImgResultModel.getFiles().get(0).isSuccessful()) {
                    showToast("头像修改失败");
                    isHead = false;
                    MyBaseApplication.getApplication().setFilterLock(false);
                } else {
                    HeadImgSuccessModel mHeadImgSuccessModel = mGson.fromJson(data, HeadImgSuccessModel.class);
                    mHeadImgUrl = mHeadImgSuccessModel.getFiles().get(0).getUrl();
                    OkHttpUtils.postString().url(Common.Url_Modify_Head).id(Common.NET_MODIFY_HEAD)
                            .content("{\"icon\":\""+ mHeadImgUrl +"\"}")
                            .mediaType(Common.JSON).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .tag(Common.NET_MODIFY_HEAD).build().execute(new MyStringCallback(getActivity(), this, true));
                }
            }
        } else if (id == Common.NET_RESET_HEAD_TWO) {
            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            if (mHeadImgResultModel!=null && mHeadImgResultModel.getFiles().size()>0 ) {
                if (!mHeadImgResultModel.getFiles().get(0).isSuccessful()) {
                    showToast("照片上传失败");
                    isHead = false;
                    MyBaseApplication.getApplication().setFilterLock(false);
                } else {
                    HeadImgSuccessModel mHeadImgSuccessModel = mGson.fromJson(data, HeadImgSuccessModel.class);
                    mHeadImgUrl = mHeadImgSuccessModel.getFiles().get(0).getUrl();
                    OkHttpUtils.postString().url(Common.Url_Verify).id(Common.NET_VERIFY)
                            .content("{\"verify_pic\":\""+ mHeadImgUrl +"\"}")
                            .mediaType(Common.JSON).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .tag(Common.NET_VERIFY).build().execute(new MyStringCallback(getActivity(), this, true));
                }
            }
        } else if (id == Common.NET_MODIFY_HEAD) {
            isHead = false;
            MyBaseApplication.getApplication().setFilterLock(false);
            showToast("头像修改成功");
            MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().setIcon(mHeadImgUrl);
            ImageLoader.getInstance().displayImage(Common.ImageUrl + mHeadImgUrl
                    , personalHeadIv, MyBaseApplication.getApplication().getOptions());
        } else if (id == Common.NET_VERIFY) {
            showToast("提交成功，请等待审核！");
        } else if (id == Common.NET_GET_RASK) {
            GetTaskAllModel mGetTaskAllModel = mGson.fromJson(data, GetTaskAllModel.class);
            for (int i=0;i<mGetTaskAllModel.getBody().size();i++) {
                if (mGetTaskAllModel.getBody().get(i).getTask_complete() == 1
                        && mGetTaskAllModel.getBody().get(i).getRewarded() == 0) {
                    personalTaskIv.setVisibility(View.VISIBLE);
                    break;
                } else {
                    personalTaskIv.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        isHead = false;
        MyBaseApplication.getApplication().setFilterLock(false);
    }

    private void UpDateUI() {
        MyBaseApplication.mUserCion = mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getGold_coin();
        MyBaseApplication.mHEAD_URL = mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon();
        personalSettingIv.setVisibility(View.VISIBLE);
        personalGenderIv.setVisibility(View.VISIBLE);

        if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level()>=4) {
            personalVipTx.setVisibility(View.GONE);
            personalSVipIv.setVisibility(View.VISIBLE);
        } else {
            personalSVipIv.setVisibility(View.GONE);
            if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level()==0) {
                personalVipTx.setVisibility(View.GONE);
            } else {
                personalVipTx.setText("VIP."+mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level());
                personalVipTx.setVisibility(View.VISIBLE);
            }
        }
        personalNameTx.setText(mGetUserIndexModel.getBody().getUserDetailBean().getNickName());
        if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level()>=3) {
            personalNameTx.setTextColor(getResources().getColor(R.color.color_red));
        } else {
            personalNameTx.setTextColor(getResources().getColor(R.color.color_7F2505));
        }
        if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("M")) {
            personalGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            personalGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }
        if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("F")) {
            if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status()==1) {//已认证
                personalGendercertificationTv.setVisibility(View.GONE);
                personalGenderIv.setVisibility(View.GONE);
                personalHeadVerifyImg.setVisibility(View.VISIBLE);
            } else {//没认证
                personalGendercertificationTv.setVisibility(View.VISIBLE);
                personalGenderIv.setVisibility(View.VISIBLE);
                personalHeadVerifyImg.setVisibility(View.GONE);
            }
        } else {
            personalGendercertificationTv.setVisibility(View.GONE);
            personalHeadVerifyImg.setVisibility(View.GONE);
        }
        ImageLoader.getInstance().displayImage(Common.ImageUrl + mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                , personalHeadIv, MyBaseApplication.getApplication().getOptions());

        initVisitorIV();

        initguardianIV();

        personalMycoinTv.setVisibility(View.VISIBLE);
        personalMycoinTv.setText(mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getGold_coin()+"｜更多");
        if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level()==0) {
            personalMyvipstatusTv.setText("去开通");
        } else {
            personalMyvipstatusTv.setText("剩余时间：" + mGetUserIndexModel.getBody().getRemainingTime()/(24*60*60*1000)+"天");
        }

        initGiftIv();
    }

    private void initGiftIv() {
        int num = mGetUserIndexModel.getBody().getGiftList().size();
        personalGiftOneIv.setVisibility(View.GONE);
        personalGiftTwoIv.setVisibility(View.GONE);
        personalGiftThreeIv.setVisibility(View.GONE);
        personalGiftFourIv.setVisibility(View.GONE);
        personalGiftFiveIv.setVisibility(View.GONE);
        if (num==1) {
            personalGiftFiveIv.setVisibility(View.VISIBLE);
            if (mGetUserIndexModel.getBody().getGiftList().get(0).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(0).getPic_url()
                        ,personalGiftFiveIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(0).getGray_pic_url()
                        ,personalGiftFiveIv,MyBaseApplication.getApplication().getOptionsNot());
            }
        } else if (num==2) {
            personalGiftFourIv.setVisibility(View.VISIBLE);
            personalGiftFiveIv.setVisibility(View.VISIBLE);
            if (mGetUserIndexModel.getBody().getGiftList().get(0).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(0).getPic_url()
                        ,personalGiftFourIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(0).getGray_pic_url()
                        ,personalGiftFourIv,MyBaseApplication.getApplication().getOptionsNot());
            }
            if (mGetUserIndexModel.getBody().getGiftList().get(1).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(1).getPic_url()
                        ,personalGiftFiveIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(1).getGray_pic_url()
                        ,personalGiftFiveIv,MyBaseApplication.getApplication().getOptionsNot());
            }
        } else if (num==3) {
            personalGiftThreeIv.setVisibility(View.VISIBLE);
            personalGiftFourIv.setVisibility(View.VISIBLE);
            personalGiftFiveIv.setVisibility(View.VISIBLE);
            if (mGetUserIndexModel.getBody().getGiftList().get(0).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(0).getPic_url()
                        ,personalGiftThreeIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(0).getGray_pic_url()
                        ,personalGiftThreeIv,MyBaseApplication.getApplication().getOptionsNot());
            }
            if (mGetUserIndexModel.getBody().getGiftList().get(1).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(1).getPic_url()
                        ,personalGiftFourIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(1).getGray_pic_url()
                        ,personalGiftFourIv,MyBaseApplication.getApplication().getOptionsNot());
            }
            if (mGetUserIndexModel.getBody().getGiftList().get(2).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(2).getPic_url()
                        ,personalGiftFiveIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(2).getGray_pic_url()
                        ,personalGiftFiveIv,MyBaseApplication.getApplication().getOptionsNot());
            }
        } else if (num==4) {
            personalGiftTwoIv.setVisibility(View.VISIBLE);
            personalGiftThreeIv.setVisibility(View.VISIBLE);
            personalGiftFourIv.setVisibility(View.VISIBLE);
            personalGiftFiveIv.setVisibility(View.VISIBLE);
            if (mGetUserIndexModel.getBody().getGiftList().get(0).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(0).getPic_url()
                        ,personalGiftTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(0).getGray_pic_url()
                        ,personalGiftTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            }
            if (mGetUserIndexModel.getBody().getGiftList().get(1).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(1).getPic_url()
                        ,personalGiftThreeIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(1).getGray_pic_url()
                        ,personalGiftThreeIv,MyBaseApplication.getApplication().getOptionsNot());
            }
            if (mGetUserIndexModel.getBody().getGiftList().get(2).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(2).getPic_url()
                        ,personalGiftFourIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(2).getGray_pic_url()
                        ,personalGiftFourIv,MyBaseApplication.getApplication().getOptionsNot());
            }
            if (mGetUserIndexModel.getBody().getGiftList().get(3).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(3).getPic_url()
                        ,personalGiftFiveIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(3).getGray_pic_url()
                        ,personalGiftFiveIv,MyBaseApplication.getApplication().getOptionsNot());
            }
        } else if (num>=5) {
            personalGiftOneIv.setVisibility(View.VISIBLE);
            personalGiftTwoIv.setVisibility(View.VISIBLE);
            personalGiftThreeIv.setVisibility(View.VISIBLE);
            personalGiftFourIv.setVisibility(View.VISIBLE);
            personalGiftFiveIv.setVisibility(View.VISIBLE);
            if (mGetUserIndexModel.getBody().getGiftList().get(0).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(0).getPic_url()
                        ,personalGiftOneIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(0).getGray_pic_url()
                        ,personalGiftOneIv,MyBaseApplication.getApplication().getOptionsNot());
            }
            if (mGetUserIndexModel.getBody().getGiftList().get(1).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(1).getPic_url()
                        ,personalGiftTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(1).getGray_pic_url()
                        ,personalGiftTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            }
            if (mGetUserIndexModel.getBody().getGiftList().get(2).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(2).getPic_url()
                        ,personalGiftThreeIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(2).getGray_pic_url()
                        ,personalGiftThreeIv,MyBaseApplication.getApplication().getOptionsNot());
            }
            if (mGetUserIndexModel.getBody().getGiftList().get(3).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(3).getPic_url()
                        ,personalGiftFourIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(3).getGray_pic_url()
                        ,personalGiftFourIv,MyBaseApplication.getApplication().getOptionsNot());
            }
            if (mGetUserIndexModel.getBody().getGiftList().get(4).getType()==1) {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(4).getPic_url()
                        ,personalGiftFiveIv,MyBaseApplication.getApplication().getOptionsNot());
            } else {
                ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGiftList().get(4).getGray_pic_url()
                        ,personalGiftFiveIv,MyBaseApplication.getApplication().getOptionsNot());
            }
        }
    }

    private void initguardianIV() {
        int num = mGetUserIndexModel.getBody().getGuardlist().size();
        if (num==1) {
            personalGuardianOneRl.setVisibility(View.GONE);
            personalGuardianTwoRl.setVisibility(View.GONE);
            personalGuardianThreeRl.setVisibility(View.VISIBLE);
            personalGuardianTopThreeIv.setImageResource(R.mipmap.other_guard_one);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGuardlist().get(0).getIcon()
                    ,personalGuardianThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num==2) {
            personalGuardianOneRl.setVisibility(View.GONE);
            personalGuardianTwoRl.setVisibility(View.VISIBLE);
            personalGuardianThreeRl.setVisibility(View.VISIBLE);
            personalGuardianTopTwoIv.setImageResource(R.mipmap.other_guard_one);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGuardlist().get(0).getIcon()
                    ,personalGuardianTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            personalGuardianTopThreeIv.setImageResource(R.mipmap.other_guard_two);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGuardlist().get(1).getIcon()
                    ,personalGuardianThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num>=3) {
            personalGuardianOneRl.setVisibility(View.VISIBLE);
            personalGuardianTwoRl.setVisibility(View.VISIBLE);
            personalGuardianThreeRl.setVisibility(View.VISIBLE);
            personalGuardianTopOneIv.setImageResource(R.mipmap.other_guard_one);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGuardlist().get(0).getIcon()
                    ,personalGuardianOneIv,MyBaseApplication.getApplication().getOptionsNot());
            personalGuardianTopTwoIv.setImageResource(R.mipmap.other_guard_two);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGuardlist().get(1).getIcon()
                    ,personalGuardianTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            personalGuardianTopThreeIv.setImageResource(R.mipmap.other_guard_three);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGuardlist().get(2).getIcon()
                    ,personalGuardianThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        }
    }

    private List<GetMyUserIndexModel.BodyBean.VisitorMapBean.VisitorListBean> dataList = new ArrayList<>();
    private int num;
    private void initVisitorIV() {
        num = mGetUserIndexModel.getBody().getVisitorMap().getVisitorSize();
        dataList.clear();
        for (int i=0;i<mGetUserIndexModel.getBody().getVisitorMap().getVisitorList().size();i++) {
            if (mGetUserIndexModel.getBody().getVisitorMap().getVisitorList().get(i).getUser_id()!=
                    MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId()) {
                dataList.add(mGetUserIndexModel.getBody().getVisitorMap().getVisitorList().get(i));
            } else {
                num -= 1;
            }
        }
        personalVisitorNumTx.setVisibility(View.VISIBLE);
        personalVisitorNumTx.setText(""+num);
        if (num==1) {
            personalVisitorOneIv.setVisibility(View.GONE);
            personalVisitorTwoIv.setVisibility(View.GONE);
            personalVisitorThreeIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(0).getIcon()
                    ,personalVisitorThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num==2) {
            personalVisitorOneIv.setVisibility(View.GONE);
            personalVisitorTwoIv.setVisibility(View.VISIBLE);
            personalVisitorThreeIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(0).getIcon()
                    ,personalVisitorTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(1).getIcon()
                    ,personalVisitorThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num>=3) {
            personalVisitorOneIv.setVisibility(View.VISIBLE);
            personalVisitorTwoIv.setVisibility(View.VISIBLE);
            personalVisitorThreeIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(0).getIcon()
                    ,personalVisitorOneIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(1).getIcon()
                    ,personalVisitorTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(2).getIcon()
                    ,personalVisitorThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_USERINDEX);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_friends_ll:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                startActivity(HisGuardianActivity.buildIntent(getActivity()
                        , mGetUserIndexModel.getBody().getUserDetailBean().getUserId(), "我的守护者"));
                break;
            case R.id.personal_visitor_ll:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                startActivity(HisVisitorActivity.buildIntent(getActivity()
                        , mGetUserIndexModel.getBody().getUserDetailBean().getUserId(), "我的访客", num));
                break;
            case R.id.personal_vip_ll:
                if (!isLogin()) {
                    showLogin();
                } else {
                    startActivity(TwoLevelCategoryListActivity.buildIntent(getActivity(),
                            Integer.parseInt(MyBaseApplication.mWEB_ID), "VIP", true));
                }
                break;
            case R.id.personal_mygift_ll:
                if (!isLogin()) {
                    showLogin();
                } else {
                    getActivity().startActivity(MyGiftActivity.buildIntent(getActivity()));
                }
                break;
            case R.id.personal_myorder_ll:
                if (!isLogin()) {
                    showLogin();
                } else {
                    startActivity(new Intent(getActivity(), MyOrderListActivity.class));
                }
                break;
            case R.id.personal_gendercertification_tv:
                MyBaseApplication.getApplication().setFilterLock(true);
                path = UiHelper.photoBefor(getActivity(), path, PICK_FROM_CAMERA);
                break;
            case R.id.personal_setting_iv:
                if (!isLogin()) {
                    showLogin();
                } else {
                    getActivity().startActivityForResult(new Intent(getActivity()
                            , MyInformationActivity.class), Common.toMyInfo);
                }
                break;
            case R.id.personal_head_iv:
                if (!isLogin()) {
                    toLogin();
                } else {
                    showHeadImg(v);
                }
                break;
            case R.id.personal_name_tx:
                if (!isLogin()) {
                    toLogin();
                }
                break;
            case R.id.personal_maillist_ll:
                if (!isLogin()) {
                    showLogin();
                } else {
                    if (!UiHelper.isTabletDevice(getActivity())) {
                        getActivity().startActivity(new Intent(getActivity(), AddressListInvitationActivity.class));
                    } else {
                        showToast("你的设备暂不支持通讯录功能。");
                    }
                }
                break;
            case R.id.personal_service_ll:
                if (!isLogin()) {
                    showLogin();
                } else {
                    showWran(getResources().getString(R.string.personal_service_phone_str));
                }
                break;
            case R.id.personal_task_ll:
                if (!isLogin()) {
                    showLogin();
                } else {
                    getActivity().startActivity(new Intent(getActivity(), TaskListActivity.class));
                }
                break;
            case R.id.personal_recharge_ll:
                if (!isLogin()) {
                    showLogin();
                } else {
                    getActivity().startActivity(RechargeActivity.buildIntent(getActivity()
                            , mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getGold_coin()
                            , mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getConvert_coin()));
                }
                break;
        }
    }

    public void showWran(final String str) {
        PromptDialog.Builder builder = new PromptDialog.Builder(getActivity());
        builder.setMessage("客服电话："+str);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("呼叫", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MyBaseApplication.getApplication().setFilterLock(true);
                toPhone(getActivity(), str);
            }
        });
        builder.show();
    }

    private void toLogin() {
        getActivity().startActivityForResult(new Intent(getActivity(), LoginActivity.class), Common.toLogin);
    }

    private Uri imageUri;//The Uri to store the big bitmap
    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;
    private final int CROP_BIG_PICTURE = 666;
    private int outputX = 350;
    private String path = "";
    private void showHeadImg(View view) {
        BottomDialog.Builder builder = new BottomDialog.Builder(getActivity());
        builder.setTitle(Html.fromHtml("<font color=\"#f9464a\">头像修改</font>"));
        builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
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
//        MyBaseApplication.getApplication().setFilterLock(false);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.toMyInfo) {
            if (!isLogin()) {
                hitSetring();
//                toLogin();
            }
        } else if (requestCode == Common.toLogin) {
            changView();
        } else if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
            imageUri = data.getData();
            cropImageUri(imageUri, outputX, outputX, CROP_BIG_PICTURE);
        } else if (requestCode == TAKE_BIG_PICTURE) {
            imageUri = Uri.parse("file:///" + path);
            cropImageUri(imageUri, outputX, outputX, CROP_BIG_PICTURE);
        } else if (requestCode == CROP_BIG_PICTURE) {
            if (imageUri != null) {
                OkHttpUtils.post().url(Common.Url_Reset_Head)
                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .addParams("uploadType", "img")
                        .addFile("files0_name", StringUtils.getFileName(imageUri.getPath()),
                                new File(imageUri.getPath())).id(Common.NET_RESET_HEAD)
                        .tag(Common.NET_RESET_HEAD).build().execute(new MyStringCallback(getActivity(), this, true));
            }
        } else if (requestCode == PICK_FROM_CAMERA) {
            imageUri = Uri.parse("file:///" + path);
            if (imageUri != null) {
                OkHttpUtils.post().url(Common.Url_Reset_Head)
                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .addParams("uploadType", "img")
                        .addFile("files0_name", StringUtils.getFileName(imageUri.getPath()),
                                new File(imageUri.getPath())).id(Common.NET_RESET_HEAD_TWO)
                        .tag(Common.NET_RESET_HEAD_TWO).build().execute(new MyStringCallback(getActivity(), this, true));
            }
        }
    }

    private void changView() {
        mGetUserIndexModel = MyBaseApplication.mGetMyUserIndexModel;
        UpDateUI();
        OkHttpUtils.get().url(Common.Url_Get_Task)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_RASK).id(Common.NET_GET_RASK).build()
                .execute(new MyStringCallback(getActivity(), this, false));
    }

    //裁减照片
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        if (uri == null) {
            return;
        }
        isHead = true;
        MyBaseApplication.getApplication().setFilterLock(true);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        imageUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }
}
