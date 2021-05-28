package com.swensun.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ziipin.social.base.multitype.inflateBindingWithGeneric

/**
 * author : zp
 * date : 2021/5/27
 * Potato
 */
abstract class Base2Activity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)
        fitStatusBarHeight()
        setTransparentStatusBar(false, R.color.colorPrimary)
        initView(savedInstanceState)
    }

    abstract fun initView(savedInstanceState: Bundle?)
}

