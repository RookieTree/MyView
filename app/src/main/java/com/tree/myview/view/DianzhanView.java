package com.tree.myview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.tree.myview.R;

import androidx.annotation.Nullable;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.view
 *  @文件名:   DianzhanView
 *  @创建者:   rookietree
 *  @创建时间:  4/25/21 3:57 PM
 *  @描述：    TODO
 */
public class DianzhanView extends View {

    private int width;
    private int cx;
    private int height;
    private int cy;
    private Paint mPaint;
    private Context mContext;
    private Bitmap mDianzan;

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
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mDianzan = BitmapFactory.decodeResource(getResources(), R.mipmap.dianzan);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        cx = w / 2;
        cy = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(cx, cy);
        Rect dst = new Rect(-50, -50, 50, 50);
        canvas.drawBitmap(mDianzan, null, dst, mPaint);

    }
}
