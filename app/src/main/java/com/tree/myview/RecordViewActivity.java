package com.tree.myview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.rulerdemo
 *  @文件名:   RecordViewActivity
 *  @创建者:   rookietree
 *  @创建时间:  4/15/21 3:14 PM
 *  @描述：    TODO
 */
public class RecordViewActivity extends AppCompatActivity {

    private RecordWaveView mRecordWaveView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_view);
        mRecordWaveView = findViewById(R.id.record_view);

        mRecordWaveView.locateTo(1000);
    }
}
