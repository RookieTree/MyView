package com.tree.myview.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.view
 *  @文件名:   MeituanBottomView
 *  @创建者:   rookietree
 *  @创建时间:  5/8/21 2:32 PM
 *  @描述：    TODO
 */
public class MeituanBottomView extends View {

    private Paint mBgPaint;
    private Paint mCirclePaint;
    private Paint mTextPaint;
    private Context mContext;
    private int width;
    private int height;
    private Path mPath;
    private int waveHeight = 60;
    private int waveOffset = 80;
    private int yOffset = 40;
    private float circleRadius;
    private int downX;
    private AnimatorSet mSet;

    public MeituanBottomView(Context context) {
        this(context, null);
    }

    public MeituanBottomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeituanBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBgPaint = new Paint();
        mBgPaint.setColor(Color.GRAY);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setDither(true);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.YELLOW);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(30);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mPath = new Path();
        circleRadius = waveHeight * 1.2f;

        ObjectAnimator waveheightAnimator = ObjectAnimator.ofInt(this, "yOffset", 30, 60, 30);
        ObjectAnimator waveoffsetanimator = ObjectAnimator.ofInt(this, "waveOffset", 60, 80, 60);
        mSet = new AnimatorSet();
        mSet.playTogether(waveheightAnimator, waveoffsetanimator);
        mSet.setDuration(1000);
        waveheightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        downX = width / 6;
    }

    public int getYOffset() {
        return yOffset;
    }

    public void setYOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public int getWaveOffset() {
        return waveOffset;
    }

    public void setWaveOffset(int waveOffset) {
        this.waveOffset = waveOffset;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(0, waveHeight);
        mPath.lineTo(downX - waveOffset, waveHeight);
        mPath.moveTo(downX - waveOffset, waveHeight);
        mPath.quadTo(downX, waveHeight - yOffset, downX + waveOffset, waveHeight);
        mPath.lineTo(width, waveHeight);
        canvas.drawPath(mPath, mBgPaint);
        canvas.drawCircle(downX, waveHeight + 1.5f * circleRadius - yOffset, circleRadius,
                mCirclePaint);

        canvas.drawText("首页", width / 6, height - 30, mTextPaint);
        canvas.drawText("订单", width / 3 + width / 6, height - 30, mTextPaint);
        canvas.drawText("我的", width * 2 / 3 + width / 6, height - 30, mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                float x = event.getX();
                if (x < width / 3) {
                    downX = width / 6;
                } else if (x < width / 3 * 2) {
                    downX = width / 3 + width / 6;
                } else {
                    downX = width * 2 / 3 + width / 6;
                }
                startMove();
                break;
            default:
                break;
        }
        return true;
    }

    public void startMove() {
        if (mSet.isRunning()){
            mSet.end();
        }
        mSet.start();
    }
}
