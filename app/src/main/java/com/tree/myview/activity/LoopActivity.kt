package com.tree.myview.activity

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.helper.widget.Carousel
import coil.load
import coil.size.Scale
import com.tree.myview.R

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.activity
 *  @文件名:   LoopActivity
 *  @创建者:   rookietree
 *  @创建时间:  2023/3/10 15:46
 *  @描述：
 */
class LoopActivity : BaseActivity() {
    private val imgs = intArrayOf(
        R.drawable.look1,
        R.drawable.look2,
        R.drawable.look3,
        R.drawable.look4,
        R.drawable.look5
    )

    override fun getLayoutId(): Int {
        return R.layout.activity_loop
    }

    override fun init() {
        val carousel = findViewById<Carousel>(R.id.carousel)
        carousel.setAdapter(object : Carousel.Adapter {
            override fun count(): Int {
                return imgs.size
            }

            override fun populate(view: View, index: Int) {
                if (view is ImageView) {
                    view.setImageResource(imgs[index])
                }
            }

            override fun onNewItem(index: Int) {
            }
        })
    }
}