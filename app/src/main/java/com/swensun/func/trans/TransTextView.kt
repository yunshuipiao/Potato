package com.swensun.func.trans

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.swensun.potato.R

class PotatoTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {


    private var translatable: Boolean = true

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.PotatoTextView)
        // 根据 xml 配置是否应用字体
        val applyFont = array.getString(R.styleable.PotatoTextView_translatable)
        
        translatable = array.getBoolean(R.styleable.PotatoTextView_translatable, true)
        array.recycle()
        // 由于 setText 在 init{} 之前调用，所以自定义属性之后 再设置 text
        text = text
    }


    override fun setText(text: CharSequence?, type: BufferType?) {
        // 进行翻译
        if (translatable) {
            val newText = "-- $text --"
            super.setText(newText, type)
        } else {
            super.setText(text, type)
        }
    }
}