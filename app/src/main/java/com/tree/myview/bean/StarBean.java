package com.tree.myview.bean;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.tree.myview.util.ImgManager;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.bean
 *  @文件名:   StarBean
 *  @创建者:   rookietree
 *  @创建时间:  4/26/21 10:35 AM
 *  @描述：    TODO
 */
public class StarBean {

    private float x;
    private float y;
    private Bitmap mBitmap;
    private RectF mRectF;
    private float starAlpha = 255;
    private int mDuration = 1200;
    private Context mContext;
    private boolean isThrowFinish;

    public StarBean(Context context) {
        mContext = context;
        mBitmap = BitmapFactory.decodeResource(context.getResources(),
                ImgManager.getInstance().getImg());
        mRectF = new RectF();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Bitmap getBitmap() {
        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(mContext.getResources(),
                    ImgManager.getInstance().getImg());
        }
        return mBitmap;
    }

    public float getStarAlpha() {
        return starAlpha;
    }

    public void setStarAlpha(float starAlpha) {
        this.starAlpha = starAlpha;
    }

    public boolean isThrowFinish() {
        return isThrowFinish;
    }

    /**
     * 开启丢图标动画
     */
    public void startThrow() {
        float topX = (float) (-500 + 1000 * Math.random());
        float topY = (float) (500 + 500 * Math.random());
        //上升动画
        //抛物线动画 x方向
        ObjectAnimator translateAnimationX = ObjectAnimator.ofFloat(this, "x", 0, topX);
        translateAnimationX.setDuration(mDuration);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        //y方向
        ObjectAnimator translateAnimationY = ObjectAnimator.ofFloat(this, "y", 0, topY);
        translateAnimationY.setDuration(mDuration);
        translateAnimationY.setInterpolator(new DecelerateInterpolator());

        //动画集合
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translateAnimationX).with(translateAnimationY);

        //下降动画
        //抛物线动画，原理：两个位移动画，一个横向匀速移动，一个纵向变速移动，两个动画同时执行，就有了抛物线的效果。
        ObjectAnimator translateAnimationXDown = ObjectAnimator.ofFloat(this, "x", topX,
                topX * 1.4f);
        translateAnimationXDown.setDuration(mDuration / 3);
        translateAnimationXDown.setInterpolator(new LinearInterpolator());

        ObjectAnimator translateAnimationYDown = ObjectAnimator.ofFloat(this, "y", topY,
                topY * 0.6f);
        translateAnimationYDown.setDuration(mDuration / 3);
        translateAnimationYDown.setInterpolator(new AccelerateInterpolator());
        //透明度
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(this, "starAlpha", 255, 0f);
        alphaAnimation.setDuration(mDuration / 3);
        AnimatorSet animatorSetDown = new AnimatorSet();//设置动画播放顺序
        //播放上升动画
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animatorSetDown.play(translateAnimationXDown).with(translateAnimationYDown).with(alphaAnimation);
                animatorSetDown.start();
            }
        });
        animatorSetDown.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isThrowFinish = true;
            }
        });
    }


    public RectF getDsts() {
        mRectF.left = getX() - 50;
        mRectF.top = -getY() - 50;
        mRectF.right = getX() + 50;
        mRectF.bottom = -getY() + 50;
        return mRectF;
    }
}
