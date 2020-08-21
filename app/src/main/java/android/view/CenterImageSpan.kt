package android.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan

class CenterImageSpan(drawable: Drawable) : ImageSpan(drawable) {
    
    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val drawable = drawable
        val fontMetrics = paint.fontMetrics
        val transY =
            ((y + fontMetrics.ascent + y + fontMetrics.descent) / 2 - (drawable.bounds.bottom + drawable
                .bounds.top) / 2).toInt()
        canvas.save()
        canvas.translate(x, transY.toFloat())
        drawable.draw(canvas)
        canvas.restore()
    }
}