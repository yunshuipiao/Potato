package com.swensun.func.feature

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swensun.base.BaseFragment
import com.swensun.potato.R
import com.swensun.potato.databinding.FeatureFragmentBinding
import com.swensun.swutils.SwUtils
import com.swensun.swutils.ui.toast
import com.swensun.swutils.util.Logger
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import java.util.*

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
        viewModel.speedLiveData.observe(viewLifecycleOwner, {
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

            fun openPermissionSetting(activity: Activity) {
                val intent = Intent()
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                intent.data = Uri.fromParts("package", activity.packageName, null)
                activity.startActivity(intent)
            }

            fun requestPermission() {
                AndPermission.with(activity).runtime()
                    .permission(Permission.ACCESS_FINE_LOCATION)
                    .onGranted {
                        Logger.d("location granted")
                    }.onDenied {
                        val shouldShowRequest = ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        if (shouldShowRequest) {
                            AlertDialog.Builder(requireContext())
                                .setTitle("位置权限")
                                .setMessage("查看附近的人需要获取位置权限")
                                .setNegativeButton("取消") { d, w ->
                                    
                                }
                                .setPositiveButton("确定") { d, w ->
                                    requestPermission()
                                }.create().show()
                            Logger.d("location denied and ask")
                        } else {
                            AlertDialog.Builder(requireContext())
                                .setTitle("位置权限")
                                .setMessage("查看附近的人需要获取位置权限")
                                .setNegativeButton("取消") { d, w ->

                                }
                                .setPositiveButton("去设置") { d, w ->
                                    openPermissionSetting(requireActivity())
                                }.create().show()
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

        binding.btnLanguage.setOnClickListener {
            val resources: Resources = SwUtils.application.resources
            val configuration = resources.configuration
            val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.locales[0]
            } else {
                Locale.getDefault()
            }
            val language = locale.language
            toast("current locale language:" + language + "  country:" + locale.country)
        }

        binding.btnDialog.setOnClickListener {
            val dialog = AlertDialog.Builder(it.context, R.style.AppTheme)
                .setTitle("title")
                .setNegativeButton("cancel", null)
                .setPositiveButton("ok", null)
                .create()
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//                dialog.window.setType(WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY)
//            }
            dialog.show()
        }


    }
}
