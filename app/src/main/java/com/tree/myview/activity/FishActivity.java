package com.tree.myview.activity;

import com.tree.myview.R;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.activity
 *  @文件名:   FishActivity
 *  @创建者:   rookietree
 *  @创建时间:  2022/4/7 16:19
 *  @描述：    TODO
 */
public class FishActivity extends BaseActivity {

//    private FishView mFishView;
//    private ImageView mIv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fish;
    }

    @Override
    public void init() {
//        mFishView = findViewById(R.id.fish);
//        mIv = findViewById(R.id.iv);
//        mIv.setImageDrawable(new FishDrawable());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
