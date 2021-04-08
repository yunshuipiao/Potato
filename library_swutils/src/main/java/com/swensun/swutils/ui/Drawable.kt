package com.swensun.swutils.ui

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.blankj.utilcode.util.ConvertUtils

/**
 * author : zp
 * date : 2021/4/8
 * Potato
 */


object Drawable {

    const val TOP_LEFT = 1
    const val TOP_RIGHT = TOP_LEFT.shl(1)
    const val BOTTOM_RIGHT = TOP_LEFT.shl(2)
    const val BOTTOM_LEFT = TOP_LEFT.shl(3)

    class Builder {
        var colors: IntArray? = null
        var radius = 0
        var radiusType = 0
        var strokeWidth = 0
        var strokeColor:  Int? = null
        var orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM
    }
}


fun drawable(block: (Drawable.Builder.() -> Unit)? = null): GradientDrawable {
    val builder = Drawable.Builder()
    val gradientDrawable = GradientDrawable()
    block?.invoke(builder)
    val type = builder.radiusType
    val radius = builder.radius

    // set gradientDrawable

    if (builder.strokeWidth != 0 && builder.strokeColor != null) {
        gradientDrawable.setStroke(
            ConvertUtils.dp2px(builder.strokeWidth.toFloat()),
            getColor(builder.strokeColor!!)
        )
    }

    builder.colors?.let { colors ->
        colors.getOrNull(0)?.let { color ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                gradientDrawable.color = ColorStateList.valueOf(getColor(color))
            } else {
                gradientDrawable.setColor(getColor(color))
            }
        }
        if (colors.size > 1) {
            gradientDrawable.colors = colors.map { getColor(it) }.toIntArray()
            gradientDrawable.orientation = builder.orientation
        }
    }


    if (type == 0) {
        gradientDrawable.cornerRadius = radius.toFloat()
    } else {
        val floatArray = FloatArray(8)
        if (Drawable.TOP_LEFT.and(type) != 0) {
            floatArray[0] = radius.toFloat()
            floatArray[1] = radius.toFloat()
        }
        if (Drawable.TOP_RIGHT.and(type) != 0) {
            floatArray[2] = radius.toFloat()
            floatArray[3] = radius.toFloat()
        }
        if (Drawable.BOTTOM_RIGHT.and(type) != 0) {
            floatArray[4] = radius.toFloat()
            floatArray[5] = radius.toFloat()
        }
        if (Drawable.BOTTOM_LEFT.and(type) != 0) {
            floatArray[6] = radius.toFloat()
            floatArray[7] = radius.toFloat()
        }
        gradientDrawable.cornerRadii = floatArray
    }
    return gradientDrawable
}