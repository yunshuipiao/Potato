package com.swensun.potato.frag

import android.os.Bundle
import android.os.PersistableBundle
import android.text.format.DateFormat
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.swensun.potato.R
import com.swensun.swutils.ui.adjustTextSize
import com.swensun.swutils.ui.getWinWidth
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_time.*
import java.util.concurrent.TimeUnit

/**
 * @Date 2019-08-07
 * @author sunwen
 * @Project Potato
 */
class TimeActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_time)
        initView()
    }

    private fun initView() {
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
}


fun getCurTime(): String {
    val now = System.currentTimeMillis()
    return DateFormat.format("HH:mm:ss", now).toString()
}