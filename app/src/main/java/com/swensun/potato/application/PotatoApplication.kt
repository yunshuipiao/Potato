package com.swensun.potato.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class PotatoApplication: Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}