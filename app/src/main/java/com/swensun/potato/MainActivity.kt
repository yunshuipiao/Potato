package com.swensun.potato

import android.content.Context
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.OnGestureListener
import android.view.MotionEvent
import androidx.lifecycle.ViewModelProviders
import com.swensun.base.BaseActivity
import com.swensun.swutils.util.LogUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    lateinit var viewModel: MainViewModel
    
    override fun getContentSubView(): Int {
        return R.layout.activity_main
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        return gestureDetector(this).onTouchEvent(event)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, LayoutFragment.newInstance())
//                .commit()
//        }
        LogUtils.d("mMedia- ${this}, $application")
        hello.setOnClickListener {
            hello.text = "hello1"

            val path = getExternalFilesDir("log")
            LogUtils.d("path, ${path?.path}")
        }
//        startActivity<RecyclerViewActivity>()

    }

//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        LogUtils.d("onWindowFocusChanged: $hasFocus")
//    }
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

