package com.atman.wysq.ui.community;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;

import okhttp3.Response;

/**
 * Created by tangbingliang on 17/4/6.
 */

public class CreateVideoPostActivity extends MyBaseActivity {

    private Context mContext = CreateVideoPostActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_videopost);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("发布视频");
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
}
