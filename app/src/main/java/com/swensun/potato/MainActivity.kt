package com.swensun.potato

import android.os.Bundle
import com.swensun.base.BaseActivity


class MainActivity : BaseActivity() {
    
    override fun getContentSubView(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
    }
}

