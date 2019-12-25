package com.swensun.potato


import com.blankj.utilcode.util.GsonUtils
import kotlinx.coroutines.runBlocking
import okhttp3.*
import org.junit.Test
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


class Retrofit_Test {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ErrorHandlerInterceptor())
        .addInterceptor { throw RuntimeException("error") }
        .build()

    val retrofit = Retrofit.Builder().
        client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.github.com/").build()


    @Test
    fun listRepos_test() {
        val githubService = retrofit.create(GitHubService::class.java)
        runBlocking {
            val response = githubService.listRepos2("octocat")
            println("res, body: ${GsonUtils.toJson(response)}")
        }
    }
}

class ErrorHandlerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        var response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            val result = BaseResponse<List<Repo>>().apply {
                result = -9999
                message = "internal error"
                data = null
            }
            response = Response.Builder()
                .body(ResponseBody.create(null, GsonUtils.toJson(result)))
                .code(200)
                .message("internal error ${e.message}")
                .protocol(Protocol.HTTP_2)
                .request(request).build()
        }
        return response
    }

}

interface GitHubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repo>>

    @GET("users/{user}/repos")
    suspend fun listRepos2(@Path("user") user: String): BaseResponse<String>
}

class BaseResponse<T> {
    var result = 0
    var message = ""
    var data: T? = null
}

class Repo(var id: Int = 0) {
}

