package com.swensun.potato

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.swensun.base.BaseDialog

public class ScoreDialog(context: Context): BaseDialog(context) {


    override fun onCreateDialogView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_score, null)
        return view
    }

}