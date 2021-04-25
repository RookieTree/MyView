package com.tree.myview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

/*
 *  @项目名：  DatouApp
 *  @包名：    com.tree.datouapp.view
 *  @文件名:   RecordWaveView
 *  @创建者:   rookietree
 *  @创建时间:  3/24/21 5:42 PM
 *  @描述：    TODO
 */
public class RecordWaveView extends View {

    private static final String TAG = "RecordWaveView";

    private Context mContext;
    //刻度距离值
    private float mRulerSpace;
    //刻度线高度
    private float mRulerSpaceHeight;
    //整值刻度线高度
    private float mRulerSpaceMaxHeight;
    //时间刻度尺
    private Paint timeRulerPaint;
    //时间值
    private Paint timeNumberPaint;
    //时间竖线进度
    private Paint timeIndicatPaint;
    //声纹波形
    private Paint timeWavePaint;

    //一屏幕内显示刻度数
    private static final int SCREEN_RULER_COUNT = 30;

    //绘制时长间隔
    private static final int DRAW_SPACE_TIME = 200;
    //秒与秒之间间隔的刻度数
    private static final int SECOND_SPACE_NUM = 5;
    //单位一秒
    private static final int SECOND = 1000;

    //一刻度代表的毫秒值 默认200ms
    private static final int SPACE_MILLISECOND = SECOND / SECOND_SPACE_NUM;

    /**
     * 绘制需要的参数，包括文字大小，线宽，线长，文字与线之间的间距，每个刻度之间的间距
     */
    private float mTextSize, mPathWidth, mLineNumHSpace;
    //最大录音时长
    private long maxSeconds = 10 * 1000;
    private float mRuleTotalCount;//总刻度数
    private float mRuleTotalDistance;//总刻度长度

    //屏幕内 开始时间和结束时间
    private long mRuleStartTime;
    private long mRuleEndTime;

    protected Scroller mScroller;

    private float height;
    private float width;
    private ValueAnimator mValueAnimator;
    private float mDistanceX;

    public RecordWaveView(Context context) {
        this(context, null);
    }

    public RecordWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mScroller = new Scroller(context);
        //刻度间隔距离
        mRulerSpace = CommonUtils.dip2px(context, 10);
        mRulerSpaceHeight = CommonUtils.dip2px(context, 8);
        mRulerSpaceMaxHeight = mRulerSpaceHeight * 2;
        //因为最开始和结束位置会设置一个刻度间隔，所以总数会+2
        mRuleTotalCount = maxSeconds / SECOND * 5 + 2;
        mRuleTotalDistance = mRuleTotalCount * mRulerSpace;

        mTextSize = CommonUtils.dip2px(context, 10);
        mPathWidth = CommonUtils.dip2px(context, 0.5f);
        //线和数字竖向的间距
        mLineNumHSpace = CommonUtils.dip2px(context, 3);

        timeRulerPaint = new Paint();
        timeRulerPaint.setColor(Color.parseColor("#565151"));
        timeRulerPaint.setStyle(Paint.Style.STROKE);
        timeRulerPaint.setStrokeWidth(mPathWidth);
        timeRulerPaint.setAntiAlias(true);
        //设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        timeRulerPaint.setDither(true);

        timeNumberPaint = new Paint();
        timeNumberPaint.setColor(Color.parseColor("#565151"));
        timeNumberPaint.setAntiAlias(true);
        timeNumberPaint.setTextSize(mTextSize);
        timeNumberPaint.setTextAlign(Paint.Align.CENTER);

        timeIndicatPaint = new Paint();
        timeIndicatPaint.setColor(Color.RED);
        timeIndicatPaint.setStyle(Paint.Style.STROKE);
        timeIndicatPaint.setStrokeWidth(mPathWidth);
        timeIndicatPaint.setAntiAlias(true);
        timeRulerPaint.setDither(true);

        //前3s以及最后3s 尺子不动
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                mDistanceX = mRuleTotalDistance * animatedValue;
                Log.d("onAnimationUpdate", "curdistance:" + mDistanceX);
                postInvalidate();
            }
        });
        mValueAnimator.setDuration(maxSeconds);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        mRulerSpace = width / SCREEN_RULER_COUNT;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawLine(canvas);
        onDrawRuler(canvas);
        onDrawIndicate(canvas);
    }

    /**
     * 画刻度直线
     *
     * @param canvas
     */
    private void onDrawLine(Canvas canvas) {
        canvas.drawLine(0, height, mRuleTotalDistance, height, timeRulerPaint);
    }

    /**
     * 画刻度
     *
     * @param canvas
     */
    private void onDrawRuler(Canvas canvas) {
        long tempTime = mRuleStartTime;
//        if (tempTime == 0) {
//            canvas.translate(mRulerSpace, 0);
//        } else {
//            canvas.translate(0, 0);
//        }
        float distance = 0;
        float currentPlayTime = mValueAnimator.getCurrentPlayTime();

        if (currentPlayTime < 3000) {
            distance = 0;
        } else if (currentPlayTime < 7000) {
            distance = -1 * (currentPlayTime / 1000 - 3) * SECOND_SPACE_NUM * mRulerSpace;
//            distance = -1 * (mDistanceX- 3 * SECOND_SPACE_NUM * mRulerSpace);
        } else {
            distance = -4 * SECOND_SPACE_NUM * mRulerSpace;
        }

        //绘制刻度条以及对应时间值
        for (int i = 0; i <= mRuleTotalCount; i++) {
            if (tempTime % SECOND == 0) { //整值
                canvas.drawLine(i * mRulerSpace + distance, height, i * mRulerSpace + distance,
                        height - mRulerSpaceMaxHeight, timeRulerPaint);
                //整值文字
                canvas.drawText(CommonUtils.timeParse(tempTime), i * mRulerSpace + distance,
                        height - mRulerSpaceMaxHeight - mLineNumHSpace, timeNumberPaint);
            } else {
                canvas.drawLine(i * mRulerSpace + distance, height, i * mRulerSpace + distance,
                        height - mRulerSpaceHeight, timeRulerPaint);
            }
            //每个刻度加两百毫秒
            tempTime += SPACE_MILLISECOND;
        }
    }

    /**
     * 画刻度直线
     *
     * @param canvas
     */
    private void onDrawIndicate(Canvas canvas) {
        //前3s和后3s动
        float currentPlayTime = mValueAnimator.getCurrentPlayTime();
        float distance = 0;

        if (currentPlayTime < 3000) {
            distance = mDistanceX;
        } else if (currentPlayTime < 7000) {
            distance = 3 * SECOND_SPACE_NUM * mRulerSpace;
        } else {
                distance =
                        3 * SECOND_SPACE_NUM*mRulerSpace + (currentPlayTime / 1000 - 7) *
                        SECOND_SPACE_NUM *mRulerSpace;
//            distance = mDistanceX - 4 * SECOND_SPACE_NUM * mRulerSpace;
        }

        canvas.drawLine(distance, 0, distance, height, timeIndicatPaint);
    }

    /**
     * 使用scroller需要重写
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        // 判断Scroller是否执行完毕
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //通过重绘来不断调用computeScroll
            invalidate();
        }
    }

    private void scrollToScale() {
        smoothScrollBy((int) mRulerSpace, 0);
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
    }

    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }


    /**
     * 把刻度尺定位到指定位置
     */
    public void locateTo(long millisecond) {
       /* mRuleStartTime = millisecond;
        //一屏幕最多显示6s,当传入的值在最后6s范围内，就强行赋值为最后6s的起始值，因为尾端需要多画个空格，所以-1
        if (mRuleStartTime > MAX_SECONDS - ((SCREEN_RULER_COUNT - 1) * 200)) {
            mRuleStartTime = MAX_SECONDS - ((SCREEN_RULER_COUNT - 1) * 200);
        }*/
        float dis = (mRuleStartTime / maxSeconds) * mRuleTotalDistance;
        mValueAnimator.start();
//        smoothScrollTo((int) dis,  0);
//        scrollToScale();
//        invalidate();
    }

}
