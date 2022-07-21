package com.tree.myview.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tree.myview.util.CommonUtils;

import androidx.annotation.Nullable;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.view
 *  @文件名:   MyTextView
 *  @创建者:   rookietree
 *  @创建时间:  2022/5/26 19:27
 *  @描述：    TODO
 */
public class MyGroup extends LinearLayout {

    private Context mContext;

    public MyGroup(Context context) {
        this(context,null);
    }

    public MyGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
    }

    public void addButton(){
        removeAllViews();
        addView(getTv());
        addView(getTv());
        addView(getTv());
//        addView(new TextView(mContext));
//        addView(new TextView(mContext));
    }
    private int i;
    private TextView getTv(){
        TextView textView = new TextView(mContext);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(CommonUtils.dip2px(mContext,150),CommonUtils.dip2px(mContext,150));
        textView.setLayoutParams(layoutParams);
        textView.setText(i+"wwwwww");
        textView.setBackgroundColor(Color.RED);
        textView.setTextColor(Color.BLUE);
        i++;
        return textView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(CommonUtils.dip2px(mContext,60),CommonUtils.dip2px(mContext,60));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
