package com.atman.wysq.ui.personal;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.PromptDialog;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/3/27.
 */

public class DiamondsToCoinActivity extends MyBaseActivity {

    @Bind(R.id.dismondstocoin_dismonds_num_tv)
    TextView dismondstocoinDismondsNumTv;
    @Bind(R.id.dismondstocoin_coin_num_tv)
    TextView dismondstocoinCoinNumTv;
    @Bind(R.id.dismondstocoin_num_et)
    MyCleanEditText dismondstocoinNumEt;

    private Context mContext = DiamondsToCoinActivity.this;
    private long diamondNum;
    private long toCoinNum;
    private String[] str;
    private long exchangeDiamonds = 0;
    private long getCoin = 0;

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

        setTextStr();
    }

    private void setTextStr() {
        dismondstocoinDismondsNumTv.setText(""+diamondNum);
        dismondstocoinCoinNumTv.setText(""+toCoinNum);
    }

    private long countToCoin(long diamondNum) {
        long Num = 0;
        str = MyBaseApplication.KDiamondChargeCoin.split(":");
        if (str.length==2 && diamondNum>0) {
            Num = diamondNum*Integer.valueOf(str[1])/Integer.valueOf(str[0]);
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
            diamondNum = diamondNum - exchangeDiamonds;
            toCoinNum = toCoinNum + getCoin;
            MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().setConvert_coin((int) diamondNum);
            MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt()
                    .setGold_coin((int) (MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean()
                    .getUserExt().getGold_coin() + getCoin));
            setTextStr();
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
                    showToast("请输入要兑换的钻石数");
                    return;
                } else if (Integer.valueOf(str)==0) {
                    showToast("请输入的钻石数不能为0");
                    return;
                }
                exchangeDiamonds = Integer.valueOf(str);
                getCoin = countToCoin(exchangeDiamonds);
                if (exchangeDiamonds > diamondNum) {
                    showWraning("兑换的钻石数大于拥有的钻石数（目前拥有钻石数："+diamondNum+")");
                    return;
                }
                PromptDialog.Builder builder = new PromptDialog.Builder(DiamondsToCoinActivity.this);
                builder.setTitle("提示");
                builder.setMessage("您确定要花"+exchangeDiamonds+"个钻石兑换"+getCoin+"金币吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OkHttpUtils.postString().url(Common.Url_DiamondsToCoin+str)
                                .tag(Common.NET_DIAMONDS_TO_COIN_ID).content("{}")
                                .mediaType(Common.JSON).id(Common.NET_DIAMONDS_TO_COIN_ID)
                                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                .build().execute(new MyStringCallback(mContext, DiamondsToCoinActivity.this, true));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
        }
    }
}
