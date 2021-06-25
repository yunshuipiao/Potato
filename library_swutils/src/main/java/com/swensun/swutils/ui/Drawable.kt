package com.swensun.swutils.ui

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.annotation.DrawableRes

/**
 * author : zp
 * date : 2021/4/8
 * Potato
 */


object Drawable {
    
    class Builder {
        var radius = 0
        var strokeWidth = 0
        var strokeColor: Int? = null
        var orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM
    }
}


fun drawable(@DrawableRes vararg colors: Int, block: (Drawable.Builder.() -> Unit)? = null): GradientDrawable {
    val builder = Drawable.Builder()
    val gradientDrawable = GradientDrawable()
    block?.invoke(builder)
    val radius = (builder.radius.toFloat())

    // set gradientDrawable

    if (builder.strokeWidth != 0 && builder.strokeColor != null) {
        gradientDrawable.setStroke(
            builder.strokeWidth,
            getColor(builder.strokeColor!!)
        )
    }

    if (colors.size == 1) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gradientDrawable.color = ColorStateList.valueOf(getColor(colors[0]))
        } else {
            gradientDrawable.setColor(getColor(colors[0]))
        }
    } else {
        gradientDrawable.colors = colors.map { getColor(it) }.toIntArray()
        gradientDrawable.orientation = builder.orientation
    }
    gradientDrawable.cornerRadius = radius
    return gradientDrawable
}