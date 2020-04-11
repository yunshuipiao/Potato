package com.swensun.func.trans

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.swensun.potato.R
import com.swensun.swutils.ui.px2dp
import kotlinx.android.synthetic.main.activity_trans_font.*
import org.jetbrains.anko.toast

class TransFontActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans_font)
    }

    fun onClick(v: View) {
        (v as? TextView)?.let {
            toast("text: ${it.height}")
        }
    }
}
