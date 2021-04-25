package com.tree.myview.activity;

import android.widget.Toast;

import com.tree.myview.R;
import com.tree.myview.view.RefreshView;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.rulerdemo
 *  @文件名:   RefreshActivity
 *  @创建者:   rookietree
 *  @创建时间:  4/23/21 3:36 PM
 *  @描述：    刷新view
 */
public class RefreshActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_refresh;
    }

    @Override
    public void init() {
        RefreshView refreshView = findViewById(R.id.refresh_view);
        refreshView.setOnRefreshCallback(new RefreshView.RefreshCallback() {
            @Override
            public void refreshFinish() {
                Toast.makeText(RefreshActivity.this, "刷新完毕", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
