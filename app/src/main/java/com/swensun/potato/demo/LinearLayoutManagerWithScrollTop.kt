package com.swensun.potato.demo

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * @author sunwen
 * @Date 2019-11-29
 * @Project Potato
 */
class LinearLayoutManagerWithScrollTop : LinearLayoutManager {

    constructor(context: Context) : super(context)

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    )

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        val topSnappedSmoothScroller = TopSnappedSmoothScroller(recyclerView.context)
        topSnappedSmoothScroller.targetPosition = position
        startSmoothScroll(topSnappedSmoothScroller)
    }

    internal inner class TopSnappedSmoothScroller(context: Context) : LinearSmoothScroller(context) {

        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            return this@LinearLayoutManagerWithScrollTop.computeScrollVectorForPosition(targetPosition)
        }

        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START//设置滚动位置
        }
    }
}