package com.swensun.potato

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import com.swensun.base.BaseActivity
import com.swensun.swutils.SwUtils
import com.swensun.swutils.util.LogUtils
import kotlinx.android.synthetic.main.activity_main.*
import android.view.GestureDetector.OnGestureListener as OnGestureListener

class MainActivity : BaseActivity() {

    lateinit var gestureDetector: GestureDetector

    override fun getContentSubView(): Int {
        return R.layout.activity_main
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        gestureDetector = GestureDetector(this, object : OnGestureListener {
            override fun onShowPress(e: MotionEvent?) {
                LogUtils.d("onShowPress")
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                LogUtils.d("onSingleTapUp")
                return false
            }

            override fun onDown(e: MotionEvent?): Boolean {
                LogUtils.d("onDown")
                return false
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                LogUtils.d("onFling")
                return false
            }

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                LogUtils.d("onScroll")
                return false
            }

            override fun onLongPress(e: MotionEvent?) {
                LogUtils.d("onLongPress")
            }
        })
        SwUtils.init(application)
    }

    private fun initView() {
        dialog.setOnClickListener {
            ScoreDialog(this@MainActivity)
//                .withTransparent()
                .withSize(300, 300)
                .show()
        }
    }
}
