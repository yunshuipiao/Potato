package com.swensun.func.customview.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.swensun.potato.R
import com.swensun.swutils.ui.dp2px
import com.swensun.swutils.ui.getColor
import com.swensun.swutils.ui.getWinHeight
import com.swensun.swutils.ui.getWinWidth

class GoogleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    val paint = Paint().apply {

    }

    var animValue = 0f
    val STROKE_WIDTH = dp2px(20f).toFloat()
    private var size: Int = 0
        get() = if (measuredWidth < measuredHeight) measuredWidth else measuredHeight

    private var circleSize = 0f
        get() = measuredWidth / 16f
    val anim = ValueAnimator.ofFloat(0f, -20f, 20f, 0f)
        .apply {
            duration = 1000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                animValue = it.animatedValue as Float
                invalidate()
            }
        }

    init {
        paint.color = getColor(R.color.black)
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.strokeWidth = STROKE_WIDTH
        setOnClickListener {
            doAnim()
        }
        doAnim()
    }

    fun doAnim() {
        if (!anim.isStarted) {
            anim.start()
            return
        }
        if (anim.isPaused) {
            anim.resume()
            return
        }
        anim.pause()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 获取宽的测量模式
        val wSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        // 获取符控件提供的 view 宽的最大值
        val wSpecSize = MeasureSpec.getSize(widthMeasureSpec)

        val hSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val hSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getWinWidth(), getWinHeight())
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getWinWidth(), hSpecSize)
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSpecSize, getWinHeight())
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val y = measuredHeight / 2f
        canvas?.drawCircle(measuredWidth * 0.2f, y + animValue, circleSize, paint)
        canvas?.drawCircle(measuredWidth * 0.4f, y, circleSize, paint)
        canvas?.drawCircle(measuredWidth * 0.6f, y, circleSize, paint)
        canvas?.drawCircle(measuredWidth * 0.8f, y, circleSize, paint)
    }
}