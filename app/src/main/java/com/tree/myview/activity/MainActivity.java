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
    private Button mBtnMeituanView;
    private Button mBtnChoukaView;
    private Button mBtnChoukaView2;
    private Button mBtnChoukaView3;
    private Button mBtnViewTest;
    private Button mBtnScroll;
    private Button mBtnBigBitmap;
    private Button mBtnFish;
    private Button mBtnActionBall;
    private Button mBtnLoop;
    private Button mBtnAutoLoop;


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
        mBtnMeituanView = findViewById(R.id.btn_meituan_view);
        mBtnChoukaView = findViewById(R.id.btn_chouka);
        mBtnChoukaView2 = findViewById(R.id.btn_chouka2);
        mBtnChoukaView3 = findViewById(R.id.btn_chouka3);
        mBtnViewTest = findViewById(R.id.btn_view_test);
        mBtnScroll = findViewById(R.id.btn_uxscroll);
        mBtnBigBitmap = findViewById(R.id.btn_big_bitmap);
        mBtnFish = findViewById(R.id.btn_fish);
        mBtnActionBall = findViewById(R.id.btn_actionball);
        mBtnLoop = findViewById(R.id.btn_loop);
        mBtnAutoLoop = findViewById(R.id.btn_auto_loop);

        mBtnBottomView.setOnClickListener(this);
        mBtnRulerView.setOnClickListener(this);
        mBtnSoundView.setOnClickListener(this);
        mBtnBallView.setOnClickListener(this);
        mBtnRefreshView.setOnClickListener(this);
        mBtnDianzhanView.setOnClickListener(this);
        mBtnMeituanView.setOnClickListener(this);
        mBtnChoukaView.setOnClickListener(this);
        mBtnChoukaView2.setOnClickListener(this);
        mBtnChoukaView3.setOnClickListener(this);
        mBtnViewTest.setOnClickListener(this);
        mBtnScroll.setOnClickListener(this);
        mBtnBigBitmap.setOnClickListener(this);
        mBtnFish.setOnClickListener(this);
        mBtnActionBall.setOnClickListener(this);
        mBtnLoop.setOnClickListener(this);
        mBtnAutoLoop.setOnClickListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
            case R.id.btn_meituan_view:
                startActivity(new Intent(this, MeituanBottomActivity.class));
                break;
            case R.id.btn_chouka:
                startActivity(new Intent(this, ChoukaActivity.class));
                break;
            case R.id.btn_chouka2:
                startActivity(new Intent(this, ChoukaActivity2.class));
                break;
            case R.id.btn_chouka3:
                startActivity(new Intent(this, ChoukaActivity3.class));
                break;
            case R.id.btn_view_test:
                startActivity(new Intent(this, ActivityViewTest.class));
                break;
            case R.id.btn_uxscroll:
                startActivity(new Intent(this, ScrollViewActivity.class));
                break;
            case R.id.btn_big_bitmap:
                startActivity(new Intent(this, BigBitmapActivity.class));
                break;
            case R.id.btn_fish:
                startActivity(new Intent(this, FishActivity.class));
                break;
            case R.id.btn_actionball:
                startActivity(new Intent(this, ActionBallActivity.class));
                break;
            case R.id.btn_loop:
                startActivity(new Intent(this, LoopActivity.class));
                break;
            case R.id.btn_auto_loop:
                startActivity(new Intent(this, AutoLoopActivity.class));
                break;
            default:
                break;
        }
    }

}