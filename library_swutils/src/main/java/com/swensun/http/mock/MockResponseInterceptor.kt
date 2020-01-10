package com.swensun.http.mock

import com.blankj.utilcode.util.GsonUtils
import com.swensun.http.BaseResponse
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.lang.RuntimeException

class MockResponseInterceptor : Interceptor {
    /**
     * 目前没有真实服务器，利用拦截器模拟数据返回。
     * 分别返回一次失败，一次成功
     */
    var count = 0
    override fun intercept(chain: Interceptor.Chain): Response {
        var result: BaseResponse<String>
        if (count % 2 == 0) {
            result = BaseResponse<String>().apply {
                this.result = 0
                this.data = "swensun"
            }
        } else {
            result = BaseResponse<String>().apply {
                this.result = 101
                this.message = "params error"
            }
        }

        count += 1

//        val response = chain.proceed(chain.request())
        val res = Response.Builder()
            .body(ResponseBody.create(null, GsonUtils.toJson(result)))
            .code(404)
            .message("server error")
            .protocol(Protocol.HTTP_2)
            .request(chain.request()).build()
        return res

    }

}