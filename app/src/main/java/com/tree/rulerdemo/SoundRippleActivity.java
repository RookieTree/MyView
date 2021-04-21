package com.tree.rulerdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.rulerdemo
 *  @文件名:   SoundRippleActivity
 *  @创建者:   rookietree
 *  @创建时间:  4/21/21 10:52 AM
 *  @描述：    声音波动view
 */
public class SoundRippleActivity extends AppCompatActivity {

    private Button mBtnStart;
    private Button mBtnStop;
    private SoundRippleView sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_ripple);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStop = findViewById(R.id.btn_stop);
        sp = findViewById(R.id.sp);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.start();
            }
        });
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.stop();
            }
        });
    }
}
