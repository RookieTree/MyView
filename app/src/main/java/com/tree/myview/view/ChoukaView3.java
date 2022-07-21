package com.tree.myview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.tree.myview.R;
import com.tree.myview.util.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.view
 *  @文件名:   ChoukaView3
 *  @创建者:   rookietree
 *  @创建时间:  2021/10/12 2:31 下午
 *  @描述：    TODO
 */
public class ChoukaView3 extends ConstraintLayout {

    private Context mContext;
    private ImageView mCard;
    private CameraImageView cimg1;
    private CameraImageView cimg2;
    private CameraImageView cimg3;
    private CameraImageView cimg4;
    private CameraImageView cimg5;
    private CameraImageView cimg6;
    private CameraImageView cimg7;
    private CameraImageView cimg8;
    private CameraImageView cimg9;
    private CameraImageView cimg10;
    private Random mRandom;
    private CameraImageView[] cimgs;
    private List<CameraImageView> mCameraImageViews;


    public ChoukaView3(@NonNull Context context) {
        this(context, null);
    }

    public ChoukaView3(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChoukaView3(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View inflate = layoutInflater.inflate(R.layout.view_chouka3, this);
        mCard = inflate.findViewById(R.id.card);
        cimg1 = inflate.findViewById(R.id.cimg1);
        cimg2 = inflate.findViewById(R.id.cimg2);
        cimg3 = inflate.findViewById(R.id.cimg3);
        cimg4 = inflate.findViewById(R.id.cimg4);
        cimg5 = inflate.findViewById(R.id.cimg5);
        cimg6 = inflate.findViewById(R.id.cimg6);
        cimg7 = inflate.findViewById(R.id.cimg7);
        cimg8 = inflate.findViewById(R.id.cimg8);
        cimg9 = inflate.findViewById(R.id.cimg9);
        cimg10 = inflate.findViewById(R.id.cimg10);
        cimgs = new CameraImageView[]{cimg1, cimg2, cimg3, cimg4, cimg5, cimg6, cimg7, cimg8,
                cimg9, cimg10};
        mCameraImageViews = Arrays.asList(cimgs);
        //生成十个旋转角度
        List<Integer> list = new ArrayList<>();
        mRandom = new Random();
        //10-80角度 选取两个
        addRotate(list, 2, 70, 10);
        //100-170角度 选取三个
        addRotate(list, 5, 70, 100);
        //190-260角度 选取两个
        addRotate(list, 7, 70, 190);
        //280-350角度 选取两个
        addRotate(list, 10, 70, 280);
        //设置每个图片的旋转角度
        for (int i = 0; i < list.size(); i++) {
            mCameraImageViews.get(i).setRotate(list.get(i));
        }
        //打乱顺序,让图片随机顺序执行动画
        Collections.shuffle(mCameraImageViews);

        mCard.setOnClickListener(v -> this.startAnimChouka());

    }

    private void startAnimChouka() {
        mCard.animate().alpha(0).start();
        for (int i = 0; i < mCameraImageViews.size(); i++) {
            CameraImageView cameraImageView = mCameraImageViews.get(i);
            cameraImageView.animate().setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    /*int width = cameraImageView.getWidth();
                    int height = cameraImageView.getHeight();
                    Log.d("cameraImageView","w,h:"+ CommonUtils.px2dip(mContext,width));*/
                    cameraImageView.resetImg();
                    cameraImageView.setX(CommonUtils.getScreenWidth(mContext)/2);
                    cameraImageView.setY(CommonUtils.getScreenHeight(mContext)/2);
                }
            });
            postDelayed(cameraImageView::animateSelf, 50L * i);
        }

    }

    private void addRotate(List<Integer> list, int size, int range, int base) {
        while (list.size() < size) {
            int i = mRandom.nextInt(range) + base;
            if (!list.contains(i)) {
                list.add(i);
            }
        }
    }
}
