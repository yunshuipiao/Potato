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
        btn_notification.setOnClickListener {

            // Create an explicit intent for an Activity in your app
            val intent = Intent(it.context, FrameLayoutActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(it.context, 0, intent, 0)
            val channelId = createNotificationChannel("1", "2", "3")
            val notification = NotificationCompat.Builder(it.context, channelId)
                .setSmallIcon(R.drawable.exo_notification_small_icon)
                .setContentTitle("通知标题")
                .setContentText("通知内容")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
            NotificationManagerCompat.from(it.context)
                .notify(notification_id, notification)
        }
    }
}