package com.swensun.swutils.util

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.os.Build
import android.os.Environment
import android.text.format.Formatter
import com.swensun.swutils.SwUtils
import android.os.StatFs
import java.io.File


@SuppressLint("StaticFieldLeak")
val context: Context = SwUtils.application.applicationContext

fun getRamInfo(): String {
    val manager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
    val info = ActivityManager.MemoryInfo()
    manager.getMemoryInfo(info)
    val availMem = Formatter.formatFileSize(context, info.availMem)
    val totalMem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        Formatter.formatFileSize(context, info.totalMem)
    } else {
        "0"
    }
    return "$availMem / $totalMem"
}

fun getInternalMemorySizeInfo(): String {
    val path = Environment.getDataDirectory()
    val statFs = StatFs(path.path)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        Formatter.formatFileSize(context, statFs.availableBytes) + " / " + Formatter.formatFileSize(
            context,
            statFs.totalBytes
        )
    } else {
        val blockSize = statFs.blockSize.toLong()
        val totalBlocks = statFs.blockCount.toLong()
        val totalSize = blockSize * totalBlocks

        val availableBlocks = statFs.availableBlocks.toLong()
        val availableSize = blockSize * availableBlocks
        Formatter.formatFileSize(context, availableSize) + " / " + Formatter.formatFileSize(context, totalSize)
    }
}

fun isSdCardExist(): Boolean {
    return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
}


