package com.swensun.func.touch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swensun.potato.R

class TouchEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.touch_event_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TouchEventFragment.newInstance())
                .commitNow()
        }
    }
}