package com.swensun.func.customview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.CustomViewFragmentBinding

class CustomViewFragment : BaseFragment<CustomViewFragmentBinding>() {

    companion object {
        fun newInstance() = CustomViewFragment()
    }

    private lateinit var viewModel: CustomViewViewModel

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(CustomViewViewModel::class.java)
    }
}
