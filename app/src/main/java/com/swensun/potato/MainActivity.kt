package com.swensun.potato

import android.content.Context
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import com.swensun.base.BaseActivity
import com.swensun.swutils.util.*
import kotlinx.android.synthetic.main.activity_main.*
import android.view.GestureDetector.OnGestureListener as OnGestureListener
import android.media.AudioManager
import android.text.format.DateFormat
import android.util.TimeUtils
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
import androidx.work.WorkManager
import com.swensun.potato.frag.KEY_TIME
import com.swensun.potato.frag.TimeActivity
import com.swensun.potato.frag.TimeWork
import com.swensun.swutils.shareprefence.SharePreferencesManager
import com.swensun.swutils.ui.timestamp2FormatTime
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit


class MainActivity : BaseActivity() {

    private var mAudioManager: AudioManager? = null

    override fun getContentSubView(): Int {
        return R.layout.activity_main
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector(this).onTouchEvent(event)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        filetest()
    }

    private fun filetest() {
        val folder = getExternalFilesDir("")?.absolutePath ?: ""
        LogUtils.d(folder)
        create.setOnClickListener {
            createFile(folder, "1.json")
        }
        get_files.setOnClickListener {
            getFiles(folder).forEach {
                LogUtils.d(it.path + "  :  " + it.parent)
            }
        }
        copy.setOnClickListener {
            copy(folder, "$folder/copy")
        }
        delete.setOnClickListener {
            com.swensun.swutils.util.deleteFile("$folder/1.json")
        }

    }

    private fun initView() {
        dialog.setOnClickListener {
            //            ScoreDialog(this@MainActivity)
////                .withTransparent()
//                .withSize(300, 300)
//                .show()
//            startActivity<RecyclerViewActivity>()
//            LogUtils.d(getRamInfo())
//            LogUtils.d(getInternalMemorySizeInfo())
//            if (mAudioManager == null) {
//                mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
//            }
//            mAudioManager?.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD)
//            startActivity<TimeActivity>()
            
        }
        val saveTime = SharePreferencesManager[KEY_TIME, 0L]
        val timeStr = DateFormat.format("yyyy/MM/EE  HH:mm:ss", saveTime)
        dialog.text = timeStr
        if (saveTime == 0L) {
            val startTimeActivity = PeriodicWorkRequest
                .Builder(TimeWork::class.java, MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                .build()
            WorkManager.getInstance(context).enqueue(startTimeActivity)
        }
    }
}


fun gestureDetector(context: Context) = GestureDetector(context, object : OnGestureListener {
    override fun onShowPress(e: MotionEvent?) {
        LogUtils.d("onShowPress  " + e?.action + "-" + e?.rawX + "-" + e?.x)
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        LogUtils.d("onSingleTapUp  " + e?.action + "-" + e?.rawX + "-" + e?.x)
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        LogUtils.d("onDown  " + e?.action + "-" + e?.rawX + "-" + e?.x)
        return false
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        LogUtils.d("onFling1  " + e1?.action + "-" + e1?.rawX + "-" + e1?.x + "-" + velocityX + "-" + velocityY)
        LogUtils.d("onFling2  " + e2?.action + "-" + e2?.rawX + "-" + e2?.x)
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        LogUtils.d("onScroll1  " + e1?.action + "-" + e1?.rawX + "-" + e1?.x + "-" + distanceX + "-" + distanceY)
        LogUtils.d("onScroll2  " + e2?.action + "-" + e2?.rawX + "-" + e2?.x)
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        LogUtils.d("onLongPress  " + e?.action + "-" + e?.rawX + "-" + e?.x)
    }
})

