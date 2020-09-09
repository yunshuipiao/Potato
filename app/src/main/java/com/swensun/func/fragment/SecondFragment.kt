package com.swensun.func.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.swensun.potato.R
import com.swensun.swutils.util.Logger

class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.d("SecondFragment onCreateView")
        return inflater.inflate(R.layout.second_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Logger.d("SecondFragment onActivityCreated")
    }

    override fun onResume() {
        super.onResume()
        Logger.d("SecondFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        Logger.d("SecondFragment onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.d("SecondFragment onDestroyView")
    }
}

