package com.dueeeke.videoplayer.controller

import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.Bitmap


/**
 * 此类的目的是为了在 ControlComponent 中既能调用 VideoView 的 api， 又能调用 BaseVideoControl 的 api。
 * 并对部分 api 做了封装，方便使用
 * 核心类
 */
class ControlWrapper(
    var playerControl: MediaPlayerControl,
    var videoController: IVideoController
) : MediaPlayerControl, IVideoController {
    override fun start() {
        playerControl.start()
    }

    override fun pause() {
        playerControl.pause()
    }

    override val duration: Long
        get() = playerControl.duration
    override var currentPosition: Long = 0
        get() = playerControl.currentPosition

    override fun seekTo(pos: Long) {
        playerControl.seekTo(pos)
    }

    override val isPlaying: Boolean
        get() = playerControl.isPlaying
    override val bufferedPercentage: Int
        get() = playerControl.bufferedPercentage

    override fun startFullScreen() {
        playerControl.startFullScreen()
    }

    override fun stopFullScreen() {
        playerControl.stopFullScreen()
    }

    override val isFullScreen: Boolean
        get() = playerControl.isFullScreen
    override var isMute: Boolean
        get() = playerControl.isMute
        set(value) {
            playerControl.isMute = value
        }

    override fun setScreenScaleType(screenScaleType: Int) {
        playerControl.setScreenScaleType(screenScaleType)
    }

    override var speed: Float
        get() = playerControl.speed
        set(value) {
            playerControl.speed = value
        }
    override val tcpSpeed: Long
        get() = playerControl.tcpSpeed

    override fun replay(resetPosition: Boolean) {
        playerControl.replay(resetPosition)
    }

    override fun setMirrorRotation(enable: Boolean) {
        playerControl.setMirrorRotation(enable)
    }

    override fun doScreenShot(): Bitmap? {
        return playerControl.doScreenShot()
    }

    override val videoSize: IntArray?
        get() = playerControl.videoSize

    override fun setRotation(rotation: Float) {
        playerControl.setRotation(rotation)
    }

    override fun startTinyScreen() {
        playerControl.startTinyScreen()
    }

    override fun stopTinyScreen() {
        playerControl.stopTinyScreen()
    }

    override val isTinyScreen: Boolean
        get() = playerControl.isTinyScreen

    override fun startFadeOut() {
        videoController.startFadeOut()
    }

    override fun stopFadeOut() {
        videoController.stopFadeOut()
    }

    override val isShowing: Boolean
        get() = videoController.isShowing
    override var isLocked: Boolean
        get() = videoController.isLocked
        set(value) {
            videoController.isLocked = value
        }

    override fun startProgress() {
        videoController.startProgress()
    }

    override fun stopProgress() {
        videoController.stopProgress()
    }

    override fun show() {
        videoController.show()
    }

    override fun hide() {
        videoController.hide()
    }

    override fun hasCutout(): Boolean {
        return videoController.hasCutout()
    }

    override val cutoutHeight: Int
        get() = videoController.cutoutHeight

    /**
     * 播放和暂停
     */
    fun togglePlay() {
        if (isPlaying) {
            pause()
        } else {
            start()
        }
    }

    /**
     * 横竖屏切换，会旋转屏幕
     */
    fun toggleFullScreen(activity: Activity?) {
        if (activity == null || activity.isFinishing) {
            return
        }
        if (isFullScreen) {
            activity.requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            stopFullScreen()
        } else {
            activity.requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            startFullScreen()
        }
    }

    /**
     * 横竖屏切换，不会旋转屏幕
     */
    fun toggleFullScreen() {
        if (isFullScreen) {
            stopFullScreen()
        } else {
            startFullScreen()
        }
    }

    /**
     * 横竖屏切换，根据适配宽高决定是否旋转屏幕
     */
    fun toggleFullScreenByVideoSize(activity: Activity?) {
        if (activity == null || activity.isFinishing) {
            return
        }
        var width = videoSize?.getOrNull(0) ?: 0
        var height = videoSize?.getOrNull(1) ?: 0
        if (isFullScreen) {
            stopFullScreen()
            if (width > height) {
                activity.requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            }
        } else {
            startFullScreen()
            if (width > height) {
                activity.requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            }
        }
    }

    /**
     * 切换锁定状态
     */
    fun toggleLockState() {
        isLocked = !isLocked
    }

    /**
     * 切换显示/隐藏状态
     */
    fun toggleShowState() {
        if (isShowing) {
            hide()
        } else {
            show()
        }
    }
}