package com.dueeeke.videoplayer.controller

import android.content.Context
import android.media.AudioManager
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.util.PlayerUtils
import kotlin.math.abs


/**
 * 包含手势操作的VideoController
 */
abstract class GestureVideoController @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseVideoController(context, attrs, defStyleAttr),
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener,
    View.OnTouchListener {


    private val mAudioManager by lazy { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    private val mGestureDetector by lazy { GestureDetector(context, this) }

    private var mCanChangePosition = true
    private var mEnableInNormal = false
    private var mIsGestureEnabled = true
    private var mCanSlide = true
    private var mCurPlayState = VideoView.STATE_IDLE
    private var mStreamVolume = 0
    private var mBrightness = 0f
    private var mFirstTouch = true
    private var mChangePosition = false
    private var mChangeBrightness = false
    private var mChangeVolume = false
    private var mSeekPosition = 0L

    override fun initView() {
        super.initView()
        setOnTouchListener(this)

    }

    /**
     * 设置是否可以滑动调节进度，默认可以
     */
    fun setCanChangePosition(canChangePosition: Boolean) {
        mCanChangePosition = canChangePosition
    }

    /**
     * 是否在竖屏模式下开始手势控制，默认关闭
     */
    fun setEnableInNormal(enableInNormal: Boolean) {
        mEnableInNormal = enableInNormal
    }

    /**
     * 是否开启手势空控制，默认开启，关闭之后，双击播放暂停以及手势调节进度，音量，亮度功能将关闭
     */
    fun setGestureEnabled(gestureEnabled: Boolean) {
        mIsGestureEnabled = gestureEnabled
    }

    override fun setPlayerState(playerState: Int) {
        super.setPlayerState(playerState)
        when (playerState) {
            VideoView.PLAYER_NORMAL -> {
                mCanSlide = mEnableInNormal
            }
            VideoView.PLAYER_FULL_SCREEN -> {
                mCanSlide = true
            }
        }
    }

    override fun setPlayState(playState: Int) {
        super.setPlayState(playState)
        mCurPlayState = playState
    }

    private fun isInPlayBackState(): Boolean {
        return mControlWrapper != null
                && mCurPlayState != VideoView.STATE_ERROR
                && mCurPlayState != VideoView.STATE_IDLE
                && mCurPlayState != VideoView.STATE_PREPARING
                && mCurPlayState != VideoView.STATE_PREPARED
                && mCurPlayState != VideoView.STATE_START_ABORT
                && mCurPlayState != VideoView.STATE_PLAYBACK_COMPLETED
    }


    /**
     * 手指按下的瞬间
     */
    override fun onDown(e: MotionEvent?): Boolean {
        if (isInPlayBackState() == false //不在播放状态
            || mIsGestureEnabled == false //手势不可用
            || PlayerUtils.isEdge(context, e) //处于屏幕边缘
        ) {
            return true
        }

        mStreamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        if (mActivity == null) {
            mBrightness = 0f
        } else {
            mBrightness = mActivity?.window?.attributes?.screenBrightness ?: 0f
        }
        mFirstTouch = true
        mChangePosition = false
        mChangeBrightness = false
        mChangeVolume = false
        return true
    }


    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    /**
     * 在屏幕上滑动
     */
    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if (isInPlayBackState() == false
            || mIsGestureEnabled == false
            || mCanSlide == false
            || isLocked
            || PlayerUtils.isEdge(context, e1)
        ) {
            return true
        }
        val deltaX = (e1?.x ?: 0f) - (e2?.x ?: 0f)
        val deltaY = (e1?.y ?: 0f) - (e2?.y ?: 0f)
        if (mFirstTouch) {
            mChangePosition = abs(distanceX) >= abs(distanceY)
            // 上下滑动
            if (mChangePosition == false) {
                //半屏幕宽度
                val halfScreen = PlayerUtils.getScreenWidth(context, true) / 2
                if (e2?.x ?: 0f > halfScreen) {
                    mChangeVolume = true
                } else {
                    mChangeBrightness = true
                }
            }

            if (mChangePosition) {
                //根据用户设置是否可以滑动调节进度来决定最终是否可以滑动调节进度
                mChangePosition = mCanChangePosition
            }
            if (mCanChangePosition || mChangeBrightness || mChangeVolume) {
                mControlComponents.keys.forEach {
                    (it as? IGestureComponent)?.onStartSlide()
                }
            }

            mFirstTouch = false
        }
        if (mChangePosition) {
            slideToChangePosition(deltaX)
        } else if (mChangeBrightness) {
            slideToChangeBrightness(deltaY)
        } else if (mChangeVolume) {
            slideToChangeVolume(deltaY)
        }
        return true
    }

    private fun slideToChangePosition(deltaX: Float) {
        val newDeltaX = -deltaX
        val duration = mControlWrapper?.duration ?: 0L
        val currentPosition = mControlWrapper?.currentPosition?.toInt() ?: 0
        var position = (newDeltaX / measuredWidth * 120000 + currentPosition).toLong() // 120000?
        if (position > duration) {
            position = duration
        }
        if (position < 0) {
            position = 0
        }
        mControlComponents.keys.forEach {
            (it as? IGestureComponent)?.onPositionChange(
                position.toInt(),
                currentPosition,
                duration.toInt()
            )
        }
        mSeekPosition = position
    }


    private fun slideToChangeBrightness(deltaY: Float) {
        mActivity?.let { activity ->
            val attributes = activity.window.attributes
            if (mBrightness == -1.0f) {
                mBrightness = 0.5f
            }
            var brightness = deltaY * 2 / measuredHeight + mBrightness
            if (brightness < 0) {
                brightness = 0f
            }
            if (brightness > 1.0f) {
                brightness = 1.0f
            }
            val percent = (brightness * 100).toInt()
            attributes.screenBrightness = brightness
            activity.window.attributes = attributes
            mControlComponents.keys.forEach {
                (it as? IGestureComponent)?.onBrightnessChange(percent)
            }
        }
    }

    private fun slideToChangeVolume(deltaY: Float) {
        val streamMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val deltaV = deltaY * 2 / measuredHeight * streamMaxVolume
        var index = mStreamVolume + deltaV
        if (index > streamMaxVolume) {
            index = streamMaxVolume.toFloat()
        }
        if (index < 0) {
            index = 0f
        }
        val percent = (index / streamMaxVolume * 100).toInt()
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index.toInt(), 0)
        mControlComponents.keys.forEach {
            (it as? IGestureComponent)?.onVolumeChange(percent)
        }
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    /**
     * 单击
     */
    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        if (isInPlayBackState()) {
            mControlWrapper?.toggleShowState()
        }
        return true
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        if (isLocked == false && isInPlayBackState()) {
            togglePlay()
        }
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return mGestureDetector.onTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //滑动结束时事件处理
        if (mGestureDetector.onTouchEvent(event) == false) {
            val action = event?.action
            when (action) {
                MotionEvent.ACTION_UP -> {
                    stopSlide()
                    if (mSeekPosition > 0) {
                        mControlWrapper?.seekTo(mSeekPosition)
                        mSeekPosition = 0
                    }
                }
                MotionEvent.ACTION_CANCEL -> {
                    stopSlide()
                    mSeekPosition = 0
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun stopSlide() {
        mControlComponents.keys.forEach {
            (it as? IGestureComponent)?.onStopSlide()
        }
    }
}