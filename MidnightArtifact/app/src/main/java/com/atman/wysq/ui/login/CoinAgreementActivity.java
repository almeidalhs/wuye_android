package com.atman.wysq.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述 用户协议
 * 作者 tangbingliang
 * 时间 16/7/6 16:17
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class CoinAgreementActivity extends MyBaseActivity {

    @Bind(R.id.about_cion_diamonds_tx)
    TextView aboutCionDiamondsTx;

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_coinagreement);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, int id) {
        Intent intent = new Intent(context, CoinAgreementActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        id = getIntent().getIntExtra("id", 0);
        if (id == 0) {
            setBarTitleTx("关于金币");
            aboutCionDiamondsTx.setText(getResources().getString(R.string.coin));
        } else {
            setBarTitleTx("关于钻石");
            aboutCionDiamondsTx.setText(getResources().getString(R.string.diamonds));
        }
    }
}
