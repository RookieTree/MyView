package com.tree.myview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.view
 *  @文件名:   UXScrollView
 *  @创建者:   rookietree
 *  @创建时间:  2022/3/1 17:57
 *  @描述：    TODO
 */
public class UXScrollView extends ConstraintLayout {

    //可滑动的最大距离
    int scrollableHeight=0;

    public UXScrollView(@NonNull Context context) {
        this(context, null);
    }

    public UXScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UXScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.measure(widthMeasureSpec, 0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //垂直布局
        int layoutLeft = 0;
        int layoutTop = 0;
        int layoutRight = right;
        int layoutBottom = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            layoutBottom = layoutTop + child.getMeasuredHeight();
            child.layout(layoutLeft, layoutTop, layoutRight, layoutBottom);
            layoutTop = layoutBottom;
        }
        //计算出可滑动的距离，要减去view的高度
        scrollableHeight=layoutBottom-getHeight();
    }

    private int getScrollableHeight(int y) {
        return Math.max(0, Math.min(scrollableHeight, y));
    }

    int[] mLastTouchLocation = new int[2];

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastTouchLocation[0] = x;
                mLastTouchLocation[1] = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int lx = mLastTouchLocation[0];
                int ly = mLastTouchLocation[1];
                //计算移动的距离
                int dy = y - ly;
                //记下最后点的位置
                mLastTouchLocation[0] = x;
                mLastTouchLocation[1] = y;
                //计算出目标的x,y坐标点，由于view要求滑动的方向，应该与手指滑动的方向一致，所以要取反
                int ty = getScrollY() + (-dy);
                scrollTo(0, ty);
                postInvalidate();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
