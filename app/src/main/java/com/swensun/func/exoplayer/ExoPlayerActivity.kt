package com.swensun.func.exoplayer

import android.app.AlertDialog
import android.os.Bundle
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.dueeeke.videoplayer.ui.StandardVideoController
import com.swensun.base.Base2Activity
import com.swensun.potato.databinding.ExoPlayerActivityBinding

class ExoPlayerActivity : Base2Activity<ExoPlayerActivityBinding>() {

    //    private var video_url = "https://media6.smartstudy.com/ae/07/3997/2/dest.m3u8"
//    private var video_url = "http://assets.zvod.badambiz.com/room_4361_1605611400_1605612600.m3u8"
    private var video_url =
        "https://549-cn-north-4.cdn-vod.huaweicloud.com/asset/dbaee6a406c4eaab07635ef37106fe19/d314a35329ce6dcd2452e586c7cbced8.mp3"

    override fun onPause() {
        super.onPause()
        binding.videoView.pause()
    }


    override fun onResume() {
        super.onResume()
        binding.videoView.resume()
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.videoView.release()

    }

    override fun onBackPressed() {
        if (!binding.videoView.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.videoView.setUrl(video_url)
        val control = StandardVideoController(this)
        control.addDefaultControlComponent("dest", false)
        control.setEnableOrientation(true)
        binding.videoView.videoController = control
        binding.videoView.start()
        binding.fab.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("lala")
                .create()
                .show()
        }

        binding.deleteFile.setOnClickListener {
            FileUtils.delete(PathUtils.getExternalAppDownloadPath() + "/Video/dest")
        }
    }
}
