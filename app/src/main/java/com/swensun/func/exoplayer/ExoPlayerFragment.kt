package com.swensun.func.exoplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.swensun.potato.R
import com.swensun.swutils.util.Logger

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

        viewModel = ViewModelProvider(this).get(ExoPlayerViewModel::class.java)

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun log(any: Any) {
        Logger.d("exoplayer: $any")
    }
}


