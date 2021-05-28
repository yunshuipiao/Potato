package com.swensun.func.time

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swensun.base.Base2Activity
import com.swensun.potato.MainActivity
import com.swensun.potato.R
import com.swensun.potato.databinding.TimeAboutActivityBinding
import com.swensun.swutils.util.Logger

class TimeAboutActivity : Base2Activity<TimeAboutActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TimeAboutFragment.newInstance())
                .commitNow()
        }
    }

}
