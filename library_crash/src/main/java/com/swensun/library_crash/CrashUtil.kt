package com.swensun.library_crash

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import java.io.PrintWriter
import java.io.StringWriter

object CrashUtil {

    var resumeActivity: Activity? = null
    lateinit var application: Application


    fun init(application: Application) {
        this.application = application
        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks() {
            override fun onActivityResumed(activity: Activity?) {
                resumeActivity = activity
            }
        })

        Handler(Looper.getMainLooper()).post {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    //捕获异常处理
                    caughtException(Looper.getMainLooper().thread, e)
                }
            }
        }
        Thread.setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler())
    }

    private  class UncaughtExceptionHandler : Thread.UncaughtExceptionHandler {
        override fun uncaughtException(t: Thread?, e: Throwable?) {
            caughtException(t, e)
        }
    }

    private fun  caughtException(t: Thread?, e: Throwable?) {
        val sw =  StringWriter()
        val pw = PrintWriter(sw)
        e?.printStackTrace(pw)
        AlertDialog.Builder(resumeActivity)
            .setTitle(t?.name)
            .setMessage(sw.toString())
            .show()
    }
    
    private open class ActivityLifecycleCallbacks: Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {

        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        }
    }
}


