package com.swensun.func.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.swensun.potato.R
import com.swensun.swutils.ui.getWinWidth
import com.swensun.swutils.util.Logger

class LeftLinearSnapHelper : LinearSnapHelper() {
    private var mVerticalHelper: OrientationHelper? = null
    private var mHorizontalHelper: OrientationHelper? = null

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray? {
        logView(targetView)
        val out = IntArray(2)
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager))
        } else {
            out[0] = 0
        }
        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager))
        } else {
            out[1] = 0
        }
        return out
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        val view = if (layoutManager is LinearLayoutManager) {
            if (layoutManager.canScrollHorizontally()) {
                getStartView(layoutManager, getHorizontalHelper(layoutManager))
            } else {
                getStartView(layoutManager, getVerticalHelper(layoutManager))
            }
        } else super.findSnapView(layoutManager)
        logView(view)
        return view
    }

    private fun distanceToStart(
        targetView: View,
        helper: OrientationHelper?
    ): Int {
        var dis = 0
        helper?.let {
            dis = helper.getDecoratedEnd(targetView) - helper.endAfterPadding
        }
        log("dis:$dis, ${helper?.getDecoratedStart(targetView)}")
        return dis
    }

    private fun getStartView(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper?
    ): View? {
        if (helper == null) {
            return null
        }
        if (layoutManager is LinearLayoutManager) {
            val firstChild = layoutManager.findFirstVisibleItemPosition()
            val isLastItem = (layoutManager.findLastCompletelyVisibleItemPosition()
                    == layoutManager.getItemCount() - 1)
            if (firstChild == RecyclerView.NO_POSITION || isLastItem) {
                return null
            }
            val child = layoutManager.findViewByPosition(firstChild)
            return if (getWinWidth() - helper.getDecoratedStart(child) >= helper.getDecoratedMeasurement(child) / 2
            ) {
                child
            } else {
                if (layoutManager.findLastCompletelyVisibleItemPosition()
                    == layoutManager.getItemCount() - 1
                ) {
                    null
                } else {
                    layoutManager.findViewByPosition(firstChild + 1)
                }
            }
        }
        return super.findSnapView(layoutManager)
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (mVerticalHelper == null) {
            mVerticalHelper =
                OrientationHelper.createVerticalHelper(layoutManager)
        }
        return mVerticalHelper
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (mHorizontalHelper == null) {
            mHorizontalHelper =
                OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return mHorizontalHelper
    }


    fun log(msg: String) {
        Logger.d("helper: $msg")
    }

    fun logView(view: View?) {
        try {
            val text = view?.findViewById<TextView>(R.id.tv_id)?.text
            log("id: $text")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}