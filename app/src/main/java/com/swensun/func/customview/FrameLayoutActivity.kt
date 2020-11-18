package com.swensun.func.customview

import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
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
        et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tv_text_left.text = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

    }
}
