package com.swensun.func.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swensun.potato.R

class LifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lifecycle_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LifecycleFragment.newInstance())
                .commitNow()
        }
    }

}
