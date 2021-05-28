package com.swensun.func.livedata

import android.os.Bundle
import com.swensun.base.Base2Activity
import com.swensun.potato.R
import com.swensun.potato.databinding.LiveDataActivityBinding


class LiveDataActivity : Base2Activity<LiveDataActivityBinding>() {

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
