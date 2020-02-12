package com.swensun.potato

import android.os.Bundle
import androidx.lifecycle.Observer
import com.swensun.base.BaseActivity
import com.swensun.func.bottom.BottomActivity
import com.swensun.func.coroutines.ui.CoroutinesActivity
import com.swensun.func.lifecycle.LifecycleActivity
import com.swensun.func.livedata.LiveDataActivity
import com.swensun.func.memory.MemoryActivity
import com.swensun.func.multidialog.MultiDialogActivity
import com.swensun.func.recycler.RecyclerViewActivity
import com.swensun.func.room.RoomActivity
import com.swensun.func.time.TimeAboutActivity
import com.swensun.func.trans.TransFontActivity
import com.swensun.func.viewpager.ViewPagerActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class MainActivity : BaseActivity() {

    override fun getContentSubView(): Int {
        return R.layout.activity_main
    }


    override fun initView(savedInstanceState: Bundle?) {

        btn_coroutines.setOnClickListener {
            startActivity<CoroutinesActivity>()
        }
        btn_viewpager.setOnClickListener {
            startActivity<ViewPagerActivity>()
        }
        btn_bottom.setOnClickListener {
            startActivity<BottomActivity>()
        }
        btn_font_trans.setOnClickListener {
            startActivity<TransFontActivity>()
        }
        btn_room.setOnClickListener {
            startActivity<RoomActivity>()
        }
        btn_time.setOnClickListener {
            startActivity<TimeAboutActivity>()
        }
        btn_lifecycle.setOnClickListener {
            startActivity<LifecycleActivity>()
        }
        btn_multi_dialog.setOnClickListener {
            startActivity<MultiDialogActivity>()
        }
        btn_livedata.setOnClickListener {
            startActivity<LiveDataActivity>()
        }
        btn_recycler.setOnClickListener {
            startActivity<RecyclerViewActivity>()
        }
        btn_memory.setOnClickListener {
            startActivity<MemoryActivity>()
        }
    }

    override fun onResume() {
        super.onResume()
    }
}

