package com.swensun.swutils.ui

import android.R
import android.accessibilityservice.AccessibilityServiceInfo
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import android.text.TextPaint
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
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.swensun.swutils.util.SwUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.annotations.NotNull
import java.util.concurrent.TimeUnit

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
fun getString(@StringRes resId: Int, vararg formatArgs: Any) = context.getString(resId, *formatArgs) ?: ""
fun getDimen(@DimenRes resId: Int) = context.resources.getDimensionPixelOffset(resId)
fun getColor(@ColorRes resId: Int) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(resId)
        } else {
            @Suppress("DEPRECATION")
            context.resources.getColor(resId)
        }

fun getColor(color: String) = Color.parseColor(color)
fun dp2px(value: Float): Int {
    val f = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            value, getDisplayMetrics())
    val res = (f + 0.5f).toInt()
    if (res != 0) return res
    if (value == 0f) return 0
    if (value > 0) return 1
    return -1
}

fun sp2px(value: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
        value, getDisplayMetrics())

fun px2dp(pxValue: Float): Int {
    val scale = getDisplayMetrics().density
    return (pxValue / scale + 0.5f).toInt()
}

fun getDisplayMetrics(): DisplayMetrics {
    return context.resources.displayMetrics
}

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
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
    return getSize().y
}

fun getWinWidth(): Int {
    return getSize().x
}

fun getNavigationBarHeight(): Int {
    context.resources.let {
        val id = it.getIdentifier("navigation_bar_height", "dimen", "android")
        return it.getDimensionPixelSize(id)
    }
}

fun TextView.adjustTextSize(maxWidth: Int, text: String) {
    val avaiWidth = maxWidth - this.paddingLeft - this.paddingRight - 10

    if (avaiWidth <= 0) {
        return
    }

    val textPaintClone = TextPaint(this.paint)
    // note that Paint text size works in px not sp
    var trySize = textPaintClone.textSize

    while (textPaintClone.measureText(text) > avaiWidth) {
        trySize--
        textPaintClone.textSize = trySize
    }

    this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize)
    this.text = text
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

fun showSnackBar(@NotNull activity: Activity, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(getActivityRootView(activity), message,Snackbar.LENGTH_SHORT)
            .setDuration(duration)
            .show()
}

fun showSnackBar(@NotNull activity: Activity,
                 message: String,
                 actionMessage: String,
                 actionListener: View.OnClickListener,
                 duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(getActivityRootView(activity), message, Snackbar.LENGTH_SHORT)
            .setAction(actionMessage, actionListener)
            .setDuration(duration)
            .show()
}

fun showSnackBar(@NotNull activity: Activity, @StringRes res: Int) =
        Snackbar.make(getActivityRootView(activity), getString(res),Snackbar.LENGTH_SHORT).show()

fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    val toast = Toast.makeText(context, message, duration)
    Observable.timer(duration.toLong(), TimeUnit.MILLISECONDS)
            .doOnSubscribe {
                toast.show()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                toast.cancel()
            }

}

// 检测开发者选项是否打开: 此方法可以检测Setting.Global下的所有设置是否打开
fun checkDevelopSettings() = Settings.Secure.getInt(context.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED) == 1

fun checkUsbDebugSettings() = Settings.Secure.getInt(context.contentResolver, Settings.Global.ADB_ENABLED) == 1
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