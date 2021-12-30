package com.swensun.func.network

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.swensun.base.BaseActivity
import com.swensun.potato.databinding.DownloadActivityBinding
import com.swensun.swutils.util.Logger
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * author : zp
 * date : 2021/3/23
 * Potato
 */


class DownloadActivity : BaseActivity<DownloadActivityBinding>() {

    private val viewModel by viewModels<DownloadViewModel>()

    private var url = "https://jsonplaceholder.typicode.com/posts/55"


    override fun initView(savedInstanceState: Bundle?) {

        val client = OkHttpClient.Builder()
            .connectTimeout(3L, TimeUnit.SECONDS)
            .build()

        binding.okhttpTimeout.setOnClickListener {
            val request = Request.Builder()
                .url(url)
                .build()


            Logger.d("start request")
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Logger.d("request fail:" + e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    Logger.d("request success:" + response.body()?.string())
                }
            })
        }

    }
}