package com.swensun.wechat.repository

import android.util.Log
import com.swensun.wechat.repository.Service.WeChatService
import com.swensun.wechat.repository.network.NetWorkClient
import com.swensun.wechat.repository.proto.UserInfoRes

object Repository {
    val weChatService = NetWorkClient.retrofit.create(WeChatService::class.java)


    fun requestUserInfo() {
        weChatService.reqUserInfo().enqueue(object :  retrofit2.Callback<UserInfoRes>{
            override fun onFailure(call: retrofit2.Call<UserInfoRes>, t: Throwable) {
                Log.d("TAG", "requestUserInfo failure, ${t.message}")
            }

            override fun onResponse(call: retrofit2.Call<UserInfoRes>, response: retrofit2.Response<UserInfoRes>) {
                if (response.isSuccessful) {
                    val res = response.body() ?: UserInfoRes()
                    Log.d("TAG", res.toString())
                } else {
                    Log.d("TAG", "requestUserInfo failure")
                }
            }
        })
    }
}