package com.swensun.func.viewpager.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ktx.SingleEventObserver
import com.swensun.potato.Global
import com.swensun.potato.GlobalEvent
import com.swensun.potato.LiveDataBus
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
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
        LiveDataBus.get<GlobalEvent>(LiveDataBus.Global).observe(this, SingleEventObserver {
            Logger.d("global-viewpager, ${it.from}")
        })
        initView()
    }

    private fun initView() {
        viewpager.adapter = adapter
        tab_layout.setupWithViewPager(viewpager)
        val fragmentList = arrayListOf<Fragment>()
        val titleList = arrayListOf<String>()
        (0 until 4).forEach {
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

    override fun onResume() {
        super.onResume()
        log("ViewPagerActivity, resume")
    }

    override fun onPause() {
        super.onPause()
        log("ViewPagerActivity, pause")
    }
}

fun log(msg: String) {
    Logger.d("viewpager_fragment, $msg")

}
