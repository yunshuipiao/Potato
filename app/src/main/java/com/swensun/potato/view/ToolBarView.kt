package com.swensun.potato.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.swensun.potato.databinding.ToolBarViewBinding

/**
 * author : zp
 * date : 2021/5/28
 * Potato
 */
class ToolBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val binding = ToolBarViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.tvTitle.text = "Main"

        
    }
}