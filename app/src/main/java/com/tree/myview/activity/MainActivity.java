package com.tree.myview.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.tree.myview.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnBottomView;
    private Button mBtnRulerView;
    private Button mBtnSoundView;
    private Button mBtnBallView;
    private Button mBtnRefreshView;
    private Button mBtnDianzhanView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        mBtnBottomView = findViewById(R.id.btn_bottom_view);
        mBtnRulerView = findViewById(R.id.btn_record_view);
        mBtnSoundView = findViewById(R.id.btn_sound_view);
        mBtnBallView = findViewById(R.id.btn_ball_view);
        mBtnRefreshView = findViewById(R.id.btn_refresh_view);
        mBtnDianzhanView = findViewById(R.id.btn_dianzhan_view);

        mBtnBottomView.setOnClickListener(this);
        mBtnRulerView.setOnClickListener(this);
        mBtnSoundView.setOnClickListener(this);
        mBtnBallView.setOnClickListener(this);
        mBtnRefreshView.setOnClickListener(this);
        mBtnDianzhanView.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bottom_view:
                startActivity(new Intent(this, BottomViewActivity.class));
                break;
            case R.id.btn_record_view:
                startActivity(new Intent(this, RecordViewActivity.class));
                break;
            case R.id.btn_sound_view:
                startActivity(new Intent(this, SoundRippleActivity.class));
                break;
            case R.id.btn_ball_view:
                startActivity(new Intent(this, MotionBallActivity.class));
                break;
            case R.id.btn_refresh_view:
                startActivity(new Intent(this, RefreshActivity.class));
                break;
            case R.id.btn_dianzhan_view:
                startActivity(new Intent(this, DianzhanActivity.class));
                break;
            default:
                break;
        }
    }

}