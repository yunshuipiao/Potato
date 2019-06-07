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