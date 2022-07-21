package com.tree.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.view
 *  @文件名:   ArcView
 *  @创建者:   rookietree
 *  @创建时间:  2021/11/3 6:01 下午
 *  @描述：    TODO
 */
public class ArcView extends View {

    private Paint mPaint=new Paint();
    private float degree;
    private RectF mRectF;

    public ArcView(Context context) {
        super(context);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                invalidate();
                num++;
                Log.d("ArcView", "receiveTime:" + System.currentTimeMillis());
            }
        }
    };

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF = new RectF(0,0,w,h);
    }

    private long currTime = 0;
    private int num=0;
    boolean isStart;

    public void startDraw(){
        isStart=true;
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if (!isStart){
            return;
        }
        degree+=5;
        canvas.drawArc(mRectF,0,degree,true,mPaint);
        Log.d("ArcView","draw time:"+ (System.currentTimeMillis() - currTime));
        currTime = System.currentTimeMillis();
        if (num==10){
            return;
        }
        num++;
        invalidate();
    }
}
