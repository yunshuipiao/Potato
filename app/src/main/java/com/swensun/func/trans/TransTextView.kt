package com.swensun.func.trans

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class TransTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    override fun setText(text: CharSequence?, type: BufferType?) {
        // 进行翻译
        val t = "- $text -"
        super.setText(t, type)
    }
}