package com.cn.library;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Property;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by nurmemet on 2016/3/10.
 */
public class CircularAnimateDrawable  extends Drawable implements Animatable{
    public static final int MIN_SWEEP_ANGLE = 90;
    private static final Interpolator ANGLE_INTERPOLATOR = new LinearInterpolator();
    private static final Interpolator SWEEP_INTERPOLATOR = new DecelerateInterpolator();

    private static final int ANGLE_ANIMATOR_DURATION = 2000;
    private static final int SWEEP_ANIMATOR_DURATION = 600;

    private Paint mPaint;
    private float mBorderWidth;

    private ObjectAnimator mAngleAnimator;
    private ObjectAnimator mSweepAnimator;

    private RectF fBounds=new RectF();

    private boolean mModeAppearing=true;


    private float mCurrentGlobalAngleOffset;

    public float getmCurrentGlobalAngle() {
        return mCurrentGlobalAngle;
    }

    public void setmCurrentGlobalAngle(float mCurrentGlobalAngle) {
        this.mCurrentGlobalAngle = mCurrentGlobalAngle;
        invalidateSelf();

    }

    public float getmCurrentSweepAngle() {
        return mCurrentSweepAngle;
    }

    public void setmCurrentSweepAngle(float mCurrentSweepAngle) {
        this.mCurrentSweepAngle = mCurrentSweepAngle;
        invalidateSelf();
    }

    public float getmCurrentGlobalAngleOffset() {
        return mCurrentGlobalAngleOffset;
    }

    public void setmCurrentGlobalAngleOffset(float mCurrentGlobalAngleOffset) {
        this.mCurrentGlobalAngleOffset = mCurrentGlobalAngleOffset;

    }

    private float mCurrentGlobalAngle;
    private float mCurrentSweepAngle;

    private boolean mRunning;

    public CircularAnimateDrawable(int color,float borderWidth) {
        mBorderWidth=borderWidth;
        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setAntiAlias(true);

        setUpAnimation();
    }

    private void setUpAnimation(){

        mAngleAnimator=ObjectAnimator.ofFloat(this,mAnglePropery,360f);
        mAngleAnimator.setInterpolator(ANGLE_INTERPOLATOR);
        mAngleAnimator.setDuration(ANGLE_ANIMATOR_DURATION);
        mAngleAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAngleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mSweepAnimator=ObjectAnimator.ofFloat(this,mSweepProperty, 360 - MIN_SWEEP_ANGLE * 2);
        mSweepAnimator.setInterpolator(SWEEP_INTERPOLATOR);
        mSweepAnimator.setDuration(SWEEP_ANIMATOR_DURATION);
        mSweepAnimator.setRepeatMode(ValueAnimator.RESTART);
        mSweepAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mSweepAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {


                toggleAppearingMode();
            }
        });

    }

    private void toggleAppearingMode() {
        mModeAppearing = !mModeAppearing;
        if (mModeAppearing) {
            mCurrentGlobalAngleOffset = (mCurrentGlobalAngleOffset + MIN_SWEEP_ANGLE * 2) % 360;
        }
    }

    @Override
    public void start() {
        if(isRunning()){
            return ;
        }
        mRunning=true;
        mSweepAnimator.start();
        mAngleAnimator.start();
        invalidateSelf();
    }

    @Override
    public void stop() {
        if(!isRunning()){
            return ;
        }
        mRunning=false;
        mSweepAnimator.cancel();
        mAngleAnimator.cancel();
        mCurrentGlobalAngleOffset=0;
        mCurrentGlobalAngle=0;
        mCurrentSweepAngle=0;
        invalidateSelf();
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

    @Override
    public void draw(Canvas canvas) {

        float startAngle = mCurrentGlobalAngle - mCurrentGlobalAngleOffset;
        float sweepAngle = mCurrentSweepAngle;
        if (!mModeAppearing) {
            startAngle = startAngle + sweepAngle;
            sweepAngle = 360 - sweepAngle - MIN_SWEEP_ANGLE;
        } else {
            sweepAngle += MIN_SWEEP_ANGLE;
        }
        canvas.drawArc(fBounds, startAngle, sweepAngle, false, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }


    private Property<CircularAnimateDrawable,Float> mAnglePropery=new Property<CircularAnimateDrawable, Float>(Float.class,"angle") {
        @Override
        public Float get(CircularAnimateDrawable object) {
            return object.getmCurrentGlobalAngle();
        }

        @Override
        public void set(CircularAnimateDrawable object, Float value) {
            object.setmCurrentGlobalAngle(value);
        }
    };

    private Property<CircularAnimateDrawable,Float> mSweepProperty=new Property<CircularAnimateDrawable,Float>(Float.class,"arc"){
        @Override
        public Float get(CircularAnimateDrawable object) {
            return object.getmCurrentSweepAngle();
        }

        @Override
        public void set(CircularAnimateDrawable object, Float value) {
            object.setmCurrentSweepAngle(value);
        }
    };

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        fBounds.left=bounds.left+mBorderWidth/2f;
        fBounds.top=bounds.top+mBorderWidth/2f;
        fBounds.right=bounds.right-mBorderWidth/2f;
        fBounds.bottom=bounds.bottom-mBorderWidth/2f;

    }
};
