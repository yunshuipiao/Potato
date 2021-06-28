package com.swensun.func.launchermode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.R
import com.swensun.potato.databinding.LauncherModeActivityBinding
import com.swensun.swutils.ui.toast

class LauncherModeActivity : BaseActivity<LauncherModeActivityBinding>() {
    
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
