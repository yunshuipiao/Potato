package com.swensun.func.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swensun.base.Base2Activity
import com.swensun.potato.R
import com.swensun.potato.databinding.LifecycleActivityBinding

class LifecycleActivity : Base2Activity<LifecycleActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LifecycleFragment.newInstance())
                .commitNow()
        }
    }
}
