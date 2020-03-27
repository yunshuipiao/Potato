package com.swensun.library_crash

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import java.io.PrintWriter
import java.io.StringWriter

object CrashUtil {

    var resumeActivity: Activity? = null
    lateinit var application: Application
    var debug = false


    fun init(application: Application) {
        this.application = application
        debug = (application.applicationInfo.flags
                and ApplicationInfo.FLAG_DEBUGGABLE) != 0
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

    private class UncaughtExceptionHandler : Thread.UncaughtExceptionHandler {
        override fun uncaughtException(t: Thread?, e: Throwable?) {
            caughtException(t, e)
        }
    }

    private fun caughtException(t: Thread?, e: Throwable?) {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        e?.printStackTrace(pw)
        val message = "线程信息: \n${t?.name}\n" +
                "堆栈信息:\n $sw"
        Handler(Looper.getMainLooper()).post {
            AlertDialog.Builder(resumeActivity)
                .setTitle("发生崩溃,信息如下")
                .setMessage(message)
                .setPositiveButton("确认", null)
                .setCancelable(false)
                .show()
        }
    }

    private open class ActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
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


