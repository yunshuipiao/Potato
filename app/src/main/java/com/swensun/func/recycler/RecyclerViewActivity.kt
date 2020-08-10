package com.swensun.func.recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.swensun.potato.R

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RecyclerViewFragment.newInstance())
                .commitNow()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
    }
}
