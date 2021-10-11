package com.tree.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

import com.tree.myview.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 功能描述: 上麦名片动效 波纹涟漪
 * 作者: Kong
 * 时间: 2021/4/19
 */
public class SoundRippleView4 extends View {

    private final int MESSAGE_START = 0;
    private final int MESSAGE_DRAW = 1;

    /**
     * 初始波纹半径
     */
    private float mInitialRadius;

    /**
     * 一个波纹从创建到消失的持续时间
     */
    private long mDuration = 1600;

    /**
     * 绘制间隔
     */
    private int mDrawTimeRate = 50;

    /**
     * 圆圈数量
     */
    private int mCircleCount = 2;

    /**
     * 圆圈相隔时间
     */
    private long mDelayTime = 800;

    /**
     * 圆圈透明度起始值(0~255)
     */
    private int mAlphaStart = 200;

    /**
     * 圆圈透明度结束值(0~255)
     */
    private int mAlphaEnd = 0;

    /**
     * Alpha的变化范围
     */
    private int mAlphaLimits;

    /**
     * 每个圆每次动画绘制的次数
     */
    private int mDrawTimes;

    /**
     * 动画插值器
     */
    private Interpolator mInterpolator;

    /**
     * 是否在运行中
     */
    private boolean isRunning;


    /**
     * 圆心坐标
     */
    private int mRx, mRy;

    private Paint mPaint;
    private List<Circle> mCircleList;

    private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_START:
                    resetProcess();
                    invalidate();
                    mHandler.sendEmptyMessage(MESSAGE_DRAW);
                    break;
                case MESSAGE_DRAW:
                    if (mHandler.hasMessages(MESSAGE_DRAW)) {
                        Log.d("SoundRippleView", "重置");
                        mHandler.removeMessages(MESSAGE_DRAW);
                        resetProcess();
                    }
                    if (!isRunning) {
                        // 当停止状态时，当两个圆都已经画完则不再发送draw消息
                        boolean isAnimEnd = true;
                        for (Circle circle : mCircleList) {
                            isAnimEnd &= circle.isAnimEnd();
                        }
                        if (isAnimEnd) {
                            break;
                        }
                    }
                    invalidate();
                    mHandler.sendEmptyMessageDelayed(MESSAGE_DRAW, mDrawTimeRate);
                    break;
            }
            return false;
        }
    });

    public SoundRippleView4(Context context) {
        this(context, null);
    }

    public SoundRippleView4(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mCircleList = new ArrayList<>();
        mInterpolator = new PathInterpolator(0.25f, 0.1f, 0.25f, 1f);
        setInitialRadius(CommonUtils.dip2px(context, 25));
        mDrawTimes = (int) (mDuration / mDrawTimeRate);
        // 每个圆直接的绘制间隔次数
        int mDelayTimes = (int) (mDelayTime / mDrawTimeRate);
        // 添加圆到绘制链表中
        for (int i = 0; i < mCircleCount; i++) {
            Circle circle = new Circle(-i * mDelayTimes);
            mCircleList.add(circle);
        }
        mAlphaLimits = mAlphaStart - mAlphaEnd;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mRx = w / 2;
        mRy = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("SoundRippleView", "onDraw()");

        Circle circle;
        for (int i = 0; i < mCircleList.size(); i++) {
            circle = mCircleList.get(i);
            Log.d("SoundRippleView", circle.toString() + "Alpha: " + circle.getAlpha() + " , " +
                    "Process" + circle.getProcess());
            mPaint.setAlpha(circle.getAlpha());
            canvas.drawCircle(mRx, mRy, circle.getProcess(), mPaint);
            circle.onDraw();
        }
    }

    /**
     * 开启动画
     */
    public void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessage(MESSAGE_START);
        }
    }

    /**
     * 停止动画
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * 设置初始半径
     */
    public void setInitialRadius(float initialRadius) {
        mInitialRadius = initialRadius;
    }

    private class Circle {
        private int process;
        private int initProcess;

        Circle(int process) {
            this.process = process;
            initProcess = process;
        }

        private float getProcessPercent() {
            float percent = ((float) process / (float) mDrawTimes);
            return percent > 0 ? percent : 0;
        }

        public float getProcess() {
            float radiusPercent = mInterpolator.getInterpolation(getProcessPercent());
            float limit = mRx - mInitialRadius;
            return limit * radiusPercent + mInitialRadius;
        }

        public int getAlpha() {
            float radiusPercent = mInterpolator.getInterpolation(getProcessPercent());
            return mAlphaStart - (int) (mAlphaLimits * radiusPercent);
        }

        public void onDraw() {
            if (process == mDrawTimes) {
                if (isRunning) {
                    process = 0;
                }
            } else {
                process++;
            }
        }

        public void reset() {
            process = initProcess;
        }

        public boolean isAnimEnd() {
            return process == mDrawTimes;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isRunning = false;
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    private void resetProcess() {
        for (Circle circle : mCircleList) {
            // 重置进度
            circle.reset();
        }
    }
}
