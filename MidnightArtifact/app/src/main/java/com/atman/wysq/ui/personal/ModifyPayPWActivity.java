package com.atman.wysq.ui.personal;

import android.content.Context;
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

public class ModifyPayPWActivity extends MyBaseActivity {

    @Bind(R.id.modify_paypw_old_et)
    MyCleanEditText modifyPaypwOldEt;
    @Bind(R.id.modify_paypw_et)
    MyCleanEditText modifyPaypwEt;
    @Bind(R.id.modify_paypw_again_et)
    MyCleanEditText modifyPaypwAgainEt;

    private Context mContext = ModifyPayPWActivity.this;
    private String onePW;
    private String twoPW;
    private String threePW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_paypw);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("修改支付密码");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_RESET_PAY_PW_ID) {
            showToast("支付密码修改成功！");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_RESET_PAY_PW_ID);
    }

    @OnClick({R.id.modify_paypw_forgot_tv, R.id.setting_paypw_ok_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modify_paypw_forgot_tv:
                startActivity(SettingPayPWForCodeActivity.buildIntent(mContext, 2));
                finish();
                break;
            case R.id.setting_paypw_ok_bt:
                onePW = modifyPaypwOldEt.getText().toString().trim();
                twoPW = modifyPaypwEt.getText().toString().trim();
                threePW = modifyPaypwAgainEt.getText().toString().trim();
                if ((onePW.isEmpty() || onePW.length()<6)) {
                    showToast("密码为6位数字");
                    return;
                } else if ((twoPW.isEmpty() || twoPW.length()<6)) {
                    showToast("密码为6位数字");
                    return;
                } else if ((threePW.isEmpty() || threePW.length()<6)) {
                    showToast("密码为6位数字");
                    return;
                } else if (!twoPW.equals(threePW)) {
                    showToast("两次密码输入不一致");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("old_password", MD5Util.getMD5(onePW));
                map.put("password", MD5Util.getMD5(threePW));
                OkHttpUtils.postString().url(Common.Url_ResetPay_PW).content(mGson.toJson(map))
                        .mediaType(Common.JSON).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_RESET_PAY_PW_ID).id(Common.NET_RESET_PAY_PW_ID).build()
                        .execute(new MyStringCallback(mContext, this, true));
                break;
        }
    }
}
