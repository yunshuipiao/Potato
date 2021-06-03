package com.swensun.func.time

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.R
import com.swensun.potato.databinding.TimeAboutActivityBinding

class TimeAboutActivity : BaseActivity<TimeAboutActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TimeAboutFragment.newInstance())
                .commitNow()
        }
    }

}
