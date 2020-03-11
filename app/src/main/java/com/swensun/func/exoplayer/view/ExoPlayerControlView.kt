package com.swensun.func.exoplayer.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Looper
import android.os.SystemClock
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ui.DefaultTimeBar
import com.google.android.exoplayer2.ui.TimeBar
import com.google.android.exoplayer2.util.Assertions
import com.google.android.exoplayer2.util.RepeatModeUtil
import com.google.android.exoplayer2.util.Util
import com.swensun.potato.R
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

/**
 * A view for controlling [Player] instances.
 *
 *
 * A ExoPlayerControlView can be customized by setting attributes (or calling corresponding
 * methods), overriding drawables, overriding the view's layout file, or by specifying a custom view
 * layout file.
 *
 * <h3>Attributes</h3>
 *
 * The following attributes can be set on a ExoPlayerControlView when used in a layout XML file:
 *
 *
 *  * **`show_timeout`** - The time between the last user interaction and the controls
 * being automatically hidden, in milliseconds. Use zero if the controls should not
 * automatically timeout.
 *
 *  * Corresponding method: [.setShowTimeoutMs]
 *  * Default: [.DEFAULT_SHOW_TIMEOUT_MS]
 *
 *  * **`rewind_increment`** - The duration of the rewind applied when the user taps the
 * rewind button, in milliseconds. Use zero to disable the rewind button.
 *
 *  * Corresponding method: [.setRewindIncrementMs]
 *  * Default: [.DEFAULT_REWIND_MS]
 *
 *  * **`fastforward_increment`** - Like `rewind_increment`, but for fast forward.
 *
 *  * Corresponding method: [.setFastForwardIncrementMs]
 *  * Default: [.DEFAULT_FAST_FORWARD_MS]
 *
 *  * **`repeat_toggle_modes`** - A flagged enumeration value specifying which repeat
 * mode toggle options are enabled. Valid values are: `none`, `one`, `all`,
 * or `one|all`.
 *
 *  * Corresponding method: [.setRepeatToggleModes]
 *  * Default: [ExoPlayerControlView.DEFAULT_REPEAT_TOGGLE_MODES]
 *
 *  * **`show_shuffle_button`** - Whether the shuffle button is shown.
 *
 *  * Corresponding method: [.setShowShuffleButton]
 *  * Default: false
 *
 *  * **`time_bar_min_update_interval`** - Specifies the minimum interval between time
 * bar position updates.
 *
 *  * Corresponding method: [.setTimeBarMinUpdateInterval]
 *  * Default: [.DEFAULT_TIME_BAR_MIN_UPDATE_INTERVAL_MS]
 *
 *  * **`controller_layout_id`** - Specifies the id of the layout to be inflated. See
 * below for more details.
 *
 *  * Corresponding method: None
 *  * Default: `R.layout.exo_player_control_view`
 *
 *  * All attributes that can be set on [DefaultTimeBar] can also be set on a
 * ExoPlayerControlView, and will be propagated to the inflated [DefaultTimeBar] unless the
 * layout is overridden to specify a custom `exo_progress` (see below).
 *
 *
 * <h3>Overriding drawables</h3>
 *
 * The drawables used by ExoPlayerControlView (with its default layout file) can be overridden by
 * drawables with the same names defined in your application. The drawables that can be overridden
 * are:
 *
 *
 *  * **`exo_controls_play`** - The play icon.
 *  * **`exo_controls_pause`** - The pause icon.
 *  * **`exo_controls_rewind`** - The rewind icon.
 *  * **`exo_controls_fastforward`** - The fast forward icon.
 *  * **`exo_controls_previous`** - The previous icon.
 *  * **`exo_controls_next`** - The next icon.
 *  * **`exo_controls_repeat_off`** - The repeat icon for [       ][Player.REPEAT_MODE_OFF].
 *  * **`exo_controls_repeat_one`** - The repeat icon for [       ][Player.REPEAT_MODE_ONE].
 *  * **`exo_controls_repeat_all`** - The repeat icon for [       ][Player.REPEAT_MODE_ALL].
 *  * **`exo_controls_shuffle_off`** - The shuffle icon when shuffling is disabled.
 *  * **`exo_controls_shuffle_on`** - The shuffle icon when shuffling is enabled.
 *  * **`exo_controls_vr`** - The VR icon.
 *
 *
 * <h3>Overriding the layout file</h3>
 *
 * To customize the layout of ExoPlayerControlView throughout your app, or just for certain
 * configurations, you can define `exo_player_control_view.xml` layout files in your
 * application `res/layout*` directories. These layouts will override the one provided by the
 * ExoPlayer library, and will be inflated for use by ExoPlayerControlView. The view identifies and
 * binds its children by looking for the following ids:
 *
 *
 *
 *
 *
 *  * **`exo_play`** - The play button.
 *
 *  * Type: [View]
 *
 *  * **`exo_pause`** - The pause button.
 *
 *  * Type: [View]
 *
 *  * **`exo_rew`** - The rewind button.
 *
 *  * Type: [View]
 *
 *  * **`exo_ffwd`** - The fast forward button.
 *
 *  * Type: [View]
 *
 *  * **`exo_prev`** - The previous button.
 *
 *  * Type: [View]
 *
 *  * **`exo_next`** - The next button.
 *
 *  * Type: [View]
 *
 *  * **`exo_repeat_toggle`** - The repeat toggle button.
 *
 *  * Type: [ImageView]
 *  * Note: ExoPlayerControlView will programmatically set the drawable on the repeat toggle
 * button according to the player's current repeat mode. The drawables used are `exo_controls_repeat_off`, `exo_controls_repeat_one` and `exo_controls_repeat_all`. See the section above for information on overriding these
 * drawables.
 *
 *  * **`exo_shuffle`** - The shuffle button.
 *
 *  * Type: [ImageView]
 *  * Note: ExoPlayerControlView will programmatically set the drawable on the shuffle button
 * according to the player's current repeat mode. The drawables used are `exo_controls_shuffle_off` and `exo_controls_shuffle_on`. See the section above
 * for information on overriding these drawables.
 *
 *  * **`exo_vr`** - The VR mode button.
 *
 *  * Type: [View]
 *
 *  * **`exo_position`** - Text view displaying the current playback position.
 *
 *  * Type: [TextView]
 *
 *  * **`exo_duration`** - Text view displaying the current media duration.
 *
 *  * Type: [TextView]
 *
 *  * **`exo_progress_placeholder`** - A placeholder that's replaced with the inflated
 * [DefaultTimeBar]. Ignored if an `exo_progress` view exists.
 *
 *  * Type: [View]
 *
 *  * **`exo_progress`** - Time bar that's updated during playback and allows seeking.
 * [DefaultTimeBar] attributes set on the ExoPlayerControlView will not be automatically
 * propagated through to this instance. If a view exists with this id, any `exo_progress_placeholder` view will be ignored.
 *
 *  * Type: [TimeBar]
 *
 *
 *
 *
 * All child views are optional and so can be omitted if not required, however where defined they
 * must be of the expected type.
 *
 * <h3>Specifying a custom layout file</h3>
 *
 * Defining your own `exo_player_control_view.xml` is useful to customize the layout of
 * ExoPlayerControlView throughout your application. It's also possible to customize the layout for a
 * single instance in a layout file. This is achieved by setting the `controller_layout_id`
 * attribute on a ExoPlayerControlView. This will cause the specified layout to be inflated instead of
 * `exo_player_control_view.xml` for only the instance on which the attribute is set.
 */
open class ExoPlayerControlView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    playbackAttrs: AttributeSet?
) : FrameLayout(context, attrs, defStyleAttr) {

    private val componentListener: ComponentListener
    private val visibilityListeners: CopyOnWriteArrayList<VisibilityListener>
    private val previousButton: View?
    private val nextButton: View?
    private val playButton: View?
    private val pauseButton: View?
    private val fastForwardButton: View?
    private val rewindButton: View?
    private val repeatToggleButton: ImageView?
    private val shuffleButton: ImageView?
    private val vrButton: View?
    private val durationView: TextView?
    private val positionView: TextView?
    private val timeBar: TimeBar?
    private val formatBuilder: StringBuilder
    private val formatter: Formatter
    private val period: Timeline.Period
    private val window: Timeline.Window
    private val updateProgressAction: Runnable
    private val hideAction: Runnable

    private val repeatOffButtonDrawable: Drawable
    private val repeatOneButtonDrawable: Drawable
    private val repeatAllButtonDrawable: Drawable
    private val repeatOffButtonContentDescription: String
    private val repeatOneButtonContentDescription: String
    private val repeatAllButtonContentDescription: String
    private val shuffleOnButtonDrawable: Drawable
    private val shuffleOffButtonDrawable: Drawable
    private val buttonAlphaEnabled: Float
    private val buttonAlphaDisabled: Float
    private val shuffleOnContentDescription: String
    private val shuffleOffContentDescription: String

    /**
     * Returns the [Player] currently being controlled by this view, or null if no player is
     * set.
     */
    /**
     * Sets the [Player] to control.
     *
     * @param player The [Player] to control, or `null` to detach the current player. Only
     * players which are accessed on the main thread are supported (`player.getApplicationLooper() == Looper.getMainLooper()`).
     */
    var player: Player? = null
        set(player) {
            Assertions.checkState(Looper.myLooper() == Looper.getMainLooper())
            Assertions.checkArgument(
                player == null || player.applicationLooper == Looper.getMainLooper()
            )
            if (this.player === player) {
                return
            }
            if (this.player != null) {
                this.player!!.removeListener(componentListener)
            }
            field = player
            player?.addListener(componentListener)
            updateAll()
        }
    private var controlDispatcher: com.google.android.exoplayer2.ControlDispatcher
    private var progressUpdateListener: ProgressUpdateListener? = null
    private var playbackPreparer: PlaybackPreparer? = null

//    private var isAttachedToWindow: Boolean = false
    private var showMultiWindowTimeBar: Boolean = false
    private var multiWindowTimeBar: Boolean = false
    private var scrubbing: Boolean = false
    private var rewindMs: Int = 0
    private var fastForwardMs: Int = 0
    private var showTimeoutMs: Int = 0
    private var timeBarMinUpdateIntervalMs: Int = 0
    @RepeatModeUtil.RepeatToggleModes
    private var repeatToggleModes: Int = 0
    private var showShuffleButton: Boolean = false
    private var hideAtMs: Long = 0
    private var adGroupTimesMs: LongArray
    private var playedAdGroups: BooleanArray
    private var extraAdGroupTimesMs: LongArray
    private var extraPlayedAdGroups: BooleanArray
    private var currentWindowOffset: Long = 0

    /** Returns whether the VR button is shown.  */
    /**
     * Sets whether the VR button is shown.
     *
     * @param showVrButton Whether the VR button is shown.
     */
    var showVrButton: Boolean
        get() = vrButton != null && vrButton.visibility == View.VISIBLE
        set(showVrButton) {
            if (vrButton != null) {
                vrButton.visibility = if (showVrButton) View.VISIBLE else View.GONE
            }
        }

    /** Returns whether the controller is currently visible.  */
    val isVisible: Boolean
        get() = visibility == View.VISIBLE

    /** Listener to be notified about changes of the visibility of the UI control.  */
    interface VisibilityListener {

        /**
         * Called when the visibility changes.
         *
         * @param visibility The new visibility. Either [View.VISIBLE] or [View.GONE].
         */
        fun onVisibilityChange(visibility: Int)
    }

    /** Listener to be notified when progress has been updated.  */
    interface ProgressUpdateListener {

        /**
         * Called when progress needs to be updated.
         *
         * @param position The current position.
         * @param bufferedPosition The current buffered position.
         */
        fun onProgressUpdate(position: Long, bufferedPosition: Long)
    }

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : this(context, attrs, defStyleAttr, attrs) {
    }

    init {
        var controllerLayoutId = R.layout.exo_play_control_view
        rewindMs = DEFAULT_REWIND_MS
        fastForwardMs = DEFAULT_FAST_FORWARD_MS
        showTimeoutMs = DEFAULT_SHOW_TIMEOUT_MS
        repeatToggleModes = DEFAULT_REPEAT_TOGGLE_MODES
        timeBarMinUpdateIntervalMs = DEFAULT_TIME_BAR_MIN_UPDATE_INTERVAL_MS
        hideAtMs = C.TIME_UNSET
        showShuffleButton = false
        if (playbackAttrs != null) {
            val a = context
                .theme
                .obtainStyledAttributes(playbackAttrs, R.styleable.ExoPlayerControlView, 0, 0)
            try {
                rewindMs = a.getInt(R.styleable.ExoPlayerControlView_rewind_increment, rewindMs)
                fastForwardMs =
                    a.getInt(R.styleable.ExoPlayerControlView_fastforward_increment, fastForwardMs)
                showTimeoutMs = a.getInt(R.styleable.ExoPlayerControlView_show_timeout, showTimeoutMs)
                controllerLayoutId = a.getResourceId(
                    R.styleable.ExoPlayerControlView_controller_layout_id,
                    controllerLayoutId
                )
                repeatToggleModes = getRepeatToggleModes(a, repeatToggleModes)
                showShuffleButton = a.getBoolean(
                    R.styleable.ExoPlayerControlView_show_shuffle_button,
                    showShuffleButton
                )
                setTimeBarMinUpdateInterval(
                    a.getInt(
                        R.styleable.ExoPlayerControlView_time_bar_min_update_interval,
                        timeBarMinUpdateIntervalMs
                    )
                )
            } finally {
                a.recycle()
            }
        }
        visibilityListeners = CopyOnWriteArrayList()
        period = Timeline.Period()
        window = Timeline.Window()
        formatBuilder = StringBuilder()
        formatter = Formatter(formatBuilder, Locale.getDefault())
        adGroupTimesMs = LongArray(0)
        playedAdGroups = BooleanArray(0)
        extraAdGroupTimesMs = LongArray(0)
        extraPlayedAdGroups = BooleanArray(0)
        componentListener = ComponentListener()
        controlDispatcher = com.google.android.exoplayer2.DefaultControlDispatcher()
        updateProgressAction = Runnable { this.updateProgress() }
        hideAction = Runnable { this.hide() }

        LayoutInflater.from(context).inflate(controllerLayoutId, /* root= */ this)
        descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS

        val customTimeBar = findViewById<View>(R.id.exo_progress)
        val timeBarPlaceholder = findViewById<View>(R.id.exo_progress_placeholder)
        if (customTimeBar != null) {
            timeBar = customTimeBar as TimeBar
        } else if (timeBarPlaceholder != null) {
            // Propagate attrs as timebarAttrs so that DefaultTimeBar's custom attributes are transferred,
            // but standard attributes (e.g. background) are not.
            val defaultTimeBar = DefaultTimeBar(context, null, 0, playbackAttrs)
            defaultTimeBar.id = R.id.exo_progress
            defaultTimeBar.layoutParams = timeBarPlaceholder.layoutParams
            val parent = timeBarPlaceholder.parent as ViewGroup
            val timeBarIndex = parent.indexOfChild(timeBarPlaceholder)
            parent.removeView(timeBarPlaceholder)
            parent.addView(defaultTimeBar, timeBarIndex)
            timeBar = defaultTimeBar
        } else {
            timeBar = null
        }
        durationView = findViewById(R.id.exo_duration)
        positionView = findViewById(R.id.exo_position)

        timeBar?.addListener(componentListener)
        playButton = findViewById(R.id.exo_play)
        playButton?.setOnClickListener(componentListener)
        pauseButton = findViewById(R.id.exo_pause)
        pauseButton?.setOnClickListener(componentListener)
        previousButton = findViewById(R.id.exo_prev)
        previousButton?.setOnClickListener(componentListener)
        nextButton = findViewById(R.id.exo_next)
        nextButton?.setOnClickListener(componentListener)
        rewindButton = findViewById(R.id.exo_rew)
        rewindButton?.setOnClickListener(componentListener)
        fastForwardButton = findViewById(R.id.exo_ffwd)
        fastForwardButton?.setOnClickListener(componentListener)
        repeatToggleButton = findViewById(R.id.exo_repeat_toggle)
        repeatToggleButton?.setOnClickListener(componentListener)
        shuffleButton = findViewById(R.id.exo_shuffle)
        shuffleButton?.setOnClickListener(componentListener)
        vrButton = findViewById(R.id.exo_vr)
        showVrButton = false

        val resources = context.resources

        buttonAlphaEnabled =
            resources.getInteger(R.integer.exo_media_button_opacity_percentage_enabled).toFloat() / 100
        buttonAlphaDisabled =
            resources.getInteger(R.integer.exo_media_button_opacity_percentage_disabled).toFloat() / 100

        repeatOffButtonDrawable = resources.getDrawable(R.drawable.exo_controls_repeat_off)
        repeatOneButtonDrawable = resources.getDrawable(R.drawable.exo_controls_repeat_one)
        repeatAllButtonDrawable = resources.getDrawable(R.drawable.exo_controls_repeat_all)
        shuffleOnButtonDrawable = resources.getDrawable(R.drawable.exo_controls_shuffle_on)
        shuffleOffButtonDrawable = resources.getDrawable(R.drawable.exo_controls_shuffle_off)
        repeatOffButtonContentDescription =
            resources.getString(R.string.exo_controls_repeat_off_description)
        repeatOneButtonContentDescription =
            resources.getString(R.string.exo_controls_repeat_one_description)
        repeatAllButtonContentDescription =
            resources.getString(R.string.exo_controls_repeat_all_description)
        shuffleOnContentDescription =
            resources.getString(R.string.exo_controls_shuffle_on_description)
        shuffleOffContentDescription =
            resources.getString(R.string.exo_controls_shuffle_off_description)
    }

    /**
     * Sets whether the time bar should show all windows, as opposed to just the current one. If the
     * timeline has a period with unknown duration or more than [ ][.MAX_WINDOWS_FOR_MULTI_WINDOW_TIME_BAR] windows the time bar will fall back to showing a single
     * window.
     *
     * @param showMultiWindowTimeBar Whether the time bar should show all windows.
     */
    fun setShowMultiWindowTimeBar(showMultiWindowTimeBar: Boolean) {
        this.showMultiWindowTimeBar = showMultiWindowTimeBar
        updateTimeline()
    }

    /**
     * Sets the millisecond positions of extra ad markers relative to the start of the window (or
     * timeline, if in multi-window mode) and whether each extra ad has been played or not. The
     * markers are shown in addition to any ad markers for ads in the player's timeline.
     *
     * @param extraAdGroupTimesMs The millisecond timestamps of the extra ad markers to show, or
     * `null` to show no extra ad markers.
     * @param extraPlayedAdGroups Whether each ad has been played. Must be the same length as `extraAdGroupTimesMs`, or `null` if `extraAdGroupTimesMs` is `null`.
     */
    fun setExtraAdGroupMarkers(
        extraAdGroupTimesMs: LongArray?, extraPlayedAdGroups: BooleanArray?
    ) {
        var extraPlayedAdGroups = extraPlayedAdGroups
        if (extraAdGroupTimesMs == null) {
            this.extraAdGroupTimesMs = LongArray(0)
            this.extraPlayedAdGroups = BooleanArray(0)
        } else {
            extraPlayedAdGroups = Assertions.checkNotNull(extraPlayedAdGroups)
            Assertions.checkArgument(extraAdGroupTimesMs.size == extraPlayedAdGroups.size)
            this.extraAdGroupTimesMs = extraAdGroupTimesMs
            this.extraPlayedAdGroups = extraPlayedAdGroups
        }
        updateTimeline()
    }

    /**
     * Adds a [VisibilityListener].
     *
     * @param listener The listener to be notified about visibility changes.
     */
    fun addVisibilityListener(listener: VisibilityListener) {
        visibilityListeners.add(listener)
    }

    /**
     * Removes a [VisibilityListener].
     *
     * @param listener The listener to be removed.
     */
    fun removeVisibilityListener(listener: VisibilityListener) {
        visibilityListeners.remove(listener)
    }

    /**
     * Sets the [ProgressUpdateListener].
     *
     * @param listener The listener to be notified about when progress is updated.
     */
    fun setProgressUpdateListener(listener: ProgressUpdateListener?) {
        this.progressUpdateListener = listener
    }

    /**
     * Sets the [PlaybackPreparer].
     *
     * @param playbackPreparer The [PlaybackPreparer], or null to remove the current playback
     * preparer.
     */
    fun setPlaybackPreparer(playbackPreparer: PlaybackPreparer?) {
        this.playbackPreparer = playbackPreparer
    }

    /**
     * Sets the [com.google.android.exoplayer2.ControlDispatcher].
     *
     * @param controlDispatcher The [com.google.android.exoplayer2.ControlDispatcher], or null
     * to use [com.google.android.exoplayer2.DefaultControlDispatcher].
     */
    fun setControlDispatcher(
        controlDispatcher: com.google.android.exoplayer2.ControlDispatcher?
    ) {
        this.controlDispatcher =
            controlDispatcher ?: com.google.android.exoplayer2.DefaultControlDispatcher()
    }

    /**
     * Sets the rewind increment in milliseconds.
     *
     * @param rewindMs The rewind increment in milliseconds. A non-positive value will cause the
     * rewind button to be disabled.
     */
    fun setRewindIncrementMs(rewindMs: Int) {
        this.rewindMs = rewindMs
        updateNavigation()
    }

    /**
     * Sets the fast forward increment in milliseconds.
     *
     * @param fastForwardMs The fast forward increment in milliseconds. A non-positive value will
     * cause the fast forward button to be disabled.
     */
    fun setFastForwardIncrementMs(fastForwardMs: Int) {
        this.fastForwardMs = fastForwardMs
        updateNavigation()
    }

    /**
     * Returns the playback controls timeout. The playback controls are automatically hidden after
     * this duration of time has elapsed without user input.
     *
     * @return The duration in milliseconds. A non-positive value indicates that the controls will
     * remain visible indefinitely.
     */
    fun getShowTimeoutMs(): Int {
        return showTimeoutMs
    }

    /**
     * Sets the playback controls timeout. The playback controls are automatically hidden after this
     * duration of time has elapsed without user input.
     *
     * @param showTimeoutMs The duration in milliseconds. A non-positive value will cause the controls
     * to remain visible indefinitely.
     */
    fun setShowTimeoutMs(showTimeoutMs: Int) {
        this.showTimeoutMs = showTimeoutMs
        if (isVisible) {
            // Reset the timeout.
            hideAfterTimeout()
        }
    }

    /**
     * Returns which repeat toggle modes are enabled.
     *
     * @return The currently enabled [RepeatModeUtil.RepeatToggleModes].
     */
    @RepeatModeUtil.RepeatToggleModes
    fun getRepeatToggleModes(): Int {
        return repeatToggleModes
    }

    /**
     * Sets which repeat toggle modes are enabled.
     *
     * @param repeatToggleModes A set of [RepeatModeUtil.RepeatToggleModes].
     */
    fun setRepeatToggleModes(@RepeatModeUtil.RepeatToggleModes repeatToggleModes: Int) {
        this.repeatToggleModes = repeatToggleModes
        if (this.player != null) {
            @Player.RepeatMode val currentMode = this.player!!.repeatMode
            if (repeatToggleModes == RepeatModeUtil.REPEAT_TOGGLE_MODE_NONE && currentMode != Player.REPEAT_MODE_OFF) {
                controlDispatcher.dispatchSetRepeatMode(this.player!!, Player.REPEAT_MODE_OFF)
            } else if (repeatToggleModes == RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE && currentMode == Player.REPEAT_MODE_ALL) {
                controlDispatcher.dispatchSetRepeatMode(this.player!!, Player.REPEAT_MODE_ONE)
            } else if (repeatToggleModes == RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL && currentMode == Player.REPEAT_MODE_ONE) {
                controlDispatcher.dispatchSetRepeatMode(this.player!!, Player.REPEAT_MODE_ALL)
            }
        }
        updateRepeatModeButton()
    }

    /** Returns whether the shuffle button is shown.  */
    fun getShowShuffleButton(): Boolean {
        return showShuffleButton
    }

    /**
     * Sets whether the shuffle button is shown.
     *
     * @param showShuffleButton Whether the shuffle button is shown.
     */
    fun setShowShuffleButton(showShuffleButton: Boolean) {
        this.showShuffleButton = showShuffleButton
        updateShuffleButton()
    }

    /**
     * Sets listener for the VR button.
     *
     * @param onClickListener Listener for the VR button, or null to clear the listener.
     */
    fun setVrButtonListener(onClickListener: OnClickListener?) {
        vrButton?.setOnClickListener(onClickListener)
    }

    /**
     * Sets the minimum interval between time bar position updates.
     *
     *
     * Note that smaller intervals, e.g. 33ms, will result in a smooth movement but will use more
     * CPU resources while the time bar is visible, whereas larger intervals, e.g. 200ms, will result
     * in a step-wise update with less CPU usage.
     *
     * @param minUpdateIntervalMs The minimum interval between time bar position updates, in
     * milliseconds.
     */
    fun setTimeBarMinUpdateInterval(minUpdateIntervalMs: Int) {
        // Do not accept values below 16ms (60fps) and larger than the maximum update interval.
        timeBarMinUpdateIntervalMs =
            Util.constrainValue(minUpdateIntervalMs, 16, MAX_UPDATE_INTERVAL_MS)
    }

    /**
     * Shows the playback controls. If [.getShowTimeoutMs] is positive then the controls will
     * be automatically hidden after this duration of time has elapsed without user input.
     */
    fun show() {
        if (!isVisible) {
            visibility = View.VISIBLE
            for (visibilityListener in visibilityListeners) {
                visibilityListener.onVisibilityChange(visibility)
            }
            updateAll()
            requestPlayPauseFocus()
        }
        // Call hideAfterTimeout even if already visible to reset the timeout.
        hideAfterTimeout()
    }

    /** Hides the controller.  */
    fun hide() {
        if (isVisible) {
            visibility = View.GONE
            for (visibilityListener in visibilityListeners) {
                visibilityListener.onVisibilityChange(visibility)
            }
            removeCallbacks(updateProgressAction)
            removeCallbacks(hideAction)
            hideAtMs = C.TIME_UNSET
        }
    }

    private fun hideAfterTimeout() {
        removeCallbacks(hideAction)
        if (showTimeoutMs > 0) {
            hideAtMs = SystemClock.uptimeMillis() + showTimeoutMs
            if (isAttachedToWindow) {
                postDelayed(hideAction, showTimeoutMs.toLong())
            }
        } else {
            hideAtMs = C.TIME_UNSET
        }
    }

    private fun updateAll() {
        updatePlayPauseButton()
        updateNavigation()
        updateRepeatModeButton()
        updateShuffleButton()
        updateTimeline()
    }

    private fun updatePlayPauseButton() {
        if (!isVisible || !isAttachedToWindow) {
            return
        }
        var requestPlayPauseFocus = false
        val shouldShowPauseButton = shouldShowPauseButton()
        if (playButton != null) {
            requestPlayPauseFocus =
                requestPlayPauseFocus or (shouldShowPauseButton && playButton.isFocused)
            playButton.visibility = if (shouldShowPauseButton) View.GONE else View.VISIBLE
        }
        if (pauseButton != null) {
            requestPlayPauseFocus =
                requestPlayPauseFocus or (!shouldShowPauseButton && pauseButton.isFocused)
            pauseButton.visibility = if (shouldShowPauseButton) View.VISIBLE else View.GONE
        }
        if (requestPlayPauseFocus) {
            requestPlayPauseFocus()
        }
    }

    private fun updateNavigation() {
        if (!isVisible || !isAttachedToWindow) {
            return
        }

        val player = this.player
        var enableSeeking = false
        var enablePrevious = false
        var enableRewind = false
        var enableFastForward = false
        var enableNext = false
        if (player != null) {
            val timeline = player.currentTimeline
            if (!timeline.isEmpty && !player.isPlayingAd) {
                timeline.getWindow(player.currentWindowIndex, window)
                val isSeekable = window.isSeekable
                enableSeeking = isSeekable
                enablePrevious = isSeekable || !window.isDynamic || player.hasPrevious()
                enableRewind = isSeekable && rewindMs > 0
                enableFastForward = isSeekable && fastForwardMs > 0
                enableNext = window.isDynamic || player.hasNext()
            }
        }

        setButtonEnabled(enablePrevious, previousButton)
        setButtonEnabled(enableRewind, rewindButton)
        setButtonEnabled(enableFastForward, fastForwardButton)
        setButtonEnabled(enableNext, nextButton)
        timeBar?.setEnabled(enableSeeking)
    }

    private fun updateRepeatModeButton() {
        if (!isVisible || !isAttachedToWindow || repeatToggleButton == null) {
            return
        }

        if (repeatToggleModes == RepeatModeUtil.REPEAT_TOGGLE_MODE_NONE) {
            repeatToggleButton.visibility = View.GONE
            return
        }

        val player = this.player
        if (player == null) {
            setButtonEnabled(false, repeatToggleButton)
            repeatToggleButton.setImageDrawable(repeatOffButtonDrawable)
            repeatToggleButton.contentDescription = repeatOffButtonContentDescription
            return
        }

        setButtonEnabled(true, repeatToggleButton)
        when (player.repeatMode) {
            Player.REPEAT_MODE_OFF -> {
                repeatToggleButton.setImageDrawable(repeatOffButtonDrawable)
                repeatToggleButton.contentDescription = repeatOffButtonContentDescription
            }
            Player.REPEAT_MODE_ONE -> {
                repeatToggleButton.setImageDrawable(repeatOneButtonDrawable)
                repeatToggleButton.contentDescription = repeatOneButtonContentDescription
            }
            Player.REPEAT_MODE_ALL -> {
                repeatToggleButton.setImageDrawable(repeatAllButtonDrawable)
                repeatToggleButton.contentDescription = repeatAllButtonContentDescription
            }
        }// Never happens.
        repeatToggleButton.visibility = View.VISIBLE
    }

    private fun updateShuffleButton() {
        if (!isVisible || !isAttachedToWindow || shuffleButton == null) {
            return
        }

        val player = this.player
        if (!showShuffleButton) {
            shuffleButton.visibility = View.GONE
        } else if (player == null) {
            setButtonEnabled(false, shuffleButton)
            shuffleButton.setImageDrawable(shuffleOffButtonDrawable)
            shuffleButton.contentDescription = shuffleOffContentDescription
        } else {
            setButtonEnabled(true, shuffleButton)
            shuffleButton.setImageDrawable(
                if (player.shuffleModeEnabled) shuffleOnButtonDrawable else shuffleOffButtonDrawable
            )
            shuffleButton.contentDescription = if (player.shuffleModeEnabled)
                shuffleOnContentDescription
            else
                shuffleOffContentDescription
        }
    }

    private fun updateTimeline() {
        val player = this.player ?: return
        multiWindowTimeBar =
            showMultiWindowTimeBar && canShowMultiWindowTimeBar(player.currentTimeline, window)
        currentWindowOffset = 0
        var durationUs: Long = 0
        var adGroupCount = 0
        val timeline = player.currentTimeline
        if (!timeline.isEmpty) {
            val currentWindowIndex = player.currentWindowIndex
            val firstWindowIndex = if (multiWindowTimeBar) 0 else currentWindowIndex
            val lastWindowIndex =
                if (multiWindowTimeBar) timeline.windowCount - 1 else currentWindowIndex
            for (i in firstWindowIndex..lastWindowIndex) {
                if (i == currentWindowIndex) {
                    currentWindowOffset = C.usToMs(durationUs)
                }
                timeline.getWindow(i, window)
                if (window.durationUs == C.TIME_UNSET) {
                    Assertions.checkState(!multiWindowTimeBar)
                    break
                }
                for (j in window.firstPeriodIndex..window.lastPeriodIndex) {
                    timeline.getPeriod(j, period)
                    val periodAdGroupCount = period.adGroupCount
                    for (adGroupIndex in 0 until periodAdGroupCount) {
                        var adGroupTimeInPeriodUs = period.getAdGroupTimeUs(adGroupIndex)
                        if (adGroupTimeInPeriodUs == C.TIME_END_OF_SOURCE) {
                            if (period.durationUs == C.TIME_UNSET) {
                                // Don't show ad markers for postrolls in periods with unknown duration.
                                continue
                            }
                            adGroupTimeInPeriodUs = period.durationUs
                        }
                        val adGroupTimeInWindowUs =
                            adGroupTimeInPeriodUs + period.positionInWindowUs
                        if (adGroupTimeInWindowUs >= 0) {
                            if (adGroupCount == adGroupTimesMs.size) {
                                val newLength =
                                    if (adGroupTimesMs.size == 0) 1 else adGroupTimesMs.size * 2
                                adGroupTimesMs = Arrays.copyOf(adGroupTimesMs, newLength)
                                playedAdGroups = Arrays.copyOf(playedAdGroups, newLength)
                            }
                            adGroupTimesMs[adGroupCount] =
                                C.usToMs(durationUs + adGroupTimeInWindowUs)
                            playedAdGroups[adGroupCount] = period.hasPlayedAdGroup(adGroupIndex)
                            adGroupCount++
                        }
                    }
                }
                durationUs += window.durationUs
            }
        }
        val durationMs = C.usToMs(durationUs)
        if (durationView != null) {
            durationView.text = Util.getStringForTime(formatBuilder, formatter, durationMs)
        }
        if (timeBar != null) {
            timeBar.setDuration(durationMs)
            val extraAdGroupCount = extraAdGroupTimesMs.size
            val totalAdGroupCount = adGroupCount + extraAdGroupCount
            if (totalAdGroupCount > adGroupTimesMs.size) {
                adGroupTimesMs = Arrays.copyOf(adGroupTimesMs, totalAdGroupCount)
                playedAdGroups = Arrays.copyOf(playedAdGroups, totalAdGroupCount)
            }
            System.arraycopy(
                extraAdGroupTimesMs,
                0,
                adGroupTimesMs,
                adGroupCount,
                extraAdGroupCount
            )
            System.arraycopy(
                extraPlayedAdGroups,
                0,
                playedAdGroups,
                adGroupCount,
                extraAdGroupCount
            )
            timeBar.setAdGroupTimesMs(adGroupTimesMs, playedAdGroups, totalAdGroupCount)
        }
        updateProgress()
    }

    private fun updateProgress() {
        if (!isVisible || !isAttachedToWindow) {
            return
        }

        val player = this.player
        var position: Long = 0
        var bufferedPosition: Long = 0
        if (player != null) {
            position = currentWindowOffset + player.contentPosition
            bufferedPosition = currentWindowOffset + player.contentBufferedPosition
        }
        if (positionView != null && !scrubbing) {
            positionView.text = Util.getStringForTime(formatBuilder, formatter, position)
        }
        if (timeBar != null) {
            timeBar.setPosition(position)
            timeBar.setBufferedPosition(bufferedPosition)
        }
        if (progressUpdateListener != null) {
            progressUpdateListener!!.onProgressUpdate(position, bufferedPosition)
        }

        // Cancel any pending updates and schedule a new one if necessary.
        removeCallbacks(updateProgressAction)
        val playbackState = player?.playbackState ?: Player.STATE_IDLE
        if (player != null && player.isPlaying) {
            var mediaTimeDelayMs = timeBar?.preferredUpdateDelay ?: MAX_UPDATE_INTERVAL_MS.toLong()

            // Limit delay to the start of the next full second to ensure position display is smooth.
            val mediaTimeUntilNextFullSecondMs = 1000 - position % 1000
            mediaTimeDelayMs = Math.min(mediaTimeDelayMs, mediaTimeUntilNextFullSecondMs)

            // Calculate the delay until the next update in real time, taking playbackSpeed into account.
            val playbackSpeed = player.playbackParameters.speed
            var delayMs: Float =
                if (playbackSpeed > 0) (mediaTimeDelayMs / playbackSpeed) else MAX_UPDATE_INTERVAL_MS.toFloat()

            // Constrain the delay to avoid too frequent / infrequent updates.
            delayMs = Util.constrainValue(
                delayMs,
                timeBarMinUpdateIntervalMs.toFloat(),
                MAX_UPDATE_INTERVAL_MS.toFloat()
            )
            postDelayed(updateProgressAction, delayMs.toLong())
        } else if (playbackState != Player.STATE_ENDED && playbackState != Player.STATE_IDLE) {
            postDelayed(updateProgressAction, MAX_UPDATE_INTERVAL_MS.toLong())
        }
    }

    private fun requestPlayPauseFocus() {
        val shouldShowPauseButton = shouldShowPauseButton()
        if (!shouldShowPauseButton && playButton != null) {
            playButton.requestFocus()
        } else if (shouldShowPauseButton && pauseButton != null) {
            pauseButton.requestFocus()
        }
    }

    private fun setButtonEnabled(enabled: Boolean, view: View?) {
        if (view == null) {
            return
        }
        view.isEnabled = enabled
        view.alpha = if (enabled) buttonAlphaEnabled else buttonAlphaDisabled
        view.visibility = View.VISIBLE
    }

    private fun previous(player: Player) {
        val timeline = player.currentTimeline
        if (timeline.isEmpty || player.isPlayingAd) {
            return
        }
        val windowIndex = player.currentWindowIndex
        timeline.getWindow(windowIndex, window)
        val previousWindowIndex = player.previousWindowIndex
        if (previousWindowIndex != C.INDEX_UNSET && (player.currentPosition <= MAX_POSITION_FOR_SEEK_TO_PREVIOUS || window.isDynamic && !window.isSeekable)) {
            seekTo(player, previousWindowIndex, C.TIME_UNSET)
        } else {
            seekTo(player, windowIndex, /* positionMs= */ 0)
        }
    }

    private fun next(player: Player) {
        val timeline = player.currentTimeline
        if (timeline.isEmpty || player.isPlayingAd) {
            return
        }
        val windowIndex = player.currentWindowIndex
        val nextWindowIndex = player.nextWindowIndex
        if (nextWindowIndex != C.INDEX_UNSET) {
            seekTo(player, nextWindowIndex, C.TIME_UNSET)
        } else if (timeline.getWindow(windowIndex, window).isDynamic) {
            seekTo(player, windowIndex, C.TIME_UNSET)
        }
    }

    private fun rewind(player: Player) {
        if (player.isCurrentWindowSeekable && rewindMs > 0) {
            seekToOffset(player, (-rewindMs).toLong())
        }
    }

    private fun fastForward(player: Player) {
        if (player.isCurrentWindowSeekable && fastForwardMs > 0) {
            seekToOffset(player, fastForwardMs.toLong())
        }
    }

    private fun seekToOffset(player: Player, offsetMs: Long) {
        var positionMs = player.currentPosition + offsetMs
        val durationMs = player.duration
        if (durationMs != C.TIME_UNSET) {
            positionMs = Math.min(positionMs, durationMs)
        }
        positionMs = Math.max(positionMs, 0)
        seekTo(player, player.currentWindowIndex, positionMs)
    }

    private fun seekToTimeBarPosition(player: Player, positionMs: Long) {
        var positionMs = positionMs
        var windowIndex: Int
        val timeline = player.currentTimeline
        if (multiWindowTimeBar && !timeline.isEmpty) {
            val windowCount = timeline.windowCount
            windowIndex = 0
            while (true) {
                val windowDurationMs = timeline.getWindow(windowIndex, window).durationMs
                if (positionMs < windowDurationMs) {
                    break
                } else if (windowIndex == windowCount - 1) {
                    // Seeking past the end of the last window should seek to the end of the timeline.
                    positionMs = windowDurationMs
                    break
                }
                positionMs -= windowDurationMs
                windowIndex++
            }
        } else {
            windowIndex = player.currentWindowIndex
        }
        val dispatched = seekTo(player, windowIndex, positionMs)
        if (!dispatched) {
            // The seek wasn't dispatched then the progress bar scrubber will be in the wrong position.
            // Trigger a progress update to snap it back.
            updateProgress()
        }
    }

    private fun seekTo(player: Player, windowIndex: Int, positionMs: Long): Boolean {
        return controlDispatcher.dispatchSeekTo(player, windowIndex, positionMs)
    }

    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (hideAtMs != C.TIME_UNSET) {
            val delayMs = hideAtMs - SystemClock.uptimeMillis()
            if (delayMs <= 0) {
                hide()
            } else {
                postDelayed(hideAction, delayMs)
            }
        } else if (isVisible) {
            hideAfterTimeout()
        }
        updateAll()
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks(updateProgressAction)
        removeCallbacks(hideAction)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            removeCallbacks(hideAction)
        } else if (ev.action == MotionEvent.ACTION_UP) {
            hideAfterTimeout()
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return dispatchMediaKeyEvent(event) || super.dispatchKeyEvent(event)
    }

    /**
     * Called to process media key events. Any [KeyEvent] can be passed but only media key
     * events will be handled.
     *
     * @param event A key event.
     * @return Whether the key event was handled.
     */
    fun dispatchMediaKeyEvent(event: KeyEvent): Boolean {
        val keyCode = event.keyCode
        val player = this.player
        if (player == null || !isHandledMediaKey(keyCode)) {
            return false
        }
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_MEDIA_FAST_FORWARD) {
                fastForward(player)
            } else if (keyCode == KeyEvent.KEYCODE_MEDIA_REWIND) {
                rewind(player)
            } else if (event.repeatCount == 0) {
                when (keyCode) {
                    KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> controlDispatcher.dispatchSetPlayWhenReady(
                        player,
                        !player.playWhenReady
                    )
                    KeyEvent.KEYCODE_MEDIA_PLAY -> controlDispatcher.dispatchSetPlayWhenReady(
                        player,
                        true
                    )
                    KeyEvent.KEYCODE_MEDIA_PAUSE -> controlDispatcher.dispatchSetPlayWhenReady(
                        player,
                        false
                    )
                    KeyEvent.KEYCODE_MEDIA_NEXT -> next(player)
                    KeyEvent.KEYCODE_MEDIA_PREVIOUS -> previous(player)
                    else -> {
                    }
                }
            }
        }
        return true
    }

    private fun shouldShowPauseButton(): Boolean {
        return (this.player != null
                && this.player!!.playbackState != Player.STATE_ENDED
                && this.player!!.playbackState != Player.STATE_IDLE
                && this.player!!.playWhenReady)
    }

    private inner class ComponentListener : Player.EventListener, TimeBar.OnScrubListener,
        OnClickListener {

        override fun onScrubStart(timeBar: TimeBar, position: Long) {
            scrubbing = true
            if (positionView != null) {
                positionView.text = Util.getStringForTime(formatBuilder, formatter, position)
            }
        }

        override fun onScrubMove(timeBar: TimeBar, position: Long) {
            if (positionView != null) {
                positionView.text = Util.getStringForTime(formatBuilder, formatter, position)
            }
        }

        override fun onScrubStop(timeBar: TimeBar, position: Long, canceled: Boolean) {
            scrubbing = false
            if (!canceled) {
                player?.let {
                    seekToTimeBarPosition(it, position)
                }
            }
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, @Player.State playbackState: Int) {
            updatePlayPauseButton()
            updateProgress()
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            updateProgress()
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            updateRepeatModeButton()
            updateNavigation()
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            updateShuffleButton()
            updateNavigation()
        }

        override fun onPositionDiscontinuity(@Player.DiscontinuityReason reason: Int) {
            updateNavigation()
            updateTimeline()
        }

        override fun onTimelineChanged(timeline: Timeline, @Player.TimelineChangeReason reason: Int) {
            updateNavigation()
            updateTimeline()
        }

        override fun onClick(view: View) {
            val player = this@ExoPlayerControlView.player ?: return
            if (nextButton === view) {
                next(player)
            } else if (previousButton === view) {
                previous(player)
            } else if (fastForwardButton === view) {
                fastForward(player)
            } else if (rewindButton === view) {
                rewind(player)
            } else if (playButton === view) {
                if (player.playbackState == Player.STATE_IDLE) {
                    if (playbackPreparer != null) {
                        playbackPreparer!!.preparePlayback()
                    }
                } else if (player.playbackState == Player.STATE_ENDED) {
                    seekTo(player, player.currentWindowIndex, C.TIME_UNSET)
                }
                controlDispatcher.dispatchSetPlayWhenReady(player, true)
            } else if (pauseButton === view) {
                controlDispatcher.dispatchSetPlayWhenReady(player, false)
            } else if (repeatToggleButton === view) {
                controlDispatcher.dispatchSetRepeatMode(
                    player, RepeatModeUtil.getNextRepeatMode(player.repeatMode, repeatToggleModes)
                )
            } else if (shuffleButton === view) {
                controlDispatcher.dispatchSetShuffleModeEnabled(player, !player.shuffleModeEnabled)
            }
        }
    }

    companion object {

        init {
            ExoPlayerLibraryInfo.registerModule("goog.exo.ui")
        }

        /** The default fast forward increment, in milliseconds.  */
        val DEFAULT_FAST_FORWARD_MS = 15000
        /** The default rewind increment, in milliseconds.  */
        val DEFAULT_REWIND_MS = 5000
        /** The default show timeout, in milliseconds.  */
        val DEFAULT_SHOW_TIMEOUT_MS = 5000
        /** The default repeat toggle modes.  */
        @RepeatModeUtil.RepeatToggleModes
        val DEFAULT_REPEAT_TOGGLE_MODES = RepeatModeUtil.REPEAT_TOGGLE_MODE_NONE
        /** The default minimum interval between time bar position updates.  */
        val DEFAULT_TIME_BAR_MIN_UPDATE_INTERVAL_MS = 200
        /** The maximum number of windows that can be shown in a multi-window time bar.  */
        val MAX_WINDOWS_FOR_MULTI_WINDOW_TIME_BAR = 100

        private val MAX_POSITION_FOR_SEEK_TO_PREVIOUS: Long = 3000
        /** The maximum interval between time bar position updates.  */
        private val MAX_UPDATE_INTERVAL_MS = 1000

        @RepeatModeUtil.RepeatToggleModes
        private fun getRepeatToggleModes(
            a: TypedArray, @RepeatModeUtil.RepeatToggleModes repeatToggleModes: Int
        ): Int {
            return a.getInt(R.styleable.ExoPlayerControlView_repeat_toggle_modes, repeatToggleModes)
        }

        @SuppressLint("InlinedApi")
        private fun isHandledMediaKey(keyCode: Int): Boolean {
            return (keyCode == KeyEvent.KEYCODE_MEDIA_FAST_FORWARD
                    || keyCode == KeyEvent.KEYCODE_MEDIA_REWIND
                    || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
                    || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY
                    || keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE
                    || keyCode == KeyEvent.KEYCODE_MEDIA_NEXT
                    || keyCode == KeyEvent.KEYCODE_MEDIA_PREVIOUS)
        }

        /**
         * Returns whether the specified `timeline` can be shown on a multi-window time bar.
         *
         * @param timeline The [Timeline] to check.
         * @param window A scratch [Timeline.Window] instance.
         * @return Whether the specified timeline can be shown on a multi-window time bar.
         */
        private fun canShowMultiWindowTimeBar(
            timeline: Timeline,
            window: Timeline.Window
        ): Boolean {
            if (timeline.windowCount > MAX_WINDOWS_FOR_MULTI_WINDOW_TIME_BAR) {
                return false
            }
            val windowCount = timeline.windowCount
            for (i in 0 until windowCount) {
                if (timeline.getWindow(i, window).durationUs == C.TIME_UNSET) {
                    return false
                }
            }
            return true
        }
    }
}/* attrs= *//* defStyleAttr= */
