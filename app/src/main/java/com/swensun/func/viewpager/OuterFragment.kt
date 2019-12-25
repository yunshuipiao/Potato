package com.swensun.func.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.swensun.potato.R
import kotlinx.android.synthetic.main.fragment_outer_view_pager.*


class OuterFragment : Fragment() {

    companion object {
        fun newInstance(id: Int): OuterFragment {
            val fragment = OuterFragment()
            fragment.arguments = Bundle().apply {
                putString("id", id.toString())
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_outer_view_pager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv.text = arguments?.getString("id")
    }
}