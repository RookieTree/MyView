package com.tree.myview.util;

import com.tree.myview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.util
 *  @文件名:   ImgManager
 *  @创建者:   rookietree
 *  @创建时间:  4/25/21 4:13 PM
 *  @描述：    TODO
 */
public class ImgManager {
    private int[] res = {R.mipmap.emoji1, R.mipmap.emoji2, R.mipmap.emoji3, R.mipmap.emoji14,
            R.mipmap.emoji5, R.mipmap.emoji6, R.mipmap.emoji7, R.mipmap.emoji18, R.mipmap.emoji9,
            R.mipmap.emoji10, R.mipmap.emoji11, R.mipmap.emoji12, R.mipmap.emoji13,
            R.mipmap.emoji14, R.mipmap.emoji15, R.mipmap.emoji16, R.mipmap.emoji17,
            R.mipmap.emoji18, R.mipmap.emoji19, R.mipmap.emoji20};
    private static final ImgManager ourInstance = new ImgManager();
    private final Random mRandom;

    public static ImgManager getInstance() {
        return ourInstance;
    }

    private ImgManager() {
        mRandom = new Random();
    }

    /**
     * 获取随机的五张图片
     *
     * @return
     */
    public List<Integer> getImgs() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(res[mRandom.nextInt(res.length)]);
        }
        return list;
    }

    /**
     * 获取随机的一张图片
     *
     * @return
     */
    public Integer getImg() {
        return res[mRandom.nextInt(res.length)];
    }
}
