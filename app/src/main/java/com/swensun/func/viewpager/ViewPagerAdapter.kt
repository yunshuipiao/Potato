package com.swensun.func.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val fragmentList = arrayListOf<Fragment>()
    val titleList = arrayListOf<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    fun setup(
        fragmentList: ArrayList<Fragment>,
        titleList: ArrayList<String>
    ) {
        this.fragmentList.clear()
        this.fragmentList.addAll(fragmentList)
        this.titleList.addAll(titleList)
        this.titleList.addAll(titleList)
        notifyDataSetChanged()
    }

}