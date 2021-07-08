package com.swensun.func.viewpager.fragment

import android.os.Bundle
import com.swensun.func.recycler.RecyclerViewActivity
import com.swensun.potato.databinding.FragmentOuterBinding
import com.swensun.swutils.util.startActivity
import java.util.logging.Logger


class OuterFragment : LazyLoadFragment<FragmentOuterBinding>() {

    var vid = ""

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
        vid = "-- ${arguments?.getString("id")} --"
        binding.tvTitle.text = vid
            binding.tvTitle.setOnClickListener {
                context?.startActivity<RecyclerViewActivity>()
            }
    }

    override fun loadData() {
        super.loadData()
    }

    override fun onResume() {
        super.onResume()
        com.swensun.swutils.util.Logger.d("onresume, -- ${arguments?.getString("id")} --")
    }
}


