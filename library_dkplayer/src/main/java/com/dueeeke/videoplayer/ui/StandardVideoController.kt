package com.dueeeke.videoplayer.ui

import android.content.Context
import android.content.pm.ActivityInfo
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Toast
import com.dueeeke.videoplayer.R
import com.dueeeke.videoplayer.controller.GestureVideoController
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.ui.component.*
import com.dueeeke.videoplayer.util.PlayerUtils
import kotlinx.android.synthetic.main.dkplayer_layout_standard_controller.view.*

/**
 * author : zp
 * date : 2020/11/14
 * Potato
 */

class StandardVideoController @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GestureVideoController(context, attrs, defStyleAttr) {

    override fun getLayoutId(): Int {
        return R.layout.dkplayer_layout_standard_controller
    }

    override fun initView() {
        super.initView()
        lock.setOnClickListener {
            mControlWrapper?.toggleLockState()
        }
    }

    fun addDefaultControlComponent(title: String, isLive: Boolean) {
        val completeView = CompleteView(context)
        val errorView = ErrorView(context)
        val prepareView = PrepareView(context)
        prepareView.setClickStart()
        val titleView = TitleView(context)
        addControlComponent(completeView, errorView, prepareView, titleView)
        if (isLive) {
            addControlComponent(LiveControlView(context))
        } else {
            addControlComponent(VodControlView(context))
        }
        addControlComponent(GestureView(context))
        setCanChangePosition(isLive)
    }

    override fun onLockStateChanged(isLocked: Boolean) {
        if (isLocked) {
            lock.isSelected = true
            Toast.makeText(context, R.string.dkplayer_locked, Toast.LENGTH_SHORT).show()
        } else {
            lock.isSelected = false
            Toast.makeText(context, R.string.dkplayer_unlocked, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onVisibilityChanged(isVisible: Boolean, anim: Animation) {
        if (mControlWrapper!!.isFullScreen) {
            if (isVisible) {
                if (lock.visibility == View.GONE) {
                    lock.visibility = View.VISIBLE
                    lock.startAnimation(anim)
                }
            } else {
                lock.visibility = View.GONE
                lock.startAnimation(anim)
            }
        }
    }

    override fun onPlayerStateChanged(playerState: Int) {
        super.onPlayerStateChanged(playerState)
        when (playerState) {
            VideoView.PLAYER_NORMAL -> {
                layoutParams = LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                lock.visibility = View.GONE
            }
            VideoView.PLAYER_FULL_SCREEN -> if (isShowing) {
                lock.visibility = View.VISIBLE
            } else {
                lock.visibility = View.GONE
            }
        }
        if (mActivity != null && hasCutout()) {
            val orientation = mActivity!!.requestedOrientation
            val dp24 = PlayerUtils.dp2px(context, 24f)
            val cutoutHeight = cutoutHeight
            when (orientation) {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT -> {
                    (lock.layoutParams as LayoutParams).setMargins(dp24, 0, dp24, 0)
                }
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE -> {
                    (lock.layoutParams as LayoutParams).setMargins(
                        dp24 + cutoutHeight,
                        0,
                        dp24 + cutoutHeight,
                        0
                    )
                }
                ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE -> {
                    (lock.layoutParams as LayoutParams).setMargins(dp24, 0, dp24, 0)
                }
            }
        }
    }

    override fun onPlayStateChanged(playState: Int) {
        super.onPlayStateChanged(playState)
        super.onPlayStateChanged(playState)
        when (playState) {
            VideoView.STATE_IDLE -> {
                lock.isSelected = false
                loading.visibility = View.GONE
            }
            VideoView.STATE_PLAYING,
            VideoView.STATE_PAUSED,
            VideoView.STATE_PREPARED,
            VideoView.STATE_ERROR,
            VideoView.STATE_BUFFERED -> {
                loading.visibility = View.GONE
            }
            VideoView.STATE_PREPARING,
            VideoView.STATE_BUFFERING -> {
                loading.visibility = View.VISIBLE
            }
            VideoView.STATE_PLAYBACK_COMPLETED -> {
                loading.visibility = View.GONE
                lock.visibility = View.GONE
                lock.isSelected = false
            }
        }
    }

    override fun onBackPressed(): Boolean {
        if (isLocked) {
            show()
            Toast.makeText(context, R.string.dkplayer_lock_tip, Toast.LENGTH_SHORT).show()
            return true
        }
        return if (mControlWrapper!!.isFullScreen) {
            stopFullScreen()
        } else super.onBackPressed()
    }
}