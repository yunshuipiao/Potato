package com.dueeeke.videoplayer.controller

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.player.VideoViewManager
import com.dueeeke.videoplayer.util.CutoutUtil
import com.dueeeke.videoplayer.util.L
import com.dueeeke.videoplayer.util.PlayerUtils


/**
 * 控制器基类，view，继承各种事件的处理逻辑，包括
 * 控制器基类
 * 此类集成各种事件的处理逻辑，包括
 * 1.播放器状态改变: {@link #handlePlayerStateChanged(int)}
 * 2.播放状态改变: {@link #handlePlayStateChanged(int)}
 * 3.控制视图的显示和隐藏: {@link #handleVisibilityChanged(boolean, Animation)}
 * 4.播放进度改变: {@link #handleSetProgress(int, int)}
 * 5.锁定状态改变: {@link #handleLockStateChanged(boolean)}
 * 6.设备方向监听: {@link #onOrientationChanged(int)}
 * Created by dueeeke on 2017/4/12.
 */
abstract class BaseVideoController @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr),
    IVideoController,
    OrientationHelper.OnOrientationChangeListener {


    /**
     * 是否有刘海
     */
    private var mHasCutout: Boolean = false
    private var mCutoutHeight: Int = 0

    /**
     * 是否开始刷新进度
     */
    private var mIsStartProgress: Boolean = false

    /**
     * 播放视图隐藏超时
     */
    private var mDefaultTimeout: Long = 4000

    /**
     * 是否处于锁定状态
     */
    private var mIsLocked: Boolean = false

    /**
     * 是否处于显示状态
     */
    private var mShowing: Boolean = false

    /**
     * 旋转角度
     */
    private var mOrientation: Int = 0

    @JvmField
    /**
     * 播放器包装类，集合了 MediaPlayerControl 和 api 和 VideoControl 的 api
     */
    protected var mControlWrapper: ControlWrapper? = null

    /**
     * 屏幕方向监听辅助类
     */
    protected val mOrientationHelper by lazy { OrientationHelper(context) }

    /**
     * 是否根据屏幕方向进入/退出全屏
     */
    private var mEnableOrientation = VideoViewManager.config.enableOrientation

    /**
     * 是否适配刘海
     */
    private var mAdaptCutout = VideoViewManager.config.adaptCutout

    private var mShowAnim = AlphaAnimation(0f, 1f).apply { duration = 300 }
    private var mHideAnim = AlphaAnimation(1f, 0f).apply { duration = 300 }


    @JvmField
    protected val mControlComponents = LinkedHashMap<IControlComponent, Boolean>()

    @JvmField
    protected var mActivity: Activity? = null


    init {
        initView()
    }

    protected open fun initView() {
        if (getLayoutId() != 0) {
            LayoutInflater.from(context).inflate(getLayoutId(), this, true)
        }
        mActivity = PlayerUtils.scanForActivity(context)
    }

    /**
     * 设置控制器布局文件，子类必须实现
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 重要：此方法用于将{@link VideoView}和控制器绑定
     */
    fun setMediaPlayer(mediaPlayerControl: MediaPlayerControl) {
        mControlWrapper = ControlWrapper(mediaPlayerControl, this)
        mControlComponents.keys.forEach {
            mControlWrapper?.let { wrapper ->
                it.attach(wrapper)
            }
        }
        mOrientationHelper.onOrientationChangeListener = this
        mOrientationHelper.enable()
    }

    /**
     * 添加控制组件，最后面添加的在最下面，合理组合顺序，可让ControlComponent位于不同的层级
     */
    fun addControlComponent(vararg component: IControlComponent) {
        component.forEach {
            addControlComponent(it, false)
        }
    }


    /**
     * 添加控制组件，最后面添加的在最下面，合理组织添加顺序，可让ControlComponent位于不同的层级
     *
     * @param isPrivate 是否为独有的组件，如果是就不添加到该控制器view的层级中，涉及隐藏/显示
     */
    fun addControlComponent(component: IControlComponent, isPrivate: Boolean) {
        mControlComponents.put(component, isPrivate)
        mControlWrapper?.let { component.attach(it) }
        if (component.view != null && isPrivate == false) {
            addView(component.view, 0)
        }
    }

    /**
     * 移除控制组件
     */
    fun removeControlComponent(component: IControlComponent) {
        removeView(component.view)
        mControlComponents.remove(component)
    }

    fun removeAllControlComponent() {
        mControlComponents.keys.forEach {
            removeView(it.view)
        }
        mControlComponents.clear()
    }

    fun removeAllPrivateComponents() {
        val allPrivate = mControlComponents.filter { it.value }
        allPrivate.keys.forEach {
            mControlComponents.remove(it)
        }
    }

    /**
     * [VideoView]调用此方法向控制器设置播放状态
     */
    @CallSuper
    open fun setPlayState(playState: Int) {
        handlePlayStateChanged(playState)
    }

    private fun handlePlayStateChanged(playState: Int) {
        mControlComponents.keys.forEach {
            it.onPlayStateChanged(playState)
        }
    }

    /**
     * 子类重写该方法并在其中更新控制 view 在不同播放状态下的 UI
     */
    @CallSuper
    protected open fun onPlayStateChanged(playState: Int) {
        when (playState) {
            VideoView.STATE_IDLE -> {
                mOrientationHelper.disable()
                mOrientation = 0
                mIsLocked = false
                mShowing = false
                removeAllPrivateComponents()
            }
            VideoView.STATE_PLAYBACK_COMPLETED -> {
                mIsLocked = false
                mShowing = false
            }
            VideoView.STATE_ERROR -> {
                mShowing = false
            }
        }
    }

    /**
     * 调用此方法向控制 view 设置播放器状态
     */
    open fun setPlayerState(playerState: Int) {
        handlePlayerStateChanged(playerState)
    }

    /**
     * 子类重写此方法并在其中更新控制 view 在不同播放器控制下的 UI
     */
    fun handlePlayerStateChanged(playerState: Int) {
        mControlComponents.keys.forEach {
            it.onPlayerStateChanged(playerState)
        }
        onPlayerStateChanged(playerState)
    }

    protected open fun onPlayerStateChanged(playerState: Int) {
        when (playerState) {
            VideoView.PLAYER_NORMAL -> {
                if (mEnableOrientation) {
                    mOrientationHelper.enable()
                } else {
                    mOrientationHelper.disable()
                }
                if (hasCutout()) {
                    CutoutUtil.adaptCutoutAboveAndroidP(context, false)
                }
            }
            VideoView.PLAYER_FULL_SCREEN -> {
                //全屏时强制监听设备方向
                mOrientationHelper.enable()
                if (hasCutout()) {
                    CutoutUtil.adaptCutoutAboveAndroidP(context, true)
                }
            }
            VideoView.PLAYER_TINY_SCREEN -> {
                mOrientationHelper.disable()
            }
        }
    }

    /**
     * 设置播放视图自动隐藏超时
     */
    fun setDismissTimeOut(timeout: Long) {
        if (timeout > 0L) {
            mDefaultTimeout = timeout
        }
    }


    override fun startFadeOut() {
        stopFadeOut()
        postDelayed(mFadeOut, mDefaultTimeout)
    }

    /**
     * 取消计时
     */
    override fun stopFadeOut() {
        removeCallbacks(mFadeOut)
    }

    protected var mFadeOut = Runnable {
        hide()
    }

    override val isShowing: Boolean
        get() = mShowing
    override var isLocked: Boolean
        get() = mIsLocked
        set(value) {
            mIsLocked = value
            handleLockStateChanged(value)
        }

    private fun handleLockStateChanged(isLocked: Boolean) {
        mControlComponents.keys.forEach {
            it.onLockStateChanged(isLocked)
        }
        onLockStateChanged(isLocked)
    }

    /**
     * 子类重写此方法，监听锁定状态改变，更新UI
     */
    protected open fun onLockStateChanged(isLocked: Boolean) {

    }

    override fun startProgress() {
        if (mIsStartProgress) {
            return
        }
        post(mShowProgress)
    }

    /**
     * 刷新进度Runnable
     */
    protected var mShowProgress: Runnable = object : Runnable {
        override fun run() {
            val pos: Int = setProgress()
            if (mControlWrapper?.isPlaying == true) {
                postDelayed(this, ((1000 - pos % 1000) / mControlWrapper!!.speed).toLong())
            } else {
                mIsStartProgress = false
            }
        }
    }

    private fun setProgress(): Int {
        val position = mControlWrapper?.currentPosition?.toInt() ?: 0
        val duration = mControlWrapper?.duration?.toInt() ?: 0
        handleSetProgress(duration, position)
        return position
    }


    private fun handleSetProgress(duration: Int, position: Int) {
        for ((component) in mControlComponents) {
            component.setProgress(duration, position)
        }
        setProgress(duration, position)
    }

    /**
     * 刷新进度回调，子类可在此方法监听进度刷新，然后更新ui
     *
     * @param duration 视频总时长
     * @param position 视频当前时长
     */
    protected open fun setProgress(duration: Int, position: Int) {

    }

    override fun stopProgress() {
        if (mIsStartProgress == false) {
            return
        }
        removeCallbacks(mShowProgress)
        mIsStartProgress = false
    }

    override fun show() {
        if (mShowing == false) {
            handleVisibilityChanged(true, mShowAnim)
            startFadeOut()
            mShowing = true
        }
    }

    /**
     * 隐藏播放视图
     */
    override fun hide() {
        if (mShowing) {
            stopFadeOut()
            handleVisibilityChanged(false, mHideAnim)
            mShowing = false
        }
    }

    private fun handleVisibilityChanged(isVisible: Boolean, anim: Animation) {
        if (mIsLocked == false) {
            mControlComponents.keys.forEach {
                it.onVisibilityChanged(isVisible, anim)
            }
        }
        onVisibilityChanged(isVisible, anim)
    }

    /**
     * 子类重写此方法，监听控制view 的显示和隐藏
     */
    protected open fun onVisibilityChanged(isVisible: Boolean, anim: Animation) {

    }

    /**
     * 设置是否适配刘海屏
     */
    fun setAdaptCutout(adaptCutout: Boolean) {
        mAdaptCutout = adaptCutout
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        checkCutout()
    }

    /**
     * 检查是否需要适配刘海屏幕
     */
    private fun checkCutout() {
        if (mAdaptCutout == false) {
            return
        }
        if (mActivity != null) {
            mHasCutout = CutoutUtil.allowDisplayToCutout(mActivity)
            if (mHasCutout) {
                mCutoutHeight = PlayerUtils.getStatusBarHeightPortrait(mActivity).toInt()
            }
        }
        L.d("hasCutout: $mHasCutout cutout height: $mCutoutHeight")

    }


    override fun hasCutout(): Boolean {
        return mHasCutout
    }

    override val cutoutHeight: Int
        get() = mCutoutHeight

    /**
     * 显示移动网络播放提示
     *
     * @return 返回显示移动网络播放提示的条件，false:不显示, true显示
     * 此处默认根据手机网络类型来决定是否显示，开发者可以重写相关逻辑
     */
    open fun showNetWarning(): Boolean {
        return PlayerUtils.getNetworkType(context) == PlayerUtils.NETWORK_MOBILE
                && VideoViewManager.playOnMobileNetwork == false
    }


    protected fun togglePlay() {
        mControlWrapper?.togglePlay()
    }

    protected fun toggleFullScreen() {
        mControlWrapper?.toggleFullScreen()
    }


    /**
     * 子类中请使用此方法来进入全屏
     *
     * @return 是否成功进入全屏
     */
    protected open fun startFullScreen(): Boolean {
        if (mActivity == null || mActivity?.isFinishing == true) {
            return false
        }
        mActivity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        mControlWrapper?.startFullScreen()
        return true
    }

    /**
     * 子类中请使用此方法来退出全屏
     *
     * @return 是否成功退出全屏
     */
    protected open fun stopFullScreen(): Boolean {
        if (mActivity == null || mActivity?.isFinishing == true) {
            return false
        }
        mActivity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mControlWrapper?.stopFullScreen()
        return true
    }

    /**
     * 改变返回逻辑，同于 activity
     */
    open fun onBackPressed(): Boolean {
        return false
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (mControlWrapper?.isPlaying == true &&
            (mEnableOrientation || mControlWrapper?.isFullScreen == false)
        ) {
            if (hasWindowFocus) {
                postDelayed({
                    mOrientationHelper.enable()
                }, 800)
            } else {
                mOrientationHelper.disable()
            }
        }
    }


    /**
     * 是否自动旋转， 默认不自动旋转
     */
    fun setEnableOrientation(enableOrientation: Boolean) {
        mEnableOrientation = enableOrientation
    }

    override fun onOrientationChanged(orientation: Int) {
        mActivity?.let { activity ->
            if (activity.isFinishing) {
                return
            }
            //记录手机上一次放置的位置
            val lastOrientation = mOrientation
            if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                //手机平放时，检测不到有效的角度
                //重置为原始位置 -1
                mOrientation = -1
                return
            }
            if (orientation > 350 || orientation < 10) {
                val o = activity.requestedOrientation
                //手动切换为竖屏
                if (o == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE && lastOrientation == 0) {
                    return
                }
                if (mOrientation == 0) {
                    return
                }
                mOrientation = 0
                onOrientationPortrait(activity)
            } else if (orientation in 81..99) {
                val o = activity.requestedOrientation
                //手动切为竖屏
                if (o == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT && lastOrientation == 90) {
                    return
                }
                if (mOrientation == 90) {
                    return
                }
                //90度，用户右侧横屏拿着手机
                mOrientation = 90
                onOrientationReverseLandscape(activity)
            } else if (orientation in 261..279) {
                val o = mActivity!!.requestedOrientation
                //手动切换横竖屏
                if (o == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT && lastOrientation == 270) return
                if (mOrientation == 270) return
                //270度，用户左侧横屏拿着手机
                mOrientation = 270
                onOrientationLandscape(activity)
            }
        }
    }

    /**
     * 竖屏
     */
    protected open fun onOrientationPortrait(activity: Activity) {
        //屏幕锁定的情况
        if (mIsLocked) return
        //没有开启设备方向监听的情况
        if (!mEnableOrientation) return
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mControlWrapper?.stopFullScreen()
    }

    /**
     * 横屏
     */
    protected open fun onOrientationLandscape(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        if (mControlWrapper!!.isFullScreen) {
            handlePlayerStateChanged(VideoView.PLAYER_FULL_SCREEN)
        } else {
            mControlWrapper?.startFullScreen()
        }
    }

    /**
     * 反向横屏
     */
    protected open fun onOrientationReverseLandscape(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
        if (mControlWrapper?.isFullScreen == true) {
            handlePlayerStateChanged(VideoView.PLAYER_FULL_SCREEN)
        } else {
            mControlWrapper?.startFullScreen()
        }
    }
}