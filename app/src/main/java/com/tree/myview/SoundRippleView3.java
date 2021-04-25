package com.tree.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;


/**
 * 水波纹view 第三种写法：每个circle有自己独立的进度，并且进度由runnable控制
 * 通过控制mDuration时间，可以使其效果不会顿挫太久
 */
public class SoundRippleView3 extends View {

    private float mInitialRadius;   // 初始波纹半径
    private long mDuration = 1600; // 一个波纹从创建到消失的持续时间
    private int rate = 50;//频率
    private int drawTimeRate = 50;

    private boolean mIsRunning = false;

    private Paint mPaint;
    private Paint mPaintSolid;
    private int rx, ry;
    private int startAlpha = 200;
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
        Circle circle2 = new Circle(400);
        Circle circle3 = new Circle(800);
        mCircleList.add(circle1);
        mCircleList.add(circle2);
        mCircleList.add(circle3);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
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
        for (int i = 0; i < mCircleList.size(); i++) {
            Circle circle = mCircleList.get(i);
            mPaint.setAlpha(circle.getAlpha());
            Log.d("sp3",
                    circle.hashCode() + ",getRadius:" + circle.getRadius() + ",getProgress:" + circle.getProgress());
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
    }


    private class Circle {
        //时间差
        private long p;
        private float mProgress = 0;
        private long lp;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        Circle(long p) {
            this.p = p;
            lp = mDuration / rate;

        }

        public int getAlpha() {
            float interpolation = mInterpolator.getInterpolation(mProgress);
            return (int) (startAlpha * (1 - interpolation));
        }

        public float getRadius() {
            float interpolation = mInterpolator.getInterpolation(mProgress);
            return mInitialRadius + (rx - mInitialRadius) * interpolation;
        }

        public float getProgress() {
            return mProgress;
        }

        private final Runnable mCreateCircle = new Runnable() {
            @Override
            public void run() {
                if (mIsRunning) {
                    float divide = divide(1, lp);
                    mProgress += divide;
                    if (mProgress > 1) {
                        mProgress = 0;
                    }
                    postDelayed(mCreateCircle, lp);
                }
            }
        };

        public void start() {
            postDelayed(mCreateCircle, p);
        }
    }

    /**
     * 除法 保留小数点后两位
     *
     * @param l1
     * @param l2
     * @return
     */
    private float divide(long l1, long l2) {
        BigDecimal b1 = new BigDecimal(l1);
        BigDecimal b2 = new BigDecimal(l2);
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
