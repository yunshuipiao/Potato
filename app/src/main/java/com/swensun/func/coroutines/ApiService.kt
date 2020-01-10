package com.swensun.func.coroutines

import com.swensun.http.BaseResponse
import retrofit2.http.GET


interface ApiService {

    @GET("/data/")
    suspend fun fetchData(): BaseResponse<String>
}