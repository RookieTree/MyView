package com.tree.myview;

import android.app.Application;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview
 *  @文件名:   TreeApp
 *  @创建者:   rookietree
 *  @创建时间:  2022/1/17 18:42
 *  @描述：    TODO
 */
public class TreeApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
    }
}
