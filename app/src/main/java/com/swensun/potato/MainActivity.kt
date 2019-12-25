package com.swensun.potato

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.func.CoroutinesActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : BaseActivity() {
    
    override fun getContentSubView(): Int {
        return R.layout.activity_main
    }
    
    override fun initView(savedInstanceState: Bundle?) {
        btn_coroutines.setOnClickListener {
            startActivity<CoroutinesActivity>()
        }
    }
}

