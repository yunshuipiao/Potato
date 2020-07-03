package com.swensun.func.feature

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import org.jetbrains.anko.toast

class FeatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feature_activity)
        toast("FeatureActivity create")
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FeatureFragment.newInstance())
                .commitNow()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        toast("FeatureActivity destroy")
    }
}
