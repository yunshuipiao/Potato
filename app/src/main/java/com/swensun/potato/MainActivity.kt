package com.swensun.potato

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.func.bottom.BottomActivity
import com.swensun.func.coroutines.ui.CoroutinesActivity
import com.swensun.func.viewpager.ViewPagerActivity
import com.swensun.func.viewpager.ViewPagerAdapter
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
        btn_viewpager.setOnClickListener {
            startActivity<ViewPagerActivity>()
        }
        btn_bottom.setOnClickListener {
            startActivity<BottomActivity>()
        }
    }
}

