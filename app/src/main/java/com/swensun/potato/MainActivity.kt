package com.swensun.potato

import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.format.DateFormat
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.swensun.TimeLog
import com.swensun.func.bottom.BottomActivity
import com.swensun.func.coroutines.ui.CoroutinesActivity
import com.swensun.func.customview.CustomViewActivity
import com.swensun.func.customview.FrameLayoutActivity
import com.swensun.func.exoplayer.ExoPlayerActivity
import com.swensun.func.lifecycle.LifecycleActivity
import com.swensun.func.livedata.LiveDataActivity
import com.swensun.func.memory.MemoryActivity
import com.swensun.func.multidialog.MultiDialogActivity
import com.swensun.func.recycler.RecyclerViewActivity
import com.swensun.func.room.RoomActivity
import com.swensun.func.time.TimeAboutActivity
import com.swensun.func.trans.TransFontActivity
import com.swensun.func.viewpager.fragment.ViewPagerActivity
import com.swensun.swutils.util.Logger
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


val bitmapList = arrayListOf<Bitmap>()

class MainActivity : AppCompatActivity() {

    //    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val today
        get() = DateFormat.format("yyyyMMdd, hh-mm-ss", System.currentTimeMillis()).toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {

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
        btn_custom_view.setOnClickListener {
            startActivity<CustomViewActivity>()
        }
        btn_exo_player.setOnClickListener {
            startActivity<ExoPlayerActivity>()
        }

        btn_launcher_mode.setOnClickListener {
//            startActivity<LauncherModeActivity>()
        }
        btn_framelayout.setOnClickListener {
            startActivity<FrameLayoutActivity>()
        }

        btn_room.performClick()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        try {
            val launcherIntent = Intent(Intent.ACTION_MAIN)
            launcherIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            launcherIntent.addCategory(Intent.CATEGORY_HOME)
            startActivity(launcherIntent)
//            moveTaskToBack(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Logger.d("onSave")
    }

    private fun sleep1(): Observable<Int> {
        return Observable.just(safe {
            Thread.sleep(1000)
            1000
        } ?: 0)
    }

    private fun sleep2(): Observable<Int> {
        return Observable.just(safe {
            Thread.sleep(2000)
            2000
        } ?: 0)
    }

    private fun sleep3(): Observable<Int> {
        return Observable.just(safe {
            Thread.sleep(3000)
            3000
        } ?: 0)
    }
}

fun TextView.setHighlightText(text: String, highlightText: String, @ColorInt color: Int) {
    val span = SpannableString(text)
    val newHighlightText = highlightText.trim()
    val index = text.indexOf(newHighlightText, 0, true)
    if (index != -1) {
        span.setSpan(
            ForegroundColorSpan(color),
            index,
            index + newHighlightText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.text = span
}

fun <T> safe(block: () -> T): T? {
    return try {
        block.invoke()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}





