package com.tree.myview.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.activity
 *  @文件名:   BaseActivity
 *  @创建者:   rookietree
 *  @创建时间:  4/25/21 3:52 PM
 *  @描述：    jilei
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init();
    }

    public abstract int getLayoutId();
    public abstract void init();
}
