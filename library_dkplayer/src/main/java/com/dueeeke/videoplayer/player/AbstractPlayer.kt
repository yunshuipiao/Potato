package com.dueeeke.videoplayer.player

import android.content.res.AssetFileDescriptor
import android.view.Surface
import android.view.SurfaceHolder

/**
 * 抽象的播放器，继承此接口扩展自己的播放器
 * Created by dueeeke on 2017/12/21.
 */
abstract class AbstractPlayer {
    /**
     * 播放器事件回调
     */
    @JvmField
    protected var mPlayerEventListener: PlayerEventListener? = null

    /**
     * 初始化播放器实例
     */
    abstract fun initPlayer()

    /**
     * 设置播放地址
     *
     * @param path    播放地址
     * @param headers 播放地址请求头
     */
    abstract fun setDataSource(path: String?, headers: Map<String?, String?>?)

    /**
     * 用于播放raw和asset里面的视频文件
     */
    abstract fun setDataSource(fd: AssetFileDescriptor?)

    /**
     * 播放
     */
    abstract fun start()

    /**
     * 暂停
     */
    abstract fun pause()

    /**
     * 停止
     */
    abstract fun stop()

    /**
     * 准备开始播放（异步）
     */
    abstract fun prepareAsync()

    /**
     * 重置播放器
     */
    abstract fun reset()

    /**
     * 是否正在播放
     */
    abstract val isPlaying: Boolean

    /**
     * 调整进度
     */
    abstract fun seekTo(time: Long)

    /**
     * 释放播放器
     */
    abstract fun release()

    /**
     * 获取当前播放的位置
     */
    abstract val currentPosition: Long

    /**
     * 获取视频总时长
     */
    abstract val duration: Long

    /**
     * 获取缓冲百分比
     */
    abstract val bufferedPercentage: Int

    /**
     * 设置渲染视频的View,主要用于TextureView
     */
    abstract fun setSurface(surface: Surface?)

    /**
     * 设置渲染视频的View,主要用于SurfaceView
     */
    abstract fun setDisplay(holder: SurfaceHolder?)

    /**
     * 设置音量
     */
    abstract fun setVolume(v1: Float, v2: Float)

    /**
     * 设置是否循环播放
     */
    abstract fun setLooping(isLooping: Boolean)

    /**
     * 设置其他播放配置
     */
    abstract fun setOptions()
    /**
     * 获取播放速度
     */
    /**
     * 设置播放速度
     */
    abstract var speed: Float

    /**
     * 获取当前缓冲的网速
     */
    abstract val tcpSpeed: Long

    /**
     * 绑定VideoView
     */
    fun setPlayerEventListener(playerEventListener: PlayerEventListener?) {
        mPlayerEventListener = playerEventListener
    }

    interface PlayerEventListener {
        fun onError()
        fun onCompletion()
        fun onInfo(what: Int, extra: Int)
        fun onPrepared()
        fun onVideoSizeChanged(width: Int, height: Int)
    }

    companion object {
        /**
         * 开始渲染视频画面
         */
        const val MEDIA_INFO_VIDEO_RENDERING_START = 3

        /**
         * 缓冲开始
         */
        const val MEDIA_INFO_BUFFERING_START = 701

        /**
         * 缓冲结束
         */
        const val MEDIA_INFO_BUFFERING_END = 702

        /**
         * 视频旋转信息
         */
        const val MEDIA_INFO_VIDEO_ROTATION_CHANGED = 10001
    }
}