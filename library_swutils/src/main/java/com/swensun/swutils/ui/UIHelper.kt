package com.swensun.swutils.ui

import android.accessibilityservice.AccessibilityServiceInfo
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.*
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import android.text.format.DateFormat
import android.text.format.Formatter
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.swensun.swutils.R
import com.swensun.swutils.SwUtils
import org.jetbrains.annotations.NotNull
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by macmini on 2017/8/17.
 */

val TAG = "UIHelper"

// 常量

val context = SwUtils.application
private val accessibilityManager =
    context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
private val phonePath = Environment.getDataDirectory()
fun getDrawable(@DrawableRes resId: Int): Drawable? = context.getDrawable(resId)
fun getString(@StringRes resId: Int) = context.getString(resId) ?: ""
fun getString(@StringRes resId: Int, vararg formatArgs: Any) =
    context.getString(resId, *formatArgs) ?: ""

fun getDimen(@DimenRes resId: Int) = context.resources.getDimensionPixelOffset(resId)
fun getColor(@ColorRes resId: Int) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        context.getColor(resId)
    } else {
        @Suppress("DEPRECATION")
        context.resources.getColor(resId)
    }

fun getColor(color: String) = Color.parseColor(color)

val displayMetrics: DisplayMetrics = context.resources.displayMetrics

fun dp2px(value: Float): Int {
    val res = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, displayMetrics)
    return (res + 0.5f).toInt()
}

fun sp2px(value: Float) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP,
    value, displayMetrics
)

fun px2dp(pxValue: Float): Int {
    val scale = displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

val Number.dp: Int
    get() = dp2px(this.toFloat())

val Number.dpf: Float
    get() = dp2px(this.toFloat()).toFloat()

fun hideKeyboard(act: Activity) {
    val view = act.currentFocus
    if (view != null) {
        val imm = act.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun showKeyboard(act: Activity) {
    try {
        val imm = act.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(act.currentFocus!!, InputMethodManager.SHOW_FORCED)
    } catch (exception: NullPointerException) {
        Log.d(TAG, "keyboard ,InputMethodManager can't find focus")
    }
}

@SuppressLint("MissingPermission")
fun isNetworkAvailable(): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val ni = connectivityManager.activeNetworkInfo ?: return false
    return ni.isConnected || ni.isAvailable && ni.isConnectedOrConnecting
}

private fun getSize(): Point {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    var size = Point()
    display.getSize(size)
    return size
}

fun getWinHeight(): Int {
    return displayMetrics.heightPixels
}

fun getWinWidth(): Int {
    return displayMetrics.widthPixels
}

fun getNavigationBarHeight(): Int {
    context.resources.let {
        val id = it.getIdentifier("navigation_bar_height", "dimen", "android")
        return it.getDimensionPixelSize(id)
    }
}

fun checkDeviceNavigation(activity: Activity): Boolean {
    val windowManager = activity.windowManager
    val d = windowManager.defaultDisplay

    val realDisplayMetrics = DisplayMetrics()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        d.getRealMetrics(realDisplayMetrics)
    }

    val realHeight = realDisplayMetrics.heightPixels
    val realWidth = realDisplayMetrics.widthPixels

    val displayMetrics = DisplayMetrics()
    d.getMetrics(displayMetrics)

    val displayHeight = displayMetrics.heightPixels
    val displayWidth = displayMetrics.widthPixels

    return realWidth - displayWidth > 0 || realHeight - displayHeight > 0
}

//click and hide keyboard
fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
    if (v != null && v is EditText) {
        val leftTop = intArrayOf(0, 0)
        v.getLocationInWindow(leftTop)
        val left = leftTop[0]
        val top = leftTop[1]
        val bottom = top + v.height
        val right = left + v.width
        return event.x < left || event.x > right || event.y < top || event.y > bottom
    }
    return false
}

fun isInsAccessibilityServiceEnable(): Boolean {
    val services = "service.MyAccessibilityService"
    val accessibilityServices =
        accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC)
    return accessibilityServices.any { it.id.contains(services) }
}

fun isDevelopAccessibilityServiceEnable(): Boolean {
    val services = "service.DevelopAccessibilityService"
    val accessibilityServices =
        accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC)
    return accessibilityServices.any { it.id.contains(services) }
}

fun openAccessibilitySetting(context: Context) {
    val accessibleIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
    context.startActivity(accessibleIntent)
}

fun getActivityRootView(activity: Activity): View {
    return activity.window.decorView.findViewById(R.id.content)
}

val View.activity: FragmentActivity
    get() {
        var context = context
        while (context is ContextWrapper) {
            if (context is FragmentActivity) {
                return context
            }
            context = context.baseContext
        }
        throw IllegalArgumentException("View could not get activity")

    }

fun showSnackBar(@NotNull activity: Activity, @StringRes res: Int) =
    Snackbar.make(getActivityRootView(activity), getString(res), Snackbar.LENGTH_SHORT).show()

// 检测开发者选项是否打开: 此方法可以检测Setting.Global下的所有设置是否打开
fun checkDevelopSettings() = Settings.Secure.getInt(
    context.contentResolver,
    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED
) == 1

fun checkUsbDebugSettings() =
    Settings.Secure.getInt(context.contentResolver, Settings.Global.ADB_ENABLED) == 1

fun getSDTotalSize(): String? {
    val sdPath = Environment.getExternalStorageDirectory()
    val statfs = StatFs(sdPath.path)
    val blockSize = statfs.blockSizeLong
    val totalBlocks = statfs.blockCountLong
    return Formatter.formatFileSize(context, blockSize * totalBlocks)
}

fun getSDAvaildableSize(): String? {
    val sdPath = Environment.getExternalStorageDirectory()
    val statfs = StatFs(sdPath.path)
    val blockSize = statfs.blockSizeLong
    val availableBlocksLong = statfs.availableBlocksLong
    return Formatter.formatFileSize(context, blockSize * availableBlocksLong)
}

fun getMemInfo(): String {
    val memInfo = ActivityManager.MemoryInfo()
    val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    am.getMemoryInfo(memInfo)
    val availMem = Formatter.formatFileSize(context, memInfo.availMem)
    val totalMem = Formatter.formatFileSize(context, memInfo.totalMem)
    return "$availMem / $totalMem"
}

private val sNextGeneratedId = AtomicInteger(1)

/**
 * Generate a value suitable for use in [.setId].
 * This value will not collide with ID values generated at build time by aapt for R.id.
 *
 * @return a generated ID value
 */
fun generateViewId(): Int {
    while (true) {
        val result = sNextGeneratedId.get()
        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
        var newValue = result + 1
        if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
        if (sNextGeneratedId.compareAndSet(result, newValue)) {
            return result
        }
    }
}


fun isWifiActive(): Boolean {
    val mgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = mgr.activeNetworkInfo
    return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI
}

fun callPhone(act: Activity, number: String) {
    act.startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$number")))
}


fun getStatusBarBarHeight(): Int {
    context.resources.let {
        val id = it.getIdentifier("status_bar_height", "dimen", "android")
        return it.getDimensionPixelSize(id)
    }
}

fun setShowAlpha(act: Activity) {
    val lp = act.window.attributes
    lp.alpha = .8f
    act.window.attributes = lp
}

fun setDismissAlpha(act: Activity) {
    val lp = act.window.attributes
    lp.alpha = 1f
    act.window.attributes = lp
}

fun copyToClipboard(strId: Int) {
    var str = getString(strId).trim()
    copyToClipboard(str)
}

fun copyToClipboard(str: String) {
    val cmb = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("contact info", str.trim())
    cmb.primaryClip = clip
}

fun Long.formatTime(): String {
    val cal = GregorianCalendar()
    cal.timeInMillis = this
    val year = cal[GregorianCalendar.YEAR]
    val month = cal[GregorianCalendar.MONTH] + 1
    val day = cal[GregorianCalendar.DAY_OF_MONTH]
    val hour = cal[GregorianCalendar.HOUR_OF_DAY]
    val min = cal[GregorianCalendar.MINUTE]
    return String.format("%d-%02d-%02d %02d:%02d", year, month, day, hour, min)
}

fun Long.timestamp2FormatTime(): String {
    val now = System.currentTimeMillis()
    val secondTime = 60 * 1000L
    val hourTime = 3600 * 1000L
    val dayTime = 24 * 3600 * 1000L

    val diffTime = now - this * 1000
    return when {
        (diffTime > dayTime) ->
            DateFormat.format("MM/dd", this * 1000).toString()

        (diffTime > hourTime) ->
            "${diffTime / hourTime}小时前"

        (diffTime > secondTime) ->
            "${diffTime / secondTime}分钟前"

        else ->
            "刚刚"
    }
}

fun Long.formattedBrowserNum(): String {
    return when {
        (this >= 100000) -> "${this / 100000}w"
        (this >= 10000) -> String.format("%.1fk", this.toFloat() / 1000)
        else -> {
            this.toString()
        }
    }
}

fun String.isValidMobile(): Boolean = this.matches(Regex("^1\\d{10}$"))

fun String.isValidCode(): Boolean = this.length >= 3

fun String.isValidEmail(): Boolean = this.matches(Regex("^\\w{1,100}@\\w{1,100}\\.\\w{1,100}$"))

fun Int.toFormattedSize(): String {
    return when {
        this >= 1048576 -> String.format("%.1fMB", this.toFloat() / 1048576)
        this >= 1024 -> "${this / 1024}KB"
        this >= 0 -> "${this}B"
        else -> "未知大小"
    }
}

fun String.toThumbnailImageUrl(): String {
    return if (this.contains("huanjuyun")) this + "?ips_thumbnail/4/0/w/200"
    else this
}

fun String.toThumbnailUrl(): String {
    return if (this.contains("huanjuyun")) this + "?ips_thumbnail/4/0/w/100"
    else this
}

fun showKeyboard(view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.showSoftInput(view, 0)
}

fun hideKeyboard(view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun isShownKeyboard(): Boolean {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    return imm?.isActive ?: false
}

//click and hide keyboard
fun View.shouldHideKeyBoard(event: MotionEvent): Boolean {
    if (this is EditText) {
        val leftTop = intArrayOf(0, 0)
        this.getLocationInWindow(leftTop)
        val left = leftTop[0]
        val top = leftTop[1]
        val bottom = top + this.height
        val right = left + this.width
        return event.x < left || event.x > right || event.y < top || event.y > bottom
    }
    return false
}

fun View.setHeight(height: Int) {
    layoutParams?.let {
        it.height = height
        this.layoutParams = it
    }
}

fun View.setWidth(width: Int) {
    layoutParams?.let {
        it.width = width
        this.layoutParams = it
    }
}