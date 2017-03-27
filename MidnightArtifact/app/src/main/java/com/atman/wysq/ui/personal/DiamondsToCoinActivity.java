package com.atman.wysq.ui.personal;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.base.baselibs.widget.MyCleanEditText;

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
        str = MyBaseApplication.KDiamondChargeCoin.split(":");
        diamondNum = MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getConvert_coin();
        dismondstocoinDismondsNumTv.setText(""+diamondNum);
        if (str.length==2 && diamondNum>0) {
            toCoinNum = diamondNum*Integer.valueOf(str[1])/Integer.valueOf(str[0]);
            dismondstocoinCoinNumTv.setText(""+toCoinNum);
        }
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

    @OnClick({R.id.dismondstocoin_ok_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dismondstocoin_ok_bt:
                break;
        }
    }
}
