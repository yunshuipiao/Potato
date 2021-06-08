package com.swensun.base

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.swensun.swutils.multitype.inflateBindingWithGeneric
import com.swensun.swutils.util.Logger

/**
 * author : zp
 * date : 2021/6/4
 * Potato
 */

fun dlog(msg: String) {
    Logger.d("__dialog, msg:${msg}")
}

abstract class ViewBindingDialog<VB : ViewBinding>(private val block: (VB.() -> Unit)? = null) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = inflateBindingWithGeneric<VB>(layoutInflater)
        val dialog = AlertDialog.Builder(context)
            .setView(binding.root)
            .create()
        block?.invoke(binding)
        return dialog
    }

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
