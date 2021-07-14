package com.swensun.func.status

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.swensun.StatusEvent

class StatusView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var lastEvent: StatusEvent = StatusEvent.SUCCESS
    private val statusViewMap = hashMapOf<StatusEvent, View>()


    fun register(event: StatusEvent, view: View) {
        statusViewMap[event] = view
    }

    fun showLoadingStatus() {
        getContentViewIfNeed()
        handleParentView(StatusEvent.LOADING)
        //更新view的状态
    }

    fun showEmptyStatus() {
        getContentViewIfNeed()
        handleParentView(StatusEvent.EMPTY)
    }

    fun showErrorStatus() {
        getContentViewIfNeed()
        handleParentView(StatusEvent.ERROR)
    }

    fun removeStatus() {
        getContentViewIfNeed()
        handleParentView(StatusEvent.SUCCESS)
    }

    private fun handleParentView(event: StatusEvent) {
        if (lastEvent == event || statusViewMap[event] == null) {
            return
        }
        lastEvent = event
        removeAllViews()
        addView(statusViewMap[event])
        invalidate()
    }

    private fun getContentViewIfNeed() {
        if (statusViewMap[StatusEvent.SUCCESS] == null && this.childCount == 1) {
            statusViewMap[StatusEvent.SUCCESS] = this.getChildAt(0)
        }
    }
}