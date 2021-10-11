package com.tree.myview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


/**
 * 水波纹view 第一种写法：每个circle共享一个进度，如果放入插值器，会产生顿挫感，效果不佳
 */
public class SoundRippleView extends View {

    private float mInitialRadius;   // 初始波纹半径
    private long mDuration = 1600; // 一个波纹从创建到消失的持续时间

    private boolean mIsRunning;
    private ValueAnimator mValueAnimator;

    private Paint mPaint;
    private Paint mPaintSolid;
    private float mProgress;
    private int width, height;
    private int rx, ry;
    private int startAlpha = 200;

    private List<Circle> mCircleList;
    private Interpolator mInterpolator;

    private static final int MSG_DRAW_CIRCLE = 0;
    private static final int MSG_DRAW_CIRCLE1 = 1;
    private static final int MSG_DRAW_CIRCLE2 = 2;

    /*private final Runnable mCreateCircle = new Runnable() {
        @Override
        public void run() {
            if (mIsRunning) {
                mProgress += 0.02;
                if (mProgress > 1) {
                    mProgress = 0;
                }
                postInvalidate();
                postDelayed(mCreateCircle, 60);
            }
        }
    };*/

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == MSG_DRAW_CIRCLE) {
                for (Circle circle : mCircleList) {
                    if (circle.isStartDraw()) {
                        circle.addProgress();
                    }
                }
                postInvalidate();
                mHandler.sendEmptyMessageDelayed(MSG_DRAW_CIRCLE, 60);
            } else if (msg.what == MSG_DRAW_CIRCLE1) {
                mCircleList.get(0).setStartDraw(true);
            } else if (msg.what == MSG_DRAW_CIRCLE2) {
                mCircleList.get(1).setStartDraw(true);
            }
        }
    };

    public SoundRippleView(Context context) {
        this(context, null);
    }

    public SoundRippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            init();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        mCircleList = new ArrayList<>();
        //构造参数：传入每个圆环之间的延迟
        Circle circle1 = new Circle(0);
        Circle circle2 = new Circle(1000);
        mCircleList.add(circle1);
        mCircleList.add(circle2);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSolid = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInterpolator = new PathInterpolator(0.25f, 0.1f, 0.25f, 1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        rx = w / 2;
        ry = h / 2;
        mInitialRadius = rx / 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mIsRunning) {
            return;
        }
        for (int i = 0; i < mCircleList.size(); i++) {
            Circle circle = mCircleList.get(i);
            if (circle.isStartDraw()) {
                mPaint.setAlpha(circle.getAlpha());
                canvas.drawCircle(rx, ry, circle.getRadius(), mPaint);
            }
        }
        canvas.drawCircle(rx, ry, mInitialRadius, mPaintSolid);
    }

    public void start() {
        if (mIsRunning) {
            return;
        }
        mIsRunning = true;
        for (Circle circle : mCircleList) {
            if (circle.delayTime == 0) {
                mHandler.sendEmptyMessage(MSG_DRAW_CIRCLE1);
            } else {
                mHandler.sendEmptyMessageDelayed(MSG_DRAW_CIRCLE2, circle.delayTime);
            }
        }
        mHandler.sendEmptyMessage(MSG_DRAW_CIRCLE);
    }

    public void stop() {
        mIsRunning = false;
        invalidate();
        mHandler.removeCallbacksAndMessages(null);
        for (Circle circle : mCircleList) {
            circle.resetProgress();
        }
    }


    private class Circle {
        public long delayTime;
        private float progress;
        private boolean isStartDraw;

        Circle(int delayTime) {
            this.delayTime = delayTime;
        }

        public boolean isStartDraw() {
            return isStartDraw;
        }

        public void setStartDraw(boolean startDraw) {
            isStartDraw = startDraw;
        }

        public float getProgress() {
            progress = progress > 1f ? 0f : progress;
            return progress;
        }

        public void resetProgress() {
            progress = 0;
            isStartDraw=false;
        }

        public void addProgress() {
            progress += 0.02;
        }

        public int getAlpha() {
            float interpolation = mInterpolator.getInterpolation(getProgress());
            return (int) (startAlpha * (1 - interpolation));

        }

        public float getRadius() {
            float interpolation = mInterpolator.getInterpolation(getProgress());
            return mInitialRadius + (rx - mInitialRadius) * interpolation;

        }
    }
}
