package com.swensun.swutils.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import org.jetbrains.anko.backgroundDrawable
import java.lang.Runnable

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

fun LinearLayout.reverseChild() {
    val views = arrayListOf<View>()
    (0 until this.childCount).forEach {
        views.add(this.getChildAt(it))
    }
    this.removeAllViews()
    views.reversed().forEach {
        this.addView(it)
    }
    this.invalidate()
}

fun TextView.setHighlightText(
    text: String,
    highlightText: String,
    @ColorInt color: Int,
    block: ((View) -> Unit)? = null
) {
    val span = SpannableString(text)
    val newHighlightText = highlightText.trim()
    val index = text.indexOf(newHighlightText, 0, true)
    if (index != -1) {
        span.setSpan(
            ForegroundColorSpan(color),
            index,
            index + newHighlightText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        span.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                block?.invoke(widget)
            }

            override fun updateDrawState(ds: TextPaint) {
                /**set textColor**/
                ds.color = ds.linkColor;
                /**Remove the underline**/
                ds.isUnderlineText = false;
            }
        }, index, index + newHighlightText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    this.movementMethod = LinkMovementMethod.getInstance()
    this.highlightColor = Color.TRANSPARENT
    this.text = span
}

fun View.setDebounceClickListener(timeMillis: Long = 300L, l: View.OnClickListener) {
    var job: Job? = null
    this.setOnClickListener {
        job?.cancel()
        job = CoroutineScope(Dispatchers.Main).launch {
            delay(timeMillis)
            l.onClick(it)
        }
    }
}

fun View.setDebounce2ClickListener(timeMillis: Long = 300L, l: View.OnClickListener) {
    val runnable = Runnable {
        l.onClick(this)
    }
    val handler = Handler(Looper.getMainLooper())
    this.setOnClickListener {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, timeMillis)
    }
}


