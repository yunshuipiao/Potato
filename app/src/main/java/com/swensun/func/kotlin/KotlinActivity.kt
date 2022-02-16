package com.swensun.func.kotlin

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.swensun.base.BaseActivity
import com.swensun.potato.databinding.ActivityKotlinBinding
import kotlinx.coroutines.flow.collect

class KotlinActivity : BaseActivity<ActivityKotlinBinding>() {

    private val viewModel: KotlinViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                binding.btnKotlin.text = " - $it - "
            }
        }
        binding.btnKotlin.setOnClickListener {
            viewModel.updateUI()
        }
    }
}