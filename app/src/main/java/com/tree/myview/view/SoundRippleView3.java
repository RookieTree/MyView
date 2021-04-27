package com.tree.myview.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;


/**
 * 水波纹view 第三种写法：每个circle有自己独立的进度，并且进度由objectanimator控制
 */
public class SoundRippleView3 extends View {

    private float mInitialRadius;   // 初始波纹半径
    private long mDuration = 1600; // 一个波纹从创建到消失的持续时间
    private int drawTimeRate = 50;

    private boolean mIsRunning;

    private Paint mPaint;
    private Paint mPaintSolid;
    private int rx, ry;
    private Interpolator mInterpolator;
    private List<Circle> mCircleList;


    private final Runnable mDrawRunnable = new Runnable() {
        @Override
        public void run() {
            postInvalidate();
            postDelayed(mDrawRunnable, drawTimeRate);
        }
    };


    public SoundRippleView3(Context context) {
        this(context, null);
    }

    public SoundRippleView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            init();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        mInterpolator = new PathInterpolator(0.25f, 0.1f, 0.25f, 1);
        mCircleList = new ArrayList<>();
        //构造参数：delay
        Circle circle1 = new Circle(0);
        Circle circle2 = new Circle(800);
        mCircleList.add(circle1);
        mCircleList.add(circle2);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaintSolid = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSolid.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        rx = w / 2;
        ry = h / 2;
        mInitialRadius = rx / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mIsRunning) {
            return;
        }
        for (int i = 0; i < mCircleList.size(); i++) {
            Circle circle = mCircleList.get(i);
            mPaint.setAlpha(circle.getAlpha());
            canvas.drawCircle(rx, ry, circle.getRadius(), mPaint);
        }
        canvas.drawCircle(rx, ry, mInitialRadius, mPaintSolid);
    }

    public void start() {
        if (mIsRunning) {
            return;
        }
        mDrawRunnable.run();
        mIsRunning = true;
        for (int i = 0; i < mCircleList.size(); i++) {
            Circle circle = mCircleList.get(i);
            circle.start();
        }
        invalidate();
    }

    public void stop() {
        mIsRunning = false;
        removeCallbacks(mDrawRunnable);
        invalidate();
        for (int i = 0; i < mCircleList.size(); i++) {
            Circle circle = mCircleList.get(i);
            circle.pause();
        }
    }


    private class Circle {
        //时间差
        private int alpha;
        private float radius;
        private AnimatorSet mSet;
        private long p;
        private ObjectAnimator mAlphaAnimator;
        private ObjectAnimator mRadiusAnimator;


        Circle(long p) {
            this.p = p;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public int getAlpha() {
            return alpha;
        }

        public float getRadius() {
            return radius;
        }

        public void setAlphaAnimator(ObjectAnimator alphaAnimator) {
            mAlphaAnimator = alphaAnimator;
        }

        public void setRadiusAnimator(ObjectAnimator radiusAnimator) {
            mRadiusAnimator = radiusAnimator;
        }

        public void start() {
            if (mSet == null) {
                mAlphaAnimator = ObjectAnimator.ofInt(this, "alpha", 255, 0);
                mRadiusAnimator = ObjectAnimator.ofFloat(this, "radius", mInitialRadius, rx);
                mAlphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
                mAlphaAnimator.setRepeatMode(ValueAnimator.RESTART);
                mRadiusAnimator.setRepeatCount(ValueAnimator.INFINITE);
                mRadiusAnimator.setRepeatMode(ValueAnimator.RESTART);
                mRadiusAnimator.setInterpolator(mInterpolator);

                mSet = new AnimatorSet();
                mSet.play(mRadiusAnimator).with(mAlphaAnimator);
                mSet.setDuration(mDuration);
                mSet.setStartDelay(p);

            }
            mSet.start();

        }

        public void pause() {
            mSet.end();
        }

    }
}
