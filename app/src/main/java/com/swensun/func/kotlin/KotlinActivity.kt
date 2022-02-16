package com.swensun.func.kotlin

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.databinding.ActivityKotlinBinding
import com.swensun.swutils.ui.toast

class KotlinActivity : BaseActivity<ActivityKotlinBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        binding.btnKotlin.setOnClickListener {
            toast("kotlin activity")
        }
    }
}