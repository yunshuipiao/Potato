package com.swensun.func.viewpager.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.swensun.potato.R
import kotlinx.android.synthetic.main.activity_view_pager.*

class ViewPagerActivity : AppCompatActivity() {

    val adapter by lazy {
        ViewPagerAdapter(
            supportFragmentManager
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        initView()
    }

    private fun initView() {
        viewpager.adapter = adapter
        tab_layout.setupWithViewPager(viewpager)
        val fragmentList = arrayListOf<Fragment>()
        val titleList = arrayListOf<String>()
        (0 until 10).forEach {
            fragmentList.add(
                OuterFragment.newInstance(
                    it.toString()
                )
            )
            titleList.add(it.toString())
            viewpager.offscreenPageLimit = fragmentList.size - 1
        }
        adapter.setup(fragmentList, titleList)
    }
}
