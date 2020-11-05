package com.swensun.func.status

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swensun.StatusEvent
import com.swensun.StatusViewModel
import com.swensun.base.BaseActivity
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.activity_status_page.*

class StatusPageActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(StatusPageViewModel::class.java) }

    override fun getContentSubView(): Int {
        return R.layout.activity_status_page
    }

    override fun initView(savedInstanceState: Bundle?) {

        viewModel.statusLiveData.observe(this, Observer {
            Logger.d("statusLiveData changed: $it")
            when (it) {
                StatusEvent.ERROR -> {

                }
                StatusEvent.LOADING -> {

                }
                StatusEvent.SUCCESS -> {

                }
            }
        })

        btn_error.setOnClickListener {
            viewModel.setStatus(StatusEvent.LOADING)
        }
        btn_empty.setOnClickListener {
            viewModel.setStatus(StatusEvent.ERROR)
        }
        btn_success.setOnClickListener {
            viewModel.setStatus(StatusEvent.SUCCESS)
        }
    }
}