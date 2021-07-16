package com.swensun.func.network

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.swensun.base.BaseActivity
import com.swensun.potato.databinding.DownloadActivityBinding

/**
 * author : zp
 * date : 2021/3/23
 * Potato
 */


class DownloadActivity : BaseActivity<DownloadActivityBinding>() {

    private val viewModel by viewModels<DownloadViewModel>()


    override fun initView(savedInstanceState: Bundle?) {
    }
}