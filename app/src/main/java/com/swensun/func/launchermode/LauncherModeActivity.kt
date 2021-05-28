package com.swensun.func.launchermode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swensun.base.Base2Activity
import com.swensun.potato.R
import com.swensun.potato.databinding.LauncherModeActivityBinding
import org.jetbrains.anko.toast

class LauncherModeActivity : Base2Activity<LauncherModeActivityBinding>() {
    
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

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LauncherModeFragment.newInstance())
                .commitNow()
        }
    }
}
