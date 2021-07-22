package com.swensun.func.feature

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.FeatureFragmentBinding
import com.swensun.swutils.util.Logger
import com.yanzhenjie.permission.AndPermission

class FeatureFragment : BaseFragment<FeatureFragmentBinding>() {

    companion object {
        fun newInstance() = FeatureFragment()
    }

    private lateinit var viewModel: FeatureViewModel

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Logger.d("FeatureActivity onConfigurationChanged, $newConfig")
        showOrientation(newConfig.orientation)
    }

    private fun showOrientation(orientation: Int) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.btnFullScreen.text = "切换横屏"
        } else {
            binding.btnFullScreen.text = "切换竖屏"
        }
    }


    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(FeatureViewModel::class.java)
        viewModel.speedLiveData.observe(viewLifecycleOwner, Observer {
            binding.btnNetSpeed.text = "${it}kb/s"
        })
        initView()
        showOrientation(resources.configuration.orientation)
    }

    @SuppressLint("MissingPermission")
    private fun initView() {
        binding.btnNetSpeed.setOnClickListener {
            viewModel.getNetSpeed()
        }
        binding.btnPermisssion.setOnClickListener {

            fun requestPermission() {
                AndPermission.with(activity).runtime()
                    .permission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    .onGranted {
                        Logger.d("location granted")
                    }.onDenied {
                        val shouldShowRequest = ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        if (shouldShowRequest) {
                            AlertDialog.Builder(requireContext())
                                .setNegativeButton("取消") { d, w ->
                                    
                                }
                                .setPositiveButton("确定") { d, w ->
                                    requestPermission()
                                }.create().show()
                            Logger.d("location denied and ask")
                        } else {
                            Logger.d("location denied and not ask")
                        }
                    }.start()
            }
            requestPermission()
        }

        binding.btnFullScreen.setOnClickListener {
            val orientation = resources.configuration?.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
    }
}
