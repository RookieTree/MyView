package com.tree.rulerdemo;

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
    private SoundRippleView mSp;
//    private SoundRippleView2 mSp;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnBottomView = findViewById(R.id.btn_bottom_view);
        mBtnRulerView = findViewById(R.id.btn_record_view);
        mBtnSoundView = findViewById(R.id.btn_sound_view);

        mBtnBottomView.setOnClickListener(this);
        mBtnRulerView.setOnClickListener(this);
        mBtnSoundView.setOnClickListener(this);
//        mSp.setInterpolator(new PathInterpolator(0.25f,0.1f,0.25f,1));
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
            default:
                break;
        }
    }
}