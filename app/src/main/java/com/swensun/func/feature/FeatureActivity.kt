package com.swensun.func.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swensun.potato.R

class FeatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feature_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FeatureFragment.newInstance())
                .commitNow()
        }
    }
}
