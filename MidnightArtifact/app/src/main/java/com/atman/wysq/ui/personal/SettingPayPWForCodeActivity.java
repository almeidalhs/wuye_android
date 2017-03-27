package com.atman.wysq.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.base.baselibs.widget.MyCleanEditText;

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

    private Context mContext = SettingPayPWForCodeActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingpay_pw_for_code);
        ButterKnife.bind(this);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.setting_paypw_code_tx, R.id.setting_paypw_code_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_paypw_code_tx:
                break;
            case R.id.setting_paypw_code_bt:
                startActivity(SettingPayPWActivity.buildIntent(mContext, ""));
                break;
        }
    }
}
