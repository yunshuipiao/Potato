package com.swensun.potato

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swensun.swutils.util.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity<MainActivity>()
        finish()
    }
}