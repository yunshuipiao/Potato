package com.swensun.func.coroutines.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swensun.StatusEvent
import com.swensun.base.BaseFragment
import com.swensun.potato.R
import com.swensun.potato.databinding.CoroutinesFragmentBinding
import com.swensun.swutils.util.Logger

class CoroutinesFragment : BaseFragment<CoroutinesFragmentBinding>() {

    companion object {
        fun newInstance() = CoroutinesFragment()
    }

    private lateinit var viewModel: CoroutinesViewModel

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(CoroutinesViewModel::class.java)
        viewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                StatusEvent.LOADING -> {
                    Logger.d("loading ${it.msg}")
                }
                StatusEvent.SUCCESS -> {
                    Logger.d("SUCCESS")

                }
                StatusEvent.ERROR -> {
                    Logger.d("ERROR ${it.msg}")
                }
            }
        })

        binding.btnToast.setOnClickListener {
            viewModel.delayToast()
        }
        binding.btnCancel.setOnClickListener {
            viewModel.cancel()
        }
    }

}
