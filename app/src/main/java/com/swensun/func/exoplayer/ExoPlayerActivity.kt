package com.swensun.func.exoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.swensun.potato.R

class ExoPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exo_player_activity)
        
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ExoPlayerFragment.newInstance())
                .commitNow()
        }
    }
}
