package com.swensun.func.customview.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.swensun.func.customview.LifecycleInterface
import com.swensun.potato.R
import com.swensun.swutils.ui.dp2px
import com.swensun.swutils.ui.getColor
import com.swensun.swutils.ui.getWinHeight
import com.swensun.swutils.ui.getWinWidth


class LoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), LifecycleInterface {

    private var isAdd: Int = 1
    private var startAngle: Float = 0f
    private var endAngle = 40f
    val paint = Paint().apply {

    }
    val rectF = RectF()


    val DEFAULT_SIZE = if (getWinWidth() < getWinHeight()) getWinWidth() else getWinHeight()
    val STROKE_WIDTH = dp2px(20f).toFloat()
    var size: Int = 0
        get() = if (measuredWidth < measuredHeight) measuredWidth else measuredHeight

    val anim = ValueAnimator.ofFloat(0f, 360f)
        .apply {
            duration = 1000
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            interpolator = LinearInterpolator()
            addUpdateListener {
                startAngle = it.animatedValue as Float
                invalidate()
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
        if (endAngle <= 45) {
            isAdd = 1
        } else if (endAngle >= 330) {
            isAdd = -1
        }
        endAngle += (2 * isAdd)
        rectF.left = 0f + STROKE_WIDTH / 2
        rectF.top = 0f + STROKE_WIDTH / 2
        rectF.right = size.toFloat() - STROKE_WIDTH / 2
        rectF.bottom = size.toFloat() - STROKE_WIDTH / 2
        canvas?.drawArc(
            rectF,
            startAngle,
            endAngle,
            false,
            paint
        )
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        anim.cancel()
    }
}