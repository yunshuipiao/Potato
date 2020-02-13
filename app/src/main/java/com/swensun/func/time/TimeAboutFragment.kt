package com.swensun.func.time

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.swensun.potato.R
import kotlinx.android.synthetic.main.time_about_fragment.*

class TimeAboutFragment : Fragment() {

    companion object {
        fun newInstance() = TimeAboutFragment()
    }

    private lateinit var viewModel: TimeAboutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.time_about_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TimeAboutViewModel::class.java)
        initView()
    }

    private fun initView() {
        btn_debounce.setOnClickListener {
        }
    }
}
