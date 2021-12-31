package com.swensun.potato

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.debounce
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.notNull
import com.swensun.base.BaseActivity
import com.swensun.base.ViewBindingDialog
import com.swensun.func.anim.AnimActivity
import com.swensun.func.bottom.BottomActivity
import com.swensun.func.coroutines.ui.CoroutinesActivity
import com.swensun.func.customview.CustomViewActivity
import com.swensun.func.customview.FrameLayoutActivity
import com.swensun.func.exoplayer.ExoPlayerActivity
import com.swensun.func.feature.FeatureActivity
import com.swensun.func.fragment.FragmentModeActivity
import com.swensun.func.framelayout.startFragmentContainerActivity
import com.swensun.func.lifecycle.LifecycleActivity
import com.swensun.func.livedata.LiveDataActivity
import com.swensun.func.memory.MemoryFragment
import com.swensun.func.network.DownloadActivity
import com.swensun.func.push.SchemeActivity
import com.swensun.func.recycler.RecyclerViewFragment
import com.swensun.func.room.RoomActivity
import com.swensun.func.room.database.RDataBase
import com.swensun.func.status.StatusPageActivity
import com.swensun.func.statusbar.StatusBarActivity
import com.swensun.func.time.TimeAboutActivity
import com.swensun.func.touch.TouchEventFragment
import com.swensun.func.trans.TransFontActivity
import com.swensun.func.userinfo.UserInfoActivity
import com.swensun.func.utilcode.UtilCodeActivity
import com.swensun.func.utilcode.UtilCodeFragment
import com.swensun.func.viewpager.fragment.ViewPagerActivity
import com.swensun.potato.application.createNotificationChannel
import com.swensun.potato.databinding.ActivityMainBinding
import com.swensun.potato.databinding.DialogLoadingBinding
import com.swensun.swutils.ui.dp
import com.swensun.swutils.ui.drawable
import com.swensun.swutils.ui.setDebounceClickListener
import com.swensun.swutils.util.Logger
import com.swensun.swutils.util.NetWorkChangeUtils
import com.swensun.swutils.util.startActivity
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val today
        get() = DateFormat.format("yyyyMMdd, hh-mm-ss", System.currentTimeMillis()).toString()

    private val viewModel: MainViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {

        binding.btnTouchEvent.setOnClickListener {
            startFragmentContainerActivity<TouchEventFragment>()
        }

        binding.btnCoroutines.setOnClickListener {
            startActivity<CoroutinesActivity>()
        }
        binding.btnViewpager.setOnClickListener {
            startActivity<ViewPagerActivity>()
        }
        binding.btnBottom.setOnClickListener {
            startActivity<BottomActivity>()
        }
        binding.btnFontTrans.setOnClickListener {
            startActivity<TransFontActivity>()
        }
        binding.btnRoom.setOnClickListener {
            startActivity<RoomActivity>()
        }
        binding.btnTime.setOnClickListener {
            startActivity<TimeAboutActivity>()
        }
        binding.btnLifecycle.setOnClickListener {
            startActivity<LifecycleActivity>()
        }
        binding.btnMultiDialog.setOnClickListener {
        }
        binding.btnLivedata.setOnClickListener {
            startActivity<LiveDataActivity>()
        }
        binding.btnRecycler.setOnClickListener {
//            startActivity<RecyclerViewActivity>()
            startFragmentContainerActivity<RecyclerViewFragment>(Bundle().apply {
                putInt("count", 20)
            })
        }
        binding.btnMemory.setOnClickListener {
            startFragmentContainerActivity<MemoryFragment>()
        }
        binding.btnCustomView.setOnClickListener {
            startActivity<CustomViewActivity>()
        }
        binding.btnExoPlayer.setOnClickListener {
            startActivity<ExoPlayerActivity>()
        }

        binding.btnLauncherMode.setOnClickListener {
            
        }
        binding.btnFramelayout.setOnClickListener {
            startActivity<FrameLayoutActivity>()
        }
        binding.btnFeature.setOnClickListener {
            startActivity<FeatureActivity>()
        }
        binding.btnStatusNavigation.setOnClickListener {
            startActivity<StatusBarActivity>()
        }
        binding.btnUtilCode.setOnClickListener {
            startActivity<UtilCodeActivity>()
        }
        binding.btnSendNotification.setOnClickListener {
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

        binding.btnFragment.setOnClickListener {
            startActivity<FragmentModeActivity>()
        }
        binding.btnStatus.setOnClickListener {
            startActivity<StatusPageActivity>()
        }
        binding.btnAnim.setOnClickListener {
            startActivity<AnimActivity>()
        }
        binding.btnUserinfo.setOnClickListener {
            startActivity<UserInfoActivity>()
        }
        binding.btnDownload.setOnClickListener {
            startActivity<DownloadActivity>()
        }

        RDataBase.init()
        viewModel.openDatabase()

        binding.fabRight.setOnClickListener {
//            LoadingDialog().apply {
//                initListener = { binding, fragment ->
//                    binding.tvLoading.text = " - loading - "
//                }
//            }.show(supportFragmentManager, "dailog")
//            timeConsumingMethod {
//                Logger.d("__async to sync, ${it}")
//            }
            lifecycleScope.launchWhenResumed {
                val result = suspendCoroutine<Int> { cont ->
                    timeConsumingMethod {
                        cont.resume(it)
                    }
                }
                Logger.d("__async to sync, ${result}")
            }
        }

        var count = 1
        binding.fabLeft.setDebounceClickListener {
            if (count % 2 == 0) {
                viewModel.stringLiveData.postValue(count.toString())
            } else {
                viewModel.stringLiveData.postValue(null)
            }
            count += 1
        }
        viewModel.stringLiveData.debounce().notNull().observe(this) {
            Logger.d("livedata: ${it}")
        }
//        initNetChangeStatus()

        binding.btnDownload.performClick()
    }

    private fun initNetChangeStatus() {
        NetWorkChangeUtils.register(object : NetWorkChangeUtils.OnNetworkStatusChangedListener {
            override fun onDisconnected() {
            }

            override fun onConnected(wifi: Boolean) {
            }
        })

        //change two
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
        dialog?.window?.setLayout(300.dp, 300.dp)
    }

    override fun initView() {
        super.initView()
        binding.root.background = drawable(R.color.white) {
            radius = 100.dp
        }
    }
}


fun timeConsumingMethod(callback: (Int) -> Unit) {
    Thread.sleep(1000)
    callback.invoke(10)
}















