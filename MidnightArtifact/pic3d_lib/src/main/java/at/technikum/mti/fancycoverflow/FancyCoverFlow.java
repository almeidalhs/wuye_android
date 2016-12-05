/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package at.technikum.mti.fancycoverflow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.SpinnerAdapter;

public class FancyCoverFlow extends Gallery {

    public static final int ACTION_DISTANCE_AUTO = Integer.MAX_VALUE;
    public static final float SCALEDOWN_GRAVITY_CENTER = 0.5f;
    private float reflectionRatio = 0.4f;
    private int reflectionGap = 20;
    private boolean reflectionEnabled = false;
    /**
     * TODO: Doc
     */
    private float unselectedAlpha;

    /**
     * Camera used for view transformation.
     */
    private Camera transformationCamera;

    /**
     * TODO: Doc
     */
    private int maxRotation = 75;

    /**
     * Factor (0-1) that defines how much the unselected children should be scaled down. 1 means no scaledown.
     */
    private float unselectedScale;

    /**
     * TODO: Doc
     */
    private float scaleDownGravity = SCALEDOWN_GRAVITY_CENTER;

    /**
     * Distance in pixels between the transformation effects (alpha, rotation, zoom) are applied.
     */
    private int actionDistance;

    /**
     * Saturation factor (0-1) of items that reach the outer effects distance.
     */
    private float unselectedSaturation;
    private float spacing;
    private boolean isScend = false;

    public FancyCoverFlow(Context context) {
        super(context);
        this.initialize(context);
    }

    public FancyCoverFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialize(context);
        this.applyXmlAttributes(attrs);
    }

    public FancyCoverFlow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initialize(context);
        this.applyXmlAttributes(attrs);
    }

    private void initialize(Context context) {
        this.transformationCamera = new Camera();
        this.spacing = dp2px(context, 5);
    }

    private void applyXmlAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FancyCoverFlow);

        this.actionDistance = a.getInteger(R.styleable.FancyCoverFlow_actionDistance, ACTION_DISTANCE_AUTO);
        this.scaleDownGravity = a.getFloat(R.styleable.FancyCoverFlow_scaleDownGravity, 1.0f);
        this.maxRotation = a.getInteger(R.styleable.FancyCoverFlow_maxRotation, 45);
        this.unselectedAlpha = a.getFloat(R.styleable.FancyCoverFlow_unselectedAlpha, 0.3f);
        this.unselectedSaturation = a.getFloat(R.styleable.FancyCoverFlow_unselectedSaturation, 0.0f);
        this.unselectedScale = a.getFloat(R.styleable.FancyCoverFlow_unselectedScale, 0.75f);
    }

    public float getReflectionRatio() {
        return reflectionRatio;
    }

    public void setReflectionRatio(float reflectionRatio) {
        if (reflectionRatio <= 0 || reflectionRatio > 0.5f) {
            throw new IllegalArgumentException("reflectionRatio may only be in the interval (0, 0.5]");
        }

        this.reflectionRatio = reflectionRatio;

        if (this.getAdapter() != null) {
            ((FancyCoverFlowAdapter) this.getAdapter()).notifyDataSetChanged();
        }
    }

    public int getReflectionGap() {
        return reflectionGap;
    }

    public void setReflectionGap(int reflectionGap) {
        this.reflectionGap = reflectionGap;

        if (this.getAdapter() != null) {
            ((FancyCoverFlowAdapter) this.getAdapter()).notifyDataSetChanged();
        }
    }

    public boolean isReflectionEnabled() {
        return reflectionEnabled;
    }

    public void setReflectionEnabled(boolean reflectionEnabled) {
        this.reflectionEnabled = reflectionEnabled;

        if (this.getAdapter() != null) {
            ((FancyCoverFlowAdapter) this.getAdapter()).notifyDataSetChanged();
        }
    }

    /**
     * Use this to provide a {@link FancyCoverFlowAdapter} to the coverflow. This
     * method will throw an {@link ClassCastException} if the passed adapter does not
     * subclass {@link FancyCoverFlowAdapter}.
     *
     * @param adapter
     */
    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        if (!(adapter instanceof FancyCoverFlowAdapter)) {
            throw new ClassCastException(FancyCoverFlow.class.getSimpleName() + " only works in conjunction with a " + FancyCoverFlowAdapter.class.getSimpleName());
        }

        super.setAdapter(adapter);
    }

    /**
     * Returns the maximum rotation that is applied to items left and right of the center of the coverflow.
     *
     * @return
     */
    public int getMaxRotation() {
        return maxRotation;
    }

    /**
     * Sets the maximum rotation that is applied to items left and right of the center of the coverflow.
     *
     * @param maxRotation
     */
    public void setMaxRotation(int maxRotation) {
        this.maxRotation = maxRotation;
    }

    /**
     * TODO: Write doc
     *
     * @return
     */
    public float getUnselectedAlpha() {
        return this.unselectedAlpha;
    }

    /**
     * TODO: Write doc
     *
     * @return
     */
    public float getUnselectedScale() {
        return unselectedScale;
    }

    /**
     * TODO: Write doc
     *
     * @param unselectedScale
     */
    public void setUnselectedScale(float unselectedScale) {
        this.unselectedScale = unselectedScale;
    }

    /**
     * TODO: Doc
     *
     * @return
     */
    public float getScaleDownGravity() {
        return scaleDownGravity;
    }

    /**
     * TODO: Doc
     *
     * @param scaleDownGravity
     */
    public void setScaleDownGravity(float scaleDownGravity) {
        this.scaleDownGravity = scaleDownGravity;
    }

    /**
     * TODO: Write doc
     *
     * @return
     */
    public int getActionDistance() {
        return actionDistance;
    }

    /**
     * TODO: Write doc
     *
     * @param actionDistance
     */
    public void setActionDistance(int actionDistance) {
        this.actionDistance = actionDistance;
    }

    /**
     * TODO: Write doc
     *
     * @param unselectedAlpha
     */
    @Override
    public void setUnselectedAlpha(float unselectedAlpha) {
        super.setUnselectedAlpha(unselectedAlpha);
        this.unselectedAlpha = unselectedAlpha;
    }

    /**
     * TODO: Write doc
     *
     * @return
     */
    public float getUnselectedSaturation() {
        return unselectedSaturation;
    }

    /**
     * TODO: Write doc
     *
     * @param unselectedSaturation
     */
    public void setUnselectedSaturation(float unselectedSaturation) {
        this.unselectedSaturation = unselectedSaturation;
    }

    public void setScend(boolean scend) {
        isScend = scend;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    // 重载视图显示顺序让左到中间显示，再到右到中间显示
    protected int getChildDrawingOrder(int childCount, int i) {
        int selectedIndex = getSelectedItemPosition() - getFirstVisiblePosition();
        if (selectedIndex < 0) {
            return i;
        }

        if (i < selectedIndex) {
            return i;
        } else if (i >= selectedIndex) {
            return childCount - 1 - i + selectedIndex;
        } else {
            return i;
        }
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        super.getChildStaticTransformation(child, t);
        // We can cast here because FancyCoverFlowAdapter only creates wrappers.
        FancyCoverFlowItemWrapper item = (FancyCoverFlowItemWrapper) child;

        // Since Jelly Bean childs won't get invalidated automatically, needs to be added for the smooth coverflow animation
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            item.invalidate();
        }

        final int coverFlowWidth = this.getWidth();//屏幕宽
        final int coverFlowCenter = coverFlowWidth / 2;//屏幕中间
        final int childWidth = item.getWidth(); //item的宽
        final int childHeight = item.getHeight(); //item的高
        final int childCenter = item.getLeft() + childWidth / 2; //item的中间

        // Use coverflow width when its defined as automatic.
        final int actionDistance = (this.actionDistance == ACTION_DISTANCE_AUTO)
                ? (int) ((coverFlowWidth + childWidth) / 2.0f) : this.actionDistance;

        // Calculate the abstract amount for all effects. item的偏移
        final float effectsAmount = Math.min(1.0f, Math.max(-1.0f, (1.0f / actionDistance) * (childCenter - coverFlowCenter)));

        // Clear previous transformations and set transformation type (matrix + alpha).
        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);

        // Alpha,透明
        if (this.unselectedAlpha != 1) {
            final float alphaAmount = (this.unselectedAlpha - 1) * Math.abs(effectsAmount) + 1;
            t.setAlpha(alphaAmount);
        }

        // Saturation,饱和度
        if (this.unselectedSaturation != 1) {
            // Pass over saturation to the wrapper.
            final float saturationAmount = (this.unselectedSaturation - 1) * Math.abs(effectsAmount) + 1;
            item.setSaturation(saturationAmount);
        }

        final Matrix imageMatrix = t.getMatrix();

        // Apply rotation.旋转
        if (this.maxRotation != 0) {
            int n = 0;
            if (effectsAmount==0) {
                n = 0;
            } else if (effectsAmount>0) {
                n = -1 * this.maxRotation;
            } else if (effectsAmount<0) {
                n = 1 * this.maxRotation;
            }
            final int rotationAngle = n;
            this.transformationCamera.save();
            this.transformationCamera.rotateY(rotationAngle);
            this.transformationCamera.getMatrix(imageMatrix);
            this.transformationCamera.restore();
        }

        // final float zoomAmount = (this.unselectedScale - 1) * Math.abs(effectsAmount) + 1;
        // Calculate the scale anchor (y anchor can be altered)
        float translateX = childWidth / 2.0f;
        float translateY = childHeight / 2.0f;
        imageMatrix.preTranslate(-translateX, -translateY);
        // Zoom.
        if (isScend) {
            if (coverFlowCenter-childWidth == childCenter+spacing) {
                imageMatrix.postScale(1, 1.0f);
            } else {
                imageMatrix.postScale(0.85f, 0.85f);
            }
        } else {
            if (this.unselectedScale != 1) {
                if (effectsAmount==0) {
                    imageMatrix.postScale(1, 1.0f);
                } else if (effectsAmount>0) {
                    imageMatrix.postScale(1, 0.85f);
                } else if (effectsAmount<0) {
                    imageMatrix.postScale(1, 0.85f);
                }
            }
        }
        imageMatrix.postTranslate(translateX, translateY);

        return true;
    }

    public int dp2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static class LayoutParams extends Gallery.LayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    private boolean mScrolling;
    private float touchDownX;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownX = event.getX();
                mScrolling = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(touchDownX - event.getX()) >= ViewConfiguration.get(
                        getContext()).getScaledTouchSlop()) {
                    mScrolling = true;
                } else {
                    mScrolling = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mScrolling = false;
                break;
        }
        return mScrolling;
    }

    float x1 = 0;
    float x2 = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                if (touchDownX - x2 > dp2px(getContext(), 40)) {
                    if(mSetOnSlideListener!=null){
                        mSetOnSlideListener.onRightToLeftSlide();
                    }
                }
                if (touchDownX - x2 < - dp2px(getContext(), 40)) {
                    if(mSetOnSlideListener!=null){
                        mSetOnSlideListener.onLeftToRightSlide();
                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    private setOnSlideListener mSetOnSlideListener;

    public setOnSlideListener getmSetOnSlideListener() {
        return mSetOnSlideListener;
    }

    public void setmSetOnSlideListener(setOnSlideListener mSetOnSlideListener) {
        this.mSetOnSlideListener = mSetOnSlideListener;
    }

    public interface setOnSlideListener{
        void onRightToLeftSlide();
        void onLeftToRightSlide();
    }
}
