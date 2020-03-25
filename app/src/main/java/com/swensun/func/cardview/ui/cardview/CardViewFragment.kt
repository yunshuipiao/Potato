package com.swensun.func.cardview.ui.cardview

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swensun.func.cardview.R

class CardViewFragment : Fragment() {

    companion object {
        fun newInstance() = CardViewFragment()
    }

    private lateinit var viewModel: CardViewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.card_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CardViewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
