package com.atman.wysq.model.danmaku;

import android.text.SpannableStringBuilder;

/**
 * Created by tangbingliang on 17/4/12.
 */

public class MyDanmakuModel {
    /**
     * content : 。。。。。。。。。。。。。。。。。。。。。。
     * textColor : -1
     * time : 0
     * textSize : 60
     * type : 1
     * userName : Tom
     * userLevel : 2
     */

    private SpannableStringBuilder content;
    private int textColor;
    private int time;
    private int textSize;
    private int type;
    private String userName;
    private int userLevel;

    public MyDanmakuModel(SpannableStringBuilder content, int time, String userName) {
        this.content = content;
        this.textColor = -1;
        this.time = time;
        this.textSize = 30;
        this.type = 1;
        this.userName = userName;
        this.userLevel = 1;
    }
}
