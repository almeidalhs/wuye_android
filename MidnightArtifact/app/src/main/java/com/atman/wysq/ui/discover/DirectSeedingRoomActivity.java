package com.atman.wysq.ui.discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;

import okhttp3.Response;

/**
 * Created by tangbingliang on 16/12/28.
 */

public class DirectSeedingRoomActivity extends MyBaseActivity {

    private Context mContext = DirectSeedingRoomActivity.this;
    private long roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directseedingroom);
    }

    public static Intent buildIntent(Context context, long roomId){
        Intent intent = new Intent(context, DirectSeedingRoomActivity.class);
        intent.putExtra("roomId", roomId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
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
