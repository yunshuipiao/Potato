package com.swensun.func.memory

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.MemoryFragmentBinding
import com.swensun.swutils.util.Logger
import kotlinx.coroutines.launch
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

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MemoryViewModel::class.java)
    }

}
