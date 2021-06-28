package com.swensun.func.status

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swensun.StatusEvent
import com.swensun.base.BaseActivity
import com.swensun.potato.databinding.ActivityStatusPageBinding
import com.swensun.swutils.ui.toast
import com.swensun.swutils.util.Logger
import com.swensun.swutils.util.startActivity

class StatusPageActivity : BaseActivity<ActivityStatusPageBinding>() {

    private val viewModel by lazy { ViewModelProvider(this).get(StatusPageViewModel::class.java) }
    private val status_view by lazy { StatusView(this) }

    override fun initView(savedInstanceState: Bundle?) {
        status_view.bindParentView(binding.rootView)

        viewModel.statusLiveData.observe(this, Observer {
            Logger.d("statusLiveData changed: $it")
            when (it) {
                StatusEvent.ERROR -> {
                    status_view.showErrorStatus()
                }
                StatusEvent.LOADING -> {
                    status_view.showLoadingStatus()
                }
                StatusEvent.SUCCESS -> {
                    status_view.removeStatus()
                }
                StatusEvent.EMPTY -> {
                    status_view.showEmptyStatus()
                }
            }
        })
        binding.btnError.setOnClickListener {
            viewModel.setStatus(StatusEvent.ERROR)
        }
        binding.btnLoading.setOnClickListener {
            viewModel.setStatus(StatusEvent.LOADING)
        }
        binding.btnSuccess.setOnClickListener {
            viewModel.setStatus(StatusEvent.SUCCESS)
        }
        binding.tvContent.setOnClickListener {
            toast("click content")
            startActivity<StatusPageActivity>()
        }
    }
}