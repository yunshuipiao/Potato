package com.swensun.func.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.swensun.base.BaseFragment
import com.swensun.potato.R
import com.swensun.potato.databinding.FirstFragmentBinding
import com.swensun.swutils.util.Logger

class FirstFragment : BaseFragment<FirstFragmentBinding>() {

    companion object {
        fun newInstance() = FirstFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {
    }
}

