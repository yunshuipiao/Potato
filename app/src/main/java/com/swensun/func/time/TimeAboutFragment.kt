package com.swensun.func.time

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.swensun.base.BaseFragment
import com.swensun.potato.R
import com.swensun.potato.databinding.TimeAboutFragmentBinding
import com.swensun.swutils.ui.getColor
import com.swensun.swutils.ui.setHighlightText
import com.swensun.swutils.ui.toast

class TimeAboutFragment : BaseFragment<TimeAboutFragmentBinding>() {

    companion object {
        fun newInstance() = TimeAboutFragment()
    }

    private lateinit var viewModel: TimeAboutViewModel

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(TimeAboutViewModel::class.java)
        initView()
    }

    private fun initView() {
        binding.btnDebounce.setHighlightText("1234567890", "23456", getColor(R.color.colorAccent)) {
            toast("span: $view")
        }
    }
}
