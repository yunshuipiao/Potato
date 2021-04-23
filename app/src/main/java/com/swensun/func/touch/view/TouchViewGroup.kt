package com.swensun.func.touch.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
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
        log("dispatchTouchEvent :${ev?.actionStr} start ")
        val result = super.dispatchTouchEvent(ev)
        log("dispatchTouchEvent :${ev?.actionStr} end with: $result")
        return result
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        log("onInterceptTouchEvent: ${ev?.actionStr} start")
        var result = super.onInterceptTouchEvent(ev)
        log("onInterceptTouchEvent: ${ev?.actionStr} end with:${result}")
        return result
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        log("onTouchEvent:${ev?.actionStr} start")
        var result = super.onTouchEvent(ev)
        log("onTouchEvent:${ev?.actionStr} end with $result")

        result = true
        return result
    }

    fun log(msg: String) {
        Logger.d("touch_event: - $tag -, $msg")
    }
}

class TouchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        log("dispatchTouchEvent :${ev?.actionStr} start ")
        var result = super.dispatchTouchEvent(ev)
        log("dispatchTouchEvent :${ev?.actionStr} end with: $result")
        return result
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        log("onTouchEvent:${ev?.actionStr} start")
        var result = super.onTouchEvent(ev)
        log("onTouchEvent:${ev?.actionStr} end with $result")
        return result
    }


    fun log(msg: String) {
        Logger.d("touch_event: -- $tag --, $msg")
    }
}