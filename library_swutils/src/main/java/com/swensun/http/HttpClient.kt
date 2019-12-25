package com.swensun.http

import com.swensun.http.mock.MockResponseInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object HttpClient {

    //模拟请求，通过拦截器模拟请求数据
    var base_url = "https://apitest.com" //

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(ErrorHandleInterceptor())
            .addInterceptor(MockResponseInterceptor())
            .build()
    }

    val retrofit by lazy {
        Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(base_url).build()
    }
}