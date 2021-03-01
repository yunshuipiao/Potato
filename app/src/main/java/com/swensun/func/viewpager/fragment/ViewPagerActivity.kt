package com.swensun.func.viewpager.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.swensun.potato.R
import kotlinx.android.synthetic.main.activity_view_pager.*

class ViewPagerActivity : AppCompatActivity() {

    val adapter by lazy {
        ViewPager2Adapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        initView()
    }

    private fun initView() {
        viewpager2.adapter = adapter
        TabLayoutMediator(tab_layout, viewpager2) { tab, position ->
            tab.text = " -- $position -- "
        }.attach()
        val fragmentList = arrayListOf<Fragment>()
        val titleList = arrayListOf<String>()
        (0 until 4).forEach {
            fragmentList.add(
                OuterFragment.newInstance(
                    it.toString()
                )
            )
            titleList.add(it.toString())
        }
        viewpager2.layoutDirection = View.LAYOUT_DIRECTION_RTL
        viewpager2.offscreenPageLimit = fragmentList.size - 1
        adapter.setup(fragmentList, titleList)
    }
}
