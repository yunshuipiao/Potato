package com.swensun.func.livedata

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.R
import com.swensun.potato.databinding.LiveDataActivityBinding


class LiveDataActivity : BaseActivity<LiveDataActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    LiveDataFragment.newInstance()
                )
                .commitNow()
        }
    }
}
