package com.swensun.func.touch

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.TouchEventFragmentBinding

class TouchEventFragment : BaseFragment<TouchEventFragmentBinding>() {


    companion object {
        fun newInstance() = TouchEventFragment()
    }

    private lateinit var viewModel: TouchEventViewModel

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(TouchEventViewModel::class.java)
        binding.touchRed.tag = "red"
        binding.touchBlue.tag = "blue"
        binding.touchGreen.tag = "green"
    }
}