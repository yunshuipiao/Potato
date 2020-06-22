package com.swensun.func.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swensun.potato.R
import kotlinx.android.synthetic.main.feature_fragment.*

class FeatureFragment : Fragment() {

    companion object {
        fun newInstance() = FeatureFragment()
    }

    private lateinit var viewModel: FeatureViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.feature_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeatureViewModel::class.java)
        viewModel.speedLiveData.observe(viewLifecycleOwner, Observer {
            btn_net_speed.text = "${it}kb/s"
        })
        initView()
    }

    private fun initView() {
        btn_net_speed.setOnClickListener {
            viewModel.getNetSpeed()
        }
    }
}
