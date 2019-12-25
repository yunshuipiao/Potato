package com.swensun.func.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.fragment_outer.*
import java.util.logging.LogManager


class OuterFragment : Fragment() {

    lateinit var adapter: ViewPagerAdapter

    companion object {
        fun newInstance(id: String): OuterFragment {
            val fragment = OuterFragment()
            fragment.arguments = Bundle().apply {
                putString("id", id)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.d("onCreateView ${arguments?.get("id")}")
        return inflater.inflate(R.layout.fragment_outer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        childFragmentManager.let {
            adapter = ViewPagerAdapter(it)
            viewpager.adapter = adapter
            tab_layout.setupWithViewPager(viewpager)
            val fragmentList = arrayListOf<Fragment>()
            val titleList = arrayListOf<String>()
            (0 until 10).forEach {
                fragmentList.add(
                    InnerFragment.newInstance("${arguments?.get("id")}-$it")
                )
                titleList.add("${arguments?.get("id")}-$it")
            }
            adapter.setup(fragmentList, titleList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.d("onDestroyView ${arguments?.get("id")}")
    }
}