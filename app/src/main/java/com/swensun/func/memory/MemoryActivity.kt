package com.swensun.func.memory

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.R
import com.swensun.potato.databinding.MemoryActivityBinding

class MemoryActivity : BaseActivity<MemoryActivityBinding>() {


    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MemoryFragment.newInstance())
                .commitNow()
        }
    }

}
