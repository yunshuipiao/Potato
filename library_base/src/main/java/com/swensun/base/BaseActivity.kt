package com.swensun.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.swensun.swutils.ui.adapterScreen
import com.swensun.swutils.ui.resetScreen
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.content_base.*

abstract class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapterScreen()
        setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)
        layoutInflater.inflate(getContentSubView(), container)
    }

    @LayoutRes
    abstract fun getContentSubView(): Int

    override fun onDestroy() {
        super.onDestroy()
        resetScreen()
    }
}