package com.swensun.potato

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ktx.SingleEvent
import androidx.lifecycle.ktx.SingleEventObserver
import androidx.lifecycle.ktx.noSticky
import com.swensun.base.BaseActivity
import com.swensun.potato.demo.RecyclerViewActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : BaseActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var userViewModel: UserViewModel

    override fun getContentSubView(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        userViewModel = ViewModelProviders.of(this, SharedViewModelFactory).get(UserViewModel::class.java)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        log.setOnClickListener {
            test()
        }

    }

    override fun onResume() {
        super.onResume()
        mainViewModel.navigationLiveData.noSticky().observe(this, SingleEventObserver {
            if (it == "3") {
                    startActivity<RecyclerViewActivity>()
                }
                log.text = it
        })
    }

    fun test() {
        mainViewModel.navigation()
    }
}



