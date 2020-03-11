package com.swensun.func.exoplayer

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swensun.potato.R

class ExoPlayerFragment : Fragment() {

    companion object {
        fun newInstance() = ExoPlayerFragment()
    }

    private lateinit var viewModel: ExoPlayerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.exo_player_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ExoPlayerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
