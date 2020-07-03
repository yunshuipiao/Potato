package com.swensun.potato

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.format.DateFormat
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ktx.SingleEvent
import com.swensun.func.bottom.BottomActivity
import com.swensun.func.coroutines.ui.CoroutinesActivity
import com.swensun.func.customview.CustomViewActivity
import com.swensun.func.customview.FrameLayoutActivity
import com.swensun.func.exoplayer.ExoPlayerActivity
import com.swensun.func.feature.FeatureActivity
import com.swensun.func.lifecycle.LifecycleActivity
import com.swensun.func.livedata.LiveDataActivity
import com.swensun.func.memory.MemoryActivity
import com.swensun.func.multidialog.MultiDialogActivity
import com.swensun.func.recycler.RecyclerViewActivity
import com.swensun.func.room.RoomActivity
import com.swensun.func.status.StatusBarActivity
import com.swensun.func.status.StatusBarFragment
import com.swensun.func.time.TimeAboutActivity
import com.swensun.func.trans.TransFontActivity
import com.swensun.func.viewpager.fragment.ViewPagerActivity
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.status_bar_fragment.*
import org.jetbrains.anko.startActivity
import kotlin.reflect.jvm.jvmName

class MainActivity : AppCompatActivity() {

    private val today
        get() = DateFormat.format("yyyyMMdd, hh-mm-ss", System.currentTimeMillis()).toString()

    private val globalEventObserver = Observer<SingleEvent<GlobalEvent>> {
        window?.decorView?.postDelayed({
            Logger.d("global-main: from:${it.peekContent()?.from}")
        }, 1000)
    }

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
//        btn_framelayout.setOnClickListener {
//            Logger.d(
//                "student, simpleName:${Student::class.simpleName}, " +
//                        "jvmName:${Student::class.jvmName}, " +
//                        "qualifiedName:${Student::class.qualifiedName}, " +
//                        "name:${Student::class.java.name}, " +
//                        "canonicalName:${Student::class.java.canonicalName}, " +
//                        "simpleName2:${Student::class.java.simpleName}, " +
//                        ""
//            )
//        }
        btn_feature.setOnClickListener {
            startActivity<FeatureActivity>()
        }
        btn_status_navigation.setOnClickListener {
            startActivity<StatusBarActivity>()
        }
        btn_status_navigation.performClick()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

//    override fun onBackPressed() {
//        try {
//            val launcherIntent = Intent(Intent.ACTION_MAIN)
//            launcherIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            launcherIntent.addCategory(Intent.CATEGORY_HOME)
//            startActivity(launcherIntent)
////            moveTaskToBack(true)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Logger.d("onSave")
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

class Student {

}





