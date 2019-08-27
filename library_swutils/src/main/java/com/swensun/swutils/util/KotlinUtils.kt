package com.swensun.swutils.util

import android.content.pm.PackageManager
import com.swensun.swutils.SwUtils

/**
 * Created  on 2019-07-16
 *
 * @author sunwen
 *
 * 常用的工具类或者代码小技巧：kotlin
 */
fun getMetaData(metaName: String): String {
    try {
        val appInfo = SwUtils.application.packageManager
            .getApplicationInfo(SwUtils.application.packageName, PackageManager.GET_META_DATA)
        appInfo?.let {
            return it.metaData.getString(metaName) ?: ""
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}

fun testNull(str: String?): String {
    if (str == null) {
        return ""
    } else {
        return str
    }
}
