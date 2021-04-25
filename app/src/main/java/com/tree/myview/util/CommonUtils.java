package com.tree.myview.util;/*
 * 深圳市有信网络技术有限公司
 * Copyright (c) 2016 All Rights Reserved.
 */


import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;


public class CommonUtils {
    /**
     * 获取手机屏幕宽度通用方法
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (null == context) {
            return 0;
        }
        Point sizePoint = new Point();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (manager != null) {
            Display display = manager.getDefaultDisplay();
            display.getSize(sizePoint);
        }
        return sizePoint.x;
    }

    /**
     * 获取手机屏幕高度通用方法
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (null == context) {
            return 0;
        }
        Point sizePoint = new Point();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (manager != null) {
            Display display = manager.getDefaultDisplay();
            display.getSize(sizePoint);
        }
        return sizePoint.y;
    }

    /**
     * 获取手机屏幕高度
     * 为了解决getScreenHeight（）方法，全面屏手机高度减去了StatusBar的高度
     *
     * @param context
     * @return
     */
    public static int getRealScreenHeight(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getRealMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 屏幕尺寸dip转px换算方法
     *
     * @param context
     * @param dipValue dip值
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        if (null == context) {
            return 0;
        }
        final float scaleValue = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scaleValue + 0.5f);
    }

    public static float getDensity(Context context) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 屏幕尺寸px转dip换算方法
     *
     * @param context
     * @param pxValue px值
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        if (null == context) {
            return 0;
        }
        final float scaleValue = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scaleValue + 0.5f);
    }


    /**
     * SP 转 PX
     *
     * @param context Context
     * @param spValue sp值
     * @return
     */
    public static float sp2px(Context context, float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取顶部状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取低部导航栏高度
     */
    public static int getNavigationBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 将毫秒转成分秒
     * @param duration
     * @return
     */
    public static String timeParse(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((float) seconds / 1000);
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }

}
