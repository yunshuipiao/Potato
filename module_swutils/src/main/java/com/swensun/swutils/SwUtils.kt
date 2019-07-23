package com.swensun.swutils

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.orhanobut.logger.FormatStrategy



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
        var isDebug = (application.applicationInfo.flags
        and ApplicationInfo.FLAG_DEBUGGABLE) != 0
    }


    lateinit var application: Application
}

fun getMetaData(metaName: String): String {
    try {
        val appInfo = SwUtils.application.packageManager
                .getApplicationInfo(SwUtils.application.packageName, PackageManager.GET_META_DATA)
        if (appInfo?.metaData != null) {
            return appInfo.metaData.getString(metaName) ?: ""
        } else {
            return ""
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}