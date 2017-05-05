package com.atman.wysq.ui.personal.wallet;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetWithdrawalsListModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.personal.AliPayAccountsBindActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.widget.PayPassWordDialog;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.MD5Util;
import com.base.baselibs.widget.MyCleanEditText;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/3/28.
 */

public class WithdrawCashActivity extends MyBaseActivity implements EditCheckBack
        , PayPassWordDialog.onPayPassWordListener {

    @Bind(R.id.withdraw_dismonds_num_tv)
    TextView withdrawDismondsNumTv;
    @Bind(R.id.withdraw_account_tv)
    TextView withdrawAccountTv;
    @Bind(R.id.withdraw_account_monify_tv)
    TextView withdrawAccountMonifyTv;
    @Bind(R.id.withdraw_account_ll)
    LinearLayout withdrawAccountLl;
    @Bind(R.id.withdraw_num_et)
    MyCleanEditText withdrawNumEt;
    @Bind(R.id.withdraw_consume_tv)
    TextView withdrawConsumeTv;
    @Bind(R.id.withdraw_wran_tv)
    TextView withdrawWranTv;
    @Bind(R.id.withdraw_sumbit_bt)
    Button withdrawSumbitBt;

    private Context mContext = WithdrawCashActivity.this;
    private long ownDiamonds = 0;
    private long consumeDiamonds = 0;
    private long inputMoney = 0;
    private boolean isnull = true;
    private long walletId = 0;

    private GetWithdrawalsListModel mGetWithdrawalsListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawcash);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("钻石提现");

        setBarRightTx("明细").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RecordDetailActivity.buildIntent(mContext, 1));
            }
        });

        withdrawWranTv.setText("提示：单次提现最低金额" + MyBaseApplication.KDiamondCashStart
                + "元，不满200元将收取3元手续费，提现转账需1-3个工作日。");

        ownDiamonds = MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean()
                .getUserExt().getConvert_coin();

        initThisView();
    }

    private void initThisView() {

        //钻石转换小于50元的不让输入
        if (countToDiamonds(Long.parseLong(MyBaseApplication.KDiamondCashStart)) > ownDiamonds) {
            withdrawNumEt.setInputType(InputType.TYPE_NULL);
            withdrawSumbitBt.setClickable(false);
            withdrawSumbitBt.setEnabled(false);
            withdrawConsumeTv.setText("您的钻石余额不足，单次最低提现"+MyBaseApplication.KDiamondCashStart
                    +"元（等于"+countToDiamonds(Long.parseLong(MyBaseApplication.KDiamondCashStart))+"钻石）");
            withdrawConsumeTv.setTextColor(getResources().getColor(R.color.color_red));
        } else {
            withdrawNumEt.addTextChangedListener(new MyTextWatcherTwo(this));
            withdrawNumEt.setInputType(InputType.TYPE_CLASS_NUMBER);
            withdrawSumbitBt.setClickable(true);
            withdrawSumbitBt.setEnabled(true);
            withdrawConsumeTv.setTextColor(getResources().getColor(R.color.color_787878));
            setConsumeText();
        }
    }

    private void setConsumeText() {
        String[] str = MyBaseApplication.KDiamondCash.split(":");
        if (str.length == 2) {
            withdrawConsumeTv.setText("每" + str[0] + "钻石兑换" + str[1] + "元人民币");
        }
    }

    private long countToDiamonds(long money) {
        long Num = 0;
        String[] str = MyBaseApplication.KDiamondCash.split(":");
        if (str.length == 2 && ownDiamonds > 0) {
            Num = money * Integer.valueOf(str[0]) / Integer.valueOf(str[1]);
        }
        return Num;
    }

    private long countToCash(long ownDiamonds) {
        long Num = 0;
        String[] str = MyBaseApplication.KDiamondCash.split(":");
        if (str.length == 2 && ownDiamonds > 0) {
            Num = ownDiamonds * Integer.valueOf(str[1]) / Integer.valueOf(str[0]);
        }
        return Num;
    }

    @Override
    protected void onResume() {
        super.onResume();
        withdrawDismondsNumTv.setText("" + ownDiamonds);

        OkHttpUtils.get().url(Common.Url_Get_Withdrawals_List)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_WITHDRAEALS_LIST_ID).id(Common.NET_GET_WITHDRAEALS_LIST_ID)
                .build().execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_WITHDRAEALS_LIST_ID) {
            initThisView();
            mGetWithdrawalsListModel = mGson.fromJson(data, GetWithdrawalsListModel.class);
            if (mGetWithdrawalsListModel.getBody().size() > 0
                    && mGetWithdrawalsListModel.getBody().get(0).getAccount()!=null
                    && !mGetWithdrawalsListModel.getBody().get(0).getAccount().equals("")) {
                walletId = mGetWithdrawalsListModel.getBody().get(0).getWallet_channel_id();
                withdrawAccountMonifyTv.setText("去修改");
                withdrawAccountTv.setText("提现到账号：" + mGetWithdrawalsListModel.getBody().get(0).getAccount());
            } else {
                withdrawNumEt.setInputType(InputType.TYPE_NULL);
                withdrawSumbitBt.setClickable(false);
                withdrawSumbitBt.setEnabled(false);
            }
        } else if (id == Common.NET_CASH_ID) {
            showWraning("您的提现申请已提交，请耐心等候！");
            ownDiamonds = MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean()
                    .getUserExt().getConvert_coin();
            MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean()
                    .getUserExt().setConvert_coin((int) (ownDiamonds - consumeDiamonds));
            withdrawDismondsNumTv.setText("" + (ownDiamonds - consumeDiamonds));
            withdrawNumEt.setText("");
            ownDiamonds = ownDiamonds - consumeDiamonds;
            initThisView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_WITHDRAEALS_LIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CASH_ID);
    }

    @OnClick({R.id.withdraw_account_monify_tv, R.id.withdraw_sumbit_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.withdraw_account_monify_tv:
                if (withdrawAccountMonifyTv.getText().toString().equals("去修改")) {
                    startActivity(AliPayAccountsBindActivity.buildIntent(mContext, 1));
                } else {
                    startActivity(AliPayAccountsBindActivity.buildIntent(mContext, 0));
                }
                break;
            case R.id.withdraw_sumbit_bt:
                if (isnull) {
                    showToast("请输入提现金额");
                    return;
                }
                if (inputMoney==0 || inputMoney<Integer.valueOf(MyBaseApplication.KDiamondCashStart)) {
                    showToast("提现金额最少"+MyBaseApplication.KDiamondCashStart);
                    return;
                }
                new PayPassWordDialog(mContext, this).show();
                break;
        }
    }

    @Override
    public void isNull(boolean isNull) {
        if (isNull) {
            isnull = true;
            setConsumeText();
        } else {
            isnull = false;
            inputMoney = Integer.valueOf(withdrawNumEt.getText().toString().trim());
            consumeDiamonds = countToDiamonds(inputMoney);
            if (consumeDiamonds > ownDiamonds) {
                withdrawNumEt.setText("" + countToCash(ownDiamonds));
            } else {
                withdrawConsumeTv.setText("本次提现将消耗您" + consumeDiamonds + "钻石");
            }
            withdrawNumEt.setSelection((String.valueOf(inputMoney)).length());
        }
    }

    @Override
    public void inputFinish(String pw) {
        Map<String, Object> map = new HashMap<>();
        map.put("diamond", consumeDiamonds);
        map.put("wallet_id", walletId);
        map.put("wallet_ps", MD5Util.getMD5(pw));
        LogUtils.e(">>>>mGson.toJson(map):"+mGson.toJson(map));
        OkHttpUtils.postString().url(Common.Url_Cash_PW).content(mGson.toJson(map))
                .mediaType(Common.JSON).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_CASH_ID).id(Common.NET_CASH_ID).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    public void cancelDialog() {

    }

    @Override
    public void forgetPassWord() {
        startActivity(SettingPayPWForCodeActivity.buildIntent(mContext, 2));
    }
}
