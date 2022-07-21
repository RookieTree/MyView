package com.tree.myview.bean;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.bean
 *  @文件名:   DegreeBean
 *  @创建者:   rookietree
 *  @创建时间:  2021/10/14 3:41 下午
 *  @描述：    TODO
 */
public class DegreeBean {

    //matrix x轴旋转角度
    private float matrixRotate;
    //view旋转的角度
    private float rotateAnim;

    public DegreeBean(float matrixRotate, float rotateAnim) {
        this.matrixRotate = matrixRotate;
        this.rotateAnim = rotateAnim;
    }

    public float getMatrixRotate() {
        return matrixRotate;
    }

    public float getRotateAnim() {
        return rotateAnim;
    }
}
