package com.swensun.func.exoplayer

import android.app.AlertDialog
import android.os.Bundle
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.dueeeke.videoplayer.ui.StandardVideoController
import com.swensun.base.BaseActivity
import com.swensun.potato.databinding.ExoPlayerActivityBinding

class ExoPlayerActivity : BaseActivity<ExoPlayerActivityBinding>() {

    //    private var video_url = "https://media6.smartstudy.com/ae/07/3997/2/dest.m3u8"
//    private var video_url = "http://assets.zvod.badambiz.com/room_4361_1605611400_1605612600.m3u8"
    private var video_url =
        "https://549-cn-north-4.cdn-vod.huaweicloud.com/asset/dbaee6a406c4eaab07635ef37106fe19/d314a35329ce6dcd2452e586c7cbced8.mp3"

    override fun onPause() {
        super.onPause()
        vb.videoView.pause()
    }


    override fun onResume() {
        super.onResume()
        vb.videoView.resume()
    }


    override fun onDestroy() {
        super.onDestroy()
        vb.videoView.release()

    }

    override fun onBackPressed() {
        if (!vb.videoView.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.videoView.setUrl(video_url)
        val control = StandardVideoController(this)
        control.addDefaultControlComponent("dest", false)
        control.setEnableOrientation(true)
        vb.videoView.videoController = control
        vb.videoView.start()
        vb.fab.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("lala")
                .create()
                .show()
        }

        vb.deleteFile.setOnClickListener {
            FileUtils.delete(PathUtils.getExternalAppDownloadPath() + "/Video/dest")
        }
    }
}
