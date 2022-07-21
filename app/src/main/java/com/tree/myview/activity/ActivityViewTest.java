package com.tree.myview.activity;

import com.tree.myview.R;
import com.tree.myview.view.MyGroup;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.activity
 *  @文件名:   ActivityViewTest
 *  @创建者:   rookietree
 *  @创建时间:  2021/11/5 3:38 下午
 *  @描述：    TODO
 */
public class ActivityViewTest extends BaseActivity{

    @Override
    public int getLayoutId() {
        return R.layout.fragment_scroll;
    }

    @Override
    public void init() {
        MyGroup myGroup = findViewById(R.id.mygroup);
        myGroup.addButton();
    }
}
