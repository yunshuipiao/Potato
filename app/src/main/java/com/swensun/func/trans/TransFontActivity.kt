package com.swensun.func.trans

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swensun.potato.R
import com.swensun.swutils.ui.px2dp
import kotlinx.android.synthetic.main.activity_trans_font.*
import org.jetbrains.anko.toast

class TransFontActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans_font)
        tv7.setOnClickListener {
            toast("tv7: ${px2dp(it.height.toFloat())}")
        }
    }
}
