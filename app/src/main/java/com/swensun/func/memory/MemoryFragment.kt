package com.swensun.func.memory

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.MemoryFragmentBinding
import com.swensun.swutils.util.Logger
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


class MemoryFragment : BaseFragment<MemoryFragmentBinding>() {

    companion object {
        fun newInstance() = MemoryFragment()
    }

    private lateinit var viewModel: MemoryViewModel
    private val instanceList = arrayListOf<Retrofit>()

    private fun intiView() {
        newRetrofitClient()
        binding.btnOkhttpInstance.setOnClickListener {
            (0 until 10).forEach {
                instanceList.add(newRetrofitClient())
            }
        }
    }

    private fun newRetrofitClient(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com/")
            .build()

        val service = retrofit.create(GitHubService::class.java)
        lifecycleScope.launchWhenResumed {
            try {
                val l = service.listRepos("octocat")
                Logger.d(l)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return retrofit
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MemoryViewModel::class.java)
        intiView()
    }

}

interface GitHubService {
    @GET("users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repo>
}

class Repo {
    val id = ""
}
