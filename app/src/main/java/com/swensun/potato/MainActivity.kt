package com.swensun.potato

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.swensun.base.BaseActivity
import com.swensun.swutils.util.Logger
import com.swensun.swutils.util.SystemPropUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    lateinit var viewModel: MainViewModel
    
    override fun getContentSubView(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, LayoutFragment.newInstance())
//                .commit()
//        }
        log.setOnClickListener {
            val key = "test"
            Logger.d("getProp: $key = ${SystemPropUtils.getProp(key)}")
            Logger.i("getProp: $key = ${SystemPropUtils.getProp(key)}")
        }
    }
}

