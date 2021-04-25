package com.tree.myview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

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

    private final Runnable mCreateCircle = new Runnable() {
        @Override
        public void run() {
            if (mIsRunning) {
                mProgress+=0.02;
                if (mProgress>1){
                    mProgress=0;
                }
                postInvalidate();
                postDelayed(mCreateCircle, 60);
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
        //构造参数：传入每个圆环之前的半径差距百分比
        Circle circle1 = new Circle(0f);
        Circle circle2 = new Circle(0.3f);
        Circle circle3 = new Circle(0.6f);
        mCircleList.add(circle1);
        mCircleList.add(circle2);
        mCircleList.add(circle3);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSolid = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mInterpolator= new PathInterpolator(0.25f,0.1f,0.25f,1);
        mInterpolator= new LinearInterpolator();
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mProgress = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

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
        for (int i = 0; i < mCircleList.size(); i++) {
            Circle circle = mCircleList.get(i);
            mPaint.setAlpha(circle.getAlpha());
            canvas.drawCircle(rx, ry, circle.getRadius(), mPaint);
        }
        canvas.drawCircle(rx, ry, mInitialRadius, mPaintSolid);
    }

    /**
     * 用thread启动，效果比插值器好一点
     */
    public void start() {
        mIsRunning=true;
        mCreateCircle.run();
    }

    /**
     * 用插值动画启动，周期之前切换会有顿挫感
     */
    public void startByAnimator(){
        mValueAnimator.start();
//        invalidate();
    }

    public void stop(){
        mIsRunning=false;
        invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void stopByAnimator(){
        mValueAnimator.pause();
    }

    private class Circle {
        private float radius;
        private int alpha;
        //传入每个圆环之前的半径差距百分比
        private float p;

        Circle(float p) {
            this.p = p;
            alpha = (int) (startAlpha * (1 - p));
            radius = mInitialRadius + (rx - mInitialRadius) * p;
        }

        public int getAlpha() {
            float interpolation = mInterpolator.getInterpolation(mProgress);
            if (1 - interpolation - p < 0) {
                return (int) (startAlpha * (2 - (p + interpolation)));
            } else {
                return (int) (startAlpha * (1 - interpolation - p));
            }
        }

        public float getRadius() {
            float interpolation = mInterpolator.getInterpolation(mProgress);
            if (interpolation + p > 1) {
                return mInitialRadius + (rx - mInitialRadius) * (p + interpolation - 1);
            } else {
                return mInitialRadius + (rx - mInitialRadius) * (interpolation + p);
            }
        }
    }
}
