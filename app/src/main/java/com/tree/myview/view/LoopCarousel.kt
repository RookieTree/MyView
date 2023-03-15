package com.tree.myview.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.helper.widget.Carousel
import androidx.constraintlayout.motion.widget.MotionLayout
import kotlinx.android.synthetic.main.activity_auto_loop.view.*


/*
 *  @项目名：  RulerDemo 
 *  @包名：    com.tree.myview.fragment
 *  @文件名:   LoopCarousel
 *  @创建者:   rookietree
 *  @创建时间:  2023/3/15 11:34
 *  @描述：    TODO
 */
class LoopCarouse @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : Carousel(context, attrs) {

    var parentView: MotionLayout?=null

    override fun refresh() {
        try {
            super.refresh()
            parentView?.rebuildScene()
        } catch (_: java.lang.Exception) {

        }
    }
}