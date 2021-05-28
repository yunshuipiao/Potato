package com.swensun.func.coroutines.ui

import android.os.Bundle
import com.swensun.base.Base2Activity
import com.swensun.potato.R
import com.swensun.potato.databinding.CoroutinesActivityBinding

class CoroutinesActivity : Base2Activity<CoroutinesActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        supportActionBar?.title = getString(R.string.coroutines)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    CoroutinesFragment.newInstance()
                )
                .commit()
        }
    }
}
