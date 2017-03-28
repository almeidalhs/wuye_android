package com.atman.wysq.ui.personal.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.login.CoinAgreementActivity;
import com.atman.wysq.ui.personal.AliPayAccountsBindActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/3/24.
 */

public class WalletSettingsActivity extends MyBaseActivity {

    private Context mContext = WalletSettingsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walletsettings);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("钱包设置");
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

    @OnClick({R.id.walletsetting_reset_pw_ll, R.id.walletsetting_modify_pw_ll, R.id.walletsetting_bind_alipay_ll, R.id.walletsetting_about_cion_ll, R.id.walletsetting_about_diamonds_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.walletsetting_reset_pw_ll:
                startActivity(SettingPayPWForCodeActivity.buildIntent(mContext, 1));
                break;
            case R.id.walletsetting_modify_pw_ll:
                startActivity(new Intent(mContext, ModifyPayPWActivity.class));
                break;
            case R.id.walletsetting_bind_alipay_ll:
                startActivity(AliPayAccountsBindActivity.buildIntent(mContext, 0));
                break;
            case R.id.walletsetting_about_cion_ll:
                startActivity(CoinAgreementActivity.buildIntent(mContext, 0));
                break;
            case R.id.walletsetting_about_diamonds_ll:
                startActivity(CoinAgreementActivity.buildIntent(mContext, 1));
                break;
        }
    }
}
