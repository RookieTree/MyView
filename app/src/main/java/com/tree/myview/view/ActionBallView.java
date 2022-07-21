package com.tree.myview.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.Nullable;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.view
 *  @文件名:   ActionBallView
 *  @创建者:   rookietree
 *  @创建时间:  2022/4/25 17:06
 *  @描述：    TODO
 */
public class ActionBallView extends View {

    private float radius = 150;
    private float width;
    private float height;
    private Paint mPaint;
    private Path mPath;
    private float startRadiusX;
    private float startRadiusY;
    private float endRadiusX;
    private float endRadiusY;
    private Context mContext;
    private float distance = 0;
    private LinearGradient mBackGradient;

    public ActionBallView(Context context) {
        this(context, null);
    }

    public ActionBallView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionBallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        //防抖
        mPaint.setDither(true);
//        mPaint.setColor(Color.parseColor("#EC9796"));
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        startRadiusX = width / 2 + width / 4;
        startRadiusY = height / 2;
        endRadiusX = width / 2 - width / 4;
        endRadiusY = height / 2;
        mBackGradient = new LinearGradient(startRadiusX - distance - radius,
                startRadiusY - radius, startRadiusX + radius, startRadiusY + radius,
                new int[]{Color.parseColor("#F99C63"), Color.parseColor("#FF6D6C"),
                        Color.parseColor("#EC9796")}, null, Shader.TileMode.CLAMP);
        mPaint.setShader(mBackGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       /* mPath.reset();
//        mPath.moveTo(startRadiusX, startRadiusY);
//        mPath.addCircle(startRadiusX, startRadiusY, radius, Path.Direction.CW);
        mPath.arcTo(startRadiusX - radius, startRadiusY - radius, startRadiusX + radius,
                startRadiusY + radius, -90, 180, true);
        mPath.lineTo(startRadiusX - distance, startRadiusY + radius);
        mPath.arcTo(startRadiusX - distance - radius, startRadiusY - radius,
                startRadiusX - distance + radius, startRadiusY + radius, 90, 180, false);
        mPath.close();
        canvas.drawPath(mPath, mPaint);*/
        canvas.drawRoundRect(startRadiusX - distance - radius, startRadiusY - radius,
                startRadiusX + radius, startRadiusY + radius, radius, radius, mPaint);
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (x < startRadiusX + radius && x > startRadiusX - radius && y > startRadiusY - radius && y < startRadiusY + radius) {
            //开始伸缩动画
            startAnim();
        }
        return super.onTouchEvent(event);
    }

    private void startAnim() {
        ObjectAnimator animator;
        if (distance == 0) {
            animator = ObjectAnimator.ofFloat(this, "distance", 0, width / 2).setDuration(500);
        } else {
            animator = ObjectAnimator.ofFloat(this, "distance", width / 2, 0).setDuration(500);
        }
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBackGradient = new LinearGradient(startRadiusX - distance - radius,
                        startRadiusY - radius, startRadiusX + radius, startRadiusY + radius,
                        new int[]{Color.parseColor("#F99C63"), Color.parseColor("#FF6D6C"),
                                Color.parseColor("#EC9796")}, null, Shader.TileMode.CLAMP);
                mPaint.setShader(mBackGradient);
                invalidate();
            }
        });

       /* final ValueAnimator valueAnimator = ValueAnimator.ofInt(0xfff99c63,
                0xffff6d6c,0xffec9796);
        valueAnimator.setEvaluator(new ArgbEvaluator());
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) valueAnimator.getAnimatedValue();
                mPaint.setColor(curValue);
                invalidate();
            }
        });
        valueAnimator.start();*/
    }
}
