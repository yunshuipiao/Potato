package com.swensun.swutils.ui

import android.content.res.ColorStateList
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import org.jetbrains.anko.backgroundDrawable

const val TOP_LEFT = 1
const val TOP_RIGHT = TOP_LEFT.shl(1)
const val BOTTOM_LEFT = TOP_LEFT.shl(2)
const val BOTTOM_RIGHT = TOP_LEFT.shl(3)

fun View.setRadiusBackground(radius: Int, color: Int? = null, type: Int = 0) {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.shape = GradientDrawable.RECTANGLE
    color?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gradientDrawable.color = ColorStateList.valueOf(it)
        } else {
            gradientDrawable.setColor(color)
        }
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

/**
 * 检测view是否被遮住，显示不全
 * @return true：表示被遮住
 */
fun View.isCover(): Boolean {
    val cover: Boolean
    val rect = Rect()
    cover = getGlobalVisibleRect(rect)
    return if (cover) {
        rect.width() < measuredWidth || rect.height() < measuredHeight
    } else true
}

fun ImageView.setImageSrc(@DrawableRes drawable: Int) {
    Glide.with(this.context).load(drawable).into(this)
}
fun ImageView.setImageSrc(url: String) {
    Glide.with(this.context).load(url).into(this)
}
