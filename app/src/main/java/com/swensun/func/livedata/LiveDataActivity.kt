package com.swensun.func.livedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swensun.potato.R
import kotlinx.android.synthetic.main.live_data_activity.*


class LiveDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.live_data_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    LiveDataFragment.newInstance()
                )
                .commitNow()
        }
    }
}
