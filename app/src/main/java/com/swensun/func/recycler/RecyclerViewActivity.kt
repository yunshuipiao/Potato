package com.swensun.func.recycler

import android.os.Bundle
import com.swensun.base.Base2Activity
import com.swensun.potato.databinding.RecyclerViewActivityBinding

//class RecyclerViewActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.recycler_view_activity)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, RecyclerViewFragment.newInstance())
//                .commitNow()
//        }
//    }
//
//    override fun finish() {
//        super.finish()
//        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
//    }
//}

class RecyclerViewActivity : Base2Activity<RecyclerViewActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(vb.container.id, RecyclerViewFragment.newInstance())
                .commitNow()
        }
    }
}