package com.swensun.potato

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.OnGestureListener
import android.view.MotionEvent
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.AdaptScreenUtils
import com.swensun.base.BaseActivity
import com.swensun.potato.demo.RecyclerViewActivity
import com.swensun.swutils.util.LogUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


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
        progress_circular.show()
        progress_circular.postDelayed({
            startActivity<RecyclerViewActivity>()
            progress_circular.hide()
        }, 3000)
    }
}

