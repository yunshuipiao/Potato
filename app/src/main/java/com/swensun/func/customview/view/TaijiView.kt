package com.swensun.func.customview.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.swensun.potato.R
import com.swensun.swutils.ui.getColor


class TaijiView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        setBackgroundColor(getColor(R.color.colorPrimary))
    }
}