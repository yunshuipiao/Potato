package com.swensun.func.viewpager.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.swensun.base.Base2Activity
import com.swensun.potato.R
import com.swensun.potato.databinding.ActivityViewPagerBinding

class ViewPagerActivity : Base2Activity<ActivityViewPagerBinding>() {

    val adapter by lazy {
        ViewPager2Adapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

    }

    private fun initView() {
        vb.viewpager2.adapter = adapter
        TabLayoutMediator(vb.tabLayout, vb.viewpager2) { tab, position ->
            tab.text = " -- $position -- "
        }.attach()
        vb.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                vb.viewpager2.setCurrentItem(tab?.position ?: 0, false)
            }

        })
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
        vb.viewpager2.layoutDirection = View.LAYOUT_DIRECTION_RTL
        vb.viewpager2.offscreenPageLimit = fragmentList.size - 1
        adapter.setup(fragmentList, titleList)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initView()
    }
}
