package com.swensun.potato


import com.blankj.utilcode.util.GsonUtils
import com.google.gson.Gson
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
        .addInterceptor(ErrorHandlerInterceptor())
        .addInterceptor(ErrorHandlerInterceptor())
        .build()

    val retrofit = Retrofit.Builder().
        client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.github.com/").build()


    @Test
    fun listRepos_test() {
        val response = retrofit.create(GitHubService::class.java)
            .listRepos("octocat")
            .execute()
        println("code: ${response.code()}, body: ${GsonUtils.toJson(response.body())}")

    }
}

class ErrorHandlerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val repoList = arrayListOf<Repo>().apply {
            add(Repo(1))
            add(Repo(2))
            add(Repo(3))
        }
        val response = Response.Builder()
            .body(ResponseBody.create(null, GsonUtils.toJson(repoList)))
            .code(200)
            .message("suc")
            .protocol(Protocol.HTTP_2)
            .request(request).build()
        return response
    }

}

interface GitHubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repo>>
}

class Repo(var id: Int = 0) {
}

