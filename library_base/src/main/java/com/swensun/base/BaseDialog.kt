package com.swensun.base

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.swensun.swutils.ui.dp2px

abstract class BaseDialog(context: Context) : AlertDialog(context) {

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val build = AlertDialog.Builder(requireContext())
//        build.setView(onCreateDialogView())
//        return build.create()
//    }

    private var isTran: Boolean? = null
    private var mWidth = 0
    private var mHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(onCreateDialogView())
        isTran?.let {
            if (it) {
                window?.setBackgroundDrawable(ColorDrawable(0))
            }
        }
        if (mWidth != 0 && mHeight != 0) {
            window?.setLayout(dp2px(mWidth.toFloat()), dp2px(mHeight.toFloat()))
        }
    }

     abstract fun onCreateDialogView(): View

    public fun withTransparent(): BaseDialog {
        isTran = true
        return this
    }

    public fun withSize(width: Int, height: Int): BaseDialog {
        mWidth = width
        mHeight = height
        return this
    }
}