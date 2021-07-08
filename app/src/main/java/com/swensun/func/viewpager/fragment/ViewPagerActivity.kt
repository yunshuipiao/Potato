package com.swensun.func.viewpager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.swensun.base.BaseActivity
import com.swensun.potato.databinding.ActivityViewPagerBinding
import com.swensun.swutils.util.Logger

class ViewPagerActivity : BaseActivity<ActivityViewPagerBinding>() {

    private val adapter by lazy {
        ViewPager2Adapter(this)
    }

    private fun initView() {
        binding.viewpager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewpager2) { tab, position ->
            tab.text = " -- $position -- "
        }.attach()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

        })
        val fragmentList = arrayListOf<Fragment>()
        val titleList = arrayListOf<String>()
        (0 until 10).forEach {
            fragmentList.add(
                OuterFragment.newInstance(
                    it.toString()
                )
            )
            titleList.add(it.toString())
        }
//        binding.tabLayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
//        binding.viewpager2.layoutDirection = View.LAYOUT_DIRECTION_RTL
        binding.viewpager2.offscreenPageLimit = fragmentList.size - 1
        adapter.setup(fragmentList, titleList)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initView()
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentCreated(
                fm: FragmentManager,
                f: Fragment,
                savedInstanceState: Bundle?
            ) {
                super.onFragmentCreated(fm, f, savedInstanceState)
                Logger.d("__onFragmentCreated, ${f.javaClass.canonicalName}, ${(f as OuterFragment).vid}")
            }

            override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                super.onFragmentResumed(fm, f)
                Logger.d("__onFragmentResumed, ${f.javaClass.canonicalName}, ${(f as OuterFragment).vid}")
            }

            override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                super.onFragmentViewDestroyed(fm, f)
                Logger.d("__onFragmentDestroyed, ${f.javaClass.canonicalName}, ${(f as OuterFragment).vid}")

            }
        }, false)
    }
}
