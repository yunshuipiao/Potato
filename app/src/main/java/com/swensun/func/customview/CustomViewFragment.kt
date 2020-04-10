package com.swensun.func.customview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.swensun.potato.R
import kotlinx.android.synthetic.main.custom_view_fragment.*

class CustomViewFragment : Fragment() {

    companion object {
        fun newInstance() = CustomViewFragment()
    }

    private lateinit var viewModel: CustomViewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.custom_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CustomViewViewModel::class.java)
        activity?.lifecycle?.addObserver(loading_view)
        activity?.lifecycle?.addObserver(skeleton_view)
        activity?.lifecycle?.addObserver(google_view)
        activity?.lifecycle?.addObserver(bagua_view)
        activity?.lifecycle?.addObserver(fo_view)
        activity?.lifecycle?.addObserver(taiji_view)
    }
}
