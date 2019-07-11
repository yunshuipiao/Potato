package com.swensun.potato

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.swensun.base.BaseDialog
import com.swensun.base.BaseDialogFragment
import com.swensun.swutils.ui.*
import kotlinx.android.synthetic.main.dialog_score.*

public class ScoreDialog(context: Context): BaseDialog(context) {


    override fun onCreateDialogView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_score, null)
        return view
    }

}