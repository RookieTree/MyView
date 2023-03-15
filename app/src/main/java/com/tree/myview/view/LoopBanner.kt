package com.tree.myview.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.constraintlayout.motion.widget.MotionLayout
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import com.tree.myview.R
import java.lang.ref.WeakReference


/*
 *  @项目名：  RulerDemo 
 *  @包名：    com.tree.myview.view
 *  @文件名:   LoopBanner
 *  @创建者:   rookietree
 *  @创建时间:  2023/3/15 11:46
 *  @描述：
 */
class LoopBanner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : MotionLayout(context, attrs) {

    companion object {
        const val MSG_START_LOOP = 1
    }

    private var carousel: LoopCarouse
    private var imgs: MutableList<Int> = mutableListOf(
        R.drawable.look1
    )
    private var currPosition = 0
    private var duration = 1500L
    private var isLoop: Boolean = false
    private var startX: Int = 0
    private var startY: Int = 0

    class LoopHandler(var weakReference: WeakReference<LoopBanner>) :
        Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val loopBanner = weakReference.get() ?: return
            if (msg.what == 1) {
                loopBanner.carousel.transitionToIndex(loopBanner.currPosition + 1, 200)
                loopBanner.startLoop()
            }
        }
    }

    private var loopHandler: LoopHandler

    init {
        val inflate = View.inflate(context, R.layout.view_auto_loop, this)
        carousel = inflate.findViewById(R.id.carousel)
        val ivLeft = inflate.findViewById<ShapeableImageView>(R.id.left)
        val ivShowLeft = inflate.findViewById<ShapeableImageView>(R.id.show_left)
        val ivCenter = inflate.findViewById<ShapeableImageView>(R.id.center)
        val ivShowRight = inflate.findViewById<ShapeableImageView>(R.id.show_right)
        val ivRight = inflate.findViewById<ShapeableImageView>(R.id.right)
        loopHandler = LoopHandler(WeakReference(this))
        carousel.parentView = this
        setAdapter()
    }

    fun setData(data: MutableList<Int>) {
        imgs.clear()
        imgs.addAll(data)
        carousel.refresh()
        startLoop()
    }

    private fun setAdapter() {
        carousel.setAdapter(object : Carousel.Adapter {
            override fun count(): Int {
                return Int.MAX_VALUE
            }

            override fun populate(view: View, index: Int) {
                if (view is ImageView) {
                    view.load(imgs.get(index % imgs.size))
                }
            }

            override fun onNewItem(index: Int) {
                Log.d("autoloop", "index:$index")
                currPosition = index
            }
        })
        currPosition = carousel.count / 2
        carousel.jumpToIndex(carousel.count / 2)

    }

    fun startLoop(delay: Long = duration) {
        loopHandler.sendEmptyMessageDelayed(MSG_START_LOOP, delay)
        isLoop = true
    }

    fun stopLoop() {
        loopHandler.removeMessages(MSG_START_LOOP)
        isLoop = false
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> startLoop()
            MotionEvent.ACTION_DOWN -> stopLoop()
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x.toInt()
                startY = event.y.toInt()
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopLoop()
    }

}