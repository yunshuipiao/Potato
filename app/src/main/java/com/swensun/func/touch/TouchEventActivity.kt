package com.swensun.func.touch

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.R
import com.swensun.potato.databinding.TouchEventActivityBinding

class TouchEventActivity : BaseActivity<TouchEventActivityBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TouchEventFragment.newInstance())
                .commitNow()
        }
    }
}