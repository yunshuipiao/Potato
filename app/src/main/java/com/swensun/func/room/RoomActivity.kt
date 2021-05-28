package com.swensun.func.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swensun.base.Base2Activity
import com.swensun.potato.R
import com.swensun.potato.databinding.RoomActivityBinding

class RoomActivity : Base2Activity<RoomActivityBinding>() {
    
    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RoomFragment.newInstance())
                .commitNow()
        }
    }
}
