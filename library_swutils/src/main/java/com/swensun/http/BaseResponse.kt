package com.swensun.http

import com.google.gson.annotations.SerializedName

class BaseResponse<T> {
    @SerializedName("result")
    var result = 0

    @SerializedName("message")
    var message = ""
    
    @SerializedName("data")
    var data: T? = null

    val success: Boolean
        get() = result == 0
}