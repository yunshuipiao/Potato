package com.swensun.func.utilcode

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.R
import com.swensun.potato.databinding.UtilCodeActivityBinding

class UtilCodeActivity : BaseActivity<UtilCodeActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UtilCodeFragment.newInstance())
                .commitNow()
        }
    }
}