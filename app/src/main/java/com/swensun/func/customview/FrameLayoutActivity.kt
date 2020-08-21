package com.swensun.func.customview

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.CenterImageSpan
import com.swensun.base.BaseActivity
import com.swensun.base.fitStatusBarHeight
import com.swensun.base.setNavigationBarColor
import com.swensun.base.setTransparentStatusBar
import com.swensun.potato.R
import com.swensun.swutils.ui.dp2px
import kotlinx.android.synthetic.main.activity_frame_layout.*

class FrameLayoutActivity : BaseActivity() {
    override fun getContentSubView(): Int {
        return R.layout.activity_frame_layout
    }

    override fun initView(savedInstanceState: Bundle?) {
        setTransparentStatusBar(false, R.color.colorPrimary)
        setNavigationBarColor(R.color.colorPrimary)
        fitStatusBarHeight()
        val text =
            "123456x789014567890"
        val span = SpannableString(text)
        val highLight = "01"
        val newHighlightText = highLight.trim()
        val index = text.indexOf(newHighlightText, 0, true)
        val drawable = getDrawable(R.drawable.ad)
        drawable?.let {
            drawable.setBounds(0, 0, dp2px(28f), dp2px(16f))
            if (index != -1) {
                span.setSpan(
                    CenterImageSpan(drawable),
                    index,
                    index + newHighlightText.length,
                    ImageSpan.ALIGN_BASELINE
                )
            }
            tv_text.text = span
        }
    }
}
