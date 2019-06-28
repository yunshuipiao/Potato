package com.swensun.potato

import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.swensun.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import io.reactivex.functions.Function3 as Function31001
import kotlin.Function as Function1001

class MainActivity : BaseActivity() {

    override fun getContentSubView(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.addLogAdapter(AndroidLogAdapter())
        initView()
    }

    private fun initView() {

        tv1.setOnClickListener {
            var sleep2 = Observable.create<Any> {
                Logger.d("sleep2")
                Thread.sleep(4000)
                it.onNext("2")
            }.subscribeOn(Schedulers.io())
            var sleep1 = Observable.create<Any> {
                Logger.d("sleep1")
                Thread.sleep(1000)
                it.onNext("1")
            }.subscribeOn(Schedulers.io())
//            var sleep3 = Observable.create<Any> {
//                Thread.sleep(3000)
//            }
            var zip = Observable.zip(sleep2, sleep1,
                BiFunction<Any, Any, Any> { _, _ ->
                    Logger.d("sleep1" + "sleep2")

                })

            zip.observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                Logger.d("end")
            }
        }
    }

    inner class InnerC {

    }
}
//