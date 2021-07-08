package com.swensun.potato

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Gravity
import android.view.KeyEvent
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.*
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
import com.swensun.func.lifecycle.LifecycleActivity
import com.swensun.func.livedata.LiveDataActivity
import com.swensun.func.memory.MemoryActivity
import com.swensun.func.network.DownloadActivity
import com.swensun.func.push.SchemeActivity
import com.swensun.func.recycler.RecyclerViewActivity
import com.swensun.func.recycler.RecyclerViewFragment
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
import com.swensun.potato.databinding.BottomListDialogBinding
import com.swensun.potato.databinding.DialogLoadingBinding
import com.swensun.swutils.ui.dp
import com.swensun.swutils.ui.getWinHeight
import com.swensun.swutils.ui.setDebounceClickListener
import com.swensun.swutils.util.Logger
import com.swensun.swutils.util.NetWorkChangeUtils
import com.swensun.swutils.util.startActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val today
        get() = DateFormat.format("yyyyMMdd, hh-mm-ss", System.currentTimeMillis()).toString()

    private val viewModel: MainViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {

        binding.btnTouchEvent.setOnClickListener {
            startActivity<TouchEventActivity>()
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
            startActivity<RecyclerViewActivity>()
//            overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
        }
        binding.btnMemory.setOnClickListener {
            startActivity<MemoryActivity>()
        }
        binding.btnCustomView.setOnClickListener {
            startActivity<CustomViewActivity>()
        }
        binding.btnExoPlayer.setOnClickListener {
            startActivity<ExoPlayerActivity>()
        }

        binding.btnLauncherMode.setOnClickListener {
//            startActivity<LauncherModeActivity>()
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
        binding.btnViewpager.performClick()

        binding.fabRight.setOnClickListener {
//            LoadingDialog().apply {
//                initListener = { binding, fragment ->
//                    binding.tvLoading.text = " - loading - "
//                }
//            }.show(supportFragmentManager, "dailog")
            BottomListDialog().show(supportFragmentManager, "dialog")
        }

        var count = 1
        binding.fabLeft.setDebounceClickListener {
            if (count % 2 == 0) {
                viewModel.stringLiveData.postValue(count.toString())
            } else {
                viewModel.stringLiveData.postValue(null)
            }
            count += 1
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
        }
        viewModel.stringLiveData.debounce().notNull().observe(this) {
            Logger.d("livedata: ${it}")
        }
        initNetChangeStatus()


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                while (true) {
                    delay(2000)
                    count += 1
                    Logger.d("repeatOnLifecycle, $count")
                }
            }
        }

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
        dialog?.window?.setLayout(300.dp, 300.dp)
    }
}

class BottomListDialog : ViewBindingDialog<BottomListDialogBinding>() {
    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.BOTTOM)
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, (getWinHeight() * 0.8).toInt()
        )
        dialog?.setOnKeyListener { dialog, keyCode, event ->
            if ((keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN)) {
                //Hide your keyboard here
                if (childFragmentManager.backStackEntryCount > 0) {
                    childFragmentManager.popBackStack()
                    true
                } else {
                    false
                }
            } else
                false; //
        }

    }

    override fun initView() {
        super.initView()
        binding.fab.setOnClickListener {
            childFragmentManager.beginTransaction()
                .add(binding.container.id, RecyclerViewFragment.newInstance()).addToBackStack(null)
                .commit()
        }
        childFragmentManager.beginTransaction()
            .add(binding.container.id, RecyclerViewFragment.newInstance()).commit()
    }


}

//class BLDialog : BottomSheetDialogFragment() {
//
//    private lateinit var binding: BottomListDialogBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = BottomListDialogBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onStart() {
//        super.onStart()
//        dialog?.window?.setGravity(Gravity.BOTTOM)
//        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.recyclerView.setHasFixedSize(true)
//
//        val layoutManager = LinearLayoutManager(context)
//        binding.recyclerView.layoutManager = layoutManager
//        /**
//         * MultiTypeAdapter
//         */
//        val adapter = MultiTypeAdapter()
//        adapter.register(RViewHolderDelegate())
//        binding.recyclerView.adapter = adapter
//        val items = (0 until 50).map { RInt(it) }
//        adapter.updateItems(items)
//    }
//}














