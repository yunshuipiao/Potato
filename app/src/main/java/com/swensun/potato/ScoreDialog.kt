package com.swensun.potato

import android.view.LayoutInflater
import android.view.View
import com.swensun.base.BaseDialogFragment

public class ScoreDialog: BaseDialogFragment() {
    override fun onCreateDialogView(): View {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_score, null)
        return view
    }

}