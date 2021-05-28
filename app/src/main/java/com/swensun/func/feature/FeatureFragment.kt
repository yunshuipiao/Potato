package com.swensun.func.feature

import android.app.AlertDialog
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.FeatureFragmentBinding
import com.swensun.swutils.util.Logger
import com.yanzhenjie.permission.AndPermission
import org.jetbrains.anko.support.v4.toast

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

    private fun initView() {
        binding.btnNetSpeed.setOnClickListener {
            viewModel.getNetSpeed()
        }
        binding.btnPermisssion.setOnClickListener {
            AndPermission.with(activity)
                .notification()
                .permission()
                .rationale { context, data, executor ->
                    AlertDialog.Builder(context)
                        .setTitle("去打开通知")
                        .setNegativeButton("取消") { dialog, which ->
                            executor.cancel()
                        }
                        .setPositiveButton("确认") { dialog, which ->
                            executor.execute()
                        }.show()
                }
                .onGranted {
                    toast("success")
                }
                .onDenied {
                    toast("failed")
                }.start()
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
