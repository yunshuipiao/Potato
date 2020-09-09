package com.swensun.func.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.swensun.potato.R
import com.swensun.swutils.util.Logger

class FirstFragment : Fragment() {

    companion object {
        fun newInstance() = FirstFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.d("FirstFragment onCreateView")
        return inflater.inflate(R.layout.first_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Logger.d("FirstFragment onActivityCreated")
    }
    
    override fun onResume() {
        super.onResume()
        Logger.d("FirstFragment onResume")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.d("FirstFragment onDestroyView")
    }


}

