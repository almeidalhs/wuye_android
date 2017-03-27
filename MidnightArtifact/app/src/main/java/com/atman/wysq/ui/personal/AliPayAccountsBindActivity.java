package com.atman.wysq.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetWithdrawalsListModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
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

public class AliPayAccountsBindActivity extends MyBaseActivity {

    @Bind(R.id.alipay_accounts_et)
    MyCleanEditText alipayAccountsEt;

    private Context mContext = AliPayAccountsBindActivity.this;
    private String alipayAccount = "";
    private GetWithdrawalsListModel mGetWithdrawalsListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipayaccountsbind);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, String alipayAccount) {
        Intent intent = new Intent(context, AliPayAccountsBindActivity.class);
        intent.putExtra("alipayAccount", alipayAccount);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("支付宝帐号");

        alipayAccount = getIntent().getStringExtra("alipayAccount");
        alipayAccountsEt.setText(alipayAccount);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_Withdrawals_List)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_WITHDRAEALS_LIST_ID).id(Common.NET_GET_WITHDRAEALS_LIST_ID)
                .build().execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_WITHDRAEALS_LIST_ID) {
            mGetWithdrawalsListModel = mGson.fromJson(data, GetWithdrawalsListModel.class);
            if (mGetWithdrawalsListModel.getBody().size() > 0) {
                alipayAccountsEt.setText(mGetWithdrawalsListModel.getBody().get(0).getAccount());
            }
        } else if (id == Common.NET_ADD_WITHDRAEALS_ACCOUNT_ID) {
            showToast("添加成功");
        } else if (id == Common.NET_ADD_WITHDRAEALS_ACCOUNT_ID) {
            showToast("修改成功");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_WITHDRAEALS_LIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_WITHDRAEALS_ACCOUNT_ID);
    }

    @OnClick({R.id.alipay_accounts_sumbit_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alipay_accounts_sumbit_bt:
                String account = alipayAccountsEt.getText().toString().trim();
                if (account.isEmpty()) {
                    showToast("请输入提现帐号");
                    return;
                }
                if (mGetWithdrawalsListModel.getBody().size() == 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("account", account);
                    map.put("account_name", "支付宝");
                    map.put("channel", 1);
                    map.put("flag", 1);
                    OkHttpUtils.postString().url(Common.Url_Add_Withdrawals_Account)
                            .tag(Common.NET_ADD_WITHDRAEALS_ACCOUNT_ID).content(mGson.toJson(map))
                            .mediaType(Common.JSON).id(Common.NET_ADD_WITHDRAEALS_ACCOUNT_ID)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .build().execute(new MyStringCallback(mContext, AliPayAccountsBindActivity.this, true));
                }
                break;
        }
    }
}
