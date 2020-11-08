package com.dueeeke.videoplayer.player

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.dueeeke.videoplayer.R
import com.dueeeke.videoplayer.controller.BaseVideoController
import com.dueeeke.videoplayer.controller.MediaPlayerControl
import com.dueeeke.videoplayer.render.IRenderView
import com.dueeeke.videoplayer.render.RenderViewFactory
import com.dueeeke.videoplayer.util.L.d
import com.dueeeke.videoplayer.util.PlayerUtils
import java.io.IOException


/**
 * 播放器
 */
class VideoView<P : AbstractPlayer> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), MediaPlayerControl,
    AbstractPlayer.PlayerEventListener {

    companion object {

        const val SCREEN_SCALE_DEFAULT = 0
        const val SCREEN_SCALE_16_9 = 1
        const val SCREEN_SCALE_4_3 = 2
        const val SCREEN_SCALE_MATCH_PARENT = 3
        const val SCREEN_SCALE_ORIGINAL = 4
        const val SCREEN_SCALE_CENTER_CROP = 5

        //播放器播放的各种状态
        const val STATE_ERROR = -1
        const val STATE_IDLE = 0
        const val STATE_PREPARING = 1
        const val STATE_PREPARED = 2
        const val STATE_PLAYING = 3
        const val STATE_PAUSED = 4
        const val STATE_PLAYBACK_COMPLETED = 5
        const val STATE_BUFFERING = 6
        const val STATE_BUFFERED = 7
        const val STATE_START_ABORT = 8 //开始播放中止

        const val PLAYER_NORMAL = 10 // 普通播放器
        const val PLAYER_FULL_SCREEN = 11 // 全屏播放器
        const val PLAYER_TINY_SCREEN = 12 // 小屏播放器

    }

    /**
     * 播放状态改变监听器
     */
    interface OnStateChangeListener {
        fun onPlayerStateChanged(playerState: Int)
        fun onPlayStateChanged(playState: Int)
    }

    /**
     * OnStateChangeListener的空实现。用的时候只需要重写需要的方法
     */
    class SimpleOnStateChangeListener : VideoView.OnStateChangeListener {
        override fun onPlayerStateChanged(playerState: Int) {}
        override fun onPlayStateChanged(playState: Int) {}
    }

    protected var mediaPlayer: AbstractPlayer? = null
    protected var playerFactory: PlayerFactory<out AbstractPlayer>? = null
    var videoController: BaseVideoController? = null
        set(value) {
            playerContainer.removeView(value)
            field = value
            if (value != null) {
                value.setMediaPlayer(this)
                val lp = LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                playerContainer.addView(field, lp)
            }
        }

    /**
     * 真正承载播放器视图的容器
     */
    protected lateinit var playerContainer: FrameLayout
    protected var renderView: IRenderView? = null
    protected var renderViewFactory: RenderViewFactory? = null
    protected var currentScreenScaleType = SCREEN_SCALE_DEFAULT

    /**
     * 重新播放
     *
     * @param resetPosition 是否从头开始播放
     */
    override fun replay(resetPosition: Boolean) {
        if (resetPosition) {
            progressManager?.saveProgress(url, 0)
        }
        addDisplay()
        startPrepare(true)
        playerContainer.keepScreenOn = true
    }

    /**
     * datasource
     */
    protected var url: String = ""
    protected var headers: Map<String?, String?>? = null
    protected var assetFileDescriptor: AssetFileDescriptor? = null
        set(value) {
            field = value
            url = ""
        }

    protected var currentPlayerState = PLAYER_NORMAL

    protected var currentPlayState = STATE_IDLE

    protected var tinyScreenSize = intArrayOf(0, 0)

    /**
     * 监听系统中音频焦点的变化
     */
    protected var enableAudioFocus = true

    //todo replace
    protected var audioFocusHelper: AudioFocusHelper? = null

    /**
     * OnStateChangeListener集合，保存了所有开发者设置的监听器
     */
    protected val onStateChangeListeners = arrayListOf<OnStateChangeListener>()

    /**
     * 进度管理器，设置之后播放器会记录播放进度，以便下次播放恢复进度
     */
    protected var progressManager: ProgressManager? = null

    /**
     * 循环播放
     */
    protected var isLooping = false
        set(value) {
            field = value
            mediaPlayer?.setLooping(value)
        }


    /**
     * {@link #mPlayerContainer}背景色，默认黑色
     */
    protected var playerBackgroundColor: Int = 0
        set(value) {
            field = value
            if (::playerContainer.isInitialized) {
                playerContainer.setBackgroundColor(value)
            }
        }

    init {
        val config = VideoViewManager.config
        enableAudioFocus = config.enableAudioFocus
        progressManager = config.progressManager
        playerFactory = config.playerFactory
        currentScreenScaleType = config.screenScaleType
        renderViewFactory = config.renderViewFactory


        //读取xml中的配置，并综合全局配置

        //读取xml中的配置，并综合全局配置
        val a = context.obtainStyledAttributes(attrs, R.styleable.VideoView)
        enableAudioFocus = a.getBoolean(R.styleable.VideoView_enableAudioFocus, enableAudioFocus)
        isLooping = a.getBoolean(R.styleable.VideoView_looping, false)
        currentScreenScaleType =
            a.getInt(R.styleable.VideoView_screenScaleType, currentScreenScaleType)
        playerBackgroundColor = a.getColor(R.styleable.VideoView_playerBackgroundColor, Color.BLACK)
        a.recycle()

        initView()
    }

    private fun initView() {
        playerContainer = FrameLayout(context)
        playerContainer.setBackgroundColor(playerBackgroundColor)
        val lp = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        addView(playerContainer, lp)
    }

    /**
     * 开始播放，注意：调用此方法后必须调用{@link #release()}释放播放器，否则会导致内存泄漏
     */
    override fun start() {
        var isStarted = false
        if (isInIdleState() || isInStartAbortState()) {
            isStarted = startPlay()
        } else if (isInPlaybackState()) {
            startInPlaybackState()
            isStarted = true
        }
        if (isStarted) {
            playerContainer.keepScreenOn = true
            audioFocusHelper?.requestFocus()
        }
    }

    /**
     * 是否处于播放状态
     */
    protected fun isInPlaybackState(): Boolean {
        return mediaPlayer != null
                && currentPlayState != VideoView.STATE_ERROR
                && currentPlayState != VideoView.STATE_IDLE
                && currentPlayState != VideoView.STATE_PREPARING
                && currentPlayState != VideoView.STATE_START_ABORT
                && currentPlayState != VideoView.STATE_PLAYBACK_COMPLETED
    }

    /**
     * 是否处于未播放状态
     */
    private fun isInIdleState(): Boolean {
        return currentPlayState == STATE_IDLE
    }


    /**
     * 播放中止状态
     */
    private fun isInStartAbortState(): Boolean {
        return currentPlayState == STATE_START_ABORT
    }

    /**
     * 第一次播放
     */
    protected fun startPlay(): Boolean {
        // 如果要显示移动网络提示则不继续
        if (showNetWarning()) {
            setPlayState(STATE_START_ABORT)
            return false
        }
        //监听音频焦点改变
        if (enableAudioFocus) {
            audioFocusHelper = AudioFocusHelper(this)
        }
        initPlayer()
        addDisplay()
        startPrepare(false)
        return true
    }

    /**
     * 是否显示移动网络提示，可在Controller中配置
     */
    protected fun showNetWarning(): Boolean {
        if (isLocalDataSource()) {
            return false
        }
        return videoController?.showNetWarning() ?: false
    }

    protected fun isLocalDataSource(): Boolean {
        if (assetFileDescriptor != null) {
            return false
        } else if (url.isNotBlank()) {
            val uri = Uri.parse(url)
            return ContentResolver.SCHEME_ANDROID_RESOURCE.equals(uri.scheme)
                    || ContentResolver.SCHEME_FILE.equals(uri.scheme)
                    || "rawresource".equals(uri.scheme)
        }
        return false
    }


    protected fun initPlayer() {
        mediaPlayer = playerFactory?.createPlayer(context)
        mediaPlayer?.setPlayerEventListener(this)
        setInitOptions()
        mediaPlayer?.initPlayer()
        setOptions()
    }

    /**
     * 初始化之前的配置
     */
    protected fun setInitOptions() {

    }

    /**
     * 初始化之后的配置
     */
    protected fun setOptions() {
        mediaPlayer?.setLooping(isLooping)
    }

    /**
     * 初始化视频渲染View
     */
    protected fun addDisplay() {
        if (renderView != null) {
            playerContainer.removeView(renderView?.view)
            renderView?.release()
        }
        renderView = renderViewFactory?.createRenderView(context)
        mediaPlayer?.let {
            renderView?.attachToPlayer(it)
        }
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            Gravity.CENTER
        )
        playerContainer.addView(renderView?.view, 0, params)
    }

    protected fun startPrepare(reset: Boolean) {
        if (reset) {
            mediaPlayer?.reset()
            //重新设置 option，reset之后失效
            setOptions()
        }
        if (prepareDataSource()) {
            mediaPlayer?.prepareAsync()
            setPlayState(STATE_PREPARING)
            val playerState = when {
                isFullScreen -> PLAYER_FULL_SCREEN
                isTinyScreen -> PLAYER_TINY_SCREEN
                else -> PLAYER_NORMAL
            }
            setPlayerState(playerState)
        }
    }

    protected fun prepareDataSource(): Boolean {
        if (assetFileDescriptor != null) {
            mediaPlayer?.setDataSource(assetFileDescriptor)
            return true
        } else if (this.url.isNotBlank()) {
            mediaPlayer?.setDataSource(url, headers)
            return true
        }
        return false
    }

    /**
     * 在播放状态下开始播放
     */
    protected fun startInPlaybackState() {
        mediaPlayer?.start()
        setPlayState(STATE_PLAYING)
    }

    /**
     * 向 Controller 设置播放状态，用于控制 Controller 的 UI 展示
     */
    protected fun setPlayState(playState: Int) {
        currentPlayState = playState
        videoController?.setPlayState(playState)
        onStateChangeListeners.let {
            PlayerUtils.getSnapshot(it).forEach {
                it?.onPlayStateChanged(playState)
            }
        }
    }

    protected fun setPlayerState(playerState: Int) {
        currentPlayerState = playerState
        videoController?.setPlayerState(playerState)
        onStateChangeListeners.let {
            PlayerUtils.getSnapshot(it).forEach {
                it?.onPlayerStateChanged(playerState)
            }
        }
    }


    /**
     * 暂停播放
     */
    override fun pause() {
        if (isInPlaybackState() && mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            setPlayState(STATE_PAUSED)
            audioFocusHelper?.abandonFocus()
            playerContainer.keepScreenOn = false
        }
    }

    override val duration: Long
        get() {
            if (isInPlaybackState()) {
                return mediaPlayer?.duration ?: 0
            }
            return 0
        }
    override var currentPosition: Long = 0
        get() {
            if (isInPlaybackState()) {
                return mediaPlayer?.currentPosition ?: 0
            }
            return 0
        }

    /**
     * 继续播放
     */
    fun resume() {
        if (isInPlaybackState() && mediaPlayer != null && mediaPlayer?.isPlaying != true) {
            setPlayState(STATE_PLAYING)
            audioFocusHelper?.requestFocus()
            playerContainer.keepScreenOn = true
        }
    }

    /**
     * 释放播放器
     */
    fun release() {
        saveProgress()
        if (isInIdleState() == false) {
            //释放播放器
            mediaPlayer?.release()
            mediaPlayer = null

            //释放RenderView
            playerContainer.removeView(renderView?.view)
            renderView?.release()
            renderView = null

            //释放Assets资源
            if (assetFileDescriptor != null) {
                try {
                    assetFileDescriptor?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //关闭AudioFocus监听
            audioFocusHelper?.abandonFocus()
            audioFocusHelper = null

            playerContainer.keepScreenOn = false
            setPlayState(STATE_IDLE)
        }
    }

    /**
     * 保存播放进度
     */
    protected fun saveProgress() {
        if (currentPosition > 0) {
            progressManager?.saveProgress(url, currentPosition)
        }
    }

    override fun onError() {
        playerContainer.keepScreenOn = false
        setPlayState(STATE_ERROR)
    }

    override fun onCompletion() {
        playerContainer.keepScreenOn = false
        currentPosition = 0L
        progressManager?.saveProgress(url, 0)
        setPlayState(STATE_PLAYBACK_COMPLETED)
    }

    override fun onInfo(what: Int, extra: Int) {
        when (what) {
            AbstractPlayer.MEDIA_INFO_BUFFERING_START -> {
                setPlayState(STATE_BUFFERING)
            }
            AbstractPlayer.MEDIA_INFO_BUFFERING_END -> {
                setPlayState(STATE_BUFFERED)
            }
            // 视频开始渲染
            AbstractPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                setPlayState(STATE_PLAYING)
                if (playerContainer.windowVisibility != View.VISIBLE) {
                    pause()
                }
            }
            AbstractPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED -> {
                renderView?.setVideoRotation(extra)
            }
        }
    }


    /**
     * 视频缓冲完毕，准备开始播放时回调
     */
    override fun onPrepared() {
        setPlayState(STATE_PREPARED)
        val lastPosition = progressManager?.getSavedProgress(url) ?: 0
        if (lastPosition > 0) {
            seekTo(lastPosition)
        }
    }

    override fun onVideoSizeChanged(width: Int, height: Int) {
        videoSize[0] = width
        videoSize[1] = height

        renderView?.let {
            it.setScaleType(currentScreenScaleType)
            it.setVideoSize(width, height)
        }
    }

    override fun seekTo(pos: Long) {
        if (isInPlaybackState()) {
            mediaPlayer?.seekTo(pos)
        }
    }

    override val isPlaying: Boolean
        get() = isInPlaybackState() && mediaPlayer?.isPlaying ?: false
    override val bufferedPercentage: Int
        get() = mediaPlayer?.bufferedPercentage ?: 0


    /**
     * 设置包含请求头信息的视频地址
     *
     * @param url     视频地址
     * @param headers 请求头
     */
    fun setUrl(url: String, headers: Map<String?, String?>? = null) {
        assetFileDescriptor = null
        this.url = url
        this.headers = headers
    }

    /**
     * 一开始播放就seek到预先设置好的位置
     */
    fun skipPositionWhenPlay(position: Int) {
        progressManager?.saveProgress(url, position.toLong())
    }


    /**
     * 设置音量 0.0f-1.0f 之间
     *
     * @param v1 左声道音量
     * @param v2 右声道音量
     */
    fun setVolume(v1: Float, v2: Float) {
        mediaPlayer?.setVolume(v1, v2)
    }

    /**
     * 进入全屏
     */
    override fun startFullScreen() {
        if (isFullScreen) {
            return
        }
        val decorView = getDecorView()
        if (decorView == null) {
            return
        }
        isFullScreen = true

        //隐藏 NavigationBar 和 StatusBar
        hideSysBar(decorView)

        //从当前 FrameLayout 中移除播放器视图
        removeView(playerContainer)
        decorView.addView(playerContainer)
        setPlayerState(PLAYER_FULL_SCREEN)
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus && isFullScreen) {
            //重新获得焦点时保持全屏状态
            hideSysBar(getDecorView())
        }
    }


    /**
     * 获取DecorView
     */
    protected fun getDecorView(): ViewGroup? {
        val activity: Activity = getActivity() ?: return null
        return activity.window.decorView as ViewGroup
    }

    /**
     * 获取Activity，优先通过Controller去获取Activity
     */
    protected fun getActivity(): Activity? {
        var activity: Activity?
        if (videoController != null) {
            activity = PlayerUtils.scanForActivity(videoController?.getContext())
            if (activity == null) {
                activity = PlayerUtils.scanForActivity(context)
            }
        } else {
            activity = PlayerUtils.scanForActivity(context)
        }
        return activity
    }


    private fun hideSysBar(decorView: ViewGroup?) {
        var uiOptions = decorView?.systemUiVisibility ?: 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            uiOptions = uiOptions or SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uiOptions = uiOptions or SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
        decorView?.systemUiVisibility = uiOptions
        getActivity()!!.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun showSysBar(decorView: ViewGroup?) {
        var uiOptions = decorView?.systemUiVisibility ?: 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            uiOptions = uiOptions and SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uiOptions = uiOptions and SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
        }
        decorView?.systemUiVisibility = uiOptions
        getActivity()!!.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun stopFullScreen() {
        if (isFullScreen == false) return

        val decorView = getDecorView() ?: return

        isFullScreen = false

        //显示NavigationBar和StatusBar
        showSysBar(decorView)

        //把播放器视图从DecorView中移除并添加到当前FrameLayout中即退出了全屏
        decorView.removeView(playerContainer)
        this.addView(playerContainer)

        setPlayerState(VideoView.PLAYER_NORMAL)
    }

    /**
     * 获取activity中的content view,其id为android.R.id.content
     */
    protected fun getContentView(): ViewGroup? {
        val activity = getActivity() ?: return null
        return activity.findViewById(android.R.id.content)
    }


    override var isFullScreen: Boolean = false
    override var isMute: Boolean = false
        set(value) {
            field = value
            mediaPlayer?.let {
                val volume: Float = if (value) {
                    0f
                } else {
                    1f
                }
                mediaPlayer?.setVolume(volume, volume)
            }
        }

    override fun startTinyScreen() {
        if (isTinyScreen) return
        val contentView = getContentView() ?: return
        removeView(playerContainer)
        var width: Int = tinyScreenSize[0]
        if (width <= 0) {
            width = PlayerUtils.getScreenWidth(context, false) / 2
        }

        var height: Int = tinyScreenSize.get(1)
        if (height <= 0) {
            height = width * 9 / 16
        }

        val params = LayoutParams(width, height)
        params.gravity = Gravity.BOTTOM or Gravity.END
        contentView.addView(playerContainer, params)
        isTinyScreen = true
        setPlayerState(VideoView.PLAYER_TINY_SCREEN)
    }

    override fun stopTinyScreen() {
        if (isTinyScreen == false) return

        val contentView = getContentView() ?: return
        contentView.removeView(playerContainer)
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        this.addView(playerContainer, params)

        isTinyScreen = false
        setPlayerState(VideoView.PLAYER_NORMAL)
    }

    override var isTinyScreen: Boolean = false

    override fun setScreenScaleType(screenScaleType: Int) {
        currentPlayerState = screenScaleType
        renderView?.setScaleType(screenScaleType)
    }

    override var speed: Float
        get() {
            if (isInPlaybackState()) {
                return mediaPlayer?.speed ?: 1f
            }
            return 1f
        }
        set(value) {
            if (isInPlaybackState()) {
                mediaPlayer?.speed = value
            }
        }
    override val tcpSpeed: Long
        get() = mediaPlayer?.tcpSpeed ?: 0

    /**
     * 设置镜像旋转，暂不支持SurfaceView
     */
    override fun setMirrorRotation(enable: Boolean) {
        renderView?.view?.scaleX = if (enable) -1f else 1f
    }

    override fun doScreenShot(): Bitmap? {
        return renderView?.doScreenShot()
    }

    override var videoSize: IntArray = intArrayOf(0, 0)


    /**
     * 旋转视频画面
     *
     * @param rotation 角度
     */
    override fun setRotation(rotation: Float) {
        super.setRotation(rotation)
        renderView?.setVideoRotation(rotation.toInt())
    }

    /**
     * 添加一个播放状态监听器，播放状态发生变化时将会调用。
     */
    fun addOnStateChangeListener(listener: VideoView.OnStateChangeListener) {
        onStateChangeListeners.add(listener)
    }

    /**
     * 移除某个播放状态监听
     */
    fun removeOnStateChangeListener(listener: VideoView.OnStateChangeListener) {
        onStateChangeListeners.remove(listener)
    }

    /**
     * 设置一个播放状态监听器，播放状态发生变化时将会调用，
     * 如果你想同时设置多个监听器，推荐 [.addOnStateChangeListener]。
     */
    fun setOnStateChangeListener(listener: VideoView.OnStateChangeListener) {
        onStateChangeListeners.clear()
        onStateChangeListeners.add(listener)
    }

    fun clearOnStateChangeListener() {
        onStateChangeListeners.clear()
    }

    fun onBackPressed(): Boolean {
        return videoController?.onBackPressed() == true
    }

    override fun onSaveInstanceState(): Parcelable? {
        d("onSaveInstanceState: $currentPosition")
        //activity切到后台后可能被系统回收，故在此处进行进度保存
        saveProgress()
        return super.onSaveInstanceState()
    }
}