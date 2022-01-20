package com.swensun.func.network

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.http.mock.MockResponseInterceptor
import com.swensun.potato.databinding.ActivityRetrofitBinding
import com.swensun.swutils.util.Logger
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class RetrofitActivity : BaseActivity<ActivityRetrofitBinding>() {


    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(100L, TimeUnit.SECONDS)
            .readTimeout(100L, TimeUnit.SECONDS)
            .writeTimeout(100L, TimeUnit.SECONDS)
//            .callTimeout(5L, TimeUnit.SECONDS)
            .build()
    }


    override fun initView(savedInstanceState: Bundle?) {
        val url = "https://httpbin.org/delay/20"
        binding.bntTimeout.setOnClickListener {
            Logger.d("start request")
            val request = Request.Builder().url(url).build()
            okHttpClient.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Logger.d("request exception, ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        Logger.d("request success, ${response.body()?.string()}")
                    } else {
                        Logger.d("request fail, ${response.code()}")
                    }
                }
            })
        }
    }
}