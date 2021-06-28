package com.swensun.func.customview

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.swensun.base.BaseFragment
import com.swensun.potato.R
import com.swensun.potato.databinding.CustomViewFragmentBinding
import com.swensun.swutils.ui.dp
import com.swensun.swutils.ui.drawable

class CustomViewFragment : BaseFragment<CustomViewFragmentBinding>() {

    companion object {
        fun newInstance() = CustomViewFragment()
    }

    private lateinit var viewModel: CustomViewViewModel

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(CustomViewViewModel::class.java)
        binding.ivCover.background = drawable() {
            radius = 100.dp
            strokeWidth = 0.5.dp
            strokeColor = R.color.black
        }
    }
}
