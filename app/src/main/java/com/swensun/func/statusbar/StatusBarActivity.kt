package com.swensun.func.statusbar

import android.os.Bundle
import com.swensun.base.Base2Activity
import com.swensun.potato.R
import com.swensun.potato.databinding.StatusBarActivityBinding

class StatusBarActivity : Base2Activity<StatusBarActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, StatusBarFragment.newInstance())
                .commitNow()
        }
    }
}