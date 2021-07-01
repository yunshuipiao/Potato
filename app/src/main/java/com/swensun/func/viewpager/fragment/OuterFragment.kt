package com.swensun.func.viewpager.fragment

import android.os.Bundle
import com.swensun.potato.databinding.FragmentOuterBinding


class OuterFragment : LazyLoadFragment<FragmentOuterBinding>() {

    private var vid = ""

    companion object {
        fun newInstance(id: String): OuterFragment {
            val fragment = OuterFragment()
            fragment.arguments = Bundle().apply {
                putString("id", id)
            }
            return fragment
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.tvTitle.text = "-- ${arguments?.getString("id")} --"
    }

    override fun loadData() {
        super.loadData()
    }
}


