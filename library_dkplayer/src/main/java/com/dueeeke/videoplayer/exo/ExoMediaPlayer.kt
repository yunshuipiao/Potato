package com.dueeeke.videoplayer.exo

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.os.Handler
import android.view.Surface
import android.view.SurfaceHolder
import com.dueeeke.videoplayer.player.AbstractPlayer
import com.dueeeke.videoplayer.player.VideoViewManager
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.analytics.AnalyticsCollector
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId
import com.google.android.exoplayer2.source.MediaSourceEventListener
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.MappingTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.util.Clock
import com.google.android.exoplayer2.util.EventLogger
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.video.VideoListener

class ExoMediaPlayer(context: Context) : AbstractPlayer(), VideoListener, Player.EventListener {

    private val appContext = context.applicationContext
    private val mediaSourceHelper = ExoMediaSourceHelper.getInstance(context)
    private var internalPlayer: SimpleExoPlayer? = null

    private var rendersFactory: RenderersFactory = DefaultRenderersFactory(appContext)
    private var trackSelector: TrackSelector = DefaultTrackSelector(appContext)
    private var loadControl: LoadControl = DefaultLoadControl()
    private var speedPlaybackParameters: PlaybackParameters? = null

    private var lastReportedPlaybackState = Player.STATE_IDLE
    private var lastReportedPlayerWhenReady = true
    private var isPreparing = false
    private var isBuffering = false

    private var mediaSource: MediaSource? = null
    private var isCache = true

    override fun initPlayer() {
        internalPlayer = SimpleExoPlayer.Builder(
            appContext,
            rendersFactory,
            trackSelector,
            loadControl,
            DefaultBandwidthMeter.getSingletonInstance(appContext),
            Util.getLooper(),
            AnalyticsCollector(Clock.DEFAULT),
            true,
            Clock.DEFAULT
        ).build()
        setOptions()

        //播放器日志
        if (VideoViewManager.config.isEnableLog && trackSelector is MappingTrackSelector) {
            internalPlayer?.addAnalyticsListener(
                EventLogger(
                    trackSelector as MappingTrackSelector,
                    "Exoplayer"
                )
            )
        }
        internalPlayer?.addListener(this)
        internalPlayer?.addVideoListener(this)
    }

    override fun setDataSource(path: String?, headers: Map<String?, String?>?) {
        mediaSource = mediaSourceHelper.getMediaSource(path, headers, isCache)
    }

    override fun setDataSource(fd: AssetFileDescriptor?) {
        // no support
    }

    override fun start() {
        internalPlayer?.playWhenReady = true
    }

    override fun pause() {
        internalPlayer?.playWhenReady = false
    }

    override fun stop() {
        internalPlayer?.stop()
    }

    override fun prepareAsync() {
        if (speedPlaybackParameters != null) {
            internalPlayer?.setPlaybackParameters(speedPlaybackParameters)
        }
        isPreparing = true
        mediaSource?.addEventListener(Handler(), mediaSourceEventListener)
        mediaSource?.let {
            internalPlayer?.prepare(it)
        }
    }

    private val mediaSourceEventListener: MediaSourceEventListener =
        object : MediaSourceEventListener {
            override fun onReadingStarted(windowIndex: Int, mediaPeriodId: MediaPeriodId) {
                if (isPreparing) {
                    mPlayerEventListener?.onPrepared()
                }
            }
        }

    override fun reset() {
        internalPlayer?.let {
            it.stop(true)
            it.setVideoSurface(null)
            isPreparing = false
            isBuffering = false
            lastReportedPlaybackState = Player.STATE_IDLE
            lastReportedPlayerWhenReady = false
        }
    }

    override val isPlaying: Boolean
        get() {
            internalPlayer?.let {
                val state = it.playbackState
                when (state) {
                    Player.STATE_BUFFERING, Player.STATE_READY -> {
                        return it.playWhenReady
                    }
                    else -> {
                        return false
                    }
                }

            }
            return false
        }

    override fun seekTo(time: Long) {
        internalPlayer?.seekTo(time)
    }

    override fun release() {
        internalPlayer?.removeListener(this)
        internalPlayer?.removeVideoListener(this)
        val player = internalPlayer
        internalPlayer = null
        Thread {
            //异步释放，防止卡顿
            player?.release()
        }.start()
    }

    override val currentPosition: Long
        get() = internalPlayer?.currentPosition ?: 0
    override val duration: Long
        get() = internalPlayer?.duration ?: 0
    override val bufferedPercentage: Int
        get() = internalPlayer?.bufferedPercentage ?: 0

    override fun setSurface(surface: Surface?) {
        internalPlayer?.setVideoSurface(surface)
    }

    override fun setDisplay(holder: SurfaceHolder?) {
        setSurface(holder?.surface)
    }

    override fun setVolume(v1: Float, v2: Float) {
        internalPlayer?.volume = (v1 + v2) / 2
    }

    override fun setLooping(isLooping: Boolean) {
        internalPlayer?.repeatMode =
            if (isLooping) Player.REPEAT_MODE_ALL else Player.REPEAT_MODE_OFF
    }

    override fun setOptions() {
        //准备好就开始播放
        internalPlayer?.playWhenReady = true
    }

    override var speed: Float
        get() = speedPlaybackParameters?.speed ?: 1f
        set(value) {
            speedPlaybackParameters = PlaybackParameters(value)
            internalPlayer?.setPlaybackParameters(speedPlaybackParameters)

        }
    override val tcpSpeed: Long
        get() = 0

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (mPlayerEventListener == null) {
            return
        }
        if (isPreparing) {
            return
        }
        if (lastReportedPlayerWhenReady != playWhenReady || lastReportedPlaybackState != playbackState) {
            when (playbackState) {
                Player.STATE_BUFFERING -> {
                    mPlayerEventListener?.onInfo(MEDIA_INFO_BUFFERING_START, bufferedPercentage)
                    isBuffering = true
                }
                Player.STATE_READY -> {
                    mPlayerEventListener?.onInfo(MEDIA_INFO_BUFFERING_END, bufferedPercentage)
                    isBuffering = false
                }
                Player.STATE_ENDED -> {
                    mPlayerEventListener?.onCompletion()
                }
            }
            lastReportedPlayerWhenReady = playWhenReady
            lastReportedPlaybackState = playbackState
        }
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        mPlayerEventListener?.onError()
    }

    override fun onVideoSizeChanged(
        width: Int,
        height: Int,
        unappliedRotationDegrees: Int,
        pixelWidthHeightRatio: Float
    ) {
        mPlayerEventListener?.onVideoSizeChanged(width, height)
        if (unappliedRotationDegrees > 0) {
            mPlayerEventListener?.onInfo(
                MEDIA_INFO_VIDEO_ROTATION_CHANGED,
                unappliedRotationDegrees
            )
        }
    }

    override fun onRenderedFirstFrame() {
        if (isPreparing) {
            mPlayerEventListener?.onInfo(MEDIA_INFO_VIDEO_RENDERING_START, 0)
            isPreparing = false
        }
    }
}