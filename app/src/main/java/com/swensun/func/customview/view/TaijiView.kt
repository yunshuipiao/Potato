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


class TaijiView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val paint = Paint().apply {

    }


    val anim = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
        .apply {
            duration = 800
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
        }

    val DEFAULT_SIZE = if (getWinWidth() < getWinHeight()) getWinWidth() else getWinHeight()
    val STROKE_WIDTH = dp2px(1f).toFloat()

    var size: Int = 0
        get() = if (measuredWidth < measuredHeight) measuredWidth else measuredHeight

    val reactWidth
        get() = size / 6f
    val reactHeight
        get() = reactWidth / 5f

    init {
//        setBackgroundColor(getColor(R.color.colorPrimary))
        paint.color = getColor(R.color.black)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = STROKE_WIDTH
        paint.isAntiAlias = true
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
        paint.color = getColor(R.color.white)
        canvas?.drawCircle(xCenter, yCenter, (size - STROKE_WIDTH) / 2, paint)

        paint.color = getColor(R.color.black)
        canvas?.drawArc(
            0f + size * 0.25f,
            0f + size * 0.25f,
            size.toFloat() * 0.75f,
            size.toFloat() * 0.75f,
            -90f,
            180f,
            false,
            paint
        )
        canvas?.drawCircle(xCenter, yCenter * 1.25f, size / 8f, paint)
        paint.color = getColor(R.color.white)
        canvas?.drawCircle(xCenter, yCenter * 0.75f, size / 8f, paint)
        canvas?.drawCircle(xCenter, yCenter * 1.25f, size / 32f, paint)
        paint.color = getColor(R.color.black)
        canvas?.drawCircle(xCenter, yCenter * 0.75f, size / 32f, paint)
        (0 until 8).forEach {
//            canvas?.rotate(it * 45f)
            drawThreeLine(canvas, arrayListOf(false, false, false))
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        anim.cancel()
    }

    fun drawThreeLine(
        canvas: Canvas?,
        list: ArrayList<Boolean>
    ) {
        val firstTop = size * 0.2f - reactHeight / 2
        list.forEachIndexed { index, b ->
            val left = size * 0.5f - reactWidth / 2
            val top = firstTop - (index * 2) * reactHeight
            val right = left + reactWidth
            val bottom = top + reactHeight
            canvas?.drawRect(left, top, right, bottom, paint)
        }
    }
}