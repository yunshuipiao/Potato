package com.swensun.func.livedata

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.LiveDataFragmentBinding
import kotlinx.coroutines.flow.MutableStateFlow


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
        F.modelFlow.asLiveData().observe(viewLifecycleOwner) {

        }
    }
}

object F {
    val modelFlow = MutableStateFlow("")
    fun testFlow(number: Int) {
        modelFlow.value = number.toString()
    }
}