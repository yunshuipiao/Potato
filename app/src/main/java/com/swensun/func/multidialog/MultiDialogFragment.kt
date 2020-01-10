package com.swensun.func.multidialog

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swensun.potato.R

class MultiDialogFragment : Fragment() {

    companion object {
        fun newInstance() = MultiDialogFragment()
    }

    private lateinit var viewModel: MultiDialogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.multi_dialog_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MultiDialogViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
