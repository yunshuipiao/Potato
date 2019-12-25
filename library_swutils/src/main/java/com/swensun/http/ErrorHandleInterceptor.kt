package com.swensun.http

import android.accounts.NetworkErrorException
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.lang.RuntimeException

class ErrorHandleInterceptor : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {

        try {
            val request = chain.request()
            // 无网络异常
            if (!NetworkUtils.isConnected()) {
                throw NetworkErrorException("no network")
            }
            // 服务器处理异常
            val res =  chain.proceed(request)
            if (!res.isSuccessful) {
                throw RuntimeException("server: ${res.message()}")
            }
            return res
        } catch (e: Exception) {
            val httpResult = BaseResponse<String>().apply {
                result = 900  // 901， 902
                data = null
                message = e.message ?: "client internal error"
            }
            val body = ResponseBody.create(null, GsonUtils.getGson().toJson(httpResult))
            return Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .message(httpResult.message)
                .body(body)
                .code(200)
                .build()
        }
    }
}