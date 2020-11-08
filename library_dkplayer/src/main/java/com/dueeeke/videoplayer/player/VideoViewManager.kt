package com.dueeeke.videoplayer.player

import android.app.Application
import com.dueeeke.videoplayer.util.L

object VideoViewManager {
    private val mVideoViews = LinkedHashMap<String, VideoView<*>>()

    var config: VideoViewConfig = VideoViewConfig()

    /**
     * 是否在移动网络下直接播放视频
     */
    var playOnMobileNetwork = config.playOnMobileNetWork


    /**
     * 添加VideoView
     *
     * @param tag 相同tag的VideoView只会保存一个，如果tag相同则会release并移除前一个
     */
    fun add(videoView: VideoView<*>, tag: String) {
        if (videoView.context !is Application) {
            L.w("The Context of this VideoView is not an Application Context,you must remove it after release,or it will lead to memory leek.")
        }
        val old = get(tag)
        if (old != null) {
            old.release()
            remove(tag)
        }
        mVideoViews[tag] = videoView
    }

    fun get(tag: String): VideoView<*>? {
        return mVideoViews[tag]
    }

    fun remove(tag: String) {
        mVideoViews.remove(tag)
    }

    fun removeAll() {
        mVideoViews.clear()
    }

    fun releaseByTag(tag: String, isRemove: Boolean = true) {
        val videoView = get(tag)
        if (videoView != null) {
            videoView.release()
            if (isRemove) {
                remove(tag)
            }
        }
    }

    fun onBackPress(tag: String): Boolean {
        val videoView = get(tag) ?: return false
        return videoView.onBackPressed()
    }
}