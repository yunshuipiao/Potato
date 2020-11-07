package com.dueeeke.videoplayer.controller

import android.graphics.Bitmap

/**
 * 播放器控制接口
 */
interface MediaPlayerControl {
    fun start()
    fun pause()
    val duration: Long
    val currentPosition: Long
    fun seekTo(pos: Long)
    val isPlaying: Boolean
    val bufferedPercentage: Int
    fun startFullScreen()
    fun stopFullScreen()
    val isFullScreen: Boolean
    var isMute: Boolean
    fun setScreenScaleType(screenScaleType: Int)
    var speed: Float
    val tcpSpeed: Long
    fun replay(resetPosition: Boolean)
    fun setMirrorRotation(enable: Boolean)
    fun doScreenShot(): Bitmap?
    val videoSize: IntArray?
    fun setRotation(rotation: Float)
    fun startTinyScreen()
    fun stopTinyScreen()
    val isTinyScreen: Boolean
}