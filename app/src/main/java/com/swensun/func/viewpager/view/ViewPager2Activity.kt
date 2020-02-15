package com.swensun.func.viewpager.view

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.swensun.potato.R
import com.tmall.ultraviewpager.UltraViewPager
import com.tmall.ultraviewpager.UltraViewPagerIndicator
import kotlinx.android.synthetic.main.activity_view_pager_2.*


class ViewPager2Activity : AppCompatActivity() {

    val adapter = ViewPager2Adapter()
    val dataList = arrayListOf(1, 2, 3, 4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_2)
        initView()
    }

    private fun initView() {

        viewpager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL)
        viewpager.setInfiniteLoop(true)
        viewpager.setAutoScroll(1000)
        viewpager.adapter = adapter
        adapter.updateList(dataList)
        viewpager.refresh()

        //内置indicator初始化
        viewpager.initIndicator()
//设置indicator样式
        viewpager.indicator
            .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
            .setFocusIcon(UltraViewPagerIndicator.convertResToBitmap(this, R.drawable.view_pager_focus))
            .setNormalIcon(UltraViewPagerIndicator.convertResToBitmap(this, R.drawable.view_pager_normal))
//            .setFocusColor(Color.GREEN)
//            .setNormalColor(Color.WHITE)
            .setMargin(20, 20, 20, 20)
//设置indicator对齐方式
        viewpager.indicator.setGravity(Gravity.LEFT or Gravity.BOTTOM)
//构造indicator,绑定到UltraViewPager
        viewpager.indicator.build()
    }
}