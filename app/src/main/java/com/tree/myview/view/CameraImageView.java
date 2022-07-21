package com.tree.myview.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.tree.myview.R;
import com.tree.myview.util.CommonUtils;

import androidx.appcompat.widget.AppCompatImageView;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.view
 *  @文件名:   CameraImageView
 *  @创建者:   rookietree
 *  @创建时间:  2021/10/12 2:46 下午
 *  @描述：    TODO
 */
public class CameraImageView extends AppCompatImageView {

    private Context mContext;
    private float rotate;
    private final float mRadius;

    public CameraImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mContext = context;
        setScaleType(ScaleType.MATRIX);
        mRadius = CommonUtils.getScreenWidth(mContext)*2;
        Glide.with(mContext).load(R.mipmap.baolong2).into(this);
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
        setRotation(rotate);
        postDelayed(() -> rotateImg(30), 500);
    }

    public float getRotate() {
        return rotate;
    }

    public void animateSelf() {
        float translateX = mRadius*getCosDeg(rotate)*(-1);
        float translateY = mRadius*getSinDeg(rotate)*(-1);
        Log.d("CameraImageViewx","rotate:"+rotate+",translateX:"+translateX+",translateY:"+translateY);
        animate().translationX(translateX).translationY(translateY).scaleX(2).scaleY(2).alpha(1).setDuration(1000).start();
    }

    /**
     * 根据传入的角度得到值
     * @param deg
     * @return
     */
    private float getTanDeg(float deg) {
        return (float) Math.tan(deg * Math.PI/180);
    }

    /**
     * 根据传入的角度得到值
     * @param deg
     * @return
     */
    private float getSinDeg(float deg) {
        return (float) Math.sin(deg * Math.PI/180);
    }

    /**
     * 根据传入的角度得到值
     * @param deg
     * @return
     */
    private float getCosDeg(float deg) {
        return (float) Math.cos(deg * Math.PI/180);
    }

    /**
     * 设置旋转中心非常必要
     *
     * @param rotateX
     */
    private void rotateImg(int rotateX) {
        Matrix matrix = getMatrix(rotateX);
        if (rotateX >= 0) {
            // 旋转中心为(0,height/2)
            matrix.preTranslate(0, -getHeight() / 2);
            matrix.postTranslate(0, getHeight() / 2);
        } else {
            // 旋转中心为(width,height/2)
            matrix.preTranslate(-getWidth(), getHeight() / 2);
            matrix.postTranslate(getWidth(), getHeight() / 2);
        }
        setImageMatrix(matrix);
    }

    public void resetImg(){
        Matrix matrix = getMatrix();
        setImageMatrix(matrix);
    }

    private Matrix getMatrix(int rotate) {
        Matrix matrix = new Matrix();
        Camera camera = new Camera();
        camera.save();
        camera.rotateY(rotate);
//        camera.rotateX(rotate);
        camera.getMatrix(matrix);
        camera.restore();
        return matrix;
    }
}
