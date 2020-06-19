package com.swensun.func.viewpager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ktx.SingleEvent
import com.swensun.potato.GlobalEvent
import com.swensun.potato.GlobalViewModel
import com.swensun.potato.R
import kotlinx.android.synthetic.main.fragment_outer.*


class OuterFragment : BaseFragment() {

    lateinit var adapter: ViewPagerAdapter
    private var vid = ""

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
        vid = arguments?.getString("id") ?: ""
        return inflater.inflate(R.layout.fragment_outer, container, false)
    }

    override fun loadData() {
        btn_send_event.setOnClickListener {
            GlobalViewModel.globalLiveData.postValue(SingleEvent(GlobalEvent().apply {
                from = "outer-${vid}"
            }))
        }
//        childFragmentManager.let {
//            adapter = ViewPagerAdapter(it)
//            viewpager.adapter = adapter
//            tab_layout.setupWithViewPager(viewpager)
//            val fragmentList = arrayListOf<Fragment>()
//            val titleList = arrayListOf<String>()
//            (0 until 10).forEach {
//                fragmentList.add(
//                    InnerFragment.newInstance("${vid}-$it")
//                )
//                titleList.add("${vid}-$it")
//                viewpager.offscreenPageLimit = fragmentList.size - 1
//            }
//            adapter.setup(fragmentList, titleList)
//        }
//        Logger.d("id--: $vid")
    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            log("outerF resume: $vid")
        }
    }

    override fun onPause() {
        super.onPause()
        log("outerF onPause: $vid")
    }
}