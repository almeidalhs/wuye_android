package com.atman.wysq.ui.personal.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.util.TimeCount;
import com.base.baselibs.widget.MyCleanEditText;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/3/27.
 */

public class SettingPayPWForCodeActivity extends MyBaseActivity {

    @Bind(R.id.setting_paypw_code_phone_et)
    MyCleanEditText settingPaypwCodePhoneEt;
    @Bind(R.id.setting_paypw_code_et)
    MyCleanEditText settingPaypwCodeEt;
    @Bind(R.id.setting_paypw_code_tx)
    TextView settingPaypwCodeTx;

    private Context mContext = SettingPayPWForCodeActivity.this;
    private String mPhoneNumber;
    private int typeID = 1;
    private TimeCount timeCount;
    private String codeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingpay_pw_for_code);
        ButterKnife.bind(this);

        timeCount = new TimeCount(settingPaypwCodeTx, 2 * 60 * 1000, 1000, settingPaypwCodePhoneEt);
    }

    public static Intent buildIntent (Context context, int typeID) {
        Intent intent = new Intent(context, SettingPayPWForCodeActivity.class);
        intent.putExtra("typeID", typeID);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("设置支付密码");


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_SEED_PAY_MEESAGE_ID) {
            showToast("验证码已发送，请注意查收！");
            timeCount.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        OkHttpUtils.getInstance().cancelTag(Common.NET_SEED_PAY_MEESAGE_ID);
    }

    @OnClick({R.id.setting_paypw_code_tx, R.id.setting_paypw_code_bt
            , R.id.setting_paypw_code_default_phone_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_paypw_code_tx:
                mPhoneNumber = settingPaypwCodePhoneEt.getText().toString().trim();
                if (mPhoneNumber.isEmpty()) {
                    showToast("请输入手机号");
                    return;
                } else if (!StringUtils.isPhone(mPhoneNumber)) {
                    showToast("请输入正确的手机号");
                    return;
                }
                OkHttpUtils.post().url(Common.Url_SeedPayMessage + mPhoneNumber + "/" + typeID)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_SEED_PAY_MEESAGE_ID).id(Common.NET_SEED_PAY_MEESAGE_ID).build()
                        .execute(new MyStringCallback(mContext, this, true));
                break;
            case R.id.setting_paypw_code_bt:
                codeStr = settingPaypwCodeEt.getText().toString().trim();
                if (codeStr.isEmpty()) {
                    showToast("请输入验证码");
                    return;
                } else if (codeStr.length()!=6) {
                    showToast("请输入正确的验证码");
                    return;
                }
                startActivity(SettingPayPWActivity.buildIntent(mContext, mPhoneNumber, codeStr, typeID));
                finish();
                break;
            case R.id.setting_paypw_code_default_phone_tv:
                settingPaypwCodePhoneEt.setText(MyBaseApplication.mGetMyUserIndexModel.getBody()
                        .getUserDetailBean().getUserExt().getMobile());
                break;
        }
    }
}
