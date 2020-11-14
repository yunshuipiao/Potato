package com.dueeeke.videoplayer.controller

import android.view.View
import android.view.animation.Animation

/**
 * 状态的相关回调接口
 */
interface IControlComponent {

//    val controlWrapper: ControlWrapper

    fun attach(controlWrapper: ControlWrapper)


    val view: View?

    /**
     * 是否可见
     *
     * @param isVisible
     * @param anim
     */
    fun onVisibilityChanged(isVisible: Boolean, anim: Animation?)

    /**
     * 播放状态回调
     *
     * @param playState
     */
    fun onPlayStateChanged(playState: Int)

    /**
     * 播放器状态回调
     *
     * @param playerState
     */
    fun onPlayerStateChanged(playerState: Int)

    /**
     * 进度回调
     *
     * @param duration
     * @param position
     */
    fun setProgress(duration: Int, position: Int)

    /**
     * 是否锁定
     *
     * @param isLocked
     */
    fun onLockStateChanged(isLocked: Boolean)
}