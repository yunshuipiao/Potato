package com.swensun.func.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swensun.base.Base2Activity
import com.swensun.potato.R
import com.swensun.potato.databinding.CustomViewActivityBinding

class CustomViewActivity : Base2Activity<CustomViewActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CustomViewFragment.newInstance())
                .commitNow()
        }
    }

}
