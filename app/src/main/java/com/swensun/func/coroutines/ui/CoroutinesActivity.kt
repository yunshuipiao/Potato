package com.swensun.func.coroutines.ui

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.R

class CoroutinesActivity : BaseActivity() {


    override fun getContentSubView(): Int {
        return R.layout.coroutines_activity
    }

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
