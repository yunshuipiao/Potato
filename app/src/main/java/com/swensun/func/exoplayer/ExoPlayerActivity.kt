package com.swensun.func.exoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dueeeke.videoplayer.ui.StandardVideoController
import com.swensun.potato.R
import kotlinx.android.synthetic.main.exo_player_activity.*

class ExoPlayerActivity : AppCompatActivity() {

    private var video_url =
        "https://media6.smartstudy.com/ae/07/3997/2/dest.m3u8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exo_player_activity)

        video_view.setUrl(video_url)
        val control = StandardVideoController(this)
        control.addDefaultControlComponent("lalala", false)
        control.setEnableOrientation(true)
        video_view.setVideoController(control)
        video_view.start()
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
