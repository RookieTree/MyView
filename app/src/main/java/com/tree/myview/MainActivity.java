package com.tree.myview;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnBottomView;
    private Button mBtnRulerView;
    private Button mBtnSoundView;
    private Button mBtnBallView;
    private Button mBtnRefreshView;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnBottomView = findViewById(R.id.btn_bottom_view);
        mBtnRulerView = findViewById(R.id.btn_record_view);
        mBtnSoundView = findViewById(R.id.btn_sound_view);
        mBtnBallView = findViewById(R.id.btn_ball_view);
        mBtnRefreshView = findViewById(R.id.btn_refresh_view);

        mBtnBottomView.setOnClickListener(this);
        mBtnRulerView.setOnClickListener(this);
        mBtnSoundView.setOnClickListener(this);
        mBtnBallView.setOnClickListener(this);
        mBtnRefreshView.setOnClickListener(this);
    }

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
            default:
                break;
        }
    }

}