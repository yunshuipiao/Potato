package com.swensun.func.lifecycle

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.LifecycleFragmentBinding
import org.jetbrains.anko.support.v4.toast

class LifecycleFragment : BaseFragment<LifecycleFragmentBinding>() {

    companion object {
        fun newInstance() = LifecycleFragment()
    }

    private lateinit var viewModel: LifecycleViewModel

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(LifecycleViewModel::class.java)
        viewModel.strLiveData.observe(this, Observer {
            toast(it)
        })
        binding.btnPostSelf.setOnClickListener {
            viewModel.postLifecycler()
        }
    }
}
