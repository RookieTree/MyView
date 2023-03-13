package com.tree.myview.activity;

import android.view.View;
import android.widget.ImageView;

import com.tree.myview.R;

import androidx.constraintlayout.helper.widget.Carousel;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.activity
 *  @文件名:   LoopActivity
 *  @创建者:   rookietree
 *  @创建时间:  2023/3/10 15:46
 *  @描述：    TODO
 */
public class LoopActivity extends BaseActivity{

    private int[] imgs={R.mipmap.meinv,R.mipmap.meinv,R.mipmap.meinv4,R.mipmap.meinv5,R.mipmap.meinv6,R.mipmap.meinv10};
    @Override
    public int getLayoutId() {
        return R.layout.activity_loop;
    }

    @Override
    public void init() {
        Carousel carousel= findViewById(R.id.carousel);
        carousel.setAdapter(new Carousel.Adapter() {
            @Override
            public int count() {
                return imgs.length;
            }

            @Override
            public void populate(View view, int index) {
                if (view instanceof ImageView){
                    ((ImageView) view).setImageResource(imgs[index]);
                }
            }

            @Override
            public void onNewItem(int index) {

            }
        });
    }
}
