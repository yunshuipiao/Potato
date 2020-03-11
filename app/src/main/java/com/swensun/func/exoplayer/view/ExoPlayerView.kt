package com.swensun.func.exoplayer.view

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Looper
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.metadata.Metadata
import com.google.android.exoplayer2.metadata.flac.PictureFrame
import com.google.android.exoplayer2.metadata.id3.ApicFrame
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.ads.AdsLoader
import com.google.android.exoplayer2.text.Cue
import com.google.android.exoplayer2.text.TextOutput
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.SubtitleView
import com.google.android.exoplayer2.ui.spherical.SingleTapListener
import com.google.android.exoplayer2.ui.spherical.SphericalGLSurfaceView
import com.google.android.exoplayer2.util.Assertions
import com.google.android.exoplayer2.util.ErrorMessageProvider
import com.google.android.exoplayer2.util.RepeatModeUtil
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.video.VideoDecoderGLSurfaceView
import com.google.android.exoplayer2.video.VideoListener
import com.swensun.potato.R
import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.*

class ExoPlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), AdsLoader.AdViewProvider {
    // LINT.ThenChange(../../../../../../res/values/attrs.xml)

    private val componentListener: ComponentListener
    private var contentFrame: AspectRatioFrameLayout?
    private var shutterView: View?
    /**
     * Gets the view onto which video is rendered. This is a:
     *
     *
     *  * [SurfaceView] by default, or if the `surface_type` attribute is set to `surface_view`.
     *  * [TextureView] if `surface_type` is `texture_view`.
     *  * [SphericalGLSurfaceView] if `surface_type` is `spherical_gl_surface_view`.
     *  * [VideoDecoderGLSurfaceView] if `surface_type` is `video_decoder_gl_surface_view`.
     *  * `null` if `surface_type` is `none`.
     *
     *
     * @return The [SurfaceView], [TextureView], [SphericalGLSurfaceView], [     ] or `null`.
     */
    var videoSurfaceView: View?
    private var artworkView: ImageView?
    /**
     * Gets the [SubtitleView].
     *
     * @return The [SubtitleView], or `null` if the layout has been customized and the
     * subtitle view is not present.
     */
    var subtitleView: SubtitleView?
    private var bufferingView: View?
    private var errorMessageView: TextView?
    private var controller: ExoPlayerControlView?
    private var adOverlayFrameLayout: FrameLayout?
    /**
     * Gets the overlay [FrameLayout], which can be populated with UI elements to show on top of
     * the player.
     *
     * @return The overlay [FrameLayout], or `null` if the layout has been customized and
     * the overlay is not present.
     */
    var overlayFrameLayout: FrameLayout?

    /** Returns the player currently set on this view, or null if no player is set.  */
    /**
     * Set the [Player] to use.
     *
     *
     * To transition a [Player] from targeting one view to another, it's recommended to use
     * [.switchTargetView] rather than this method. If you do
     * wish to use this method directly, be sure to attach the player to the new view *before*
     * calling `setPlayer(null)` to detach it from the old one. This ordering is significantly
     * more efficient and may allow for more seamless transitions.
     *
     * @param player The [Player] to use, or `null` to detach the current player. Only
     * players which are accessed on the main thread are supported (`player.getApplicationLooper() == Looper.getMainLooper()`).
     */
    /* isNewPlayer= */ var player: Player? = null
        set(player) {
            Assertions.checkState(Looper.myLooper() == Looper.getMainLooper())
            Assertions.checkArgument(
                player == null || player.applicationLooper == Looper.getMainLooper()
            )
            if (this.player === player) {
                return
            }
            val oldPlayer = this.player
            if (oldPlayer != null) {
                oldPlayer.removeListener(componentListener)
                val oldVideoComponent = oldPlayer.videoComponent
                if (oldVideoComponent != null) {
                    oldVideoComponent.removeVideoListener(componentListener)
                    if (videoSurfaceView is TextureView) {
                        oldVideoComponent.clearVideoTextureView(videoSurfaceView as TextureView?)
                    } else if (videoSurfaceView is SphericalGLSurfaceView) {
                        (videoSurfaceView as SphericalGLSurfaceView).setVideoComponent(null)
                    } else if (videoSurfaceView is VideoDecoderGLSurfaceView) {
                        oldVideoComponent.setVideoDecoderOutputBufferRenderer(null)
                    } else if (videoSurfaceView is SurfaceView) {
                        oldVideoComponent.clearVideoSurfaceView(videoSurfaceView as SurfaceView?)
                    }
                }
                val oldTextComponent = oldPlayer.textComponent
                oldTextComponent?.removeTextOutput(componentListener)
            }
            field = player
            if (useController()) {
                controller!!.player = player
            }
            if (subtitleView != null) {
                subtitleView!!.setCues(null)
            }
            updateBuffering()
            updateErrorMessage()
            updateForCurrentTrackSelections(true)
            if (player != null) {
                val newVideoComponent = player.videoComponent
                if (newVideoComponent != null) {
                    if (videoSurfaceView is TextureView) {
                        newVideoComponent.setVideoTextureView(videoSurfaceView as TextureView?)
                    } else if (videoSurfaceView is SphericalGLSurfaceView) {
                        (videoSurfaceView as SphericalGLSurfaceView).setVideoComponent(newVideoComponent)
                    } else if (videoSurfaceView is VideoDecoderGLSurfaceView) {
                        newVideoComponent.setVideoDecoderOutputBufferRenderer(
                            (videoSurfaceView as VideoDecoderGLSurfaceView).videoDecoderOutputBufferRenderer
                        )
                    } else if (videoSurfaceView is SurfaceView) {
                        newVideoComponent.setVideoSurfaceView(videoSurfaceView as SurfaceView?)
                    }
                    newVideoComponent.addVideoListener(componentListener)
                }
                val newTextComponent = player.textComponent
                newTextComponent?.addTextOutput(componentListener)
                player.addListener(componentListener)
                maybeShowController(false)
            } else {
                hideController()
            }
        }
    private var useController: Boolean = false
    private var controllerVisibilityListener: ExoPlayerControlView.VisibilityListener? = null
    private var useArtwork: Boolean = false
    private var defaultArtwork: Drawable? = null
    @ExoPlayerView.ShowBuffering
    private var showBuffering: Int = 0
    private var keepContentOnPlayerReset: Boolean = false
    private var errorMessageProvider: ErrorMessageProvider<in ExoPlaybackException>? = null
    private var customErrorMessage: CharSequence? = null
    private var controllerShowTimeoutMs: Int = 0
    /**
     * Returns whether the playback controls are automatically shown when playback starts, pauses,
     * ends, or fails. If set to false, the playback controls can be manually operated with [ ][.showController] and [.hideController].
     */
    /**
     * Sets whether the playback controls are automatically shown when playback starts, pauses, ends,
     * or fails. If set to false, the playback controls can be manually operated with [ ][.showController] and [.hideController].
     *
     * @param controllerAutoShow Whether the playback controls are allowed to show automatically.
     */
    var controllerAutoShow: Boolean = false
    private var controllerHideDuringAds: Boolean = false
    private var controllerHideOnTouch: Boolean = false
    private var textureViewRotation: Int = 0
    private var isTouching: Boolean = false

    /** Returns the [AspectRatioFrameLayout.ResizeMode].  */
    /**
     * Sets the [AspectRatioFrameLayout.ResizeMode].
     *
     * @param resizeMode The [AspectRatioFrameLayout.ResizeMode].
     */
    var resizeMode: Int
        @AspectRatioFrameLayout.ResizeMode
        get() {
            Assertions.checkStateNotNull(contentFrame)
            return contentFrame!!.resizeMode
        }
        set(@AspectRatioFrameLayout.ResizeMode resizeMode) {
            Assertions.checkStateNotNull(contentFrame)
            contentFrame!!.resizeMode = resizeMode
        }

    /** Returns whether the controller is currently visible.  */
    val isControllerVisible: Boolean
        get() = controller != null && controller!!.isVisible

    private val isPlayingAd: Boolean
        get() = this.player != null && this.player!!.isPlayingAd && this.player!!.playWhenReady

    // LINT.IfChange
    /**
     * Determines when the buffering view is shown. One of [.SHOW_BUFFERING_NEVER], [ ][.SHOW_BUFFERING_WHEN_PLAYING] or [.SHOW_BUFFERING_ALWAYS].
     */
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @IntDef(SHOW_BUFFERING_NEVER, SHOW_BUFFERING_WHEN_PLAYING, SHOW_BUFFERING_ALWAYS)
    annotation class ShowBuffering

    init {

        componentListener = ComponentListener()

        if (isInEditMode) {
            contentFrame = null
            shutterView = null
            videoSurfaceView = null
            artworkView = null
            subtitleView = null
            bufferingView = null
            errorMessageView = null
            controller = null
            adOverlayFrameLayout = null
            overlayFrameLayout = null
            val logo = ImageView(context)
            if (Util.SDK_INT >= 23) {
                configureEditModeLogoV23(resources, logo)
            } else {
                configureEditModeLogo(resources, logo)
            }
            addView(logo)
        }

        var shutterColorSet = false
        var shutterColor = 0
        var playerLayoutId = R.layout.exo_player_view
        var useArtwork = true
        var defaultArtworkId = 0
        var useController = true
        var surfaceType = SURFACE_TYPE_SURFACE_VIEW
        var resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        var controllerShowTimeoutMs =
            ExoPlayerControlView.DEFAULT_SHOW_TIMEOUT_MS
        var controllerHideOnTouch = true
        var controllerAutoShow = true
        var controllerHideDuringAds = true
        var showBuffering = SHOW_BUFFERING_NEVER
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ExoPlayerView, 0, 0)
            try {
                shutterColorSet = a.hasValue(R.styleable.ExoPlayerView_shutter_background_color)
                shutterColor =
                    a.getColor(R.styleable.ExoPlayerView_shutter_background_color, shutterColor)
                playerLayoutId =
                    a.getResourceId(R.styleable.ExoPlayerView_player_layout_id, playerLayoutId)
                useArtwork = a.getBoolean(R.styleable.ExoPlayerView_use_artwork, useArtwork)
                defaultArtworkId =
                    a.getResourceId(R.styleable.ExoPlayerView_default_artwork, defaultArtworkId)
                useController = a.getBoolean(R.styleable.ExoPlayerView_use_controller, useController)
                surfaceType = a.getInt(R.styleable.ExoPlayerView_surface_type, surfaceType)
                resizeMode = a.getInt(R.styleable.ExoPlayerView_resize_mode, resizeMode)
                controllerShowTimeoutMs =
                    a.getInt(R.styleable.ExoPlayerView_show_timeout, controllerShowTimeoutMs)
                controllerHideOnTouch =
                    a.getBoolean(R.styleable.ExoPlayerView_hide_on_touch, controllerHideOnTouch)
                controllerAutoShow =
                    a.getBoolean(R.styleable.ExoPlayerView_auto_show, controllerAutoShow)
                showBuffering = a.getInteger(R.styleable.ExoPlayerView_show_buffer, showBuffering)
                keepContentOnPlayerReset = a.getBoolean(
                    R.styleable.ExoPlayerView_keep_content_on_player_reset, keepContentOnPlayerReset
                )
                controllerHideDuringAds =
                    a.getBoolean(R.styleable.ExoPlayerView_hide_during_ads, controllerHideDuringAds)
            } finally {
                a.recycle()
            }
        }

        LayoutInflater.from(context).inflate(playerLayoutId, this)
        descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS

        // Content frame.
        contentFrame = findViewById(R.id.exo_content_frame)
        if (contentFrame != null) {
            setResizeModeRaw(contentFrame!!, resizeMode)
        }

        // Shutter view.
        shutterView = findViewById(R.id.exo_shutter)
        if (shutterView != null && shutterColorSet) {
            shutterView!!.setBackgroundColor(shutterColor)
        }

        // Create a surface view and insert it into the content frame, if there is one.
        if (contentFrame != null && surfaceType != SURFACE_TYPE_NONE) {
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            when (surfaceType) {
                SURFACE_TYPE_TEXTURE_VIEW -> videoSurfaceView = TextureView(context)
                SURFACE_TYPE_SPHERICAL_GL_SURFACE_VIEW -> {
                    val sphericalGLSurfaceView = SphericalGLSurfaceView(context)
                    sphericalGLSurfaceView.setSingleTapListener(componentListener)
                    videoSurfaceView = sphericalGLSurfaceView
                }
                SURFACE_TYPE_VIDEO_DECODER_GL_SURFACE_VIEW -> videoSurfaceView =
                    VideoDecoderGLSurfaceView(context)
                else -> videoSurfaceView = SurfaceView(context)
            }
            videoSurfaceView!!.layoutParams = params
            contentFrame!!.addView(videoSurfaceView, 0)
        } else {
            videoSurfaceView = null
        }

        // Ad overlay frame layout.
        adOverlayFrameLayout = findViewById(R.id.exo_ad_overlay)

        // Overlay frame layout.
        overlayFrameLayout = findViewById(R.id.exo_overlay)

        // Artwork view.
        artworkView = findViewById(R.id.exo_artwork)
        this.useArtwork = useArtwork && artworkView != null
        if (defaultArtworkId != 0) {
            defaultArtwork = ContextCompat.getDrawable(getContext(), defaultArtworkId)
        }

        // Subtitle view.
        subtitleView = findViewById(R.id.exo_subtitles)
        subtitleView?.let{
            it.setUserDefaultStyle()
            it.setUserDefaultTextSize()
        }

        // Buffering view.
        bufferingView = findViewById(R.id.exo_buffering)
        if (bufferingView != null) {
            bufferingView!!.visibility = View.GONE
        }
        this.showBuffering = showBuffering

        // Error message view.
        errorMessageView = findViewById(R.id.exo_error_message)
        if (errorMessageView != null) {
            errorMessageView!!.visibility = View.GONE
        }

        // Playback control view.
        val customController =
            findViewById<ExoPlayerControlView>(R.id.exo_controller)
        val controllerPlaceholder = findViewById<View>(R.id.exo_controller_placeholder)
        if (customController != null) {
            controller = customController
        } else if (controllerPlaceholder != null) {
            // Propagate attrs as playbackAttrs so that ExoPlayerControlView's custom attributes are
            // transferred, but standard attributes (e.g. background) are not.
            this.controller =
                ExoPlayerControlView(context, null, 0, attrs)
            controller?.id = R.id.exo_controller
            controller?.layoutParams = controllerPlaceholder.layoutParams
            val parent = controllerPlaceholder.parent as ViewGroup
            val controllerIndex = parent.indexOfChild(controllerPlaceholder)
            parent.removeView(controllerPlaceholder)
            parent.addView(controller, controllerIndex)
        } else {
            this.controller = null
        }
        this.controllerShowTimeoutMs = if (controller != null) controllerShowTimeoutMs else 0
        this.controllerHideOnTouch = controllerHideOnTouch
        this.controllerAutoShow = controllerAutoShow
        this.controllerHideDuringAds = controllerHideDuringAds
        this.useController = useController && controller != null
        hideController()
        updateContentDescription()
        controller?.addVisibilityListener(/* listener= */componentListener)
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (videoSurfaceView is SurfaceView) {
            // Work around https://github.com/google/ExoPlayer/issues/3160.
            videoSurfaceView!!.visibility = visibility
        }
    }

    /** Returns whether artwork is displayed if present in the media.  */
    fun getUseArtwork(): Boolean {
        return useArtwork
    }

    /**
     * Sets whether artwork is displayed if present in the media.
     *
     * @param useArtwork Whether artwork is displayed.
     */
    fun setUseArtwork(useArtwork: Boolean) {
        Assertions.checkState(!useArtwork || artworkView != null)
        if (this.useArtwork != useArtwork) {
            this.useArtwork = useArtwork
            updateForCurrentTrackSelections(/* isNewPlayer= */false)
        }
    }

    /** Returns the default artwork to display.  */
    fun getDefaultArtwork(): Drawable? {
        return defaultArtwork
    }

    /**
     * Sets the default artwork to display if `useArtwork` is `true` and no artwork is
     * present in the media.
     *
     * @param defaultArtwork the default artwork to display.
     */
    @Deprecated("use (@link {@link #setDefaultArtwork(Drawable)} instead.")
    fun setDefaultArtwork(defaultArtwork: Bitmap?) {
        setDefaultArtwork(
            if (defaultArtwork == null) null else BitmapDrawable(resources, defaultArtwork)
        )
    }

    /**
     * Sets the default artwork to display if `useArtwork` is `true` and no artwork is
     * present in the media.
     *
     * @param defaultArtwork the default artwork to display
     */
    fun setDefaultArtwork(defaultArtwork: Drawable?) {
        if (this.defaultArtwork !== defaultArtwork) {
            this.defaultArtwork = defaultArtwork
            updateForCurrentTrackSelections(/* isNewPlayer= */false)
        }
    }

    /** Returns whether the playback controls can be shown.  */
    fun getUseController(): Boolean {
        return useController
    }

    /**
     * Sets whether the playback controls can be shown. If set to `false` the playback controls
     * are never visible and are disconnected from the player.
     *
     * @param useController Whether the playback controls can be shown.
     */
    fun setUseController(useController: Boolean) {
        Assertions.checkState(!useController || controller != null)
        if (this.useController == useController) {
            return
        }
        this.useController = useController
        if (useController()) {
            controller!!.player = this.player
        } else if (controller != null) {
            controller?.hide()
            controller?.player = null
        }
        updateContentDescription()
    }

    /**
     * Sets the background color of the `exo_shutter` view.
     *
     * @param color The background color.
     */
    fun setShutterBackgroundColor(color: Int) {
        if (shutterView != null) {
            shutterView!!.setBackgroundColor(color)
        }
    }

    /**
     * Sets whether the currently displayed video frame or media artwork is kept visible when the
     * player is reset. A player reset is defined to mean the player being re-prepared with different
     * media, the player transitioning to unprepared media, [Player.stop] being called
     * with `reset=true`, or the player being replaced or cleared by calling [ ][.setPlayer].
     *
     *
     * If enabled, the currently displayed video frame or media artwork will be kept visible until
     * the player set on the view has been successfully prepared with new media and loaded enough of
     * it to have determined the available tracks. Hence enabling this option allows transitioning
     * from playing one piece of media to another, or from using one player instance to another,
     * without clearing the view's content.
     *
     *
     * If disabled, the currently displayed video frame or media artwork will be hidden as soon as
     * the player is reset. Note that the video frame is hidden by making `exo_shutter` visible.
     * Hence the video frame will not be hidden if using a custom layout that omits this view.
     *
     * @param keepContentOnPlayerReset Whether the currently displayed video frame or media artwork is
     * kept visible when the player is reset.
     */
    fun setKeepContentOnPlayerReset(keepContentOnPlayerReset: Boolean) {
        if (this.keepContentOnPlayerReset != keepContentOnPlayerReset) {
            this.keepContentOnPlayerReset = keepContentOnPlayerReset
            updateForCurrentTrackSelections(/* isNewPlayer= */false)
        }
    }

    /**
     * Sets whether a buffering spinner is displayed when the player is in the buffering state. The
     * buffering spinner is not displayed by default.
     *
     * @param showBuffering Whether the buffering icon is displayed
     */
    @Deprecated(
        "Use {@link #setShowBuffering(int)}\n" +
                "      "
    )
    fun setShowBuffering(showBuffering: Boolean) {
        setShowBuffering(if (showBuffering) SHOW_BUFFERING_WHEN_PLAYING else SHOW_BUFFERING_NEVER)
    }

    /**
     * Sets whether a buffering spinner is displayed when the player is in the buffering state. The
     * buffering spinner is not displayed by default.
     *
     * @param showBuffering The mode that defines when the buffering spinner is displayed. One of
     * [.SHOW_BUFFERING_NEVER], [.SHOW_BUFFERING_WHEN_PLAYING] and [     ][.SHOW_BUFFERING_ALWAYS].
     */
    fun setShowBuffering(@ExoPlayerView.ShowBuffering showBuffering: Int) {
        if (this.showBuffering != showBuffering) {
            this.showBuffering = showBuffering
            updateBuffering()
        }
    }

    /**
     * Sets the optional [ErrorMessageProvider].
     *
     * @param errorMessageProvider The error message provider.
     */
    fun setErrorMessageProvider(
        errorMessageProvider: ErrorMessageProvider<in ExoPlaybackException>?
    ) {
        if (this.errorMessageProvider !== errorMessageProvider) {
            this.errorMessageProvider = errorMessageProvider
            updateErrorMessage()
        }
    }

    /**
     * Sets a custom error message to be displayed by the view. The error message will be displayed
     * permanently, unless it is cleared by passing `null` to this method.
     *
     * @param message The message to display, or `null` to clear a previously set message.
     */
    fun setCustomErrorMessage(message: CharSequence?) {
        Assertions.checkState(errorMessageView != null)
        customErrorMessage = message
        updateErrorMessage()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (this.player != null && this.player!!.isPlayingAd) {
            return super.dispatchKeyEvent(event)
        }

        val isDpadKey = isDpadKey(event.keyCode)
        var handled = false
        if (isDpadKey && useController() && !controller!!.isVisible) {
            // Handle the key event by showing the controller.
            maybeShowController(true)
            handled = true
        } else if (dispatchMediaKeyEvent(event) || super.dispatchKeyEvent(event)) {
            // The key event was handled as a media key or by the super class. We should also show the
            // controller, or extend its show timeout if already visible.
            maybeShowController(true)
            handled = true
        } else if (isDpadKey && useController()) {
            // The key event wasn't handled, but we should extend the controller's show timeout.
            maybeShowController(true)
        }
        return handled
    }

    /**
     * Called to process media key events. Any [KeyEvent] can be passed but only media key
     * events will be handled. Does nothing if playback controls are disabled.
     *
     * @param event A key event.
     * @return Whether the key event was handled.
     */
    fun dispatchMediaKeyEvent(event: KeyEvent): Boolean {
        return useController() && controller!!.dispatchMediaKeyEvent(event)
    }

    /**
     * Shows the playback controls. Does nothing if playback controls are disabled.
     *
     *
     * The playback controls are automatically hidden during playback after {[ ][.getControllerShowTimeoutMs]}. They are shown indefinitely when playback has not started yet,
     * is paused, has ended or failed.
     */
    fun showController() {
        showController(shouldShowControllerIndefinitely())
    }

    /** Hides the playback controls. Does nothing if playback controls are disabled.  */
    fun hideController() {
        controller?.hide()
    }

    /**
     * Returns the playback controls timeout. The playback controls are automatically hidden after
     * this duration of time has elapsed without user input and with playback or buffering in
     * progress.
     *
     * @return The timeout in milliseconds. A non-positive value will cause the controller to remain
     * visible indefinitely.
     */
    fun getControllerShowTimeoutMs(): Int {
        return controllerShowTimeoutMs
    }

    /**
     * Sets the playback controls timeout. The playback controls are automatically hidden after this
     * duration of time has elapsed without user input and with playback or buffering in progress.
     *
     * @param controllerShowTimeoutMs The timeout in milliseconds. A non-positive value will cause the
     * controller to remain visible indefinitely.
     */
    fun setControllerShowTimeoutMs(controllerShowTimeoutMs: Int) {
        Assertions.checkStateNotNull(controller)
        this.controllerShowTimeoutMs = controllerShowTimeoutMs
        if (controller!!.isVisible) {
            // Update the controller's timeout if necessary.
            showController()
        }
    }

    /** Returns whether the playback controls are hidden by touch events.  */
    fun getControllerHideOnTouch(): Boolean {
        return controllerHideOnTouch
    }

    /**
     * Sets whether the playback controls are hidden by touch events.
     *
     * @param controllerHideOnTouch Whether the playback controls are hidden by touch events.
     */
    fun setControllerHideOnTouch(controllerHideOnTouch: Boolean) {
        Assertions.checkStateNotNull(controller)
        this.controllerHideOnTouch = controllerHideOnTouch
        updateContentDescription()
    }

    /**
     * Sets whether the playback controls are hidden when ads are playing. Controls are always shown
     * during ads if they are enabled and the player is paused.
     *
     * @param controllerHideDuringAds Whether the playback controls are hidden when ads are playing.
     */
    fun setControllerHideDuringAds(controllerHideDuringAds: Boolean) {
        this.controllerHideDuringAds = controllerHideDuringAds
    }

    /**
     * Set the [com.google.android.exoplayer2.ui.PlayerControlView.VisibilityListener].
     *
     * @param listener The listener to be notified about visibility changes, or null to remove the
     * current listener.
     */
    fun setControllerVisibilityListener(
        listener: ExoPlayerControlView.VisibilityListener?
    ) {
        Assertions.checkStateNotNull(controller)
        if (this.controllerVisibilityListener === listener) {
            return
        }
        if (this.controllerVisibilityListener != null) {
            controller!!.removeVisibilityListener(this.controllerVisibilityListener!!)
        }
        this.controllerVisibilityListener = listener
        if (listener != null) {
            controller!!.addVisibilityListener(listener)
        }
    }

    /**
     * Sets the [PlaybackPreparer].
     *
     * @param playbackPreparer The [PlaybackPreparer], or null to remove the current playback
     * preparer.
     */
    fun setPlaybackPreparer(playbackPreparer: PlaybackPreparer?) {
        Assertions.checkStateNotNull(controller)
        controller!!.setPlaybackPreparer(playbackPreparer)
    }

    /**
     * Sets the [ControlDispatcher].
     *
     * @param controlDispatcher The [ControlDispatcher], or null to use [     ].
     */
    fun setControlDispatcher(controlDispatcher: ControlDispatcher?) {
        Assertions.checkStateNotNull(controller)
        controller!!.setControlDispatcher(controlDispatcher)
    }

    /**
     * Sets the rewind increment in milliseconds.
     *
     * @param rewindMs The rewind increment in milliseconds. A non-positive value will cause the
     * rewind button to be disabled.
     */
    fun setRewindIncrementMs(rewindMs: Int) {
        Assertions.checkStateNotNull(controller)
        controller!!.setRewindIncrementMs(rewindMs)
    }

    /**
     * Sets the fast forward increment in milliseconds.
     *
     * @param fastForwardMs The fast forward increment in milliseconds. A non-positive value will
     * cause the fast forward button to be disabled.
     */
    fun setFastForwardIncrementMs(fastForwardMs: Int) {
        Assertions.checkStateNotNull(controller)
        controller!!.setFastForwardIncrementMs(fastForwardMs)
    }

    /**
     * Sets which repeat toggle modes are enabled.
     *
     * @param repeatToggleModes A set of [RepeatModeUtil.RepeatToggleModes].
     */
    fun setRepeatToggleModes(@RepeatModeUtil.RepeatToggleModes repeatToggleModes: Int) {
        Assertions.checkStateNotNull(controller)
        controller!!.setRepeatToggleModes(repeatToggleModes)
    }

    /**
     * Sets whether the shuffle button is shown.
     *
     * @param showShuffleButton Whether the shuffle button is shown.
     */
    fun setShowShuffleButton(showShuffleButton: Boolean) {
        Assertions.checkStateNotNull(controller)
        controller?.setShowShuffleButton(showShuffleButton)
    }

    /**
     * Sets whether the time bar should show all windows, as opposed to just the current one.
     *
     * @param showMultiWindowTimeBar Whether to show all windows.
     */
    fun setShowMultiWindowTimeBar(showMultiWindowTimeBar: Boolean) {
        Assertions.checkStateNotNull(controller)
        controller!!.setShowMultiWindowTimeBar(showMultiWindowTimeBar)
    }

    /**
     * Sets the millisecond positions of extra ad markers relative to the start of the window (or
     * timeline, if in multi-window mode) and whether each extra ad has been played or not. The
     * markers are shown in addition to any ad markers for ads in the player's timeline.
     *
     * @param extraAdGroupTimesMs The millisecond timestamps of the extra ad markers to show, or
     * `null` to show no extra ad markers.
     * @param extraPlayedAdGroups Whether each ad has been played, or `null` to show no extra ad
     * markers.
     */
    fun setExtraAdGroupMarkers(
        extraAdGroupTimesMs: LongArray?, extraPlayedAdGroups: BooleanArray?
    ) {
        Assertions.checkStateNotNull(controller)
        controller!!.setExtraAdGroupMarkers(extraAdGroupTimesMs, extraPlayedAdGroups)
    }

    /**
     * Set the [AspectRatioFrameLayout.AspectRatioListener].
     *
     * @param listener The listener to be notified about aspect ratios changes of the video content or
     * the content frame.
     */
    fun setAspectRatioListener(
        listener: AspectRatioFrameLayout.AspectRatioListener?
    ) {
        Assertions.checkStateNotNull(contentFrame)
        contentFrame!!.setAspectRatioListener(listener)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!useController() || this.player == null) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isTouching = true
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (isTouching) {
                    isTouching = false
                    performClick()
                    return true
                }
                return false
            }
            else -> return false
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        return toggleControllerVisibility()
    }

    override fun onTrackballEvent(ev: MotionEvent): Boolean {
        if (!useController() || this.player == null) {
            return false
        }
        maybeShowController(true)
        return true
    }

    /**
     * Should be called when the player is visible to the user and if `surface_type` is `spherical_gl_surface_view`. It is the counterpart to [.onPause].
     *
     *
     * This method should typically be called in `Activity.onStart()`, or `Activity.onResume()` for API versions &lt;= 23.
     */
    fun onResume() {
        if (videoSurfaceView is SphericalGLSurfaceView) {
            (videoSurfaceView as SphericalGLSurfaceView).onResume()
        }
    }

    /**
     * Should be called when the player is no longer visible to the user and if `surface_type`
     * is `spherical_gl_surface_view`. It is the counterpart to [.onResume].
     *
     *
     * This method should typically be called in `Activity.onStop()`, or `Activity.onPause()` for API versions &lt;= 23.
     */
    fun onPause() {
        if (videoSurfaceView is SphericalGLSurfaceView) {
            (videoSurfaceView as SphericalGLSurfaceView).onPause()
        }
    }

    /**
     * Called when there's a change in the aspect ratio of the content being displayed. The default
     * implementation sets the aspect ratio of the content frame to that of the content, unless the
     * content view is a [SphericalGLSurfaceView] in which case the frame's aspect ratio is
     * cleared.
     *
     * @param contentAspectRatio The aspect ratio of the content.
     * @param contentFrame The content frame, or `null`.
     * @param contentView The view that holds the content being displayed, or `null`.
     */
    protected fun onContentAspectRatioChanged(
        contentAspectRatio: Float,
        contentFrame: AspectRatioFrameLayout?,
        contentView: View?
    ) {
        contentFrame?.setAspectRatio(
            if (contentView is SphericalGLSurfaceView) 0f else contentAspectRatio
        )
    }

    // AdsLoader.AdViewProvider implementation.

    override fun getAdViewGroup(): ViewGroup {
        return Assertions.checkStateNotNull(
            adOverlayFrameLayout, "exo_ad_overlay must be present for ad playback"
        )
    }
    
    override fun getAdOverlayViews(): Array<View> {
        val overlayViews = ArrayList<View>()
        if (overlayFrameLayout != null) {
            overlayViews.add(overlayFrameLayout!!)
        }
        if (controller != null) {
            overlayViews.add(controller!!)
        }
        return overlayViews.toTypedArray()
    }

    // Internal methods.

    //    @EnsuresNonNullIf(expression = "controller", result = true)
    private fun useController(): Boolean {
        if (useController) {
            Assertions.checkStateNotNull(controller)
            return true
        }
        return false
    }

    //    @EnsuresNonNullIf(expression = "artworkView", result = true)
    private fun useArtwork(): Boolean {
        if (useArtwork) {
            Assertions.checkStateNotNull(artworkView)
            return true
        }
        return false
    }

    private fun toggleControllerVisibility(): Boolean {
        if (!useController() || this.player == null) {
            return false
        }
        if (!controller!!.isVisible) {
            maybeShowController(true)
        } else if (controllerHideOnTouch) {
            controller?.hide()
        }
        return true
    }

    /** Shows the playback controls, but only if forced or shown indefinitely.  */
    private fun maybeShowController(isForced: Boolean) {
        if (isPlayingAd && controllerHideDuringAds) {
            return
        }
        if (useController()) {
            val wasShowingIndefinitely = controller!!.isVisible && controller?.getShowTimeoutMs() ?: 0 <= 0
            val shouldShowIndefinitely = shouldShowControllerIndefinitely()
            if (isForced || wasShowingIndefinitely || shouldShowIndefinitely) {
                showController(shouldShowIndefinitely)
            }
        }
    }

    private fun shouldShowControllerIndefinitely(): Boolean {
        if (this.player == null) {
            return true
        }
        val playbackState = this.player!!.playbackState
        return controllerAutoShow && (playbackState == Player.STATE_IDLE
                || playbackState == Player.STATE_ENDED
                || !this.player!!.playWhenReady)
    }

    private fun showController(showIndefinitely: Boolean) {
        if (!useController()) {
            return
        }
        controller?.setShowTimeoutMs(if (showIndefinitely) 0 else controllerShowTimeoutMs)
        controller?.show()
    }

    private fun updateForCurrentTrackSelections(isNewPlayer: Boolean) {
        val player = this.player
        if (player == null || player.currentTrackGroups.isEmpty) {
            if (!keepContentOnPlayerReset) {
                hideArtwork()
                closeShutter()
            }
            return
        }

        if (isNewPlayer && !keepContentOnPlayerReset) {
            // Hide any video from the previous player.
            closeShutter()
        }

        val selections = player.currentTrackSelections
        for (i in 0 until selections.length) {
            if (player.getRendererType(i) == C.TRACK_TYPE_VIDEO && selections.get(i) != null) {
                // Video enabled so artwork must be hidden. If the shutter is closed, it will be opened in
                // onRenderedFirstFrame().
                hideArtwork()
                return
            }
        }

        // Video disabled so the shutter must be closed.
        closeShutter()
        // Display artwork if enabled and available, else hide it.
        if (useArtwork()) {
            for (i in 0 until selections.length) {
                val selection = selections.get(i)
                if (selection != null) {
                    for (j in 0 until selection.length()) {
                        val metadata = selection.getFormat(j).metadata
                        if (metadata != null && setArtworkFromMetadata(metadata)) {
                            return
                        }
                    }
                }
            }
            if (setDrawableArtwork(defaultArtwork)) {
                return
            }
        }
        // Artwork disabled or unavailable.
        hideArtwork()
    }

    //    @RequiresNonNull("artworkView")
    private fun setArtworkFromMetadata(metadata: Metadata): Boolean {
        var isArtworkSet = false
        var currentPictureType = PICTURE_TYPE_NOT_SET
        for (i in 0 until metadata.length()) {
            val metadataEntry = metadata.get(i)
            val pictureType: Int
            val bitmapData: ByteArray
            if (metadataEntry is ApicFrame) {
                bitmapData = metadataEntry.pictureData
                pictureType = metadataEntry.pictureType
            } else if (metadataEntry is PictureFrame) {
                bitmapData = metadataEntry.pictureData
                pictureType = metadataEntry.pictureType
            } else {
                continue
            }
            // Prefer the first front cover picture. If there aren't any, prefer the first picture.
            if (currentPictureType == PICTURE_TYPE_NOT_SET || pictureType == PICTURE_TYPE_FRONT_COVER) {
                val bitmap = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.size)
                isArtworkSet = setDrawableArtwork(BitmapDrawable(resources, bitmap))
                currentPictureType = pictureType
                if (currentPictureType == PICTURE_TYPE_FRONT_COVER) {
                    break
                }
            }
        }
        return isArtworkSet
    }

    //    @RequiresNonNull("artworkView")
    private fun setDrawableArtwork(drawable: Drawable?): Boolean {
        if (drawable != null) {
            val drawableWidth = drawable.intrinsicWidth
            val drawableHeight = drawable.intrinsicHeight
            if (drawableWidth > 0 && drawableHeight > 0) {
                val artworkAspectRatio = drawableWidth.toFloat() / drawableHeight
                onContentAspectRatioChanged(artworkAspectRatio, contentFrame, artworkView)
                artworkView!!.setImageDrawable(drawable)
                artworkView!!.visibility = View.VISIBLE
                return true
            }
        }
        return false
    }

    private fun hideArtwork() {
        if (artworkView != null) {
            artworkView!!.setImageResource(android.R.color.transparent) // Clears any bitmap reference.
            artworkView!!.visibility = View.INVISIBLE
        }
    }

    private fun closeShutter() {
        if (shutterView != null) {
            shutterView!!.visibility = View.VISIBLE
        }
    }

    private fun updateBuffering() {
        if (bufferingView != null) {
            val showBufferingSpinner = (this.player != null
                    && this.player!!.playbackState == Player.STATE_BUFFERING
                    && (showBuffering == SHOW_BUFFERING_ALWAYS || showBuffering == SHOW_BUFFERING_WHEN_PLAYING && this.player!!.playWhenReady))
            bufferingView!!.visibility = if (showBufferingSpinner) View.VISIBLE else View.GONE
        }
    }

    private fun updateErrorMessage() {
        if (errorMessageView != null) {
            if (customErrorMessage != null) {
                errorMessageView!!.text = customErrorMessage
                errorMessageView!!.visibility = View.VISIBLE
                return
            }
            val error = if (this.player != null) this.player!!.playbackError else null
            if (error != null && errorMessageProvider != null) {
                val errorMessage = errorMessageProvider!!.getErrorMessage(error).second
                errorMessageView!!.text = errorMessage
                errorMessageView!!.visibility = View.VISIBLE
            } else {
                errorMessageView!!.visibility = View.GONE
            }
        }
    }

    private fun updateContentDescription() {
        if (controller == null || !useController) {
            contentDescription = null
        } else if (controller?.visibility == View.VISIBLE) {
            contentDescription = if (controllerHideOnTouch)
                resources.getString(R.string.exo_controls_hide)
            else
                null
        } else {
            contentDescription = resources.getString(R.string.exo_controls_show)
        }
    }

    //    @SuppressLint("InlinedApi")
    private fun isDpadKey(keyCode: Int): Boolean {
        return (keyCode == KeyEvent.KEYCODE_DPAD_UP
                || keyCode == KeyEvent.KEYCODE_DPAD_UP_RIGHT
                || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
                || keyCode == KeyEvent.KEYCODE_DPAD_DOWN_RIGHT
                || keyCode == KeyEvent.KEYCODE_DPAD_DOWN
                || keyCode == KeyEvent.KEYCODE_DPAD_DOWN_LEFT
                || keyCode == KeyEvent.KEYCODE_DPAD_LEFT
                || keyCode == KeyEvent.KEYCODE_DPAD_UP_LEFT
                || keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
    }

    private inner class ComponentListener : Player.EventListener, TextOutput, VideoListener,
        OnLayoutChangeListener, SingleTapListener, ExoPlayerControlView.VisibilityListener {

        // TextOutput implementation

        override fun onCues(cues: List<Cue>) {
            if (subtitleView != null) {
                subtitleView!!.onCues(cues)
            }
        }

        // VideoListener implementation

        override fun onVideoSizeChanged(
            width: Int, height: Int, unappliedRotationDegrees: Int, pixelWidthHeightRatio: Float
        ) {
            var videoAspectRatio: Float =
                if (height == 0 || width == 0) 1f else width * pixelWidthHeightRatio / height

            if (videoSurfaceView is TextureView) {
                // Try to apply rotation transformation when our surface is a TextureView.
                if (unappliedRotationDegrees == 90 || unappliedRotationDegrees == 270) {
                    // We will apply a rotation 90/270 degree to the output texture of the TextureView.
                    // In this case, the output video's width and height will be swapped.
                    videoAspectRatio = 1 / videoAspectRatio
                }
                if (textureViewRotation != 0) {
                    videoSurfaceView!!.removeOnLayoutChangeListener(this)
                }
                textureViewRotation = unappliedRotationDegrees
                if (textureViewRotation != 0) {
                    // The texture view's dimensions might be changed after layout step.
                    // So add an OnLayoutChangeListener to apply rotation after layout step.
                    videoSurfaceView!!.addOnLayoutChangeListener(this)
                }
                applyTextureViewRotation((videoSurfaceView as TextureView?)!!, textureViewRotation)
            }

            onContentAspectRatioChanged(videoAspectRatio, contentFrame, videoSurfaceView)
        }

        override fun onRenderedFirstFrame() {
            if (shutterView != null) {
                shutterView!!.visibility = View.INVISIBLE
            }
        }
        
        override fun onTracksChanged(
            trackGroups: TrackGroupArray,
            trackSelections: TrackSelectionArray
        ) {
            updateForCurrentTrackSelections(/* isNewPlayer= */false)
        }

        // Player.EventListener implementation

        override fun onPlayerStateChanged(playWhenReady: Boolean, @Player.State playbackState: Int) {
            updateBuffering()
            updateErrorMessage()
            if (isPlayingAd && controllerHideDuringAds) {
                hideController()
            } else {
                maybeShowController(false)
            }
        }

        override fun onPositionDiscontinuity(@Player.DiscontinuityReason reason: Int) {
            if (isPlayingAd && controllerHideDuringAds) {
                hideController()
            }
        }

        // OnLayoutChangeListener implementation

        override fun onLayoutChange(
            view: View,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            applyTextureViewRotation(view as TextureView, textureViewRotation)
        }

        // SingleTapListener implementation

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return toggleControllerVisibility()
        }

        // ExoPlayerControlView.VisibilityListener implementation

        override fun onVisibilityChange(visibility: Int) {
            updateContentDescription()
        }
    }

    companion object {
        /** The buffering view is never shown.  */
        const val SHOW_BUFFERING_NEVER = 0
        /**
         * The buffering view is shown when the player is in the [buffering][Player.STATE_BUFFERING]
         * state and [playWhenReady][Player.getPlayWhenReady] is `true`.
         */
        const val SHOW_BUFFERING_WHEN_PLAYING = 1
        /**
         * The buffering view is always shown when the player is in the [ buffering][Player.STATE_BUFFERING] state.
         */
        const val SHOW_BUFFERING_ALWAYS = 2
        // LINT.ThenChange(../../../../../../res/values/attrs.xml)

        // LINT.IfChange
        private val SURFACE_TYPE_NONE = 0
        private val SURFACE_TYPE_SURFACE_VIEW = 1
        private val SURFACE_TYPE_TEXTURE_VIEW = 2
        private val SURFACE_TYPE_SPHERICAL_GL_SURFACE_VIEW = 3
        private val SURFACE_TYPE_VIDEO_DECODER_GL_SURFACE_VIEW = 4
        private val PICTURE_TYPE_FRONT_COVER = 3
        private val PICTURE_TYPE_NOT_SET = -1

        /**
         * Switches the view targeted by a given [Player].
         *
         * @param player The player whose target view is being switched.
         * @param oldPlayerView The old view to detach from the player.
         * @param newPlayerView The new view to attach to the player.
         */
        fun switchTargetView(
            player: Player,
            oldPlayerView: com.google.android.exoplayer2.ui.PlayerView?,
            newPlayerView: com.google.android.exoplayer2.ui.PlayerView?
        ) {
            if (oldPlayerView === newPlayerView) {
                return
            }
            // We attach the new view before detaching the old one because this ordering allows the player
            // to swap directly from one surface to another, without transitioning through a state where no
            // surface is attached. This is significantly more efficient and achieves a more seamless
            // transition when using platform provided video decoders.
            if (newPlayerView != null) {
                newPlayerView.player = player
            }
            if (oldPlayerView != null) {
                oldPlayerView.player = null
            }
        }

        @TargetApi(23)
        private fun configureEditModeLogoV23(resources: Resources, logo: ImageView) {
            logo.setImageDrawable(resources.getDrawable(R.drawable.exo_edit_mode_logo, null))
            logo.setBackgroundColor(
                resources.getColor(
                    R.color.exo_edit_mode_background_color,
                    null
                )
            )
        }

        private fun configureEditModeLogo(resources: Resources, logo: ImageView) {
            logo.setImageDrawable(resources.getDrawable(R.drawable.exo_edit_mode_logo))
            logo.setBackgroundColor(resources.getColor(R.color.exo_edit_mode_background_color))
        }

        private fun setResizeModeRaw(aspectRatioFrame: AspectRatioFrameLayout, resizeMode: Int) {
            aspectRatioFrame.resizeMode = resizeMode
        }

        /** Applies a texture rotation to a [TextureView].  */
        private fun applyTextureViewRotation(textureView: TextureView, textureViewRotation: Int) {
            val transformMatrix = Matrix()
            val textureViewWidth = textureView.width.toFloat()
            val textureViewHeight = textureView.height.toFloat()
            if (textureViewWidth != 0f && textureViewHeight != 0f && textureViewRotation != 0) {
                val pivotX = textureViewWidth / 2
                val pivotY = textureViewHeight / 2
                transformMatrix.postRotate(textureViewRotation.toFloat(), pivotX, pivotY)

                // After rotation, scale the rotated texture to fit the TextureView size.
                val originalTextureRect = RectF(0f, 0f, textureViewWidth, textureViewHeight)
                val rotatedTextureRect = RectF()
                transformMatrix.mapRect(rotatedTextureRect, originalTextureRect)
                transformMatrix.postScale(
                    textureViewWidth / rotatedTextureRect.width(),
                    textureViewHeight / rotatedTextureRect.height(),
                    pivotX,
                    pivotY
                )
            }
            textureView.setTransform(transformMatrix)
        }
    }
}/* attrs= */
