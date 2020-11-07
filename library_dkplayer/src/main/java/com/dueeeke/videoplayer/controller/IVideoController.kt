package com.dueeeke.videoplayer.controller

/**
 * 播放器控制回调接口
 */
interface IVideoController {
    /**
     * 开始控制视图自动隐藏倒计时
     */
    fun startFadeOut()

    /**
     * 取消控制视图自动隐藏倒计时
     */
    fun stopFadeOut()

    /**
     * 控制视图是否处于显示状态
     */
    val isShowing: Boolean
    /**
     * 是否处于锁定状态
     */
    /**
     * 设置锁定状态
     * @param locked 是否锁定
     */
    var isLocked: Boolean

    /**
     * 开始刷新进度
     */
    fun startProgress()

    /**
     * 停止刷新进度
     */
    fun stopProgress()

    /**
     * 显示控制视图
     */
    fun show()

    /**
     * 隐藏控制视图
     */
    fun hide()

    /**
     * 是否需要适配刘海
     */
    fun hasCutout(): Boolean

    /**
     * 获取刘海的高度
     */
    val cutoutHeight: Int
}