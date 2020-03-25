package com.swensun.func.cardview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swensun.func.cardview.ui.cardview.CardViewFragment
import com.swensun.potato.R

class CardViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_view_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CardViewFragment.newInstance())
                .commitNow()
        }
    }
}
