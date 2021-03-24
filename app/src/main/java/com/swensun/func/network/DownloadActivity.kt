package com.swensun.func.network

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.swensun.base.BaseActivity
import com.swensun.potato.R
import kotlinx.android.synthetic.main.download_activity.*
import org.jetbrains.anko.startActivity

/**
 * author : zp
 * date : 2021/3/23
 * Potato
 */

fun Context.startDownloadActivity() {
    startActivity<DownloadActivity>()
}

class DownloadActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(DownloadViewModel::class.java) }

    override fun getContentSubView(): Int {
        return R.layout.download_activity
    }

    override fun initView(savedInstanceState: Bundle?) {
        download.setOnClickListener {
            viewModel.downloadLive()
        }
    }
}