package com.swensun.func.push

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.swensun.func.recycler.RecyclerViewActivity
import com.swensun.func.utilcode.UtilCodeActivity
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import org.jetbrains.anko.startActivity

class PushActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.push_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PushFragment.newInstance())
                .commitNow()
        }
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Logger.d("PushActivity__ onNewIntent")
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val string = intent?.getStringExtra("extra") ?: ""
        Logger.d("PushActivity__ handleIntent, ${string}")
        if (string.isNotBlank()) {
            startActivity<UtilCodeActivity>()
            finish()
        }
    }
}