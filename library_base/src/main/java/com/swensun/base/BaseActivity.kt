package com.swensun.base

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import com.blankj.utilcode.util.BarUtils.getStatusBarHeight
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.content_base.*

abstract class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)
        layoutInflater.inflate(getContentSubView(), container)
        initView(savedInstanceState)
    }

    @LayoutRes
    abstract fun getContentSubView(): Int

    abstract fun initView(savedInstanceState: Bundle?)
}

fun Activity.setTransparentStatusBar(lightMode: Boolean = true, @ColorRes statusBarResId: Int = 0) {
    val lightStatus = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        0
    }
    window?.decorView?.apply {
        systemUiVisibility =
            if (lightMode) {
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or lightStatus
            } else {
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
    }
    window?.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = if (statusBarResId == 0) {
                Color.TRANSPARENT
            } else {
                com.swensun.swutils.ui.getColor(statusBarResId)
            }
        }
    }
}

fun Activity.setNavigationBarColor(@ColorRes navBarResId: Int = 0) {
    window?.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            navigationBarColor = if (navBarResId == 0) {
                Color.TRANSPARENT
            } else {
                com.swensun.swutils.ui.getColor(navBarResId)
            }
        }
    }
}

fun Activity.fitStatusBarHeight() {
    val contextView = findViewById<View>(android.R.id.content)
    contextView.setPadding(0, getStatusBarHeight(), 0, 0)
}