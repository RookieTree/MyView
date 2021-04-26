package com.tree.myview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tree.myview.R;
import com.tree.myview.bean.StarBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.Nullable;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.view
 *  @文件名:   DianzhanView
 *  @创建者:   rookietree
 *  @创建时间:  4/25/21 3:57 PM
 *  @描述：    点赞view
 */
public class DianzhanView extends View {

    private int width;
    private int cx;
    private int height;
    private int cy;
    private Paint mPaint;
    private Paint mPaintStar;
    private Context mContext;
    private Bitmap mDianzan;

    private boolean isStartThrow;
    private long drawTimeRate = 50;
    private List<StarBean> mStarBeanList;
    private long lastDownTime;

    private final Runnable mDrawRunnable = new Runnable() {
        @Override
        public void run() {
            if (isStartThrow) {
                postInvalidate();
                postDelayed(mDrawRunnable, drawTimeRate);
            }
        }
    };
    private final Runnable finishThrow = new Runnable() {
        @Override
        public void run() {
            isStartThrow = false;
            mStarBeanList.clear();
            postInvalidate();
        }
    };

    public DianzhanView(Context context) {
        this(context, null);
    }

    public DianzhanView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DianzhanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mStarBeanList = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(60);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(Color.MAGENTA);

        mPaintStar = new Paint();
        mPaintStar.setAntiAlias(true);
        mPaintStar.setDither(true);

        mDianzan = BitmapFactory.decodeResource(getResources(), R.mipmap.dianzan);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        cx = w / 2;
        cy = h * 2 / 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画点赞图片
        canvas.translate(cx, cy);
        Rect dst = new Rect(-50, -50, 50, 50);
        canvas.drawBitmap(mDianzan, null, dst, mPaint);
        canvas.drawText("点击点赞按钮有惊喜", 0,  130, mPaint);
        if (!isStartThrow) {
            return;
        }
        Iterator<StarBean> iterator = mStarBeanList.iterator();
        while (iterator.hasNext()) {
            StarBean next = iterator.next();
            if (next.isThrowFinish()) {
                iterator.remove();
                continue;
            }
            mPaintStar.setAlpha((int) next.getStarAlpha());
            canvas.drawBitmap(next.getBitmap(), null, next.getDsts(), mPaintStar);
        }
    }

    public void startThrowStar() {
        isStartThrow = true;
        for (int i = 0; i < 5; i++) {
            StarBean starBean = new StarBean(mContext);
            mStarBeanList.add(starBean);
            starBean.startThrow();
        }
        mDrawRunnable.run();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (System.currentTimeMillis() - lastDownTime < 200) {
                    lastDownTime = System.currentTimeMillis();
                    return true;
                }
                lastDownTime = System.currentTimeMillis();
                float downX = event.getX();
                float downY = event.getY();
                if (Math.abs(downX - cx) < 50 && Math.abs(downY - cy) < 50) {
                    startThrowStar();
                }
                break;
            case MotionEvent.ACTION_UP:
                removeCallbacks(finishThrow);
                postDelayed(finishThrow, 2000);
                break;
            default:
                break;
        }
        return true;
    }
}
