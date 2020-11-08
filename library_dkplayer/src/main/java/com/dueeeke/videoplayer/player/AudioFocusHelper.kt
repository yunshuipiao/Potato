package com.dueeeke.videoplayer.player

import android.content.Context
import android.media.AudioManager
import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference

class AudioFocusHelper(videoView: VideoView<*>) : AudioManager.OnAudioFocusChangeListener {

    private val mWeakVideoView = WeakReference(videoView)
    private val mAudioManager by lazy { videoView.context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }

    private var mCurrentFocus = 0
    private val mHandler = Handler(Looper.getMainLooper())

    private var mStartRequested = false
    private var mPausedForLoss = false


    override fun onAudioFocusChange(focusChange: Int) {
        if (mCurrentFocus == focusChange) {
            return
        }

        //由于onAudioFocusChange有可能在子线程调用，
        //故通过此方式切换到主线程去执行

        //由于onAudioFocusChange有可能在子线程调用，
        //故通过此方式切换到主线程去执行
        mHandler.post {
            handleAudioFocusChange(focusChange)
        }
    }

    private fun handleAudioFocusChange(focusChange: Int) {
        val videoView = mWeakVideoView.get()
        videoView?.let {
            when (focusChange) {
                AudioManager.AUDIOFOCUS_GAIN, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT -> {
                    if (mStartRequested || mPausedForLoss) {
                        it.start()
                        mStartRequested = false
                        mPausedForLoss = false
                    }
                    if (videoView.isMute == false) {
                        videoView.setVolume(1f, 1f)
                    }
                }
                AudioManager.AUDIOFOCUS_LOSS, AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                    if (videoView.isPlaying) {
                        mPausedForLoss = true
                        videoView.pause()
                    }
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                    if (videoView.isPlaying && videoView.isMute == false) {
                        videoView.setVolume(0.1f, 0.1f)
                    }
                }
            }
        }
    }

    fun requestFocus() {
        if (mCurrentFocus == AudioManager.AUDIOFOCUS_GAIN) {
            return
        }
        val status = mAudioManager.requestAudioFocus(
            this,
            AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN
        )
        if (AudioManager.AUDIOFOCUS_REQUEST_GRANTED == status) {
            mCurrentFocus = AudioManager.AUDIOFOCUS_GAIN
            return
        }
        mStartRequested = true
    }

    fun abandonFocus() {
        mStartRequested = false
        mAudioManager.abandonAudioFocus(this)
    }
}