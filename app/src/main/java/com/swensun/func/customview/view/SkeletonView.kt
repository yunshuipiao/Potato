package com.swensun.func.customview.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.swensun.func.customview.LifecycleInterface
import com.swensun.potato.R
import com.swensun.swutils.ui.dp2px
import com.swensun.swutils.ui.getColor
import com.swensun.swutils.ui.getWinHeight
import com.swensun.swutils.ui.getWinWidth

class SkeletonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), LifecycleInterface {

    val paint = Paint().apply {

    }

    //    var animPercent = 0f
    val rectF = RectF()
    val DEFAULT_SIZE = if (getWinWidth() < getWinHeight()) getWinWidth() else getWinHeight()
    val STROKE_WIDTH = dp2px(20f).toFloat()
    var size: Int = 0
        get() = if (measuredWidth < measuredHeight) measuredWidth else measuredHeight

    val anim = ValueAnimator.ofFloat(0.1f, 0.18f, 0.1f)
        .apply {
            duration = 1000
            repeatCount = ValueAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                //                animPercent = it.animatedValue as Float
                alpha = it.animatedValue as Float
//                invalidate()
            }
        }

    init {
        paint.color = getColor(R.color.black)
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.strokeWidth = STROKE_WIDTH
//        setBackgroundColor(getColor(R.color.black))
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
//        alpha = animPercent
//        canvas?.drawCircle(100f, 100f, 10f, paint)
        val r = (measuredWidth.toFloat() / 20).coerceAtMost(measuredHeight.toFloat() / 20)
        rectF.left = 0f
        rectF.top = 0f
        rectF.right = measuredWidth.toFloat()
        rectF.bottom = measuredHeight.toFloat()
        canvas?.drawRoundRect(rectF, r, r, paint)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        anim.cancel()
    }

}