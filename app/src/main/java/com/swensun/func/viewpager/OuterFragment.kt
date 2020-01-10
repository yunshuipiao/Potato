package com.swensun.func.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.activity_view_pager.*
import kotlinx.android.synthetic.main.fragment_outer.*
import kotlinx.android.synthetic.main.fragment_outer.tab_layout
import kotlinx.android.synthetic.main.fragment_outer.viewpager
import java.util.logging.LogManager


class OuterFragment : BaseFragment() {

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
        return inflater.inflate(R.layout.fragment_outer, container, false)
    }

    override fun loadData() {
        val id = arguments?.get("id")
        childFragmentManager.let {
            adapter = ViewPagerAdapter(it)
            viewpager.adapter = adapter
            tab_layout.setupWithViewPager(viewpager)
            val fragmentList = arrayListOf<Fragment>()
            val titleList = arrayListOf<String>()
            (0 until 10).forEach {
                fragmentList.add(
                    InnerFragment.newInstance("${id}-$it")
                )
                titleList.add("${id}-$it")
                viewpager.offscreenPageLimit = fragmentList.size - 1
            }
            adapter.setup(fragmentList, titleList)
        }
        Logger.d("id--: $id")
    }
}