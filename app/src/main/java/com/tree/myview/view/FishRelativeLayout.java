package com.tree.myview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.view
 *  @文件名:   FishRelativeLayout
 *  @创建者:   rookietree
 *  @创建时间:  2022/4/11 18:16
 *  @描述：    TODO
 */
public class FishRelativeLayout extends RelativeLayout {

    private Paint mPaint;
    private ImageView mIvFish;
    private FishDrawable mFishDrawable;
    private float touchx;
    private float touchy;
    private float ripple;
    private int alpha;

    public FishRelativeLayout(Context context) {
        this(context, null);
    }

    public FishRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FishRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //viewgroup默认不执行ondraw
        setWillNotDraw(false);
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        //防抖
        mPaint.setDither(true);
        //设置颜色
//        mPaint.setColor(Color.argb(OTHER_ALPHA, 244, 92, 71));
        mPaint.setStrokeWidth(8);
        mIvFish = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mIvFish.setLayoutParams(layoutParams);
        mFishDrawable = new FishDrawable();
        mIvFish.setImageDrawable(mFishDrawable);
        addView(mIvFish);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAlpha(alpha);
        canvas.drawCircle(touchx, touchy, ripple * 150, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchx = event.getX();
        touchy = event.getY();
        ObjectAnimator objectAnimator =
                ObjectAnimator.ofFloat(this, "ripple", 0, 1f).setDuration(1000);
        objectAnimator.start();
        makeTrail();
        return super.onTouchEvent(event);
    }

    private void makeTrail() {
        //鱼的重心：相对ImageView坐标
        PointF fishRelativePoint = mFishDrawable.getMiddlePoint();
        //鱼的重心：绝对坐标
        PointF fishMiddle = new PointF(mIvFish.getX() + fishRelativePoint.x,
                mIvFish.getY() + fishRelativePoint.y);
        //鱼头圆心的坐标-->控制点1
        PointF fishHead = new PointF(mIvFish.getX() + mFishDrawable.getHeadPoint().x,
                mIvFish.getY() + mFishDrawable.getHeadPoint().y);
        //点击坐标 -->结束点
        PointF touch = new PointF(touchx, touchy);

        float angle = includeAngle(fishMiddle, fishHead, touch) / 2;
        float delta = includeAngle(fishMiddle, new PointF(fishMiddle.x + 1, fishMiddle.y),
                fishHead);
        //控制点2的坐标
        PointF controlPoint = mFishDrawable.cacluatePoint(fishMiddle,
                mFishDrawable.getHEAD_RADIUS() * 1.6f, angle + delta);
        Path path = new Path();
        path.moveTo(fishMiddle.x - fishRelativePoint.x, fishMiddle.y - fishRelativePoint.y);
        path.cubicTo(fishHead.x - fishRelativePoint.x, fishHead.y - fishRelativePoint.y,
                controlPoint.x - fishRelativePoint.x, controlPoint.y - fishRelativePoint.y,
                touchx - fishRelativePoint.x, touchy - fishRelativePoint.y);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIvFish, "x", "y", path);
        objectAnimator.setDuration(2000);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mFishDrawable.setFrequence(1f);
                mFishDrawable.stopFins();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mFishDrawable.setFrequence(2f);
                mFishDrawable.startFins();
            }
        });
        PathMeasure pathMeasure=new PathMeasure(path,false);
        float[] tan=new float[2];
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //在整个周期的百分比
                float fraction = (float) animation.getAnimatedFraction();
                pathMeasure.getPosTan(pathMeasure.getLength()*fraction,null,tan);
                float angle= (float) Math.toDegrees(Math.atan2(-tan[1],tan[0]));
                mFishDrawable.setFishMainAngle(angle);
            }
        });
        objectAnimator.start();
    }

    public float includeAngle(PointF O, PointF A, PointF B) {
        //cosAOB
        float AOB = (A.x - O.x) * (B.x - O.x) + (A.y - O.y) * (B.y - O.y);
        float OALength = (float) Math.sqrt((A.x - O.x) * (A.x - O.x) + (A.y - O.y) * (A.y - O.y));
        float OBLength = (float) Math.sqrt((B.x - O.x) * (B.x - O.x) + (B.y - O.y) * (B.y - O.y));
        float cosAOB = AOB / (OALength * OBLength);
        //acos 反余弦
        float angleAOB = (float) Math.toDegrees(Math.acos(cosAOB));
        //AB连线与X的夹角的tan值 减去 OB与X轴的夹角的tan值
        float direction = (A.y - B.y) / (A.x - B.x) - (O.y - B.y) * (O.x - B.x);
        if (direction == 0) {
            if (AOB >= 0) {
                return 0;
            } else {
                return 180;
            }
        } else {
            if (direction > 0) {
                return -angleAOB;
            } else {
                return angleAOB;
            }
        }
    }

    public float getRipple() {
        return ripple;
    }

    public void setRipple(float ripple) {
        //透明度的变化由100到0
        alpha = (int) (100 * (1 - ripple));
        this.ripple = ripple;
        invalidate();
    }
}
