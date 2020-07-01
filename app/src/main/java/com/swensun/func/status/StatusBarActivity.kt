package com.swensun.func.status

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swensun.potato.R

class StatusBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.status_bar_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, StatusBarFragment.newInstance())
                .commitNow()
        }
    }
}