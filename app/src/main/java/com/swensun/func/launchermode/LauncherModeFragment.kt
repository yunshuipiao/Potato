package com.swensun.func.launchermode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.LauncherModeFragmentBinding

class LauncherModeFragment : BaseFragment<LauncherModeFragmentBinding>() {

    companion object {
        fun newInstance() = LauncherModeFragment()
    }

    private lateinit var viewModel: LauncherModeViewModel

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(LauncherModeViewModel::class.java)
        binding.btnLauncherMode.setOnClickListener {
        }
    }
}
