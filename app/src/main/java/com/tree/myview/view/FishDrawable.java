package com.tree.myview.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.view
 *  @文件名:   FishDrawable
 *  @创建者:   rookietree
 *  @创建时间:  2022/4/9 19:57
 *  @描述：    TODO
 */
public class FishDrawable extends Drawable {

    private Paint mPaint;
    private Path mPath;
    private int OTHER_ALPHA = 110;
    private int BODY_ALPHA = 160;

    //鱼的重心
    private PointF middlePoint;
    //鱼的主要朝向角度
    private float fishMainAngle = 90;

    private float currentValue;

    //鱼头圆的半径
    private float HEAD_RADIUS = 50;
    //鱼身长度
    private float BODY_LENGTH = HEAD_RADIUS * 3.2f;

    //寻找鱼鳍起始点坐标的线长
    private float FIND_FINS_LENGTH = 0.9f * HEAD_RADIUS;
    //鱼鳍的长度
    private float FINS_LENGTH = 1.3f * HEAD_RADIUS;
    //大圆的半径
    private float BIG_RADIUS = 0.7f * HEAD_RADIUS;
    //中圆的半径
    private float MIDDLE_RADIUS = 0.6f * BIG_RADIUS;
    //小圆的半径
    private float SMALL_RADIUS = 0.4f * MIDDLE_RADIUS;
    //寻找尾部中圆圆心的线长
    private final float FIND_MIDDLE_CIRCLE_LENGTH = BIG_RADIUS * (1 + 0.6f);
    //寻找尾部小圆圆心的线长
    private final float FIND_SMALL_CIRCLE_LENGTH = MIDDLE_RADIUS * (0.4f + 2.7f);
    //寻找大三角形底部中心点的线长
    private final float FIND_TRIANGLE_LENGTH = MIDDLE_RADIUS * 2.7f;
    private PointF mHeadPoint;

    private float frequence=1f;

    private float fins_control_length=1.8f*FINS_LENGTH;
    private ObjectAnimator mFinsAnim;

    public FishDrawable() {
        init();
    }

    private void init() {
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        //防抖
        mPaint.setDither(true);
        //设置颜色
        mPaint.setColor(Color.argb(OTHER_ALPHA, 244, 92, 71));

        mPath = new Path();
        middlePoint = new PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 720f);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValue = (float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });
        valueAnimator.start();

        mFinsAnim = ObjectAnimator.ofFloat(this,"fins_control_length",1.8f*FINS_LENGTH,1.2f*FINS_LENGTH,1.8f*FINS_LENGTH);
        mFinsAnim.setDuration(500);
        mFinsAnim.setRepeatMode(ValueAnimator.RESTART);
        mFinsAnim.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        float fishAngle = (float) (fishMainAngle + Math.sin(Math.toRadians(currentValue*frequence)) * 10);
        //鱼头的圆心坐标
        mHeadPoint = cacluatePoint(middlePoint, BODY_LENGTH / 2, fishAngle);
        canvas.drawCircle(mHeadPoint.x, mHeadPoint.y, HEAD_RADIUS, mPaint);
        //画右鱼鳍
        PointF rightFinsPointF = cacluatePoint(mHeadPoint, FIND_FINS_LENGTH, fishAngle - 110);
        makeFins(canvas, rightFinsPointF, fishAngle, true);
        //画左鱼鳍
        PointF leftFinsPointF = cacluatePoint(mHeadPoint, FIND_FINS_LENGTH, fishAngle + 110);
        makeFins(canvas, leftFinsPointF, fishAngle, false);

        PointF bodyBottomCenterPoint = cacluatePoint(mHeadPoint, BODY_LENGTH, fishAngle - 180);
        //画节肢1
        PointF middleCenterPoint = makeSegment(canvas, bodyBottomCenterPoint, BIG_RADIUS,
                MIDDLE_RADIUS, FIND_MIDDLE_CIRCLE_LENGTH, fishAngle, true);
        //画节肢2
        makeSegment(canvas, middleCenterPoint, MIDDLE_RADIUS, SMALL_RADIUS,
                FIND_SMALL_CIRCLE_LENGTH, fishAngle, false);
        //尾巴
        float findEdgeLenth =
                (float) Math.abs(Math.sin(Math.toRadians(currentValue * 1.5*frequence)) * BIG_RADIUS);
        makeTriangel(canvas, middleCenterPoint, FIND_TRIANGLE_LENGTH, findEdgeLenth, fishAngle);
        makeTriangel(canvas, middleCenterPoint, FIND_TRIANGLE_LENGTH - 10, findEdgeLenth - 20,
                fishAngle);
        //身体
        makeBody(canvas, mHeadPoint, bodyBottomCenterPoint, fishAngle);

    }

    private void makeBody(Canvas canvas, PointF headPoint, PointF bodyBottomCenterPoint,
                          float fishAngle) {
        //身体的四个
        PointF topLeftPoint = cacluatePoint(headPoint, HEAD_RADIUS, fishAngle + 90);
        PointF topRightPoint = cacluatePoint(headPoint, HEAD_RADIUS, fishAngle - 90);
        PointF bottomLeftPoint = cacluatePoint(bodyBottomCenterPoint, BIG_RADIUS, fishAngle + 90);
        PointF bottomRightPoint = cacluatePoint(bodyBottomCenterPoint, BIG_RADIUS, fishAngle - 90);
        //鱼的胖瘦点
        PointF controlLeft = cacluatePoint(headPoint, BODY_LENGTH * 0.56f, fishAngle + 130);
        PointF controlRight = cacluatePoint(headPoint, BODY_LENGTH * 0.56f, fishAngle - 130);
        mPath.reset();
        mPath.moveTo(topLeftPoint.x, topLeftPoint.y);
        mPath.quadTo(controlLeft.x, controlLeft.y, bottomLeftPoint.x, bottomLeftPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        mPath.quadTo(controlRight.x, controlRight.y, topRightPoint.x, topRightPoint.y);
        mPaint.setAlpha(BODY_ALPHA);
        canvas.drawPath(mPath, mPaint);
    }

    private void makeTriangel(Canvas canvas, PointF startPoint, float findCenterLength,
                              float findEdgeLength, float fishAngle) {
        float triangelAngle =
                (float) (fishAngle + Math.sin(Math.toRadians(currentValue * 1.5*frequence)) * 35);
        //三角形底边的中心坐标
        PointF centerPoint = cacluatePoint(startPoint, findCenterLength, triangelAngle - 180);
        //三角形底边两点
        PointF leftPoint = cacluatePoint(centerPoint, findEdgeLength, triangelAngle + 90);
        PointF rightPoint = cacluatePoint(centerPoint, findEdgeLength, triangelAngle - 90);
        mPath.reset();
        mPath.moveTo(startPoint.x, startPoint.y);
        mPath.lineTo(leftPoint.x, leftPoint.y);
        mPath.lineTo(rightPoint.x, rightPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    private PointF makeSegment(Canvas canvas, PointF bottomCenterPoint, float bigRadius,
                               float smallRadius, float findSmallCircleLength, float fishAngle,
                               boolean hasBigCircle) {
        float segmentAngle;
        if (hasBigCircle) {
            //节肢1
            segmentAngle = (float) (fishAngle + Math.cos(Math.toRadians(currentValue * 1.5*frequence)) * 15);
        } else {
            //节肢2
            segmentAngle = (float) (fishAngle + Math.sin(Math.toRadians(currentValue * 1.5*frequence)) * 35);
        }
        //梯形上底圆的圆心
        PointF upperCenterPoint = cacluatePoint(bottomCenterPoint, findSmallCircleLength,
                segmentAngle - 180);
        //梯形的四个点
        PointF bottomLeftPoint = cacluatePoint(bottomCenterPoint, bigRadius, segmentAngle + 90);
        PointF bottomRightPoint = cacluatePoint(bottomCenterPoint, bigRadius, segmentAngle - 90);
        PointF upperLeftPoint = cacluatePoint(upperCenterPoint, smallRadius, segmentAngle + 90);
        PointF upperrightPoint = cacluatePoint(upperCenterPoint, smallRadius, segmentAngle - 90);
        //画大圆
        if (hasBigCircle) {
            //只在节肢1上才绘画
            canvas.drawCircle(bottomCenterPoint.x, bottomCenterPoint.y, bigRadius, mPaint);
        }
        //画小圆
        canvas.drawCircle(upperCenterPoint.x, upperCenterPoint.y, smallRadius, mPaint);
        //画梯形
        mPath.reset();
        mPath.moveTo(upperLeftPoint.x, upperLeftPoint.y);
        mPath.lineTo(upperrightPoint.x, upperrightPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        mPath.lineTo(bottomLeftPoint.x, bottomLeftPoint.y);
        canvas.drawPath(mPath, mPaint);
        return upperCenterPoint;
    }

    /**
     * 画鱼鳍
     *
     * @param canvas
     * @param startPoint
     * @param fishAngle
     * @param isRight    是否是右鱼鳍
     */
    private void makeFins(Canvas canvas, PointF startPoint, float fishAngle, boolean isRight) {
        float controlAngle = 115;
        //鱼鳍的终点
        PointF endPoint = cacluatePoint(startPoint, FINS_LENGTH, fishAngle - 180);
        //控制点
        PointF controlPoint = cacluatePoint(startPoint, fins_control_length, isRight ?
                fishAngle - controlAngle : fishAngle + controlAngle);
        mPath.reset();
        mPath.moveTo(startPoint.x, startPoint.y);
        mPath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * @param startPoint 起始点坐标
     * @param length     要求的点到起始点的直线距离--线长
     * @param angle      鱼当前的朝向角度
     * @return
     */
    public PointF cacluatePoint(PointF startPoint, float length, float angle) {
        //x坐标
        float deltaX = (float) (Math.cos(Math.toRadians(angle)) * length);
        //y坐标
        float deltaY = (float) (Math.sin(Math.toRadians(angle - 180)) * length);
        return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    /**
     * 根据setAlpha中设置的值来进行调整
     * 比如alpha=0,完全透明，完全不显示任何内容，就设置为PixelFormat.TRANSPARENT
     * 比如alpha=255,完全不透明，遮盖在他下面所有内容，就设置为PixelFormat.OPAQUE
     * 其他透明度就设置为PixelFormat.TRANSLUCENT
     *
     * @return
     */
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    public PointF getMiddlePoint() {
        return middlePoint;
    }

    public PointF getHeadPoint() {
        return mHeadPoint;
    }

    public float getHEAD_RADIUS() {
        return HEAD_RADIUS;
    }

    public float getFrequence() {
        return frequence;
    }

    public void setFrequence(float frequence) {
        this.frequence = frequence;
    }

    public float getFishMainAngle() {
        return fishMainAngle;
    }

    public void setFishMainAngle(float fishMainAngle) {
        this.fishMainAngle = fishMainAngle;
    }

    public float getFins_control_length() {
        return fins_control_length;
    }

    public void setFins_control_length(float fins_control_length) {
        this.fins_control_length = fins_control_length;
    }

    public void startFins(){
        mFinsAnim.cancel();
        mFinsAnim.start();
    }
    public void stopFins(){
        mFinsAnim.cancel();
    }
}
