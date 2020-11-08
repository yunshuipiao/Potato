package com.swensun.potato.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.dueeeke.videoplayer.player.ProgressManager
import com.dueeeke.videoplayer.player.VideoViewConfig
import com.dueeeke.videoplayer.player.VideoViewManager
import com.swensun.TimeLog
import com.swensun.func.room.database.RDataBase
import com.swensun.http.SimpleActivityLifecycleCallbacks
import com.swensun.library_crash.CrashUtil
import com.swensun.swutils.SwUtils
import com.swensun.swutils.shareprefence.SharePreferencesManager
import org.jetbrains.anko.alert


class PotatoApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        alert {

        }
//        val callback = object : Choreographer.FrameCallback {
//            override fun doFrame(frameTimeNanos: Long) {
//                val diff = frameTimeNanos / 1000000 - startTime
//                Logger.d("diff: $diff")
//                Choreographer.getInstance().postFrameCallback(this)
//            }
//        }
//        startTime = System.currentTimeMillis()
//        Choreographer.getInstance().postFrameCallback(callback)
//        AppUtils.registerAppStatusChangedListener(object: Utils.OnAppStatusChangedListener
//        {
//            override fun onBackground(activity: Activity?) {
//                toast("后台, $activity")
//
//            }
//
//            override fun onForeground(activity: Activity?) {
//                toast("前台, $activity")
//            }
//        })

        registerActivityLifecycleCallbacks(object : SimpleActivityLifecycleCallbacks() {
        })

        if (SwUtils.debug) {
            CrashUtil.init(this)
        }
        TimeLog.log("2")

        VideoViewManager.setConfig(
            VideoViewConfig().apply {
                progressManager = object : ProgressManager() {
                    override fun saveProgress(url: String?, progress: Long) {
                        SharePreferencesManager.put(url ?: "", progress)
                    }

                    override fun getSavedProgress(url: String?): Long {
                        val progress = SharePreferencesManager[url ?: "", 0L]
                        return progress
                    }
                }
            }
        )
        RDataBase.init()
    }

}

fun createNotificationChannel(
    name: String = "potato",
    desc: String = "potato",
    channelId: String = "potato"
): String {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = desc
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            SwUtils.application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        return channelId
    } else {
        return ""
    }
}