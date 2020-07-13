package com.swensun.func.viewpager.fragment

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.swensun.swutils.ui.dp2px
import com.swensun.swutils.ui.getWinWidth
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.h_item.view.*


class GridSpacingItemDecoration(
    val spanCount: Int = 3,
    val spacing: Int = dp2px(12f),
    val includeEdge: Boolean = true
) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val left = view.left
        when {
            left < getWinWidth() * 0.25 -> {
//                outRect.left = dp2px(12f)
//                outRect.right = dp2px(6f)
                view.tv_number.text = " - $position - ${left}- left"
            }
            left < getWinWidth() * 0.5 -> {
//                outRect.left = dp2px(6f)
//                outRect.right = dp2px(6f)
                view.tv_number.text = " - $position - ${left}- center"
            }
            left < 0.75 * getWinWidth() -> {
//                outRect.left = dp2px(6f)
//                outRect.right = dp2px(12f)
                view.tv_number.text = " - $position - ${left}- right"
            }
        }
        outRect.bottom = dp2px(18f)
        Logger.d("grid parent")
    }
}