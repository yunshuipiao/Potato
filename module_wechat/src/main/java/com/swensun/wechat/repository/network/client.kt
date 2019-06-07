package com.swensun.wechat.repository.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object NetWorkClient {
    val logInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

    val okHttpClient = OkHttpClient
        .Builder()
        .addNetworkInterceptor(logInterceptor)
        .build()
    val gson = GsonBuilder()
        .setLenient()
        .create()
    var retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("http://thoughtworks-ios.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}