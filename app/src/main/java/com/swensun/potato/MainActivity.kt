package com.swensun.potato

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import com.swensun.base.BaseActivity
import com.swensun.func.bottom.BottomActivity
import com.swensun.func.coroutines.ui.CoroutinesActivity
import com.swensun.func.customview.CustomViewActivity
import com.swensun.func.lifecycle.LifecycleActivity
import com.swensun.func.livedata.LiveDataActivity
import com.swensun.func.memory.MemoryActivity
import com.swensun.func.multidialog.MultiDialogActivity
import com.swensun.func.recycler.RecyclerViewActivity
import com.swensun.func.room.RoomActivity
import com.swensun.func.time.TimeAboutActivity
import com.swensun.func.trans.TransFontActivity
import com.swensun.func.viewpager.view.ViewPager2Activity
import com.swensun.swutils.ui.isRtl
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : BaseActivity() {

    override fun getContentSubView(): Int {
        return R.layout.activity_main
    }


    override fun initView(savedInstanceState: Bundle?) {

        btn_coroutines.setOnClickListener {
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

        val wy = "نى ئىزدەش"
        var index = 0
        val list = arrayListOf<String>()
            .apply {
                add("$wy \"hello\"")
                add("\"hello\" $wy")
            }
        btn_custom_view.textDirection = View.TEXT_DIRECTION_LTR
        btn_custom_view.setOnClickListener {
            btn_custom_view.text = list[index]
            isRtl(list[index])
            index = (index + 1) % list.size
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

