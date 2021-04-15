package com.tree.rulerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnBottomView;
    private Button mBtnRulerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnBottomView = findViewById(R.id.btn_bottom_view);
        mBtnRulerView = findViewById(R.id.btn_record_view);
        mBtnBottomView.setOnClickListener(this);
        mBtnRulerView.setOnClickListener(this);
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
            default:
                break;
        }
    }
}