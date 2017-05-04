package com.atman.wysq.ui.yunxinfriend;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.bean.AddFriendRecord;
import com.atman.wysq.model.bean.TouChuanGiftNotice;
import com.atman.wysq.model.event.YunXinAddFriendEvent;
import com.atman.wysq.model.greendao.gen.AddFriendRecordDao;
import com.atman.wysq.model.response.GetChatTokenModel;
import com.atman.wysq.model.response.GetMyUserIndexModel;
import com.atman.wysq.model.response.GetUserIndexModel;
import com.atman.wysq.ui.PictureBrowsingActivity;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.community.ReportActivity;
import com.atman.wysq.ui.community.ReportListActivity;
import com.atman.wysq.ui.personal.PersonalAlbumActivity;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.CustomImageView;
import com.base.baselibs.widget.PromptDialog;
import com.base.baselibs.widget.RoundImageView;
import com.base.baselibs.widget.adview.ADInfo;
import com.base.baselibs.widget.adview.CycleViewPager;
import com.base.baselibs.widget.adview.ViewFactory;
import com.base.baselibs.widget.pullzoom.PullZoomScrollVIew;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/26 11:45
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class OtherPersonalActivity extends MyBaseActivity implements View.OnClickListener{

    @Bind(R.id.otherpersonal_scrollview)
    PullZoomScrollVIew otherpersonalScrollview;
    @Bind(R.id.otherpersonal_chat_iv)
    ImageView otherpersonalChatIv;

    private Context mContext = OtherPersonalActivity.this;
    private long id;
    private GetUserIndexModel mGetMyUserIndexModel;
    private List<AddFriendRecord> mAddFriendRecord;
    private AddFriendRecordDao mAddFriendRecordDao;

    private LinearLayout otherpersonalBackLl;
    private LinearLayout otherpersonalMoreLl;
    private LinearLayout otherpersonalDynamicLl;
    private LinearLayout otherpersonalVisitorLl;
    private LinearLayout otherpersonalFriendsLl;
    private LinearLayout fragmentOtherImgLl;
    private ImageView fragmentOtherImgIv;
    private ImageView otherpersonalHeadIv;
    private ImageView otherpersonalGenderIv;
    private ImageView otherpersonalHeadVerifyImg;
    private TextView otherpersonalSvipIv;
    private TextView otherpersonalNameTx;
    private TextView otherpersonalVipTx;
    private TextView otherpersonalDynamicNumberTx;
    private TextView otherpersonalVisitorNumTx;
    private TextView otherpersonalOftenaddrTv;
    private TextView otherpersonalRelationshipTv;
    private TextView otherpersonalRelationshipBt;
    private TextView otherpersonalGiftBt;
    private CustomImageView otherpersonalDynmicOneIv, otherpersonalDynmicTwoIv
            , otherpersonalDynmicThreeIv, otherpersonalDynmicFourIv;
    private RoundImageView otherpersonalVisitorOneIv, otherpersonalVisitorTwoIv, otherpersonalVisitorThreeIv;
    private RoundImageView otherpersonalGuardianOneIv, otherpersonalGuardianTwoIv, otherpersonalGuardianThreeIv;
    private ImageView otherpersonalGuardianTopOneIv, otherpersonalGuardianTopTwoIv, otherpersonalGuardianTopThreeIv;
    private RelativeLayout otherpersonalGuardianOneRl,otherpersonalGuardianTwoRl,otherpersonalGuardianThreeRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_otherpersonal);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent (Context context, long id) {
        Intent intent = new Intent(context, OtherPersonalActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        hideTitleBar();
        id = getIntent().getLongExtra("id", -1);
//        id = 450000168;
        LogUtils.e("id:"+id);

        mAddFriendRecordDao = MyBaseApplication.getApplication().getDaoSession().getAddFriendRecordDao();
        mAddFriendRecord = mAddFriendRecordDao.queryBuilder().where(AddFriendRecordDao.Properties.ToRequest.eq(id)
                , AddFriendRecordDao.Properties.FromRequest.eq(PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USERID))).build().list();

        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(getmWidth(), (int) (7.0F * (getmWidth() / 12.0F)));
        otherpersonalScrollview.setHeaderLayoutParams(localObject);

        fragmentOtherImgLl = (LinearLayout) otherpersonalScrollview.findViewById(R.id.fragment_other_img_ll);
        otherpersonalBackLl = (LinearLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_back_ll);
        otherpersonalBackLl.setOnClickListener(this);
        otherpersonalMoreLl = (LinearLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_more_ll);
        otherpersonalMoreLl.setOnClickListener(this);
        otherpersonalDynamicLl = (LinearLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_dynamic_ll);
        otherpersonalDynamicLl.setOnClickListener(this);
        otherpersonalVisitorLl = (LinearLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_visitor_ll);
        otherpersonalVisitorLl.setOnClickListener(this);
        otherpersonalFriendsLl = (LinearLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_friends_ll);
        otherpersonalFriendsLl.setOnClickListener(this);

        fragmentOtherImgIv = (ImageView) otherpersonalScrollview.findViewById(R.id.fragment_other_img_iv);
        otherpersonalHeadIv = (ImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_head_iv);
        otherpersonalGenderIv = (ImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_gender_iv);
        otherpersonalHeadVerifyImg = (ImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_head_verify_img);
        otherpersonalSvipIv = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_svip_iv);
        otherpersonalNameTx = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_name_tx);
        otherpersonalVipTx = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_vip_tx);
        otherpersonalDynamicNumberTx = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_dynamic_number_tx);
        otherpersonalVisitorNumTx = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_visitor_num_tx);
        otherpersonalOftenaddrTv = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_oftenaddr_tv);
        otherpersonalRelationshipTv = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_relationship_tv);
        otherpersonalRelationshipBt = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_relationship_bt);
        otherpersonalRelationshipBt.setOnClickListener(this);
        otherpersonalGiftBt = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_gift_bt);
        otherpersonalGiftBt.setOnClickListener(this);
        fragmentOtherImgIv.setOnClickListener(this);

        otherpersonalDynmicOneIv = (CustomImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_dynmic_one_iv);
        otherpersonalDynmicTwoIv = (CustomImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_dynmic_two_iv);
        otherpersonalDynmicThreeIv = (CustomImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_dynmic_three_iv);
        otherpersonalDynmicFourIv = (CustomImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_dynmic_four_iv);

        otherpersonalVisitorOneIv = (RoundImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_visitor_one_iv);
        otherpersonalVisitorTwoIv = (RoundImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_visitor_two_iv);
        otherpersonalVisitorThreeIv = (RoundImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_visitor_three_iv);

        otherpersonalGuardianOneIv = (RoundImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_one_iv);
        otherpersonalGuardianTwoIv = (RoundImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_two_iv);
        otherpersonalGuardianThreeIv = (RoundImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_three_iv);
        otherpersonalGuardianTopOneIv = (ImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_top_one_iv);
        otherpersonalGuardianTopTwoIv = (ImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_top_two_iv);
        otherpersonalGuardianTopThreeIv = (ImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_top_three_iv);
        otherpersonalGuardianOneRl = (RelativeLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_one_rl);
        otherpersonalGuardianTwoRl = (RelativeLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_two_rl);
        otherpersonalGuardianThreeRl = (RelativeLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_three_rl);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_UserIndex + "/" + id)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_USERINDEX).id(Common.NET_GET_USERINDEX).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private String[] imageUrls;
    private List<ImageView> views = new ArrayList<>();
    private List<ADInfo> infos = new ArrayList<>();
    private CycleViewPager cycleViewPager;
    private void initialize() {
        List<GetUserIndexModel.BodyBean.UserDetailBeanBean.PhotoListBean> temp
                = mGetMyUserIndexModel.getBody().getUserDetailBean().getPhotoList();

        if (temp.size()==0) {
            fragmentOtherImgLl.setVisibility(View.GONE);
            return;
        }
        fragmentOtherImgLl.setVisibility(View.VISIBLE);

        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.fragment_other_img_content);

        imageUrls = new String[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            imageUrls[i] = Common.ImageUrl + temp.get(i).getPic_url();
        }

        infos.clear();
        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }

        views.clear();
        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(mContext, infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            ImageView img = ViewFactory.getImageView(mContext, infos.get(i).getUrl());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views.add(img);
        }
        // 将第一个ImageView添加进来
        ImageView imageView = ViewFactory.getImageView(mContext, infos.get(0).getUrl());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        views.add(imageView);

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);
        cycleViewPager.setScrollable(true);
        cycleViewPager.setWheel(true);
        cycleViewPager.setHitIndicator(true);

        cycleViewPager.setData(views, infos, mAdCycleViewListener);

        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorRight();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
            }
            if (position<0) {
                position = 0;
            }
            toPicWatch(position);
        }

    };

    private void toPicWatch(int p) {
        String imagePath = "";
        for (int i = 0; i < mGetMyUserIndexModel.getBody().getUserDetailBean().getPhotoList().size(); i++) {
            if (i != 0) {
                imagePath += ",";
            }
            imagePath += (Common.ImageUrl + mGetMyUserIndexModel.getBody().getUserDetailBean().getPhotoList().get(i).getPic_url());
        }

        Intent intent = new Intent();
        intent.putExtra("image", imagePath);
        intent.putExtra("num", p);
        intent.setClass(mContext, PictureBrowsingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_USERINDEX) {
            mGetMyUserIndexModel = mGson.fromJson(data, GetUserIndexModel.class);
            updataView();
            initialize();
        } else if (id == Common.NET_ADD_BLACKLIST) {
            showToast("已成功拉黑");
            Intent mIntent = new Intent();
            setResult(RESULT_OK,mIntent);
            finish();
        } else if (id == Common.NET_DLELTE_FRIEND) {
            mGetMyUserIndexModel.getBody().setFriend(false);
            otherpersonalRelationshipBt.setText("关注");
            otherpersonalRelationshipTv.setText("陌生人");
            MyBaseApplication.getApplication().getDaoSession().getAddFriendRecordDao().deleteAll();
        } else if (id == Common.NET_ADD_FOLLOW_ID) {
            showToast("关注成功");
            if (mGetMyUserIndexModel.getBody().getUserFelation()==0) {
                mGetMyUserIndexModel.getBody().setUserFelation(1);
            } else {
                mGetMyUserIndexModel.getBody().setUserFelation(3);
            }
            setViewFelation(mGetMyUserIndexModel.getBody().getUserFelation());
        } else if (id == Common.NET_CANCEL_MYCONCERNLIST_ID) {
            showToast("取消关注成功");
            mGetMyUserIndexModel.getBody().setUserFelation(0);
            otherpersonalRelationshipBt.setText("关注");
            otherpersonalRelationshipTv.setText("陌生人");
            MyBaseApplication.getApplication().getDaoSession().getAddFriendRecordDao().deleteAll();
        } else if (id == Common.NET_ISTABALCK_ID) {
            GetChatTokenModel mGetChatTokenModel = mGson.fromJson(data, GetChatTokenModel.class);
            if (mGetChatTokenModel.getBody().equals("1")) {
                showToast("你已被对方加入黑名单");
                return;
            }
            startActivity(P2PChatActivity.buildIntent(mContext, String.valueOf(this.id)
                    , mGetMyUserIndexModel.getBody().getUserDetailBean().getNickName()
                    , mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex()
                    , mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                    , mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status()));
        } else if (id == Common.NET_CANCEL_BLACKLIST_ID) {
            mGetMyUserIndexModel.getBody().setIsBlack(0);
            showToast("已从黑名单移除");
        }
    }

    private void updataView() {
        otherpersonalNameTx.setText(mGetMyUserIndexModel.getBody().getUserDetailBean().getNickName());
        if (mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level()>=3) {
            otherpersonalNameTx.setTextColor(getResources().getColor(R.color.color_red));
        } else {
            otherpersonalNameTx.setTextColor(getResources().getColor(R.color.color_7F2505));
        }
        otherpersonalVipTx.setText("VIP."+mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getUserLevel());
        if (mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level()>=4) {
            otherpersonalVipTx.setVisibility(View.GONE);
            otherpersonalSvipIv.setVisibility(View.VISIBLE);
        } else {
            otherpersonalSvipIv.setVisibility(View.GONE);
        }
        if (mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("M")) {
            otherpersonalGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            otherpersonalGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }
        if (mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("F")) {
            if (mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status()==1) {//已认证
                otherpersonalGenderIv.setVisibility(View.GONE);
                otherpersonalHeadVerifyImg.setVisibility(View.VISIBLE);
            } else {//没认证
                otherpersonalGenderIv.setVisibility(View.VISIBLE);
                otherpersonalHeadVerifyImg.setVisibility(View.GONE);
            }
        } else {
            otherpersonalHeadVerifyImg.setVisibility(View.GONE);
        }
        ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                ,otherpersonalHeadIv,MyBaseApplication.getApplication().getOptionsNot());

        otherpersonalDynamicNumberTx.setText(""+mGetMyUserIndexModel.getBody().getBlogImageMap().getDataSize());
        initDynamicIV();

        initVisitorIV();

        initguardianIV();

        setViewFelation(mGetMyUserIndexModel.getBody().getUserFelation());

        otherpersonalOftenaddrTv.setText(mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getAround_site());
    }

    private void setViewFelation(int userFelation) {
        if (userFelation==1) {
            otherpersonalRelationshipBt.setText("取消关注");
            otherpersonalRelationshipTv.setText("已关注");
        } else if (userFelation==3) {
            otherpersonalRelationshipBt.setText("取消关注");
            otherpersonalRelationshipTv.setText("互相关注");
        }else if (userFelation==2) {
            otherpersonalRelationshipBt.setText("关注");
            otherpersonalRelationshipTv.setText("粉丝");
        }
    }

    private void initguardianIV() {
        int num = mGetMyUserIndexModel.getBody().getGuardlist().size();
        if (num==1) {
            otherpersonalGuardianOneRl.setVisibility(View.GONE);
            otherpersonalGuardianTwoRl.setVisibility(View.GONE);
            otherpersonalGuardianThreeRl.setVisibility(View.VISIBLE);
            otherpersonalGuardianTopThreeIv.setImageResource(R.mipmap.other_guard_one);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getGuardlist().get(0).getIcon()
                    ,otherpersonalGuardianThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num==2) {
            otherpersonalGuardianOneRl.setVisibility(View.GONE);
            otherpersonalGuardianTwoRl.setVisibility(View.VISIBLE);
            otherpersonalGuardianThreeRl.setVisibility(View.VISIBLE);
            otherpersonalGuardianTopTwoIv.setImageResource(R.mipmap.other_guard_one);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getGuardlist().get(0).getIcon()
                    ,otherpersonalGuardianTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            otherpersonalGuardianTopThreeIv.setImageResource(R.mipmap.other_guard_two);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getGuardlist().get(1).getIcon()
                    ,otherpersonalGuardianThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num>=3) {
            otherpersonalGuardianOneRl.setVisibility(View.VISIBLE);
            otherpersonalGuardianTwoRl.setVisibility(View.VISIBLE);
            otherpersonalGuardianThreeRl.setVisibility(View.VISIBLE);
            otherpersonalGuardianTopOneIv.setImageResource(R.mipmap.other_guard_one);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getGuardlist().get(0).getIcon()
                    ,otherpersonalGuardianOneIv,MyBaseApplication.getApplication().getOptionsNot());
            otherpersonalGuardianTopTwoIv.setImageResource(R.mipmap.other_guard_two);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getGuardlist().get(1).getIcon()
                    ,otherpersonalGuardianTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            otherpersonalGuardianTopThreeIv.setImageResource(R.mipmap.other_guard_three);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getGuardlist().get(2).getIcon()
                    ,otherpersonalGuardianThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        }
    }

    private List<GetUserIndexModel.BodyBean.VisitorMapBean.VisitorListBean> dataList = new ArrayList<>();
    private int num;
    private void initVisitorIV() {
        num = mGetMyUserIndexModel.getBody().getVisitorMap().getVisitorSize();
        dataList.clear();
        for (int i=0;i<mGetMyUserIndexModel.getBody().getVisitorMap().getVisitorList().size();i++) {
            if (mGetMyUserIndexModel.getBody().getVisitorMap().getVisitorList().get(i).getUser_id()!= id) {
                dataList.add(mGetMyUserIndexModel.getBody().getVisitorMap().getVisitorList().get(i));
            } else {
                num -= 1;
            }
        }
        otherpersonalVisitorNumTx.setText(""+num);
        if (num==1) {
            otherpersonalVisitorOneIv.setVisibility(View.GONE);
            otherpersonalVisitorTwoIv.setVisibility(View.GONE);
            otherpersonalVisitorThreeIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(0).getIcon()
                    ,otherpersonalVisitorThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num==2) {
            otherpersonalVisitorOneIv.setVisibility(View.GONE);
            otherpersonalVisitorTwoIv.setVisibility(View.VISIBLE);
            otherpersonalVisitorThreeIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(0).getIcon()
                    ,otherpersonalVisitorTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(1).getIcon()
                    ,otherpersonalVisitorThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num>=3) {
            otherpersonalVisitorOneIv.setVisibility(View.VISIBLE);
            otherpersonalVisitorTwoIv.setVisibility(View.VISIBLE);
            otherpersonalVisitorThreeIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(0).getIcon()
                    ,otherpersonalVisitorOneIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(1).getIcon()
                    ,otherpersonalVisitorTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+dataList.get(2).getIcon()
                    ,otherpersonalVisitorThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        }
    }

    private void initDynamicIV() {
        int num = mGetMyUserIndexModel.getBody().getBlogImageMap().getDataList().size();
        if (num==1) {
            otherpersonalDynmicOneIv.setVisibility(View.GONE);
            otherpersonalDynmicTwoIv.setVisibility(View.GONE);
            otherpersonalDynmicThreeIv.setVisibility(View.GONE);
            otherpersonalDynmicFourIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getBlogImageMap().getDataList().get(0)
                    ,otherpersonalDynmicFourIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num==2) {
            otherpersonalDynmicOneIv.setVisibility(View.GONE);
            otherpersonalDynmicTwoIv.setVisibility(View.GONE);
            otherpersonalDynmicThreeIv.setVisibility(View.VISIBLE);
            otherpersonalDynmicFourIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getBlogImageMap().getDataList().get(1)
                    ,otherpersonalDynmicThreeIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getBlogImageMap().getDataList().get(0)
                    ,otherpersonalDynmicFourIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num==3) {
            otherpersonalDynmicOneIv.setVisibility(View.GONE);
            otherpersonalDynmicTwoIv.setVisibility(View.VISIBLE);
            otherpersonalDynmicThreeIv.setVisibility(View.VISIBLE);
            otherpersonalDynmicFourIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getBlogImageMap().getDataList().get(2)
                    ,otherpersonalDynmicTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getBlogImageMap().getDataList().get(1)
                    ,otherpersonalDynmicThreeIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getBlogImageMap().getDataList().get(0)
                    ,otherpersonalDynmicFourIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num>=4) {
            otherpersonalDynmicOneIv.setVisibility(View.VISIBLE);
            otherpersonalDynmicTwoIv.setVisibility(View.VISIBLE);
            otherpersonalDynmicThreeIv.setVisibility(View.VISIBLE);
            otherpersonalDynmicFourIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getBlogImageMap().getDataList().get(3)
                    ,otherpersonalDynmicOneIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getBlogImageMap().getDataList().get(2)
                    ,otherpersonalDynmicTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getBlogImageMap().getDataList().get(1)
                    ,otherpersonalDynmicThreeIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetMyUserIndexModel.getBody().getBlogImageMap().getDataList().get(0)
                    ,otherpersonalDynmicFourIv,MyBaseApplication.getApplication().getOptionsNot());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_USERINDEX);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_BLACKLIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CANCEL_BLACKLIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ISTABALCK_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_DLELTE_FRIEND);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CANCEL_MYCONCERNLIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_FOLLOW_ID);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(com.base.baselibs.R.anim.activity_bottom_in, com.base.baselibs.R.anim.activity_bottom_out);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.toSelectGift) {
            String text = data.getStringExtra("text");
            String giftName = data.getStringExtra("name");
            String giftPic = data.getStringExtra("giftPic");
            int price = data.getIntExtra("price", 0);
            File file = ImageLoader.getInstance().getDiskCache().get(Common.ImageUrl+data.getStringExtra("url"));

            // 构造自定义通知，指定接收者
            CustomNotification notification = new CustomNotification();
            notification.setSessionId(String.valueOf(id));
            notification.setSessionType(SessionTypeEnum.P2P);
            // 构建通知的具体内容。为了可扩展性，这里采用 json 格式，以 "id" 作为类型区分。
            // 这里以类型 “1” 作为“正在输入”的状态通知。
            TouChuanGiftNotice mTouChuanGiftNotice = new TouChuanGiftNotice();
            mTouChuanGiftNotice.setGiftContent(text);
            mTouChuanGiftNotice.setGiftSendName(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getNickName());
            mTouChuanGiftNotice.setGiftPrice(price);
            mTouChuanGiftNotice.setType(8);
            mTouChuanGiftNotice.setGiftIcon(giftPic);
            notification.setContent(mGson.toJson(mTouChuanGiftNotice));
            // 发送自定义通知
            NIMClient.getService(MsgService.class).sendCustomNotification(notification);
            showToast("赠送礼物["+giftName+"]成功！");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onMessageEvent(YunXinAddFriendEvent event) {//参数必须是ClassEvent类型, 否则不会调用此方法
        if (event.id==2) {
            otherpersonalRelationshipBt.setText("删除");
            otherpersonalRelationshipTv.setText("好友");
            mGetMyUserIndexModel.getBody().setFriend(true);
        }
    }

    @OnClick({R.id.otherpersonal_chat_iv})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.otherpersonal_gift_bt:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                if (id==MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId()) {
                    showWraning("亲，这是你自己哦！");
                    return;
                }
                startActivityForResult(SelectGiftActivity.buildIntent(mContext, String.valueOf(id), false, 0), Common.toSelectGift);
                break;
            case R.id.otherpersonal_relationship_bt:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                if (id==MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId()) {
                    showWraning("亲，这是你自己哦！");
                    return;
                }
                if (mGetMyUserIndexModel.getBody().getUserFelation()==1
                        || mGetMyUserIndexModel.getBody().getUserFelation()==3) {
                    PromptDialog.Builder builder = new PromptDialog.Builder(this);
                    builder.setMessage("确定取消关注TA吗?");
                    builder.setPositiveButton("不了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消关注", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            OkHttpUtils.postString()
                                    .url(Common.Url_Cancel_MyConcernList + id)
                                    .tag(Common.NET_CANCEL_MYCONCERNLIST_ID).id(Common.NET_CANCEL_MYCONCERNLIST_ID)
                                    .content(mGson.toJson(""))
                                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                    .build().execute(new MyStringCallback(mContext, OtherPersonalActivity.this, true));
                        }
                    });
                    builder.show();
//                    PromptDialog.Builder builder = new PromptDialog.Builder(this);
//                    builder.setMessage("您确定要删除好友吗？");
//                    builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            OkHttpUtils.delete().url(Common.Url_Delete_Friends+id)
//                                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
//                                    .tag(Common.NET_DLELTE_FRIEND).id(Common.NET_DLELTE_FRIEND).build()
//                                    .execute(new MyStringCallback(mContext, OtherPersonalActivity.this, true));
//                        }
//                    });
//                    builder.show();
                } else {
                    Map<String, Long> p = new HashMap<>();
                    p.put("follow_user_id", id);
                    OkHttpUtils.postString().url(Common.Url_Add_Follow).content(mGson.toJson(p))
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .mediaType(Common.JSON)
                            .tag(Common.NET_ADD_FOLLOW_ID).id(Common.NET_ADD_FOLLOW_ID).build()
                            .execute(new MyStringCallback(mContext, OtherPersonalActivity.this, true));
//                    if (mAddFriendRecord.size()>0) {
//                        showToast("已请求添加\""+mGetMyUserIndexModel.getBody().getUserDetailBean().getNickName()+"\"为好友");
//                        return;
//                    }
//                    // 构造自定义通知，指定接收者
//                    CustomNotification notification = new CustomNotification();
//                    notification.setSessionId(String.valueOf(id));
//                    notification.setSessionType(SessionTypeEnum.P2P);
//                    // 构建通知的具体内容。为了可扩展性，这里采用 json 格式，以 "id" 作为类型区分。
//                    // 这里以类型 “1” 作为“正在输入”的状态通知。
//                    TouChuanOtherNotice mTouChuanOtherNotice = new TouChuanOtherNotice();
//                    mTouChuanOtherNotice.setNoticeType(1);
//                    mTouChuanOtherNotice.setSend_nickName(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getNickName());
//                    mTouChuanOtherNotice.setSend_userId(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId());
//                    mTouChuanOtherNotice.setReceive_nickName(mGetMyUserIndexModel.getBody().getUserDetailBean().getNickName());
//                    mTouChuanOtherNotice.setReceive_userId(mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId());
//                    mTouChuanOtherNotice.setTime(String.valueOf(System.currentTimeMillis()));
//                    mTouChuanOtherNotice.setAddfriendType(1);
//                    notification.setContent(mGson.toJson(mTouChuanOtherNotice));
//                    // 发送自定义通知
//                    NIMClient.getService(MsgService.class).sendCustomNotification(notification);
//
//                    showToast("已请求添加\""+mGetMyUserIndexModel.getBody().getUserDetailBean().getNickName()+"\"为好友");
//                    AddFriendRecord temp = new AddFriendRecord(null, String.valueOf(id)
//                            , PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USERID));
//                    mAddFriendRecordDao.insertOrReplace(temp);
                }
                break;
            case R.id.otherpersonal_back_ll:
                finish();
                break;
            case R.id.otherpersonal_visitor_ll:
                if (!isLogin() || MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level()<1) {
                    showWraning("VIP.1以上用户才有权限查看");
                    return;
                }
                startActivity(HisVisitorActivity.buildIntent(mContext, id, "TA的访客", num));
                break;
            case R.id.otherpersonal_friends_ll:
                if (!isLogin() || MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level()<2) {
                    showWraning("VIP.2以上用户才有权限查看");
                    return;
                }
                startActivity(HisGuardianActivity.buildIntent(mContext, id, "TA的守护者"));
                break;
            case R.id.otherpersonal_dynamic_ll:
                startActivity(HisDynamicsActivity.buildIntent(mContext, id));
                break;
            case R.id.otherpersonal_more_ll:
                showBottomImg();
                break;
            case R.id.otherpersonal_chat_iv:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                if (id==MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId()) {
                    showWraning("亲，这是你自己哦！");
                    return;
                }
                OkHttpUtils.get().url(Common.Url_IsTaBalck + "/" + id)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_ISTABALCK_ID).id(Common.NET_ISTABALCK_ID).build()
                        .execute(new MyStringCallback(mContext, this, true));
                break;
            case R.id.fragment_other_img_iv:
                break;
        }
    }

    private void showBottomImg() {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        String[] str = new String[]{"举报", "把TA加入黑名单"};
        if (mGetMyUserIndexModel.getBody().getIsBlack()==1) {
            str[1] = "从黑名单移除";
        }
        builder.setItems(str, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                if (which == 0) {//举报
                    startActivity(ReportListActivity.buildIntent(mContext, id, 1));
//                    startActivity(ReportActivity.buildIntent(mContext, id, 1));
                } else if (which == 1) {//把TA加入黑名单
                    if (mGetMyUserIndexModel.getBody().getIsBlack()==1) {
                        OkHttpUtils.postString()
                                .url(Common.Url_Cancel_BlackList + id)
                                .tag(Common.NET_CANCEL_BLACKLIST_ID).id(Common.NET_CANCEL_BLACKLIST_ID)
                                .content(mGson.toJson(""))
                                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                .build().execute(new MyStringCallback(mContext, OtherPersonalActivity.this, true));
                    } else {
                        OkHttpUtils.postString().url(Common.Url_Add_BlackList)
                                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                .content("{\"black_user_id\":" + id + "}")
                                .mediaType(Common.JSON).id(Common.NET_ADD_BLACKLIST).tag(Common.NET_ADD_BLACKLIST)
                                .build().execute(new MyStringCallback(mContext, OtherPersonalActivity.this, true));
                    }
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
}
