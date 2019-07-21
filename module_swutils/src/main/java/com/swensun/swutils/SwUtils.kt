package com.swensun.swutils

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

object SwUtils {
    fun init(application: Application) {
        SwUtils.application = application
        Logger.addLogAdapter(AndroidLogAdapter())
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