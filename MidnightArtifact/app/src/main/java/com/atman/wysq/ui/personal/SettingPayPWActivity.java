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

public class SettingPayPWActivity extends MyBaseActivity {

    @Bind(R.id.setting_paypw_et)
    MyCleanEditText settingPaypwEt;
    @Bind(R.id.setting_paypw_again_et)
    MyCleanEditText settingPaypwAgainEt;
    private Context mContext = SettingPayPWActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingpay_pw);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, String pw) {
        Intent intent = new Intent(context, SettingPayPWActivity.class);
        intent.putExtra("pw", pw);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, com.base.baselibs.R.anim.slide_right_out);
    }

    @OnClick({R.id.setting_paypw_next_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_paypw_next_bt:
                finish();
                break;
        }
    }
}
