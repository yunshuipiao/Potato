package com.swensun.potato.application

import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.ProcessLifecycleOwner
import com.dueeeke.videoplayer.player.ProgressManager
import com.dueeeke.videoplayer.player.VideoViewConfig
import com.dueeeke.videoplayer.player.VideoViewManager
import com.facebook.stetho.Stetho
import com.swensun.http.SimpleActivityLifecycleCallbacks
import com.swensun.swutils.SwUtils
import com.swensun.swutils.shareprefence.SharePreferencesManager
import com.swensun.swutils.util.ApplicationLifecycleObserver


class PotatoApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        if (SwUtils.debug) {
//            CrashUtil.init(this)
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationLifecycleObserver())

        VideoViewManager.config = VideoViewConfig().apply {
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
        Stetho.initializeWithDefaults(this)
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