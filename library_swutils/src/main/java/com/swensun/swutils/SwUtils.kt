package com.swensun.swutils

import android.app.Application
import android.content.pm.ApplicationInfo
import kotlin.properties.Delegates


object SwUtils {
    fun init(application: Application) {
        SwUtils.application = application
        debug = (application.applicationInfo.flags
                and ApplicationInfo.FLAG_DEBUGGABLE) != 0

    }

    var debug: Boolean by Delegates.notNull()
        private set
    lateinit var application: Application
        private set
}