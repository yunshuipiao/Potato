package com.swensun.swutils.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

public class WordDailog: DialogFragment() {

    private var mItem: Array<CharSequence>? = null
    private var onClickListener: DialogInterface.OnClickListener? = null

    fun setItems(items: Array<CharSequence>?, onClickListener: DialogInterface.OnClickListener?): WordDailog {
        this.mItem = items
        this.onClickListener = onClickListener
        return this
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
                .setItems(mItem, onClickListener)
                .create()
    }
}