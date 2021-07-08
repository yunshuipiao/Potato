package com.swensun.func.feature

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
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
//            AndPermission.with(activity)
//                .notification()
//                .permission()
//                .rationale { context, data, executor ->
//                    AlertDialog.Builder(context)
//                        .setTitle("去打开通知")
//                        .setNegativeButton("取消") { dialog, which ->
//                            executor.cancel()
//                        }
//                        .setPositiveButton("确认") { dialog, which ->
//                            executor.execute()
//                        }.show()
//                }
//                .onGranted {
//                    toast("success")
//                }
//                .onDenied {
//                    toast("failed")
//                }.start()


//            AndPermission.with(activity).runtime()
//                .permission(
//                    android.Manifest.permission.ACCESS_FINE_LOCATION,
//                )
//                .onGranted {
//                    Logger.d("location granted")
//                }.onDenied {
//                    Logger.d("location denied")
//                }.start()
            val locationAvailable = AndPermission.hasPermissions(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
            if (locationAvailable) {
                showLocation()
            }
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

    private fun showLocation() {
        if (GPSUtils.isLocationProviderEnabled()) {
            showLocationWithToast()
        } else {
            requestLocation()
        }
    }

    private fun showLocationWithToast() {
        val location = GPSUtils.getLocation() ?: return
        Toast.makeText(
            context,
            "地理位置：lon:${location.longitude};lat:${location.latitude}",
            Toast.LENGTH_LONG
        ).show()
    }

    /**
     * 检查地理位置开关是否打开，如果未打开，则提示用户打开地理位置开关。
     * 如果已打开，则显示地理位置；如果被拒绝，直接关闭窗口。
     */
    private fun requestLocation() {
        val message = "本应用需要获取地理位置，请打开获取位置的开关"
        val alertDialog = AlertDialog.Builder(context).setMessage(message).setCancelable(false)
            .setPositiveButton(android.R.string.ok)
            { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .create()
        alertDialog.show()
    }
}
