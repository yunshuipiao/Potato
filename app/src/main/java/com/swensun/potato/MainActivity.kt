package com.swensun.potato

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.swutils.util.SwUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getContentSubView(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        SwUtils.init(application)
    }

    private fun initView() {
        hello.setOnClickListener {
            ScoreDialog(this@MainActivity)
//                .withTransparent()
                .withSize(300, 300)
                .show()
        }
    }

    fun testBranch() {

    }
}
