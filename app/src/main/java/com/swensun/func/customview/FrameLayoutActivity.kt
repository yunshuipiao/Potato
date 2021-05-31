package com.swensun.func.customview

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.swensun.base.Base2Activity
import com.swensun.potato.databinding.ActivityFrameLayoutBinding

class FrameLayoutActivity : Base2Activity<ActivityFrameLayoutBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        vb.et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                vb.tvTextLeft.text = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}
