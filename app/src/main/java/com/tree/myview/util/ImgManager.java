package com.tree.myview.util;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.util
 *  @文件名:   ImgManager
 *  @创建者:   rookietree
 *  @创建时间:  4/25/21 4:13 PM
 *  @描述：    TODO
 */
class ImgManager {
    private static final ImgManager ourInstance = new ImgManager();

    static ImgManager getInstance() {
        return ourInstance;
    }

    private ImgManager() {
    }
}
