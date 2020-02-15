package com.swensun.func.viewpager.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.swensun.potato.R
import kotlinx.android.synthetic.main.item_view_pager.view.*

class ViewPager2Adapter : PagerAdapter() {

    val list = arrayListOf<Int>()

    fun updateList(l: List<Int>) {
        list.clear()
        list.addAll(l)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView =
            LayoutInflater.from(container.context).inflate(R.layout.item_view_pager, null)
        container.addView(itemView)
        val index = position % list.size
        itemView.tv_text.text = list[index].toString()

        return itemView

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}