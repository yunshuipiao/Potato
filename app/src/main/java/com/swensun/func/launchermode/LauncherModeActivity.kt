package com.swensun.func.launchermode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swensun.potato.R
import org.jetbrains.anko.toast

class LauncherModeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launcher_mode_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LauncherModeFragment.newInstance())
                .commitNow()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        toast("new intent")
    }

    override fun finish() {
        toast("launcher activity finish")
        super.finish()
    }

    override fun onDestroy() {
        toast("launcher activity destroy")
        super.onDestroy()
    }
}
