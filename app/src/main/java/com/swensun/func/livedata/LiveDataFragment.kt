package com.swensun.func.livedata

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.LiveDataFragmentBinding


class LiveDataFragment : BaseFragment<LiveDataFragmentBinding>() {

    companion object {
        fun newInstance() = LiveDataFragment()
    }

    private lateinit var viewModel: LiveDataViewModel
    private var count = 0


    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(LiveDataViewModel::class.java)
        binding.btnLivedata.setOnClickListener {
            count += 1
            if (count % 2 == 1) {
            } else {
            }
        }

        viewModel.intFlow.asLiveData().observe(this) {
            binding.content.text = "- $it -"
        }
        binding.content.setOnClickListener {
            viewModel.delayCallback()
        }
    }
}