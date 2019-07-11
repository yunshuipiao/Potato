package com.swensun.swutils.ui

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.View
import org.jetbrains.anko.backgroundDrawable

const val TOP_LEFT = 1
const val TOP_RIGHT = TOP_LEFT.shl(1)
const val BOTTOM_LEFT = TOP_LEFT.shl(2)
const val BOTTOM_RIGHT = TOP_LEFT.shl(3)

fun View.setRadiusBackground(radius: Int, color: Int? = null, type: Int = 0) {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.shape = GradientDrawable.RECTANGLE
    color?.let {
        gradientDrawable.color = ColorStateList.valueOf(it)
    }
    if (type == 0) {
        gradientDrawable.cornerRadius = dp2px(radius.toFloat()).toFloat()
    } else {
        val floatArray = FloatArray(8)
        if (TOP_LEFT.and(type) != 0) {
            floatArray[0] = radius.toFloat()
            floatArray[1] = radius.toFloat()
        }
        if (TOP_RIGHT.and(type) != 0) {
            floatArray[2] = radius.toFloat()
            floatArray[3] = radius.toFloat()
        }
        if (BOTTOM_LEFT.and(type) != 0) {
            floatArray[4] = radius.toFloat()
            floatArray[5] = radius.toFloat()
        }
        if (BOTTOM_RIGHT.and(type) != 0) {
            floatArray[6] = radius.toFloat()
            floatArray[7] = radius.toFloat()
        }
        gradientDrawable.cornerRadii = floatArray
    }
    this.backgroundDrawable = gradientDrawable
}
