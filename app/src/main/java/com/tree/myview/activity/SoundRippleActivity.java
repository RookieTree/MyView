package com.tree.myview.activity;

import android.view.View;
import android.widget.Button;

import com.tree.myview.R;
import com.tree.myview.view.SoundRippleView3;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.rulerdemo
 *  @文件名:   SoundRippleActivity
 *  @创建者:   rookietree
 *  @创建时间:  4/21/21 10:52 AM
 *  @描述：    声音波动view
 */
public class SoundRippleActivity extends BaseActivity {

    private Button mBtnStart;
    private Button mBtnStop;
    private SoundRippleView3 sp;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sound_ripple;
    }

    @Override
    public void init() {
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
