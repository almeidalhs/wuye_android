package com.atman.wysq.ui.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.MyTools;
import com.base.baselibs.base.AppManager;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;

import java.util.List;

/**
 * Created by tangbingliang on 16/12/23.
 */

public class BackgroundReceiver extends BroadcastReceiver {

    private Context context;
    private long TIME = 60000L;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                LogUtils.e("do...");
                if (!isAppOnFreground(context)
                        || isApplicationBroughtToBackgroundByTask(context)) {//在后台
                    LogUtils.e("exit...");
                    PreferenceUtil.saveBoolPreference(context, PreferenceUtil.PARM_ISOUT, true);
                    AppManager.getAppManager().AppExit(context);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e("e:"+e.toString());
            }
        }
    };

    public BackgroundReceiver(Context context){
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.e("onReceive");
        if (!action.equals(Intent.ACTION_TIME_TICK)) {
            if (action.equals(StartReciverSeriver.ACTION_END)) {
                handler.removeCallbacks(runnable);
            } else {
                handler.postDelayed(runnable, TIME); //3s后执行
            }
        }
    }

    /**
     * 是否在后台
     * @return false 在后台; true 在前台
     * @return
     */
    public boolean isAppOnFreground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String curPackageName = MyBaseApplication.getApplication().getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> app = am.getRunningAppProcesses();
        if (app == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo a : app) {
            if (a.processName.equals(curPackageName) &&
                    a.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前应用程序是否处于后台，通过getRunningTasks的方式
     * @return true 在后台; false 在前台
     */
    public  boolean isApplicationBroughtToBackgroundByTask(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals("com.atman.wysq")) {
                return true;
            }
        }
        return false;
    }
}
