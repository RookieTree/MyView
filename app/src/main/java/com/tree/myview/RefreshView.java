package com.tree.myview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.BounceInterpolator;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.rulerdemo
 *  @文件名:   RefreshView
 *  @创建者:   rookietree
 *  @创建时间:  4/23/21 3:06 PM
 *  @描述：    刷新view
 */
public class RefreshView extends ConstraintLayout {
    private int width;
    private int height;
    private Paint mTopPaint;
    private Paint mBallPaint;
    //滑动的距离
    private float distance;
    //顶部拖动最大距离
    private static final int MAX_DISTANCE_1 = 200;
    //球体拖动最大距离
    private static final int MAX_DISTANCE_2 = 300;
    private float downY;
    private boolean isDown;
    private ValueAnimator mValueAnimatorTop;
    private ValueAnimator mValueAnimatorDrop;
    private float mTopProgress;
    private float mDropProgress;
    private Path mPath;
    //阶段
    private int step;
    //拉动顶部阶段
    private static final int STEP_TOP_PULL = 1;
    //拉动球体阶段
    private static final int STEP_BALL_PULL = 2;
    //球体下落阶段
    private static final int STEP_BALL_DROP = 3;

    private static final int DROP_DISTANCE = 300;

    private float ballRadius;
    private float ballY;
    private float topY;
    private static final int DROP_FINISH = -1;
    private RefreshCallback mCallback;
    public interface RefreshCallback{
        void refreshFinish();
    }
    public void setOnRefreshCallback(RefreshCallback callback){
        mCallback=callback;
    }
    public void removeCallback(){
        mCallback=null;
    }

    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        setWillNotDraw(false);
        mTopPaint = new Paint();
        mTopPaint.setColor(Color.BLACK);
        mTopPaint.setStyle(Paint.Style.FILL);
        mTopPaint.setAntiAlias(true);
        mTopPaint.setDither(true);

        mBallPaint = new Paint();
        mBallPaint.setColor(Color.BLUE);
        mBallPaint.setStyle(Paint.Style.FILL);
        mBallPaint.setAntiAlias(true);
        mBallPaint.setDither(true);
        mPath = new Path();
        mValueAnimatorTop = ValueAnimator.ofFloat(0, 1);
        mValueAnimatorTop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTopProgress = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimatorTop.setDuration(500);
        mValueAnimatorDrop = ValueAnimator.ofFloat(0, 1);
        mValueAnimatorDrop.setInterpolator(new BounceInterpolator());
        mValueAnimatorDrop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDropProgress = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimatorDrop.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mDropProgress = DROP_FINISH;
                invalidate();
                if (mCallback!=null){
                    mCallback.refreshFinish();
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mValueAnimatorDrop.setDuration(1000);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        if (isDown) {
            if (step == STEP_TOP_PULL) {
                ballRadius = distance * 0.2f;
                topY = distance;
                ballY = distance;

                mPath.moveTo(0, 0);
                mPath.quadTo(width / 2, topY, width, 0);
                mPath.close();
                canvas.drawCircle(width / 2, ballY, ballRadius, mBallPaint);
            } else if (step == STEP_BALL_PULL) {
                ballRadius = distance * 0.2f;
                ballY = distance;
                topY = MAX_DISTANCE_1;

                mPath.moveTo(0, 0);
                mPath.quadTo(width / 2, topY, width, 0);
                mPath.close();
                canvas.drawCircle(width / 2, ballY, ballRadius, mBallPaint);
            } else if (step == STEP_BALL_DROP) {
                ballRadius = MAX_DISTANCE_2 * 0.2f;
                ballY = MAX_DISTANCE_2;
                topY = MAX_DISTANCE_1;

                mPath.moveTo(0, 0);
                mPath.quadTo(width / 2, topY, width, 0);
                mPath.close();
                //
                canvas.drawCircle(width / 2, ballY, ballRadius, mBallPaint);
            }

        } else {

            mPath.moveTo(0, 0);
            mPath.quadTo(width / 2, topY * (1 - mTopProgress), width, 0);
            mPath.close();
            if (step <= STEP_BALL_PULL) {
                //画球体缩小
                canvas.drawCircle(width / 2, ballY * (1 - mTopProgress),
                        ballRadius * (1 - mTopProgress), mBallPaint);
            } else if (step == STEP_BALL_DROP) {
                //画球体下落
                if (mDropProgress == DROP_FINISH) {
                    return;
                }
                canvas.drawCircle(width / 2, ballY + DROP_DISTANCE * mDropProgress, ballRadius,
                        mBallPaint);
            }
        }
        canvas.drawPath(mPath, mTopPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                step = 0;
                isDown = true;
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                distance = (event.getY() - downY) * 0.5f;
                if (distance < MAX_DISTANCE_1) {
                    step = STEP_TOP_PULL;
                } else if (distance < MAX_DISTANCE_2) {
                    step = STEP_BALL_PULL;
                } else {
                    step = STEP_BALL_DROP;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isDown = false;
                if (step <= STEP_BALL_PULL) {
                    mValueAnimatorTop.start();
                } else {
                    mValueAnimatorTop.start();
                    mValueAnimatorDrop.start();
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mValueAnimatorTop.removeAllUpdateListeners();
        mValueAnimatorTop.removeAllListeners();
        mValueAnimatorDrop.removeAllUpdateListeners();
        mValueAnimatorDrop.removeAllListeners();
        removeCallbacks(null);
    }
}
