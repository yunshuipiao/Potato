package com.swensun.func.memory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swensun.base.Base2Activity
import com.swensun.base.BaseFragment
import com.swensun.potato.R
import com.swensun.potato.databinding.MemoryActivityBinding

class MemoryActivity : Base2Activity<MemoryActivityBinding>() {


    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MemoryFragment.newInstance())
                .commitNow()
        }
    }

}
