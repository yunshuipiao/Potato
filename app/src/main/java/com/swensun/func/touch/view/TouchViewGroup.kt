package com.swensun.func.touch.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.swensun.swutils.util.Logger

/**
 * author : zp
 * date : 2021/4/23
 * Potato
 */


val MotionEvent.actionStr: String
    get() {
        return when (this.action) {
            0 -> "down"
            1 -> "up"
            2 -> "move"
            3 -> "cancel"
            else -> ""
        }
    }


class TouchViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        log("dispatchTouchEvent, action:${ev?.actionStr}")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        log("onInterceptTouchEvent, action:${ev?.actionStr}")
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        log("onTouchEvent, action:${ev?.actionStr}")
        return super.onTouchEvent(ev)
    }

    override fun performClick(): Boolean {
        log("performClick")
        return super.performClick()
    }

    fun log(msg: String) {
        Logger.d("touch_event: - $tag -, $msg")
    }
}

class TouchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        log("dispatchTouchEvent, action:${ev?.actionStr}")
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        log("onTouchEvent, action:${ev?.actionStr}")
        return super.onTouchEvent(ev)
    }

    override fun performClick(): Boolean {
        log("performClick")
        return super.performClick()
    }

    fun log(msg: String) {
        Logger.d("touch_event: -- $tag --, $msg")
    }
}