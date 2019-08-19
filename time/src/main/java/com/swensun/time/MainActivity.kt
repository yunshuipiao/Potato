package com.swensun.time

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Service
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.Window
import android.view.WindowManager
import com.swensun.swutils.ui.adjustTextSize
import com.swensun.swutils.ui.getWinWidth
import com.swensun.time.setting.SettingsActivity
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

/**
 * @Date 2019-08-07
 * @author sunwen
 * @Project Potato
 */
class MainActivity : RxAppCompatActivity() {

    private val animList = arrayListOf<ObjectAnimator>()
    private var disposable: Disposable? = null
    lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.main_activity)
        initView()
    }

    private fun initView() {
        ma_tv_setting.setOnClickListener {
            // settingActivity
            startActivity<SettingsActivity>()
        }
//        playAnim()
        vibrator = getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
    }
    

    override fun onResume() {
        super.onResume()
        val content = defaultSharedPreferences.getString("signature", "")
        if (content.isNullOrBlank()) {
            startTimeGo()
        } else {
            disposable?.let { if (!it.isDisposed) it.dispose() }
            ma_tv_time.adjustTextSize(getWinWidth(), content)
        }

    }

    @SuppressLint("CheckResult")
    private fun startTimeGo() {
        Observable.interval(1, TimeUnit.SECONDS)
            .compose(bindToLifecycle())
            .doOnSubscribe {
                disposable = it
                ma_tv_time.adjustTextSize(getWinWidth(), getCurTime())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                ma_tv_time.adjustTextSize(getWinWidth(), getCurTime())
                // 震动
//                if (vibrator.hasVibrator()) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        vibrator.vibrate(VibrationEffect.createOneShot(500, 10))
//                    } else {
//                        vibrator.vibrate(500)
//                    }
//                }
                
            }
    }

    private fun playAnim() {
        val anim = ObjectAnimator
            .ofFloat(ma_tv_time, "translationX", 0f + getWinWidth(), 0f - getWinWidth())
            .apply {
                duration = 3000
                repeatMode = ObjectAnimator.RESTART
                repeatCount = ObjectAnimator.INFINITE
            }
        animList.add(anim)
        anim.start()
    }

    override fun onPause() {
        super.onPause()
        animList.forEach {
            it.cancel()
        }
    }
}

fun getCurTime(): String {
    val now = System.currentTimeMillis()
    return DateFormat.format("HH:mm:ss", now).toString()
}
