package com.swensun.func.status

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import com.swensun.potato.R
import com.swensun.potato.databinding.ViewStatusSubBinding
import com.swensun.swutils.ui.dp
import com.swensun.swutils.ui.drawable

/**
 * author : zp
 * date : 2021/7/14
 * Potato
 */
class StatusSubView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val binding = ViewStatusSubBinding.inflate(LayoutInflater.from(context), this, true)
    
    init {
        binding.tvConfirm.background = drawable(R.color.colorPrimary) {
            radius = 100.dp
        }
        this.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}