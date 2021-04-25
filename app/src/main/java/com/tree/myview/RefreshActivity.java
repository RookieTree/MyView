package com.tree.myview;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.rulerdemo
 *  @文件名:   RefreshActivity
 *  @创建者:   rookietree
 *  @创建时间:  4/23/21 3:36 PM
 *  @描述：    刷新view
 */
public class RefreshActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        RefreshView refreshView = findViewById(R.id.refresh_view);
        refreshView.setOnRefreshCallback(new RefreshView.RefreshCallback() {
            @Override
            public void refreshFinish() {
                Toast.makeText(RefreshActivity.this, "刷新完毕", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
