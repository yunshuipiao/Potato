package com.swensun.func.push

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.swensun.func.utilcode.UtilCodeActivity
import com.swensun.potato.R
import kotlinx.android.synthetic.main.push_fragment.*
import org.jetbrains.anko.support.v4.startActivity

class PushFragment : Fragment() {

    companion object {
        fun newInstance() = PushFragment()
    }

    private lateinit var viewModel: PushViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.push_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PushViewModel::class.java)
        btn_push.setOnClickListener {
            startActivity<UtilCodeActivity>()
        }
    }
}