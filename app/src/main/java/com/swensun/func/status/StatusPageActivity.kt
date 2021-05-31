package com.swensun.func.status

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swensun.StatusEvent
import com.swensun.base.Base2Activity
import com.swensun.potato.databinding.ActivityStatusPageBinding
import com.swensun.swutils.util.Logger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class StatusPageActivity : Base2Activity<ActivityStatusPageBinding>() {

    private val viewModel by lazy { ViewModelProvider(this).get(StatusPageViewModel::class.java) }
    private val status_view by lazy { StatusView(this) }

    override fun initView(savedInstanceState: Bundle?) {
        status_view.bindParentView(vb.rootView)

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
        vb.btnError.setOnClickListener {
            viewModel.setStatus(StatusEvent.ERROR)
        }
        vb.btnLoading.setOnClickListener {
            viewModel.setStatus(StatusEvent.LOADING)
        }
        vb.btnSuccess.setOnClickListener {
            viewModel.setStatus(StatusEvent.SUCCESS)
        }
        vb.tvContent.setOnClickListener {
            toast("click content")
            startActivity<StatusPageActivity>()
        }
    }
}