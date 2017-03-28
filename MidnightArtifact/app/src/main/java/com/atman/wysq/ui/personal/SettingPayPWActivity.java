package com.atman.wysq.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.MD5Util;
import com.base.baselibs.widget.MyCleanEditText;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

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
    private String mobile;
    private String code;
    private String oldPW;
    private String againPW;
    private int typeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingpay_pw);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, String mobile, String code, int typeID) {
        Intent intent = new Intent(context, SettingPayPWActivity.class);
        intent.putExtra("mobile", mobile);
        intent.putExtra("code", code);
        intent.putExtra("typeID", typeID);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("设置支付密码");

        mobile = getIntent().getStringExtra("mobile");
        code = getIntent().getStringExtra("code");
        typeID = getIntent().getIntExtra("typeID", 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_SET_PAY_PW_ID) {
            showToast("支付密码设置成功！");
            finish();
        } else if (id == Common.NET_SET_PAY_PW_FOEGOT_ID) {
            showToast("支付密码重新设置成功！");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        OkHttpUtils.getInstance().cancelTag(Common.NET_SET_PAY_PW_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_SET_PAY_PW_FOEGOT_ID);
    }

    @OnClick({R.id.setting_paypw_next_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_paypw_next_bt:
                oldPW = settingPaypwEt.getText().toString().toString();
                againPW = settingPaypwAgainEt.getText().toString().toString();
                if ((oldPW.isEmpty() || oldPW.length()<6)) {
                    showToast("密码为6位数字");
                    return;
                } else if ((againPW.isEmpty() || againPW.length()<6)) {
                    showToast("密码为6位数字");
                    return;
                } else if (!oldPW.equals(againPW)) {
                    showToast("两次密码输入不一致");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("mobile", mobile);
                map.put("valid_code", code);
                map.put("password", MD5Util.getMD5(againPW));
                String Url = Common.Url_SetPay_PW;
                int reponseId = Common.NET_SET_PAY_PW_ID;
                if (typeID == 2) {
                    Url = Common.Url_SetPay_PW_Forgot;
                    reponseId = Common.NET_SET_PAY_PW_FOEGOT_ID;
                }
                OkHttpUtils.postString().url(Url).content(mGson.toJson(map)).mediaType(Common.JSON)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(reponseId).id(reponseId).build()
                        .execute(new MyStringCallback(mContext, this, true));
                break;
        }
    }
}
