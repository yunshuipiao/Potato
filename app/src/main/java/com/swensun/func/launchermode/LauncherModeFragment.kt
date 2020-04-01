package com.swensun.func.launchermode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.swensun.potato.MainActivity
import com.swensun.potato.databinding.LauncherModeFragmentBinding
import org.jetbrains.anko.support.v4.startActivity

class LauncherModeFragment : Fragment() {

    companion object {
        fun newInstance() = LauncherModeFragment()
    }

    private lateinit var viewModel: LauncherModeViewModel
    private lateinit var mBinding: LauncherModeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        return inflater.inflate(R.layout.launcher_mode_fragment, container, false)
        mBinding = LauncherModeFragmentBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LauncherModeViewModel::class.java)
        mBinding.btnLauncherMode.setOnClickListener {
            startActivity<MainActivity>()
        }
    }
}
