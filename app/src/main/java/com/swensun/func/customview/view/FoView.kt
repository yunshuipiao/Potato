package com.swensun.func.customview.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.swensun.potato.R
import com.swensun.swutils.ui.dp2px
import com.swensun.swutils.ui.getColor
import com.swensun.swutils.ui.getWinHeight
import com.swensun.swutils.ui.getWinWidth


class FoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var startAngle: Float = 0f
    val paint = Paint().apply {

    }

    val DEFAULT_SIZE = if (getWinWidth() < getWinHeight()) getWinWidth() else getWinHeight()
    val STROKE_WIDTH = dp2px(20f).toFloat()
    var size: Int = 0
        get() = if (measuredWidth < measuredHeight) measuredWidth else measuredHeight
    val rectSize: Float
        get() = size / 8f

    val anim = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
        .apply {
            duration = 1000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
//                startAngle = it.animatedValue as Float
//                invalidate()
            }
        }

    init {
//        setBackgroundColor(getColor(R.color.colorPrimary))
        paint.color = getColor(R.color.black)
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.strokeWidth = STROKE_WIDTH

        setOnClickListener {
            doAnim()
        }
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
            setMeasuredDimension(DEFAULT_SIZE, DEFAULT_SIZE)
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_SIZE, hSpecSize)
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSpecSize, DEFAULT_SIZE)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val xCenter = (right - left) / 2f
        val yCenter = (bottom - top) / 2f
        canvas?.scale(0.65f, 0.65f, xCenter, yCenter)
        canvas?.drawLine(
            measuredWidth / 2f,
            0f,
            measuredWidth / 2f,
            measuredHeight.toFloat(),
            paint
        )
        canvas?.drawLine(
            0f,
            measuredHeight / 2f,
            measuredWidth.toFloat(),
            measuredHeight / 2f,
            paint
        )
//        paint.strokeWidth = STROKE_WIDTH * 2
        canvas?.drawLine(0f, 0f, measuredWidth / 2f + STROKE_WIDTH / 2, 0f, paint)
        canvas?.drawLine(
            measuredWidth / 2f - STROKE_WIDTH / 2,
            measuredHeight.toFloat(),
            measuredWidth.toFloat(),
            measuredHeight.toFloat(),
            paint
        )
        canvas?.drawLine(0f, measuredHeight / 2f - STROKE_WIDTH / 2, 0f, measuredHeight.toFloat(), paint)
        canvas?.drawLine(
            measuredWidth.toFloat(),
            0f,
            measuredWidth.toFloat(),
            measuredHeight / 2f + STROKE_WIDTH / 2,
            paint
        )
    }
}