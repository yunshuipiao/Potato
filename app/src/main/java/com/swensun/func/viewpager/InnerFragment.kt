package com.swensun.func.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.fragment_inner.*

class InnerFragment : Fragment() {

    companion object {
        fun newInstance(id: String): InnerFragment {
            val fragment = InnerFragment()
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
        Logger.d("onCreateView ${arguments?.get("id")}")
        return inflater.inflate(R.layout.fragment_inner, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_content.text = arguments?.getString("id")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.d("onDestroyView ${arguments?.get("id")}")

    }
}