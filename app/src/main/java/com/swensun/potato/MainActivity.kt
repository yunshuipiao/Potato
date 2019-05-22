package com.swensun.potato

import android.os.Bundle
import com.swensun.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getContentSubView(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        hello.text = "hello kotln"

        hello.text = "1"
    }

    inner class InnerC {
    }
}
