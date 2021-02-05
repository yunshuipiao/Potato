package com.swensun.potato

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swensun.base.BaseActivity
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
import com.swensun.func.multidialog.MultiDialogActivity
import com.swensun.func.push.DeeplinkActivity
import com.swensun.func.recycler.RecyclerViewActivity
import com.swensun.func.room.RoomActivity
import com.swensun.func.status.StatusPageActivity
import com.swensun.func.statusbar.StatusBarActivity
import com.swensun.func.time.TimeAboutActivity
import com.swensun.func.trans.TransFontActivity
import com.swensun.func.userinfo.UserInfoActivity
import com.swensun.func.utilcode.UtilCodeActivity
import com.swensun.func.utilcode.UtilCodeFragment
import com.swensun.func.viewpager.fragment.ViewPagerActivity
import com.swensun.potato.application.createNotificationChannel
import com.swensun.swutils.ui.reverseChild
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.clipboardManager
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {

    private val today
        get() = DateFormat.format("yyyyMMdd, hh-mm-ss", System.currentTimeMillis()).toString()

    lateinit var viewModel: MainViewModel

    override fun getContentSubView(): Int {
        return R.layout.activity_main
    }
    
    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        btn_coroutines.setOnClickListener {
            startActivity<CoroutinesActivity>()
        }
        btn_viewpager.setOnClickListener {
            startActivity<ViewPagerActivity>()
        }
        btn_bottom.setOnClickListener {
            startActivity<BottomActivity>()
        }
        btn_font_trans.setOnClickListener {
            startActivity<TransFontActivity>()
        }
        btn_room.setOnClickListener {
            startActivity<RoomActivity>()
        }
        btn_time.setOnClickListener {
            startActivity<TimeAboutActivity>()
        }
        btn_lifecycle.setOnClickListener {
            startActivity<LifecycleActivity>()
        }
        btn_multi_dialog.setOnClickListener {
            startActivity<MultiDialogActivity>()
        }
        btn_livedata.setOnClickListener {
            startActivity<LiveDataActivity>()
        }
        btn_recycler.setOnClickListener {
            startActivity<RecyclerViewActivity>()
//            overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
        }
        btn_memory.setOnClickListener {
            startActivity<MemoryActivity>()
        }
        btn_custom_view.setOnClickListener {
            startActivity<CustomViewActivity>()
        }
        btn_exo_player.setOnClickListener {
            startActivity<ExoPlayerActivity>()
        }

        btn_launcher_mode.setOnClickListener {
//            startActivity<LauncherModeActivity>()
        }
        btn_framelayout.setOnClickListener {
            startActivity<FrameLayoutActivity>()
        }
        btn_feature.setOnClickListener {
            startActivity<FeatureActivity>()
        }
        btn_status_navigation.setOnClickListener {
            startActivity<StatusBarActivity>()
        }
        btn_util_code.setOnClickListener {
            startActivity<UtilCodeActivity>()
        }
        btn_send_notification.setOnClickListener {
            // Create an explicit intent for an Activity in your app
            val intent = Intent(it.context, DeeplinkActivity::class.java)
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
        fab.setOnClickListener {
            layout_btn.reverseChild()
        }
        btn_fragment.setOnClickListener {
            startActivity<FragmentModeActivity>()
        }
        btn_status.setOnClickListener {
            startActivity<StatusPageActivity>()
        }
        btn_anim.setOnClickListener {
            startActivity<AnimActivity>()
        }
        btn_userinfo.setOnClickListener {
            startActivity<UserInfoActivity>()
        }
        btn_time.performClick()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Logger.d("onSave, $outState")
    }
}


   





