package com.swensun.func.memory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.swensun.potato.R
import kotlinx.android.synthetic.main.memory_fragment.*
import okhttp3.OkHttpClient

class MemoryFragment : Fragment() {

    companion object {
        fun newInstance() = MemoryFragment()
    }

    private lateinit var viewModel: MemoryViewModel
    private val instanceList = arrayListOf<OkHttpClient>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.memory_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MemoryViewModel::class.java)
        intiView()
    }

    private fun intiView() {
        btn_okhttp_instance.setOnClickListener {
            (0 until 10).forEach {
                instanceList.add(newOkHttpClient())
            }
        }
    }

    private fun newOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

}
