package com.swensun.func.status

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.swensun.StatusEvent
import com.swensun.potato.R

class StatusView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    private var parentView: ViewGroup? = null
    private var lastEvent: StatusEvent = StatusEvent.SUCCESS

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_status, this)
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        this.layoutParams = lp
    }

    fun showLoadingStatus() {
        handleParentView(StatusEvent.LOADING)
        //更新view的状态
    }

    fun showEmptyStatus() {
        handleParentView(StatusEvent.EMPTY)
    }

    fun showErrorStatus() {
        handleParentView(StatusEvent.ERROR)
    }

    fun removeStatus() {
        handleParentView(StatusEvent.SUCCESS)
    }

    fun bindParentView(parent: ViewGroup) {
        this.parentView = parent
    }

    private fun handleParentView(event: StatusEvent) {
        if (lastEvent == event) {
            return
        }
        parentView?.let { it ->
            if (parent != null) {
                (parent as? ViewGroup)?.removeView(this)
            }
            when (event) {
                StatusEvent.ERROR, StatusEvent.EMPTY -> {
                    it.addView(this, 0)
                }
                StatusEvent.LOADING -> {
                    it.addView(this)
                }
            }
            lastEvent = event
        }
    }
}