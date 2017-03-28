package com.atman.wysq.ui.personal;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
 * Created by tangbingliang on 17/3/28.
 */

public class WithdrawCashActivity extends MyBaseActivity {

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

    private Context mContext = WithdrawCashActivity.this;
    private long ownDiamonds = 0;
    private long canToCash = 0;

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

        withdrawConsumeTv.setText("提示：单次提现最低金额"+MyBaseApplication.KDiamondCashStart
                +"元，不满200元将收取3元手续费，提现转账需1-3个工作日。");

        ownDiamonds = MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean()
                .getUserExt().getConvert_coin();
        canToCash = countToCash(ownDiamonds);
    }

    private long countToCash(long ownDiamonds) {
        long Num = 0;
        String[] str = MyBaseApplication.KDiamondCash.split(":");
        if (str.length==2 && ownDiamonds>0) {
            Num = ownDiamonds*Integer.valueOf(str[1])/Integer.valueOf(str[0]);
        }
        return Num;
    }

    @Override
    protected void onResume() {
        super.onResume();
        withdrawDismondsNumTv.setText("" + ownDiamonds);
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.withdraw_account_monify_tv, R.id.withdraw_sumbit_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.withdraw_account_monify_tv:
                break;
            case R.id.withdraw_sumbit_bt:
                break;
        }
    }
}
