package com.tree.myview.activity;

import android.util.Log;

import com.tree.myview.R;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.activity
 *  @文件名:   ChoukaActivity
 *  @创建者:   rookietree
 *  @创建时间:  2021/10/11 3:28 下午
 *  @描述：    利用组合控件实现抽卡
 */
public class ChoukaActivity extends BaseActivity{
    @Override
    public int getLayoutId() {
        return R.layout.activity_chouka;
    }

    @Override
    public void init() {

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ChoukaActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ChoukaActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ChoukaActivity","onDestroy");
    }
}
