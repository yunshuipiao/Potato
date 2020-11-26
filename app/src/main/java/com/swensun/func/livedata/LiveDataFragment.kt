package com.swensun.func.livedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ktx.debounce
import com.swensun.potato.R
import kotlinx.android.synthetic.main.live_data_fragment.*

class LiveDataFragment : Fragment() {

    companion object {
        fun newInstance() = LiveDataFragment()
    }

    private lateinit var viewModel: LiveDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.live_data_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LiveDataViewModel::class.java)
        btn_livedata_1.setOnClickListener {
            val last = viewModel.oneLiveData.value ?: "s"
            viewModel.oneLiveData.postValue("$last-s")
        }

        viewModel.oneLiveData.debounce(10).observe(viewLifecycleOwner, Observer {
            btn_livedata_1.text = it
        })
    }
}
