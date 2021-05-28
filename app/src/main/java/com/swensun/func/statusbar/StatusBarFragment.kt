package com.swensun.func.statusbar

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.StatusBarFragmentBinding

class StatusBarFragment : BaseFragment<StatusBarFragmentBinding>() {

    companion object {
        fun newInstance() = StatusBarFragment()
    }

    private lateinit var viewModel: StatusBarViewModel
    private var mCount = 0


    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(StatusBarViewModel::class.java)
        binding.btnSystemUiDark.setOnClickListener {
            activity?.window?.decorView?.apply {
                systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE
            }
        }
        binding.btnHideStatusBar.setOnClickListener {
            activity?.window?.decorView?.apply {
                systemUiVisibility =
                    View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }
        }
        binding.btnAfterStatusBar.setOnClickListener {
            activity?.window?.decorView?.apply {
                systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            }
        }
        binding.btnImmersiveStatusBar.setOnClickListener {
            activity?.window?.decorView?.apply {
                systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        if (mCount % 2 == 0) {
                            View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
                        } else {
                            View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        }
                mCount += 1
            }
        }

        binding.btnStatusBarTrans.setOnClickListener {
            val lightStatus = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                0
            }
            val lightNavigation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                0
            }
            activity?.window?.decorView?.apply {
                systemUiVisibility =
                    if (mCount % 2 == 0) {
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or lightNavigation or lightStatus
                    } else {
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    }
            }
            activity?.window?.apply {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = Color.TRANSPARENT
                if (mCount % 2 == 0) {
                    navigationBarColor = Color.BLACK
                } else {
                    navigationBarColor = Color.WHITE
                }
            }
            mCount += 1
        }

    }
}