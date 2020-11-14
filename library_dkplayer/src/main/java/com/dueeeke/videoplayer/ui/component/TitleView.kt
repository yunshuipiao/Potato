package com.dueeeke.videoplayer.ui.component

import android.content.Context
import android.content.pm.ActivityInfo
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.widget.FrameLayout
import com.dueeeke.videoplayer.R
import com.dueeeke.videoplayer.controller.ControlWrapper
import com.dueeeke.videoplayer.controller.IControlComponent
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.util.PlayerUtils
import com.dueeeke.videoplayer.util.visible
import kotlinx.android.synthetic.main.dkplayer_layout_title_view.view.*

/**
 * author : zp
 * date : 2020/11/14
 * Potato
 */

class TitleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), IControlComponent {

    private val itemView =
        LayoutInflater.from(context).inflate(R.layout.dkplayer_layout_title_view, this)
    private val mActivity by lazy { PlayerUtils.scanForActivity(context) }

    private lateinit var mControlWrapper: ControlWrapper

    override fun attach(controlWrapper: ControlWrapper) {
        mControlWrapper = controlWrapper
    }

    init {
        visible = false
        back.setOnClickListener {
            if (mControlWrapper.isFullScreen) {
                mActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                mControlWrapper.stopFullScreen()
            }
        }
    }

    override val view: View?
        get() = this

    public fun setTitle(title: String) {
        this.title.text = title
    }

    override fun onVisibilityChanged(isVisible: Boolean, anim: Animation?) {
        //只有全屏时才有效
        if (mControlWrapper.isFullScreen == false) {
            return
        }
        if (isVisible) {
            if (visible == false) {
                visible = true
                anim?.let { startAnimation(it) }
            }
        } else {
            if (visible) {
                visible = false
                anim?.let { startAnimation(it) }
            }
        }
    }

    override fun onPlayStateChanged(playState: Int) {
        when (playState) {
            VideoView.STATE_IDLE, VideoView.STATE_START_ABORT, VideoView.STATE_PREPARING, VideoView.STATE_PREPARED, VideoView.STATE_ERROR, VideoView.STATE_PLAYBACK_COMPLETED ->
                visible = false
        }
    }

    override fun onPlayerStateChanged(playerState: Int) {
        when (playerState) {
            VideoView.PLAYER_FULL_SCREEN -> {
                if (mControlWrapper.isShowing && mControlWrapper.isLocked == false) {
                    visible = true
                }
                title.isSelected = true
            }
            VideoView.PLAYER_NORMAL -> {
                visible = false
                title.isSelected = false
            }
        }
        if (mControlWrapper.hasCutout()) {
            val orientation: Int = mActivity.requestedOrientation
            val cutoutHeight = mControlWrapper.cutoutHeight
            when (orientation) {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT -> {
                    title_container.setPadding(0, 0, 0, 0)
                }
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE -> {
                    title_container.setPadding(cutoutHeight, 0, 0, 0)
                }
                ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE -> {
                    title_container.setPadding(0, 0, cutoutHeight, 0)
                }
            }
        }
    }

    override fun setProgress(duration: Int, position: Int) {
    }

    override fun onLockStateChanged(isLocked: Boolean) {
        visible = !isLocked
    }
}