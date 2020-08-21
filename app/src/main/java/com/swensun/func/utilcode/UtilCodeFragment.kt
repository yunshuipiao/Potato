package com.swensun.func.utilcode

import android.app.PendingIntent
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.AppUtils
import com.swensun.func.customview.FrameLayoutActivity
import com.swensun.func.push.PushActivity
import com.swensun.potato.R
import com.swensun.potato.application.createNotificationChannel
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.util_code_fragment.*

class UtilCodeFragment : Fragment() {

    companion object {
        fun newInstance() = UtilCodeFragment()
        const val notification_id = 0x0001
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.util_code_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initView()
    }

    private fun initView() {
        btn_test.setOnClickListener {
            val infos = AppUtils.getAppsInfo()
            infos.map { it.versionName }.forEach {
                Logger.d("info:${it}")
            }
        }
    }
}