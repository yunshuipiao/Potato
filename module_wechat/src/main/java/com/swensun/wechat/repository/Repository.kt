package com.swensun.wechat.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.swensun.wechat.repository.Service.WeChatService
import com.swensun.wechat.repository.network.NetWorkClient
import com.swensun.wechat.repository.proto.UserInfoRes
import com.swensun.wechat.repository.proto.UserTweetRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

    val TAG = "Repository"

    val weChatService = NetWorkClient.retrofit.create(WeChatService::class.java)

    val tweets = arrayListOf<UserTweetRes>()

    fun init(tweetsLiveData: MutableLiveData<List<UserTweetRes>>) {
        weChatService.reqUserTweets().enqueue(object : Callback<List<UserTweetRes>> {
            override fun onFailure(call: Call<List<UserTweetRes>>, t: Throwable) {
                Log.d(TAG, "requestUserTweets failure, ${t.message}")
            }

            override fun onResponse(call: Call<List<UserTweetRes>>, response: Response<List<UserTweetRes>>) {
                if (response.isSuccessful) {
                    val res = response.body() ?: arrayListOf()
                    tweets.addAll(res.filter { it.sender.nick.isNotEmpty() })
                    tweetsLiveData.postValue(getTweets(0))
                } else {
                    Log.d(TAG, "requestUserInfo failure")
                }
            }
        })
    }

    fun requestUserInfo() {
        weChatService.reqUserInfo().enqueue(object : retrofit2.Callback<UserInfoRes> {
            override fun onFailure(call: retrofit2.Call<UserInfoRes>, t: Throwable) {
                Log.d(TAG, "requestUserInfo failure, ${t.message}")
            }

            override fun onResponse(call: retrofit2.Call<UserInfoRes>, response: retrofit2.Response<UserInfoRes>) {
                if (response.isSuccessful) {
                    val res = response.body() ?: UserInfoRes()
                    Log.d(TAG, res.toString())
                } else {
                    Log.d(TAG, "requestUserInfo failure")
                }
            }
        })
    }

    fun requestUserTweetsFirstPage(tweetsLiveData: MutableLiveData<List<UserTweetRes>>) {
        // Simulated network request
        if (tweets.isEmpty()) {
            init(tweetsLiveData)
        } else {
            tweetsLiveData.postValue(getTweets(0))
        }
    }

    fun requestUserTweetsNextPage(start: Int, tweetsLiveData: MutableLiveData<List<UserTweetRes>>) {
        // Simulated network request
        tweetsLiveData.postValue(getTweets(start))
    }

    fun getTweets(start: Int): MutableList<UserTweetRes> {
        return if (tweets.size - start >= 5) {
            tweets.subList(start, start + 5)
        } else {
            tweets.subList(start, tweets.size)
        }
    }
}