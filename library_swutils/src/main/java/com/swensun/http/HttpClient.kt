package com.swensun.http

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.swensun.http.mock.MockResponseInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object HttpClient {

    //模拟请求，通过拦截器模拟请求数据
    private var base_url = "https://apitest.com"

    fun replaceUrl(url: String) {
        base_url = url
    }

    val gson = GsonBuilder()
        .registerTypeAdapter(String::class.java, StringTypeAdapter())
        .create()

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(MockResponseInterceptor())
            .build()
    }

    val retrofit by lazy {
        Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(base_url).build()
    }

    inline fun <reified T> createService(): T {
        return retrofit.create(T::class.java)
    }
}