package com.swensun.wechat.repository

import android.util.Log
import com.swensun.wechat.repository.Service.WeChatService
import com.swensun.wechat.repository.network.NetWorkClient
import com.swensun.wechat.repository.proto.UserInfoRes
import com.swensun.wechat.repository.proto.UserTweetRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun requestUserTweets() {
        weChatService.reqUserTweets().enqueue(object : Callback<List<UserTweetRes>> {
            override fun onFailure(call: Call<List<UserTweetRes>>, t: Throwable) {
                Log.d("TAG", "requestUserTweets failure, ${t.message}")
            }

            override fun onResponse(call: Call<List<UserTweetRes>>, response: Response<List<UserTweetRes>>) {
                if (response.isSuccessful) {
                    val res = response.body() ?: arrayListOf()
                    Log.d("TAG", res.toString())
                } else {
                    Log.d("TAG", "requestUserInfo failure")
                }
            }
        })
    }
}