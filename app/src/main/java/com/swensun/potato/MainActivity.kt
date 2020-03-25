package com.swensun.potato

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.swensun.func.bottom.BottomActivity
import com.swensun.func.coroutines.ui.CoroutinesActivity
import com.swensun.func.customview.CustomViewActivity
import com.swensun.func.exoplayer.ExoPlayerActivity
import com.swensun.func.lifecycle.LifecycleActivity
import com.swensun.func.livedata.LiveDataActivity
import com.swensun.func.memory.MemoryActivity
import com.swensun.func.multidialog.MultiDialogActivity
import com.swensun.func.recycler.RecyclerViewActivity
import com.swensun.func.room.RoomActivity
import com.swensun.func.time.TimeAboutActivity
import com.swensun.func.trans.TransFontActivity
import com.swensun.func.viewpager.view.ViewPager2Activity
import com.swensun.potato.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {

        mBinding.btnCoroutines.setOnClickListener {
            startActivity<CoroutinesActivity>()
        }
        btn_viewpager.setOnClickListener {
            startActivity<ViewPager2Activity>()
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
        btn_custom_view.setOnClickListener {
            startActivity<CustomViewActivity>()
        }
        btn_exo_player.setOnClickListener {
            startActivity<ExoPlayerActivity>()
        }
    }
}

fun TextView.setHighlightText(text: String, highlightText: String, @ColorInt color: Int) {
    val span = SpannableString(text)
    val newHighlightText = highlightText.trim()
    val index = text.indexOf(newHighlightText, 0, true)
    if (index != -1) {
        span.setSpan(ForegroundColorSpan(color), index, index + newHighlightText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    this.text = span
}


