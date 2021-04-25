package com.tree.myview.activity;

import com.tree.myview.R;
import com.tree.myview.view.RecordWaveView;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.rulerdemo
 *  @文件名:   RecordViewActivity
 *  @创建者:   rookietree
 *  @创建时间:  4/15/21 3:14 PM
 *  @描述：    TODO
 */
public class RecordViewActivity extends BaseActivity {

    private RecordWaveView mRecordWaveView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_record_view;
    }

    @Override
    public void init() {
        mRecordWaveView = findViewById(R.id.record_view);

        mRecordWaveView.locateTo(1000);
    }
}
