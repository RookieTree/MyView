package com.tree.rulerdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.rulerdemo
 *  @文件名:   BottomWaveView
 *  @创建者:   rookietree
 *  @创建时间:  3/31/21 4:30 PM
 *  @描述：    TODO
 */
public class BottomWaveView extends View {

    private Context mContext;
    private int width;
    private int height;
    private Paint mWavePaint;
    private static final int X_TYPE = 0;
    private static final int Y_TYPE = 1;

    //波纹长度
    private float waveLength;
    //波纹的高度
    private float waveHeight;
    //margin
    private float waveMargin;

    //一个item区域的长度
    private float itemLength;
    //贝塞尔曲线路径
    private Path mPath;

    //偏移量
    private float offset;
    private float distance;

    private ValueAnimator mValueAnimator;
    //动画时间
    private int duration = 500;

    private ArrayList<PointF> mControlPoints = null;    // 控制点集
    private static final int FRAME = 1000;  // 1000帧
    private List<PointF> mBeiPointFS;
    private float lastX;
    private float downX;
    //贝塞尔点
    private PointF mPointF1;
    private PointF mPointF2;
    private PointF mPointF3;
    private PointF mPointF4;
    private PointF mPointF5;

    public BottomWaveView(Context context) {
        this(context, null);
    }

    public BottomWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        itemLength = width / 3;
        waveLength = itemLength / 4 * 3;
        waveMargin = itemLength / 8;
        waveHeight = 2* height;

        lastX = waveLength / 2 + waveMargin;
    }

    private void init() {
        mControlPoints = new ArrayList<>();
        mPointF1 = new PointF();
        mPointF2 = new PointF();
        mPointF3 = new PointF();
        mPointF4 = new PointF();
        mPointF5 = new PointF();

        mWavePaint = new Paint();
        mWavePaint.setColor(Color.BLACK);
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setAntiAlias(true);
        mWavePaint.setDither(true);

        mPath = new Path();

        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                offset = distance * animatedValue;
                invalidate();
            }
        });
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                lastX = downX;
                offset = 0;
                invalidate();
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
        });
        mValueAnimator.setDuration(duration);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mControlPoints.clear();
        mPointF1.x = lastX - waveLength / 2 + offset;
        mPointF1.y = 0;
        mPointF2.x = lastX - waveLength / 2 + waveLength / 3 + offset;
        mPointF2.y = 5;
        mPointF3.x = lastX + offset;
        mPointF3.y = waveHeight;
        mPointF4.x = lastX + waveLength / 2 - waveLength / 3 + offset;
        mPointF4.y = 5;
        mPointF5.x = lastX + waveLength / 2 + offset;
        mPointF5.y = 0;
        mControlPoints.add(mPointF1);
        mControlPoints.add(mPointF2);
        mControlPoints.add(mPointF3);
        mControlPoints.add(mPointF4);
        mControlPoints.add(mPointF5);
        mBeiPointFS = BezierUtils.buildBezierPoints(mControlPoints);
        mPath.reset();
        PointF pointF = mBeiPointFS.get(0);
        mPath.moveTo(pointF.x, pointF.y);
        for (int i = 1; i < mBeiPointFS.size(); i++) {
            PointF pointF1 = mBeiPointFS.get(i);
            mPath.lineTo(pointF1.x, pointF1.y);
        }
        mPath.lineTo(width, 0);
        mPath.lineTo(width, height);
        mPath.lineTo(0, height);
        mPath.lineTo(0, 0);
        mPath.close();
        canvas.drawPath(mPath, mWavePaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float x = event.getX();
                if (x < width / 3) {
                    downX = waveMargin + waveLength / 2;
                } else if (x < width / 3 * 2) {
                    downX = width / 3 * 2 - waveMargin - waveLength / 2;
                } else {
                    downX = width - waveMargin - waveLength / 2;
                }
                distance = downX - lastX;
                startMove();
                break;
            default:
                break;
        }
        return true;
    }

    public void startMove() {
        mValueAnimator.start();
    }
}
