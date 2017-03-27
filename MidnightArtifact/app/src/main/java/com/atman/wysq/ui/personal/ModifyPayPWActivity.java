package com.atman.wysq.ui.personal;

import android.content.Context;
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

public class ModifyPayPWActivity extends MyBaseActivity {

    @Bind(R.id.modify_paypw_old_et)
    MyCleanEditText modifyPaypwOldEt;
    @Bind(R.id.modify_paypw_et)
    MyCleanEditText modifyPaypwEt;
    @Bind(R.id.modify_paypw_again_et)
    MyCleanEditText modifyPaypwAgainEt;

    private Context mContext = ModifyPayPWActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_paypw);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
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

    @OnClick({R.id.modify_paypw_forgot_tv, R.id.setting_paypw_next_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modify_paypw_forgot_tv:
                break;
            case R.id.setting_paypw_next_bt:
                break;
        }
    }
}
