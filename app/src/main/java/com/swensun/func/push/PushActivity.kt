package com.swensun.func.push

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swensun.func.customview.CustomViewActivity
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import org.jetbrains.anko.startActivity

class PushActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.push_activity)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Logger.d("PushActivity__ onNewIntent")
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val uri = intent?.data
        if (uri != null) {
            val path = uri.path
            val host = uri.host
            if (uri.getQueryParameter("name") == "potato") {
                startActivity<CustomViewActivity>()
            }
            finish()
        }
    }
}