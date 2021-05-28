package com.swensun.func.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.swensun.base.BaseFragment
import com.swensun.potato.R
import com.swensun.potato.databinding.SecondFragmentBinding
import com.swensun.swutils.util.Logger

class SecondFragment : BaseFragment<SecondFragmentBinding>() {

    companion object {
        fun newInstance() = SecondFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

}