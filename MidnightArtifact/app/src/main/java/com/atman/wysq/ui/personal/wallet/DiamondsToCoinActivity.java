package com.atman.wysq.ui.personal.wallet;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.widget.PayPassWordDialog;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.MD5Util;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.PromptDialog;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/3/27.
 */

public class DiamondsToCoinActivity extends MyBaseActivity implements EditCheckBack
        , PayPassWordDialog.onPayPassWordListener {

    @Bind(R.id.dismondstocoin_dismonds_num_tv)
    TextView dismondstocoinDismondsNumTv;
    @Bind(R.id.dismondstocoin_coin_num_tv)
    TextView dismondstocoinCoinNumTv;
    @Bind(R.id.dismondstocoin_num_et)
    MyCleanEditText dismondstocoinNumEt;
    @Bind(R.id.dismondstocoin_consume_tv)
    TextView dismondstocoinConsumeTv;

    private Context mContext = DiamondsToCoinActivity.this;
    private long diamondNum;
    private long toCoinNum;
    private long inputCoinNum;
    private long consumeDiamondNum;
    private String[] str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diamondstocoin);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("钻石兑换金币");
        diamondNum = MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getConvert_coin();
        toCoinNum = countToCoin(diamondNum);

        dismondstocoinNumEt.addTextChangedListener(new MyTextWatcherTwo(this));
        setTextStr();
    }

    private void setTextStr() {
        dismondstocoinDismondsNumTv.setText("" + diamondNum);
        dismondstocoinCoinNumTv.setText("" + toCoinNum);
    }

    private long countToCoin(long diamondNum) {
        long Num = 0;
        str = MyBaseApplication.KDiamondChargeCoin.split(":");
        if (str.length == 2 && diamondNum > 0) {
            Num = diamondNum * Integer.valueOf(str[1]) / Integer.valueOf(str[0]);
        }
        return Num;
    }

    private long countToDiamonds(long coinNum) {
        long Num = 0;
        str = MyBaseApplication.KDiamondChargeCoin.split(":");
        if (str.length == 2 && coinNum > 0) {
            Num = coinNum * Integer.valueOf(str[0]) / Integer.valueOf(str[1]);
        }
        return Num;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_DIAMONDS_TO_COIN_ID) {
            showToast("兑换成功");
            diamondNum = diamondNum - consumeDiamondNum;
            toCoinNum = toCoinNum + inputCoinNum;
            MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().setConvert_coin((int) diamondNum);
            MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt()
                    .setLeft_coin((int) (MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean()
                            .getUserExt().getLeft_coin() + inputCoinNum));
            setTextStr();
            dismondstocoinNumEt.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        OkHttpUtils.getInstance().cancelTag(Common.NET_DIAMONDS_TO_COIN_ID);
    }

    @OnClick({R.id.dismondstocoin_ok_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dismondstocoin_ok_bt:
                final String str = dismondstocoinNumEt.getText().toString().trim();
                if (str.isEmpty()) {
                    showToast("请输入要兑换的金币数");
                    return;
                } else if (Integer.valueOf(str) == 0) {
                    showToast("请输入的金币数不能为0");
                    return;
                }

                new PayPassWordDialog(mContext, this).show();
                break;
        }
    }

    @Override
    public void inputFinish(String pw) {
        Map<String, Object> map = new HashMap<>();
        map.put("diamond", consumeDiamondNum);
        map.put("wallet_ps", MD5Util.getMD5(pw));
        OkHttpUtils.postString().url(Common.Url_DiamondsToCoin)
                .tag(Common.NET_DIAMONDS_TO_COIN_ID).content(mGson.toJson(map))
                .mediaType(Common.JSON).id(Common.NET_DIAMONDS_TO_COIN_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .build().execute(new MyStringCallback(mContext, DiamondsToCoinActivity.this, true));
    }

    @Override
    public void cancelDialog() {

    }

    @Override
    public void forgetPassWord() {
        startActivity(SettingPayPWForCodeActivity.buildIntent(mContext, 2));
    }

    @Override
    public void isNull(boolean isNull) {
        if (isNull) {
            dismondstocoinConsumeTv.setVisibility(View.INVISIBLE);
        } else {
            dismondstocoinConsumeTv.setVisibility(View.VISIBLE);

            inputCoinNum = Long.parseLong(dismondstocoinNumEt.getText().toString().trim());
            consumeDiamondNum = countToDiamonds(inputCoinNum);

            if (consumeDiamondNum > diamondNum) {
                dismondstocoinNumEt.setText(""+toCoinNum);
                return;
            }

            dismondstocoinConsumeTv.setText("本次兑换将消耗您"+consumeDiamondNum+"钻石");
            dismondstocoinNumEt.setSelection((String.valueOf(inputCoinNum)).length());
        }
    }
}
