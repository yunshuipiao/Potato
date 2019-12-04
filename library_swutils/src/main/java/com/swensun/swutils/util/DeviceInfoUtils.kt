package com.swensun.swutils.util

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.os.Environment
import android.text.format.Formatter
import com.swensun.swutils.SwUtils
import android.os.StatFs


@SuppressLint("StaticFieldLeak")
private val context: Context = SwUtils.application.applicationContext

fun getRamInfo(): String {
    val manager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
    val info = ActivityManager.MemoryInfo()
    manager.getMemoryInfo(info)
    val availMem = Formatter.formatFileSize(context, info.availMem)
    val totalMem = Formatter.formatFileSize(context, info.totalMem)
    return "$availMem / $totalMem"
}

fun getInternalMemorySizeInfo(): String {
    val path = Environment.getDataDirectory()
    val statFs = StatFs(path.path)
    return Formatter.formatFileSize(context, statFs.availableBytes) + " / " +
            Formatter.formatFileSize(context, statFs.totalBytes)
}

fun isSdCardExist(): Boolean {
    return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
}


