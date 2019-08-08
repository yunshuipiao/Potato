package com.swensun.potato.frag

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.media.session.PlaybackStateCompat
import android.text.format.DateFormat
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnCancel
import com.swensun.potato.R
import com.swensun.swutils.ui.adjustTextSize
import com.swensun.swutils.ui.getWinWidth
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.observable.ObservableJoin
import kotlinx.android.synthetic.main.activity_time.*
import java.util.concurrent.TimeUnit

/**
 * @Date 2019-08-07
 * @author sunwen
 * @Project Potato
 */
class TimeActivity : RxAppCompatActivity() {

    private val animList = arrayListOf<ObjectAnimator>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_time)
        initView()
    }

    private fun initView() {
        startTimeGo()
//        playAnim()
    }

    @SuppressLint("CheckResult")
    private fun startTimeGo() {
        Observable.interval(1, TimeUnit.SECONDS)
            .compose(bindToLifecycle())
            .doOnSubscribe {
                at_tv_time.adjustTextSize(getWinWidth(), getCurTime())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                at_tv_time.adjustTextSize(getWinWidth(), getCurTime())
            }
    }

    private fun playAnim() {
        val anim = ObjectAnimator
            .ofFloat(at_tv_time, "translationX", 0f + getWinWidth(), 0f - getWinWidth())
            .apply {
                duration = 3000
                repeatMode =  ObjectAnimator.RESTART
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