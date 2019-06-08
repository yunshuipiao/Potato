package com.swensun.wechat.repository.Service

import com.swensun.wechat.repository.proto.UserInfoRes
import com.swensun.wechat.repository.proto.UserTweetRes
import retrofit2.Call
import retrofit2.http.GET

public interface WeChatService {
    @GET("user/jsmith")
    fun reqUserInfo(): Call<UserInfoRes>

    @GET("user/jsmith/tweets")
    fun reqUserTweets(): Call<List<UserTweetRes>>
}