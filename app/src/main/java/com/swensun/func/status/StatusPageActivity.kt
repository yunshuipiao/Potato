package com.swensun.func.status

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swensun.StatusEvent
import com.swensun.base.BaseActivity
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.activity_status_page.*
import org.jetbrains.anko.toast

class StatusPageActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(StatusPageViewModel::class.java) }
    private val status_view by lazy { StatusView(this) }

    override fun getContentSubView(): Int {
        return R.layout.activity_status_page
    }

    override fun initView(savedInstanceState: Bundle?) {
        status_view.bindParentView(root_view)
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
        btn_error.setOnClickListener {
            viewModel.setStatus(StatusEvent.ERROR)
        }
        btn_loading.setOnClickListener {
            viewModel.setStatus(StatusEvent.LOADING)
        }
        btn_success.setOnClickListener {
            viewModel.setStatus(StatusEvent.SUCCESS)
        }
        tv_content.setOnClickListener {
            toast("click content")
        }
    }
}