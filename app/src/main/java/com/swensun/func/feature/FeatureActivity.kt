package com.swensun.func.feature

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.swensun.base.Base2Activity
import com.swensun.potato.R
import com.swensun.potato.databinding.FeatureActivityBinding
import org.jetbrains.anko.toast

class FeatureActivity : Base2Activity<FeatureActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.feature_activity)
        toast("FeatureActivity create")
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FeatureFragment.newInstance())
                .commitNow()
        }
    }
}
