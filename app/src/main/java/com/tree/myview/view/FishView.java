package com.tree.myview.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.tree.myview.util.CommonUtils;

import androidx.annotation.Nullable;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.view
 *  @文件名:   FishView
 *  @创建者:   rookietree
 *  @创建时间:  2022/4/7 16:20
 *  @描述：    TODO
 */
public class FishView extends View {

    private Context mContext;
    private Paint mPaintQian;
    private Paint mPaintShen;
    private float headRadius;
    private float yaoRadius;
    private float tailRadius;
    private float endRadius;
    private float headX;
    private float headY;
    private float yaoX;
    private float yaoY;
    private float tailX;
    private float tailY;
    private float endX;
    private float endY;
    private Path leftQiP;
    private Path rightQiP;
    private Path bodyP;
    private Path yaoP;
    private Path tailP;
    private Path sanjiaoP;
    private Path sanjiaoP2;
    private float yuqiOffset=80;
    private ObjectAnimator mYuqiAnimator;
    private float width;
    private float height;
    private ObjectAnimator mTailAnim;

    public FishView(Context context) {
        this(context, null);
    }

    public FishView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mPaintQian = new Paint();
        mPaintShen = new Paint();
        mPaintQian.setAntiAlias(true);
        mPaintShen.setAntiAlias(true);
        mPaintQian.setDither(true);
        mPaintShen.setDither(true);
        mPaintQian.setColor(Color.parseColor("#55F99D92"));
        mPaintShen.setColor(Color.parseColor("#55F67D6C"));
        headRadius = CommonUtils.dip2px(mContext, 50);
        yaoRadius = CommonUtils.dip2px(mContext, 30);
        tailRadius = CommonUtils.dip2px(mContext, 15);
        endRadius = CommonUtils.dip2px(mContext, 5);
        leftQiP = new Path();
        rightQiP = new Path();
        bodyP = new Path();
        yaoP = new Path();
        tailP = new Path();
        sanjiaoP = new Path();
        sanjiaoP2 = new Path();
        mYuqiAnimator = ObjectAnimator.ofFloat(this, "yuqiOffset", 50, 80, 50);
        mYuqiAnimator.setDuration(1500);
        mYuqiAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mYuqiAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mYuqiAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        mTailAnim = ObjectAnimator.ofFloat(this, "Rotation", -30, 30);
        mTailAnim.setRepeatCount(ValueAnimator.INFINITE);
        mTailAnim.setRepeatMode(ValueAnimator.REVERSE);
        mTailAnim.setDuration(500);
        mTailAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
    }

    public float getYuqiOffset() {
        return yuqiOffset;
    }

    public void setYuqiOffset(float yuqiOffset) {
        this.yuqiOffset = yuqiOffset;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        headX = width / 2;
        headY = height / 2 - CommonUtils.dip2px(mContext, 100);
        yaoX = width / 2;
        yaoY = headY + CommonUtils.dip2px(mContext, 180);
        tailX = width / 2;
        tailY = yaoY + CommonUtils.dip2px(mContext, 55);
        endX = width / 2;
        endY = tailY + CommonUtils.dip2px(mContext, 50);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(headX, headY, headRadius, mPaintQian);
        canvas.drawCircle(yaoX, yaoY, yaoRadius, mPaintQian);
        canvas.drawCircle(tailX, tailY, tailRadius, mPaintQian);
        canvas.drawCircle(endX, endY, endRadius, mPaintQian);
        //鱼鳍
        leftQiP.reset();
        rightQiP.reset();
        leftQiP.moveTo(headX - headRadius + CommonUtils.dip2px(mContext, 5),
                headY + CommonUtils.dip2px(mContext, 10));
        leftQiP.quadTo(headX - headRadius - CommonUtils.dip2px(mContext, yuqiOffset),
                headY + CommonUtils.dip2px(mContext, 40),
                headX - headRadius + CommonUtils.dip2px(mContext, 5),
                headY + CommonUtils.dip2px(mContext, 70));
        leftQiP.close();
        rightQiP.moveTo(headX + headRadius - CommonUtils.dip2px(mContext, 5),
                headY + CommonUtils.dip2px(mContext, 10));
        rightQiP.quadTo(headX + headRadius + CommonUtils.dip2px(mContext, yuqiOffset),
                headY + CommonUtils.dip2px(mContext, 40),
                headX + headRadius - CommonUtils.dip2px(mContext, 5),
                headY + CommonUtils.dip2px(mContext, 70));
        rightQiP.close();
        canvas.drawPath(leftQiP, mPaintQian);
        canvas.drawPath(rightQiP, mPaintQian);
        //身体
        bodyP.reset();
        bodyP.moveTo(headX - headRadius, headY);
        bodyP.quadTo(headX - headRadius - CommonUtils.dip2px(mContext, 10), headY + 40,
                yaoX - yaoRadius, yaoY);
        bodyP.lineTo(yaoX + yaoRadius, yaoY);
        bodyP.quadTo(headX + headRadius + CommonUtils.dip2px(mContext, 10), headY + 40,
                headX + headRadius, headY);
        bodyP.close();
        canvas.drawPath(bodyP, mPaintShen);
        //腰子
        yaoP.reset();
        yaoP.moveTo(yaoX - yaoRadius, yaoY + CommonUtils.dip2px(mContext, 2));
        yaoP.lineTo(tailX - tailRadius, tailY);
        yaoP.lineTo(tailX + tailRadius, tailY);
        yaoP.lineTo(yaoX + yaoRadius, yaoY + CommonUtils.dip2px(mContext, 2));
        canvas.drawPath(yaoP, mPaintShen);
        //尾巴
        tailP.reset();
        tailP.moveTo(tailX - tailRadius, tailY + CommonUtils.dip2px(mContext, 2));
        tailP.lineTo(endX - endRadius, endY);
        tailP.lineTo(endX + endRadius, endY);
        tailP.lineTo(tailX + tailRadius, tailY + CommonUtils.dip2px(mContext, 2));
        canvas.drawPath(tailP, mPaintShen);
        //三角尾
        sanjiaoP.reset();
        sanjiaoP2.reset();
        sanjiaoP.moveTo(tailX, tailY + CommonUtils.dip2px(mContext, 2));
        sanjiaoP.lineTo(tailX - CommonUtils.dip2px(mContext, 25),
                endY - CommonUtils.dip2px(mContext, 8));
        sanjiaoP.lineTo(tailX + CommonUtils.dip2px(mContext, 25),
                endY - CommonUtils.dip2px(mContext, 8));
        sanjiaoP.close();
        canvas.drawPath(sanjiaoP, mPaintQian);
        sanjiaoP2.moveTo(tailX, tailY + CommonUtils.dip2px(mContext, 2));
        sanjiaoP2.lineTo(tailX - CommonUtils.dip2px(mContext, 16),
                endY - CommonUtils.dip2px(mContext, 13));
        sanjiaoP2.lineTo(tailX + CommonUtils.dip2px(mContext, 16), endY - CommonUtils.dip2px(mContext, 13));
        sanjiaoP2.close();
        canvas.drawPath(sanjiaoP2, mPaintShen);
    }

    public void startAnim(){
        //鱼鳍扇动动画
        mYuqiAnimator.start();
        mTailAnim.start();
    }
}
