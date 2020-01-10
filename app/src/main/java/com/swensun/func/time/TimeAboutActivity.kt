package com.swensun.func.time

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swensun.potato.R

class TimeAboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.time_about_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TimeAboutFragment.newInstance())
                .commitNow()
        }
    }

}
