package com.swensun.func.fragment

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.R
import kotlinx.android.synthetic.main.activity_fragment_mode.*

class FragmentModeActivity : BaseActivity() {


    override fun getContentSubView(): Int {
        return R.layout.activity_fragment_mode
    }

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, FirstFragment.newInstance())
                .commitNow()
        }

        tv_add.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, SecondFragment.newInstance())
                .commit()
        }
        tv_delete.setOnClickListener {
            supportFragmentManager.popBackStack()
        }
    }
}