package com.swensun.base

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment: DialogFragment() {

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val build = AlertDialog.Builder(requireContext())
//        build.setView(onCreateDialogView())
//        return build.create()
//    }

    private var isTran: Boolean? = null
    private lateinit var mActivity: BaseActivity

    /**
     *  dialog.show() 之后才会调用的方法，可用建造者模式设置背景透明等信息
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = onCreateDialogView()
        isTran?.let {
            if (it) {
                dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
        return view
    }

     abstract fun onCreateDialogView(): View

    public fun setTransparent(): BaseDialogFragment {
        isTran = true
        return this
    }

    public fun setAct(act: Activity): BaseDialogFragment {
        if (act is BaseActivity) {
            mActivity = act
        }
        return this
    }

    public fun show() {
        show(mActivity.supportFragmentManager, "dialog")
    }
}