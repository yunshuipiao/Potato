package com.swensun.func.network

import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.PathUtils

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
        

    }
}