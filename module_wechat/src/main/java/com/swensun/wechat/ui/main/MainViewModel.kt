package com.swensun.wechat.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swensun.wechat.repository.Repository
import com.swensun.wechat.repository.proto.UserInfoRes
import com.swensun.wechat.repository.proto.UserTweetRes

class MainViewModel : ViewModel() {

    var tweetsLiveData = MutableLiveData<List<UserTweetRes>>()
    var userInfoLiveData = MutableLiveData<UserInfoRes>()
    var hasMoreTweets = true

    fun requestUserInfo() {
        Repository.requestUserInfo(userInfoLiveData)
    }

    fun requestUserTweetsFirstPage() {
        Repository.requestUserTweetsFirstPage(tweetsLiveData)
    }

    fun requestUserTweetsNextPage(start: Int) {
        Log.d("TAG", "load more data: $start, $hasMoreTweets")
        if (hasMoreTweets) {
            Repository.requestUserTweetsNextPage(start, tweetsLiveData)
        }
    }
}
