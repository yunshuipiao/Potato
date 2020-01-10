package com.swensun.func.multidialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swensun.potato.R


class MultiDialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multi_dialog_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MultiDialogFragment.newInstance())
                .commitNow()
        }
    }

}
