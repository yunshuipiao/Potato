package com.swensun.potato.demo

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.R


class RecyclerViewActivity : BaseActivity() {
    override fun getContentSubView(): Int {
        return R.layout.recycler_view_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    RecyclerViewFragment.newInstance()
                )
                .commitNow()
        }
    }
}
