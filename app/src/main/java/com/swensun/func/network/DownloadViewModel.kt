package com.swensun.func.network

import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.PathUtils
import com.swensun.swutils.SwUtils
import com.tonyodev.fetch2.*
import com.tonyodev.fetch2core.DownloadBlock
import com.tonyodev.fetch2core.Func

/**
 * author : zp
 * date : 2021/3/23
 * Potato
 */

class DownloadViewModel : ViewModel() {

    var livePkg = "com.badambiz.live"
    var liveApkUrl = "https://zvod-share.badambiz.com/g/live_live_banner"
    val parentPath = PathUtils.getExternalAppFilesPath() + "/live/"
    val fileName = "live.apk"
    
    fun downloadLive() {
        val config = FetchConfiguration.Builder(SwUtils.application)
            .setDownloadConcurrentLimit(2)
            .build()
        val fetch = Fetch.Impl.getInstance(config)
        val request = Request(liveApkUrl, parentPath + fileName)
        fetch.addListener(object: FetchListener {
            override fun onAdded(download: Download) {

            }

            override fun onCancelled(download: Download) {
            }

            override fun onCompleted(download: Download) {
            }

            override fun onDeleted(download: Download) {
            }

            override fun onDownloadBlockUpdated(
                download: Download,
                downloadBlock: DownloadBlock,
                totalBlocks: Int
            ) {
            }

            override fun onError(download: Download, error: Error, throwable: Throwable?) {
            }

            override fun onPaused(download: Download) {
            }

            override fun onProgress(
                download: Download,
                etaInMilliSeconds: Long,
                downloadedBytesPerSecond: Long
            ) {
                com.swensun.swutils.util.Logger.d("progress: ${download.progress}, ${etaInMilliSeconds}, $downloadedBytesPerSecond")
            }

            override fun onQueued(download: Download, waitingOnNetwork: Boolean) {
            }

            override fun onRemoved(download: Download) {
            }

            override fun onResumed(download: Download) {
            }

            override fun onStarted(
                download: Download,
                downloadBlocks: List<DownloadBlock>,
                totalBlocks: Int
            ) {
            }

            override fun onWaitingNetwork(download: Download) {
            }

        })
        fetch.enqueue(request, Func {

        }, Func {
            
        })

    }
}