package com.swensun.wechat.repository.proto

import com.google.gson.annotations.SerializedName

class UserInfoReq {}

class UserInfoRes {
    val nick = ""
    @SerializedName("username")
    val userName = ""

    @SerializedName("profile-image")
    val profileImage = ""

    val avatar = ""

    override fun toString(): String {
        return "$nick -- $userName -- $avatar -- $profileImage"
    }
}

class UserTweetReq {

}

class UserTweetRes {
    val content = ""
    val images: List<Image> = arrayListOf()
    val sender = Sender()
    val comments: List<Comment> = arrayListOf()
}

class Sender {
    val username = ""
    val nick = ""
    val avatar = ""
}

class Image {
    val url = ""
}

class Comment {
    val content = ""
    val sender = Sender()
}

