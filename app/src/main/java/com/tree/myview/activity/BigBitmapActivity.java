package com.tree.myview.activity;

import com.tree.myview.R;
import com.tree.myview.adapter.BigBitmapAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.activity
 *  @文件名:   BigBitmapActivity
 *  @创建者:   rookietree
 *  @创建时间:  2022/3/22 10:45
 *  @描述：    TODO
 */
public class BigBitmapActivity extends BaseActivity{

    private RecyclerView mRv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_big_bitmap;
    }

    @Override
    public void init() {
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        BigBitmapAdapter bigBitmapAdapter=new BigBitmapAdapter();
        List<String> list=new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add(String.valueOf(i));
        }
        bigBitmapAdapter.setNewInstance(list);
        mRv.setAdapter(bigBitmapAdapter);
    }
}
