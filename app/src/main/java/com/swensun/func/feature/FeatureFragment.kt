package com.swensun.func.feature

import android.app.AlertDialog
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.feature_fragment.*
import org.jetbrains.anko.support.v4.toast

class FeatureFragment : Fragment() {

    companion object {
        fun newInstance() = FeatureFragment()
    }

    private lateinit var viewModel: FeatureViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.feature_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeatureViewModel::class.java)
        viewModel.speedLiveData.observe(viewLifecycleOwner, Observer {
            btn_net_speed.text = "${it}kb/s"
        })
        initView()
        showOrientation(resources.configuration.orientation)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Logger.d("FeatureActivity onConfigurationChanged, $newConfig")
        showOrientation(newConfig.orientation)
    }
    
    private fun showOrientation(orientation: Int) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            btn_full_screen.text = "切换横屏"
        } else {
            btn_full_screen.text = "切换竖屏"
        }
    }

    private fun initView() {
        btn_net_speed.setOnClickListener {
            viewModel.getNetSpeed()
        }
        btn_permisssion.setOnClickListener {
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

        btn_full_screen.setOnClickListener {
            val orientation = resources.configuration?.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
    }
}
