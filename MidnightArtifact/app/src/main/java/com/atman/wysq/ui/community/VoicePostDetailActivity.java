package com.atman.wysq.ui.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/4/7.
 */

public class VoicePostDetailActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_postdetail);
    }

    public static Intent buildIntent(Context context, String tilte, long id, boolean isMy, int vipLevel) {
        Intent intent = new Intent(context, ImageTextPostDetailActivity.class);
        intent.putExtra("tilte", tilte);
        intent.putExtra("id", id);
        intent.putExtra("isMy", isMy);
        intent.putExtra("vipLevel", vipLevel);
        return intent;
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
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
