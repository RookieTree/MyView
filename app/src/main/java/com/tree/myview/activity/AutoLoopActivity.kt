package com.tree.myview.activity

import android.widget.Button
import com.tree.myview.R
import com.tree.myview.view.LoopBanner

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.activity
 *  @文件名:   LoopActivity
 *  @创建者:   rookietree
 *  @创建时间:  2023/3/10 15:46
 *  @描述：    TODO
 */
class AutoLoopActivity : BaseActivity() {

    private val imgs: MutableList<Int> = mutableListOf(
        R.drawable.look1,
        R.drawable.look2,
        R.drawable.look3,
        R.drawable.look4,
        R.drawable.look5
    )

    lateinit var loopBanner: LoopBanner

    override fun getLayoutId(): Int {
        return R.layout.activity_auto_loop
    }

    override fun init() {
        val btnLoop = findViewById<Button>(R.id.btn_loop)
        loopBanner = findViewById(R.id.loop_banner)
        loopBanner.setData(imgs)
        btnLoop.setOnClickListener {
            imgs.removeAt(0)
            imgs.removeAt(0)
            loopBanner.setData(imgs)
        }
    }
}