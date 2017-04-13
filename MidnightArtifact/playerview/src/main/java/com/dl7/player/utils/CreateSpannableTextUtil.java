package com.dl7.player.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

/**
 * Created by tangbingliang on 17/4/13.
 */

public class CreateSpannableTextUtil {

    public static SpannableStringBuilder getSpannableText(Context context, Bitmap bitmap, String text) {
        if (bitmap==null) {
            bitmap = BitmapFactory.decodeResource(context.getResources()
                    , com.dl7.player.R.mipmap.niming_ic);
        }
        CircleImageDrawable circleDrawable = new CircleImageDrawable(bitmap);
        int w = circleDrawable.getIntrinsicWidth();
        circleDrawable.setBounds(0, 0, w, w);
        return createSpannable(circleDrawable, text);
    }

    public static SpannableStringBuilder createSpannable(Drawable drawable, String content) {
        String text = "bitmap";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        CenteredImageSpan span = new CenteredImageSpan(drawable);
        spannableStringBuilder.setSpan(span, 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        if (!TextUtils.isEmpty(content)) {
            spannableStringBuilder.append(" ");
            spannableStringBuilder.append(content.trim());
            spannableStringBuilder.append(" ");
        }
        return spannableStringBuilder;
    }
}
