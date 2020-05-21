package com.swensun.func.framelayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.R

class FrameLayoutActivity : BaseActivity() {
    
    override fun getContentSubView(): Int {
        return R.layout.activity_frame_layout
    }

    override fun initView(savedInstanceState: Bundle?) {
    }
}
