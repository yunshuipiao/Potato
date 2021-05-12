package com.swensun.func.viewpager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swensun.potato.R
import kotlinx.android.synthetic.main.fragment_outer.*


class OuterFragment : BaseFragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_title.text = "-- $vid --"
    }

    override fun loadData() {
        super.loadData()
    }
}


