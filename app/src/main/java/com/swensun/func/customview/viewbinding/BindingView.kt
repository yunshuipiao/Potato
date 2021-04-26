package com.swensun.func.customview.viewbinding

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.swensun.potato.R
import com.swensun.potato.databinding.BindingViewBinding

/**
 * author : zp
 * date : 2021/4/26
 * Potato
 */

class BindingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = BindingViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
//        LayoutInflater.from(context).inflate(R.layout.binding_view, this)
    }
}