package com.swensun.func.feature

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.swensun.potato.R
import org.jetbrains.anko.toast

class FeatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
        window?.decorView?.let {
        }
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
