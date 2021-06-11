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

abstract class ViewBindingDialog<VB : ViewBinding> : DialogFragment() {

    data class DialogBuilder<VB : ViewBinding>(val binding: VB, val dialog: DialogFragment)

    var initListener: (DialogBuilder<VB>.() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = inflateBindingWithGeneric<VB>(layoutInflater)
        val dialog = AlertDialog.Builder(context)
            .setView(binding.root)
            .create()
        initView(binding)
        initListener?.invoke(DialogBuilder(binding, this))
        return dialog
    }

    open fun initView(binding: VB) {

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
