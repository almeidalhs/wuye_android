package com.atman.wysq.ui.receiver;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by tangbingliang on 16/12/23.
 */

public class StartReciverSeriver extends Service {
    public static String ACTION_START = "start";
    public static String ACTION_END = "end";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        IntentFilter mFilter = new IntentFilter(Intent.ACTION_TIME_TICK); //注册IntentFilter
        mFilter.setPriority(Integer.MAX_VALUE); //设置级别
        mFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);//home键
        mFilter.addAction(Intent.ACTION_SCREEN_ON);  //开屏
        mFilter.addAction(Intent.ACTION_SCREEN_OFF);//锁屏
        mFilter.addAction(Intent.ACTION_USER_PRESENT);//解锁
        mFilter.addAction(ACTION_START);//加入
        mFilter.addAction(ACTION_END);//移除
        BackgroundReceiver receiver = new BackgroundReceiver(this);//本地服务
        registerReceiver(receiver, mFilter);//注册广播

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
