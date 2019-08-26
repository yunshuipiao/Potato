package com.swensun.potato.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    var paint: Paint = Paint()
    val path = Path()
    val rectF = RectF()
    val cornerPathEffect = CornerPathEffect(50F)
    
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) {
            return
        }
        canvas.drawColor(Color.RED)
        paint.pathEffect = cornerPathEffect
        paint.isAntiAlias = true
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 10f
        //获取到view的宽度
        val viewWidth: Float = this.width.toFloat()
        val viewHeight: Float = this.height.toFloat()

//        path.moveTo(0f, viewHeight / 2)
//        path.lineTo(viewWidth, 0f)
//        path.lineTo(viewWidth, viewHeight)
//        path.lineTo(0f, viewHeight / 2)
//        path.close()

        rectF.set(0f, 0f, viewWidth / 2f, viewHeight / 2f)
        canvas.drawRect(rectF, paint)
    }
}