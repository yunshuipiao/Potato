package com.swensun.func.recycler

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.databinding.RecyclerViewActivityBinding


class RecyclerViewActivity : BaseActivity<RecyclerViewActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, RecyclerViewFragment.newInstance())
                .commitNow()
        }
    }
}
