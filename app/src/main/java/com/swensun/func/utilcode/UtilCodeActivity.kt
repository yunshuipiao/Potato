package com.swensun.func.utilcode

import android.os.Bundle
import com.swensun.base.Base2Activity
import com.swensun.potato.R
import com.swensun.potato.databinding.UtilCodeActivityBinding

class UtilCodeActivity : Base2Activity<UtilCodeActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UtilCodeFragment.newInstance())
                .commitNow()
        }
    }
}