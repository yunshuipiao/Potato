package com.swensun.potato

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.swensun.base.BaseDialogFragment
import com.swensun.swutils.ui.*
import kotlinx.android.synthetic.main.dialog_score.*

public class ScoreDialog: BaseDialogFragment() {
    override fun onCreateDialogView(): View {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_score, null)
        val layout = view.findViewById<LinearLayout>(R.id.layout)
        layout.setRadiusBackground(30, getColor(R.color.colorAccent), TOP_LEFT.or(TOP_RIGHT).or(BOTTOM_RIGHT))
        return view
    }

}