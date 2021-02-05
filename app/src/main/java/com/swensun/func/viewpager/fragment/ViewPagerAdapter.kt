package com.swensun.func.viewpager.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    val fragmentList = arrayListOf<Fragment>()
    val titleList = arrayListOf<String>()

    
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

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}