package com.swensun.potato


import com.blankj.utilcode.util.GsonUtils
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import org.junit.Test
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.RuntimeException


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
        val response = githubService
            .listRepos("octocat")
            .execute()
        println("code: ${response.code()}, body: ${GsonUtils.toJson(response.body())}")

//        GlobalScope.launch(Dispatchers.Default) {
//            val res = githubService.listRepos2("123")
//            println("res: $res")
//        }

    }
}

class ErrorHandlerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        var response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            val repoList = arrayListOf<Repo>().apply {
                add(Repo(1))
                add(Repo(2))
                add(Repo(3))
            }
            response = Response.Builder()
                .body(ResponseBody.create(null, GsonUtils.toJson(repoList)))
                .code(200)
                .message("suc")
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
    suspend fun listRepos2(@Path("user") user: String): List<Repo>
}

class Repo(var id: Int = 0) {
}

