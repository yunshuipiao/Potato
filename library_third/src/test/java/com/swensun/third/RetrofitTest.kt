package com.swensun.third

import android.util.Log
import com.orhanobut.logger.Logger
import org.junit.Test
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Retrofit




/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class RetrofitTest {



    @Test
    fun syncGetTest() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .build()

        val service = retrofit.create(GitHubService::class.java)
        val repos = service.listRepos("octocat")
    }
}

class Repo {
    val name = ""
}

interface GitHubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repo>>
}
