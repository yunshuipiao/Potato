package com.swensun.func.exoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.dueeeke.videoplayer.ui.StandardVideoController
import com.swensun.potato.R
import kotlinx.android.synthetic.main.exo_player_activity.*
import org.jetbrains.anko.toast

class ExoPlayerActivity : AppCompatActivity() {

    private var video_url =
        "https://media6.smartstudy.com/ae/07/3997/2/dest.m3u8"

    private var local_video_url = PathUtils.getExternalAppDownloadPath() + "/Video/dest/dest.m3u8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exo_player_activity)
        video_view.setUrl(local_video_url)
        val control = StandardVideoController(this)
        control.addDefaultControlComponent("dest", false)
        control.setEnableOrientation(true)
        video_view.setVideoController(control)
        video_view.start()
        fab.setOnClickListener {
            val file = FileUtils.getFileByPath(local_video_url)
            toast(file.path)
        }
    }

    override fun onPause() {
        super.onPause()
        video_view.pause()
    }


    override fun onResume() {
        super.onResume()
        video_view.resume()
    }


    override fun onDestroy() {
        super.onDestroy()
        video_view.release()

    }

    override fun onBackPressed() {
        if (!video_view.onBackPressed()) {
            super.onBackPressed()
        }
    }


}
