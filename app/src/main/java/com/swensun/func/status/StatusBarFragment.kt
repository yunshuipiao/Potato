package com.swensun.func.status

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.DisplayCutoutCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.swensun.potato.R
import kotlinx.android.synthetic.main.status_bar_fragment.*

class StatusBarFragment : Fragment() {

    companion object {
        fun newInstance() = StatusBarFragment()
    }

    private lateinit var viewModel: StatusBarViewModel
    private var mCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.status_bar_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StatusBarViewModel::class.java)
        btn_system_ui_dark.setOnClickListener {
            activity?.window?.decorView?.apply {
                systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE
            }
        }
        btn_hide_status_bar.setOnClickListener {
            activity?.window?.decorView?.apply {
                systemUiVisibility =
                    View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }
        }
        btn_after_status_bar.setOnClickListener {
            activity?.window?.decorView?.apply {
                systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            }
        }
        btn_immersive_status_bar.setOnClickListener {
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

        btn_status_bar_trans.setOnClickListener {
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

    private fun translucent() {

    }
}