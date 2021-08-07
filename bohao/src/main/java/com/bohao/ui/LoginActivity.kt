package com.bohao.ui

import android.os.Bundle
import com.bohao.databinding.ActivityLoginBinding
import com.swensun.base.BaseActivity
import com.swensun.swutils.ui.toast
import com.swensun.swutils.util.startActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        binding.tvLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (username == "test" && password == "123456") {
                toast("登录成功")
                startActivity<MainActivity>()
                finish()
            } else {
                toast("登录失败")
            }
        }
    }
}