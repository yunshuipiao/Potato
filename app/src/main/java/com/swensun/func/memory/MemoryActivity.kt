package com.swensun.func.memory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swensun.potato.R

class MemoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.memory_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MemoryFragment.newInstance())
                .commitNow()
        }
    }

}
