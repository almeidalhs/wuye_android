package com.base.baselibs.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tangbingliang on 17/3/20.
 */

public class MyTwoViewPager extends ViewPager {
    private float xPosition;// 手指触摸点的x轴坐标
    private final float MINIMUM_DISTANCE = 3.0f;// 手指移动的最小距离
    private int distance;// 根据屏幕密度计算出来的，手指移动的最小距离

    public MyTwoViewPager(Context context) {
        super(context);
        distance = dip2px(context, MINIMUM_DISTANCE);
    }

    public MyTwoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        distance = dip2px(context, MINIMUM_DISTANCE);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 先保存手指按下的x轴的坐标
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
            xPosition = ev.getX();
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            /*
             计算手指移动时的坐标跟按下的坐标之间的绝对值，如果超过给定的值，
             就认为viewpager需要滚动。通过调节distance的大小，可以改变滑动
             灵敏度
              */
            if (Math.abs(ev.getX() - xPosition) < distance)
                return false;
            else// 意思就是：touch事件已经被PeopleViewPager自己消费了，不会传递到子控件
                return true;
        }
        // 其他情况，依旧保持默认的处理方法
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context Context
     * @param dpValue float
     * @return int dpValue对应的px值
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
