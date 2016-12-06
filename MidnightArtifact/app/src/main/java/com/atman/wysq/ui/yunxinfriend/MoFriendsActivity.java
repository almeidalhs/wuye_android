package com.atman.wysq.ui.yunxinfriend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetFansNumModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.personal.AddressListInvitationActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.UiHelper;
import com.base.baselibs.net.MyStringCallback;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/25 17:19
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MoFriendsActivity extends MyBaseActivity {

    @Bind(R.id.mofriends_myattion_num_tv)
    TextView mofriendsMyattionNumTv;
    @Bind(R.id.mofriends_myfans_num_tv)
    TextView mofriendsMyfansNumTv;

    private Context mContext = MoFriendsActivity.this;
    private GetFansNumModel mGetFansNumModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mofriends);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("夜友");

        setBarRightIv(R.mipmap.message_top_right_ic, true);
        getBarRightRl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    showLogin();
                } else {
                    if (!UiHelper.isTabletDevice(mContext)) {
                        startActivity(new Intent(mContext, AddressListInvitationActivity.class));
                    } else {
                        showToast("你的设备暂不支持通讯录功能。");
                    }
                }
            }
        });
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        OkHttpUtils.get().url(Common.Url_Get_FansNum)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_FANSNUM_ID).id(Common.NET_GET_FANSNUM_ID).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_FANSNUM_ID) {
            mGetFansNumModel = mGson.fromJson(data, GetFansNumModel.class);

            mofriendsMyattionNumTv.setText(""+mGetFansNumModel.getBody().getFollows());
            mofriendsMyfansNumTv.setText(""+mGetFansNumModel.getBody().getFans());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_FANSNUM_ID);
    }

    @OnClick({R.id.mofriends_myattion_ll, R.id.mofriends_myfans_ll, R.id.mofriends_blacklist_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mofriends_myattion_ll:
                startActivity(new Intent(mContext, MyConcernActivity.class));
                break;
            case R.id.mofriends_myfans_ll:
                startActivity(new Intent(mContext, MyFansActivity.class));
                break;
            case R.id.mofriends_blacklist_ll:
                startActivity(new Intent(mContext, BlackListActivity.class));
                break;
        }
    }
}
