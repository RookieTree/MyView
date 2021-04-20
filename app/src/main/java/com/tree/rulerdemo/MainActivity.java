package com.tree.rulerdemo;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnBottomView;
    private Button mBtnRulerView;
    private SoundRippleView mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnBottomView = findViewById(R.id.btn_bottom_view);
        mBtnRulerView = findViewById(R.id.btn_record_view);
        mSp = findViewById(R.id.sp);
        mBtnBottomView.setOnClickListener(this);
        mBtnRulerView.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bottom_view:
//                startActivity(new Intent(this, BottomViewActivity.class));
                mSp.start();
                break;
            case R.id.btn_record_view:
//                startActivity(new Intent(this, RecordViewActivity.class));
                mSp.stop();
                break;
            default:
                break;
        }
    }
}