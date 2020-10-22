package com.swensun.func.exoplayer.download

import android.net.Uri
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.PathUtils
import com.liulishuo.okdownload.DownloadContext
import com.liulishuo.okdownload.DownloadContextListener
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.StatusUtil
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.cause.ResumeFailedCause
import com.liulishuo.okdownload.core.listener.DownloadListener1
import com.liulishuo.okdownload.core.listener.DownloadListener2
import com.liulishuo.okdownload.core.listener.DownloadListener3
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist
import com.swensun.swutils.util.Logger
import java.io.File
import java.lang.Exception

/**
 * author : zp
 * date : 2020/10/22
 * Potato
 */

object DownloadManager {


    private var task: DownloadTask? = null

    private var downloadListener1 = object : DownloadListener1() {
        override fun taskStart(task: DownloadTask, model: Listener1Assist.Listener1Model) {
            Logger.d("task_ ts start: ${task.file}")
        }

        override fun taskEnd(
            task: DownloadTask,
            cause: EndCause,
            realCause: Exception?,
            model: Listener1Assist.Listener1Model
        ) {
            Logger.d("task_ ts end: ${task.file}")

        }

        override fun progress(task: DownloadTask, currentOffset: Long, totalLength: Long) {
            Logger.d("task_ ts progress: ${task.file}, ${currentOffset}, $totalLength")
        }

        override fun connected(
            task: DownloadTask,
            blockCount: Int,
            currentOffset: Long,
            totalLength: Long
        ) {
//            Logger.d("task_ ts connected: ${task.file}")
        }

        override fun retry(task: DownloadTask, cause: ResumeFailedCause) {
            Logger.d("task_ ts retry: ${task.file}")

        }

    }

    fun downloadM3u8(url: String, fileName: String): DownloadTask? {
        val fileNameWithSuffix = "$fileName.m3u8"
        val parentFolderName = PathUtils.getExternalAppDownloadPath() + "/Video/${fileName}/"
        task = DownloadTask.Builder(
            url,
            parentFolderName,
            fileNameWithSuffix
        )
            .setMinIntervalMillisCallbackProcess(100)
            .build()
        task?.enqueue(object : DownloadListener2() {
            override fun taskStart(task: DownloadTask) {
                Logger.d("task_ m3u8 start: ${task.file}")
            }

            override fun taskEnd(task: DownloadTask, cause: EndCause, realCause: Exception?) {
                Logger.d("task_ m3u8 end: ${task.file}")
                downloadTs(task.file?.absolutePath ?: "", url, parentFolderName)
            }
        })
        return task
    }


    fun downloadTs(
        m3u8fileName: String,
        m3u8Url: String,
        parentFolderName: String
    ) {
        val tasks = arrayListOf<DownloadTask>()
        val m3u8Uri = Uri.parse(m3u8Url)
        val tsList = parseM3u8toTs(m3u8fileName)

        /**
         * queue
         */
        val builder = DownloadContext.QueueSet()
            .setParentPath(parentFolderName)
            .setMinIntervalMillisCallbackProcess(1000)
            .setPassIfAlreadyCompleted(true)
            .commit()
        tsList.forEach {
            val tsUrl = m3u8Url.replace(m3u8Uri.lastPathSegment ?: "", it)
            Logger.d("task_ ts: $tsUrl")
            builder.bind(tsUrl)
        }
        builder.setListener(object : DownloadContextListener {
            override fun taskEnd(
                context: DownloadContext,
                task: DownloadTask,
                cause: EndCause,
                realCause: Exception?,
                remainCount: Int
            ) {
                Logger.d("task_ queue taskend:${remainCount}")
            }

            override fun queueEnd(context: DownloadContext) {
                Logger.d("task_ queue queueEnd:${context}")
            }
        })
        val context: DownloadContext = builder.build()
        context.startOnParallel(null)
    }

    private fun parseM3u8toTs(m3u8fileName: String): List<String> {
        val list = FileIOUtils.readFile2List(m3u8fileName)
        return list.filter { it.endsWith(".ts") }
    }
}