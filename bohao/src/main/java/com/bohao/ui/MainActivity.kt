package com.bohao.ui

import android.os.Bundle
import com.bohao.databinding.ActivityMainBinding
import com.swensun.base.BaseActivity
import com.swensun.swutils.util.startActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        binding.tvLogin.setOnClickListener {
            startActivity<LoginActivity>()
        }
    }
}