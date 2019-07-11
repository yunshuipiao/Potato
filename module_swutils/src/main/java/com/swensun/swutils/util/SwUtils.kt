package com.swensun.swutils.util

import android.app.Application
import android.content.pm.PackageManager

object SwUtils {
    fun init(application: Application) {
        this.application = application
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