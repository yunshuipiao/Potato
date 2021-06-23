package com.swensun.potato

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.swensun.base.BaseActivity
import com.swensun.base.ViewBindingDialog
import com.swensun.func.KvStore
import com.swensun.func.anim.AnimActivity
import com.swensun.func.bottom.BottomActivity
import com.swensun.func.coroutines.ui.CoroutinesActivity
import com.swensun.func.customview.CustomViewActivity
import com.swensun.func.customview.FrameLayoutActivity
import com.swensun.func.exoplayer.ExoPlayerActivity
import com.swensun.func.feature.FeatureActivity
import com.swensun.func.fragment.FragmentModeActivity
import com.swensun.func.lifecycle.LifecycleActivity
import com.swensun.func.livedata.LiveDataActivity
import com.swensun.func.memory.MemoryActivity
import com.swensun.func.network.startDownloadActivity
import com.swensun.func.push.SchemeActivity
import com.swensun.func.recycler.RecyclerViewActivity
import com.swensun.func.room.RoomActivity
import com.swensun.func.room.database.RDataBase
import com.swensun.func.status.StatusPageActivity
import com.swensun.func.statusbar.StatusBarActivity
import com.swensun.func.time.TimeAboutActivity
import com.swensun.func.touch.TouchEventActivity
import com.swensun.func.trans.TransFontActivity
import com.swensun.func.userinfo.UserInfoActivity
import com.swensun.func.utilcode.UtilCodeActivity
import com.swensun.func.utilcode.UtilCodeFragment
import com.swensun.func.viewpager.fragment.ViewPagerActivity
import com.swensun.potato.application.createNotificationChannel
import com.swensun.potato.databinding.ActivityMainBinding
import com.swensun.potato.databinding.DialogLoadingBinding
import com.swensun.swutils.ui.dp
import com.swensun.swutils.ui.setDebounceClickListener
import com.swensun.swutils.util.Logger
import com.swensun.swutils.util.NetWorkChangeUtils
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val today
        get() = DateFormat.format("yyyyMMdd, hh-mm-ss", System.currentTimeMillis()).toString()

    private val viewModel: MainViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {

        vb.btnTouchEvent.setOnClickListener {
            startActivity<TouchEventActivity>()
        }

        vb.btnCoroutines.setOnClickListener {
            startActivity<CoroutinesActivity>()
        }
        vb.btnViewpager.setOnClickListener {
            startActivity<ViewPagerActivity>()
        }
        vb.btnBottom.setOnClickListener {
            startActivity<BottomActivity>()
        }
        vb.btnFontTrans.setOnClickListener {
            startActivity<TransFontActivity>()
        }
        vb.btnRoom.setOnClickListener {
            startActivity<RoomActivity>()
        }
        vb.btnTime.setOnClickListener {
            startActivity<TimeAboutActivity>()
        }
        vb.btnLifecycle.setOnClickListener {
            startActivity<LifecycleActivity>()
        }
        vb.btnMultiDialog.setOnClickListener {
        }
        vb.btnLivedata.setOnClickListener {
            startActivity<LiveDataActivity>()
        }
        vb.btnRecycler.setOnClickListener {
            startActivity<RecyclerViewActivity>()
//            overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
        }
        vb.btnMemory.setOnClickListener {
            startActivity<MemoryActivity>()
        }
        vb.btnCustomView.setOnClickListener {
            startActivity<CustomViewActivity>()
        }
        vb.btnExoPlayer.setOnClickListener {
            startActivity<ExoPlayerActivity>()
        }

        vb.btnLauncherMode.setOnClickListener {
//            startActivity<LauncherModeActivity>()
        }
        vb.btnFramelayout.setOnClickListener {
            startActivity<FrameLayoutActivity>()
        }
        vb.btnFeature.setOnClickListener {
            startActivity<FeatureActivity>()
        }
        vb.btnStatusNavigation.setOnClickListener {
            startActivity<StatusBarActivity>()
        }
        vb.btnUtilCode.setOnClickListener {
            startActivity<UtilCodeActivity>()
        }
        vb.btnSendNotification.setOnClickListener {
            // Create an explicit intent for an Activity in your app
            val intent = Intent(it.context, SchemeActivity::class.java)
            intent.putExtra("extra", "jump")
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
                .notify(UtilCodeFragment.notification_id, notification)
        }

        vb.btnFragment.setOnClickListener {
            startActivity<FragmentModeActivity>()
        }
        vb.btnStatus.setOnClickListener {
            startActivity<StatusPageActivity>()
        }
        vb.btnAnim.setOnClickListener {
            startActivity<AnimActivity>()
        }
        vb.btnUserinfo.setOnClickListener {
            startActivity<UserInfoActivity>()
        }
        vb.btnDownload.setOnClickListener {
            startDownloadActivity()
        }

        RDataBase.init()
        viewModel.opeDatabase()

        vb.fabRight.setOnClickListener {
            LoadingDialog().apply {
                initListener = {
                    binding.tvLoading.text = " - loading - "
                }
            }.show(supportFragmentManager, "dailog")
        }

        vb.fabLeft.setDebounceClickListener {
//            AlertDialog.Builder(this)
//                .setPositiveButton("confirm") { i, a ->
//
//                }
//                .setNegativeButton("cancel") { i, a ->
//
//                }
//                .setTitle("title")
//                .setMessage("content")
//                .show()
//            val resolveIntent: Intent? =
//                packageManager.getLaunchIntentForPackage("com.ziipin.social.xjfad.dev")
//            Logger.d("getLaunchIntentForPackage, $resolveIntent")
//            resolveIntent?.let {
//                it.putExtra("url", "sirdax://com.ziipin.social/demo")
//                startActivity(it)
//            }
            Logger.d("__click 1")
        }
        initNetChangeStatus()
        KvStore.set("init", "init")
        KvStore.set("init", Any())
    }

    private fun initNetChangeStatus() {
        NetWorkChangeUtils.register(object : NetWorkChangeUtils.OnNetworkStatusChangedListener {
            override fun onDisconnected() {
            }

            override fun onConnected(wifi: Boolean) {
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed() {
        try {
            val launcherIntent = Intent(Intent.ACTION_MAIN)
            launcherIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            launcherIntent.addCategory(Intent.CATEGORY_HOME)
            startActivity(launcherIntent)
//            moveTaskToBack(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

// loading dialog 的使用

class LoadingDialog : ViewBindingDialog<DialogLoadingBinding>() {
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(100.dp, 100.dp)
    }
}














