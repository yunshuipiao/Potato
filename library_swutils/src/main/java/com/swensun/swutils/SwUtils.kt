package com.swensun.swutils

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.swensun.swutils.shareprefence.SharePreferencesManager
import kotlin.properties.Delegates


object SwUtils {
    fun init(application: Application) {
        SwUtils.application = application
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
            .methodCount(0)         // (Optional) How many method line to show. Default 2
            .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
            .tag("sw")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        isDebug = (application.applicationInfo.flags
                and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        SharePreferencesManager.init(application)
    }

    var isDebug: Boolean by Delegates.notNull()
        private set
    lateinit var application: Application
        private set
}