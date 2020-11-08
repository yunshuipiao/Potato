package com.dueeeke.videoplayer.player

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.*
import android.net.Uri
import android.os.Build
import android.view.Surface
import android.view.SurfaceHolder

/**
 * 封装系统的MediaPlayer，不推荐，系统的MediaPlayer兼容性较差，建议使用IjkPlayer或者ExoPlayer
 */
class AndroidMediaPlayer(context: Context) : AbstractPlayer() {
    protected var mMediaPlayer: MediaPlayer? = null
    override var bufferedPercentage = 0
        private set
    private val mAppContext: Context
    private var mIsPreparing = false
    override fun initPlayer() {
        mMediaPlayer = MediaPlayer()
        setOptions()
        mMediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mMediaPlayer!!.setOnErrorListener(onErrorListener)
        mMediaPlayer!!.setOnCompletionListener(onCompletionListener)
        mMediaPlayer!!.setOnInfoListener(onInfoListener)
        mMediaPlayer!!.setOnBufferingUpdateListener(onBufferingUpdateListener)
        mMediaPlayer!!.setOnPreparedListener(onPreparedListener)
        mMediaPlayer!!.setOnVideoSizeChangedListener(onVideoSizeChangedListener)
    }

    override fun setDataSource(path: String?, headers: Map<String?, String?>?) {
        try {
            mMediaPlayer!!.setDataSource(mAppContext, Uri.parse(path), headers)
        } catch (e: Exception) {
            mPlayerEventListener?.onError()
        }
    }

    override fun setDataSource(fd: AssetFileDescriptor?) {
        try {
            mMediaPlayer!!.setDataSource(fd!!.fileDescriptor, fd.startOffset, fd.length)
        } catch (e: Exception) {
            mPlayerEventListener?.onError()
        }
    }

    override fun start() {
        try {
            mMediaPlayer!!.start()
        } catch (e: IllegalStateException) {
            mPlayerEventListener?.onError()
        }
    }

    override fun pause() {
        try {
            mMediaPlayer!!.pause()
        } catch (e: IllegalStateException) {
            mPlayerEventListener?.onError()
        }
    }

    override fun stop() {
        try {
            mMediaPlayer!!.stop()
        } catch (e: IllegalStateException) {
            mPlayerEventListener?.onError()
        }
    }

    override fun prepareAsync() {
        try {
            mIsPreparing = true
            mMediaPlayer!!.prepareAsync()
        } catch (e: IllegalStateException) {
            mPlayerEventListener?.onError()
        }
    }

    override fun reset() {
        mMediaPlayer!!.reset()
        mMediaPlayer!!.setSurface(null)
        mMediaPlayer!!.setDisplay(null)
        mMediaPlayer!!.setVolume(1f, 1f)
    }

    override val isPlaying: Boolean
        get() = mMediaPlayer!!.isPlaying

    override fun seekTo(time: Long) {
        try {
            mMediaPlayer!!.seekTo(time.toInt())
        } catch (e: IllegalStateException) {
            mPlayerEventListener?.onError()
        }
    }

    override fun release() {
        mMediaPlayer!!.setOnErrorListener(null)
        mMediaPlayer!!.setOnCompletionListener(null)
        mMediaPlayer!!.setOnInfoListener(null)
        mMediaPlayer!!.setOnBufferingUpdateListener(null)
        mMediaPlayer!!.setOnPreparedListener(null)
        mMediaPlayer!!.setOnVideoSizeChangedListener(null)
        object : Thread() {
            override fun run() {
                try {
                    mMediaPlayer!!.release()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    override val currentPosition: Long
        get() = mMediaPlayer!!.currentPosition.toLong()
    override val duration: Long
        get() = mMediaPlayer!!.duration.toLong()

    override fun setSurface(surface: Surface?) {
        try {
            mMediaPlayer!!.setSurface(surface)
        } catch (e: Exception) {
            mPlayerEventListener?.onError()
        }
    }

    override fun setDisplay(holder: SurfaceHolder?) {
        try {
            mMediaPlayer!!.setDisplay(holder)
        } catch (e: Exception) {
            mPlayerEventListener?.onError()
        }
    }

    override fun setVolume(v1: Float, v2: Float) {
        mMediaPlayer!!.setVolume(v1, v2)
    }

    override fun setLooping(isLooping: Boolean) {
        mMediaPlayer!!.isLooping = isLooping
    }

    override fun setOptions() {}

    // only support above Android M
    // only support above Android M
    override var speed: Float
        get() {
            // only support above Android M
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    return mMediaPlayer!!.playbackParams.speed
                } catch (e: Exception) {
                    mPlayerEventListener?.onError()
                }
            }
            return 1f
        }
        set(speed) {
            // only support above Android M
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    mMediaPlayer!!.playbackParams = mMediaPlayer!!.playbackParams.setSpeed(speed)
                } catch (e: Exception) {
                    mPlayerEventListener?.onError()
                }
            }
        }

    // no support
    override val tcpSpeed: Long
        get() =// no support
            0
    private val onErrorListener = MediaPlayer.OnErrorListener { mp, what, extra ->
        mPlayerEventListener?.onError()
        true
    }
    private val onCompletionListener =
        OnCompletionListener { mPlayerEventListener?.onCompletion() }
    private val onInfoListener =
        MediaPlayer.OnInfoListener { mp, what, extra -> //解决MEDIA_INFO_VIDEO_RENDERING_START多次回调问题
            if (what == MEDIA_INFO_VIDEO_RENDERING_START) {
                if (mIsPreparing) {
                    mPlayerEventListener?.onInfo(what, extra)
                    mIsPreparing = false
                }
            } else {
                mPlayerEventListener?.onInfo(what, extra)
            }
            true
        }
    private val onBufferingUpdateListener: OnBufferingUpdateListener =
        object : OnBufferingUpdateListener {
            override fun onBufferingUpdate(mp: MediaPlayer, percent: Int) {
                bufferedPercentage = percent
            }
        }
    private val onPreparedListener = OnPreparedListener {
        mPlayerEventListener?.onPrepared()
        start()
    }
    private val onVideoSizeChangedListener = OnVideoSizeChangedListener { mp, width, height ->
        val videoWidth = mp.videoWidth
        val videoHeight = mp.videoHeight
        if (videoWidth != 0 && videoHeight != 0) {
            mPlayerEventListener?.onVideoSizeChanged(videoWidth, videoHeight)
        }
    }

    init {
        mAppContext = context.applicationContext
    }
}